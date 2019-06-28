package com.app.lifegames.ui.premimum

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails
import com.app.lifegames.R
import com.app.lifegames.activity.ActivityMainNew
import com.app.lifegames.base_classes.BaseFragment
import com.app.lifegames.ui.login.LoginFragment
import com.app.lifegames.ui.payment.PaymentFragment
import com.app.lifegames.ui.payment.apiHit.PaymentViewModel
import com.app.lifegames.ui.plannerActivitiesList.apiHit.PlannerActivityViewModel
import com.app.lifegames.ui.premimum.apiHit.AvaliablityViewModel
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.PreferenceClass
import com.app.lifegames.utils.security.ApiFailureTypes
import kotlinx.android.synthetic.main.fragment_planner_activites.*
import kotlinx.android.synthetic.main.fragment_premium.*
import java.text.SimpleDateFormat
import java.util.*

class FragmentPremimum : BaseFragment(), BillingProcessor.IBillingHandler {
    override fun onBillingInitialized() {

    }

    override fun onPurchaseHistoryRestored() {

    }

    override fun onProductPurchased(productId: String, details: TransactionDetails?) {
        val value = getCurrentDate()
        val amount = PreferenceClass.getStringPreferences(activity, Constants.PAYMENT_AMOUNT)
        val age = PreferenceClass.getStringPreferences(activity, Constants.AGE)
        val cat = PreferenceClass.getStringPreferences(activity, Constants.CATEGORY)
        val planID = PreferenceClass.getStringPreferences(activity, Constants.PAYMENT_PLAN)
        mlPaymentModel!!.FinalPaymentResponse(userID!!, "", amount, planID, value, "1", age, cat)
        attachobesrver()
    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {

        Log.d("Error"," "+error.toString())

    }


    lateinit var bp: BillingProcessor
    var click1:Boolean=false
    var click2:Boolean=false
    var click3:Boolean=false
    var click4:Boolean=false
    private var mViewListModel: AvaliablityViewModel? = null
    var userID:String?=null
    var age:String?=null
    var category:String?=null
    private var mlPaymentModel: PaymentViewModel? = null
    var productId:String?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bp = BillingProcessor(activity, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuUTNKb6nJRJNCtZVjUGTbJNJlLoASieDvQsP0yJSGMZ65GCHer+ZqIwBBgLRE1phhIAxJI1XaucYbBdZv6MBBTt1C1ZubbF1GMCaCR9IANBY+5T22gxPBh5VGbPSGwnRaOuidMTBSg/0OayBbR/Z5t9vectyTmma4LNBMLQ238XI2fgxIFPOK1LF2J+EDRDfBkaGrY43sSs+VQMtm08bXSbba0YbdnAI+BHAXq3CFVKRUrGuCKNuBbPL3Z2g/60LLHcG59PK349NYfgwexe+Rejr5kdwqoLA35xddEhNIgm+YKgjEqh39eKTG0dse3I3LLnj5jINShUdnOFfs/ie5QIDAQAB", this)
        bp.initialize()

        mlPaymentModel = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
        userID = PreferenceClass.getStringPreferences(this.activity, Constants.USER_ID)
        age= PreferenceClass.getStringPreferences(this.activity, Constants.AGE)
        category = PreferenceClass.getStringPreferences(this.activity, Constants.CATEGORY)
        mViewListModel= ViewModelProviders.of(this).get(AvaliablityViewModel::class.java)
        val res = getResources();
        val text = String.format(res.getString(R.string.complete_option_heading), PreferenceClass.getStringPreferences(activity,Constants.AGE));
        val text2 = String.format(res.getString(R.string.complete_option_detail), PreferenceClass.getStringPreferences(activity,Constants.AGE));

        tv_complete_pckg.setText(text)
        tv_compelte_package_detail.setText(text2)
        attachobesrver()
        click2=true;
        productId="monthly_subscription_product";
        PreferenceClass.setStringPreference(activity, Constants.PAYMENT_PLAN,"2")
        PreferenceClass.setStringPreference(activity, Constants.PAYMENT_AMOUNT,"4.99")
        PreferenceClass.setStringPreference(activity, Constants.PAYMENT_PLAN_NAME,tv_subscribe.text.toString())
        selectPlane(relativeLayout3)
        relativeLayout2.setOnClickListener {
            if(click1==false)
            {
                purchase_btn.isEnabled=false
                productId="12_activity_add_on_pack";
                selectPlane(relativeLayout2)
                unSelectPlane(relativeLayout3)
                unSelectPlane(relativeLayout4)
                unSelectPlane(relativeLayout5)
                PreferenceClass.setStringPreference(activity, Constants.PAYMENT_AMOUNT,"6.99")
                PreferenceClass.setStringPreference(activity, Constants.PAYMENT_PLAN,"1")
                PreferenceClass.setStringPreference(activity, Constants.PAYMENT_PLAN_NAME,tv_activity.text.toString())
                click1=true;
                click2=false;
                click3=false
                click4=false
                mViewListModel!!.authenticate(userID!!,category!!,age!!)

            }
            else
            {
                purchase_btn.isEnabled=true
                unSelectPlane(relativeLayout2)
                click1=false;
            }

        }

        relativeLayout3.setOnClickListener {
            if(click2==false)
            {
                productId="monthly_subscription_product";

                selectPlane(relativeLayout3)
                unSelectPlane(relativeLayout2)
                unSelectPlane(relativeLayout4)
                unSelectPlane(relativeLayout5)
                click1=false;
                click2=true;
                click3=false
                click4=false;
                PreferenceClass.setStringPreference(activity, Constants.PAYMENT_PLAN,"2")
                PreferenceClass.setStringPreference(activity, Constants.PAYMENT_AMOUNT,"4.99")
                PreferenceClass.setStringPreference(activity, Constants.PAYMENT_PLAN_NAME,tv_subscribe.text.toString())

            }
            else
            {
                unSelectPlane(relativeLayout3)
                click2=false
            }

        }

        relativeLayout4.setOnClickListener {
            if(click3==false)
            {
                productId="ultimate_package"
                selectPlane(relativeLayout4)
                unSelectPlane(relativeLayout2)
                unSelectPlane(relativeLayout3)
                unSelectPlane(relativeLayout5)
                click1=false;
                click2=false;
                click3=true
                click4=false;
                PreferenceClass.setStringPreference(activity, Constants.PAYMENT_PLAN,"3")
                PreferenceClass.setStringPreference(activity, Constants.PAYMENT_AMOUNT,"74.99")
                PreferenceClass.setStringPreference(activity, Constants.PAYMENT_PLAN_NAME,tv_ultimate_pckg.text.toString())

            }
            else
            {

                unSelectPlane(relativeLayout4)
                click3=false
            }

        }

        relativeLayout5.setOnClickListener {
            if(click4==false)
            {
                productId="complete_age_selection"

                selectPlane(relativeLayout5)
                unSelectPlane(relativeLayout2)
                unSelectPlane(relativeLayout3)
                unSelectPlane(relativeLayout4)
                click1=false;
                click2=false;
                click3=false;
                click4=true
                PreferenceClass.setStringPreference(activity, Constants.PAYMENT_PLAN,"4")
                PreferenceClass.setStringPreference(activity, Constants.PAYMENT_AMOUNT,"29.99")
                PreferenceClass.setStringPreference(activity, Constants.PAYMENT_PLAN_NAME,tv_complete_pckg.text.toString())

            }
            else
            {

                unSelectPlane(relativeLayout5)
                click4=false
            }

        }

        purchase_btn.setOnClickListener {

            if(click1==false && click2==false && click3==false && click4==false)
            {
                Toast.makeText(activity,"Please select an option to proceed.",Toast.LENGTH_SHORT).show();
            }
            else
            {
                val type: String = PreferenceClass.getStringPreferences(this.activity, Constants.LOGIN_STATUS)
                if(type.equals("1"))
                {
                    bp.subscribe(activity, productId)
                   // addFragment(PaymentFragment(),true, R.id.container_3)
                }
                else
                {

                    addFragment(LoginFragment(),true, R.id.container_3)

                }
            }


        }


    }

    private fun attachobesrver() {
        mlPaymentModel?.response?.observe(this, Observer {
            it?.let {

                if (it.status == 1) {
                  //  Toast.makeText(activity, it.message.toString(), Toast.LENGTH_SHORT).show()
                    var intent: Intent = Intent(activity, ActivityMainNew::class.java)
                    startActivity(intent)
                    activity!!.finish()

                }


            }
        })

        mlPaymentModel?.apiError?.observe(this, Observer {
            it?.let {
                Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        mlPaymentModel?.isLoading?.observe(this, Observer {

            // it?.let { showLoading(it) }
        })

        mlPaymentModel?.onFailure?.observe(this, android.arch.lifecycle.Observer {
            it?.let {

                showSnackBar(ApiFailureTypes().getFailureMessage(it))
            }
        })

        mViewListModel?.response?.observe(this, Observer {
            it?.let {
                if(it.status==1)
                {
                  purchase_btn.isEnabled=true
                }
            }
        })

        mViewListModel?.apiError?.observe(this, Observer {
            it?.let {
                showSnackBar(it)
            }
        })

        mViewListModel?.isLoading?.observe(this, Observer {

            // it?.let { showLoading(it) }
        })

        mViewListModel?.onFailure?.observe(this, android.arch.lifecycle.Observer {
            it?.let {

                showSnackBar(ApiFailureTypes().getFailureMessage(it))
            }
        })
    }

    private fun unSelectPlane(relLay: RelativeLayout?) {
        relLay!!.setBackgroundResource(R.drawable.premium_feilds_bg)
    }

    private fun selectPlane(relLay: RelativeLayout?) {
        relLay!!.setBackgroundResource(R.drawable.premium_selected_feilds_bg)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_premium,container,false)
    }


}