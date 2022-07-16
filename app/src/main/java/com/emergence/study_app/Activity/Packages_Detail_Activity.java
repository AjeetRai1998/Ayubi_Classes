package com.emergence.study_app.Activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emergence.study_app.Adapter.CouponAdapter;
import com.emergence.study_app.Adapter.Simillar_Product_Adapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.emergence.study_app.newTech.retrofit.model.SimillarProduct.DataItem;
import com.emergence.study_app.newTech.retrofit.model.SimillarProduct.ResponseSimillarProduct;
import com.emergence.study_app.newTech.retrofit.model.Subject.PackagesItem;
import com.emergence.study_app.newTech.retrofit.model.Subject.VideoItem;
import com.emergence.study_app.newTech.retrofit.model.responseCoupon.ResponseCoupon;
import com.emergence.study_app.newTech.utils.LoggerUtil;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Packages_Detail_Activity extends BaseActivity implements Coupon{
    TextView t_coursename,title,price,cut_price,desription,wwlearn,buy_price,sm_txt,tv_browse_coupon,tv_apply;
    TextView ct_chapt,ct_lacture,ct_notes,ct_video;
    ImageView menu_icon,back_icon,img_sub;
    LinearLayout view_detail_price,lyt_chapt,lyt_lact,lyt_notes,lyt_video;
    RecyclerView rec_simmilar;
    Button btn_buy;
    CircleImageView usr_profile;
    PreferencesManager preferencesManager;
    public static PackagesItem data;
    public static VideoItem Videodata;
    CardView buy_lay;
    EditText et_coupon_code;
    RecyclerView recyclerSearchHistory;
    BottomSheetDialog popupWindow;
    String amount="";
    String amount_befor_disc="";
    public static String Coupon_Id="";
    public static String Coupon_disc="";
    TextView tv_ihc_charge;
    double handling_charge=0;
    List<DataItem> slist=new ArrayList<>();
    public static String is_Coupon="";
    List<com.emergence.study_app.newTech.retrofit.model.responseCoupon.DataItem> couponList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages_detail);
        preferencesManager=new PreferencesManager(Packages_Detail_Activity.this);
        t_coursename=findViewById(R.id.sub_name);

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
                if (tv_apply.getText().equals("Apply")) {
                    for (int i = 0; i < couponList.size(); i++) {
                        if (couponList.get(i).getCode().equals(et_coupon_code.getText().toString())) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date strDate = null;
                            Date strDate1=null;
                            try {
                                strDate = sdf.parse(couponList.get(i).getValid());
                                strDate1 = sdf.parse(Utils.getDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (strDate1.after(strDate)) {
                                Toast.makeText(context, "This Coupon has expired ! \n Please select another Coupon", Toast.LENGTH_SHORT).show();
                            }else {
                                int temp = Integer.parseInt(amount_befor_disc);
                                if (Double.parseDouble(amount) >= Double.parseDouble(couponList.get(i).getCriteria())) {
                                    int t_amount = temp - Integer.parseInt(couponList.get(i).getDiscount());
                                    buy_price.setText("\u20B9 " + t_amount);
                                    et_coupon_code.setText(couponList.get(i).getCode());
                                    tv_apply.setText("Applied");
                                    amount = String.valueOf(t_amount);
                                    Coupon_disc = couponList.get(i).getDiscount();
                                    Coupon_Id = couponList.get(i).getId();
                                    showMessage("Coupon Applied !");
                                } else {
                                    showMessage("You can apply coupon on order above \u20B9 " + couponList.get(i).getCriteria());
                                }

                            }

                            break;
                        } else {
                            if (i == couponList.size() - 1)
                                showMessage("No such Coupon code available !");

                        }
                    }

                }else {
                    showMessage("Coupon Already Applied !");
                }
            }
        });
        buy_lay=findViewById(R.id.buy_lay);

