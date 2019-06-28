package com.app.lifegames.ui.payment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.app.lifegames.R
import com.app.lifegames.activity.ActivityMainNew
import com.app.lifegames.base_classes.BaseFragment
import com.app.lifegames.ui.login.LoginFragment
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.PreferenceClass
import com.braintreepayments.api.Card
import com.braintreepayments.api.UnionPay
import com.braintreepayments.api.models.CardBuilder
import com.braintreepayments.api.models.UnionPayCardBuilder
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_premium.*
import android.content.Intent.getIntent
import com.braintreepayments.api.dropin.DropInRequest
import com.braintreepayments.api.dropin.DropInActivity
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import com.app.lifegames.ui.login.apiHit.LoginViewModel
import com.app.lifegames.utils.security.ApiFailureTypes
import com.app.lifegames.utils.social.SocialLoginManager
import com.braintreepayments.api.dropin.DropInResult
import kotlinx.android.synthetic.main.fragment_login.*


class PaymentFragment : BaseFragment() {

    var click1:Boolean=false
    var click2:Boolean=false
    var click3:Boolean=false

    var mListeners: SocialLoginManager.SocialLoginListener? = null
    private var mloginViewModel: LoginViewModel? = null
    var token:String?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is ActivityMainNew)
            mListeners = activity as ActivityMainNew

        mloginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        val userID: String = PreferenceClass.getStringPreferences(this.activity, Constants.USER_ID)

        mloginViewModel?.getToken(userID)
        observer()
        val res = getResources();
        val text = String.format(res.getString(R.string.pay_heading), PreferenceClass.getStringPreferences(activity,Constants.PAYMENT_PLAN_NAME),
                PreferenceClass.getStringPreferences(activity,Constants.PAYMENT_AMOUNT));
        payment_heading.setText(text)


        click()

    }

    private fun observer() {
        mloginViewModel?.tokenResponse?.observe(this, Observer {
            it?.let {

                if (it.status == 1) {
                  token=it.token
                }


            }
        })

        mloginViewModel?.apiError?.observe(this, Observer {
            it?.let {
                showSnackBar(it)
            }
        })

        mloginViewModel?.isLoading?.observe(this, Observer {

        })

        mloginViewModel?.onFailure?.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                showSnackBar(ApiFailureTypes().getFailureMessage(it))
            }
        })
    }

    private fun click() {
        btn_pay_by_card.setOnClickListener {
            mListeners!!.onPyamentClick()
        }
        btn_paypal.setOnClickListener {
            mListeners!!.onPyamentClick()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_payment,container,false)
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                val result = data!!.getParcelableExtra<DropInResult>(DropInResult.EXTRA_DROP_IN_RESULT)
                  var nonce=result.getPaymentMethodNonce()!!.getNonce();
                //send to your server
                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                val error = data!!.getSerializableExtra(DropInActivity.EXTRA_ERROR) as Exception
            }
        }
    }


}