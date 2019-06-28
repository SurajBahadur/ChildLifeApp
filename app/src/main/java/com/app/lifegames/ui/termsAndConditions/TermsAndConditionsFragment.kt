package com.app.lifegames.ui.termsAndConditions

import android.content.DialogInterface
import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.app.lifegames.R
import com.app.lifegames.base_classes.BaseFragment
import com.app.lifegames.ui.selectAge.SelectionFragment
import com.app.lifegames.utils.Constants
import kotlinx.android.synthetic.main.fragment_language_selection.*
import kotlinx.android.synthetic.main.fragment_terms_and_conditions.*
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.annotation.TargetApi
import android.widget.Toast
import android.webkit.WebViewClient




class TermsAndConditionsFragment : BaseFragment() {

    var languageList:List<String>?=ArrayList()
    var country = arrayOf("UK English")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       /* mWebview.clearHistory()
        mWebview.clearFormData()
        mWebview.clearCache(true)
*/

        mWebview.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show()
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView, req: WebResourceRequest, rerr: WebResourceError) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.errorCode, rerr.description.toString(), req.url.toString())
            }
        }

        mWebview.loadUrl(Constants.TERMS_AND_CONDITIONS)
        /*mWebview.setWebViewClient(object : WebViewClient() {


            override fun onPageFinished(view: WebView, url: String) {
              *//* This call inject JavaScript into the page which just finished loading. *//*
                    mWebview.loadUrl(Constants.TERMS_AND_CONDITIONS)
              }


            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                //Your code to do
               // layout_web_error.setVisibility(View.VISIBLE)
               // layout_web_view.setVisibility(View.GONE)

            }

            // commented on 14 dec
            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                var failingUrl = failingUrl
               // onReceivedErrorFlag = 1

            }


        })*/
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_terms_and_conditions,container,false)

    }


}