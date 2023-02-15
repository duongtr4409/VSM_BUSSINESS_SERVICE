package com.vsm.business.common.Graph;

import com.azure.core.http.rest.RequestOptions;
import com.azure.identity.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.microsoft.aad.msal4j.*;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.http.GraphServiceException;
import com.microsoft.graph.logger.DefaultLogger;
import com.microsoft.graph.logger.ILogger;
import com.microsoft.graph.logger.LoggerLevel;
import com.microsoft.graph.models.*;
import com.microsoft.graph.options.HeaderOption;
import com.microsoft.graph.options.Option;
import com.microsoft.graph.options.QueryOption;
import com.microsoft.graph.requests.*;
import com.microsoft.graph.tasks.IProgressCallback;
import com.microsoft.graph.tasks.LargeFileUploadResult;
import com.microsoft.graph.tasks.LargeFileUploadTask;
import com.vsm.business.common.AppConstant;
import com.vsm.business.common.HttpClientHelper;
import com.vsm.business.common.Mail.MailQueue;
import com.vsm.business.common.Mail.MailService;
import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.domain.MailLog;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.repository.MailLogRepository;
import com.vsm.business.service.authenicate.OAuthDTO;
import com.vsm.business.service.authenicate.OAuthTokenDTO;
import com.vsm.business.service.custom.file.UploadFile365CustomService;
import com.vsm.business.service.custom.mail.bo.MailInfoDTO;
import com.vsm.business.service.dto.MailLogDTO;
import com.vsm.business.utils.CallAPIUtils;
import joptsimple.internal.Strings;
import okhttp3.Request;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class GraphService {

    private final Logger log = LoggerFactory.getLogger(GraphService.class);

    @Value("${microsoft.clientId:d435afb3-26ea-44b8-9486-ccaf133c7c56}")
    private String CLIENT_ID; // = "d435afb3-26ea-44b8-9486-ccaf133c7c56";
    @Value("${microsoft.authority:https://login.microsoftonline.com/e2c6dedd-f4aa-4599-b436-e649faf0d375/}")
    private String AUTHORITY; // = "https://login.microsoftonline.com/e2c6dedd-f4aa-4599-b436-e649faf0d375/";
    @Value("${microsoft.clientSecret:gLe8Q~ggF_gHYL9sXQBQ1aiAbTrv6yQkUiOXdbmK}")
    private String CLIENT_SECRET; // = "gLe8Q~ggF_gHYL9sXQBQ1aiAbTrv6yQkUiOXdbmK";

    @Value("${microsoft.tenantGuid:e2c6dedd-f4aa-4599-b436-e649faf0d375}")
    private String TENANT_GUID;

    @Value("${microsoft.username}")
    private String USERNAME = "eoffice.vcr@2bsystem.com.vn";

    @Value("${microsoft.password}")
    private String PASSWORD = "1q2w3e4r@@RR";


//    private final static String CLIENT_ID = "d435afb3-26ea-44b8-9486-ccaf133c7c56";
//    private final static String AUTHORITY = "https://login.microsoftonline.com/e2c6dedd-f4aa-4599-b436-e649faf0d375/";
//    private final static String CLIENT_SECRET = "gLe8Q~ggF_gHYL9sXQBQ1aiAbTrv6yQkUiOXdbmK";

    private final static Set<String> SCOPE = Collections.singleton("https://graph.microsoft.com/.default");
    private IClientCredential credential;
    private ConfidentialClientApplication cca;
    private final String PDF_FORMAT = "pdf";
    private final String pathApplication = new File(".").getCanonicalPath();

    @Value("${graph.uri.base:https://graph.microsoft.com/v1.0}")
    private String BASE_URI = "https://graph.microsoft.com/v1.0";

    @Value("${graph.uri.me:/me}")
    private String URI_ME = "/me";

    @Value("${graph.mail.delimiter:\\$}")
    private String mailDelimiter = "\\$";

    @Autowired
    private AttachmentFileRepository attachmentFileRepository;

    @Autowired
    private CallAPIUtils callAPIUtils;

    @Autowired
    private MailLogRepository mailLogRepository;

    @Autowired
    private MailService mailService;

    public GraphService() throws IOException {

    }

    @PostConstruct
    public void init() throws IOException {
        credential = ClientCredentialFactory.createFromSecret(CLIENT_SECRET);
        cca = ConfidentialClientApplication.builder(CLIENT_ID, credential).authority(AUTHORITY).build();
    }

    public IAuthenticationResult acquireToken() throws Exception {
        return acquireToken(SCOPE);
    }

    public IAuthenticationResult acquireToken(Set<String> scopes) throws Exception {
        ClientCredentialParameters parameters = ClientCredentialParameters.builder(scopes).skipCache(true).build();
        return cca.acquireToken(parameters).join();
    }

    private JSONObject callAPI(Set<String> scopes, URL url) {
        JSONObject jsObject;
        try {
            IAuthenticationResult authenticationResult = acquireToken(scopes);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + authenticationResult.accessToken());
            httpURLConnection.setRequestProperty("Accept", "application/json");
            String response = HttpClientHelper.getResponseStringFromConn(httpURLConnection);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return null;
            }
            jsObject = HttpClientHelper.processResponse(responseCode, response);
        } catch (Exception e) {
            return null;
        }
        return jsObject;
    }

    private JSONObject callAPI(Set<String> scopes, URL url, HttpMethod method) {
        JSONObject jsObject;
        try {
            IAuthenticationResult authenticationResult = acquireToken(scopes);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + authenticationResult.accessToken());
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestMethod(method.toString());
            String response = HttpClientHelper.getResponseStringFromConn(httpURLConnection);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return null;
            }
            jsObject = HttpClientHelper.processResponse(responseCode, response);
        } catch (Exception e) {
            return null;
        }
        return jsObject;
    }

    private JSONObject callAPI(URL url) throws Exception {
        return callAPI(SCOPE, url);
    }

    private JSONObject callAPI(URL url, HttpMethod method) throws Exception {
        return callAPI(SCOPE, url, method);
    }

    public JSONObject me_Get() throws Exception {
        URL url = new URL("https://graph.microsoft.com/v1.0/me");
        return callAPI(url);
    }

    public JSONObject users() throws Exception {
        URL url = new URL("https://graph.microsoft.com/v1.0/users");
        return callAPI(url);
    }

    public JSONObject groups() throws Exception {
        URL url = new URL("https://graph.microsoft.com/v1.0/groups");
        return callAPI(url);
    }

    public UserCollectionPage getAllUser() {
        graphClient = this.buildGraphClient();
        UserCollectionPage userCollectionPage = graphClient.users().buildRequest().get();
        return userCollectionPage;
    }

    /**
     * Hàm thực hiện lấy toàn bộ user (kể cả các user bị xóa)
     * @return
     */
    public UserDeltaCollectionPage getAllUserDelta() {
        graphClient = this.buildGraphClient();
        UserDeltaCollectionPage userDeltaCollectionPage = graphClient.users().delta().buildRequest().get();
        return userDeltaCollectionPage;
    }

    public User getUserWithManager(String userId) {
        graphClient = this.buildGraphClient();
        LinkedList<Option> requestOptions = new LinkedList<Option>();
        requestOptions.add(new HeaderOption("ConsistencyLevel", "eventual"));
        User user = graphClient.users(userId).buildRequest(requestOptions).expand("manager($levels=max;$select=id,displayName,officeLocation,department)").get();
        return user;
    }

