package com.ninja.ultron.restclient;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.ninja.ultron.constant.Constants;
import com.ninja.ultron.entity.AsgardCodeMessageEntity;
import com.ninja.ultron.entity.AssetDetailsMiniEntity;
import com.ninja.ultron.entity.AssetMiniEntity;
import com.ninja.ultron.entity.AssetTypeMiniEntity;
import com.ninja.ultron.entity.GetPenaltyApiEntity;
import com.ninja.ultron.entity.InitApiEntity;
import com.ninja.ultron.entity.InitiateTransferEntity;
import com.ninja.ultron.entity.LabourAttendanceMobileDTO;
import com.ninja.ultron.entity.LabourAttendanceMobileDTOAPI;
import com.ninja.ultron.entity.LabourAttendanceTrackerEntity;
import com.ninja.ultron.entity.LabourShiftDetailAPI;
import com.ninja.ultron.entity.LabourShiftDetailEntity;
import com.ninja.ultron.entity.LabourTimeEntity;
import com.ninja.ultron.entity.LoginEntity;
import com.ninja.ultron.entity.NewAssetInitateRequestEntity;
import com.ninja.ultron.entity.PendingRequestDetailsMiniEntity;
import com.ninja.ultron.entity.PendingRequestMiniEntity;
import com.ninja.ultron.entity.PostBulkLaboursAttendanceAPIEntity;
import com.ninja.ultron.entity.TransferReasonsMiniEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.functions.UserDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ninja.ultron.constant.Constants.GET_LABOUR_FOR_ATTENDANCE_URL;
import static com.ninja.ultron.constant.Constants.GET_LABOUR_SHIFT_DETAILS_URL;
import static com.ninja.ultron.constant.Constants.GET_LABOUR_TIME_ABSOLUTE_URL;
import static com.ninja.ultron.constant.Constants.GET_PENALTIES_URL;
import static com.ninja.ultron.constant.Constants.MARK_ATTENDANCE_URL;
import static com.ninja.ultron.constant.Constants.REVOKE_ATTENDANCE_URL;

public class RestClientImplementation {
    static RequestQueue queue;
    private static final String BASE_URL = Constants.BASE_URL;

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public synchronized  static AsgardCodeMessageEntity processVolleyError(VolleyError error){
        AsgardCodeMessageEntity asgardCodeMessageEntity = new AsgardCodeMessageEntity();
        if (error.networkResponse != null && error.networkResponse.data != null) {
            Gson gson = new Gson();
            AsgardCodeMessageEntity erroAsgardCodeMessageEntity = gson.fromJson(new String(error.networkResponse.data), AsgardCodeMessageEntity.class);
            if (erroAsgardCodeMessageEntity.getMessage() != null) {
                asgardCodeMessageEntity.setMessage(erroAsgardCodeMessageEntity.getMessage());
                asgardCodeMessageEntity.setCode(erroAsgardCodeMessageEntity.getCode());
            }
        } else {
            try {
                if (error.getCause() != null && error.getCause().getMessage() != null) {
                    asgardCodeMessageEntity.setMessage(error.getCause().getMessage());
                    asgardCodeMessageEntity.setCode(999);
                }
            } catch (Exception e) {
                if (error.getCause() != null && error.getCause().getMessage() != null) {
                    asgardCodeMessageEntity.setMessage(error.getCause().getMessage());
                    asgardCodeMessageEntity.setCode(999);
                } else {
                    asgardCodeMessageEntity.setMessage("Error - Exception");
                    asgardCodeMessageEntity.setCode(999);
                }
            }
        }
        if(asgardCodeMessageEntity==null){
            asgardCodeMessageEntity.setCode(999);
            asgardCodeMessageEntity.setMessage("Error");
        }
        return asgardCodeMessageEntity;
    }

