package com.emergence.study_app.newTech.retrofit;


import com.emergence.study_app.newTech.retrofit.model.Banner.ResponseBanner;
import com.emergence.study_app.newTech.retrofit.model.Chapter.ResponseGetChapter;
import com.emergence.study_app.newTech.retrofit.model.Class.ResponseClass;
import com.emergence.study_app.newTech.retrofit.model.Coaching.ResponseCoaching;
import com.emergence.study_app.newTech.retrofit.model.Get_User.ResponseGetUser;
import com.emergence.study_app.newTech.retrofit.model.Help_Support.ResponseHeloSupport;
import com.emergence.study_app.newTech.retrofit.model.Lacture.ResponseLacture;
import com.emergence.study_app.newTech.retrofit.model.Live_Session.ResponseLiveSession;
import com.emergence.study_app.newTech.retrofit.model.My_Order.ResponseMyOrder;
import com.emergence.study_app.newTech.retrofit.model.Notes.ResponseNotes;
import com.emergence.study_app.newTech.retrofit.model.Notification.ResponseNotificastion;
import com.emergence.study_app.newTech.retrofit.model.Number_Verify.ResponseMobile;
import com.emergence.study_app.newTech.retrofit.model.Order.ResponseOrder;
import com.emergence.study_app.newTech.retrofit.model.PkgDetail.ResponsePkgDetail;
import com.emergence.study_app.newTech.retrofit.model.Privacy_Policy.ResponsePrivacyPolicy;
import com.emergence.study_app.newTech.retrofit.model.SimillarProduct.ResponseSimillarProduct;
import com.emergence.study_app.newTech.retrofit.model.Subject.ResponseSubByCoaching;
import com.emergence.study_app.newTech.retrofit.model.TermsCondition.ResponseTerm;
import com.emergence.study_app.newTech.retrofit.model.Update_User.ResponseUserDetail;
import com.emergence.study_app.newTech.retrofit.model.Update_profile.ResponseUpdateProfile;
import com.emergence.study_app.newTech.retrofit.model.VerifyOtp.ResponseOTP;
import com.emergence.study_app.newTech.retrofit.model.Video.ResponseVideo;
import com.emergence.study_app.newTech.retrofit.model.responseAllPackageDetails.ResponseAllPackageDetails;
import com.emergence.study_app.newTech.retrofit.model.responseAnswer.ResponseAnswer;
import com.emergence.study_app.newTech.retrofit.model.responseAnswerList.ResponseAnswerList;
import com.emergence.study_app.newTech.retrofit.model.responseAttemptPaper.ResponseAttemptPaper;
import com.emergence.study_app.newTech.retrofit.model.responseCheckAppUpdate.ResponseCheckAppUpdate;
import com.emergence.study_app.newTech.retrofit.model.responseComboOffer.ResponseComboOffer;
import com.emergence.study_app.newTech.retrofit.model.responseCoupon.ResponseCoupon;
import com.emergence.study_app.newTech.retrofit.model.responseExitLiveSession.ResponseExitLiveSession;
import com.emergence.study_app.newTech.retrofit.model.responseFinishTestSeriese.ResponseFinishTestSeriese;
import com.emergence.study_app.newTech.retrofit.model.responseFreeVideos.ResponseFreeVideo;
import com.emergence.study_app.newTech.retrofit.model.responseJoinClass.ResponseJoinClass;
import com.emergence.study_app.newTech.retrofit.model.responseJumpQuestion.ResponseJumpQuestion;
import com.emergence.study_app.newTech.retrofit.model.responseLive.ResponseLive;
import com.emergence.study_app.newTech.retrofit.model.responsePoints.ResponsePoints;
import com.emergence.study_app.newTech.retrofit.model.responsePurchasedTestSeries.ResponsePurchasedTestSeries;
import com.emergence.study_app.newTech.retrofit.model.responseQuestion.ResponseQuestion;
import com.emergence.study_app.newTech.retrofit.model.responseQuiz.ResponseQuiz;
import com.emergence.study_app.newTech.retrofit.model.responseRank.ResponseRank;
import com.emergence.study_app.newTech.retrofit.model.responseSendMessage.ResponseSendMessage;
import com.emergence.study_app.newTech.retrofit.model.responseSendToken.ResponseSendToken;
import com.emergence.study_app.newTech.retrofit.model.responseTestSeriesDetails.ResponseTestSeriesDetails;
import com.emergence.study_app.newTech.retrofit.model.responseTestSeriese.ResponseTestSeriese;
import com.emergence.study_app.newTech.retrofit.model.responseUserSession.ResponseUserSession;
import com.emergence.study_app.newTech.retrofit.model.responseVideoCountRemove.ResponseVideoCountRemove;
import com.emergence.study_app.newTech.retrofit.model.responseWalletPoints.ResponseWalletPoints;
import com.emergence.study_app.newTech.retrofit.model.videoPackageOrder.ResponseVideoPackageOrder;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServices {

    @POST("User.php")
    @FormUrlEncoded
    Call<ResponseMobile> getRegister(
            @Field("mobile") String mobile,
            @Field("flag") String Register
    );
    @POST("User.php")
    @FormUrlEncoded
    Call<ResponseOTP> getVerifyOTP(
            @Field("mobile") String mobile,
            @Field("otp") String otp,
            @Field("flag") String VerifyOTP
    );
    @POST("User.php")
    @FormUrlEncoded
    Call<ResponseUserDetail> getUpdateDetail(
            @Field("refer_code") String refer_code,
            @Field("id") String id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("gender") String gender,
            @Field("coaching") String coaching,
            @Field("class") String clas,
            @Field("flag") String UpdateDetail
    );
    @POST("User.php")
    @FormUrlEncoded
    Call<ResponseCoaching> getCoaching(
            @Field("flag") String Coaching
    );
    @POST("User.php")
    @FormUrlEncoded
    Call<ResponseClass> getClass(
            @Field("flag") String Class
    );

    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseNotificastion> getNotification(
            @Field("flag") String Notification
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseCoupon> getCoupon(
            @Field("flag") String Coupons
    );

    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseBanner> getBanner(
            @Field("flag") String Banner,
            @Field("admin_id") String id
    );

    @POST("User.php")
    @FormUrlEncoded
    Call<ResponseGetUser> getGetUser(
            @Field("id") String id,
            @Field("flag") String GetUser
    );
    @POST("User.php")
    @FormUrlEncoded
    Call<ResponseUpdateProfile> getUpdateProfile(
            @Field("flag") String UpdateProfile,
            @Field("id") String id,
            @Field("image") String image,
            @Field("name") String name,
            @Field("email") String email
    );

    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseSubByCoaching> getSubjectByClassAndCoaching(
            @Field("flag") String SubjectByClassAndCoaching,
            @Field("class_id") String class_id,
            @Field("coaching_id") String coaching_id,
            @Field("user_id") String user_id
    );

    @POST("Orders.php")
    @FormUrlEncoded
    Call<ResponseVideoPackageOrder> getVideoPackageOrder(
            @Field("flag") String VideoOrders,
            @Field("user_id") String user_id,
            @Field("order_type") String order_type  
    );

    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseCheckAppUpdate> checkAppUpdate(
            @Field("flag") String get_version
    );

/* @Field("transaction_id") String trans_id,*/
    @POST("Orders.php")
    @FormUrlEncoded
    Call<ResponseOrder> getOrder(
            @Field("flag") String Order,
            @Field("user_id") String user_id,
            @Field("package_id") String package_id,
            @Field("method") String method,
            @Field("order_type") String order_type
    );
    @POST("Orders.php")
    @FormUrlEncoded
    Call<ResponseOrder> getPackageOrder(
            @Field("flag") String Order,
            @Field("user_id") String user_id,
            @Field("package_id") String package_id,
            @Field("method") String method,
            @Field("order_type") String order_type,
            @Field("wallet") String wallet,
            @Field("coupon") String coupon,
            @Field("wallet_amount") String wallet_amount,
            @Field("discount") String discount

    );
    @POST("Orders.php")
    @FormUrlEncoded
    Call<ResponseOrder> ComboOrder(
            @Field("flag") String combo_order,
            @Field("user_id") String user_id,
            @Field("id") String id,
            @Field("method") String method,
            @Field("order_type") String order_type
    );
    @POST("Orders.php")
    @FormUrlEncoded
    Call<ResponseMyOrder> getMyOrders(
            @Field("flag") String MyOrders,
            @Field("user_id") String user_id,
            @Field("order_type") String order_type
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseGetChapter> getChapter(
            @Field("flag") String Chapter,
            @Field("package_id") String package_id
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseWalletPoints> getreferPoints(
            @Field("flag") String flag
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseLacture> getLecture(
            @Field("flag") String Lecture,
            @Field("package_id") String package_id,
            @Field("chapter_id") String chapter_id
    );
    @POST("User.php")
    @FormUrlEncoded
    Call<ResponseUserSession> getSession(
            @Field("flag") String UserSession,
            @Field("user_id") String id,
            @Field("session_id") String session_id
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseVideo> getVideo(
            @Field("user_id") String id,
            @Field("flag") String Videos,
            @Field("package_id") String package_id,
            @Field("chapter_id") String chapter_id,
            @Field("lecture_id") String lacture_id
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseNotes> getNotes(
            @Field("flag") String Notes,
            @Field("package_id") String package_id,
            @Field("chapter_id") String chapter_id,
            @Field("lecture_id") String lecture_id
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseNotes> getAllNotes(
            @Field("flag") String AllNotes,
            @Field("package_id") String package_id
    );
    @POST("PrivacyPolicy.php")
    @FormUrlEncoded
    Call<ResponsePrivacyPolicy> getPrivacyPolicy(
            @Field("flag") String PrivacyPolicy
    );
    @POST("HelpSupport.php")
    @FormUrlEncoded
    Call<ResponseHeloSupport> getHelpSupport(
            @Field("flag") String HelpSupport
    );

    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseSimillarProduct> getSimilarProduct(
            @Field("flag") String SimilarProduct,
            @Field("class_id") String class_id,
            @Field("subject_id") String subject_id,
            @Field("coaching_id") String coaching_id
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseLiveSession> getLiveSession(
            @Field("flag") String LiveSession,
            @Field("package_id") String package_id
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponsePkgDetail> getSingleProduct(
            @Field("flag") String SingleProduct,
            @Field("package_id") String package_id
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseLacture> getAllLecture(
            @Field("flag") String AllLecture,
            @Field("package_id") String package_id
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseVideoCountRemove> getVideoCount(
            @Field("flag") String video_view,
            @Field("video_id") String video_id,
            @Field("user_id") String user_id
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseVideo> getAllVideos(
            @Field("user_id") String user_id,
            @Field("flag") String AllVideos,
            @Field("package_id") String package_id
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseAllPackageDetails> getPackageDetails(
            @Field("flag") String SingleProduct,
            @Field("package_id") String package_id
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponsePoints> getPoints(
            @Field("flag") String Convert
    );
    @POST("TermsConditions.php")
    @FormUrlEncoded
    Call<ResponseTerm> getTermsConditions(
            @Field("flag") String TermsConditions
    );

    /******************************************   Live Video  *************************/
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseLive> LiveVideo(
            @Field("flag") String LiveSession,
            @Field("package_id") String c_id
    );
    @POST("live_class.php")
    @FormUrlEncoded
    Call<ResponseJoinClass> joinClass(
            @Field("flag") String join,
            @Field("student_id") String student_id,
            @Field("class_id") String class_id
    );
    @POST("live_class.php")
    @FormUrlEncoded
    Call<ResponseSendMessage> sendMessage(
            @Field("flag") String comments,
            @Field("student_id") String student_id,
            @Field("class_id") String class_id,
            @Field("comments") String comment
    );
    @POST("live_class.php")
    @FormUrlEncoded
    Call<ResponseExitLiveSession> exitLiveSession(
            @Field("flag") String exit,
            @Field("student_id") String student_id,
            @Field("class_id") String class_id
    );

    @POST("User.php")
    @FormUrlEncoded
    Call<ResponseSendToken> sendToken(
            @Field("flag") String token_update,
            @Field("student_id") String student_id,
            @Field("token") String token

    );

    /******************************************    Test Series  ****************************/

    @POST("TestSeries.php")
    @FormUrlEncoded
    Call<ResponseTestSeriese> getTestSeries(
            @Field("flag") String TestSeries
    );
    @POST("TestSeries.php")
    @FormUrlEncoded
    Call<ResponseTestSeriesDetails> getTestSeriesDetails(
            @Field("flag") String Test,
            @Field("testseries_id") String testseries_id,
            @Field("user_id") String user_id
    );
    @POST("Orders.php")
    @FormUrlEncoded
    Call<ResponsePurchasedTestSeries> purchasedTestSeries(
            @Field("flag") String MyOrders,
            @Field("user_id") String user_id,
            @Field("order_type") String order_type
    );
    @POST("TestSeries.php")
    @FormUrlEncoded
    Call<ResponseJumpQuestion> JumptoQuestion(
            @Field("flag") String GetQuestionsList,
            @Field("user_id") String user_id,
            @Field("test_id") String test_id

    );
    @POST("Quiz.php")
    @FormUrlEncoded
    Call<ResponseJumpQuestion> JumptoQuestionQuiz(
            @Field("flag") String GetQuestionsList,
            @Field("user_id") String user_id,
            @Field("test_id") String test_id

    );
    @POST("TestSeries.php")
    @FormUrlEncoded
    Call<ResponseAnswer> getAnswer(
            @Field("flag") String Answer,
            @Field("user_id") String user_id,
            @Field("test_id") String test_id,
            @Field("q_id") String q_id,
            @Field("ans") String ans,
            @Field("status") String status


    );
    @POST("Quiz.php")
    @FormUrlEncoded
    Call<ResponseAnswer> getQuizAnswer(
            @Field("flag") String Answer,
            @Field("user_id") String user_id,
            @Field("test_id") String test_id,
            @Field("q_id") String q_id,
            @Field("ans") String ans,
            @Field("status") String status


    );
    @POST("TestSeries.php")
    @FormUrlEncoded
    Call<ResponseQuestion> getQuestion(
            @Field("flag") String GetQuestions,
            @Field("test_id") String test_id,
            @Field("user_id") String user_id


    );
    @POST("Quiz.php")
    @FormUrlEncoded
    Call<ResponseQuestion> getQuizQuestion(
            @Field("flag") String GetQuestions,
            @Field("test_id") String test_id,
            @Field("user_id") String user_id


    );
    @POST("TestSeries.php")
    @FormUrlEncoded
    Call<ResponseFinishTestSeriese> finishTestSeriese(
            @Field("flag") String finishTest,
            @Field("user_id") String user_id,
            @Field("test_id") String test_id
    );
    @POST("Quiz.php")
    @FormUrlEncoded
    Call<ResponseFinishTestSeriese> finishQuizTestSeriese(
            @Field("flag") String finishTest,
            @Field("user_id") String user_id,
            @Field("test_id") String test_id
    );
    @POST("TestSeries.php")
    @FormUrlEncoded
    Call<ResponseAttemptPaper> attemptPaper(
            @Field("flag") String Attempts,
            @Field("user_id") String user_id,
            @Field("test_id") String test_id,
            @Field("lang") String lang


    );
    @POST("Quiz.php")
    @FormUrlEncoded
    Call<ResponseAttemptPaper> attemptQuiz(
            @Field("flag") String Attempts,
            @Field("user_id") String user_id,
            @Field("test_id") String test_id,
            @Field("lang") String lang


    );
    @POST("TestSeries.php")
    @FormUrlEncoded
    Call<ResponseAnswerList> answerList(
            @Field("flag") String GetCompleteTestsDetails,
            @Field("user_id") String user,
            @Field("test_id") String test_id
    );
    @POST("Quiz.php")
    @FormUrlEncoded
    Call<ResponseAnswerList> QuizanswerList(
            @Field("flag") String GetCompleteTestsDetails,
            @Field("user_id") String user,
            @Field("test_id") String test_id
    );

    @POST("Rank.php")
    @FormUrlEncoded
    Call<ResponseRank> getRank(
            @Field("test_id") String test_id
    );
    @POST("Quiz.php")
    @FormUrlEncoded
    Call<ResponseRank> getQuizRank(
            @Field("flag") String flag,
            @Field("test_id") String test_id
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseComboOffer> getComboOffer(
            @Field("flag") String Get_Combo,
            @Field("coaching_id") String coaching_id,
            @Field("user_id") String user
    );
    @POST("GetData.php")
    @FormUrlEncoded
    Call<ResponseFreeVideo> getFreeVideo(
            @Field("flag") String FreeVideo,
            @Field("coaching_id") String coaching_id
    );
    @POST("Quiz.php")
    @FormUrlEncoded
    Call<ResponseQuiz> getQuiz(
            @Field("flag") String quiz,
            @Field("user_id") String user
    );
}
