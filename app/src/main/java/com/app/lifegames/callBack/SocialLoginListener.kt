package com.app.lifegames.callBack

interface SocialLoginListener {
    abstract fun onLoginStart()

   // abstract fun onLoginSuccess(userData: FacebookModel)

    abstract fun onLoginFailure(errorCode: Int)

    abstract fun onFacbookLogin()

    abstract fun onGoogleLogin()
}


