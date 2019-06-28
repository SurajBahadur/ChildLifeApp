package com.app.lifegames.ui.language

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.lifegames.R

import com.app.lifegames.base_classes.BaseFragment
import com.app.lifegames.database.DatabaseClient
import com.app.lifegames.database.Entities.ScheduleDatesTable
import com.app.lifegames.ui.selectAge.SelectionFragment
import com.app.lifegames.ui.splash.apiHit.SaveDeviceIDViewModel
import com.app.lifegames.utils.Constants
import com.app.lifegames.utils.PreferenceClass
import com.app.lifegames.utils.security.ApiFailureTypes
import kotlinx.android.synthetic.main.fragment_language_selection.*
import android.widget.ArrayAdapter
import android.text.TextPaint
import android.widget.Toast
import com.app.lifegames.ui.termsAndConditions.TermsAndConditionsFragment
import com.app.lifegames.utils.PatternEditableBuilder
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.LinkBuilder

import java.util.regex.Pattern
import android.content.Intent
import android.net.Uri
import android.widget.PopupMenu
import java.lang.Exception
import java.util.*


class LanguageSelectionFragment  : BaseFragment() {

    var languageList:List<String>?=ArrayList()
    var country = arrayOf("UK English")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      /*  var string=tv_term_text.text.toString().split(" Terms and Conditions")
        var substr=tv_term_text.text.toString().subSequence(string[0].length-1,tv_term_text.text.toString().length)

       Log.d("length"," "+tv_term_text.text.length+" "+tv_term_text.text.toString().split(" Terms and Conditions")
       + " split length "+string[0].length+" total "+tv_term_text.text.toString().length+" substring "+substr+" substring length "+substr.length)
*/
        val link = Link("terms and conditions.")
                .setTextColor(Color.parseColor("#FF8080"))                 // optional, defaults to holo blue
                // optional, defaults to holo blue
                .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                .setUnderlined(true)                                       // optional, defaults to true
                .setBold(true).setOnClickListener {
                   /* addFragment(TermsAndConditionsFragment(),false,R.id.container)*/
                    try {
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(Constants.TERMS_AND_CONDITIONS)
                        startActivity(i)
                    }
                    catch (e:Exception)
                    {
                        e.printStackTrace()
                    }

                }
        val link2 = Link("privacy policy")
                .setTextColor(Color.parseColor("#FF8080"))                 // optional, defaults to holo blue
                // optional, defaults to holo blue
                .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                .setUnderlined(true)                                       // optional, defaults to true
                .setBold(true).setOnClickListener {
                    /* addFragment(TermsAndConditionsFragment(),false,R.id.container)*/
                    try {
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(Constants.PRIVACY_POLICY)
                        startActivity(i)
                    }
                    catch (e:Exception)
                    {
                        e.printStackTrace()
                    }

                }      // optional, defaults to false
        LinkBuilder.on(tv_term_text)
                .addLink(link)
                .addLink(link2)
                .build();

        tv_cnt.setOnClickListener {
            replaceFragment(SelectionFragment(),false,R.id.container)
        }

        spin.setOnClickListener {
            //Creating the instance of PopupMenu
                val popup = PopupMenu(activity, spin);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                    .inflate(R.menu.language_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.ukEngkish ->
                            popup.dismiss()
                           // Toast.makeText(activity, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                             }
                    true
                })

            popup.show(); //showing popup menu
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_language_selection,container,false)

    }


}