package com.emergence.study_app.newTech.constants;

import static com.emergence.study_app.newTech.app.AppConfig.PAYLOAD_BUNDLE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.example.study_app.R;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.MvpView;
import com.emergence.study_app.newTech.retrofit.ServiceGenerator;
import com.emergence.study_app.newTech.utils.DialogUtil;
import com.emergence.study_app.newTech.utils.LoggerUtil;
import com.emergence.study_app.newTech.utils.NetworkConnectionChecker;
import com.emergence.study_app.newTech.utils.Utils;
import com.google.gson.JsonObject;

import java.util.Locale;
import java.util.Random;


@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements
        NetworkConnectionChecker.OnConnectivityChangedListener, View.OnClickListener, MvpView {
    private ProgressDialog mProgressDialog;
    private static final String TAG = "BaseActivity";
    protected static final int PHONE_STATE_PERMISSION_REQUEST_CODE = 12;
    public Activity context;
    public ApiServices apiServices, createServiceUtilityV2;
    public PreferencesManager preferencesManager;
//    String keys = BuildConfig.ENCRYPTION_KEY;
//    public SecretKey easypay_key = new SecretKeySpec(keys.getBytes(), "AES");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        apiServices = ServiceGenerator.createService(ApiServices.class);
        createServiceUtilityV2 = ServiceGenerator.createServiceUtilityV2(ApiServices.class);
        PreferencesManager.initializeInstance(context);
//        setLangRecreate(PreferencesManager.getInstance(context).getLANGUAGE(), false);
    }

    public String getColoredSpanned(String text, String color) {
        return "<font color=" + color + ">" + text + "</font>";
    }

    public JsonObject encryptBody(JsonObject param) {
        JsonObject body = new JsonObject();
        try {
//            body.addProperty("body", AESEncDec.encryptMsg(param.toString(), easypay_key));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

//    @Override
//    public void uncaughtException(Thread paramThread,
//                                  Throwable paramThrowable) {
//        Log.e("Alert", "Restarting app !!!");
//        Intent intent = getBaseContext().getPackageManager()
//                .getLaunchIntentForPackage( getBaseContext().getPac
//                        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTI
//                                startActivity(intent);
//        System.exit(2);
//    }

    public void logoutDialog(Activity context, Class<?> activity) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Logout");
        builder1.setMessage("Do you really want to logout?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                (dialog, id) -> {
                    PreferencesManager.getInstance(context).clear();
                    goToActivityWithFinish(context, activity, null);
                    dialog.cancel();
                });

        builder1.setNegativeButton("No", (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

   /* @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/

    public void setLangRecreate(String langval, boolean bool) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(langval);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        if (bool)
            recreate();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void showLoading() {
        mProgressDialog = DialogUtil.showLoadingDialog(BaseActivity.this, "Base Activity");
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    public void onError(int resId) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void connectivityChanged(boolean availableNow) {

    }

    public void createInfoDialog(Context context, String title,
                                 String msg) {
        androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        androidx.appcompat.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public String generatePin() {
        Random random = new Random();
        @SuppressLint("DefaultLocale") String randomPIN = String.format("%04d", random.nextInt(10000));

        return randomPIN;
    }

    @Override
    public void sendUnUsedEPin(String epinNo, String from) {
    }

    public void hideSoftKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        View v = activity.getCurrentFocus();
        if (v != null) {
            IBinder binder = activity.getCurrentFocus().getWindowToken();
            if (binder != null) {
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(binder, 0);
                }
            }
        }
    }

    public void goToActivity(Activity activity, Class<?> classActivity, Bundle bundle) {
        Utils.hideSoftKeyboard(activity);
        Intent intent = new Intent(activity, classActivity);
       // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if (bundle != null)
            intent.putExtra(PAYLOAD_BUNDLE, bundle);
        activity.startActivity(intent);
    }

    public void goToActivityWithFinish(Activity activity, Class<?> classActivity, Bundle bundle) {
        Intent intent = new Intent(context, classActivity);
       // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if (bundle != null)
            intent.putExtra(PAYLOAD_BUNDLE, bundle);
        Utils.hideSoftKeyboard(activity);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void finishActivity(Activity activity) {
        Utils.hideSoftKeyboard(activity);
        activity.finish();
    }


    protected static final int PERMISSION_REQUEST_AUDIO_VIDEO = 15;

    public void hasAudioVideoStateContactPermission() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            requestAudioVideoStateContactPermission();
    }

    public void requestAudioVideoStateContactPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Utils.createSimpleDialog1(context, getString(R.string.alert_text), getString(R.string.permission_camera_rationale11), getString(R.string.reqst_permission), new Utils.Method() {
                @Override
                public void execute() {
                    ActivityCompat.requestPermissions(BaseActivity.this, new String[]{
                            Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA}, PERMISSION_REQUEST_AUDIO_VIDEO);
                }
            });

        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_AUDIO_VIDEO);
        }
    }


    protected static final int STORAGE_PERMISSION_REQUEST_CODE = 16;

    public void hasStoragePermission() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.PACKAGE_USAGE_STATS) != PackageManager.PERMISSION_GRANTED
        )
            requestStoragePermission();
    }

    public void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.PACKAGE_USAGE_STATS)) {
            Utils.createSimpleDialog1(context, getString(R.string.alert_text), getString(R.string.permission_camera_rationale11), getString(R.string.reqst_permission), new Utils.Method() {
                @Override
                public void execute() {
                    ActivityCompat.requestPermissions(BaseActivity.this, new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.PACKAGE_USAGE_STATS},
                            STORAGE_PERMISSION_REQUEST_CODE);
                }
            });

        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.PACKAGE_USAGE_STATS
                    },
                    STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    public void shareDataText(String shareBody, String shareSubject){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setPackage("com.facebook.katana");
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(sharingIntent);
    }

    public void SharingToSocialMedia(String application, String shareBody) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);

        boolean installed = checkAppInstall(application);
        if (installed) {
            intent.setPackage(application);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Install application first", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkAppInstall(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

}