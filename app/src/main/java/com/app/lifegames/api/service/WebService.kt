package com.app.lifegames.api.service

import com.app.lifegames.data.completedActivtites.CompletedActivityResponse
import com.app.lifegames.data.favouriteActivities.FavouriteActivitiesResponse
import com.app.lifegames.data.login.LoginResponse
import com.app.lifegames.data.login.socialLogin.SocialLoginResponse
import com.app.lifegames.data.markAsFavResponse.MarkAsFavResponse
import com.app.lifegames.data.markAsPlanner.MarkAsPlannerResponse
import com.app.lifegames.data.newActivityList.NewActivitiesListResponse
import com.app.lifegames.data.payment.FinalPaymentResponse
import com.app.lifegames.data.payment.GetTokenResponse
import com.app.lifegames.data.plannerActivities.PlannerActivitiesResponse
import com.app.lifegames.data.rateActivity.RateActivityResponse
import com.app.lifegames.data.removeFromFav.RemoveFromFavResponse
import com.app.lifegames.data.removePlannerActivity.RemovePlannerActivityResponse
import com.app.lifegames.data.reviewedActivity.AddReviewResponse
import com.app.lifegames.data.saveDeviceID.SaveDeviceResponse
import com.app.lifegames.data.scheduleDatesResponse.ScheduleDatesResponse
import com.app.lifegames.data.selectedActivity.SelectedActivityResponse
import com.app.lifegames.data.selectiondata.SelectionCategoryResponse
import com.app.lifegames.data.signup.SignUpResponse
import com.app.lifegames.data.updateAsLogout.UpdateLogoutResponse
import retrofit2.Call
import retrofit2.http.*


/**
 *  Created by shivam on 3/11/17.
 *  All web services are declared here
 */
interface WebService {

//    //    @Headers("Accept: " + "application/json")
//    @POST("login")
//    @FormUrlEncoded
//    fun loginUser(@Field("code") code: String): Call<com.hmu.kotlin.data.pinLogin.LoginResponse>
//
//
//    @POST(Constants.UPDATE_INFO)
//    fun updateInformation(@Body request: ResetPasswordRequest): Call<UpdateInfoPojo>


//
//    @Headers("Accept: " + "application/json")
//    @POST("forgot_password")
//    fun resetPassword(@Body request: ResetPasswordRequest): Call<Status>
//
//
//    @Headers("Accept: " + "application/json")
//    @POST("signup")
//    fun register(@Body request: RegisterationRequest): Call<Status>


//
//    @Headers("Accept: " + "application/json")
//    @POST("mr-register")
//    fun registeration(@Body request: RegisterationRequest): Call<RegisterationResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @POST("change-password")
//    fun changePassword(@Body request: ChangePasswordRequest): Call<ChangePasswordResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @POST("contact-us")
//    fun contactUs(@Body request: ContactUsRequest): Call<ContactUsResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @GET("master-data/products-list?name=Sa")
//    fun productsPromotedList(@Query("name") value: String,
//                             @Query("start") start: Int,
//                             @Query("length") length: Int): Call<ProductListResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @GET("master-data/register-fields")
//    fun getIndusteryList(): Call<RegisterFeildResponse>
//
//

    @GET("futureDates")
    fun getDateList(): Call<ScheduleDatesResponse>

    @POST("removeFromPlanner")
    @FormUrlEncoded
    fun removePlannerActivitiy(@Field("user_id") userID: String,@Field("activity_id") activity_id:String): Call<RemovePlannerActivityResponse>

    @POST("markAsPlanner")
    @FormUrlEncoded
    fun markAsPlanner(@Field("user_id") userID: String,@Field("activity_id") activity_id: String?,@Field("schedule") schedule: String
                      ,@Field("at_the_time") at_the_time: String,@Field("description") description: String): Call<MarkAsPlannerResponse>
   /* @POST("markAsPlanner")
    @FormUrlEncoded
    fun markAsPlanner(@Field("user_id") userID: String,@Field("activity_id") activity_id: String?,@Field("schedule") schedule: String
                     ): Call<MarkAsPlannerResponse>*/

    @POST("saveDeviceId")
    @FormUrlEncoded
    fun saveDeviceID(@Field("device_id") deviceID:String,@Field("device_token") deviceToken:String): Call<SaveDeviceResponse>

    @POST("newActivities")
    @FormUrlEncoded
    fun getNewActivities(@Field("user_id") userID: String,@Field("category") category: String?,@Field("age") services: String): Call<NewActivitiesListResponse>


    @POST("completedActivities")
    @FormUrlEncoded
    fun getDoneActivities(@Field("user_id") userID:String): Call<CompletedActivityResponse>

    @POST("favouriteActivities")
    @FormUrlEncoded
    fun getFavActivities(@Field("user_id") userID:String): Call<FavouriteActivitiesResponse>


