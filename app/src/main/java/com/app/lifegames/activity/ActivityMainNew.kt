package com.app.lifegames.activity

import android.app.Activity
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import com.app.lifegames.R
import com.app.lifegames.alarmServices.AlarmReceiver
import com.app.lifegames.ui.activitiesList.ActivitiyListFragment
import com.app.lifegames.ui.doneActivitiesList.DoneActivitiesFragment
import com.app.lifegames.ui.favouriteActivitiesList.FavouriteActivitiesFragment
import com.app.lifegames.ui.login.apiHit.LoginViewModel
import com.app.lifegames.ui.newActivity.FragmentNewActivity
import com.app.lifegames.ui.payment.PaymentFragment
import com.app.lifegames.ui.payment.apiHit.PaymentViewModel
import com.app.lifegames.ui.plannerActivitiesList.PlannerActivitiesFragment
import com.app.lifegames.ui.premimum.FragmentPremimum
import com.app.lifegames.ui.selectAge.SelectionFragment
import com.app.lifegames.ui.selectAge.apiHit.ActivityListViewModel
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.PreferenceClass
import com.app.lifegames.utils.Utils
import com.app.lifegames.utils.security.ApiFailureTypes
import com.app.lifegames.utils.social.FacebookLoginManager
import com.app.lifegames.utils.social.FacebookModel
import com.app.lifegames.utils.social.SocialLoginManager
import com.braintreepayments.api.dropin.DropInActivity
import com.braintreepayments.api.dropin.DropInRequest
import com.braintreepayments.api.dropin.DropInResult
import com.facebook.internal.CallbackManagerImpl
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.messaging.RemoteMessage
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_logout.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.include_login.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ActivityMainNew : BaseActivity(), SocialLoginManager.SocialLoginListener, GoogleApiClient.OnConnectionFailedListener {


    private val RC_SIGN_IN = 7
    private var mViewListModel: ActivityListViewModel? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    var mFacebookLoginManager: FacebookLoginManager? = null
    private var client: TwitterAuthClient? = null
    private var mloginViewModel: LoginViewModel? = null
    private var mlPaymentModel: PaymentViewModel? = null
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    var click: Boolean = false
    var click2: Boolean = false
    var click3: Boolean = false
    var click4: Boolean = false
    var token: String? = null
    var userID: String? = null
    private var alreadyClicked: Int = -1;

    override fun getLayoutId(): Int {
        return R.layout.activity_main;
    }
/*    override fun getRootLayout(): View {
        return container!!;

    }*/
/**/

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onEvent(data: RemoteMessage) {
        selecteTab(premimum_tab)
        unSelectTab(fav_tab)
        unSelectTextTab(new_tab)
        unSelectTextTab(done_tab)
        replaceFragment(PlannerActivitiesFragment());

        click = true
        click2 = false
        click3 = false
        click4 = false
    }

    override fun onLayoutCreated() {
        val intent = getIntent();
        EventBus.getDefault().register(this);

        //initialize twitter auth client
        client = TwitterAuthClient()
        initGooglePlus()
        userID = PreferenceClass.getStringPreferences(this, Constants.USER_ID)
        mViewListModel= ViewModelProviders.of(this).get(ActivityListViewModel::class.java)
        mloginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        mlPaymentModel = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
        mloginViewModel?.getToken(userID!!)

        var data=  intent.extras
       /* val success: String? = PreferenceClass.getStringPreferences(this, Constants.SUCCESS)
        if(success.equals("1"))
        {
            addFragment(FragmentPremimum(),false, R.id.container_3)
        }
        else
        {
            replaceFragment(ActivitiyListFragment());
        }*/


          if(intent!=null)
          {

              if(intent.getBooleanExtra("Notification",false)==true)
              {
                  selecteTab(premimum_tab)
                  unSelectTab(fav_tab)
                  unSelectTextTab(new_tab)
                  unSelectTextTab(done_tab)
                  replaceFragment(PlannerActivitiesFragment());

                  click = true
                  click2 = false
                  click3 = false
                  click4 = false
              }
              else
              {
                  replaceFragment(ActivitiyListFragment());
              }

          }
        else
          {
              replaceFragment(ActivitiyListFragment());
          }






        observer()

        premimum_tab.setOnClickListener {

            if (click == false) {

                selecteTab(premimum_tab)
                unSelectTab(fav_tab)
                unSelectTextTab(new_tab)
                unSelectTextTab(done_tab)
                click = true
                click2 = false
                click3 = false
                click4 = false

            } else {
                unSelectTab(premimum_tab)

                click = false
            }

            val userType: String? = PreferenceClass.getStringPreferences(this, Constants.USER_TYPE)
            if (userType.equals("1")) {
                val loginStatus: String? = PreferenceClass.getStringPreferences(this, Constants.LOGIN_STATUS)
                if(loginStatus.equals("1"))
                {
                    replaceFragment(PlannerActivitiesFragment());

                }
                else
                {
                    replaceFragment(FragmentPremimum());
                }

            }
            else {
                val loginStatus: String? = PreferenceClass.getStringPreferences(this, Constants.LOGIN_STATUS)
                if(loginStatus.equals("1"))
                {
                    replaceFragment(PlannerActivitiesFragment());

                }
                else
                {
                    replaceFragment(FragmentPremimum());
                }

            }

        }
        new_tab.setOnClickListener {

            replaceFragment(ActivitiyListFragment());

            if (click2 == false) {
                SelectTextTab(new_tab)
                unSelectTab(fav_tab)
                unSelectTab(premimum_tab)
                unSelectTextTab(done_tab)

                click = false
                click2 = true
                click3 = false
                click4 = false

            } else {
                unSelectTextTab(new_tab)
                click2 = false
            }

        }

        done_tab.setOnClickListener {
            if (click3 == false) {


                SelectTextTab(done_tab)
                unSelectTab(fav_tab)
                unSelectTab(premimum_tab)
                unSelectTextTab(new_tab)
                click = false
                click2 = false
                click3 = true
                click4 = false

            } else {
                unSelectTextTab(done_tab)

                click3 = false
            }
            replaceFragment(DoneActivitiesFragment());
        }

        fav_tab.setOnClickListener {
            replaceFragment(FavouriteActivitiesFragment());
            if (click4 == false) {

                selecteTab(fav_tab)
                unSelectTab(premimum_tab)
                unSelectTextTab(new_tab)
                unSelectTextTab(done_tab)
                click = false
                click2 = false
                click3 = false
                click4 = true

            } else {
                unSelectTab(fav_tab)

                click4 = false
            }

        }
        menuBtn.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this, menuBtn)

            val login=PreferenceClass.getStringPreferences(this, Constants.LOGIN_STATUS)

            if(login.equals("1"))
            {
                popupMenu.menuInflater.inflate(R.menu.activity_logout_menu, popupMenu.menu)
            }
            else
            {
                popupMenu.menuInflater.inflate(R.menu.activity_main_drawer, popupMenu.menu)
            }


            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.nav_item_tabs -> {
                        var intent: Intent = Intent(this, ActivityMainNew::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.nav_pro_item_home -> {
                        addFragment(SelectionFragment(), true, R.id.container_3)
                    }
                    R.id.nav_item_one -> {
                        addFragment(FragmentPremimum(), true, R.id.container_3)
                    }
                    R.id.nav_logout -> {
                        showLogout()

                    }


                }
                true
            })
            popupMenu.show()
        }


    }

    private fun showLogout() {
            val logoutDialog = Dialog(this);
        logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //reviewDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        logoutDialog.setContentView(R.layout.dialog_logout);
        logoutDialog.show();

            val tvYesBtn = logoutDialog.findViewById(R.id.tv_yes_btn) as TextView
            val tvNoBtn = logoutDialog.findViewById(R.id.tv_no_btn) as TextView
        tvYesBtn.setOnClickListener {
                PreferenceClass.setStringPreference(this,Constants.LOGIN_STATUS,"0")
                finish()
                 mViewListModel!!.authenticateupdateLogout(userID!!)
                logoutDialog.dismiss()
            }
        tvNoBtn.setOnClickListener {
            logoutDialog.dismiss()
        }
    }

    private fun unSelectTextTab(textTab: TextView?) {
        textTab!!.setBackgroundResource(R.drawable.tab_bg)
    }

    private fun SelectTextTab(textTab: TextView?) {
        textTab!!.setBackgroundResource(R.drawable.border_lines_bg)
    }

    private fun unSelectTab(tab: LinearLayout?) {
        tab!!.setBackgroundResource(R.drawable.tab_bg)
    }

    private fun selecteTab(tab: LinearLayout?) {
        tab!!.setBackgroundResource(R.drawable.border_lines_bg)
    }


    override fun onBackPressed() {

       /* var intent: Intent = Intent(this, MainActivity::class.java)
        intent.putExtra("Back",true)
        startActivity(intent)
*/
        if (supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else if (mDoubleBackToExitPressedOnce) {
            this.mDoubleBackToExitPressedOnce = true
            Utils.showToast(this, resources.getString(R.string.home_back_button))
            Handler().postDelayed({mDoubleBackToExitPressedOnce = false }, 1000)
        } else {
            super.onBackPressed()
            return
        }
    }

    override fun onGoogleLogin() {
        sign_in_button.performClick()
        signIn()
    }

    private fun signIn() {
        val intent: Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, RC_SIGN_IN)
    }

    fun initGooglePlus() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                /* .requestIdToken(resources.getString(R.string.gmail_login_key))
                .requestScopes(Scope(Scopes.PLUS_LOGIN))
                .requestScopes(Scope(Scopes.PLUS_ME))*/
                .requestEmail()
                //  .requestProfile()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {

            if (mFacebookLoginManager != null) {
                mFacebookLoginManager?.onActivityResult(requestCode, resultCode, data)
            }
        }
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
        // Pass the activity result to the twitterAuthClient.
        if (client != null)
            client!!.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result to the login button.
        default_twitter_login_button.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                val result = data!!.getParcelableExtra<DropInResult>(DropInResult.EXTRA_DROP_IN_RESULT)
                var nonce = result.getPaymentMethodNonce()!!.getNonce();

                PreferenceClass.setStringPreference(this,Constants.LOGIN_STATUS,"1")
                val value = getCurrentDate()
                PreferenceClass.setStringPreference(this, Constants.LOGIN_STATUS,"1")
                val amount = PreferenceClass.getStringPreferences(this, Constants.PAYMENT_AMOUNT)
                val age = PreferenceClass.getStringPreferences(this, Constants.AGE)
                val cat = PreferenceClass.getStringPreferences(this, Constants.CATEGORY)
                val planID = PreferenceClass.getStringPreferences(this, Constants.PAYMENT_PLAN)
                mlPaymentModel!!.FinalPaymentResponse(userID!!, nonce, amount, planID, value, "1", age, cat)
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                val error = data!!.getSerializableExtra(DropInActivity.EXTRA_ERROR) as Exception
            }
        }

    }


    override fun onLoginStart() {

    }

    override fun onLoginFailure(errorCode: Int) {

    }

    override fun onFacbookLogin() {
        mFacebookLoginManager = FacebookLoginManager(this, this)
        mFacebookLoginManager?.login()
    }


    override fun onTwitterLogin() {
        //NOTE : calling default twitter login in OnCreate/OnResume to initialize twitter callback
        defaultLoginTwitter()
        default_twitter_login_button.performClick()
    }


    private fun defaultLoginTwitter() {
        //check if user is already authenticated or not
        val session = TwitterCore.getInstance()!!.sessionManager!!.activeSession
        if (session == null) {

            //if user is not authenticated start authenticating

            default_twitter_login_button.setCallback(object : Callback<TwitterSession>() {
                override fun failure(exception: TwitterException?) {

                    Toast.makeText(this@ActivityMainNew, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show()

                }

                override fun success(result: com.twitter.sdk.android.core.Result<TwitterSession>?) {
                    // Do something with result, which provides a TwitterSession for making API calls
                    val twitterSession = result!!.data

                    //call fetch email only when permission is granted
                    fetchTwitterEmail(twitterSession)
                }


            })

        } else {

            //if user is already authenticated direct call fetch twitter email api
            Toast.makeText(this@ActivityMainNew, "User already authenticated", Toast.LENGTH_SHORT).show()
            fetchTwitterEmail(getTwitterSession())
        }

    }

    private fun fetchTwitterEmail(twitterSession: TwitterSession) {
        client!!.requestEmail(twitterSession, object : Callback<String>() {
            override fun success(result: com.twitter.sdk.android.core.Result<String>?) {
                Log.d("Twitter", "User Id : " + twitterSession.getUserId() + "\nScreen Name : " + twitterSession.getUserName() + "\nEmail Id : " + result!!.data)

                mloginViewModel!!.authenticateSocial("3", twitterSession.getUserId().toString(), deviceToken())
            }


            override fun failure(exception: TwitterException) {
                Toast.makeText(this@ActivityMainNew, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getTwitterSession(): TwitterSession {

        //NOTE : if you want to get token and secret too use uncomment the below code
        /*TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;*/

        return TwitterCore.getInstance()!!.sessionManager!!.activeSession
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    private fun handleSignInResult(result: GoogleSignInResult) {

        if (result.isSuccess) {
            // Signed in successfully, show authenticated UI.
            val acct = result.signInAccount

            if (acct != null) {
                val personPhotoUrl = acct?.photoUrl.toString()
                val model = FacebookModel()
                model.id = acct?.id
                model.firstName = acct?.displayName
                model.email = acct?.email
                model.avatarUrl = personPhotoUrl
                model.type = "2"
                if (model.firstName == null) {
                    model.firstName = "No Name"
                }
                EventBus.getDefault().post(model)
                hitSocialApi(model)
            }

//    replaceFragment(IntroductionFragment())
//
        } else {
            Log.e("Gmail Error", result.toString()+" "+result.status.statusMessage.toString())
            Log.e("Gmail Error", result.status.statusMessage.toString())

        }
    }

    private fun hitSocialApi(model: FacebookModel) {
        mloginViewModel!!.authenticateSocial("1", model.id, deviceToken())
    }


    override fun onLoginSuccess(userData: FacebookModel?) {

        if (userData != null) {
            mloginViewModel!!.authenticateSocial("2", userData.id, deviceToken())
        }
    }

    private fun observer() {


        mloginViewModel?.socialResponse?.observe(this, Observer {
            it?.let {

                if (it.status == 1) {
                    PreferenceClass.setStringPreference(this, Constants.USER_ID, it.data!!.userId)
                    PreferenceClass.setStringPreference(this, Constants.LOGIN_STATUS, "1")
                    PreferenceClass.setStringPreference(this, Constants.USER_TYPE, it.data.type)
                    supportFragmentManager!!.popBackStackImmediate()
                    //addFragment(FragmentPremimum(),true, R.id.container_3)
                   // addFragment(PaymentFragment(), true, R.id.container_3)

                }


            }
        })

        mlPaymentModel?.response?.observe(this, Observer {
            it?.let {

                if (it.status == 1) {
                   Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    var intent: Intent = Intent(this, ActivityMainNew::class.java)
                    startActivity(intent)
                    finish()

                }


            }
        })

        mlPaymentModel?.apiError?.observe(this, Observer {
            it?.let {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        mloginViewModel?.tokenResponse?.observe(this, Observer {
            it?.let {

                if (it.status == 1) {
                    token = it.token
                }


            }
        })
        mloginViewModel?.apiError?.observe(this, Observer {
            it?.let {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        mloginViewModel?.isLoading?.observe(this, Observer {

            // it?.let { showLoading(it) }
        })

        mloginViewModel?.onFailure?.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onPyamentClick() {
        val dropInRequest = DropInRequest()
                .clientToken(token)
        startActivityForResult(dropInRequest.getIntent(this), 100)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this);
    }
}
