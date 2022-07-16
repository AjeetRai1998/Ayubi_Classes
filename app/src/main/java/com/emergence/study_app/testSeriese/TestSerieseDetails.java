package com.emergence.study_app.testSeriese;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Activity.Buy_Activity;
import com.emergence.study_app.Activity.Checksum;
import com.emergence.study_app.Adapter.TestSeriesDetailAdapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Get_User.ResponseGetUser;
import com.emergence.study_app.newTech.retrofit.model.Order.ResponseOrder;
import com.emergence.study_app.newTech.retrofit.model.responseTestSeriesDetails.ResponseTestSeriesDetails;
import com.example.study_app.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestSerieseDetails extends BaseActivity implements PaymentResultListener {
    ImageView iv_back;
    RecyclerView recycler_paid_series;
    String test_Id="";
    String price="";
    TextView tv_title,tv_payable_amount,tv_pay_now;
    PreferencesManager preferencesManager;
    RelativeLayout relative_bottom;
    String mob_no="";
    String u_email="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_seriese_details);
        preferencesManager=new PreferencesManager(TestSerieseDetails.this);
        Checkout.preload(getApplicationContext());
        iv_back=findViewById(R.id.iv_back);
        relative_bottom=findViewById(R.id.relative_bottom);
        recycler_paid_series=findViewById(R.id.recycler_paid_series);
        test_Id=getIntent().getStringExtra("id");
        price=getIntent().getStringExtra("price");
        if (price.equalsIgnoreCase("combo")){
            relative_bottom.setVisibility(View.GONE);
        }else {
            relative_bottom.setVisibility(View.VISIBLE);
        }
        tv_title=findViewById(R.id.tv_title);
        tv_payable_amount=findViewById(R.id.tv_payable_amount);
        tv_payable_amount.setText("\u20B9 "+price);
        tv_title.setText(getIntent().getStringExtra("name"));
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_pay_now=findViewById(R.id.tv_pay_now);
        tv_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startPayment();

                Intent intent=new Intent(TestSerieseDetails.this, Checksum.class);
                intent.putExtra("orderid",System.currentTimeMillis()+"");
                intent.putExtra("amount",price);
                intent.putExtra("str_pkgid",test_Id);
                intent.putExtra("type","TestSeries");
                intent.putExtra("custid",preferencesManager.getUserId());
                startActivity(intent);
            }
        });
        freecoursesRecycler();
    }
    private void startPayment() {
        Checkout checkout = new Checkout();

        float wholeamt = Float.parseFloat(String.valueOf(price));
        wholeamt = wholeamt * 100;
        String pay= String.valueOf(Math.round(wholeamt));
        checkout.setKeyID("rzp_live_m1tA6Dl5RBDypq");


//        checkout.setKeyID("rzp_live_m1tA6Dl5RBDypq");
//        checkout.setImage(R.mipmap.ic_launcher);
//        final Activity activity = this;
//        float wholeamt = Float.parseFloat(String.valueOf(amount));
//        wholeamt = wholeamt * 100;
//        String pay= String.valueOf(Math.round(wholeamt));
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("description", R.string.app_name);
//            jsonObject.put("currency", "INR");
//            jsonObject.put("amount", pay);
//            jsonObject.put("prefill.email", u_email);
//            jsonObject.put("prefill.contact", mob_no);
//            jsonObject.put("payment_capture", false);
//            checkout.open(activity, jsonObject);
//
//            Log.e("CheckOutRazor", jsonObject.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }






        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Ayubis Info Education Private Limited");
            options.put("description", R.string.app_name);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", pay);//pass amount in currency subunits
            options.put("prefill.email", u_email);
            options.put("prefill.contact",mob_no);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }


    }
    private void getUser() {
        ApiServices user_api= API_Config.getApiClient_ByPost();
        Call<ResponseGetUser> userCall=user_api.getGetUser(preferencesManager.getUserId(),"GetUser");
        userCall.enqueue(new Callback<ResponseGetUser>() {
            @Override
            public void onResponse(Call<ResponseGetUser> call, Response<ResponseGetUser> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    mob_no=response.body().getData().getMobile();
                    u_email=response.body().getData().getEmail();


                }else {
                }
            }
            @Override
            public void onFailure(Call<ResponseGetUser> call, Throwable t) {
                hideLoading();
            }
        });
    }


    private void getOrder(String trans_id) {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseOrder> orderCall=apiServices.getOrder("Order",preferencesManager.getUserId(),test_Id,"upi","TestSeries");
        orderCall.enqueue(new Callback<ResponseOrder>() {
            @Override
            public void onResponse(Call<ResponseOrder> call, Response<ResponseOrder> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
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

    private void freecoursesRecycler() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseTestSeriesDetails> couresesCall=apiServices.getTestSeriesDetails("Test",test_Id,preferencesManager.getUserId());
        couresesCall.enqueue(new Callback<ResponseTestSeriesDetails>() {
            @Override
            public void onResponse(Call<ResponseTestSeriesDetails> call, Response<ResponseTestSeriesDetails> response) {
                getUser();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    recycler_paid_series.setHasFixedSize(true);
                    recycler_paid_series.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    TestSeriesDetailAdapter adapter=new TestSeriesDetailAdapter(getApplicationContext(),response.body().getData());
                    recycler_paid_series.setAdapter(adapter);
                }else {
                }
            }

            @Override
            public void onFailure(Call<ResponseTestSeriesDetails> call, Throwable t) {

                hideLoading();
            }
        });

    }

    @Override
    public void onPaymentSuccess(String s) {
        getOrder(s);

    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}