//        if (data.getIsPurchased().equalsIgnoreCase("yes")){
//            buy_lay.setVisibility(View.GONE);
//        }else {
//            buy_lay.setVisibility(View.VISIBLE);
//        }

        usr_profile=findViewById(R.id.usr_profile);
        lyt_chapt=findViewById(R.id.lyt_chapt);
        lyt_lact=findViewById(R.id.lyt_lact);
        lyt_notes=findViewById(R.id.lyt_notes);
        lyt_video=findViewById(R.id.lyt_video);
        sm_txt=findViewById(R.id.sm_txt);
        title=findViewById(R.id.main_titile);
        view_detail_price=findViewById(R.id.view_price_detail);
        menu_icon=findViewById(R.id.menu_icon);
        buy_price=findViewById(R.id.buy_price);
        desription=findViewById(R.id.description);
        ct_chapt=findViewById(R.id.ct_chapt);
        ct_lacture=findViewById(R.id.ct_lacture);
        ct_notes=findViewById(R.id.ct_notes);
        ct_video=findViewById(R.id.ct_video);
        back_icon=findViewById(R.id.back_btn);
        img_sub=findViewById(R.id.img_sub);
        price=findViewById(R.id.price);
        btn_buy=findViewById(R.id.buy_btn);
        cut_price=findViewById(R.id.cut_price);
        wwlearn=findViewById(R.id.wwlearn);
        rec_simmilar=findViewById(R.id.simmilar_recycler);
        sm_txt.setVisibility(View.GONE);

