package com.app.lifegames.utils.social;

import android.content.Context;

/**
 * Abstract class for get information,
 * that necessary for login from social networks.
 */
public abstract class SocialLoginManager {
    public static final int ERROR_GENERAL = 1;
    public static final int ERROR_NO_EMAIL = 2;
    public static final int ERROR_CANCELED = 3;
    public static final int ERROR_FB = 4;
    public static final int ERROR_FB_AUTH = 5;

    protected Context mContext;
    private SocialLoginListener mListener;

    public SocialLoginManager(Context context, SocialLoginListener listener) {
        mContext = context;
        mListener = listener;
    }

    public abstract void login();

    protected void callOnSuccess(FacebookModel userData) {
        if (mListener != null) {
            mListener.onLoginSuccess(userData);
        }
    }

    protected void callOnFailure(int errorCode) {
        if (mListener != null) {
            mListener.onLoginFailure(errorCode);
        }
    }

    protected void callOnStart() {
        if (mListener != null) {
            mListener.onLoginStart();
        }
    }

    public interface SocialLoginListener {
        void onLoginStart();

        void onLoginSuccess(FacebookModel userData);

        void onLoginFailure(int errorCode);

        void onFacbookLogin();
        void onTwitterLogin();

         void  onPyamentClick();

        void onGoogleLogin();
    }

}
