package com.app.lifegames.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.lifegames.activity.ActivityMainNew
import com.app.lifegames.base_classes.BaseFragment
import com.app.lifegames.ui.login.apiHit.LoginViewModel
import com.app.lifegames.ui.signUp.FragmentSignUp
import com.app.lifegames.utils.security.ApiFailureTypes
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.include_login.*
import java.util.regex.Pattern
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import android.widget.Toast
import com.app.lifegames.R
import com.app.lifegames.ui.payment.PaymentFragment
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.PreferenceClass
import com.app.lifegames.utils.Utils
import com.app.lifegames.utils.social.SocialLoginManager
import android.support.v4.app.FragmentManager
import com.app.lifegames.ui.premimum.FragmentPremimum


class LoginFragment : BaseFragment() {

    var mListeners: SocialLoginManager.SocialLoginListener? = null
    //twitter auth client required for custom login
    private var client: TwitterAuthClient? = null
    private var mloginViewModel: LoginViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.printFacebookHashKey(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is ActivityMainNew)
            mListeners = activity as ActivityMainNew
        Utils.printFacebookHashKey(context)
        mloginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        //NOTE : calling default twitter login in OnCreate/OnResume to initialize twitter callback
        click()


    }



    private fun click() {

        btFacebook.setOnClickListener {
            mListeners!!.onFacbookLogin()

        }

        btGoogle.setOnClickListener {
            mListeners!!.onGoogleLogin()
        }


        bttwitter.setOnClickListener {
            mListeners!!.onTwitterLogin()

        }
        signup_textview2.setOnClickListener {
            addFragment(FragmentSignUp(), true, R.id.container_3)
        }




        btDoneLogin.setOnClickListener {

            if (etEmails.text.toString().isEmpty() || TextUtils.isEmpty(etEmails.text.toString()) ||
                    etpassword.text.toString().isEmpty() || TextUtils.isEmpty(etpassword.text.toString())) {

                showMessage("Please fill email address.")

            } else if (!isEmailValid(etEmails.text.toString())) {

                etEmails.setError("Invalid Email Address")

            } else {
                Log.d("Email and pasword", " " + etEmails.text.toString() + " " + etpassword.text.toString());
                mloginViewModel!!.authenticate(etEmails.text.toString(), etpassword.text.toString(),deviceToken())
                observer()
            }

        }
    }

    private fun observer() {
        mloginViewModel?.response?.observe(this, Observer {
            it?.let {

                if (it.status == 1) {

                    showMessage(it.message.toString())
                    PreferenceClass.setStringPreference(activity, Constants.USER_ID,it.data!!.userId)
                    PreferenceClass.setStringPreference(activity, Constants.LOGIN_STATUS,"1")
                    PreferenceClass.setStringPreference(activity, Constants.USER_TYPE,it.data.type)
                    PreferenceClass.setStringPreference(activity, Constants.SUCCESS,"1")
             /*       var intent: Intent = Intent(activity, ActivityMainNew::class.java)
                    startActivity(intent)
                    activity?.finish()*/
                    activity?.supportFragmentManager!!.popBackStackImmediate()
              //      addFragment(FragmentPremimum(),false, R.id.container_3)

                }


            }
        })

        mloginViewModel?.apiError?.observe(this, Observer {
            it?.let {
                showSnackBar(it)
            }
        })

        mloginViewModel?.isLoading?.observe(this, Observer {
            if (it == true) {
                login_loading_lay.visibility = View.VISIBLE
            } else {
                login_loading_lay.visibility = View.GONE
            }
            // it?.let { showLoading(it) }
        })

        mloginViewModel?.onFailure?.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                showSnackBar(ApiFailureTypes().getFailureMessage(it))
            }
        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    fun isEmailValid(email: String): Boolean {



        var isValid = false

        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"

        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }




}