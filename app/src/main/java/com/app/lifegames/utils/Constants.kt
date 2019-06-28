package com.app.lifegames.utils

/**
 * Created by android on 2/11/17.
 */
object Constants {
    const val DB_NAME="LifeGames"
    const val FCM_TOKEN="fcm_token"
    const val TABLE_NAME_CATEGORY="CategoriesTable"
    const val TABLE_NAME_CAT="Categories"
    const val IS_LOGGED_IN = "is_logged_in"
    const val BASE_URL = "http://app.lifegamesbooks.com/lifegames/api/"
    const val PRIVACY_POLICY = "https://lifegamesbooks.com/app_privacy_policy.html"
    const val TERMS_AND_CONDITIONS = "https://lifegamesbooks.com/app_terms_and_conditions.html"
    const val LOGIN = "login"
    const val TOKEN = "token"
    const val LOGIN_RESPONSE = "login_response"
    const val PAGE_URL = "page_url"
    const val DATE_CHECK="date_check"
    const val DATE_POS="date_pos"
    const val REMINDER_CHECK="reminder_check"
    const val TIME_CHECK="time_check"
    const val TIME_POS="time_pos"
    const val UPDATE_INFO = "update-personal-info"
    const val ACTIVIYLIST = "activityList"
    const val AGE = "age"
    const val CHECK_RUN = "checkRun"
    const val CATEGORY = "category"
    const val ACTIVIY_ID = "activity_id"
    const val IS_REGISTERED = "registered"


    const val REQUEST_CAMERA = 1
    const val REQUEST_GALLERY = 2
    const val REQUEST_GPS_ENABLE_PERMISSION = 100
    const val LAT = "Lat"
    const val LNG = "lng"
    const val ISEDIT = "IsEditProfile"
    const val DIALOG_SHOWN = "dialog_shown"
    const val PERMISSION_READ_EXTERNAL_STORAGE = 123
    const val REQUEST_SELECT_PLACE = 500
    const val REQUEST_CODE_ASK_PERMISSIONS = 100
    const val PERMISSION_REQUEST_CODE = 98
    const val LOCATION_PERMISSION_REQUEST_CODE = 101
    const val TYPE_SIMPLE = 0
    const val TYPE_BREAKFAST = 1
    const val TYPE_LUNCH = 2
    const val TYPE_TASK = 3
    const val TYPE_UNVERIFIED_DOC = 4


    const val MODEL = "model"
    const val USER_MODEL = "user_model"
    const val USER_ID = "userID"
    const val PAYMENT_AMOUNT = "payment_amount"
    const val PAYMENT_PLAN = "payment_plan"
    const val PAYMENT_PLAN_NAME = "payment_plan_name"
    const val USER_TYPE= "userType"
    const val SUCCESS= "success"

    const val LOGIN_STATUS= "loginStatus"
    const val ACTIVITY_TYPE= "activityType"

    const val FAQURL = "http://54.213.174.41/mr/faq"
    const val TERMCONDITION_URL = "http://54.213.174.41/mr/terms-conditions"


    const val NOTIFICATION = "notification"

    const val SPLASH_TIME_OUT: Long = 3000
    const val HANDLER_DELAY_TIME:Long=2000

    const val SIGN_UP = "2"

    const val APPOINTMENT = "appointment"
    const val APPOINTMENT_ID = "appointment_id"
    const val PRIORITY = "priority"
    const val ADDRESS_ID = "address_id"
    const val NOTI_COUNT = "noti_count"

    const val IS_LOCKED = "is_locked"
    const val TODO = "todo"

    const val FROM_TODO = 91
    const val FROM_APPOINTMENT = 92


    var DATA_LIMIT = 100
    var DATA_DOCTOR_LIMIT = 20

    var LOAD_MORE_LIMIT = 20


    const val STATIC_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjE0MSwiaXNzIjoiaHR0cDovLzU0LjIxMy4xNzQuNDEvYXBpL3YxL2xvZ2luIiwiaWF0IjoxNTIzMjUxNTQ1LCJleHAiOjE1NTQ3ODc1NDUsIm5iZiI6MTUyMzI1MTU0NSwianRpIjoianVRbU5yS01MU0JiNjVoVCJ9.LuUTJNIULvbTrqr_z2HKq75ZUxj3Bjp5v-yqpvvgDKg"

    const val BREAK_FAST = 1
    const val LUNCH = 2

    const val DOCTOR_PRIORITY_LOW = 1
    const val DOCTOR_PRIORITY_MEDIUM = 2
    const val DOCTOR_PRIORITY_HIGH = 3

