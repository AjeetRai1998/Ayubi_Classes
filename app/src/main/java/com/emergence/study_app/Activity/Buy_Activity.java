package com.emergence.study_app.Activity;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Adapter.CouponAdapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Get_User.ResponseGetUser;
import com.emergence.study_app.newTech.retrofit.model.Order.ResponseOrder;
import com.emergence.study_app.newTech.retrofit.model.responseCoupon.ResponseCoupon;
import com.emergence.study_app.newTech.retrofit.model.responsePoints.ResponsePoints;
import com.emergence.study_app.newTech.utils.LoggerUtil;
import com.emergence.study_app.newTech.utils.RandomString;
import com.example.study_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.PaymentApp;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Buy_Activity extends BaseActivity implements PaymentResultListener,Coupon, PaymentStatusListener {
    ImageView menu_icon,back_menu;
    TextView title_tool,course_name,price,mrp,discount,price_s,tv_total_have_pay,tv_cut_price,tv_browse_coupon,tv_apply;
    Button btn_purchase;
    PreferencesManager preferencesManager;
    EasyUpiPayment easyUpiPayment;
    String amount="";
    CheckBox checkbox;
    String coins="";
    String coins_amount="";
    String str_pkgid="";
    String t_lact="";
    String t_chapt="";
    String t_note="";
    String t_video="";
    String t_desc="";
    String sub_name="";
    String ww_l="";
    String mob_no="";
    String u_email="";
    TextView tv_points,tv_wallet_discount;
    String wallet_points="";
    public static String wallet_discount="";
    String temp="";
    EditText et_coupon_code;
    RecyclerView recyclerSearchHistory;
    BottomSheetDialog popupWindow;
    String orders="";
    public static String wallet_Used="false";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        preferencesManager=new PreferencesManager(Buy_Activity.this);
        Checkout.preload(getApplicationContext());
        menu_icon=findViewById(R.id.menu_icon);
        tv_points=findViewById(R.id.tv_points);
        tv_wallet_discount=findViewById(R.id.tv_wallet_discount);
        tv_browse_coupon=findViewById(R.id.tv_browse_coupon);
        tv_apply=findViewById(R.id.tv_apply);
        et_coupon_code=findViewById(R.id.et_coupon_code);
        tv_browse_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CouponPopup();
            }
        });
        tv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tv_total_have_pay=findViewById(R.id.tv_total_have_pay);
        btn_purchase=findViewById(R.id.purchase_btn);
        price=findViewById(R.id.price);
        mrp=findViewById(R.id.mrp);
        checkbox=findViewById(R.id.checkbox);
        checkbox.setTag("false");
        discount=findViewById(R.id.discount);
        price_s=findViewById(R.id.price_s);
        tv_cut_price=findViewById(R.id.tv_cut_price);
        back_menu=findViewById(R.id.back_btn);
        title_tool=findViewById(R.id.main_titile);
        course_name=findViewById(R.id.course_name);
        course_name.setText(getIntent().getStringExtra("course_name"));
        amount=getIntent().getStringExtra("price");
        price.setText("Price "+"\u20B9 "+amount);
        price_s.setText("Price "+"\u20B9 "+amount);
        mrp.setText("MRP "+"\u20B9 "+getIntent().getStringExtra("mrp"));
        discount.setText("Discount "+getIntent().getStringExtra("discount")+"%");
        tv_total_have_pay.setText("\u20B9 "+amount);
        tv_cut_price.setPaintFlags(tv_cut_price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        temp = amount;
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkbox.getTag().equals("false")) {
                    checkbox.setChecked(true);
                    checkbox.setTag("true");
                    tv_cut_price.setVisibility(View.VISIBLE);
                    double am = Double.parseDouble(temp) - Double.parseDouble(wallet_discount);
                    tv_total_have_pay.setText("\u20B9" + am);
                    tv_cut_price.setText("\u20B9 " + temp);
                    tv_points.setText("00");
                    tv_wallet_discount.setVisibility(View.GONE);
                    amount= String.valueOf(am);
                    wallet_Used="true";

                } else {
                    amount = temp;
                    checkbox.setChecked(false);
                    checkbox.setTag("false");
                    tv_cut_price.setVisibility(View.GONE);
                    tv_total_have_pay.setText("\u20B9" + amount);
                    tv_points.setText(wallet_points);
                    tv_wallet_discount.setVisibility(View.VISIBLE);
                    wallet_Used="false";


                }
            }
        });

        t_lact=getIntent().getStringExtra("t_lact");
        t_chapt=getIntent().getStringExtra("t_chapt");
        t_note=getIntent().getStringExtra("t_note");
        t_video=getIntent().getStringExtra("t_video");
        t_desc=getIntent().getStringExtra("t_desc");
        sub_name=getIntent().getStringExtra("sub_name");
        ww_l=getIntent().getStringExtra("ww_l");
        str_pkgid=getIntent().getStringExtra("package_id");

        String str_id=preferencesManager.getUserId();

        menu_icon.setVisibility(View.GONE);
        back_menu.setVisibility(View.VISIBLE);

        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startPayment();

                Intent intent=new Intent(Buy_Activity.this, Checksum.class);
                intent.putExtra("orderid",System.currentTimeMillis()+"");
                intent.putExtra("amount",amount);
                intent.putExtra("str_pkgid",str_pkgid);
                intent.putExtra("type","Package");
                intent.putExtra("custid",preferencesManager.getUserId());
                startActivity(intent);

