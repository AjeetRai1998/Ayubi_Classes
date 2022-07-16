package com.emergence.study_app.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Class.ResponseClass;
import com.emergence.study_app.newTech.retrofit.model.Update_User.ResponseUserDetail;
import com.example.study_app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Detail_Activity extends BaseActivity {
    Button btn_next;
    Spinner spn_coaching,spn_class;
    EditText et_name,et_email,et_ref_id;
    private RadioGroup radio_gender;
    private RadioButton rmale,rfemail;

    String coachind_id="",class_id="",gender="";
    PreferencesManager preferencesManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        preferencesManager=new PreferencesManager(User_Detail_Activity.this);

        btn_next=findViewById(R.id.btn_next);
        et_ref_id=findViewById(R.id.et_ref_id);
        et_name=findViewById(R.id.et_name);
        et_email=findViewById(R.id.et_email);
        spn_class=findViewById(R.id.spin_class);
        radio_gender=findViewById(R.id.radio_group);
        rmale=findViewById(R.id.radio_male);
        rfemail=findViewById(R.id.radio_female);
        String str_id=preferencesManager.getUserId();
        String str_coachingid=getIntent().getStringExtra("coaching_id");
        preferencesManager.setCoaching_id("1");
        getClass_id();




        radio_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.radio_male){
                    gender=rmale.getText().toString();
                }else if (checkedId==R.id.radio_female){
                    gender=rfemail.getText().toString();
                }else{
                    gender="";
                }
            }
        });



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (et_name.getText().toString().equalsIgnoreCase("")){
                    et_name.setError("Enter Name");
                    et_name.requestFocus();
                }else if (et_email.getText().toString().equalsIgnoreCase("")) {
                    et_email.setError("Enter Email");
                    et_email.requestFocus();
                }else if (gender.equalsIgnoreCase("")){
                    showMessage("Choose Gender");
                }else if (email.matches(emailPattern)){
                    getUserDetail("1",et_name.getText().toString(),et_email.getText().toString(),str_id,et_ref_id.getText().toString());
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();

                }
            }
        });




    }



    private void getClass_id() {
        ApiServices apiServi= API_Config.getApiClient_ByPost();
        Call<ResponseClass> classCall=apiServi.getClass("Class");
        classCall.enqueue(new Callback<ResponseClass>() {
            @Override
            public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")) {
                    String[] clas = new String[response.body().getData().size() + 1];

                    clas[0] = "Select Class";
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        clas[i + 1] = response.body().getData().get(i).getName();
                    }
                    spn_class.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                    ArrayAdapter<String> adapter_section = new ArrayAdapter<String>(User_Detail_Activity.this, R.layout.item_spinner, clas);
                    adapter_section.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_class.setAdapter(adapter_section);

                    spn_class.setOnItemSelectedListener(
                            new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0) {
                                        class_id = "";
                                    } else {
                                        //  state_id=response.body().getData().get(position-1).getId();
                                        //   showMessage(state_id);
                                        class_id = response.body().getData().get(position - 1).getId();


//                                        preferencesManager.setState(response.body().getData().get(position-1).getName());
//                                        preferencesManager.setState_id(response.body().getData().get(position-1).getId());
                                        // operator=responseOperators.getData().get(position-1).getName();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            }
                    );
                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponseClass> call, Throwable t) {
                hideLoading();
            }
        });
    }

    private void getUserDetail(String str_cid,String name, String email,String str_id,String ref_id) {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseUserDetail> userDetailCall=apiServices.getUpdateDetail(ref_id,str_id,name,email,gender,str_cid,"","UpdateDetail");
        userDetailCall.enqueue(new Callback<ResponseUserDetail>() {
            @Override
            public void onResponse(Call<ResponseUserDetail> call, Response<ResponseUserDetail> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("class_id",class_id);
                    startActivity(intent);
                    preferencesManager.setClass_id(class_id);
                    finish();
                    showMessage(response.body().getMsg());

                }else {
                    showMessage(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResponseUserDetail> call, Throwable t) {
                hideLoading();
                showMessage(t.getMessage().toString());

            }
        });

    }
}