//    public String uploadFile() throws Exception {
//        if(graphClient == null) graphClient = buildGraphClient();
//
//        File file = new File(pathApplication + "\\temp\\Deploy project lên Sharepoint_v1.docx");
//        InputStream fileStream = new FileInputStream(file);
//        long streamSize = file.length();
//
//        IProgressCallback callback = new IProgressCallback() {
//            @Override
//            // Called after each slice of the file is uploaded
//            public void progress(final long current, final long max) {
//                System.out.println(
//                    String.format("Uploaded %d bytes of %d total bytes", current, max)
//                );
//            }
//        };
//
//        DriveItemCreateUploadSessionParameterSet uploadParams =
//            DriveItemCreateUploadSessionParameterSet.newBuilder()
//                .withItem(new DriveItemUploadableProperties()).build();
//
//        UploadSession uploadSession = graphClient
//            .drive()
//            .root()
//            .itemWithPath(file.getName())
//            .createUploadSession(uploadParams)
//            .buildRequest()
//            .post();
//
//        LargeFileUploadTask<DriveItem> largeFileUploadTask =
//            new LargeFileUploadTask<DriveItem>
//                (uploadSession, graphClient, fileStream, streamSize, DriveItem.class);
//
//        LargeFileUploadResult<DriveItem> upload = largeFileUploadTask.upload(0, null, callback);
//        return upload.responseBody.id;
//    }


    private static GraphServiceClient<Request> graphClient = null;
    private static TokenCredentialAuthProvider authProvider = null;

    private UsernamePasswordCredential usernamePasswordCredential;
    private TokenCredentialAuthProvider tokenCredentialAuthProvider;

    public GraphServiceClient buildGraphClient() {
        if (graphClient != null) return graphClient;
        try {
            // Create the auth provider
//            ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(CLIENT_ID).tenantId(TENANT_GUID).clientSecret(CLIENT_SECRET).tokenCachePersistenceOptions(new TokenCachePersistenceOptions()).build();
//
//            authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default", ""), credential);
//
//            // Build a Graph client
//            graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).buildClient();

            usernamePasswordCredential = new UsernamePasswordCredentialBuilder().clientId(CLIENT_ID).username(USERNAME).password(PASSWORD).build();
            tokenCredentialAuthProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default", ""), usernamePasswordCredential);
            ILogger logger = new DefaultLogger();
            logger.setLoggingLevel(LoggerLevel.DEBUG);
            graphClient = GraphServiceClient.builder().authenticationProvider(tokenCredentialAuthProvider).logger(logger).buildClient();
            return graphClient;

//            String client_id = "59caccd6-5951-4de7-b9ee-e6d581756cfa";
//            String username = "dev.azurevcr@vingroup.net";
//            String password = "##Thang06@2022#";
//            String tenantId = "ed6a2939-d153-4f92-94f8-3d790d96c9f8";
//            usernamePasswordCredential = new UsernamePasswordCredentialBuilder().clientId(client_id).tenantId(tenantId).username(username).password(password).build();
//            tokenCredentialAuthProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default", ""), usernamePasswordCredential);
//            graphClient = GraphServiceClient.builder().authenticationProvider(tokenCredentialAuthProvider).buildClient();
//            return graphClient;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public GraphServiceClient buildGraphClient_bak() {
        if (graphClient != null) return graphClient;
        try {
            // Create the auth provider
//            ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(CLIENT_ID).tenantId(TENANT_GUID).clientSecret(CLIENT_SECRET).tokenCachePersistenceOptions(new TokenCachePersistenceOptions()).build();
//
//            authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default", ""), credential);
//
//            // Build a Graph client
//            graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).buildClient();

            usernamePasswordCredential = new UsernamePasswordCredentialBuilder().clientId(CLIENT_ID).username(USERNAME).password(PASSWORD).build();
            tokenCredentialAuthProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default", ""), usernamePasswordCredential);
            graphClient = GraphServiceClient.builder().authenticationProvider(tokenCredentialAuthProvider).buildClient();
            return graphClient;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public static String getUserAccessToken() throws Exception {
        String accesstoken;

        try {
            URL meUrl = new URL("https://graph.microsoft.com/v1.0/me");
            accesstoken = authProvider.getAuthorizationTokenAsync(meUrl).get();
        } catch (Exception ex) {
            System.out.println(ex.toString());
            throw ex;
        }
        return accesstoken;
    }

    public UserCollectionPage getListUser() {
        graphClient = this.buildGraphClient();
        UserCollectionPage userCollectionPage = graphClient.users().buildRequest().get();
        return userCollectionPage;
    }

    public File getAttachment(String id) throws IOException {
        graphClient = this.buildGraphClient();
        InputStream inputStream1 = graphClient.customRequest("/drive/items/" + id + "/content", InputStream.class).buildRequest().get();
//        InputStream inputStream = graphClient.drive().items(id).content().buildRequest().get();
        InputStream inputStream = graphClient.me().drive().items(id).content().buildRequest().get();

        File result = new File(pathApplication + "\\temp\\duowngtora.docx");
        FileUtils.copyInputStreamToFile(inputStream1, result);
        return result;
    }

    public void getFilePDF(HttpServletResponse response, AttachmentFile attachmentFile, String id) throws IOException {
        LinkedList<Option> requestOptions = new LinkedList<Option>();
        requestOptions.add(new QueryOption("format", PDF_FORMAT));

        graphClient = this.buildGraphClient();
//        InputStream inputStream = graphClient.drive().items(id).content().buildRequest(requestOptions).get();
        InputStream inputStream = graphClient.me().drive().items(id).content().buildRequest(requestOptions).get();

        response.setContentType("application/octet-stream");

        String[] splitFileName = attachmentFile.getFileName().split("\\.");
        String fileDownLoadName = String.join(".", Arrays.copyOfRange(splitFileName, 0, splitFileName.length - 1)) + ".pdf";
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileDownLoadName + "\"");
        response.getOutputStream().write(IOUtils.toByteArray(inputStream));
    }

    public void getFilePDF_v4(HttpServletResponse response, String id) throws IOException, ExecutionException, InterruptedException {
        LinkedList<Option> requestOptions = new LinkedList<Option>();
        requestOptions.add(new QueryOption("format", PDF_FORMAT));

        graphClient = this.buildGraphClient();
//        InputStream inputStream1 = graphClient.drive().items(id).content().buildRequest(requestOptions).get();
        InputStream inputStream1 = graphClient.me().drive().items(id).content().buildRequest(requestOptions).get();

//        InputStream inputStream = graphClient.drive().root().itemWithPath("TKCT_TAU_VAO_ROI_CANG_V1.0.docx").content().buildRequest(requestOptions).getAsync().get();
        InputStream inputStream = graphClient.me().drive().root().itemWithPath("TKCT_TAU_VAO_ROI_CANG_V1.0.docx").content().buildRequest(requestOptions).getAsync().get();

        response.setContentType("application/octet-stream");
        String fileDownLoadName = "TKCT_TAU_VAO_ROI_CANG_V1.0.pdf";
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileDownLoadName + "\"");
        response.getOutputStream().write(IOUtils.toByteArray(inputStream));
    }

    /**
     * hàm tạo folder
     *
     * @param folderName   : tên folder cần tạo
     * @param parentItemId : id của folder cha
     * @return
     */
    public DriveItem createFolder(String folderName, String parentItemId) {
        //folderName = folderName.replaceAll("[^A-Za-z0-9]", "_");
        folderName = folderName.replaceAll("[\\/\\*<>?:|#%]", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
        folderName = folderName.replace("\\", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
        if (parentItemId == null || parentItemId.isEmpty())
            parentItemId = UploadFile365CustomService.ROOT_FOLDER_ITEM_ID;
        graphClient = this.buildGraphClient();
        DriveItem driveItem = new DriveItem();
        driveItem.name = folderName;
        Folder folder = new Folder();
        driveItem.folder = folder;
        driveItem.additionalDataManager().put("@microsoft.graph.conflictBehavior", new JsonPrimitive("rename"));

        try {
            DriveItem result = null;
            if (parentItemId == null || "".equals(parentItemId.trim())) {
//                result = graphClient.drive().root().children().buildRequest().post(driveItem);
                result = graphClient.me().drive().root().children().buildRequest().post(driveItem);
            } else {
                result = graphClient.me().drive().items(parentItemId).children().buildRequest().post(driveItem);
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * hàm thực hiện thay đổi file trên office
     *
     * @param itemId      : itemId của
     * @param fileContent : nội dung của file sau khi chỉnh sửa
     * @return :
     * @throws IOException
     */
    public DriveItem updateFile(String itemId, byte[] fileContent) throws IOException {
        try {
            graphClient = this.buildGraphClient();
            DriveItem driveItem = graphClient
                .me()
                .drive()
                .items(itemId)
                .content()
                .buildRequest()
                .put(fileContent);
            return driveItem;
        }catch (GraphServiceException e){
            log.error("{}", e.getMessage());
            log.debug("{}", e);
//            if(e.getResponseCode() == 413){
                DriveItem driveItem = graphClient.me().drive().items(itemId).buildRequest().get();
                String name = driveItem.name;
                String parentId = driveItem.parentReference.id;
                return this.updateLargeFile(name, parentId, fileContent).responseBody;
//            }
        }
//        return null;
    }

    public LargeFileUploadResult<DriveItem> updateLargeFile(String fileName, String parentItemId, byte[] fileContent) throws IOException {
        if (parentItemId == null || parentItemId.isEmpty())
            parentItemId = UploadFile365CustomService.ROOT_FOLDER_ITEM_ID;
        graphClient = this.buildGraphClient();
        InputStream fileStream = new ByteArrayInputStream(fileContent);
        long streamSize = (long) fileStream.available();

        String newFileName = fileName.replaceAll("[\\/\\*<>?:|#%]", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
        newFileName = newFileName.replace("\\", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);

        IProgressCallback callback = new IProgressCallback() {
            @Override
            // Called after each slice of the file is uploaded
            public void progress(final long current, final long max) {
                System.out.println(String.format("Uploaded %d bytes of %d total bytes", current, max));
            }
        };

        DriveItemUploadableProperties driveItemUploadableProperties = new DriveItemUploadableProperties();
        driveItemUploadableProperties.additionalDataManager().put("@microsoft.graph.conflictBehavior", new JsonPrimitive("replace"));
        DriveItemCreateUploadSessionParameterSet uploadParams = DriveItemCreateUploadSessionParameterSet.newBuilder().withItem(driveItemUploadableProperties).build();
        UploadSession uploadSession;
        if (parentItemId == null || "".equals(parentItemId.trim())) {
//            uploadSession = graphClient.drive().root().itemWithPath(newFileName).createUploadSession(uploadParams).buildRequest().post();
            uploadSession = graphClient.me().drive().root().itemWithPath(newFileName).createUploadSession(uploadParams).buildRequest().post();
        } else {
//            uploadSession = graphClient.drive().items(parentItemId).itemWithPath(newFileName).createUploadSession(uploadParams).buildRequest().post();
            uploadSession = graphClient.me().drive().items(parentItemId).itemWithPath(newFileName).createUploadSession(uploadParams).buildRequest().post();
        }

        LargeFileUploadTask<DriveItem> largeFileUploadTask = new LargeFileUploadTask<DriveItem>(uploadSession, graphClient, fileStream, streamSize, DriveItem.class);

        LargeFileUploadResult<DriveItem> upload = largeFileUploadTask.upload(0, null, callback);

        String itemId = upload.responseBody.id;

        // gắn quyền
        Permission permission = this.grantAccess(itemId);
        upload.responseBody.webUrl = permission.link.webUrl;

        // close Stream
        try {
            fileStream.close();
        }catch (Exception e){log.error("{}", e);}

//        return itemId;
        return upload;
    }

    /**
     * Hàm thực hiện tạo file
     *
     * @param fileName     : tên file
     * @param parentItemId : id của folder chứa file
     * @param fileContent  : nội dung file (dạng byte[])
     * @return : itemId của file đã tọa được
     * @throws IOException
     */
    public LargeFileUploadResult<DriveItem> createFile(String fileName, String parentItemId, byte[] fileContent) throws IOException {
        if (parentItemId == null || parentItemId.isEmpty())
            parentItemId = UploadFile365CustomService.ROOT_FOLDER_ITEM_ID;
        graphClient = this.buildGraphClient();
        InputStream fileStream = new ByteArrayInputStream(fileContent);
        long streamSize = (long) fileStream.available();

        String newFileName = fileName.replaceAll("[\\/\\*<>?:|#%]", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
        newFileName = newFileName.replace("\\", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);

        IProgressCallback callback = new IProgressCallback() {
            @Override
            // Called after each slice of the file is uploaded
            public void progress(final long current, final long max) {
                System.out.println(String.format("Uploaded %d bytes of %d total bytes", current, max));
            }
        };

        DriveItemUploadableProperties driveItemUploadableProperties = new DriveItemUploadableProperties();
        DriveItemCreateUploadSessionParameterSet uploadParams = DriveItemCreateUploadSessionParameterSet.newBuilder().withItem(driveItemUploadableProperties).build();
        UploadSession uploadSession;
        if (parentItemId == null || "".equals(parentItemId.trim())) {
//            uploadSession = graphClient.drive().root().itemWithPath(newFileName).createUploadSession(uploadParams).buildRequest().post();
            uploadSession = graphClient.me().drive().root().itemWithPath(newFileName).createUploadSession(uploadParams).buildRequest().post();
        } else {
//            uploadSession = graphClient.drive().items(parentItemId).itemWithPath(newFileName).createUploadSession(uploadParams).buildRequest().post();
            uploadSession = graphClient.me().drive().items(parentItemId).itemWithPath(newFileName).createUploadSession(uploadParams).buildRequest().post();
        }

        LargeFileUploadTask<DriveItem> largeFileUploadTask = new LargeFileUploadTask<DriveItem>(uploadSession, graphClient, fileStream, streamSize, DriveItem.class);

        LargeFileUploadResult<DriveItem> upload = largeFileUploadTask.upload(0, null, callback);

        String itemId = upload.responseBody.id;

        // gắn quyền
        Permission permission = this.grantAccess(itemId);
        upload.responseBody.webUrl = permission.link.webUrl;

        // close Stream
        try {
            fileStream.close();
        }catch (Exception e){log.error("{}", e);}

//        return itemId;
        return upload;
    }

    /**
     * Hàm thực hiện xóa file hoặc là Folder
     *
     * @param itemId: id của Item cần xóa (có thể là cả folder và file)
     * @return
     */
    public boolean removeItem(String itemId) {
        graphClient = this.buildGraphClient();
//        DriveItem result = graphClient.drive().items(itemId).buildRequest().delete();
        DriveItem result = graphClient.me().drive().items(itemId).buildRequest().delete();
        return true;
    }

    /**
     * Hàm thực hiện copy file hoặc folder
     *
     * @param itemIdSource : itemId của file(folder) cần copy
     * @param itemTarget   : itemId của Folder cần copy đến
     * @param name         : tên của file(folder) copy được
     */
    public DriveItem copyFile(String itemIdSource, String itemTarget, String name) throws IOException {
        graphClient = this.buildGraphClient();
        ItemReference parentReference = new ItemReference();

        String newFileName = name.replaceAll("[\\/\\*<>?:|#%]", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
        newFileName = newFileName.replace("\\", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);

//        Drive drive = graphClient.drive().buildRequest().get();
        Drive drive = graphClient.me().drive().buildRequest().get();
        parentReference.id = itemTarget;
        parentReference.driveId = drive.id;
//        DriveItem result = graphClient.drive().items(itemIdSource).copy(DriveItemCopyParameterSet.newBuilder().withName(name).withParentReference(parentReference).build()).buildRequest().post();
        DriveItem result = graphClient.me().drive().items(itemIdSource).copy(DriveItemCopyParameterSet.newBuilder().withName(newFileName).withParentReference(parentReference).build()).buildRequest().post();

        //DriveItemCollectionPage driveItemCollectionPage = graphClient.drive().items(itemTarget).children().buildRequest().get();
        JsonElement graphResponseHeaders = result.additionalDataManager().get("graphResponseHeaders");
        JsonElement location = graphResponseHeaders.getAsJsonObject().get("location");
        String linkResultCopy = location.getAsJsonArray().get(0).getAsString();
        DriveItem driveItem = getResultCopyV2(linkResultCopy);

        // gắn quyền
        Permission permission = this.grantAccess(driveItem.id);
        driveItem.webUrl = permission.link.webUrl;

        return driveItem;
    }

    /**
     * Hàm thực hiện copy file hoặc folder chưa lấy kết quả luôn (cần call lại để lấy kết quả)
     *
     * @param itemIdSource : itemId của file(folder) cần copy
     * @param itemTarget   : itemId của Folder cần copy đến
     * @param name         : tên của file(folder) copy được
     */
    public DriveItem copyFileWaitResult(String itemIdSource, String itemTarget, String name) throws IOException {
        graphClient = this.buildGraphClient();
        ItemReference parentReference = new ItemReference();

        String newFileName = name.replaceAll("[\\/\\*<>?:|#%]", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
        newFileName = newFileName.replace("\\", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);

//        Drive drive = graphClient.drive().buildRequest().get();
        Drive drive = graphClient.me().drive().buildRequest().get();
        parentReference.id = itemTarget;
        parentReference.driveId = drive.id;
//        DriveItem result = graphClient.drive().items(itemIdSource).copy(DriveItemCopyParameterSet.newBuilder().withName(name).withParentReference(parentReference).build()).buildRequest().post();
        DriveItem result = graphClient.me().drive().items(itemIdSource).copy(DriveItemCopyParameterSet.newBuilder().withName(newFileName).withParentReference(parentReference).build()).buildRequest().post();

        return result;
    }

    /**
     * Hàm thực hiện lấy kết quả coppy file
     *
     * @param url
     * @return
     * @throws IOException
     */
    @Value("${graph-api.time-wait-copy:3000}")
    private String TIME_WAIT_COPY;

    public DriveItem getResultCopy(String url) throws IOException {
        try {
            Thread.sleep(Long.valueOf(TIME_WAIT_COPY));
        } catch (InterruptedException e) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
            }
        }
        ;
        URL urlResult = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlResult.openConnection();
        String response = HttpClientHelper.getResponseStringFromConn(httpURLConnection);
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            //return getResultCopyV2(url);
            return null;
        }
        JSONObject jsObject = HttpClientHelper.processResponse(responseCode, response);
        JSONObject responseMsg = jsObject.getJSONObject("responseMsg");
        String resourceId = responseMsg.get("resourceId").toString();
//        DriveItem driveItem = graphClient.drive().items(resourceId).buildRequest().get();
        DriveItem driveItem = graphClient.me().drive().items(resourceId).buildRequest().get();
        return driveItem;
    }

    public DriveItem getResultCopyV2(String url) throws IOException {
        try {
            Thread.sleep(Long.valueOf(TIME_WAIT_COPY));
        } catch (InterruptedException e) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
            }
        }
        ;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseTemp = restTemplate.getForEntity(url, String.class);
        String resourceId = null;
        try {
            JSONObject jsonResponse = new JSONObject(responseTemp.getBody());
            resourceId = jsonResponse.get("resourceId").toString();
        } catch (Exception e) {
            log.error("{}", e);
            return getResultCopy(url);
            //return null;
        }

//        DriveItem driveItem = graphClient.drive().items(resourceId).buildRequest().get();
        DriveItem driveItem = graphClient.me().drive().items(resourceId).buildRequest().get();
        return driveItem;
    }

    public DriveItem getResultCopyV3(String url) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseTemp = restTemplate.getForEntity(url, String.class);
        String resourceId = null;
        try {
            JSONObject jsonResponse = new JSONObject(responseTemp.getBody());
            resourceId = jsonResponse.get("resourceId").toString();
        } catch (Exception e) {
            log.error("{}", e);
            return getResultCopyV2(url);
            //return null;
        }
//        DriveItem driveItem = graphClient.drive().items(resourceId).buildRequest().get();
        DriveItem driveItem = graphClient.me().drive().items(resourceId).buildRequest().get();
        return driveItem;
    }

    public DriveItem moveItem(String itemIdSource, String itemTarget, String name) {
        graphClient = this.buildGraphClient();
        DriveItem driveItem = new DriveItem();
        ItemReference parentReference = new ItemReference();
        parentReference.id = itemTarget;
        driveItem.parentReference = parentReference;

//        DriveItem result = graphClient.drive().items(itemIdSource).buildRequest().patch(driveItem);
        DriveItem result = graphClient.me().drive().items(itemIdSource).buildRequest().patch(driveItem);

        return result;
    }

    @Autowired
    public com.vsm.business.utils.FileUtils fileUtils;

    /**
     * hàm lấy dữ liệu file
     *
     * @param itemId : id của file cần lấy dữ liệu
     * @return : dữ liệu file dạng InputStream
     */
    public InputStream getFile(String itemId, String format) throws Exception {
        graphClient = this.buildGraphClient();
        InputStream inputStream;
        if (format == null || "".equals(format.trim())) {
//            inputStream = graphClient.drive().items(itemId).content().buildRequest().get();
            inputStream = graphClient.me().drive().items(itemId).content().buildRequest().get();
        } else {
            graphClient.getHttpProvider();
            LinkedList<Option> requestOptions = new LinkedList<Option>();
            requestOptions.add(new QueryOption("format", format));
//            inputStream = graphClient.drive().items(itemId).content().buildRequest(requestOptions).get();
            try {
                inputStream = graphClient.me().drive().items(itemId).content().buildRequest(requestOptions).get();
            } catch (Exception e) {
                log.error("{}", e);

                // TH nếu graphAPI convert sang PDF lỗi ->
                inputStream = graphClient.me().drive().items(itemId).content().buildRequest().get();
//                InputStream resultPDFFile = convertDocToPDFDOCX4J(inputStream);
//                return resultPDFFile;
                InputStream resultPDFFile = fileUtils.convertDocToPDFDOCX4J(inputStream);
                return resultPDFFile;
            }
        }
        return inputStream;
    }


    @Value("${system.folder.TEMP_FOLDER:./temp/}")
    public String tempFolder;
    @Value("${system.plutext.config.host:http://localhost:9016/v1/00000000-0000-0000-0000-000000000000/convert}")
    public String PLUTEXT_CONFIG_HOST;
    private static final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();

    private InputStream convertDocToPDFDOCX4J(InputStream inputStreamSource) throws IOException, Docx4JException {
        String tempFolderPath = (tempFolder + String.valueOf(System.currentTimeMillis()) + PATH_SEPARATOR).replace("//", PATH_SEPARATOR);
        File folderTemp = new File(tempFolderPath);
        if (!folderTemp.exists()) folderTemp.mkdir();
        String timeMillisTemp = String.valueOf(System.currentTimeMillis());
        String fileTempPath = folderTemp.getAbsolutePath() + PATH_SEPARATOR + "DuowngTora_" + timeMillisTemp + ".docx";
        String fileResultTempPath = folderTemp.getAbsolutePath() + PATH_SEPARATOR + "DuowngTora_" + timeMillisTemp + ".pdf";
        File sourceFileTemp = new File(fileTempPath);
        File resultFileTemp = new File(fileResultTempPath);

        FileOutputStream fileOutputStream = new FileOutputStream(sourceFileTemp);

        byte[] bytes = org.apache.commons.compress.utils.IOUtils.toByteArray(inputStreamSource);
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();


        FileOutputStream fileResultOutputStream = new FileOutputStream(resultFileTemp);
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(sourceFileTemp);
        org.docx4j.Docx4jProperties.setProperty("com.plutext.converter.URL", PLUTEXT_CONFIG_HOST);
        org.docx4j.Docx4J.toPDF(wordMLPackage, fileResultOutputStream);
        fileResultOutputStream.flush();
        fileResultOutputStream.close();


        Path path = Paths.get(fileResultTempPath);
        byte[] data = Files.readAllBytes(path);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);

        try {
            sourceFileTemp.delete();
            resultFileTemp.delete();
            folderTemp.delete();
        } catch (Exception e) {
            log.error("{}", e);
        }

        return byteArrayInputStream;
    }

    /**
     * Hàm thực hiện xoá item (file hoặc là folder)
     *
     * @param id : itemId của Item cần xoá
     */
    public void deleteItem(String id) {
        graphClient = this.buildGraphClient();
//        DriveItem resultDelete = graphClient.drive().items(id).buildRequest().delete();
        DriveItem resultDelete = graphClient.me().drive().items(id).buildRequest().delete();
        log.info("resultDelete: {}", resultDelete);
    }

    /**
     * Hàm thực hiện xóa file khi file đang bị mở (Dùng trong TH xóa biểu mẫu)
     *
     * @param id: itemId của Item cần xóa
     */
    public boolean deleteItemLocked(String id){
        graphClient = this.buildGraphClient();
        try {
//        DriveItem resultDelete = graphClient.drive().items(id).buildRequest().delete();
            DriveItem resultDelete = graphClient.me().drive().items(id).buildRequest().delete();
            log.info("resultDelete: {}", resultDelete);
        }catch (GraphServiceException graphServiceException){
            log.error("{}", graphServiceException);
            // Th bị nếu dính lỗi 423: locked là file đang bị mở ở đâu đó -> checkout file rồi xóa lại
            if(423 == graphServiceException.getResponseCode()){
                try {
                    graphClient.me().drive().items(id).checkout().buildRequest().post();
                    DriveItem resultDelete2 = graphClient.me().drive().items(id).buildRequest().delete();
                    log.info("resultDelete: {}", resultDelete2);
                }catch (Exception e){
                    log.error("{}", e);
                    return false;
                }
            }
        }catch (Exception e){
            log.error("{}", e);
            return false;
        }
        return true;
    }

    /**
     * Hàm thực hiện đổi tên item (file hoặc là folder)
     *
     * @param itemId   : itemId của item cần đổi tên
     * @param fileName : tên muốn đổi
     * @return
     */
    public DriveItem reNameItem(String itemId, String fileName) {
        graphClient = this.buildGraphClient();

        String newFileName = fileName.replaceAll("[\\/\\*<>?:|#%]", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
        newFileName = newFileName.replace("\\", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);

        DriveItem driveItem = new DriveItem();
        driveItem.name = newFileName;
//        DriveItem updateResult = graphClient.drive().items(itemId).buildRequest().patch(driveItem);
        DriveItem updateResult = graphClient.me().drive().items(itemId).buildRequest().patch(driveItem);
        return updateResult;
    }

    /**
     * Hàm thực hiện lấy đường dẫn xem preview file
     *
     * @param itemId : itemId của file cần xem
     * @return : trả về đường dẫn để xem preview file
     */
    public ItemPreviewInfo previewFile(String itemId) {
        graphClient = this.buildGraphClient();
        DriveItemPreviewParameterSet driveItemPreviewParameterSet = new DriveItemPreviewParameterSet();
//        ItemPreviewInfo result = graphClient.drive().items(itemId).preview(driveItemPreviewParameterSet).buildRequest().post();
        ItemPreviewInfo result = graphClient.me().drive().items(itemId).preview(driveItemPreviewParameterSet).buildRequest().post();
        return result;
    }

    /**
     * Hàm thực hiện gán quyền
     *
     * @Param itemId: itemId của
     */
    public Permission grantAccess(String itemId) throws UnsupportedEncodingException {
        graphClient = this.buildGraphClient();

        String type = "edit"; //"view";   // view

        String scope = "organization";

        Permission permission = graphClient.me().drive().items(itemId)
            .createLink(DriveItemCreateLinkParameterSet
                .newBuilder()
                .withType(type)
                .withScope(scope)
                .withExpirationDateTime(null)
                .withMessage(null)
                .withRetainInheritedPermissions(null)
                .build())
            .buildRequest()
            .post();
        return permission;
    }

    /**
     * Hàm thực hiện shared link cho người dùng
     * @param listUser  : danh sách người dùng cần chia sẻ (email)
     * @param sharedLink: link cần chia sẻ
     */
    public PermissionGrantCollectionPage grantAccessToUser(List<String> listUser, String sharedLink, String item) throws UnsupportedEncodingException {
        graphClient = this.buildGraphClient();

        if(listUser == null || listUser.isEmpty()){
            Permission permission = this.grantAccess(item);
            return new PermissionGrantCollectionPage(Arrays.asList(permission), null);
        }

        String base64Value = Base64.getEncoder().encodeToString(sharedLink.getBytes(StandardCharsets.UTF_8));
        String encodeUrl = "u!" + StringUtils.stripEnd(base64Value, "=").replace("/", "_").replace("+", "-");

        try {
            LinkedList<String> rolesList = new LinkedList<String>();
            rolesList.add("read");
            rolesList.add("write");

            LinkedList<DriveRecipient> recipientList = new LinkedList<>();
            listUser.forEach(ele -> {
                DriveRecipient recipient = new DriveRecipient();
                recipient.email = ele;
                recipientList.add(recipient);
            });

            PermissionGrantCollectionPage permissionGrantCollectionPage = graphClient.shares(encodeUrl)
                .permission()
                .grant(PermissionGrantParameterSet.newBuilder()
                    .withRoles(rolesList)
                    .withRecipients(recipientList)
                    .build())
                .buildRequest()
                .post();

            return permissionGrantCollectionPage;
        }catch (Exception e){           // nếu không phân quyền được -> phân quyền cho toàn bộ organization
            log.error("{}", e);
            Permission permission = this.grantAccess(item);
            return new PermissionGrantCollectionPage(Arrays.asList(permission), null);
        }
    }



    // Message (Mail) \\
    @Value("${microsoft.mail.email-address-send:vanminh.vu@10dd23.onmicrosoft.com}")
    private String EMAIL_ADDRESS_SEND;          //  địa chỉ mail dùng để gửi mail
    private String USER_ID = "";                // id của người dùng (dùng để gửi mail)

//    public void getIDUserSendMail() {
//        graphClient = this.buildGraphClient();
//        UserCollectionPage userCollectionPage = graphClient.users().buildRequest().get();
//        User user = userCollectionPage.getCurrentPage().stream().filter(ele -> EMAIL_ADDRESS_SEND.equals(ele.mail)).findFirst().get();
//        USER_ID = user.id;
//    }

    /**
     * Hàm thực hiện chức năng gửi mail
     *
     * @param mailInfoDTO: Thông tin trên mail cần gửi
     * @return
     */
    public boolean sendMessage(List<File> files, MailInfoDTO mailInfoDTO) {
        graphClient = this.buildGraphClient();
//        Message message = new Message();
//        message.subject = mailInfoDTO.getSubject();
//        message.importance = Importance.LOW;
//        ItemBody body = new ItemBody();
//        body.contentType = BodyType.HTML;
//        body.content = this.bindData(mailInfoDTO.getContent(), mailInfoDTO.getProps());
//        message.body = body;
//
//        LinkedList<Recipient> toRecipientsList = new LinkedList<Recipient>();
//        mailInfoDTO.getEmailAddressTo().forEach(ele -> {
//            Recipient toRecipients = new Recipient();
//            EmailAddress emailAddress = new EmailAddress();
//            emailAddress.address = ele;
//            toRecipients.emailAddress = emailAddress;
//            toRecipientsList.add(toRecipients);
//        });
//
//        LinkedList<Recipient> ccRecipientsList = new LinkedList<Recipient>();
//        if(mailInfoDTO.getEmailAddressCC() != null){
//            mailInfoDTO.getEmailAddressCC().forEach(ele -> {
//                Recipient ccRecipients = new Recipient();
//                EmailAddress emailAddress = new EmailAddress();
//                emailAddress.address = ele;
//                ccRecipients.emailAddress = emailAddress;
//                ccRecipientsList.add(ccRecipients);
//            });
//        }
//
//        LinkedList<Recipient> bccRecipientsList = new LinkedList<Recipient>();
//        if(mailInfoDTO.getEmailAddressBCC() != null){
//            mailInfoDTO.getEmailAddressCC().forEach(ele -> {
//                Recipient bccRecipients = new Recipient();
//                EmailAddress emailAddress = new EmailAddress();
//                emailAddress.address = ele;
//                bccRecipients.emailAddress = emailAddress;
//                ccRecipientsList.add(bccRecipients);
//            });
//        }
//
//        message.toRecipients = toRecipientsList;
//        message.ccRecipients = ccRecipientsList;
//        message.bccRecipients = bccRecipientsList;
//
//
//        Message messageIn365 = graphClient.users(USER_ID).messages()
//            .buildRequest()
//            .post(message);
//
//        // attachmentFile
////        if(files != null){
//////            files.forEach(ele -> {
////            for(File ele : files) {
////                FileAttachment attachment = new FileAttachment();
//////                try {
////                    attachment.name = ele.getName();
////                    attachment.contentBytes =  Base64.getDecoder().decode("DuowngTora");
//////                    attachment.contentBytes = FileUtils.readFileToByteArray(ele);
////
////                    Attachment attachment1Result = graphClient.users(USER_ID).messages(messageIn365.id).attachments()
////                        .buildRequest()
////                        .post(attachment);
//////                } catch (IOException e) {
//////                    throw new RuntimeException(e);
//////                }
////            }
//////            });
////        }
//
//
//        graphClient.users(USER_ID).messages(messageIn365.id).send().buildRequest().post();
        Message message = new Message();
        message.subject = "Meet for lunch?";
        ItemBody body = new ItemBody();
        body.contentType = BodyType.TEXT;
        body.content = "The new cafeteria is open.";
        message.body = body;
        LinkedList<Recipient> toRecipientsList = new LinkedList<Recipient>();
        Recipient toRecipients = new Recipient();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.address = "vanduong.nguyen@10dd23.onmicrosoft.com";
        toRecipients.emailAddress = emailAddress;
        toRecipientsList.add(toRecipients);
        message.toRecipients = toRecipientsList;
        LinkedList<Recipient> ccRecipientsList = new LinkedList<Recipient>();
        Recipient ccRecipients = new Recipient();
        EmailAddress emailAddress1 = new EmailAddress();
        emailAddress1.address = "vanduong.nguyen@10dd23.onmicrosoft.com";
        ccRecipients.emailAddress = emailAddress1;
        ccRecipientsList.add(ccRecipients);
        message.ccRecipients = ccRecipientsList;

        boolean saveToSentItems = false;

        graphClient.users(USER_ID).sendMail(UserSendMailParameterSet.newBuilder().withMessage(message).withSaveToSentItems(saveToSentItems).build()).buildRequest().post();

        return true;
    }

    @Autowired
    private JavaMailSender mailSender;
    private final String ENCODING = "utf-8";

    @Value("${mailServer.email:eoffice.vcr@2bsystem.com.vn}")
    private String MAIL_FROM;

    public boolean sendMail(List<File> files, MailInfoDTO mailInfoDTO) {
        try {
            Instant now = Instant.now();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, ENCODING);
            MailLog mailLog = new MailLog();
            MailQueue mailQueue = new MailQueue();
            helper.setText(mailInfoDTO.getContent(), true);
            mailLog.setMailContent(mailInfoDTO.getContent());
            helper.setSubject(mailInfoDTO.getSubject());
            mailLog.setMailTitle(mailInfoDTO.getSubject());
            helper.setFrom(this.MAIL_FROM);
            helper.setReplyTo(this.MAIL_FROM);
            String[] emailTo = mailInfoDTO.getEmailAddressTo().stream().filter( e-> {
                if(org.elasticsearch.common.Strings.isNullOrEmpty(e)){
                    return false;
                }
                try {
                    InternetAddress internetAddress = new InternetAddress(e);
                    internetAddress.validate();
                    return true;
                }catch (AddressException exception){
                    log.error("sendMail: error={}", exception);
                    return false;
                }
            }).toArray(String[]::new);
            helper.setTo(emailTo);
            mailLog.setReceiveEmail(String.join(mailDelimiter, emailTo));
            String[] emailCC = mailInfoDTO.getEmailAddressCC().stream().filter(e->{
                if(org.elasticsearch.common.Strings.isNullOrEmpty(e)){
                    return false;
                }
                try {
                    InternetAddress internetAddress = new InternetAddress(e);
                    internetAddress.validate();
                    return true;
                }catch (AddressException exception){
                    log.error("sendMail: error={}", exception);
                    return false;
                }
            }).toArray(String[]::new);
            helper.setCc(emailCC);
            mailLog.setCcEmail(String.join(mailDelimiter, emailCC));
            mailLog.setCreatedDate(now);
            mailLog.setIsSucess(false);
            mailLog.setSendCount(0L);
            mailLog.setResult("");
            mailLog = mailLogRepository.save(mailLog);
//            mailSender.send(mimeMessage);
            mailQueue.setMailLog(mailLog);
            mailQueue.setMimeMessage(mimeMessage);
            mailService.getMailQueues().offer(mailQueue);
        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            log.error("{}", e);
        }
        return true;
    }

    public boolean sendMail(List<String> receiver, String subject, String content){
        return sendMail(receiver, subject, content, new ArrayList<>());
    }

    public boolean sendMail(List<String> receiver, String subject, String content, List<String> cc){
        try {
            Instant now = Instant.now();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, ENCODING);
            MailLog mailLog = new MailLog();
            MailQueue mailQueue = new MailQueue();
            helper.setText(content, true);
            mailLog.setMailContent(content);
            helper.setSubject(subject);
            mailLog.setMailTitle(subject);
            helper.setFrom(this.MAIL_FROM);
            helper.setReplyTo(this.MAIL_FROM);
            String[] emailTo = receiver.stream().filter( e-> {
                if(org.elasticsearch.common.Strings.isNullOrEmpty(e)){
                    return false;
                }
                try {
                    InternetAddress internetAddress = new InternetAddress(e);
                    internetAddress.validate();
                    return true;
                }catch (AddressException exception){
                    log.error("sendMail: error={}", exception);
                    return false;
                }
            }).toArray(String[]::new);
            helper.setTo(emailTo);
            mailLog.setReceiveEmail(String.join(mailDelimiter, emailTo));
            String[] emailCC = cc.stream().filter(e->{
                if(org.elasticsearch.common.Strings.isNullOrEmpty(e)){
                    return false;
                }
                try {
                    InternetAddress internetAddress = new InternetAddress(e);
                    internetAddress.validate();
                    return true;
                }catch (AddressException exception){
                    log.error("sendMail: error={}", exception);
                    return false;
                }
            }).toArray(String[]::new);
            helper.setCc(emailCC);
            mailLog.setCcEmail(String.join(mailDelimiter, emailCC));
            mailLog.setCreatedDate(now);
            mailLog.setIsSucess(false);
            mailLog.setSendCount(0L);
            mailLog.setResult("");
            mailLog = mailLogRepository.save(mailLog);
//            mailSender.send(mimeMessage);
            mailQueue.setMailLog(mailLog);
            mailQueue.setMimeMessage(mimeMessage);
            mailService.getMailQueues().offer(mailQueue);
            return true;
        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            log.error("{}", e);
        }
        return false;
    }

    public boolean sendMail_bak(List<File> files, MailInfoDTO mailInfoDTO) {
        graphClient = this.buildGraphClient();
        Message message = new Message();
        message.subject = mailInfoDTO.getSubject();
        ItemBody body = new ItemBody();
        body.contentType = BodyType.HTML;
        body.content = this.bindData(mailInfoDTO.getContent(), mailInfoDTO.getProps());
        message.body = body;
        LinkedList<Recipient> toRecipientsList = new LinkedList<Recipient>();
        mailInfoDTO.getEmailAddressTo().forEach(ele -> {
            Recipient toRecipients = new Recipient();
            EmailAddress emailAddress = new EmailAddress();
            emailAddress.address = ele;
            toRecipients.emailAddress = emailAddress;
            toRecipientsList.add(toRecipients);
        });
        message.toRecipients = toRecipientsList;

        if (mailInfoDTO.getEmailAddressCC() != null && !mailInfoDTO.getEmailAddressCC().isEmpty()) {
            LinkedList<Recipient> ccRecipientsList = new LinkedList<Recipient>();
            mailInfoDTO.getEmailAddressCC().forEach(ele -> {
                Recipient ccRecipients = new Recipient();
                EmailAddress emailAddress = new EmailAddress();
                emailAddress.address = ele;
                ccRecipients.emailAddress = emailAddress;
                ccRecipientsList.add(ccRecipients);
            });
            message.ccRecipients = ccRecipientsList;
        }

        if (mailInfoDTO.getEmailAddressBCC() != null && !mailInfoDTO.getEmailAddressBCC().isEmpty()) {
            LinkedList<Recipient> bccRecipientsList = new LinkedList<Recipient>();
            mailInfoDTO.getEmailAddressBCC().forEach(ele -> {
                Recipient bccRecipients = new Recipient();
                EmailAddress emailAddress = new EmailAddress();
                emailAddress.address = ele;
                bccRecipients.emailAddress = emailAddress;
                bccRecipientsList.add(bccRecipients);
            });
            message.bccRecipients = bccRecipientsList;
        }

        LinkedList<Attachment> attachmentsList = new LinkedList<Attachment>();
        if (files != null && !files.isEmpty()) {
//            files.forEach(ele -> {
            for (File ele : files) {
                FileAttachment attachments = new FileAttachment();
                attachments.name = ele.getName();
                try {
                    attachments.contentType = Files.probeContentType(ele.toPath());
                    byte[] bytes = null;
                    bytes = Files.readAllBytes(ele.toPath());
                    attachments.contentBytes = bytes;
                    attachments.oDataType = "#microsoft.graph.fileAttachment";
                } catch (IOException e) {
                    //throw new RuntimeException(e);
                    log.error("{}", e);
                }
                attachmentsList.add(attachments);
            }
//            });
            AttachmentCollectionResponse attachmentCollectionResponse = new AttachmentCollectionResponse();
            attachmentCollectionResponse.value = attachmentsList;
            AttachmentCollectionPage attachmentCollectionPage = new AttachmentCollectionPage(attachmentCollectionResponse, null);
            message.attachments = attachmentCollectionPage;
        }

        graphClient.me().sendMail(UserSendMailParameterSet.newBuilder().withMessage(message).withSaveToSentItems(null).build()).buildRequest().post();

        return true;
    }

    /**
     * hàm thực hiện binding dữ liệu vào trong content
     *
     * @param content: nội dung gốc
     * @param props    : danh sách binding dữ liệu (key là kí hiệu trong content, value là gì giá cần thay thế tương ứng)
     * @return content sau khi binding dữ liệu
     */
    private String bindData(String content, Map<String, Object> props) {
        if (props == null) return content;
        Set<String> keySet = props.keySet();
        for (String key : keySet) {
//            String prop = "\\$\\{" + key + "\\}";
//            content = content.replaceAll(prop, String.valueOf(props.get(key)));
            content = content.replace(key, String.valueOf(props.get(key)));
        }
        return content;
    }


    // data room \\
    public List<DriveItem> getSharedItem() {
        graphClient = this.buildGraphClient();
        DriveSharedWithMeCollectionPage driveSharedWithMeCollectionPage = graphClient.me().drive().sharedWithMe().buildRequest().get();
        System.out.println("driveSharedWithMeCollectionPage: " + driveSharedWithMeCollectionPage);
        List<DriveItem> result = new ArrayList<>();
        do {
            List<DriveItem> currentPage = driveSharedWithMeCollectionPage.getCurrentPage();
            result.addAll(currentPage);
            DriveSharedWithMeCollectionRequestBuilder nextPage = driveSharedWithMeCollectionPage.getNextPage();
            driveSharedWithMeCollectionPage = nextPage == null ? null : nextPage.buildRequest().get();
        } while (driveSharedWithMeCollectionPage != null);

        this.getAllSite();

        return result;
    }

    @Value("${dr.site}")
    public String site = "";

    /**
     * Lấy dánh sách các site
     *
     * @return
     */
    public List<Site> getAllSite() {
        graphClient = this.buildGraphClient();
        List<Site> result = new ArrayList<>();
        LinkedList<Option> requestOption = new LinkedList<>();
        requestOption.add(new QueryOption("search", site));

        SiteCollectionPage siteCollectionPage = graphClient.sites().buildRequest(requestOption).get();
        do {
            List<Site> currentPage = siteCollectionPage.getCurrentPage();
            result.addAll(currentPage);
            SiteCollectionRequestBuilder nextPage = siteCollectionPage.getNextPage();
            siteCollectionPage = nextPage == null ? null : nextPage.buildRequest().get();
        } while (siteCollectionPage != null);
        return result;
    }

    public Site getAllItemOfSite(String siteId) {
        graphClient = this.buildGraphClient();
        Site site = graphClient.sites(siteId).buildRequest().get();
        return site;
    }

    public List<Object> getAllItemOfSite_v2(String siteId) {
        graphClient = this.buildGraphClient();
        List<Object> result = new ArrayList<>();

        ListCollectionPage listCollectionPage = graphClient.sites(siteId).lists().buildRequest().get();
        do {
            List<com.microsoft.graph.models.List> currentPage = listCollectionPage.getCurrentPage();
            result.addAll(currentPage);
            ListCollectionRequestBuilder nextPage = listCollectionPage.getNextPage();
            listCollectionPage = nextPage == null ? null : nextPage.buildRequest().get();
        } while (listCollectionPage != null);

        DriveCollectionPage driveCollectionPage = graphClient.sites(siteId).drives().buildRequest().get();
        do {
            List<Drive> currentPage = driveCollectionPage.getCurrentPage();
            result.addAll(currentPage);
            DriveCollectionRequestBuilder nextPage = driveCollectionPage.getNextPage();
            driveCollectionPage = nextPage == null ? null : nextPage.buildRequest().get();
        } while (driveCollectionPage != null);

        SiteCollectionPage siteCollectionPage = graphClient.sites(siteId).sites().buildRequest().get();
        do {
            List<Site> currentPage = siteCollectionPage.getCurrentPage();
            result.addAll(currentPage);
            SiteCollectionRequestBuilder nextPage = siteCollectionPage.getNextPage();
            siteCollectionPage = nextPage == null ? null : nextPage.buildRequest().get();
        } while (siteCollectionPage != null);

        return result;
    }


    @Value("${dr.id-root-folder:01QZ34IVZPT72VIRAZCNALKAPTE3X35EEV}")
    private String ID_ROOT_FOLDER_DATAROOM;

    public List<DriveItem> getAllItem(String itemId) {
        graphClient = this.buildGraphClient();
        itemId = itemId == null ? ID_ROOT_FOLDER_DATAROOM : itemId;
        List<DriveItem> result = new ArrayList<>();
        DriveItemCollectionPage driveItemCollectionPage = graphClient.me().drive().items(itemId).children().buildRequest().get();
        do {
            List<DriveItem> currentPage = driveItemCollectionPage.getCurrentPage();
            result.addAll(currentPage);
            DriveItemCollectionRequestBuilder nextPage = driveItemCollectionPage.getNextPage();
            driveItemCollectionPage = nextPage == null ? null : nextPage.buildRequest().get();
        } while (driveItemCollectionPage != null);
        return result;
    }

    @Value("${dr.id-root-site:vingroupjsc.sharepoint.com,67ea0c57-202c-42ab-ad16-096093f182f3,5e889fea-cd7d-4166-893b-fd7dd36c0cce}")
    public String ID_ROOT_SITE;

    public List<Object> getAllDriectoryObject(String siteId) {
        siteId = Strings.isNullOrEmpty(siteId) ? ID_ROOT_SITE : siteId;
        graphClient = this.buildGraphClient();
        List<Object> result = new ArrayList<>();
        // get all sub site
        SiteCollectionPage siteCollectionPage = graphClient.sites(siteId).sites().buildRequest().get();
        List<Site> allSubSite = new ArrayList<>();
        do {
            List<Site> currentPage = siteCollectionPage.getCurrentPage();
            allSubSite.addAll(currentPage);
            SiteCollectionRequestBuilder nextPage = siteCollectionPage.getNextPage();
            siteCollectionPage = nextPage == null ? null : nextPage.buildRequest().get();
        } while (siteCollectionPage != null);
        result.addAll(allSubSite);

        DriveCollectionPage driveCollectionPage = graphClient.sites(siteId).drives().buildRequest().get();
        List<Drive> allDrive = new ArrayList<>();
        do {
            List<Drive> currentPage = driveCollectionPage.getCurrentPage();
            allDrive.addAll(currentPage);
            DriveCollectionRequestBuilder nextPage = driveCollectionPage.getNextPage();
            driveCollectionPage = nextPage == null ? null : nextPage.buildRequest().get();
        } while (driveCollectionPage != null);
        result.addAll(allDrive);

        return result;
    }

    public List<DriveItem> getAllItemFormDrive(String driveId, String itemId) {
        graphClient = this.buildGraphClient();
        if (Strings.isNullOrEmpty(itemId)) {
            DriveItemCollectionPage driveItemCollectionPage = graphClient.drives(driveId).root().children().buildRequest().get();
            List<DriveItem> result = new ArrayList<>();
            do {
                List<DriveItem> currentPage = driveItemCollectionPage.getCurrentPage().stream().filter(ele -> ele.file == null).collect(Collectors.toList());
                result.addAll(currentPage);
                DriveItemCollectionRequestBuilder nextPage = driveItemCollectionPage.getNextPage();
                driveItemCollectionPage = nextPage == null ? null : nextPage.buildRequest().get();
            } while (driveItemCollectionPage != null);
            return result;
        } else {
            DriveItemCollectionPage driveItemCollectionPage = graphClient.drives(driveId).items(itemId).children().buildRequest().get();
            List<DriveItem> result = new ArrayList<>();
            do {
                List<DriveItem> currentPage = driveItemCollectionPage.getCurrentPage().stream().filter(ele -> ele.file == null).collect(Collectors.toList());
                result.addAll(currentPage);
                DriveItemCollectionRequestBuilder nextPage = driveItemCollectionPage.getNextPage();
                driveItemCollectionPage = nextPage == null ? null : nextPage.buildRequest().get();
            } while (driveItemCollectionPage != null);
            return result;
        }
    }


    /**
     * Tạo file trên driver khác
     *
     * @param fileName:     tên file
     * @param driverId:     id của driver sẽ tạo
     * @param parentItemId:
     * @param fileContent
     * @return
     * @throws IOException
     */
    public LargeFileUploadResult<DriveItem> createFileDrive(String fileName, String driverId, String parentItemId, byte[] fileContent) throws IOException {
        log.debug("GraphService: createFileDrive(fileName: {}, driverId: {}, parentItemId: {}, fileContent: )", fileName, driverId, parentItemId);
        if (parentItemId == null || parentItemId.isEmpty())
            parentItemId = UploadFile365CustomService.ROOT_FOLDER_ITEM_ID;
        graphClient = this.buildGraphClient();
        InputStream fileStream = new ByteArrayInputStream(fileContent);
        long streamSize = (long) fileStream.available();

        String newFileName = fileName.replaceAll("[\\/\\*<>?:|#%]", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
        newFileName = newFileName.replace("\\", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);

        IProgressCallback callback = new IProgressCallback() {
            @Override
            // Called after each slice of the file is uploaded
            public void progress(final long current, final long max) {
                System.out.println(String.format("Uploaded %d bytes of %d total bytes", current, max));
            }
        };

        DriveItemCreateUploadSessionParameterSet uploadParams = DriveItemCreateUploadSessionParameterSet.newBuilder().withItem(new DriveItemUploadableProperties()).build();


        UploadSession uploadSession;
        if (parentItemId == null || "".equals(parentItemId.trim())) {
//            uploadSession = graphClient.drive().root().itemWithPath(newFileName).createUploadSession(uploadParams).buildRequest().post();
            uploadSession = graphClient.drives(driverId).root().itemWithPath(newFileName).createUploadSession(uploadParams).buildRequest().post();
        } else {
//            uploadSession = graphClient.drive().items(parentItemId).itemWithPath(newFileName).createUploadSession(uploadParams).buildRequest().post();
            uploadSession = graphClient.drives(driverId).items(parentItemId).itemWithPath(newFileName).createUploadSession(uploadParams).buildRequest().post();
        }

        LargeFileUploadTask<DriveItem> largeFileUploadTask = new LargeFileUploadTask<DriveItem>(uploadSession, graphClient, fileStream, streamSize, DriveItem.class);

        LargeFileUploadResult<DriveItem> upload = largeFileUploadTask.upload(0, null, callback);

        String itemId = upload.responseBody.id;

        // gắn quyền
        Permission permission = this.grantAccess(itemId);
        upload.responseBody.webUrl = permission.link.webUrl;

//        return itemId;
        return upload;
    }


    @Value("${dr.default-sync.is-default:true}")
    public String IS_DEFAULT;
    @Value("${dr.default-sync.driveId-default:b!VwzqZywgq0KtFglgk_GC86Xb2tj3HBBClJ15MclEcXB33947HrF0RZZggpzeC2g9}")
    public String DEFAULT_DRIVEID;
    @Value("${dr.default-sync.itemId-default:01NHK7J2G7QITLGFEJTJBYJRDS7R3BGJFK}")
    public String DEFAULT_ITEMID;

    /**
     * Hàm thực hiện copy file hoặc folder
     *
     * @param itemIdSource : itemId của file(folder) cần copy
     * @param itemTarget   : itemId của Folder cần copy đến
     * @param name         : tên của file(folder) copy được
     */
    public String copyFileDrive(String itemIdSource, String itemTarget, String driveId, String name) throws IOException {
        graphClient = this.buildGraphClient();

        String newFileName = name.replaceAll("[\\/\\*<>?:|#%]", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
        newFileName = newFileName.replace("\\", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);

        if ("TRUE".equalsIgnoreCase(this.IS_DEFAULT)) {
            itemTarget = this.DEFAULT_ITEMID;
            driveId = this.DEFAULT_DRIVEID;
        }

        ItemReference parentReference = new ItemReference();
        parentReference.id = itemTarget;
        parentReference.driveId = driveId;
//        DriveItem result = graphClient.drive().items(itemIdSource).copy(DriveItemCopyParameterSet.newBuilder().withName(name).withParentReference(parentReference).build()).buildRequest().post();
        DriveItem result = graphClient.me().drive().items(itemIdSource).copy(DriveItemCopyParameterSet.newBuilder().withName(newFileName).withParentReference(parentReference).build()).buildRequest().post();

        //DriveItemCollectionPage driveItemCollectionPage = graphClient.drive().items(itemTarget).children().buildRequest().get();
        JsonElement graphResponseHeaders = result.additionalDataManager().get("graphResponseHeaders");
        JsonElement location = graphResponseHeaders.getAsJsonObject().get("location");
        String linkResultCopy = location.getAsJsonArray().get(0).getAsString();
        return linkResultCopy;
    }

    public String getResultCopyToDrive(String url) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseTemp = restTemplate.getForEntity(url, String.class);
        String resourceId = null;
        try {
            JSONObject jsonResponse = new JSONObject(responseTemp.getBody());
            resourceId = jsonResponse.get("resourceId").toString();
        } catch (Exception e) {
            log.error("{}", e);
            return null;
        }
        return resourceId;
    }


    // Lấy thông tin data room thông qua user \\
    public List<Object> getAllDriectoryObjectWithUser(String siteId, String userId) {
        siteId = Strings.isNullOrEmpty(siteId) ? ID_ROOT_SITE : siteId;
        graphClient = this.buildGraphClient();
        if (Strings.isNullOrEmpty(userId)) {
            return this.getAllDriectoryObject(siteId);
        }

        List<Object> result = new ArrayList<>();
        // get all sub site
        SiteCollectionWithReferencesPage site1 = graphClient.users(userId).followedSites().buildRequest().get();
        SiteCollectionPage siteCollectionPage = graphClient.sites(siteId).sites().buildRequest().get();
        List<Site> allSubSite = new ArrayList<>();
        do {
            List<Site> currentPage = siteCollectionPage.getCurrentPage();
            allSubSite.addAll(currentPage);
            SiteCollectionRequestBuilder nextPage = siteCollectionPage.getNextPage();
            siteCollectionPage = nextPage == null ? null : nextPage.buildRequest().get();
        } while (siteCollectionPage != null);
        result.addAll(allSubSite);

        DriveCollectionPage driveCollectionPage = graphClient.sites(siteId).drives().buildRequest().get();
        List<Drive> allDrive = new ArrayList<>();
        do {
            List<Drive> currentPage = driveCollectionPage.getCurrentPage();
            allDrive.addAll(currentPage);
            DriveCollectionRequestBuilder nextPage = driveCollectionPage.getNextPage();
            driveCollectionPage = nextPage == null ? null : nextPage.buildRequest().get();
        } while (driveCollectionPage != null);
        result.addAll(allDrive);

        return result;
    }

    public List<DriveItem> getAllItemFormDriveWithUser(String driveId, String itemId, String userId) {
        graphClient = this.buildGraphClient();
        if (Strings.isNullOrEmpty(userId)) {
            return this.getAllItemFormDrive(driveId, itemId);
        }

        if (Strings.isNullOrEmpty(itemId)) {
            DriveItemCollectionPage driveItemCollectionPage = graphClient.users(userId).drives(driveId).root().children().buildRequest().get();
            List<DriveItem> result = new ArrayList<>();
            do {
                List<DriveItem> currentPage = driveItemCollectionPage.getCurrentPage().stream().filter(ele -> ele.file == null).collect(Collectors.toList());
                result.addAll(currentPage);
                DriveItemCollectionRequestBuilder nextPage = driveItemCollectionPage.getNextPage();
                driveItemCollectionPage = nextPage == null ? null : nextPage.buildRequest().get();
            } while (driveItemCollectionPage != null);
            return result;
        } else {
            DriveItemCollectionPage driveItemCollectionPage = graphClient.users(itemId).drives(driveId).items(itemId).children().buildRequest().get();
            List<DriveItem> result = new ArrayList<>();
            do {
                List<DriveItem> currentPage = driveItemCollectionPage.getCurrentPage().stream().filter(ele -> ele.file == null).collect(Collectors.toList());
                result.addAll(currentPage);
                DriveItemCollectionRequestBuilder nextPage = driveItemCollectionPage.getNextPage();
                driveItemCollectionPage = nextPage == null ? null : nextPage.buildRequest().get();
            } while (driveItemCollectionPage != null);
            return result;
        }
    }

    public User getUserByUsername(OAuthTokenDTO oAuthTokenDTO) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + oAuthTokenDTO.getToken());
        if (Strings.isNullOrEmpty(oAuthTokenDTO.getToken())) {
            return null;
        }
        User user = null;
        try {
            user = callAPIUtils.createRestTemplateGraph(BASE_URI + URI_ME, null, HttpMethod.GET, headers, User.class);

        } catch (Exception exception) {
            log.error("getUserByUsername: {}", exception);
        }
        return user;
    }
}