    @POST("addedPlannerActivities")
    @FormUrlEncoded
    fun getPlannerActivities(@Field("user_id") userID: String): Call<PlannerActivitiesResponse>

    @POST("checkAvailableActivity")
    @FormUrlEncoded
    fun checkPlan(@Field("user_id") userID: String,@Field("selected_category") category: String?,@Field("selected_age") services: String): Call<PlannerActivitiesResponse>


    @POST("selectedActivity")
    @FormUrlEncoded
    fun getServiceDetail(@Field("user_id") userID: String,@Field("activity_id") category: String?): Call<SelectedActivityResponse>

    @POST("rateApp")
    @FormUrlEncoded
    fun rateActivity(@Field("user_id") userID: String, @Field("activity_id") category: String?,
                     @Field("ratings") ratings: Float?,@Field("comment") comment: String): Call<RateActivityResponse>

    @POST("login")
    @FormUrlEncoded
    fun createAccount(@Field("email") email: String,@Field("password") password: String?,@Field("device_id") deviceid: String?): Call<LoginResponse>


    @POST("updateLoginStatus")
    @FormUrlEncoded
    fun updateLoginStatus(@Field("user_id") user_id: String): Call<UpdateLogoutResponse>

    @POST("socialLogin ")
    @FormUrlEncoded
    fun socialLogin(@Field("social_type") type: String,@Field("social_id") social_id: String,@Field("device_id") toekn: String?): Call<SocialLoginResponse>


    @POST("markAsDone")
    @FormUrlEncoded
    fun setActivityAsDone(@Field("user_id") userID: String,@Field("activity_id") category: String?): Call<AddReviewResponse>


    @POST("removeFromFav")
    @FormUrlEncoded
    fun removeFromFav(@Field("user_id") userID: String,@Field("activity_id") category: String?): Call<RemoveFromFavResponse>


    @POST("markAsFav")
    @FormUrlEncoded
    fun markAsFav(@Field("user_id") userID: String,@Field("activity_id") category: String?): Call<MarkAsFavResponse>


    @GET("searchScreen")
    fun getCategories(): Call<SelectionCategoryResponse>

    @POST("signUp")
    @FormUrlEncoded
    fun signUp(@Field("username") name: String,@Field("full_name") full_name: String,
               @Field("email") email: String,@Field("password") password: String?,
               @Field("device_token") device_token: String, @Field("device_id") device_id: String): Call<SignUpResponse>

    @POST("generatePaymentToken")
    @FormUrlEncoded
    fun getToken(@Field("user_id") name: String): Call<GetTokenResponse>


    @POST("finalpayment")
    @FormUrlEncoded
    fun finalPayment(@Field("user_id") name: String,
                     @Field("nonce") nonce: String,
                     @Field("plan_amt") plane_amount: String,
                     @Field("plan_id") plan_id: String,
                     @Field("start_date") start_date: String,
                     @Field("status") status: String, @Field("selected_age") selected_age:String,
                     @Field("selected_category") selected_category:String): Call<FinalPaymentResponse>

   /* user_id:16
    card_number:3530111333300000
    expiry_month:08
    expiry_year:2022
    card_cvv:745*/


