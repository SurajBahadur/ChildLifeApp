/*
package com.pnw.quranic.quranicandroid.activities.subscription

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import com.anjlab.android.iab.v3.BillingProcessor
import com.app.lifegames.R
import com.google.android.gms.tasks.OnCompleteListener

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap




lateinit var bp: BillingProcessor
val LEVEL_ANDROID_KEY = "LEVEL_ANDROID_KEY"
val USER_TYPE_ANDROID_KEY = "USER_TYPE_ANDROID_KEY"
val LEVEL_FROM_SUBSCRIPTION = "LEVEL_FROM_SUBSCRIPTION"
private var levelAndroid = ""

private var fromSettings = false

class Subscription : AppCompatActivity(), BillingProcessor.IBillingHandler {
    */
/**
     * @todo bp.loadOwnedPurchasesFromGoogle();
     *//*


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)
        Crashlytics.setUserIdentifier(FirebaseAuth.getInstance().currentUser?.uid)

        LoggingUtils.appendToLog(this, "Subscription.log", "Subscription activity started")

        tv_sub_upgrade.text = "Upgrade \uD83C\uDF89 "

        fromSettings = intent.getBooleanExtra("fromSettings", false)
        ib_sub_close.setOnClickListener {
            onBackPressed()
        }

        val prefs = applicationContext.getSharedPreferences("SomePref", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("isTimeUpdated", false).apply()

        bp = BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7eeiCVYjaGiJesbS0C97OhAvu6ww6Bpu6Oxf4taIhnNT9O81zVtdZmUFRnHcgfZJAajMjaLfxUE4hvVPNkZYqdhOUhPsRJMmYVGlAlO9ud8FqsTFksXV1j5BouOy8DBDwBbNRiYayAaq1CpQEEIxtz1jvfioqmq7omJJ1aFdqWPsCKgE68NCi9c5ZnjRk5LL9SlqzWqc4gB/sR5myrEI/2hPQ7uadJKZKxzy+/9PTLokWERwaHgWfyT/qFKuTr8NAm6KJldk13nPSsU1h1v5yXlDsJ373JCl8a78xy9KfJ2NT/zqZqhOfybWRL+FUi23Ch+gBhQu+UY+G8fG2JN1VwIDAQAB", this)
        bp.initialize()
        IB_subsc_type1.setOnClickListener {
            LoggingUtils.appendToLog(this, "Subscription.log", "Basic plan clicked")
            bp.subscribe(this, "qapp.basic.plan")
        }
        IB_subsc_type2.setOnClickListener {
            LoggingUtils.appendToLog(this, "Subscription.log", "Pro plan clicked")
            bp.subscribe(this, "qapp.pro.plan")
        }

//        println("owned.. subs?"+bp.listOwnedSubscriptions())
//        println("google.. owned"+bp.loadOwnedPurchasesFromGoogle())

    }

    override fun onBillingInitialized() {
        val str = "$bp\n" +
                "Billing initialized...\n" +
                "Google owned: ${bp.loadOwnedPurchasesFromGoogle()}\n" +
                "Owned subs: ${bp.listOwnedSubscriptions()}\n" +
                "Basic transaction details: ${bp.getSubscriptionTransactionDetails("qapp.basic.plan")}\n" +
                "Pro transaction details: ${bp.getSubscriptionTransactionDetails("qapp.pro.plan")}\n"
        LoggingUtils.appendToLog(this, "Subscription.log", str)

        println("billing initialized!!")
        println("google owned" + bp.loadOwnedPurchasesFromGoogle())
        println("owned subs in billing init?" + bp.listOwnedSubscriptions())
        println("pro details" + bp.getSubscriptionTransactionDetails("qapp.pro.plan"))
        println("basic details" + bp.getSubscriptionTransactionDetails("qapp.basic.plan"))
        println("pro4purchase auto renew" + bp.getSubscriptionTransactionDetails("qapp.pro.plan")?.purchaseInfo?.purchaseData?.autoRenewing)
    }

    override fun onPurchaseHistoryRestored() {
        LoggingUtils.appendToLog(this, "Subscription.log", "Purchase history restored")
        println("purchase history restored!!")
    }


    override fun onProductPurchased(productId: String, details: TransactionDetails?) {
        val str = "Product id: $productId\n"
        if (details != null) {
            str.plus("$details\n" +
                    "Purchase State: ${details.purchaseInfo.purchaseData.purchaseState}\n" +
                    "Purchase Token: ${details.purchaseInfo.purchaseData.purchaseToken}\n" +
                    "Order ID: ${details.purchaseInfo.purchaseData.orderId}\n" +
                    "Purchase Time: ${details.purchaseInfo.purchaseData.purchaseTime}")
        }

        println("product purchased!!")
        println("productId: $productId")
        println("details: $details")
        println("details: ${details!!.purchaseInfo.responseData}")
        println("details: ${details.purchaseInfo.purchaseData.purchaseState}")
        println("details: ${details.purchaseInfo.purchaseData.purchaseToken}")
        println("details: ${details.purchaseInfo.purchaseData.orderId}")
        println("purchase time: ${details.purchaseInfo.purchaseData.purchaseTime}")

        val myDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val cal = Calendar.getInstance()
        val todayDate = dateFormat.format(myDate) + " " + cal.timeZone.id
        val date = dateFormat.format(cal.time)
        val expiryTime = date + " " + cal.timeZone.id

        if (productId == "qapp.basic.plan") {
            levelAndroid = "basic"
            FirebaseAnalyticsUtil.logEvent(this@Subscription, "user_subscribe")
            FirebaseAnalyticsUtil.logEvent(this@Subscription, "purchase_basic")
            val params = Bundle()
            params.putString("type", "basic")
            FirebaseAnalyticsUtil.logEvent(this@Subscription, "purchase", params)

        } else if (productId == "qapp.pro.plan") {
            levelAndroid = "pro"
            FirebaseAnalyticsUtil.logEvent(this@Subscription, "user_subscribe")
            FirebaseAnalyticsUtil.logEvent(this@Subscription, "purchase_pro")
            val params = Bundle()
            params.putString("type", "pro")
            FirebaseAnalyticsUtil.logEvent(this@Subscription, "purchase", params)

        }

        val purchaseDate = details.purchaseInfo.purchaseData.purchaseTime.toString()
        val datealtered = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss z", details.purchaseInfo.purchaseData.purchaseTime)
        println("datealtered $datealtered")
        println("auto renewing +" + details.purchaseInfo.purchaseData.autoRenewing)
        println("subsc" + bp.isSubscriptionUpdateSupported)
        mDatabase = PersistentFirebaseUtil.getDatabase().reference
        mDatabase.keepSynced(true)
        val user = FirebaseAuth.getInstance().currentUser

        //  TODO: Test
        val onCompleteListener = DatabaseReference.CompletionListener { databaseError, databaseReference ->
            try {
                if (databaseError != null) {
                    LoggingUtils.appendToLog(this@Subscription, "Subscription.log", "$databaseError")
                }
                LoggingUtils.appendToLog(this@Subscription, "Subscription.log", "${databaseReference.parent}")
            } catch (ex: Exception) {
            }
        }


        mDatabase.child("users").child(user!!.uid).child("subscription").child("purchaseDateAndroid").setValue(todayDate, onCompleteListener)
        mDatabase.child("users").child(user.uid).child("subscription").child("autoRenew").setValue(true, onCompleteListener)
        mDatabase.child("users").child(user.uid).child("subscription").child("productId").setValue(productId, onCompleteListener)
        mDatabase.child("users").child(user.uid).child("subscription").child("playStoreSubs").setValue(true, onCompleteListener)


        //TEST WORKAROUND TO SOLVE THE SUBSCRIPTION ISSUE
        val test=SubscriptionRequest(todayDate,productId,true, levelAndroid,true)
        mDatabase.child("users").child(user!!.uid).child("subscription").setValue(test) { error, ref ->
            println(error)
            println(ref)
        }


        val prefs = applicationContext.getSharedPreferences("SomePref", Context.MODE_PRIVATE)
        if (levelAndroid == "basic" || levelAndroid == "pro") {
            mDatabase.child("users").child(user!!.uid).child("subscription").child("levelAndroid").setValue(levelAndroid)
            if (levelAndroid == "basic") {
                mDatabase.child("users").child(user.uid).child("userTypeAndroid").setValue(2, onCompleteListener)
                prefs.edit().putString(LEVEL_ANDROID_KEY, levelAndroid).apply()
                prefs.edit().putInt(USER_TYPE_ANDROID_KEY, 2).apply()
                LoggingUtils.appendToLog(this@Subscription, "Subscription.log", "Basic preference apply called")
                MailchimpUtils.setUserCategory(applicationContext, MailchimpUtils.CATEGORY_BASIC_PURCHASE_USERS)
                Settings.levelsubscription = levelAndroid
            } else if (levelAndroid == "pro") {
                prefs.edit().putString(LEVEL_ANDROID_KEY, levelAndroid).apply()
                prefs.edit().putInt(USER_TYPE_ANDROID_KEY, 3).apply()
                LoggingUtils.appendToLog(this@Subscription, "Subscription.log", "Pro preference apply called")
                mDatabase.child("users").child(user.uid).child("userTypeAndroid").setValue(3, onCompleteListener)
                MailchimpUtils.setUserCategory(applicationContext, MailchimpUtils.CATEGORY_PRO_PURCHASE_USERS)
                Settings.levelsubscription = levelAndroid
            }
        }

    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {
        LoggingUtils.appendToLog(this@Subscription, "Subscription.log", "Error Code $errorCode\n" +
                "Error: ${error.toString()}")
        println("billing error!!")
        println("error: $error")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        println("requestcode: $requestCode")
        println("resultcode: $resultCode")
        println("data: $data")
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data)
        println("activity result")
    }

    override fun onDestroy() {
        if (bp != null)
            bp.release()
        super.onDestroy()
        println("activity destroyed")
    }

    override fun onBackPressed() {
        if (fromSettings) {
            val intent = Intent(this, Settings::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
*/
