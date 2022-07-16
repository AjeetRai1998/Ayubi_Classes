package com.emergence.study_app.Activity;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emergence.study_app.Adapter.ComboPackageAdapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Order.ResponseOrder;
import com.emergence.study_app.newTech.retrofit.model.responseComboOffer.JsonMemberPackageItem;
import com.emergence.study_app.newTech.retrofit.model.responseComboOffer.TestseriesItem;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComboDetailsActivity extends BaseActivity implements PaymentResultListener {
    ImageView iv_back,image;
    TextView tv_desc,tv_name,tv_price,price_rec,cut_price_rec;
    RecyclerView recycler_package,recycler_testSeriese;
    public static List<JsonMemberPackageItem> pack_list=new ArrayList();
    public static List<TestseriesItem> test_list=new ArrayList();
    String price="";
    String comb_Id="";
    PreferencesManager preferencesManager;
    TextView tv_purchase;
    String purchase="";
    RelativeLayout rl_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_details);

        preferencesManager=new PreferencesManager(ComboDetailsActivity.this);
        iv_back=findViewById(R.id.iv_back);
        cut_price_rec=findViewById(R.id.cut_price_rec);
        price_rec=findViewById(R.id.price_rec);
        rl_pay=findViewById(R.id.rl_pay);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_purchase=findViewById(R.id.tv_purchase);
        tv_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
        tv_name=findViewById(R.id.tv_name);
        tv_desc=findViewById(R.id.tv_desc);
        image=findViewById(R.id.image);
        tv_price=findViewById(R.id.tv_price);
        recycler_package=findViewById(R.id.recycler_package);
        recycler_testSeriese=findViewById(R.id.recycler_testSeriese);
        tv_name.setText(getIntent().getStringExtra("name"));
        String img=getIntent().getStringExtra("image");
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("image")).into(image);
        tv_desc.setText(getIntent().getStringExtra("desc"));
        price=getIntent().getStringExtra("price");
        tv_price.setText("\u20B9 "+price);
        comb_Id=getIntent().getStringExtra("id");
        price_rec.setText("\u20B9 "+price);
        cut_price_rec.setText("\u20B9 "+getIntent().getStringExtra("cut_price"));
        cut_price_rec.setPaintFlags(cut_price_rec.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        aDDpACKAGE();


        purchase=getIntent().getStringExtra("purchase");
        if (purchase.equalsIgnoreCase("yes")){
            rl_pay.setVisibility(View.GONE);
        }else {
            rl_pay.setVisibility(View.VISIBLE);
        }
    }
    private void getOrder() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseOrder> orderCall=apiServices.ComboOrder("combo_order",preferencesManager.getUserId(),comb_Id,"upi","");
        orderCall.enqueue(new Callback<ResponseOrder>() {
            @Override
            public void onResponse(Call<ResponseOrder> call, Response<ResponseOrder> response) {
                hideLoading();
                Utils.CheckUserSession(ComboDetailsActivity.this,preferencesManager.getUserId(),preferencesManager.getSession_id());
                if (response.body().getRes().equalsIgnoreCase("success")){
                    finish();
                }else {
                }
            }

            @Override
            public void onFailure(Call<ResponseOrder> call, Throwable t) {
                hideLoading();
                showMessage(t.getMessage().toString());


            }
        });
    }
    private void aDDpACKAGE() {
        recycler_package.setHasFixedSize(true);
        recycler_package.setNestedScrollingEnabled(false);
        recycler_testSeriese.setHasFixedSize(true);
        recycler_testSeriese.setNestedScrollingEnabled(false);
        recycler_package.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        recycler_testSeriese.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
       if (test_list.size()!=0){
           recycler_testSeriese.setAdapter(new ComboTestAdapter(getApplicationContext(),test_list));

       }

       if (pack_list.size()!=0){
           recycler_package.setAdapter(new ComboPackageAdapter(getApplicationContext(),pack_list));

       }

    }

    private void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setImage(R.mipmap.ic_launcher);
        final Activity activity = this;
        float wholeamt = Float.parseFloat(String.valueOf(price));
        wholeamt = wholeamt * 100;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("description", R.string.app_name);
            jsonObject.put("currency", "INR");
            jsonObject.put("amount", wholeamt);
            jsonObject.put("payment_capture", false);
            checkout.open(activity, jsonObject);

            Log.e("CheckOutRazor", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        getOrder();

    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}