    public static void userLogin(final LoginEntity loginEntity, final LoginEntity.RestClientInterface restclientinterface, final Context context) {
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        //String url = getAbsoluteUrl("/user/login/app", context);
        String url = "http://88.99.31.206:1111"+"/web/login";
        JSONObject postParams = loginEntity.getJsonObjectAsParams();
        Log.d("login_params", "" + postParams);
        Log.e("login_url", "" + url);
        JsonBaseRequest postRequest = new JsonBaseRequest(Request.Method.POST, url, postParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("reponse", "" + response);
                            Gson gson = new Gson();
                            LoginEntity newLoginSuccessEntity = gson.fromJson((response.getJSONObject("response")).toString(), LoginEntity.class);
                            loginEntity.setAsgardUser(newLoginSuccessEntity.getAsgardUser());
                            loginEntity.setMessage("Success");
                            loginEntity.setSessionId(newLoginSuccessEntity.getSessionId());
                            loginEntity.setToken(newLoginSuccessEntity.getToken());
                            restclientinterface.onLogin(loginEntity, null);
                        } catch (Exception e) {
                            Log.e("reponseError", "" + e.toString());
                            restclientinterface.onLogin(loginEntity, new VolleyError());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    Gson gson = new Gson();
                    LoginEntity newLoginErrorEntity = gson.fromJson(new String(error.networkResponse.data), LoginEntity.class);
                    if (newLoginErrorEntity.getMessage() != null) {
                        loginEntity.setMessage(newLoginErrorEntity.getMessage());
                        loginEntity.setCode(newLoginErrorEntity.getCode());
                    }
                }
                restclientinterface.onLogin(loginEntity, new VolleyError());
            }
        }, 30000, 0) {


        };
        queue.add(postRequest);

    }



    public static void initApi(final InitApiEntity initApiEntity, final InitApiEntity.UltronRestClientInterface restClientInterface, final Context context) {
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        String url = getAbsoluteUrl("/initializeConfig");
        JSONObject postParams = initApiEntity.getInitAPIParams();
        Log.d("", "" + postParams);
        JsonBaseRequest postRequest = new JsonBaseRequest(Request.Method.POST, url, postParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gson = new Gson();
                            InitApiEntity newInitSuccessEntity = gson.fromJson(response.toString(), InitApiEntity.class);
                            initApiEntity.setCurrentServerDate(newInitSuccessEntity.getCurrentServerDate());
                            initApiEntity.setAsgardUser(newInitSuccessEntity.getAsgardUser());
                            initApiEntity.setInitializeConfig(newInitSuccessEntity.getInitializeConfig());
                            // initApiEntity.setMyAssetList(newInitSuccessEntity.getMyAssetList());
                            /*if (newInitSuccessEntity.getCustomer() == null) {
                                initApiEntity.setCustomer(null);
                            } else {
                                initApiEntity.setCustomer(newInitSuccessEntity.getCustomer());
                            }*/
                            restClientInterface.onInitialize(initApiEntity, null);
                        } catch (Exception e) {
                            restClientInterface.onInitialize(initApiEntity, new VolleyError());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    Gson gson = new Gson();
                    InitApiEntity newInitErrorEntity = gson.fromJson(new String(error.networkResponse.data), InitApiEntity.class);
                    if (newInitErrorEntity.getMessage() != null) {
                        initApiEntity.setMessage(newInitErrorEntity.getMessage());
                        initApiEntity.setCode(newInitErrorEntity.getCode());
                    }
                }
                restClientInterface.onInitialize(initApiEntity, new VolleyError());
            }
        }, 30000, 0) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s", UserDetails.getUserName(context), UserDetails.getUserPassword(context));
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);
                params.put(Constants.APP_VERSION_KEY, String.valueOf(CommonFunctions.getPackageVersion(context)));
                return params;
            }
        };
        queue.add(postRequest);
    }


    public static void assetListApi(final AssetMiniEntity assetMiniEntity, final AssetMiniEntity.UltronRestClientInterface restClientInterface, final Context context,final String category){
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        String userId= String.valueOf(UserDetails.getAsgardUserId(context));
        Log.d("UserId",userId);

        JsonBaseRequest getRequest = new JsonBaseRequest(Request.Method.GET,Constants.ASSET_LIST_URL+userId+category, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    Log.d("RESPONSE CHECK",response.toString());
                    Gson gson = new Gson();
                    AssetMiniEntity successAssetMiniEntity = gson.fromJson(response.toString(), AssetMiniEntity.class);
                    assetMiniEntity.setStatusCode(successAssetMiniEntity.getStatusCode());
                    assetMiniEntity.setResponse(successAssetMiniEntity.getResponse());
                    assetMiniEntity.setMessage(successAssetMiniEntity.getMessage());
                    restClientInterface.onInitialize(assetMiniEntity, null);
                }catch (Exception e){
                    assetMiniEntity.setStatusCode(500);
                    assetMiniEntity.setMessage("Cast exception");
                    restClientInterface.onInitialize(assetMiniEntity, new VolleyError());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    Gson gson = new Gson();
                    AssetMiniEntity newAssetMiniEntity = gson.fromJson(new String(error.networkResponse.data), AssetMiniEntity.class);
                    if (newAssetMiniEntity.getMessage() != null) {
                        assetMiniEntity.setMessage(newAssetMiniEntity.getMessage());
                        assetMiniEntity.setStatusCode(newAssetMiniEntity.getStatusCode());
                    }
                }
                restClientInterface.onInitialize(assetMiniEntity, new VolleyError());
            }
        },30000, 0){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<String,String>();
                params.put("sessionid",UserDetails.getSessionId(context));
                params.put("accesstoken",UserDetails.getSessionToken(context));
                Log.d("",params.toString());
                return params;
            }
        };
        queue.add(getRequest);
    }
    public static void assetDetailsApi(final AssetDetailsMiniEntity assetDetailsMiniEntity, final AssetDetailsMiniEntity.UltronRestClientInterface restClientInterface, final Context context){
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        JsonBaseRequest getRequest = new JsonBaseRequest(Request.Method.GET, Constants.ASSET_DETAILS_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    Gson gson = new Gson();
                    AssetDetailsMiniEntity successAssetMiniEntity = gson.fromJson(response.toString(), AssetDetailsMiniEntity.class);
                    assetDetailsMiniEntity.setStatusCode(successAssetMiniEntity.getStatusCode());
                    assetDetailsMiniEntity.setResponse(successAssetMiniEntity.getResponse());
                    assetDetailsMiniEntity.setMessage(successAssetMiniEntity.getMessage());
                    restClientInterface.onInitialize(assetDetailsMiniEntity, null);
                }catch (Exception e){
                    assetDetailsMiniEntity.setStatusCode(500);
                    assetDetailsMiniEntity.setMessage("Cast exception");
                    restClientInterface.onInitialize(assetDetailsMiniEntity, new VolleyError());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    Gson gson = new Gson();
                    AssetMiniEntity newAssetMiniEntity = gson.fromJson(new String(error.networkResponse.data), AssetMiniEntity.class);
                    if (newAssetMiniEntity.getMessage() != null) {
                        assetDetailsMiniEntity.setMessage(newAssetMiniEntity.getMessage());
                        assetDetailsMiniEntity.setStatusCode(newAssetMiniEntity.getStatusCode());
                    }
                }
                restClientInterface.onInitialize(assetDetailsMiniEntity, new VolleyError());
            }
        },30000, 0){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<String,String>();
                params.put("sessionid",UserDetails.getSessionId(context));
                params.put("accesstoken",UserDetails.getSessionToken(context));
                return params;
            }
        };
        queue.add(getRequest);
    }

    public static void pendingRequestsApi(final PendingRequestMiniEntity pendingRequestMiniEntity,final PendingRequestMiniEntity.UltronRestClientInterface ultronRestClientInterface,final Context context)
    {
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        String userId= String.valueOf(UserDetails.getAsgardUserId(context));
        JsonBaseRequest getRequest=new JsonBaseRequest(Request.Method.GET, Constants.PENDING_REQUESTS_URL+userId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    Gson gson=new Gson();
                    Log.d("Response_pen",response.toString());
                    PendingRequestMiniEntity successEntity=gson.fromJson(response.toString(),PendingRequestMiniEntity.class);
                    pendingRequestMiniEntity.setMessage((String)successEntity.getMessage());
                    pendingRequestMiniEntity.setStatusCode((int)successEntity.getStatusCode());
                    pendingRequestMiniEntity.setResponse(successEntity.getResponse());
                    ultronRestClientInterface.onInitialize(pendingRequestMiniEntity,null);
                }
                catch (Exception e)
                {
                    pendingRequestMiniEntity.setStatusCode(500);
                    pendingRequestMiniEntity.setMessage("Cast Exception");
                    ultronRestClientInterface.onInitialize(pendingRequestMiniEntity,new VolleyError());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error.networkResponse!=null && error.networkResponse.data!=null)
                {
                    Gson gson=new Gson();
                    PendingRequestMiniEntity failureEntity=gson.fromJson(new String(error.networkResponse.data),PendingRequestMiniEntity.class );
                    if(failureEntity.getMessage()!=null)
                    {
                        pendingRequestMiniEntity.setMessage(failureEntity.getMessage());
                        pendingRequestMiniEntity.setStatusCode(failureEntity.getStatusCode());
                    }
                }
                ultronRestClientInterface.onInitialize(pendingRequestMiniEntity,new VolleyError());
            }
        },3000,0){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<String,String>();
                params.put("sessionid",UserDetails.getSessionId(context));
                params.put("accesstoken",UserDetails.getSessionToken(context));
                return params;
            }
        };
        queue.add(getRequest);
    }

    public static void initiateTranferApi(final InitiateTransferEntity initiateTransferEntity, final InitiateTransferEntity.UltronRestClientInterface ultronRestClientInterface,final Context context){
        final Gson gson = new Gson();
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        JSONObject initiateTransferJson = initiateTransferEntity.getJsonObjectAsParams();
        Log.d("JsonCheck",initiateTransferJson.toString());
        JsonBaseRequest postRequest = new JsonBaseRequest(Request.Method.POST, Constants.INITIATE_TRANSFER_URL, initiateTransferJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ultronRestClientInterface.onInitialize(initiateTransferEntity, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ultronRestClientInterface.onInitialize(initiateTransferEntity,new VolleyError());
            }
        });
        queue.add(postRequest);
    }

    public static void getTransferReasonsApi(final TransferReasonsMiniEntity transferReasonsMiniEntity,final TransferReasonsMiniEntity.UltronRestClientInterface ultronRestClientInterface,final Context context )
    {
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        JsonBaseRequest getRequest=new JsonBaseRequest(Request.Method.GET,Constants.TRANSFER_REASONS_API, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    Gson gson=new Gson();
                    TransferReasonsMiniEntity successEntity=gson.fromJson(response.toString(),TransferReasonsMiniEntity.class);
                    transferReasonsMiniEntity.setStatusCode(successEntity.getStatusCode());
                    transferReasonsMiniEntity.setMessage(successEntity.getMessage());
                    transferReasonsMiniEntity.setReponse(successEntity.getReponse());
                    ultronRestClientInterface.onInitialize(transferReasonsMiniEntity,null);
                }

                catch (Exception e)
                {
                    transferReasonsMiniEntity.setStatusCode(500);
                    transferReasonsMiniEntity.setMessage("Cast Exception");
                    ultronRestClientInterface.onInitialize(transferReasonsMiniEntity,new VolleyError());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error.networkResponse!=null && error.networkResponse.data!=null)
                {
                    Gson gson=new Gson();
                    TransferReasonsMiniEntity failureEntity=gson.fromJson(new String(error.networkResponse.data),TransferReasonsMiniEntity.class);

                    if(failureEntity.getMessage()!=null)
                    {
                        transferReasonsMiniEntity.setMessage(failureEntity.getMessage());
                        transferReasonsMiniEntity.setStatusCode(failureEntity.getStatusCode());
                    }

                }
                ultronRestClientInterface.onInitialize(transferReasonsMiniEntity,new VolleyError());
            }
        },3000,0){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<String,String>();
                params.put("sessionid",UserDetails.getSessionId(context));
                params.put("accesstoken",UserDetails.getSessionToken(context));
                return params;
            }
        };
        queue.add(getRequest);
    }

    public static void intiateNewAssetRequestApi(final NewAssetInitateRequestEntity newAssetInitateRequestEntity, final NewAssetInitateRequestEntity.UltronRestClientInterface ultronRestClientInterface,final  Context context)
    {
        final Gson gson = new Gson();
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        JSONObject assetRequestJson = newAssetInitateRequestEntity.getJsonObjectAsParams();
        Log.d("qwertt",assetRequestJson.toString());
        JsonBaseRequest postRequest = new JsonBaseRequest(Request.Method.POST, Constants.ASSET_REQUEST_URL, assetRequestJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //int code = response.getInt("statusCode");
                Log.d("CheckRespnce",response.toString());
                ultronRestClientInterface.onInitialize(newAssetInitateRequestEntity, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.toString());
                ultronRestClientInterface.onInitialize(newAssetInitateRequestEntity,new VolleyError());
            }
        });
        queue.add(postRequest);
    }

    public static void getAssetTypeApi(final AssetTypeMiniEntity assetTypeMiniEntity,final AssetTypeMiniEntity.UltronRestClientInterface ultronRestClientInterface,final Context context){
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        JsonBaseRequest getRequest = new JsonBaseRequest(Request.Method.GET,Constants.ASSET_TYPE_API,null,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("resonse",response.toString());
                    Gson gson = new Gson();
                    AssetTypeMiniEntity successEnitity = gson.fromJson(response.toString(),AssetTypeMiniEntity.class);
                    assetTypeMiniEntity.setStatusCode(successEnitity.getStatusCode());
                    assetTypeMiniEntity.setMessage(successEnitity.getMessage());
                    assetTypeMiniEntity.setResponse(successEnitity.getResponse());
                    ultronRestClientInterface.onInitialize(assetTypeMiniEntity,null);
                }

                catch (Exception e)
                {
                    assetTypeMiniEntity.setStatusCode(500);
                    assetTypeMiniEntity.setMessage("Cast Exception");
                    ultronRestClientInterface.onInitialize(assetTypeMiniEntity,new VolleyError());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse!=null && error.networkResponse.data!=null){
                    Gson gson = new Gson();
                    AssetTypeMiniEntity failureEntity = gson.fromJson(new String(error.networkResponse.data),AssetTypeMiniEntity.class);
                    if(failureEntity.getMessage()!=null){
                        assetTypeMiniEntity.setMessage(failureEntity.getMessage());
                        assetTypeMiniEntity.setStatusCode(failureEntity.getStatusCode());
                    }
                }
                ultronRestClientInterface.onInitialize(assetTypeMiniEntity,new VolleyError());
            }
        },3000,0){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<String,String>();
                params.put("sessionid",UserDetails.getSessionId(context));
                params.put("accesstoken",UserDetails.getSessionToken(context));
                return params;
            }
        };
        queue.add(getRequest);
    }

    public static void getPendingRequestDetailsApi(final PendingRequestDetailsMiniEntity pendingRequestDetailsMiniEntity,final PendingRequestDetailsMiniEntity.UltronRestClientInterface ultronRestClientInterface,final Context context)
    {
        queue= VolleySingleton.getInstance(context).getRequestQueue();
        final JsonBaseRequest getRequest=new JsonBaseRequest(Request.Method.GET, Constants.PENDING_REQUESTS_DETAILS_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    Gson gson=new Gson();
                    PendingRequestDetailsMiniEntity successEntity=gson.fromJson(response.toString(),PendingRequestDetailsMiniEntity.class);
                    pendingRequestDetailsMiniEntity.setStatusCode(successEntity.getStatusCode());
                    pendingRequestDetailsMiniEntity.setMessage(successEntity.getMessage());
                    pendingRequestDetailsMiniEntity.setResponse(successEntity.getResponse());
                    ultronRestClientInterface.onInitialize(pendingRequestDetailsMiniEntity,null);
                }

                catch (Exception e)
                {
                    pendingRequestDetailsMiniEntity.setStatusCode(500);
                    pendingRequestDetailsMiniEntity.setMessage("Cast Exception");
                    ultronRestClientInterface.onInitialize(pendingRequestDetailsMiniEntity,new VolleyError());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error.networkResponse!=null && error.networkResponse.data!=null)
                {
                    Gson gson=new Gson();
                    PendingRequestDetailsMiniEntity failureEntity=gson.fromJson(new String(error.networkResponse.data),PendingRequestDetailsMiniEntity.class);
                    if(failureEntity.getMessage()!=null)
                    {
                        pendingRequestDetailsMiniEntity.setMessage(failureEntity.getMessage());
                        pendingRequestDetailsMiniEntity.setStatusCode(failureEntity.getStatusCode());
                        ultronRestClientInterface.onInitialize(pendingRequestDetailsMiniEntity,new VolleyError());
                    }
                }

            }
        },3000,0){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<String,String>();
                params.put("sessionid",UserDetails.getSessionId(context));
                params.put("accesstoken",UserDetails.getSessionToken(context));
                return params;
            }
        };
        queue.add(getRequest);
    }

    public static void getLabourShiftDetail(final LabourShiftDetailAPI labourShiftDetailAPI, final LabourShiftDetailAPI.FlashRestClientInterface restclientinterface, final Context context,int userId) {
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        String url = "";
        url = GET_LABOUR_SHIFT_DETAILS_URL+"?userId="+userId;
        JsonBaseRequest getRequest = new JsonBaseRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("responseCheck:", "" + response);
                try {
                    Gson gson = new Gson();
                    //labourShiftDetailAPI=gson.fromJson(response.toString(),LabourShiftDetailAPI.class);
                    List<LabourShiftDetailEntity> labourShiftDetailEntities = gson.fromJson((response.getJSONArray("response")).toString(), new TypeToken<ArrayList<LabourShiftDetailEntity>>() {
                    }.getType());
                    Log.d("", "" + labourShiftDetailEntities);
                    labourShiftDetailAPI.setMessage(response.getString("message"));
                    labourShiftDetailAPI.setStatusCode(response.getInt("statusCode"));
                    labourShiftDetailAPI.setResponse(labourShiftDetailEntities);
                    restclientinterface.onLabourShiftDetail(labourShiftDetailAPI, null);
                } catch (Exception e) {
                    labourShiftDetailAPI.setMessage("Exception!!! something went wrong");
                    labourShiftDetailAPI.setStatusCode(999);
                    restclientinterface.onLabourShiftDetail(labourShiftDetailAPI, new VolleyError());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // AsgardCodeMessageEntity tempAsgardCodeMessageEntity = processVolleyError(error);
               // labourShiftDetailAPI.setStatusCode(tempAsgardCodeMessageEntity.getCode());
                //labourShiftDetailAPI.setMessage(tempAsgardCodeMessageEntity.getMessage());
                restclientinterface.onLabourShiftDetail(labourShiftDetailAPI, new VolleyError());
            }
        }, 30000, 0) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<String,String>();
                params.put("sessionid",UserDetails.getSessionId(context));
                params.put("accesstoken",UserDetails.getSessionToken(context));
                return params;
            }
        };
        queue.add(getRequest);
    }

    public static void cancelRequest(String tag) {
        if (queue != null) {
            queue.cancelAll(tag);
        }
    }

    public static void getLabourForMarkingAttendance(final LabourAttendanceMobileDTOAPI labourAttendanceMobileDTOAPI, final LabourAttendanceMobileDTOAPI.FlashRestClientInterface restclientinterface, final Context context, String searchTag,int userId) {
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        if(searchTag!=null && !searchTag.isEmpty()) {
            cancelRequest(searchTag);
        }
        String url = "";
        int offset = labourAttendanceMobileDTOAPI.getOffset();
        int limit = labourAttendanceMobileDTOAPI.getLimit();
        int labourAgencyId = labourAttendanceMobileDTOAPI.getLabourAgencyId();
        int labourId = labourAttendanceMobileDTOAPI.getLabourId();
        int shiftId=labourAttendanceMobileDTOAPI.getShiftDetailId();
        //url = Constants.GET_LABOUR_FOR_ATTENDANCE_URL+"?userId="+userId;
        url=GET_LABOUR_FOR_ATTENDANCE_URL;
        //url = url + "&labourId=" + labourId + "&labourAgencyId=" + labourAgencyId + "&offset=" + offset + "&limit=" + limit+"&shiftId="+shiftId;
        JsonArrayBaseRequest getRequest = new JsonArrayBaseRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Labours", "" + response);
                try {
                    Gson gson = new Gson();
                    List<LabourAttendanceMobileDTO> labourAttendanceMobileDTOList = gson.fromJson(response.toString(), new TypeToken<List<LabourAttendanceMobileDTO>>() {
                    }.getType());
                    Log.d("", "" + labourAttendanceMobileDTOList);
                    labourAttendanceMobileDTOAPI.setLabourAttendanceMobileDTOList(labourAttendanceMobileDTOList);
                    restclientinterface.onLabourAttendanceMobileDTO(labourAttendanceMobileDTOAPI, null);
                } catch (Exception e) {
                    labourAttendanceMobileDTOAPI.setMessage("Exception!!! something went wrong");
                    labourAttendanceMobileDTOAPI.setCode(999);
                    restclientinterface.onLabourAttendanceMobileDTO(labourAttendanceMobileDTOAPI, new VolleyError());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AsgardCodeMessageEntity tempAsgardCodeMessageEntity = processVolleyError(error);
                labourAttendanceMobileDTOAPI.setCode(tempAsgardCodeMessageEntity.getCode());
                labourAttendanceMobileDTOAPI.setMessage(tempAsgardCodeMessageEntity.getMessage());
                restclientinterface.onLabourAttendanceMobileDTO(labourAttendanceMobileDTOAPI, new VolleyError());
            }
        }, 30000, 0) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<String,String>();
                params.put("sessionid",UserDetails.getSessionId(context));
                params.put("accesstoken",UserDetails.getSessionToken(context));
                return params;
            }
        };
        queue.add(getRequest);
    }

    public static void postLabourAttendance(final LabourAttendanceTrackerEntity labourAttendanceTrackerEntity, final LabourAttendanceTrackerEntity.FlashRestClientInterface restclientinterface, final Context context,int userId) {
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        String url = "";
        url = Constants.MARK_ATTENDANCE_URL+"?userId="+userId;
        JSONObject postParams = labourAttendanceTrackerEntity.getJsonObjectAsParams();
        Log.d("params",postParams.toString());
        //Log.e("labourAttendanceTrackerEntity PARAMS", "" + postParams);
        JsonBaseRequest postRequest = new JsonBaseRequest(Request.Method.POST, url, postParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("reponse", "" + response);
                            Gson gson = new Gson();
                            LabourAttendanceTrackerEntity newLabourAttendanceTrackerEntity = gson.fromJson(response.toString(), LabourAttendanceTrackerEntity.class);
                            labourAttendanceTrackerEntity.setCode(newLabourAttendanceTrackerEntity.getCode());
                            labourAttendanceTrackerEntity.setMessage(newLabourAttendanceTrackerEntity.getMessage());
                            restclientinterface.onLabourAttendanceTracker(labourAttendanceTrackerEntity, null);
                        } catch (Exception e) {
                            Log.e("reponseError", "" + e.toString());
                            restclientinterface.onLabourAttendanceTracker(labourAttendanceTrackerEntity, new VolleyError());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AsgardCodeMessageEntity tempAsgardCodeMessageEntity = processVolleyError(error);
                labourAttendanceTrackerEntity.setCode(tempAsgardCodeMessageEntity.getCode());
                labourAttendanceTrackerEntity.setMessage(tempAsgardCodeMessageEntity.getMessage());
                restclientinterface.onLabourAttendanceTracker(labourAttendanceTrackerEntity, new VolleyError());
            }
        }, 30000, 0) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<String,String>();
                params.put("sessionid",UserDetails.getSessionId(context));
                params.put("accesstoken",UserDetails.getSessionToken(context));
                return params;
            }
        };
        queue.add(postRequest);

    }

    public static void getReportedLabourDetail(final LabourAttendanceMobileDTOAPI labourAttendanceMobileDTOAPI, final LabourAttendanceMobileDTOAPI.FlashRestClientInterface restclientinterface, final Context context,int userId) {
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        String url = "";

        url = Constants.GET_REPORTED_LABOUR_URL+labourAttendanceMobileDTOAPI.getShiftDetailId()+"&userId="+userId;
        JsonArrayBaseRequest getRequest = new JsonArrayBaseRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("", "" + response);
                try {
                    Gson gson = new Gson();
                    List<LabourAttendanceMobileDTO> labourAttendanceMobileDTOList = gson.fromJson(response.toString(), new TypeToken<ArrayList<LabourAttendanceMobileDTO>>() {
                    }.getType());
                    Log.d("", "" + labourAttendanceMobileDTOList);
                    labourAttendanceMobileDTOAPI.setLabourAttendanceMobileDTOList(labourAttendanceMobileDTOList);
                    restclientinterface.onLabourAttendanceMobileDTO(labourAttendanceMobileDTOAPI, null);
                } catch (Exception e) {
                    labourAttendanceMobileDTOAPI.setMessage("Exception!!! something went wrong");
                    labourAttendanceMobileDTOAPI.setCode(999);
                    restclientinterface.onLabourAttendanceMobileDTO(labourAttendanceMobileDTOAPI, new VolleyError());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AsgardCodeMessageEntity tempAsgardCodeMessageEntity = processVolleyError(error);
                labourAttendanceMobileDTOAPI.setCode(tempAsgardCodeMessageEntity.getCode());
                labourAttendanceMobileDTOAPI.setMessage(tempAsgardCodeMessageEntity.getMessage());
                restclientinterface.onLabourAttendanceMobileDTO(labourAttendanceMobileDTOAPI, new VolleyError());
            }
        }, 30000, 0) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<String,String>();
                params.put("sessionid",UserDetails.getSessionId(context));
                params.put("accesstoken",UserDetails.getSessionToken(context));
                return params;
            }
        };
        queue.add(getRequest);
    }

    public static void revokeAttendance(final LabourAttendanceTrackerEntity labourAttendanceTrackerEntity, final LabourAttendanceTrackerEntity.FlashRestClientInterface restClientInterface, final Context context,int userId){
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        final Gson gson=new Gson();
        JSONObject params = labourAttendanceTrackerEntity.getJsonObjectAsParams();
        Log.d("postParams",params.toString());
        JsonBaseRequest postRequest=new JsonBaseRequest(Request.Method.POST, REVOKE_ATTENDANCE_URL+"?userId="+userId, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("respopnse",response.toString());
                try {
                    LabourAttendanceTrackerEntity successEntity=gson.fromJson((response.getJSONObject("response")).toString(),LabourAttendanceTrackerEntity.class);
                    labourAttendanceTrackerEntity.setCode(successEntity.getCode());
                    labourAttendanceTrackerEntity.setMessage(successEntity.getMessage());
                    restClientInterface.onLabourAttendanceTracker(labourAttendanceTrackerEntity,null);
                } catch (JSONException e) {
                    labourAttendanceTrackerEntity.setMessage("Something Went Wrong");
                    labourAttendanceTrackerEntity.setCode(999);
                    restClientInterface.onLabourAttendanceTracker(labourAttendanceTrackerEntity,new VolleyError());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error!=null&&error.networkResponse!=null){
                    Gson gson=new Gson();
                    LabourAttendanceTrackerEntity failureEntity=gson.fromJson(error.networkResponse.toString(),LabourAttendanceTrackerEntity.class);
                    if(failureEntity.getMessage()!=null){
                        labourAttendanceTrackerEntity.setMessage(failureEntity.getMessage());
                        labourAttendanceTrackerEntity.setCode(failureEntity.getCode());
                        restClientInterface.onLabourAttendanceTracker(labourAttendanceTrackerEntity,new VolleyError());
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<String,String>();
                params.put("sessionid",UserDetails.getSessionId(context));
                params.put("accesstoken",UserDetails.getSessionToken(context));
                return params;
            }
        };
        queue.add(postRequest);
    }

    public static void bulkAttendanceMarker(final PostBulkLaboursAttendanceAPIEntity postBulkLaboursAttendanceAPIEntity, final PostBulkLaboursAttendanceAPIEntity.UltronRestClientInterface ultronRestClientInterface, Context context){
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        String URL=MARK_ATTENDANCE_URL;
        Gson gson=new Gson();
        String postParamString=gson.toJson(postBulkLaboursAttendanceAPIEntity,PostBulkLaboursAttendanceAPIEntity.class);
        Log.d("Post params",postParamString);
        try {
            JSONObject postParams = new JSONObject(postParamString);
            JsonBaseRequest postBulkAttendance=new JsonBaseRequest(Request.Method.POST,URL,postParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("ResponseTest",response.toString());
                    ultronRestClientInterface.postBulkAttendance(postBulkLaboursAttendanceAPIEntity,null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ultronRestClientInterface.postBulkAttendance(postBulkLaboursAttendanceAPIEntity,error);
                }
            });
            queue.add(postBulkAttendance);
        }catch (Exception e){
            ultronRestClientInterface.postBulkAttendance(postBulkLaboursAttendanceAPIEntity,new VolleyError());
        }

    }

    public static void getPenaltyApi(final GetPenaltyApiEntity getPenaltyApiEntity, final GetPenaltyApiEntity.UltronRestClientInterface ultronRestClientInterface, Context context){
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        JsonBaseRequest getPenaltyRequest=new JsonBaseRequest(Request.Method.GET, GET_PENALTIES_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                GetPenaltyApiEntity successEntity;
                Log.d("ResponseTestPenalty",response.toString());
                Gson gson=new Gson();
                try {
                    successEntity = gson.fromJson(response.toString(), GetPenaltyApiEntity.class);
                    getPenaltyApiEntity.setResponse(successEntity.getResponse());
                    getPenaltyApiEntity.setStatusCode(successEntity.getStatusCode());
                    getPenaltyApiEntity.setMessage(successEntity.getMessage());
                    ultronRestClientInterface.onGetPenalties(getPenaltyApiEntity,null);
                }catch (Exception e) {
                    ultronRestClientInterface.onGetPenalties(getPenaltyApiEntity,new VolleyError());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Gson gson=new Gson();
                GetPenaltyApiEntity failEntity;
                failEntity=gson.fromJson(error.networkResponse.data.toString(),GetPenaltyApiEntity.class);
                getPenaltyApiEntity.setMessage(failEntity.getMessage());
                getPenaltyApiEntity.setStatusCode(failEntity.getStatusCode());
                ultronRestClientInterface.onGetPenalties(getPenaltyApiEntity,error);
            }
        });
        queue.add(getPenaltyRequest);
    }
    public static void getShiftTimeApi(final LabourTimeEntity entity, final LabourTimeEntity.UltronRestClientInterface ultronRestClientInterface, Context context){
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        String URL=GET_LABOUR_TIME_ABSOLUTE_URL+"?labourId="+entity.getLabourId();
        JsonBaseRequest getTimeRequest=new JsonBaseRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TimeResponseTest",response.toString());
                Gson gson=new Gson();
                try {
                    LabourTimeEntity successEntity = gson.fromJson(response.toString(), LabourTimeEntity.class);
                    entity.setResponse(successEntity.getResponse());
                    entity.setStatusCode(successEntity.getStatusCode());
                    entity.setMessage(successEntity.getMessage());
                    ultronRestClientInterface.onGetTimeEntity(entity, null);
                }catch (Exception e){
                    ultronRestClientInterface.onGetTimeEntity(entity,new VolleyError());
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LabourTimeEntity failEntity=(new Gson()).fromJson(error.networkResponse.data.toString(),LabourTimeEntity.class);
                entity.setStatusCode(failEntity.getStatusCode());
                entity.setMessage(failEntity.getMessage());
                ultronRestClientInterface.onGetTimeEntity(entity,error);
            }
        });
        queue.add(getTimeRequest);
    }
}
