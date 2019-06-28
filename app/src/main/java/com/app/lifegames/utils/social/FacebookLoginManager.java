package com.app.lifegames.utils.social;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

 //Facebook login Manager class handle facebook login


public class FacebookLoginManager extends SocialLoginManager {
    public static final int FB_REQUEST_CODE = 1694;
    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String PICTURE = "picture";
    public static final String COVER = "cover";
    public static final String ACCESS_TOKEN = "access_token";
    private static final String FIELDS = "fields";
    Context context;
    private CallbackManager mCallbackManager;


    public FacebookLoginManager(Context context, SocialLoginListener listener) {

        super(context, listener);
        this.context = context;
    }


    @Override
    public void login() {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(getClass().getSimpleName(), "facebook onLoginSuccess");

                final AccessToken token = loginResult.getAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.d(getClass().getSimpleName(), "user JSON: " + object.toString());

                                try {
                                    String email = "";
                                    if (object.has(EMAIL)) {
                                        email = object.getString(EMAIL);
                                    }
                                    String firstName = object.getString(FIRST_NAME);
                                    String lastName = object.getString(LAST_NAME);

                                    String id = object.getString(ID);
                                    String avatarUrl = "https://graph.facebook.com/" + id + "/picture?type=large";
                                    String coverUrl = formatCoverUrl(object);
                                    FacebookModel model = new FacebookModel();
                                    model.setId(id);
                                    model.setEmail(email);
                                    model.setFirstName(firstName);
                                    model.setLastName(lastName);
                                    model.setAvatarUrl(avatarUrl);
                                    model.setCoverUrl(coverUrl);
                                    model.setAccessToken(token);
                                    callOnSuccess(model);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    callOnFailure(SocialLoginManager.ERROR_GENERAL);
                                }
                            }
                        });
                String fieldsValue = ID + "," + EMAIL + "," + FIRST_NAME + "," + LAST_NAME + "," + COVER + "," + "picture.width(300).height(300)";
                Bundle params = new Bundle();
                params.putString(FIELDS, fieldsValue);
                request.setParameters(params);
                request.executeAsync();

                callOnStart();
            }

            @Override
            public void onCancel() {
                callOnFailure(SocialLoginManager.ERROR_CANCELED);
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(getClass().getSimpleName(), "facebook onError");
                error.printStackTrace();
                if (error instanceof FacebookAuthorizationException) {
                    //should never happen, but in this case user will need to push Login button again
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                        login();
                    }
                } else {
                    callOnFailure(SocialLoginManager.ERROR_FB);
                }
            }
        });
        LoginManager.getInstance().logInWithReadPermissions((Activity) mContext, Arrays.asList("email", "public_profile"));
    }

    private String formatAvatarUrl(JSONObject object) throws JSONException {
//        return "http://graph.facebook.com/" + id + "/picture?width=250&height=250";
        JSONObject picture = object.getJSONObject("picture");
        JSONObject data = picture.getJSONObject("data");
        return data.getString("url");
    }

    private String formatCoverUrl(JSONObject source) throws JSONException {
        if (source.has(COVER)) {
            JSONObject coverObject = source.getJSONObject(COVER);
            if (coverObject != null) {
                return coverObject.getString("source");
            }
        }

        return null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
