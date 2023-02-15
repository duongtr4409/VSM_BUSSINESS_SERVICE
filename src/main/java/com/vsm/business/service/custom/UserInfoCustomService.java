package com.vsm.business.service.custom;

import com.microsoft.graph.models.User;
import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.domain.*;
import com.vsm.business.repository.*;
import com.vsm.business.repository.search.UserInfoSearchRepository;
import com.vsm.business.service.auth.MD5Service;
import com.vsm.business.service.authenicate.OAuthDTO;
import com.vsm.business.service.authenicate.OAuthTokenDTO;
import com.vsm.business.service.custom.bo.SearchUserDTO;
import com.vsm.business.service.custom.mail.bo.MailInfoDTO;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.UserInfoDTO;
import com.vsm.business.service.mapper.UserInfoMapper;
import com.vsm.business.utils.ConditionUtils;
import com.vsm.business.utils.GeneratorUtils;
import com.vsm.business.utils.OTPUtils;
import org.elasticsearch.common.Strings;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserInfoCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private UserInfoRepository userInfoRepository;
    private UserInfoSearchRepository userInfoSearchRepository;
    protected UserInfoMapper userInfoMapper;
    private OrganizationRepository organizationRepository;
    private UserGroupRepository userGroupRepository;
    private RankRepository rankRepository;

    private GraphService graphService;

    private ConditionUtils conditionUtils;

    private MD5Service md5Service;

    private RoleRepository roleRepository;

    @Value("${microsoft.clientId:d435afb3-26ea-44b8-9486-ccaf133c7c56}")
    private String clientId = "d435afb3-26ea-44b8-9486-ccaf133c7c56";

    @Value("${system.otp.length:4}")
    private int OTP_LENGTH = 4;

    @Value("${system.otp.subject:[Eoffice VCR] Email gửi thông tin xác nhận tài khoản}")
    private String EMAIL_SUBJECT;

    @Value("${system.otp.content:Mã xác nhận:[rfa_otp_code]}")
    private String EMAIL_CONTENT;

    @Value("${system.otp.placeholder:{{OTP_CODE}}}")
    private String OTP_PLACEHOLDER;

    public UserInfoCustomService(UserInfoRepository userInfoRepository, UserInfoSearchRepository userInfoSearchRepository, UserInfoMapper userInfoMapper, OrganizationRepository organizationRepository, UserGroupRepository userGroupRepository, RankRepository rankRepository, GraphService graphService, ConditionUtils conditionUtils, MD5Service md5Service, RoleRepository roleRepository) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoSearchRepository = userInfoSearchRepository;
        this.userInfoMapper = userInfoMapper;
        this.organizationRepository = organizationRepository;
        this.userGroupRepository = userGroupRepository;
        this.rankRepository = rankRepository;
        this.graphService = graphService;
        this.conditionUtils = conditionUtils;
        this.md5Service = md5Service;
        this.roleRepository = roleRepository;
    }

    public List<UserInfoDTO> getAll() {
        List<UserInfoDTO> result = this.userInfoRepository.findAll().stream().map(userInfoMapper::toDto).collect(Collectors.toList());
        log.debug("UserInfoCustomService: getAll() {}", result);
        return result;
    }

    public List<UserInfoDTO> deleteAll(List<UserInfoDTO> userInfoDTOS) {
        log.debug("UserInfoCustomService: deleteAll({})", userInfoDTOS);
        List<Long> ids = userInfoDTOS.stream().map(UserInfoDTO::getId).collect(Collectors.toList());
        this.userInfoRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            userInfoRepository.deleteById(id);
            userInfoSearchRepository.deleteById(id);
            log.debug("UserInfoCustomService: delete({}) {}", id);
            return true;
        } catch (Exception e) {
            log.error("UserInfoCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<UserInfoDTO> saveAll(List<UserInfoDTO> userInfoDTOList) {
        List<UserInfoDTO> result = userInfoRepository.saveAll(userInfoDTOList.stream().map(userInfoMapper::toEntity).collect(Collectors.toList())).stream().map(userInfoMapper::toDto).collect(Collectors.toList());
        log.debug("UserInfoCustomService: saveAll({}) {}", userInfoDTOList, result);
        return result;
    }

    public List<UserInfoDTO> getAllByUserGroup(Long userGroupId) {
        List<UserInfoDTO> result = this.userGroupRepository.findById(userGroupId).get().getUserInfos().stream().map(userInfoMapper::toDto).collect(Collectors.toList());
        log.debug("UserInfoCustomService: getAllByUserGroup({}) {}", userGroupId, result);
        return result;
    }

    public List<UserInfoDTO> getAllByOrganizationAndRank(Long organizationId, Long rankId) {
        List<UserInfoDTO> result;
        Organization organization = (organizationId != null) ? organizationRepository.findById(organizationId).orElse(null) : null;
        Rank rank = (rankId != null) ? rankRepository.findById(rankId).orElse(null) : null;
        if (organization != null && rank == null) {
            result = userInfoRepository.findAllByOrganizations(organization).stream().map(userInfoMapper::toDto).collect(Collectors.toList());
        } else if (rank != null && organization == null) {
            result = userInfoRepository.findAllByRanks(rank).stream().map(userInfoMapper::toDto).collect(Collectors.toList());
        } else if (rank == null && organization == null) {
            result = userInfoRepository.findAll().stream().map(userInfoMapper::toDto).collect(Collectors.toList());
        } else {
            result = userInfoRepository.findAllByOrganizationsAndRanks(organization, rank).stream().map(userInfoMapper::toDto).collect(Collectors.toList());
        }
        log.debug("UserInfoCustomService: getAllByOrganizationAndRank(organizationId: {}, rankId: {}) {}", organizationId, rankId, result);
        return result;
    }

    public List<UserInfoDTO> findAllByIdInMicrosoft(String idInMicrosoft) {
        return this.userInfoRepository.findAllByIdInMicrosoft(idInMicrosoft).stream().map(userInfoMapper::toDto).collect(Collectors.toList());
    }

    public UserInfoDTO findByUsernameOrEmail(String username, String email) {
        Optional<UserInfo> result = this.userInfoRepository.findByuserNameOrEmail(username, email);
        if (!result.isPresent()) {
            return null;
        }
        return userInfoMapper.toDto(result.get());
    }

    public UserInfo myFindByUsernameOrEmail(String username, String email) {
        Optional<UserInfo> result = this.userInfoRepository.findByuserNameOrEmail(username, email);
        if (!result.isPresent()) {
            return null;
        }
        return result.get();
    }

    public UserInfoDTO findByUsernameAndPassword(String username, String password) {
        //Optional<UserInfo> result = this.userInfoRepository.findByuserNameOrEmailAndPassword(username, username, password);
        Optional<UserInfo> result = this.userInfoRepository.findByUserNameAndIsDeleteNotAndIsActiveOrEmailAndIsDeleteNotAndIsActive(username, true, true, username, true, true).stream().filter(ele -> {
            return password.equals(ele.getPassword()) && (ele.getExpiryDate() == null || Instant.now().isBefore(ele.getExpiryDate()));
        }).findFirst();
        if (!result.isPresent()) {
            return null;
        }
        return userInfoMapper.toDto(result.get());
    }

    public UserInfoDTO findByOAuth(OAuthTokenDTO oAuthTokenDTO) {
        User user = graphService.getUserByUsername(oAuthTokenDTO);
        if (user == null) {
            return null;
        }
        Optional<UserInfo> result = this.userInfoRepository.findAllByUserNameAndIsDeleteNotAndIsActive(user.userPrincipalName, true, true).stream().findFirst();
        if (result.isPresent()) {
            return userInfoMapper.toDto(result.get());
        }
        Instant now = Instant.now();
        UserInfo userInfo = new UserInfo();
        userInfo.setIdInMicrosoft(user.id);
        userInfo.setEmail(user.mail);
        userInfo.setFullName(user.displayName);
        userInfo.setGivenName(user.givenName);
        userInfo.setDisplayName(user.displayName);
        userInfo.setName(user.displayName);
        userInfo.setSurname(user.surname);
        userInfo.setAddress(user.streetAddress);
        userInfo.setBusinessPhones(user.businessPhones != null ? user.businessPhones.stream().collect(Collectors.joining(", ")) : "");
        userInfo.setContactAddress(user.streetAddress);
        userInfo.setCreatedDate(now);
        userInfo.setModifiedDate(now);
        userInfo.setFax(user.faxNumber);
        userInfo.setMobilePhone(user.mobilePhone);
        userInfo.setIsActive(true);
        userInfo.setIsDelete(false);
        userInfo.setJobTitle(user.jobTitle);
        userInfo.setMail(user.mail);
        userInfo.setUserName(user.userPrincipalName);
        try {
            userInfo = userInfoRepository.save(userInfo);
        } catch (Exception e) {
            throw e;
        }
        return userInfoMapper.toDto(userInfo);
    }

    private final String mail_send_customer_subject = "VCR: THÔNG TIN TÀI KHOẢN HỆ THỐNG EOFFICE";
    private final String mail_send_customer_content = "<p>Dear anh/chị:</p><p>VCR gửi thông tin tài khoản hệ thống Eoffice:</p><p>-Tài Khoản: <strong>{{userName}}</strong></p><p>-Mật Khẩu: <strong>{{password}}</strong></p><p>anh/chị vui lòng truy cập <a href=\"{{link}}\" rel=\"noopener noreferrer nofollow\">hệ thống</a> để kiểm tra thông tin</p><p><br class=\"ProseMirror-trailingBreak\"></p><p>ps: <u>Mail gửi tự động không cần phản hồi</u></p><p>Thank You.</p>";
    @Value("${system.mailtemplate.link-symtem:https://uat-eoffice.vincom.com.vn/}")
    public String link_system;

    public UserInfoDTO customSave(UserInfoDTO userInfoDTO, boolean isCreate) throws Exception {

        Boolean checkExitSameMail = false;
        checkExitSameMail = this.userInfoRepository.findAllByEmail(userInfoDTO.getEmail()).stream().anyMatch(ele -> !ele.getId().equals(userInfoDTO.getId()) && !this.conditionUtils.checkDelete(ele.getIsDelete()));
        if (checkExitSameMail)
            throw new Exception("Exist User same Email");
        UserInfo userInfo = userInfoMapper.toEntity(userInfoDTO);
        userInfo.setUserName(userInfoDTO.getEmail());
        String password = GeneratorUtils.generateVCRPassword();
        if (userInfoDTO.getId() == null) {
            userInfo.setPassword(md5Service.getMD5(password));
        }
        userInfo.setUserType("Tài Khoản Khách");

        // thêm các quyền được cấu hình mặc định thêm khi tạo mới cho UserInfo
        if(isCreate){
            Set<Role> roles = userInfo.getRoles() != null ? userInfo.getRoles() : new HashSet<>();
            roles.addAll(this.roleRepository.findAllByIsAutoAdd(true).stream().filter(ele -> {
                return !this.conditionUtils.checkDelete(ele.getIsDelete()) && this.conditionUtils.checkTrueFalse(ele.getIsActive());
            }).collect(Collectors.toSet()));
            userInfo.setRoles(roles);
        }

        // thêm các nhóm người dùng được cấu hình mặc định thêm khi tạo UserInfo
        if(isCreate){
            Set<UserGroup> userGroups = userInfo.getUserGroups() != null ? userInfo.getUserGroups() : new HashSet<>();
            userGroups.addAll(this.userGroupRepository.findAllByIsAutoAdd(true).stream().filter(ele -> {
                return !this.conditionUtils.checkDelete(ele.getIsDelete()) && this.conditionUtils.checkTrueFalse(ele.getIsActive());
            }).collect(Collectors.toList()));
            userInfo.setUserGroups(userGroups);

            if(userGroups != null){
                UserInfo finalUserInfo = userInfo;
                userGroups.forEach(ele -> {
                    Set<UserInfo> tmpSet = (ele.getUserInfos() != null ? ele.getUserInfos() : new HashSet<>());
                    tmpSet.add(finalUserInfo);
                    ele.setUserInfos(tmpSet);
                    this.userGroupRepository.save(ele);
                });
            }

        }

        if(!isCreate){      // TH là update thì ko thay đổi đến password
            try {
                UserInfo userInfoOld = this.userInfoRepository.findById(userInfoDTO.getId()).get();
                userInfo.setPassword(userInfoOld.getPassword());
                userInfo.setPasswordEncode(userInfoOld.getPasswordEncode());
            }catch (Exception e){log.error("{}", e);}
        }

        userInfo = userInfoRepository.save(userInfo);
        UserInfoDTO result = userInfoMapper.toDto(userInfo);
        log.debug("UserInfoCustomService : customSave({}): {}", userInfoDTO);
        try {
//            userInfoSearchRepository.save(userInfo);
        } catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e) {

        }
        if (userInfoDTO.getId() == null) {
            // lưu thành công tài khoản -> gửi mail
            try {
                MailInfoDTO mailInfoDTO = new MailInfoDTO();
                String content = new String(this.mail_send_customer_content);
                content = content.replace("{{userName}}", userInfo.getUserName());
                content = content.replace("{{password}}", password);
                content = content.replace("{{link}}", this.link_system);
                mailInfoDTO.setSubject(this.mail_send_customer_subject);
                mailInfoDTO.setEmailAddressTo(Arrays.asList(userInfo.getEmail()));
                mailInfoDTO.setContent(content);
                this.graphService.sendMail(null, mailInfoDTO);
            } catch (Exception e) {
                log.error("{}", e);
            }
        }
        return result;
    }

    public UserInfoDTO resetPassword(UserInfoDTO userInfoDTO) {
        Optional<UserInfo> search = userInfoRepository.findById(userInfoDTO.getId());
        if (!search.isPresent()) {
            throw new NullPointerException();
        }
        UserInfo userInfo = search.get();
        String password = GeneratorUtils.generateVCRPassword();
        userInfo.setPassword(md5Service.getMD5(password));
        try {
            userInfo = userInfoRepository.save(userInfo);
            MailInfoDTO mailInfoDTO = new MailInfoDTO();
            String content = new String(this.mail_send_customer_content);
            content = content.replace("{{userName}}", userInfo.getUserName());
            content = content.replace("{{password}}", password);
            content = content.replace("{{link}}", this.link_system);
            mailInfoDTO.setSubject(this.mail_send_customer_subject);
            mailInfoDTO.setEmailAddressTo(Arrays.asList(userInfo.getEmail()));
            mailInfoDTO.setContent(content);
            this.graphService.sendMail(null, mailInfoDTO);
        } catch (Exception e) {
            log.error("resetPassword: userInfoDTO={}, error={}", userInfoDTO, e);
            throw e;
        }
        return userInfoMapper.toDto(userInfo);
    }

    @Autowired
    private EntityManager entityManager;

    public ISearchResponseDTO searchUser(SearchUserDTO searchUserDTO, Pageable pageable) {
        ISearchResponseDTO iSearchResponseDTO = new ISearchResponseDTO();
        List<UserInfo> result = new ArrayList<>();
        if (searchUserDTO.getFullName() == null && searchUserDTO.getOrganizationId() == null) {
            //return this.userInfoRepository.findAll().stream().filter(ele -> !conditionUtils.checkDelete(ele.getIsDelete()) && !conditionUtils.checkTrueFalse(ele.getIsActive())).map(ele -> this.userInfoMapper.toDto(ele)).collect(Collectors.toList());
            iSearchResponseDTO.setListData(this.userInfoRepository.findAll(pageable).stream().filter(ele -> !conditionUtils.checkDelete(ele.getIsDelete()) && conditionUtils.checkTrueFalse(ele.getIsActive())).map(ele -> this.userInfoMapper.toDto(ele)).collect(Collectors.toList()));
            iSearchResponseDTO.setTotal(this.userInfoRepository.findAll(pageable).stream().filter(ele -> !conditionUtils.checkDelete(ele.getIsDelete()) && conditionUtils.checkTrueFalse(ele.getIsActive())).count());
        } else {
            if (searchUserDTO.getFullName() == null) searchUserDTO.setFullName("");
            if (searchUserDTO.getOrganizationId() == null) searchUserDTO.setOrganizationId(-1L);
            int pageNumber = pageable.getPageNumber();
            int pageSize = pageable.getPageSize();
            String query = "select * from user_info where (is_delete is null or is_delete = false) and (is_active = true) and (\"length\"('" + searchUserDTO.getFullName() + "') = 0 or \"upper\"(full_name) like '%" + searchUserDTO.getFullName().toUpperCase() + "%' or  \"upper\"(email) like '%" + searchUserDTO.getFullName().toUpperCase() + "%') and ( " + searchUserDTO.getOrganizationId() + "=-1 or id in (select user_info_id From rel_user_info__organization where organization_id = " + searchUserDTO.getOrganizationId() + ")) order by id desc limit " + pageSize + " offset " + pageNumber + ";";
            Query nativeQuery = entityManager.createNativeQuery(query, UserInfo.class);
            result = nativeQuery.getResultList();

            String totalCountQuery = "select count(1) from user_info where (is_delete is null or is_delete = false) and (is_active = true) and (\"length\"('" + searchUserDTO.getFullName() + "') = 0 or \"upper\"(full_name) like '%" + searchUserDTO.getFullName().toUpperCase() + "%' or  \"upper\"(email) like '%" + searchUserDTO.getFullName().toUpperCase() + "%') and ( " + searchUserDTO.getOrganizationId() + "=-1 or id in (select user_info_id From rel_user_info__organization where organization_id = " + searchUserDTO.getOrganizationId() + "));";
            Query nativeQueryCount = entityManager.createNativeQuery(totalCountQuery);
            BigInteger total = (BigInteger) nativeQueryCount.getSingleResult();

            iSearchResponseDTO.setTotal(total.longValue());
            iSearchResponseDTO.setListData(result.stream().map(ele -> this.userInfoMapper.toDto(ele)).collect(Collectors.toList()));
        }
        return iSearchResponseDTO;
    }

    public UserInfoDTO createOTP(UserInfoDTO userInfoDTO) {
        Optional<UserInfo> search = userInfoRepository.findById(userInfoDTO.getId());
        UserInfo userInfo = search.get();
        String otp = OTPUtils.generate(OTP_LENGTH);
        userInfo.setPasswordEncode(md5Service.getMD5(otp));
        try {
            userInfoRepository.save(userInfo);
            List<String> receiver = new ArrayList<>();
            receiver.add(userInfo.getEmail());
            graphService.sendMail(receiver, EMAIL_SUBJECT, EMAIL_CONTENT.replace(OTP_PLACEHOLDER, otp));
            System.out.println("DuowngTora: " + otp);
        } catch (Exception e) {
            return null;
        }
        return userInfoMapper.toDto(userInfo);
    }

    public boolean verify(UserInfoDTO userInfoDTO, String otp) {
        if (!userInfoDTO.getPasswordEncode().equals(md5Service.getMD5(otp))) {
            return false;
        }
        Optional<UserInfo> search = userInfoRepository.findById(userInfoDTO.getId());
        UserInfo userInfo = search.get();
        userInfo.setPasswordEncode(md5Service.getMD5(userInfo.getPasswordEncode()));
        try {
            userInfoRepository.save(userInfo);
            return true;
        } catch (Exception e) {
            log.error("verify({},{}): {}", userInfoDTO, otp, e);
        }
        return false;
    }

    public Optional<UserInfoDTO> findByIdAndIsDeleteNot(Long id, boolean isDelete) {
        return userInfoRepository.findByIdAndIsDeleteNot(id, isDelete).map(userInfoMapper::toDto);
    }

    public Optional<UserInfoDTO> findActiveById(Long id) {
        return findByIdAndIsDeleteNot(id, true);
    }
}