//        preferencesManager.setPkg_id(data.getId());

        if (getIntent().getStringExtra("from").equalsIgnoreCase("video")){
            price.setText("\u20B9"+Videodata.getPrice());

            cut_price.setText("\u20B9"+Videodata.getMrp());
            ct_chapt.setText(""+Videodata.getTotalChapters()+" Subjects");
            ct_lacture.setText(""+Videodata.getTotalLectures()+" Chapter");
            ct_notes.setText(""+Videodata.getTotalNotes()+" Notes");
            ct_video.setText(""+Videodata.getTotalVideos()+" Video");
            amount=Videodata.getPrice();
            amount_befor_disc=amount;
            Coupon_disc="";
            handling_charge=(Integer.parseInt(amount)*2.6)/100;
            amount= String.valueOf(Integer.parseInt(amount)+handling_charge);
            buy_price.setText("\u20B9"+amount);
//        showMessage(handling_charge+"\n"+amount);
            cut_price.setPaintFlags(cut_price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            desription.setText(Html.fromHtml(Videodata.getDescription()));
            wwlearn.setText(Html.fromHtml(Videodata.getWwlearn()));
            menu_icon.setVisibility(View.GONE);
            usr_profile.setVisibility(View.GONE);
            back_icon.setVisibility(View.VISIBLE);
            title.setText("Subject Detail");
            LoggerUtil.logItem(data);


            Glide.with(img_sub)
                    .load(Image_Path.Imagepath+"packages/"+Videodata.getImage())
                    .fitCenter()
                    .into(img_sub);
            String pkg_id=Videodata.getId();
            String coach_id=preferencesManager.getCoaching_id();
            String sub_id= String.valueOf(Videodata.getSubjectId());

            getsimilllarProduct(pkg_id,coach_id,sub_id);
            t_coursename.setText(Videodata.getName());
        }else {
            price.setText("\u20B9"+data.getPrice());

            cut_price.setText("\u20B9"+data.getMrp());
            ct_chapt.setText(""+data.getTotalChapters()+" Subjects");
            ct_lacture.setText(""+data.getTotalLectures()+" Chapter");
            ct_notes.setText(""+data.getTotalNotes()+" Notes");
            ct_video.setText(""+data.getTotalVideos()+" Video");
            amount=data.getPrice();
            amount_befor_disc=amount;
            Coupon_disc="";
            handling_charge=(Integer.parseInt(amount)*2.6)/100;
            amount= String.valueOf(Integer.parseInt(amount)+handling_charge);
            buy_price.setText("\u20B9"+amount);
//        showMessage(handling_charge+"\n"+amount);
            cut_price.setPaintFlags(cut_price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            desription.setText(Html.fromHtml(data.getDescription()));
            wwlearn.setText(Html.fromHtml(data.getWwlearn()));
            menu_icon.setVisibility(View.GONE);
            usr_profile.setVisibility(View.GONE);
            back_icon.setVisibility(View.VISIBLE);
            title.setText("Subject Detail");
            LoggerUtil.logItem(data);


            Glide.with(img_sub)
                    .load(Image_Path.Imagepath+"packages/"+data.getImage())
                    .fitCenter()
                    .into(img_sub);
            String pkg_id=data.getId();
            String coach_id=preferencesManager.getCoaching_id();
            String sub_id= String.valueOf(data.getSubjectId());

            getsimilllarProduct(pkg_id,coach_id,sub_id);
            t_coursename.setText(data.getName());
        }

        view_detail_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog();
            }
        });


        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("from").equalsIgnoreCase("video")){
                    Intent intent=new Intent(Packages_Detail_Activity.this,Buy_Activity.class);
                    intent.putExtra("course_name",Videodata.getName());
                    intent.putExtra("package_id",Videodata.getId());
                    intent.putExtra("price",""+amount);
                    intent.putExtra("mrp",""+Videodata.getMrp());
                    intent.putExtra("discount",""+Videodata.getDiscount());
                    intent.putExtra("t_video",""+Videodata.getTotalVideos());
                    intent.putExtra("t_lact",""+Videodata.getTotalLectures());
                    intent.putExtra("t_chapt",""+Videodata.getTotalChapters());
                    intent.putExtra("t_note",""+Videodata.getTotalNotes());
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent(Packages_Detail_Activity.this,Buy_Activity.class);
                    intent.putExtra("course_name",data.getName());
                    intent.putExtra("package_id",data.getId());
                    intent.putExtra("price",""+amount);
                    intent.putExtra("mrp",""+data.getMrp());
                    intent.putExtra("discount",""+data.getDiscount());
                    intent.putExtra("t_video",""+data.getTotalVideos());
                    intent.putExtra("t_lact",""+data.getTotalLectures());
                    intent.putExtra("t_chapt",""+data.getTotalChapters());
                    intent.putExtra("t_note",""+data.getTotalNotes());
                    startActivity(intent);
                    finish();
                }

            }
        });

        lyt_chapt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Please purchase this package to access content");
               /* Intent intent=new Intent(getApplicationContext(),Chapter_Activity.class);
                intent.putExtra("id",data.getId());
                intent.putExtra("name",data.getName());
                startActivity(intent);*/
            }
        });
        lyt_lact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Please purchase this package to access content");
             /*   Lacture_Activity.statusTransit=1;
                Intent intent=new Intent(getApplicationContext(),Lacture_Activity.class);
                intent.putExtra("id",data.getId());
                intent.putExtra("name",data.getName());
                intent.putExtra("chapter","details");
                startActivity(intent);*/
            }
        });
        lyt_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Please purchase this package to access content");
               /* Notes_Fragment.notestransition=1;
                Notes_Video_Activity.statusTrans=0;
                Notes_Video_Activity.package_id=data.getId();
                Notes_Video_Activity.lacture_name=data.getName();
                Intent intent = new Intent(Packages_Detail_Activity.this, Notes_Video_Activity.class);
                startActivity(intent);*/


            }
        });
        lyt_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Please purchase this package to access content");
              /*  Notes_Video_Activity.statusTrans=1;
                Video_Fragment.videoransition=1;
                Notes_Video_Activity.package_id=data.getId();
                Notes_Video_Activity.lacture_name=data.getName();
                Intent intent = new Intent(Packages_Detail_Activity.this, Notes_Video_Activity.class);
                startActivity(intent);*/

            }
        });


    }

    private void getsimilllarProduct(String clas_id, String coach_id,String sub_id) {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseSimillarProduct> productCall=apiServices.getSimilarProduct("SimilarProduct",clas_id,sub_id,"1");
        productCall.enqueue(new Callback<ResponseSimillarProduct>() {
            @Override
            public void onResponse(Call<ResponseSimillarProduct> call, Response<ResponseSimillarProduct> response) {
                addRecyclerCoupon();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    for (int i=0;i<response.body().getData().size();i++){
                        if (response.body().getData().get(i).getExp_date().equalsIgnoreCase("")){
                            slist.add(response.body().getData().get(i));
                            LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                            Simillar_Product_Adapter simillar_product_adapter=new Simillar_Product_Adapter(slist,getApplicationContext());
                            rec_simmilar.setLayoutManager(layoutManager);
                            rec_simmilar.setAdapter(simillar_product_adapter);
                            sm_txt.setVisibility(View.VISIBLE);
                        }else {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date strDate = null;
                            Date strDate1=null;
                            try {
                                strDate = sdf.parse(response.body().getData().get(i).getExp_date());
                                strDate1 = sdf.parse(Utils.getDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (!strDate1.after(strDate)) {
                                slist.add(response.body().getData().get(i));
                                LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                                Simillar_Product_Adapter simillar_product_adapter=new Simillar_Product_Adapter(slist,getApplicationContext());
                                rec_simmilar.setLayoutManager(layoutManager);
                                rec_simmilar.setAdapter(simillar_product_adapter);
                                sm_txt.setVisibility(View.VISIBLE);
                            }else {
//                            LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
//                            Simillar_Product_Adapter simillar_product_adapter=new Simillar_Product_Adapter(response.body().getData(),getApplicationContext());
//                            rec_simmilar.setLayoutManager(layoutManager);
//                            rec_simmilar.setAdapter(simillar_product_adapter);
//                            sm_txt.setVisibility(View.VISIBLE);
                            }
                        }


                    }


                }else {
                    sm_txt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseSimillarProduct> call, Throwable t) {
                hideLoading();
            }
        });

    }

    private void BottomSheetDialog(){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.item_view_price_detail);
        TextView btm_mtp=bottomSheetDialog.findViewById(R.id.btm_mrp);
        TextView btm_discount=bottomSheetDialog.findViewById(R.id.btm_discount);
        TextView btm_price=bottomSheetDialog.findViewById(R.id.btm_price);
        Button btm_buy=bottomSheetDialog.findViewById(R.id.btm_buy);
        LinearLayout ll_couponDiscount=bottomSheetDialog.findViewById(R.id.ll_couponDiscount);
        TextView tv_coupon_amount=bottomSheetDialog.findViewById(R.id.tv_coupon_amount);
        tv_ihc_charge=bottomSheetDialog.findViewById(R.id.tv_ihc_charge);
        if (Coupon_disc.equalsIgnoreCase("")){
            ll_couponDiscount.setVisibility(View.GONE);
        }else {
            ll_couponDiscount.setVisibility(View.VISIBLE);
            tv_coupon_amount.setText("\u20B9 "+Coupon_disc);
        }

        tv_ihc_charge.setText("\u20B9 "+handling_charge);

        btm_price.setText(amount);
        if (getIntent().getStringExtra("from").equalsIgnoreCase("video")){
            btm_mtp.setText(Videodata.getMrp());
            btm_discount.setText(Videodata.getDiscount()+"%");
        }else {
            btm_mtp.setText(data.getMrp());
            btm_discount.setText(data.getDiscount()+"%");
        }



        btm_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("from").equalsIgnoreCase("video")){
                    Intent intent=new Intent(Packages_Detail_Activity.this,Buy_Activity.class);
                    intent.putExtra("course_name",Videodata.getName());
                    intent.putExtra("package_id",Videodata.getId());
                    intent.putExtra("price",""+amount);
                    intent.putExtra("mrp",""+Videodata.getMrp());
                    intent.putExtra("discount",""+Videodata.getDiscount());
                    intent.putExtra("t_video",""+Videodata.getTotalVideos());
                    intent.putExtra("t_la" +
                            "ct",""+Videodata.getTotalLectures());
                    intent.putExtra("t_chapt",""+Videodata.getTotalChapters());
                    intent.putExtra("t_note",""+Videodata.getTotalNotes());
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent(Packages_Detail_Activity.this,Buy_Activity.class);
                    intent.putExtra("course_name",data.getName());
                    intent.putExtra("package_id",data.getId());
                    intent.putExtra("price",""+amount);
                    intent.putExtra("mrp",""+data.getMrp());
                    intent.putExtra("discount",""+data.getDiscount());
                    intent.putExtra("t_video",""+data.getTotalVideos());
                    intent.putExtra("t_la" +
                            "ct",""+data.getTotalLectures());
                    intent.putExtra("t_chapt",""+data.getTotalChapters());
                    intent.putExtra("t_note",""+data.getTotalNotes());
                    startActivity(intent);
                    finish();
                }


               // startActivity(new Intent(getApplicationContext(),Buy_Activity.class));
            }
        });

        bottomSheetDialog.show();


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
                    recyclerSearchHistory.setAdapter(new CouponAdapter(getApplicationContext(),response.body().getData(),Packages_Detail_Activity.this));

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

    private void addRecyclerCoupon() {
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseCoupon> call=apiServices.getCoupon("Coupons");
        call.enqueue(new Callback<ResponseCoupon>() {
            @Override
            public void onResponse(Call<ResponseCoupon> call, Response<ResponseCoupon> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().getRes().equalsIgnoreCase("success")){
                    couponList=response.body().getData();
                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponseCoupon> call, Throwable t) {
                hideLoading();
            }
        });

    }


    @Override
    public void ApplyCoupon(String coup_id, String coupon_code, String coupon_discount, String min_order_amount) {

          int temp= Integer.parseInt(amount_befor_disc);
        if (Double.parseDouble(amount)>=Double.parseDouble(min_order_amount)){
            int t_amount=temp-Integer.parseInt(coupon_discount);
            buy_price.setText("\u20B9 "+t_amount);
            et_coupon_code.setText(coupon_code);
            tv_apply.setText("Applied");
            amount= String.valueOf(t_amount);
            Coupon_disc=coupon_discount;
            Coupon_Id=coup_id;
            popupWindow.dismiss();
            is_Coupon=coupon_code;
        }else {
            showMessage("You can apply coupon on order above \u20B9 "+min_order_amount);
        }


    }
}