    const val REQUST_FROM_SCREEN = "request_from_screen"
    const val ADD_UPDATE_DOCTOR_SCREEN = 1
    const val SUGGESTION_SCREEN = 2
    const val FROM_UNVERIFIED_DOCTOR_SCREEN = 3
    const val SUGGESTION_TYPE = "suggestion_type"

    const val PROFILE_MODE = "profile_mode"
    const val DOCTOR_PRIORITY = "doctor_priority"
    const val PROFILE_MODE_CREATE = 1
    const val PROFILE_MODE_UPDATE = 2
    const val SUGGESTION_DATA = "suggestion_data"


    const val TIME_FORMAT = "h:mm a"

    const val APP_HIDDEN_FOLDER="/.besttyme"


    //booking schedule with doctor
    const val BOOKING_TIME = "booking_time"
    const val DOCTOR_ID = "booking_time"
    const val START_TIME = "start_time"
    const val BOOKING_ITEM = "booking_item"
    const val FRAGMENT_TYPE = "fragment_type"
    const val BUNDLE_DATA = "bundle_data"
    const val DOCTOR_INFO = "doctor_info"
    const val CONTACT_ID = "contact_id"
    const val DATA_ITEM = "data_item"


    //navigate to fragment
    const val VERIFIED_DOCTOR_FRAGMENT = 1
    const val UNVERIFIED_DOCTOR_FRAGMENT = 2
    const val ADD_DOCTOR_FRAGMENT = 3
    const val CREATE_NEW_APPOINTMENT = 4
    const val VERIFIED_DOCTOR_FRAGMENT_EDIT_APPOINTMENT = 5
    const val UNVERIFIED_DOCTOR_FRAGMENT_EDIT_APPOINTMENT = 6
    const val CHANGE_PASSWORD = 7
    const val TERMS_POLICY = 8
    const val FAQ = 9
    const val CONTACT_US = 10
    const val EDIT_PROFILE = 11
    const val DOCTOR_FROM_PUSH_WITH_ADDTO_CONTACT = 12
    const val DOCTOR_FROM_PUSH = 13

    const val FOR_NOTIFICATION = 1000


    const val SELECTED_ARRAY_ID = "array"


    internal interface httpcodes {
        companion object {
            val STATUS_OK = 200
            val STATUS_BAD_REQUEST = 400
            val STATUS_SESSION_EXPIRED = 401
            val STATUS_PLAN_EXPIRED = 403
            val STATUS_VALIDATION_ERROR = 404
            val STATUS_SERVER_ERROR = 500
            val STATUS_UNKNOWN_ERROR = 503
            val STATUS_API_VALIDATION_ERROR = 422

        }
    }

    const val SNACK_BAR_DURATION = 2500


    const val IS_LOGOUT = "is_logout"

    const val IMAGE_UPLOAD_FAILURE_MESSAGE = "Uploaded failure please try again!"
    const val IMAGE_UPLOADED_MESSAGE = "Successfully uploaded"
    //FAILURE MESSAGES
    const val SOMETHING_WENT_WRONG = "Something went wrong please try again later!"
    const val FAILURE_TIME_OUT_ERROR = "Request time out!"
    const val FAILURE_SOMETHING_WENT_WRONG = "Something went wrong please try again later!"
    const val FAILURE_SERVER_NOT_RESPONDING = "Oops! looks like we are having internal problems. Please try again later."
    const val FAILURE_INTERNET_CONNECTION = "Internet connection appears to be offline. Please check your network settings."
    const val SESSION_EXPIRED = "Sorry, looks like you are logged in another device with the same user."


    var DEVICE_TOKEN_UPDATED = "device_token_updated"

    //menu constants
    const val MENU_VIEW_SCHEDULE = 0
    const val MENU_VIEW_TARGETS = 1
    const val MENU_ADD_TASK = 4
    const val MENU_SAMPLE_REQUEST = 7
    const val MENU_VIEW_ANALYTICS = 9
    const val MENU_SETTINGS = 10
    const val MENU_LOGOUT = 11
    const val MENU_EDIT_PROFILE=12





    const val NOTI_TYPE = "noti_type"

    //doctor appointment type enum
    enum class TYPE(val type: Int) {
        SIMPLE(0), BREAKFAST(1), LUNCH(2), TASK(3), UNVERIFIEDOCTOR(4)
    }

    enum class WEEK(val type: Int) {
        MONDAY(0), TUESDAY(1), WEDNESDAY(2), THURSDAY(3), FRIDAY(4), SATURDAY(5), SUNDAY(6)
    }



}