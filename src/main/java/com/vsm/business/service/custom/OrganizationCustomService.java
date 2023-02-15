package com.vsm.business.service.custom;

import com.vsm.business.domain.CategoryGroup;
import com.vsm.business.domain.Organization;
import com.vsm.business.repository.OrganizationRepository;
import com.vsm.business.repository.search.OrganizationSearchRepository;
import com.vsm.business.service.dto.CategoryGroupDTO;
import com.vsm.business.service.dto.OrganizationDTO;
import com.vsm.business.service.mapper.OrganizationMapper;
import com.vsm.business.utils.ConditionUtils;
import com.vsm.business.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrganizationCustomService {
    private final Logger log = LoggerFactory.getLogger(OrganizationCustomService.class);

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    private final ConditionUtils conditionUtils;

    private final OrganizationSearchRepository organizationSearchRepository;

    private final ObjectUtils objectUtils;

    public OrganizationCustomService(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper, ConditionUtils conditionUtils, OrganizationSearchRepository organizationSearchRepository, ObjectUtils objectUtils) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.conditionUtils = conditionUtils;
        this.organizationSearchRepository = organizationSearchRepository;
        this.objectUtils = objectUtils;
    }

    public List<OrganizationDTO> findAll() {
        List<OrganizationDTO> result = organizationRepository.findAll().stream().map(organizationMapper::toDto).collect(Collectors.toList());
        log.debug("OrganizationCustomService findAll(): {}", result);
        return result;
    }

    public List<OrganizationDTO> findAll(boolean ignoreField) {
        if(ignoreField){
            List<OrganizationDTO> result = organizationRepository.findAll().stream().filter(ele -> {
                return !this.conditionUtils.checkDelete(ele.getIsDelete()) && this.conditionUtils.checkTrueFalse(ele.getIsActive());
            }).map(ele -> {
                OrganizationDTO organizationDTO = new OrganizationDTO();
                try {organizationDTO = this.objectUtils.coppySimpleType(ele, organizationDTO, OrganizationDTO.class);} catch (IllegalAccessException e) {log.error("{}", e);}

                // chỉnh sửa để lấy được thêm name và code của org cha (DuowngTora 20230202)
                if(ele.getOrgParent() != null){
                    organizationDTO.setOrgParentName(ele.getOrgParent().getOrganizationName());
                    organizationDTO.setOrgParentCode(ele.getOrgParent().getOrganizationCode());
                }else{
                    organizationDTO.setOrgParentName("");
                    organizationDTO.setOrgParentCode("");
                }

                return organizationDTO;
            }).collect(Collectors.toList());
            log.debug("OrganizationCustomService findAll(): {}", result);
            return result;
        }else{
            List<OrganizationDTO> result = organizationRepository.findAll().stream().map(organizationMapper::toDto).collect(Collectors.toList());
            log.debug("OrganizationCustomService findAll(): {}", result);
            return result;
        }
    }

    public OrganizationDTO customSave(OrganizationDTO organizationDTO) throws Exception {
        // check kiểm tra không cho phép tạo trùng dữ liệu (trùng tên trong cùng 1 đơn vị)
        Boolean checkExit = false;
        if(organizationDTO.getOrgParent()!= null){
            checkExit = organizationRepository.findAllByOrganizationNameAndOrgParentId(organizationDTO.getOrganizationName(), organizationDTO.getOrgParent().getId()).stream().anyMatch(ele -> {
                return !this.conditionUtils.checkDelete(ele.getIsDelete()) && !ele.getId().equals(organizationDTO.getId());
            });
        }else{
//            checkExit = this.organizationRepository.findAll().stream().filter(ele -> {
//                return !this.conditionUtils.checkDelete(ele.getIsDelete()) && !ele.getId().equals(organizationDTO.getId()) && ele.getOrgParent() == null;
//            }).anyMatch(ele -> {
//               return ele.getOrganizationName().equals(organizationDTO.getOrganizationName());
//            });

            checkExit = this.organizationRepository.findALlByOrganizationName(organizationDTO.getOrganizationName()).stream().anyMatch(ele -> {
               return ele.getOrgParent() == null &&  !this.conditionUtils.checkDelete(ele.getIsDelete()) && !ele.getId().equals(organizationDTO.getId());
            });
        }
        if(checkExit) throw new RuntimeException("Duplicate Name");

        Organization organization = organizationMapper.toEntity(organizationDTO);
        organization = organizationRepository.save(organization);
        OrganizationDTO result = organizationMapper.toDto(organization);
        try {
            //organizationSearchRepository.save(organization);
        }catch (StackOverflowError e){
            log.debug(e.getMessage());
        }
        return result;
    }

    public OrganizationDTO customGetALlChildOrganization(Long id){
        Organization organ = organizationRepository.findById(id).orElse(null);
        if(organ == null) return null;
        List<OrganizationDTO> listOrg = organizationRepository.getALlChildOrganization(id).stream().map(organizationMapper::toDto).collect(Collectors.toList());
        Stack<OrganizationDTO> stack = new Stack<>();
        Map<Long, OrganizationDTO> mapOrg = new HashMap<>();
        listOrg.stream().forEach(ele -> {mapOrg.put(ele.getId(), ele);});
        stack.push(mapOrg.get(id));
        while(!stack.isEmpty()){
            OrganizationDTO temp = stack.pop();
            for(int i=0; i<listOrg.size(); i++){
                if(listOrg.get(i).getId() == temp.getId()){
                    listOrg.get(i).setChildOrganization(listOrg.stream().filter(ele -> {
                        if(ele.getOrgParent() != null && ele.getOrgParent().getId() == temp.getId()){
                            stack.push(ele);
                            return true;
                        }
                        return false;
                    }).collect(Collectors.toList()));
                }
            }
        }
        return mapOrg.get(id);
    }

    public List<OrganizationDTO> getAllOrganization(Long id){
        List<OrganizationDTO> result = organizationRepository.getALlChildOrganization(id).stream().map(organizationMapper::toDto).collect(Collectors.toList());
        log.debug("OrganizationCustomService: getAllOrganization({}): {}", id, result);
        return result;
    }

    public List<OrganizationDTO> customGetAllOrganization(){
        List<Organization> organizations = organizationRepository.findAll().stream().filter(ele -> ele.getOrgParent() == null).collect(Collectors.toList());
        List<OrganizationDTO> result = new ArrayList<>();
        organizations.forEach(ele -> {
            result.add(this.customGetALlChildOrganization(ele.getId()));
        });
        return result;
    }

    @Transactional
    public List<OrganizationDTO> saveAll(List<OrganizationDTO> organizationDTOList){
//        List<OrganizationDTO> result = this.organizationRepository.saveAll(organizationDTOList.stream().map(organizationMapper::toEntity).collect(Collectors.toList())).stream().map(organizationMapper::toDto).collect(Collectors.toList());
//        log.info("OrganizationCustomService saveAll(): {}", organizationDTOList, result);
        if(organizationDTOList != null){
            organizationDTOList.forEach(ele -> {
                this.organizationRepository.deleteAllChildOrganization(ele.getId());
            });
        }
        return organizationDTOList;
    }

    public List<OrganizationDTO> findAllIgnoreField() {
        //List<OrganizationDTO> result = organizationRepository.findAll().stream().map(organizationMapper::toDto).collect(Collectors.toList());
        List<OrganizationDTO> result = organizationRepository.findAll().stream().map(ele -> {
            OrganizationDTO organizationDTO = new OrganizationDTO();
            if(ele != null){
                try {this.objectUtils.coppySimpleType(ele, organizationDTO, OrganizationDTO.class);} catch (IllegalAccessException e) {log.error("{}", e);}
            }
            return organizationDTO;
        }).collect(Collectors.toList());
        log.debug("OrganizationCustomService findAll(): {}", result);
        return result;
    }
}
