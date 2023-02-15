package com.vsm.business.common.Sign.sim.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static final int ID_TOKEN_EXPIRE_DURATION = 3600; // seconds
    public static final String LOGIN_ERROR_MSG = "Sai tên đăng nhập/mật khẩu";

    // DIGITAL SIGNATURE CONFIGS - HTTPS
//    public static final String AP_ENDPOINT = "https://137.59.46.39:9061/rest/service";
//    public static final String HOST = "137.59.46.39:9061";
//    public static final String AP_ENDPOINT = "http://10.16.151.210/signMobi/rest/service";
//    public static final String AP_ENDPOINT = "http://10.1.0.52/signMobi/rest/service"; //Mobifone kong


    // DuowngTora \\ chuyển thông số ra file config

    public static final String HOST = "mss.newtel-ca.vn:9061";       //"mss.newtel-ca.vn:9061"; //"10.38.2.3:9061";
    public static final String API_KEY = "crw51LlmbeH7GQpZGc8cx4LRcYS6GSygaN0DhLVIAJk8t5kh";        //"UhZ2mmzhu55LvDUQKRleq29Uxh8cFNCHGAwH0bCZMSkonMzc";    //"mFd9VKjt4wXEGH1B1Ff5cJGQqyJoeiiFM9Z68XvFvDC0TUCv";
    public static final String AP_ID = "http://eoffice.vincom.com.vn";      // "http://smart-office.vn";    //http://eoffice-ap";

    public static final String AP_NAME = "eoffice-ap";      // "eoffice-ap";      //"eoffice-ap";

//    @Value("${system.sign.sim.mobi.appHost:mss.newtel-ca.vn:9061}")
//    public String HOST;
//    @Value("${system.sign.sim.mobi.appKey:UhZ2mmzhu55LvDUQKRleq29Uxh8cFNCHGAwH0bCZMSkonMzc}")
//    public String API_KEY;
//
//    @Value("${system.sign.sim.mobi.appId:http://smart-office.vn}")
//    public String AP_ID;
//
//    @Value("${system.sign.sim.mobi.appName:eoffice-ap}")
//    public String AP_NAME;
    // end DuowngTora \\


