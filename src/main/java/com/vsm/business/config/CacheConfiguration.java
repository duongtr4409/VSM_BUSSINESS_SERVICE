package com.vsm.business.config;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.redisson.Redisson;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;

    @Bean
    public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(JHipsterProperties jHipsterProperties) {
        MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();

        URI redisUri = URI.create(jHipsterProperties.getCache().getRedis().getServer()[0]);

        Config config = new Config();
        if (jHipsterProperties.getCache().getRedis().isCluster()) {
            ClusterServersConfig clusterServersConfig = config
                .useClusterServers()
                .setTimeout(100000)
                .setMasterConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .addNodeAddress(jHipsterProperties.getCache().getRedis().getServer());

            if (redisUri.getUserInfo() != null) {
                clusterServersConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        } else {
            SingleServerConfig singleServerConfig = config
                .useSingleServer()
                .setTimeout(100000)
                .setConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .setAddress(jHipsterProperties.getCache().getRedis().getServer()[0]);

            if (redisUri.getUserInfo() != null) {
                singleServerConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        }
        jcacheConfig.setStatisticsEnabled(true);
        jcacheConfig.setExpiryPolicyFactory(
            CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, jHipsterProperties.getCache().getRedis().getExpiration()))
        );
        return RedissonConfiguration.fromInstance(Redisson.create(config), jcacheConfig);
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cm) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cm);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer(javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
        return cm -> {
            /*createCache(cm, com.vsm.business.domain.Field.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Field.class.getName() + ".fieldData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Field.class.getName() + ".fieldInForms", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Field.class.getName() + ".forms", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Form.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Form.class.getName() + ".fields", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Form.class.getName() + ".requests", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Form.class.getName() + ".formData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Form.class.getName() + ".fieldInForms", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.FieldInForm.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.FieldInForm.class.getName() + ".fieldData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Pattern.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Rank.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Rank.class.getName() + ".rankInOrgs", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Rank.class.getName() + ".userInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Role.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Role.class.getName() + ".userInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Role.class.getName() + ".userGroups", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Organization.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Organization.class.getName() + ".requestData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Organization.class.getName() + ".dispatchBooks", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Organization.class.getName() + ".transferHandles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Organization.class.getName() + ".rankInOrgs", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Organization.class.getName() + ".templateForms", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Organization.class.getName() + ".userInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Organization.class.getName() + ".processInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Organization.class.getName() + ".dispatchBookOrgs", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Organization.class.getName() + ".mailTemplates", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Organization.class.getName() + ".orgStorages", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".roles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".ranks", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".organizations", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".userInSteps", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdFields", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedFields", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdForms", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedForms", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdFieldInForms", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedFieldInForms", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdRanks", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedRanks", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdRoles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedRoles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdUserInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedUserInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdUserInSteps", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedUserInSteps", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdStepInProcesses", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedStepInProcesses", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdSteps", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedSteps", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdProcessInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedProcessInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdTemplateForms", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedTemplateForms", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdRequests", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedRequests", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdRequestTypes", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedRequestTypes", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdRequestGroups", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedRequestGroups", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdProcessDatas", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedProcessDatas", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdStepDatas", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedStepDatas", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdRequestDatas", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedRequestDatas", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".approvedRequestDatas", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".revokedRequestDatas", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdStatuses", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedStatuses", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdFormDatas", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedFormDatas", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdFieldDatas", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedFieldDatas", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdAttachmentFiles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedAttachmentFiles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdFileTypes", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedFileTypes", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdReqdataChangeHis", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedReqdataChangeHis", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdCategoryDatas", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedCategoryDatas", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".createdCategoryGroups", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".modifiedCategoryGroups", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".signatureInfomations", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".attachmentPermisitions", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".signData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".requestData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".stepData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".userGroups", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".offDispatchUserReads", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInfo.class.getName() + ".receiversHandles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserGroup.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserGroup.class.getName() + ".userInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserGroup.class.getName() + ".roles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.SignatureInfomation.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.SignatureInfomation.class.getName() + ".signData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.UserInStep.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.StepInProcess.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.StepInProcess.class.getName() + ".userInSteps", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Step.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Step.class.getName() + ".stepInProcesses", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ProcessInfo.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ProcessInfo.class.getName() + ".organizations", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ProcessInfo.class.getName() + ".stepInProcesses", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ProcessInfo.class.getName() + ".requests", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ProcessInfo.class.getName() + ".mailTemplates", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.TemplateForm.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.TemplateForm.class.getName() + ".organizations", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.TemplateForm.class.getName() + ".attachmentFiles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.TemplateForm.class.getName() + ".requests", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Request.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Request.class.getName() + ".templateForms", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Request.class.getName() + ".processInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Request.class.getName() + ".requestData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestType.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestType.class.getName() + ".requests", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestType.class.getName() + ".requestData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestGroup.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestGroup.class.getName() + ".requests", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestGroup.class.getName() + ".requestData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ProcessData.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ProcessData.class.getName() + ".stepData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.StepData.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.StepData.class.getName() + ".userInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.StepData.class.getName() + ".reqdataProcessHis", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.StepData.class.getName() + ".examines", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.StepData.class.getName() + ".consults", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.StepData.class.getName() + ".attachmentInSteps", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.StepData.class.getName() + ".requestRecalls", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.StepData.class.getName() + ".transferHandles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.StepData.class.getName() + ".resultOfSteps", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ResultOfStep.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestData.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestData.class.getName() + ".userInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestData.class.getName() + ".formData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestData.class.getName() + ".attachmentFiles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestData.class.getName() + ".reqdataProcessHis", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestData.class.getName() + ".reqdataChangeHis", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestData.class.getName() + ".processData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestData.class.getName() + ".stepData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestData.class.getName() + ".fieldData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestData.class.getName() + ".informationInExchanges", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestData.class.getName() + ".tagInExchanges", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestData.class.getName() + ".requestRecalls", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestData.class.getName() + ".oTPS", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestData.class.getName() + ".signData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestData.class.getName() + ".manageStampInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Status.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Status.class.getName() + ".statusRequests", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Status.class.getName() + ".subStatusRequests", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.FormData.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.FormData.class.getName() + ".fieldData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.FieldData.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.AttachmentFile.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.AttachmentFile.class.getName() + ".attachmentPermisitions", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.AttachmentFile.class.getName() + ".changeFileHistories", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.FileType.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.FileType.class.getName() + ".attachmentFiles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.AttachmentPermisition.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ChangeFileHistory.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ReqdataProcessHis.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ReqdataProcessHis.class.getName() + ".attachmentFiles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ReqdataChangeHis.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".fields", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".forms", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".fieldInForms", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".ranks", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".roles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".userInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".userInSteps", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".stepInProcesses", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".steps", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".processInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".templateForms", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".requests", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".requestTypes", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".requestGroups", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".processData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".stepData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".requestData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".statuses", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".formData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".fieldData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".attachmentFiles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".fileTypes", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".reqdataChangeHis", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".categoryData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".categoryGroups", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Tennant.class.getName() + ".resultOfSteps", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.CategoryGroup.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.CategoryGroup.class.getName() + ".categoryData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.CategoryGroup.class.getName() + ".children", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.CategoryData.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ThemeConfig.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Examine.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Examine.class.getName() + ".examineReplies", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ExamineReply.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Consult.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Consult.class.getName() + ".consultReplies", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ConsultReply.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ManageStampInfo.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ManageStampInfo.class.getName() + ".orgStorages", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.ManageStampInfo.class.getName() + ".attachmentFiles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Stamp.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Stamp.class.getName() + ".manageStampInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.AttachmentInStepType.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.AttachmentInStepType.class.getName() + ".attachmentInSteps", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.AttachmentInStep.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.SignData.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.OTP.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.InformationInExchange.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.InformationInExchange.class.getName() + ".tagInExchanges", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.TagInExchange.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RequestRecall.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.MailLog.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.MailTemplate.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.MailTemplate.class.getName() + ".organizations", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.MailTemplate.class.getName() + ".processInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.MailTemplate.class.getName() + ".stepInProcesses", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.MailTemplate.class.getName() + ".stepInProMailTemplateCustomers", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.MailTemplate.class.getName() + ".steps", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.MailTemplate.class.getName() + ".stepMailTemplateCustomers", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.MailTemplate.class.getName() + ".stepData", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.MailTemplate.class.getName() + ".stepDataMailTemplateCustomers", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.MailTemplate.class.getName() + ".attachmentFiles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.PriorityLevel.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.PriorityLevel.class.getName() + ".officialDispatches", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.SecurityLevel.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.SecurityLevel.class.getName() + ".officialDispatches", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.DocumentType.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.DocumentType.class.getName() + ".officialDispatches", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.OfficialDispatchType.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.OfficialDispatchType.class.getName() + ".officialDispatches", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.OfficialDispatchStatus.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.OfficialDispatchStatus.class.getName() + ".officialDispatches", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.DispatchBook.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.DispatchBook.class.getName() + ".dispatchBookOrgs", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.DispatchBook.class.getName() + ".officialDispatches", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.DispatchBook.class.getName() + ".transferHandles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.DispatchBookType.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.OfficialDispatch.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.OfficialDispatch.class.getName() + ".offDispatchUserReads", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.OfficialDispatch.class.getName() + ".attachmentFiles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.OfficialDispatch.class.getName() + ".officialDispatchHis", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.OfficialDispatch.class.getName() + ".stepProcessDocs", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.OfficialDispatchHis.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.StepProcessDoc.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.StepProcessDoc.class.getName() + ".attachmentFiles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.TransferHandle.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.TransferHandle.class.getName() + ".receiversHandles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.StatusTransferHandle.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.StatusTransferHandle.class.getName() + ".transferHandles", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.OutOrganization.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.OutOrganization.class.getName() + ".officialDispatches", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.RankInOrg.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.DelegateInfo.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.DelegateType.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.DelegateType.class.getName() + ".delegateInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.DelegateDocument.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.DelegateDocument.class.getName() + ".delegateInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.BusinessPartner.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.BusinessPartnerType.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.BusinessPartnerType.class.getName() + ".businessPartners", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Vendor.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.Vendor.class.getName() + ".priceInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.GoodService.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.GoodService.class.getName() + ".priceInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.GoodServiceType.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.GoodServiceType.class.getName() + ".goodServices", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.CurrencyUnit.class.getName(), jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.CurrencyUnit.class.getName() + ".goodServices", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.CurrencyUnit.class.getName() + ".priceInfos", jcacheConfiguration);
            createCache(cm, com.vsm.business.domain.PriceInfo.class.getName(), jcacheConfiguration);*/
            // jhipster-needle-redis-add-entry
                    // categoryGroup
            createCache(cm, "/_all/category-groups", jcacheConfiguration);
            createCache(cm, "/_all-child/{id}/category-groups", jcacheConfiguration);
            createCache(cm, "/_all_child_pageable/{id}/category-groups", jcacheConfiguration);
            createCache(cm, "/_all_has_child/category-groups", jcacheConfiguration);
            createCache(cm, "/_all_not_has_parent/category-groups", jcacheConfiguration);
                    // request
            createCache(cm, "/_all/request_group/{requestGroupId}/requests", jcacheConfiguration);

                    // attachmentFile
            createCache(cm, "/template-form/{templateFormId}/_all/attachment-files", jcacheConfiguration);
        };
    }

    private void createCache(
        javax.cache.CacheManager cm,
        String cacheName,
        javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
