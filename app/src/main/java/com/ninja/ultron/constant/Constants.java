package com.ninja.ultron.constant;

public class Constants {

    //public static final String BASE_URL  = "http://direct.ninjacart.in:8080";  //DNS PROD
    public static final String HENER_ALPHA = ":8111";
    public static final String HEZNER_BETA = ":8222";
    public static final String HEZNER_GAMMA = ":8333";
    public static final String HEZNER_DELTA = ":8444";
    public static final String HEZNER_THETA = ":8555";
    public static final String HEZNER_EPSILON = ":8666";
    public static final String HETZNER_OMEGA_PORT = ":8777";
    public static final String HETZNER_LEMBDA_PORT = ":9111";
    public static final String HETZNER_SIGMA_PORT = ":8888";
    public static final String HETZNER_IOTA_PORT = ":8999";
    public static final String USER_PREFERENCE = "User_Pref";

    public static final String INIT_APP_NAME = "Ultron";
    public static final String APP_VERSION_KEY = "appVersion";
    public static final String NO_INTERNET_CONNECTION = "No Internet Connection. Kindly check";




    public static final String BASE_URL = "http://88.99.31.206" +HEZNER_DELTA; //GAMMA-HEZNER

    public static final String USER_ROLE_DISPATCH_EXECUTIVE = "DISPATCH_EXECUTIVE";
    public static final String USER_ROLE_UNIT_PICK_EXECUTIVE = "UNIT_PICK_EXECUTIVE";
    public static final String USER_ROLE_DC_EXECUTIVE = "DC_EXECUTIVE";
    public static final String USER_ROLE_DC_SUPERVISOR = "DC_SUPERVISOR";
    public static final String USER_ROLE_RECOVERY_OPS_EXECUTIVE = "RECOVERY_OPS_EXECUTIVE";
    public static final String USER_ROLE_RECOVERY_SALES_EXECUTIVE = "RECOVERY_SALES_EXECUTIVE";
    public static final String USER_ROLE_DC_OPS_LEAD = "DC_OPS_LEAD";
    public static final int HTTP_AUTHENTICATON_FAILURE_CODE = 401;
    public static final String REST_FAILURE_MESSAGE = "Some thing went wrong";
    public static final int NUMBER_FEED_FETECHED_PER_PULL = 10;

    public static final int YOUR_ORDERS_FETCH_LIMIT = 10;

    public static final int YOUR_ORDERS_FETCH_OFFSET = 0;

    public static final int FEED_LEFT_BEFORE_NEW_ORDER_FETCH = 3;

    public static final String ASSET_LIST_URL="http://88.99.31.206:1111/api/web/find/asset?userId=";
    public static final String ASSET_DETAILS_MODEL_URL="http://88.99.31.206:1111/api/web/details/asset?assetId=";
    public static String ASSET_DETAILS_URL;
    public static final String PENDING_REQUESTS_URL="http://10.0.0.3:1111/api/web/request/pending?userId=8&approver=0";
    public static final String PENDING_REQUESTS_DETAILS_MODEL_URL="http://88.99.31.206:1111/api/web/request/?assetRequestId=";
    public static String PENDING_REQUESTS_DETAILS_URL;
    public static final String INITIATE_TRANSFER_URL="http://88.99.31.206:1111/api/web/request/transfer";
    public static final String TRANSFER_REASONS_API="http://88.99.31.206:1111/api/web/find/asset/issueReason";
    public static final String GET_LABOUR_FOR_ATTENDANCE_URL="http://88.99.31.206:1111/api/web/labour/attendance";
    public static final String MARK_ATTENDANCE_URL="http://88.99.31.206:1111/api/web/labourAttendanceTracker";
    public static final String GET_LABOUR_SHIFT_DETAILS_URL="http://88.99.31.206:1111/api/web/labourAttendanceTracker/facilityShiftDetails";
    public static final String GET_REPORTED_LABOUR_URL="http://88.99.31.206:1111/api/web/labourAttendanceTracker/reportedLabour?shiftDetailId=";
    public static final String REVOKE_ATTENDANCE_URL="http://88.99.31.206:1111/api/web/labourAttendanceTracker/revokeAttendance";

}
