package com.app.lifegames.utils

import Preferences
import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.app.lifegames.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object SingleTon {

    private var mLat: Double = 0.0
    private var mLng: Double = 0.0

    private var FORMAT = "yyyy-MM-dd HH:mm:ss"


    fun getLatitude(): Double {
        return mLat
    }

    fun getLongitude(): Double {
        return mLng
    }

    fun setLatitude(lat: Double) {
        mLat = lat
    }

    fun setLongitude(lng: Double) {
        mLng = lng
    }

    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }


    fun getFormatedTime(dateString: String?, dateFormat: String?): String? {

        val format = SimpleDateFormat(FORMAT)

        val formatedDateString: String
        try {
            val timeFormat = SimpleDateFormat(dateFormat)
            val date = format.parse(dateString)
            formatedDateString = timeFormat.format(date)
        } catch (e: ParseException) {
            Log.e("exp", e.localizedMessage)
            return dateString
        } catch (e: NullPointerException) {
            Log.e("exp", e.localizedMessage)
            return dateString
        }

        return formatedDateString
    }

    /**
     *********** view schedule show booking time UTC to Local *************
     */

    fun getFormatedTimeFromUtc(dateString: String?, dateFormat: String?): String? {

        val format = SimpleDateFormat(FORMAT,Locale.US)

        val formatedDateString: String
        try {

            val utcZone = TimeZone.getTimeZone("UTC")
            format.timeZone = utcZone// Set UTC time zone
            val date = format.parse(dateString)

            val timeFormat = SimpleDateFormat(dateFormat,Locale.US)
            timeFormat.timeZone = TimeZone.getDefault()// Set device time zone

            formatedDateString = timeFormat.format(date)
        } catch (e: ParseException) {
            Log.e("exp", e.localizedMessage)
            return dateString
        } catch (e: NullPointerException) {
            Log.e("exp", e.localizedMessage)
            return dateString
        }

        return formatedDateString
    }


    fun getFormatedDate(context: Context?, dateString: String?): String? {

        val format = SimpleDateFormat(FORMAT)


        var formatedDateString = ""
        try {
            val timeFormat = SimpleDateFormat("hh:mma")
            val dateFormat = SimpleDateFormat("MMMM dd")
            val yearFormat = SimpleDateFormat("yyyy")
            val dayFormat = SimpleDateFormat("dd")

            val date = format.parse(dateString)
            formatedDateString = timeFormat.format(date) + " " + context?.getString(R.string.on) + " " + dateFormat.format(date).trim() + getDayNumberSuffix(dayFormat.format(date).toInt()) + ", " + yearFormat.format(date)

        } catch (e: ParseException) {
            Log.e("exp", e.localizedMessage)
            return context?.getString(R.string.not_visited_yet)
        } catch (e: NullPointerException) {

            return context?.getString(R.string.not_visited_yet)
        }

        return formatedDateString
    }


    fun getBookingFormatedDate(context: Context, dateString: String?): String? {

        val format = SimpleDateFormat(FORMAT)


        val formatedDateString: String
        try {
            val timeFormat = SimpleDateFormat("hh:mm a - EEEE, MMMM dd, yyyy", Locale.US)

            val date = format.parse(dateString)
            formatedDateString = timeFormat.format(date)
        } catch (e: ParseException) {
            Log.e("exp", e.localizedMessage)
            return dateString
        } catch (e: NullPointerException) {
            Log.e("exp", e.localizedMessage)
            return context.getString(R.string.nothing_available)
        }
        return formatedDateString
    }

    fun getBookingFormatedDateFromUtc(context: Context, dateString: String?): String? {

        val format = SimpleDateFormat(FORMAT)


        val formatedDateString: String
        try {
            val timeFormat = SimpleDateFormat("hh:mm a - EEEE, MMMM dd, yyyy", Locale.US)
            val utcZone = TimeZone.getTimeZone("UTC")
            format.timeZone=utcZone
            val date = format.parse(dateString)
            formatedDateString = timeFormat.format(date)
        } catch (e: ParseException) {
            Log.e("exp", e.localizedMessage)
            return dateString
        } catch (e: NullPointerException) {
            Log.e("exp", e.localizedMessage)
            return context.getString(R.string.nothing_available)
        }
        return formatedDateString
    }



    fun getDayNumberSuffix(day: Int): String {
        if (day in 11..13) {
            return "th"
        }
        return when (day % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }


    fun getUTCTimeFormat(localTime: String?): String? {
        val dateFormat = SimpleDateFormat(FORMAT)
        dateFormat.timeZone = TimeZone.getDefault()
        var formatedDateString = ""
        try {

            Log.e("localtime", localTime)
            val date = dateFormat.parse(localTime)
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            formatedDateString = dateFormat.format(date)
            Log.e("formatedtime", formatedDateString)


        } catch (e: ParseException) {
            Log.e("parseeror", e.localizedMessage)
            return localTime
        } catch (e: NullPointerException) {
            Log.e("nullerror", e.localizedMessage)
            return localTime
        }

        return formatedDateString
    }



    fun getDateFromUtc(dateStr: String?): String {
        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
        val formattedDate: String
        try {
            val timeFormat = SimpleDateFormat("MMMM dd, yyyy",Locale.getDefault())
            val date = format.parse(dateStr)
            formattedDate = timeFormat.format(date)
        } catch (e: ParseException) {
            Log.e("parseeror", e.localizedMessage)
            return dateStr + ""
        }
        return formattedDate
    }



    fun getDate(dateStr: String?): Date? {
        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
        val formattedDate: String
        var date=Date()
        try {
            val timeFormat = SimpleDateFormat("MMMM dd, yyyy",Locale.getDefault())
              date = format.parse(dateStr)
            formattedDate = timeFormat.format(date)
        } catch (e: ParseException) {
            Log.e("parseeror", e.localizedMessage)
            return  null
        }
        return date
    }


    fun getDateTimeFromUtc(dateStr: String?,getformat:String,formatTimeUtc:String): String {
        val format = SimpleDateFormat(formatTimeUtc, Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("UTC")
        val formattedDate: String
        try {
            val timeFormat = SimpleDateFormat(getformat)
            val date = format.parse(dateStr)
            formattedDate = timeFormat.format(date)
            Log.e("singltontimetodo",formattedDate)
        } catch (e: ParseException) {
            Log.e("parseeror", e.localizedMessage)
            return dateStr + ""
        }
        return formattedDate
    }




    fun checkMorningEveningMsg(): String? {
        var msg: String? = null
        val c = Calendar.getInstance()
        val timeOfDay = c.get(Calendar.HOUR_OF_DAY)

        when (timeOfDay) {
            in 0..11 -> msg = "Good Morning"
            in 12..15 -> msg = "Good Afternoon"
            in 16..20 -> msg = "Good Evening"
            in 21..23 -> msg = "Good Night"
        }
        return msg
    }

    fun getUserLoginStatus():Boolean?{
        return Preferences.prefs?.getValue(Constants.IS_LOGGED_IN, false)

    }



}