    /*@POST("sendingNonceforpayment")
    @FormUrlEncoded
    fun sendingNonce(@Field("user_id") user_id: String,@Field("card_number") card_number: String,
               @Field("expiry_month") expiry_month: String,@Field("expiry_year") expiry_year: String?,
               @Field("card_cvv") card_cvv: String): Call<SendingNonceResponse>*/

//
//    @Headers("Accept: " + "application/json")
//    @GET("contact")
//    fun unverifiedDoctorDetails(@Query("id") doctorId:Int): Call<UnverifiedDoctorResponse>
//    fun doctorDetails(@Header("Authorization") token: String?, @Query("id") doctorId: Int?): Call<ViewDoctorDetailsResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @GET("master-data/specialty-list")
//    fun getSpecializationList(): Call<SpecializationListResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @POST("search-doctor")
//    fun searchDoctor(@Body request: SearchDoctorRequeest): Call<SearchDoctorResponse>
//
//    @Headers("Accept: " + "application/json")
//    @POST("appointment")
//    fun bookSlot(@Body request: BookingRequest): Call<BookingResponse>
//
//
//
//    @Headers("Accept: " + "application/json")
//    @POST("appointment")
//    fun bookSlotUnverifiedDoctor(@Body request: UnverifiedDoctorBookingRequest): Call<BookingResponse>
//
//    @Headers("Accept: " + "application/json")
//    @GET("doctor-schedule")
//    fun getDoctorSchedule(@Query("id") value: Int?,
//                          @Query("address_id") addressId: Int?,
//                          @Query("date") date: String?): Call<DoctorScheduleReponse>
//
//    @Headers("Accept: " + "application/json")
//    @GET("contact-schedule")
//    fun getUnverifiedDoctorSchedule(@Query("id") value: Int?,
//
//                          @Query("date") date: String?): Call<DoctorScheduleReponse>
//
//
//
//    @Headers("Accept: " + "application/json")
//    @POST("last-visits")
//    fun appointmentsList(@Body request: ViewAnalyticsRequest): Call<ViewAnalyticsResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @POST("appointments-list")
//    fun viewScheduleList(@Body request: ViewScheduleRequest): Call<ViewScheduleResponse>
//
//    @Headers("Accept: " + "application/json")
//    @POST("contact")
//    fun createContacts( @Body request: ContactRequest): Call<ContactResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @POST("send-invitation")
//    fun sendInvitation(@Body request: InviteDoctorRequest): Call<InviteDoctorResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @HTTP(method = "DELETE", path = "contact", hasBody = true)
//    fun deleteContact( @Body request: DeleteUnverifiedDoctorRequest ): Call<DeleteUnverifiedDoctorResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @GET("contact-sugesstion")
//    fun getViewSuggestionList(@Query("id") id: Int?
//                              ,@Query("start") start: Int?
//                              ,@Query("length") length: Int?): Call<SuggestionResponse>
//
//
//
//    @Headers("Accept: " + "application/json")
//    @GET("notifications")
//    fun getNotificationList(@Query("page") page: Int?
//                              ): Call<NotificationResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @GET("contact-sugesstion?id=1465")
//    fun getViewSuggestionList(@Header("Authorization") token: String?): Call<ViewSuggestionResponse>
//
//    @Headers("Accept: " + "application/json")
//    @GET("view-contact")
//    fun getUserDetails(@Query("id") id: Int?): Call<GetUserDetailResponse>
//
//    @Headers("Accept: " + "application/json")
//    @POST("todo")
//    fun getTodoList(@Body request: TodoRequest): Call<TodoResponse>
//
//    @Headers("Accept: " + "application/json")
//    @POST("confirm-sugesstion")
//    fun addToContact(@Body request: AddToContactRequest): Call<AddToContactResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @POST("confirm-doctor")
//    fun addToContactWithPriority(@Body request: AddToPriorityContactRequest): Call<AddToContactResponse>
//
//
//
//
//    @Headers("Accept: " + "application/json")
//    @PUT("contact")
//    fun updateToContact(@Body request: ContactRequest): Call<ContactResponse>
//
//    @Headers("Accept: " + "application/json")
//    @PUT("profile")
//    fun updateProfile(@Body request: RegisterationRequest): Call<UpdateProfileSignUpResponse>
//
//    @Headers("Accept: " + "application/json")
//    @HTTP(method = "DELETE", path = "appointment", hasBody = true)
//    fun deleteAppointment( @Body request: DeleteAppointmentRequest ): Call<DeleteAppointmentResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @HTTP(method = "DELETE", path = "todo/{id}")
//    fun deleteTodo(@Path("id")  id: String): Call<DeleteAppointmentResponse>
//
//
//
//
//    @Headers("Accept: " + "application/json")
//    @POST("push-notification")
//    fun pushNotification(@Body request: PushNotificationRequest): Call<PushNotificationResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @DELETE("delete-account")
//    fun deleteAccount(): Call<DeleteAccountResponse>
//
//
//
//    @Headers("Accept: " + "application/json")
//    @GET("sample-requests")
//    fun getSampleList(@Query("page") page: Int? ): Call<SampleRequestResponse>
//
//
//
//    @Headers("Accept: " + "application/json")
//    @PUT("sample-request")
//    fun acceptRejectSampleCall(@Body request: SampleAcceptRejectRequest): Call<SampleAcceptRejectResponse>
//
//
//
//    @Headers("Accept: " + "application/json")
//    @POST("appointment-sugesstions")
//    fun suggestionList(@Body request: SuggestionRequest): Call<ViewScheduleResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @PUT("token")
//    fun updateFirebaseDeviceToken(@Body request: UpdateTokenRequest): Call<UpdateTokenResponse>
//
//    @Headers("Accept: " + "application/json")
//    @HTTP(method = "DELETE", path = "sample-request", hasBody = true)
//    fun deleteSample( @Body request: DeleteSampleRequest ): Call<DeleteSampleResponse>
//
//
//    @Headers("Accept: " + "application/json")
//    @HTTP(method = "DELETE", path = "notification", hasBody = true)
//    fun deleteNotification( @Body request: NotificationRequest): Call<NotificationOperationResponse>
//
//    @Headers("Accept: " + "application/json")
//    @PUT("notification")
//    fun updateNotificationStatus(@Body request: NotificationRequest): Call<NotificationOperationResponse>
//
//    @Headers("Accept: " + "application/json")
//    @GET("notification-count")
//    fun getNotificationCount(): Call<NotificationCountResponse>


}