//    public static final String AP_ENDPOINT = "https://137.59.46.39:9061/rest/service"; //https://mobica-api.mobifone.vn:9061/rest/service
//    public static final String HOST = "137.59.46.39:9061";
////    public static final String AP_ENDPOINT = "https://10.38.2.3:9061/rest/service";
////    public static final String HOST = "10.38.2.3:9061";
//    public static final String API_KEY = "e794TZy9OvH5jKTdP0OMsXHsGwffGD2HWNzkJxX1nwjGoLCG";
//    public static final String AP_ID = "http://eoffice_saas_demo";
//    public static final String AP_NAME = "eoffice_saas_demo";
    //    public static final String AP_ID = "T02";

    // cấu hình nhà mạng các SIM kí số
    public static final String VIETTEL_CA = "viettel";
    public static final String MOBIFONE_CA = "mobifone";
    public static final String BAN_CO_YEU_CA = "bcy";
    public static final String VINAPHONE_CA = "vinaphone";
    public static final class VIETTEL {
        public static final String AP_ID = "AP2";
        public static final String PROCESS_CODE_SIGN = "000001";
        public static final String PROCESS_CODE_QUERYCER = "000006";
        public static final String MODE_SYNC = "SYNC";
//        public static final String URL_WS = "http://mobilepki.viettel-ca.vn:8080/apws.asmx";
//        public static final String URL_WS = "http://10.16.151.210/signViettel";
//        public static final String URL_WS = "http://10.1.0.52/signViettel";
        public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAI1ZGghrDQryVQcDDe1gpJ1TQvRWZz0P59/04LlMOdcf0G7XGHy7tzmWffdKgVmlkTbw0tMGa37lg7Suxrm9JpmhcEQmTMo6L06SEmBYSiZfPWMyDnjdWhE8mPMP3ju3xx24UVXcpSclykQflTBr42yZgqsgM19ndsDFkwtz2uWDAgMBAAECgYAJzOPBMar111eN5OhSTSEcx2kdB+Cgmzm4jYIHVwGrqMkK5l8MRvetRoH1Y3UUgiZPaOM1Pny1j7RSEsw0lKjYY4Jawm5n7js13VkIs9tO8HhK00Oo/7a6ZRxAbczpfvGHmMdwaUQgHSGngzE7T3D8Eh4xx3Qu6fmTAIeKPNSMAQJBANfPb9gDkWIsQ/16siOQaTEfacASx/2MvucfrQ2WYGWbG1xNVfA1hkC2tmRRu3SRJp/1lhlERTvOSac4m9IBMasCQQCnq75nAlQTU+/1GvH8nLyEPrCudn40jMCKSEkMWJKKVuiKCrF2GJCZQipNs1DfMSyPggux3Z3hQ62JBuZfNvOJAkEAxIYCM5QMMHpe79Vrozc+k50nj+GKfTpOHeqajGUEI4K7x7IlMDmNqCC6t2A2dFA5/DCIHzosUeno6H6EZxjvQQJAY+IStgiUD0OEge4AU+0G/HzgAb5C5okmtfnj0j/9Y/3r3zgJiYGOuk3JJ6p3tc30brUYxGdyAtyvRx7eI8B3iQJBAJpa4qW6sJ36AKZFLq4D6EwaL2G3kc1bVFSwgRB0TFMB3Vak4O4mu1HWfgCWo20RvJCfcYCrIEdguvd3IunQ9Mc=";
        public static final String MODE_ASYNC_SERVER_SERVER = "SS";
        public static final String MODE_ASYNC_CLIENT_SERVER = "CS";
        public static final String FORMAT_DATE = "{0:yyyyMMddHHmmss}";
        public static final String MSSP_ID = "Viettel";
        public static final int FORCE_PIN = 1;
        public static final int RUN_IMM = 0; // ko yeu cau ky ngay
        public static final int SIG_TYPE = 0; // ky van ban
        public static final String HASH_ALGORITHM_SHA1 = "SHA1";
    }

    public static final class VINA_PHONE {
        public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtc3NwIjpbIlZpbmFQaG9uZSJdLCJ1bml0IjoiTW9iaUZvbmUiLCJuYW1lIjoiTW9iaUZvbmUiLCJpc3MiOiJWTlBUIFNpZ25hdHVyZSIsImdyb3VwcyI6WyJST0xFX1NJR05fRklMRSIsIlJPTEVfU0lHTl9IQVNIIl0sImV4cCI6MTYwOTk4NDM2NSwiaWF0IjoxNTk0MDg2NzY1fQ.7VucH6RVVQ-3cWVIntmzYZ3QZeUzQSQte66NF7SxVh0";
//        public static final String CERT_URL = "http://mobileid.vnpt.vn/VNPTsignature/rest/signature/getCertByPhone";
////        public static final String SIGN_URL = "http://mobileid.vnpt.vn/VNPTsignature/rest/signature/sign/hash";
//        public static final String CERT_URL = "http://10.16.151.210/signVina/getCertByPhone";
//        public static final String SIGN_URL = "http://10.16.151.210/signVina/sign/hash";
//        public static final String CERT_URL = "http://10.1.0.52/signVina/getCertByPhone";
//        public static final String SIGN_URL = "http://10.1.0.52/signVina/sign/hash";
    }

    public static final class BAN_CO_YEU {
        public static String TSA = "http://tsa.ca.gov.vn";
        public static String NO_IMAGE_SIGNATURE="iVBORw0KGgoAAAANSUhEUgAAAAEAAAABAQMAAAAl21bKAAAAA1BMVEX/TQBcNTh/AAAAAXRSTlPM0jRW/QAAAApJREFUeJxjYgAAAAYAAzY3fKgAAAAASUVORK5CYII=";
    }
    // cấu hình tenant
    public class HEADER {
        public static final String X_TENANT_CODE = "TenantCode";
    }

    public interface TENANT {
        interface HEADER {
            String X_TENANT_CODE = "TenantCode";
        }

        String TENANT_FIELD_NAME = "tenantCode";

        interface STATUS {
            String ACTIVE = "active";
            String DEACTIVE = "deactive";
        }
    }

    public interface DELETE {

        public static final Long DELETED = 1L;
        public static final Long NORMAL = 0L;
    }
}
