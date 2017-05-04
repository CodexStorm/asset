package com.ninja.ultron.restclient;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.ninja.ultron.constant.Constants;
import com.ninja.ultron.entity.LoginEntity;

import org.json.JSONObject;

public class RestClientImplementation {
    static RequestQueue queue;
    private static final String BASE_URL = Constants.BASE_URL;

    private static String getAbsoluteUrl(String relativeUrl, final Context checkContext) {
        /*if (UserDetails.isQAMode(checkContext)) {
            String qaUrl = UserDetails.getQAServerUrl(checkContext) + relativeUrl;
            return qaUrl;
        } else {
            if (UserDetails.getAppBaseUrl(checkContext) != null && UserDetails.getAppBaseUrl(checkContext).length() > 0) {
                return UserDetails.getAppBaseUrl(checkContext) + relativeUrl;
            } else {
                return BASE_URL + relativeUrl;
            }

        }
*/
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

}
