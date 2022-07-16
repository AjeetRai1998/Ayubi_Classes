package com.emergence.study_app.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import androidx.annotation.RequiresApi;

import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Order.ResponseOrder;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Checksum extends BaseActivity implements PaytmPaymentTransactionCallback {

    private static final String TAG = Checksum.class.getSimpleName();
    String custid = "", orderId = "";
    String amount="",order_status="",memberID="";
    public static String pre_amount="",pre_discount="",pre_balance="";

    ProgressDialog pd;
    public  static Activity Checksum;
    String mid,param;
    View parentLayout;
    String acc_no="";
    String type="";

    String CHECKSUMHASH = "";
    PreferencesManager preferencesManager;
    String str_pkgid;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        preferencesManager=new PreferencesManager(Checksum.this);

        Checksum=this;
       // Constaints.Checksum=1;
        Intent intent = getIntent();
        orderId =intent.getExtras().getString("orderid");
        custid = intent.getExtras().getString("custid");
        amount =intent.getExtras().getString("amount");
        str_pkgid =intent.getExtras().getString("str_pkgid");
        type =intent.getExtras().getString("type");

        int am= Math.toIntExact(Math.round(Double.parseDouble(amount)));
        amount=""+am;



        mid = "jCcPoy23145470928731";//LIeaHI55471047333459
        parentLayout = findViewById(android.R.id.content);


        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);




    }

    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(Checksum.this);

        //private String orderId , mid, custid, amt;
        String url = "https://ayubiclasses.in/paytm_checksum.php";
//        String url = "http://sib.cluster18.com/Paytm/generateChecksum.php";
        String varifyurl = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID"+orderId;
       // String varifyurl = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
        // "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID"+orderId;
        // "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID"+orderId;

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        protected String doInBackground(ArrayList<String>... alldata) {

            JSONParser jsonParser = new JSONParser(Checksum.this);
             param =
                            "MID=" + mid +
                            "&ORDER_ID=" + orderId +
                            "&CUST_ID=" + custid +
                            "&CHANNEL_ID=WAP&TXN_AMOUNT="+amount+"&WEBSITE=DEFAULT" +
                            "&CALLBACK_URL=" + varifyurl + "&INDUSTRY_TYPE_ID=Retail";

            JSONObject jsonObject = jsonParser.makeHttpRequest(url, "POST", param);
            // yaha per checksum ke saht order id or status receive hoga..
            // Log.e("CheckSum result >>",jsonObject.toString());
            if (jsonObject != null) {
                // Log.e("CheckSum result >>",jsonObject.toString());
                try {
                    // CHECKSUMHASH=jsonObject.getString("CHECKSUMHASH");
                    CHECKSUMHASH = jsonObject.has("CHECKSUMHASH") ? jsonObject.getString("CHECKSUMHASH") : "";
                    Log.e("CheckSum result >>", CHECKSUMHASH);

//                    Toast.makeText(getApplicationContext(), CHECKSUMHASH, Toast.LENGTH_SHORT).show();

                }
                catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
            return CHECKSUMHASH;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ", "  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

//            PaytmPGService Service = PaytmPGService.getStagingService("");

            // when app is ready to publish use production service
             PaytmPGService Service = PaytmPGService.getProductionService();

            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
            HashMap<String, String> paramMap = new HashMap<String, String>();
            //these are mandatory parameters
            paramMap.put("MID", mid); //MID provided by paytm
            paramMap.put("ORDER_ID", orderId);
            paramMap.put("CUST_ID", custid);
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT", amount);//amount
            paramMap.put("WEBSITE", "DEFAULT");
            paramMap.put("CALLBACK_URL", varifyurl);
            //paramMap.put( "EMAIL" , "abc@gmail.com");   // no need
            // paramMap.put( "MOBILE_NO" , "9144040888");  // no need
            paramMap.put("CHECKSUMHASH", CHECKSUMHASH);
            //paramMap.put("PAYMENT_TYPE_ID" ,"CC");    // no need
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");

            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum ", "param " + paramMap.toString());
            Service.initialize(Order, null);
            // start payment service call here
            Service.startPaymentTransaction(Checksum.this, true, true, Checksum.this);

// transaction
        }

    }


    @Override
    public void onTransactionResponse(final Bundle bundle) {
        if (bundle!=null){
            if (bundle.get("STATUS").equals("TXN_SUCCESS")){
                getOrder();
                showMessage(bundle.get("STATUS").toString());
            }else {
                showMessage(bundle.get("STATUS").toString());
                finish();
            }
        }else {
            finish();
        }
    }

    @Override
    public void networkNotAvailable() {
        showMessage("No network");
        finish();
    }

    @Override
    public void onErrorProceed(String s) {
        showMessage(s.toString());
        finish();

    }


    @Override
    public void clientAuthenticationFailed(String s) {
        showMessage(s);
        finish();
    }

    @Override
    public void someUIErrorOccurred(String s) {
        showMessage(s);
        finish();
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        showMessage("error loading webpage");
        finish();
    }

    @Override
    public void onBackPressedCancelTransaction() {
        showMessage("on backpress cancel transaction");
        finish();
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        showMessage(s);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }

    private void getOrder() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseOrder> orderCall=apiServices.getPackageOrder("Order",preferencesManager.getUserId(),str_pkgid,"upi",type,Buy_Activity.wallet_Used,Packages_Detail_Activity.is_Coupon,Buy_Activity.wallet_discount,Packages_Detail_Activity.Coupon_disc);
        orderCall.enqueue(new Callback<ResponseOrder>() {
            @Override
            public void onResponse(Call<ResponseOrder> call, Response<ResponseOrder> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    Buy_Activity.wallet_Used="false";
                    Packages_Detail_Activity.is_Coupon="";
                    Buy_Activity.wallet_discount="";
                    Packages_Detail_Activity.Coupon_disc="";
                    showMessage(response.body().getMsg());
                    startActivity(new Intent(Checksum.this,MainActivity.class));
                    finish();

                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponseOrder> call, Throwable t) {
                hideLoading();


            }
        });
    }




}
