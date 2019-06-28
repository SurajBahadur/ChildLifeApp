package com.app.lifegames.activity

import Preferences
import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import com.app.lifegames.R
import com.app.lifegames.database.AppDatabase
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.Utils

import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by android on 8/11/17.
 * Base activity for all activities
 */
@SuppressLint("Registered")
 abstract class BaseActivity : AppCompatActivity() {
    /////////
    var mFragmentList: ArrayList<Fragment>? = ArrayList();
    ////////////

    protected var mDoubleBackToExitPressedOnce = false

    private val PERMISSION_REQUEST = 121

    protected val TAG: String = javaClass.simpleName

    private var mProgressDialog: Dialog? = null

    private var mIsSessionExpired = true
    protected var mContent: View? = null// For showing snackbar

    /************abstract methods to get the layout id and create the view in the child class*/

     abstract fun getLayoutId(): Int

     abstract fun onLayoutCreated()

    /************** child class view creation ********************/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(getLayoutId())

        onLayoutCreated()

    }

    fun showSnackBar(message: String, content: View) {
        this.let {
            Snackbar.make(content, message, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // pass activity's  result to the fragments
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        fragment?.onActivityResult(requestCode, resultCode, data)

    }

    override fun onPause() {
        super.onPause()
        Utils.hideKeyboard(this)
    }

    override fun onStart() {
        super.onStart()
        Utils.hideKeyboard(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        fragment?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun removeAllFragments111()
    {

        for (fragment in supportFragmentManager.fragments) {
            /*if (fragment is NavigationDrawerFragment) {
                continue
            } else if (fragment != null) {
                supportFragmentManager.beginTransaction().remove(fragment).commit()
            }*/

            if (fragment != null)
                supportFragmentManager.beginTransaction().remove(fragment).commit()
        }


    }

    fun replaceFragment(fragment: Fragment)
    {
        //removeAllFragments()
        val tag: String = fragment::class.java.simpleName
        val transaction = supportFragmentManager?.beginTransaction()
        // transaction?.setCustomAnimations(R.anim.anim_in, R.anim.anim_out, R.anim.anim_in_reverse, R.anim.anim_out_reverse)
        transaction?.setCustomAnimations(R.anim.fade_in,
                0)

        transaction?.replace(R.id.container_main, fragment, tag)
                ?.commitAllowingStateLoss()
    }









    fun addMyFragmentIntoList(fragment: Fragment)
    {
            mFragmentList?.add(fragment)
    }

    fun clearAllFragments() // call before replace fragment from child
    {
        if (mFragmentList?.size!! > 0)
        {
            for (i in 0 until mFragmentList?.size!!)
            {
                supportFragmentManager.beginTransaction().remove(mFragmentList?.get(i)!!).commitAllowingStateLoss()
            }
        }
        mFragmentList?.clear()
    }

    /*
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            mFragmentList?.clear()

        }
        super.onBackPressed()

    }
    */




    //will use later
    private fun sessionExpired(message: String) {
        val builder = AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.dialog_app_name))
            setMessage(message)
        }

        val positiveText = getString(android.R.string.ok)
        builder.setPositiveButton(positiveText
        ) { dialog, which ->
            // positive button logic
        }


        val dialog = builder.create()
        // display dialog
        dialog.show()


    }
    fun showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = Dialog(this, android.R.style.Theme_Translucent)
            mProgressDialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            mProgressDialog?.setContentView(R.layout.loader_half__layout)
            mProgressDialog?.setCancelable(false)

        }
        mProgressDialog!!.show()
    }

    fun hideProgress() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog?.dismiss()
        }
    }

    fun showLoading(show: Boolean?) {
        if (show!!) showProgress() else hideProgress()
    }

    /**
     ***************** when permission is diabled then show user a dialog
     * force to enable permission from setting
     */

    fun permissionDenied() {
        val builder = android.support.v7.app.AlertDialog.Builder(this)

        builder.setMessage(getString(R.string.permission_denied))

        val positiveText = getString(android.R.string.ok)
        builder.setPositiveButton(positiveText,
                { dialog, which ->
                    enablePermission()
                })

        val negativeText = getString(android.R.string.cancel)
        builder.setNegativeButton(negativeText,
                { dialog, which ->
                    dialog.dismiss()
                })

        val dialog = builder.create()
        // display dialog
        dialog.show()
    }

    /**
     ************** start app detail activity to enable disabled permissions ***********
     */

    fun enablePermission() {
        val packageName = packageName

        try {
            //Open the specific App Info page:
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:$packageName")
            startActivityForResult(intent, PERMISSION_REQUEST)

        } catch (e: ActivityNotFoundException) {
            //e.printStackTrace();
            //Open the generic Apps page:
            val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
            startActivityForResult(intent, PERMISSION_REQUEST)

        }

    }

    /**
     ************ clear images stored in folder bestyme of map snaps *******
     */
    fun clearCacheImages() {
        if (checkForStoragePermission()) {
            val directoryName = Environment.getExternalStorageDirectory().toString() + Constants.APP_HIDDEN_FOLDER
            val mapSnapDirectory = File(directoryName)
            val list = mapSnapDirectory.listFiles()

            if (list != null) {
                for (tempFile in list) {
                    tempFile.delete()
                }
            }
        }

    }


    fun checkForStoragePermission(): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                return false
            }
        }
        return true
    }

    //use later
    fun requestStoragePermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), Constants.PERMISSION_REQUEST_CODE)
                return false
            }
        }
        return true
    }



    /**
     * This method will request permission
     */
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), Constants.LOCATION_PERMISSION_REQUEST_CODE)

    }


    fun showAlert(showAlertDialog: AlertDialog.Builder.() -> Any) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.showAlertDialog()
        dialogBuilder.create()
        dialogBuilder.show()
    }



    //  protected abstract fun getRootLayout(): View




    fun addFragment(fragment: Fragment, addToBackStack: Boolean, id: Int) {
        val tag = fragment.javaClass.simpleName
        val fragmentManager = supportFragmentManager
        val fragmentOldObject = fragmentManager?.findFragmentByTag(tag)
        val transaction = fragmentManager?.beginTransaction()
        // transaction?.setCustomAnimations(R.anim.anim_in, R.anim.anim_out, R.anim.anim_in_reverse, R.anim.anim_out_reverse)
        if (fragmentOldObject != null) {
            fragmentManager.popBackStackImmediate(tag, 0)
        } else {
            if (addToBackStack) {
                transaction?.addToBackStack(tag)
            }
            transaction?.add(id, fragment, tag)
                    ?.commitAllowingStateLoss()
        }
    }



    fun replaceFragmentM(fragment: Fragment)
    {
        //removeAllFragments()
        val tag: String = fragment::class.java.simpleName
        val transaction = supportFragmentManager?.beginTransaction()
        // transaction?.setCustomAnimations(R.anim.anim_in, R.anim.anim_out, R.anim.anim_in_reverse, R.anim.anim_out_reverse)
        transaction?.setCustomAnimations(R.anim.fade_in,
                0)

        transaction?.replace(R.id.container_2, fragment, tag)
                ?.commitAllowingStateLoss()
    }
    fun deviceToken(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }
    fun getCurrentDate(): String
    {
        val c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        val df = SimpleDateFormat("yyyy-mm-dd");
        val formattedDate = df.format(c);

        return formattedDate
    }

}