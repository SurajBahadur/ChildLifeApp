package com.app.lifegames.ui.signUp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.lifegames.R
import com.app.lifegames.base_classes.BaseFragment
import com.app.lifegames.ui.login.LoginFragment
import com.app.lifegames.ui.login.apiHit.LoginViewModel
import com.app.lifegames.ui.signUp.apiHit.SignUpViewModel
import com.app.lifegames.utils.Utils.isEmailValid
import com.app.lifegames.utils.security.ApiFailureTypes
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_premium.*
import kotlinx.android.synthetic.main.fragment_singup.*
import kotlinx.android.synthetic.main.include_login.*
import kotlinx.android.synthetic.main.include_sign_up.*

class FragmentSignUp : BaseFragment() {

    private var mSignUpViewModel: SignUpViewModel? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSignUpViewModel= ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        click()


    }

    private fun click() {
        btsignUp.setOnClickListener {

            if(etemails_.text.toString().isEmpty() || TextUtils.isEmpty(etemails_.text.toString()) ||
                    etpassword_.text.toString().isEmpty() || TextUtils.isEmpty(etpassword_.text.toString())
                    || TextUtils.isEmpty(etname_.text.toString()) || TextUtils.isEmpty(etfullname_.text.toString()))
            {

                showMessage("Please fill email address.")

            }
            else if(!isEmailValid(etemails_.text.toString())) {

                etemails_.setError("Invalid Email Address")

            }
            else
            {
                Log.d("Email and pasword"," "+etemails_.text.toString()+" "+etpassword_.text.toString());
                mSignUpViewModel!!.authenticate(etname_.text.toString(),etfullname_.text.toString(),
                        etemails_.text.toString(),etpassword_.text.toString(),"",deviceToken())
                observer()
            }

        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_singup,container,false)
    }
    private fun observer() {
        mSignUpViewModel?.response?.observe(this, Observer {
            it?.let {

                if(it.status==1)
                {
                    showMessage(it.message.toString())


                }


            }
        })

        mSignUpViewModel?.apiError?.observe(this, Observer {
            it?.let {
                showSnackBar(it)
            }
        })

        mSignUpViewModel?.isLoading?.observe(this, Observer {
            if(it==true)
            {
                sup_loading_lay.visibility= View.VISIBLE
            }
            else
            {
                sup_loading_lay.visibility= View.GONE
            }
            // it?.let { showLoading(it) }
        })

        mSignUpViewModel?.onFailure?.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                showSnackBar(ApiFailureTypes().getFailureMessage(it))
            }
        })
    }

}