package com.emergence.study_app.newTech.app;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferencesManager {

    //app login variables
    private static final String PREF_NAME = "com.viraat.PREF";
    private static final String Full_Name = "Full_Name";
    private static final String City_id = "City_id";
    private static final String State_id = "State_id";
    private static final String Dist_id = "Dist_id";
    private static final String Area_id = "Area_id";
    private static final String Area = "Area";
    private static final String Language = "Language";
    private static final String Session_id = "Session_id";
    private static final String Language_id = "Language_id";
    private static final String State = "State";
    private static final String UserId = "UserId";
    private static final String UserType = "UserType";
    private static final String ProfilePic = "ProfilePic";
    private static final String Contact = "Contact";
    private static final String City = "City";
    private static final String LoginId = "LoginId";
    private static final String Password = "Password";
    private static final String IsVideoUploaded = "IsVideoUploaded";
    private static final String ProfileType = "ProfileType";
    private static final String EmailId = "EmailId";
    private static final String AlternateEmail = "AlternateEmail";
    private static final String MobileNo = "MobileNo";
    private static final String Ref_Id = "Ref_Id";
    private static final String OperationCity = "OperationCity";
    private static final String Total_Ref_Amount = "Total_Ref_Amount";
    private static final String Ref_Count = "Ref_Count";
    private static final String Coaching_id = "coaching_id";
    private static final String Class_id = "class_id";
    private static final String coaching_name = "coaching_name";
    private static final String class_name = "class_name";
    private static final String pkg_id = "pkg_id";
   /* private static final String ProfileType = "ProfileType";
    private static final String ProfileType = "ProfileType";
    private static final String ProfileType = "ProfileType";
    private static final String ProfileType = "ProfileType";
    private static final String ProfileType = "ProfileType";
    private static final String ProfileType = "ProfileType";
    private static final String ProfileType = "ProfileType";*/

    public static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    //for fragment
    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    //for getting instance
    public static synchronized PreferencesManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
        return sInstance;
    }

    public boolean clear() {
        return mPref.edit().clear().commit();
    }


    //Full_Name
    public void setCity_id(String value) {
        mPref.edit().putString(City_id, value).apply();
    }

    public String getCity_id() {
        return mPref.getString(City_id, "");
    }
    //Full_Name
    public void setTotal_Ref_Amount(String value) {
        mPref.edit().putString(Total_Ref_Amount, value).apply();
    }

    public String getTotal_Ref_Amount() {
        return mPref.getString(Total_Ref_Amount, "");
    }
    //Full_Name
    public void setRef_Count(String value) {
        mPref.edit().putString(Ref_Count, value).apply();
    }

    public String getRef_Count() {
        return mPref.getString(Ref_Count, "");
    }
    //Full_Name
    public void setRef_Id(String value) {
        mPref.edit().putString(Ref_Id, value).apply();
    }

    public String getRef_Id() {
        return mPref.getString(Ref_Id, "");
    }
    //Full_Name
    public void setLanguage(String value) {
        mPref.edit().putString(Language, value).apply();
    }

    public String getLanguage() {
        return mPref.getString(Language, "");
    }
    //Full_Name
    public void setState_id(String value) {
        mPref.edit().putString(State_id, value).apply();
    }

    public String getState_id() {
        return mPref.getString(State_id, "");
    }
    //Full_Name
    public void setArea_id(String value) {
        mPref.edit().putString(Area_id, value).apply();
    }

    public String getArea_id() {
        return mPref.getString(Area_id, "");
    }

    //Full_Name
    public void setDist_id(String value) {
        mPref.edit().putString(Dist_id, value).apply();
    }

    public String getDist_id() {
        return mPref.getString(Dist_id, "");
    }
    //Full_Name
    public void setArea(String value) {
        mPref.edit().putString(Area, value).apply();
    }

    public String getArea() {
        return mPref.getString(Area, "");
    }

    //Full_Name
    public void setIsVideoUploaded(String value) {
        mPref.edit().putString(IsVideoUploaded, value).apply();
    }

    public String getIsVideoUploaded() {
        return mPref.getString(IsVideoUploaded, "");
    }





    //firstTime
    public void setIsFirstTimeLaunch(Boolean value) {
        mPref.edit().putBoolean(IS_FIRST_TIME_LAUNCH, value).apply();
    }

    public boolean getIsFirstTimeLaunch() {
        return mPref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    //Full_Name
    public void setFull_Name(String value) {
        mPref.edit().putString(Full_Name, value).apply();
    }

    public String getFull_Name() {
        return mPref.getString(Full_Name, "");
    }

    //email
    public void setEmailId(String value) {
        mPref.edit().putString(EmailId, value).apply();
    }

    public String getEmailId() {
        return mPref.getString(EmailId, "");
    }

    //UserId
    public void setUserId(String value) {
        mPref.edit().putString(UserId, value).apply();
    }

    public String getUserId() {
        return mPref.getString(UserId, "");
    }
    //Isteacher
    public void setSession_id(String value) {
        mPref.edit().putString(Session_id, value).apply();
    }

    public String getSession_id() {
        return mPref.getString(Session_id, "");
    }
    //teacherName
    public void setLanguage_id(String value) {
        mPref.edit().putString(Language_id, value).apply();
    }

    public String getLanguage_id() {
        return mPref.getString(Language_id, "");
    }
    //teacherId
    public void setState(String value) {
        mPref.edit().putString(State, value).apply();
    }

    public String getState() {
        return mPref.getString(State, "");
    }
    //mobile
    public void setMobileNo(String value) {
        mPref.edit().putString(MobileNo, value).apply();
    }

    public String getMobileNo() {
        return mPref.getString(MobileNo, "");
    }

    //UserType
    public void setUserType(String value) {
        mPref.edit().putString(UserType, value).apply();
    }

    public String getUserType() {
        return mPref.getString(UserType, "");
    }

    //ProfilePic
    public void setProfilePic(String value) {
        mPref.edit().putString(ProfilePic, value).apply();
    }

    public String getProfilePic() {
        return mPref.getString(ProfilePic, "");
    }

    //Contact
    public void setContact(String value) {
        mPref.edit().putString(Contact, value).apply();
    }

    public String getContact() {
        return mPref.getString(Contact, "");
    }

    //City
    public void setCity(String value) {
        mPref.edit().putString(City, value).apply();
    }

    public String getCity() {
        return mPref.getString(City, "");
    }

    //LoginId
    public void setLoginId(String value) {
        mPref.edit().putString(LoginId, value).apply();
    }

    public String getLoginId() {
        return mPref.getString(LoginId, "");
    }

    //Password
    public void setPassword(String value) {
        mPref.edit().putString(Password, value).apply();
    }

    public String getPassword() {
        return mPref.getString(Password, "");
    }
    //Coaching id
    public void setCoaching_id(String value) {
        mPref.edit().putString(Coaching_id, value).apply();
    }

    public String getCoaching_id() {
        return mPref.getString(Coaching_id, "");
    }

    //Class id
    public void setClass_id(String value) {
        mPref.edit().putString(Class_id, value).apply();
    }

    public String getClass_id() {
        return mPref.getString(Class_id, "");
    }
    //coaching name
    public void setCoaching_name(String value) {
        mPref.edit().putString(coaching_name, value).apply();
    }

    public String getCoaching_name() {
        return mPref.getString(coaching_name, "");
    }
    //class name
    public void setClass_name(String value) {
        mPref.edit().putString(class_name, value).apply();
    }

    public String getClass_name() {
        return mPref.getString(class_name, "");
    }

    //pkg id
    public void setPkg_id(String value) {
        mPref.edit().putString(pkg_id, value).apply();
    }

    public String getPkg_id() {
        return mPref.getString(pkg_id, "");
    }


}