//                UPIPayment();


            }
        });
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title_tool.setText("Payment");
        getUser();
    }

    private void getUser() {
        showLoading();
        ApiServices user_api= API_Config.getApiClient_ByPost();
        Call<ResponseGetUser> userCall=user_api.getGetUser(preferencesManager.getUserId(),"GetUser");
        userCall.enqueue(new Callback<ResponseGetUser>() {
            @Override
            public void onResponse(Call<ResponseGetUser> call, Response<ResponseGetUser> response) {

                if (response.body().getRes().equalsIgnoreCase("success")){
                    mob_no=response.body().getData().getMobile();
                    u_email=response.body().getData().getEmail();
                    if (response.body().getData().getWallet().equalsIgnoreCase("0")){
                        tv_points.setText("00");
                        wallet_points="00";
                        checkbox.setVisibility(View.GONE);
                        tv_wallet_discount.setVisibility(View.GONE);
                    }else {
                        tv_points.setText(response.body().getData().getWallet());
                        wallet_points=response.body().getData().getWallet();
                        checkbox.setVisibility(View.VISIBLE);
                        tv_wallet_discount.setVisibility(View.VISIBLE);
                    }

                }else {
                }
                getPonts();
            }
            @Override
            public void onFailure(Call<ResponseGetUser> call, Throwable t) {
                hideLoading();
            }
        });
    }
    private void getPonts() {
        ApiServices user_api= API_Config.getApiClient_ByPost();
        Call<ResponsePoints> userCall=user_api.getPoints("Convert");
        userCall.enqueue(new Callback<ResponsePoints>() {
            @Override
            public void onResponse(Call<ResponsePoints> call, Response<ResponsePoints> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    // tv_points.setText(response.body().getData().getPoints()+" = \u20B9"+response.body().getData().getAmount());

                    if (wallet_points.equalsIgnoreCase("00")){
                        tv_wallet_discount.setText("");
                        tv_wallet_discount.setVisibility(View.GONE);
                    }else {
                        if (Integer.parseInt(wallet_points)>Integer.parseInt(response.body().getData().getPoints())){
                            tv_wallet_discount.setVisibility(View.VISIBLE);
                            double disc=Double.parseDouble(wallet_points)/Double.parseDouble(response.body().getData().getPoints());
                           double amount_disc=disc*Double.parseDouble(response.body().getData().getAmount());
                            tv_wallet_discount.setText("You will get discount of Rs. \u20B9 "+amount_disc);
                            wallet_discount= String.valueOf(amount_disc);
                        }
                    }
                }else {
                }
            }
            @Override
            public void onFailure(Call<ResponsePoints> call, Throwable t) {
                hideLoading();
            }
        });
    }

    public void UPIPayment(){

        EasyUpiPayment.Builder  builder= new EasyUpiPayment.Builder(this)
                .with(PaymentApp.ALL)
                .setPayeeVpa("q93880757@ybl")
                .setPayeeName("Ayubi Classes")
                .setTransactionId(System.currentTimeMillis()+"")
                .setTransactionRefId(System.currentTimeMillis()+"")
                .setPayeeMerchantCode(System.currentTimeMillis()+"")
                .setDescription("Payment for Ayubi Classes")
                .setAmount("1.00");

        try {
            // Build instance
            easyUpiPayment = builder.build();

            // Register Listener for Events
            easyUpiPayment.setPaymentStatusListener(this);

            // Start payment / transaction
            easyUpiPayment.startPayment();
        } catch (Exception exception) {
            exception.printStackTrace();
            Toast.makeText(getApplicationContext(), exception.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {

        switch (transactionDetails.getTransactionStatus()) {
            case SUCCESS:
                onTransactionSuccess();
                break;
            case FAILURE:
                onTransactionFailed();
                break;
            case SUBMITTED:
                onTransactionSubmitted();
                break;
        }
    }

    @Override
    public void onTransactionCancelled() {
        // Payment Cancelled by User
        Toast.makeText(Buy_Activity.this,"Cancelled by user",Toast.LENGTH_LONG).show();
    }

    private void onTransactionSuccess() {
        showMessage("Payment success");
        getOrder(str_pkgid,t_lact,t_chapt,t_note,t_video,t_desc,sub_name,ww_l);

    }

    private void onTransactionSubmitted() {
        // Payment Pending
        Toast.makeText(Buy_Activity.this,"Pending | Submitted",Toast.LENGTH_LONG).show();

    }

    private void onTransactionFailed() {
        // Payment Failed
        Toast.makeText(Buy_Activity.this,"Failed",Toast.LENGTH_LONG).show();

    }


    private void startPayment() {
        Checkout checkout = new Checkout();

        //amount

        float wholeamt = Float.parseFloat(String.valueOf("1"));
        wholeamt = wholeamt * 100;
        String pay= String.valueOf(Math.round(wholeamt));
        checkout.setKeyID("rzp_live_m1tA6Dl5RBDypq");

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
//            options.put("order_id", ""+System.currentTimeMillis()+RandomString.alphanum);//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", pay);//pass amount in currency subunits
            options.put("prefill.email", preferencesManager.getEmailId());
            options.put("prefill.contact",preferencesManager.getMobileNo());
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }


    }
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
//        getOrder(razorpayPaymentID,str_pkgid,t_lact,t_chapt,t_note,t_video,t_desc,sub_name,ww_l);
        /**
         * Add your logic here for a successful payment response
         */
    }

    @Override
    public void onPaymentError(int code, String response) {
        /**
         * Add your logic here for a failed payment response
         */
    }


    private void getOrder(String str_pkgid,String t_chapt,String t_lact,String t_note,String t_video,String desc,String sub_name, String ww_l) {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseOrder> orderCall=apiServices.getPackageOrder("Order",preferencesManager.getUserId(),str_pkgid,"upi","Package",Buy_Activity.wallet_Used,Packages_Detail_Activity.is_Coupon,wallet_discount,Packages_Detail_Activity.Coupon_disc);
        orderCall.enqueue(new Callback<ResponseOrder>() {
            @Override
            public void onResponse(Call<ResponseOrder> call, Response<ResponseOrder> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
//                    Intent intent=new Intent(getApplicationContext(),Purchased_pkg_Activity.class);
//                  intent.putExtra("pack_id",response.body().getData().getId());
//                    showMessage(response.body().getMsg());
//                    showMessage(response.body().getData().getId()+"id");
//                    startActivity(intent);
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
    private void CouponPopup() {
        View promptView = getLayoutInflater().inflate(R.layout.coupon_popup, null);
        popupWindow = new BottomSheetDialog(this);
        popupWindow.setContentView(promptView);
        popupWindow.show();
        popupWindow.setCancelable(false);
        recyclerSearchHistory=promptView.findViewById(R.id.recycler_search_history);
        ImageView iv_cancel=promptView.findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        addRecycler();


    }
    private void addRecycler() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseCoupon> call=apiServices.getCoupon("Coupons");
        call.enqueue(new Callback<ResponseCoupon>() {
            @Override
            public void onResponse(Call<ResponseCoupon> call, Response<ResponseCoupon> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().getRes().equalsIgnoreCase("success")){
                    recyclerSearchHistory.setHasFixedSize(true);
                    recyclerSearchHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerSearchHistory.setAdapter(new CouponAdapter(getApplicationContext(),response.body().getData(),Buy_Activity.this));

                }else {
                    popupWindow.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseCoupon> call, Throwable t) {

                hideLoading();
            }
        });

    }

    @Override
    public void ApplyCoupon(String coupon_id, String coupon_code, String coupon_discount, String min_order_amount) {



    }
}