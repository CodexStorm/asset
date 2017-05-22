package com.ninja.ultron.restclient;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.ninja.ultron.constant.Constants;
import com.ninja.ultron.entity.AssetDetailsMiniEntity;
import com.ninja.ultron.entity.AssetMiniEntity;
import com.ninja.ultron.entity.InitApiEntity;
import com.ninja.ultron.entity.LoginEntity;
import com.ninja.ultron.entity.PendingRequestMiniEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.functions.UserDetails;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RestClientImplementation {
    static RequestQueue queue;
    private static final String BASE_URL = Constants.BASE_URL;

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void userLogin(final LoginEntity loginEntity, final LoginEntity.RestClientInterface restclientinterface, final Context context) {
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        //String url = getAbsoluteUrl("/user/login/app", context);
        String url = Constants.BASE_URL + "/user/login/app";
        JSONObject postParams = loginEntity.getJsonObjectAsParams();
        Log.e("login_params", "" + postParams);
        Log.e("login_url", "" + url);
        JsonBaseRequest postRequest = new JsonBaseRequest(Request.Method.POST, url, postParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("reponse", "" + response);
                            Gson gson = new Gson();
                            LoginEntity newLoginSuccessEntity = gson.fromJson(response.toString(), LoginEntity.class);
                            loginEntity.setAsgardUser(newLoginSuccessEntity.getAsgardUser());
                            loginEntity.setMessage("Success");
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

    public static void assetListApi(final AssetMiniEntity assetMiniEntity, final AssetMiniEntity.UltronRestClientInterface restClientInterface, final Context context){
        queue = VolleySingleton.getInstance(context).getRequestQueue();
        JsonBaseRequest getRequest = new JsonBaseRequest(Request.Method.GET, Constants.ASSET_LIST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
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
        },30000, 0);
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
        },30000, 0);
        queue.add(getRequest);
    }

    public static void pendingRequestsApi(final PendingRequestMiniEntity pendingRequestMiniEntity,final PendingRequestMiniEntity.UltronRestClientInterface ultronRestClientInterface,final Context context)
    {
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        JsonBaseRequest getRequest=new JsonBaseRequest(Request.Method.GET, Constants.PENDING_REQUESTS_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    Gson gson=new Gson();
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
        },3000,0);
        queue.add(getRequest);
    }

}
