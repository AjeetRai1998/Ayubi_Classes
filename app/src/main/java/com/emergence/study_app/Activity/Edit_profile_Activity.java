package com.emergence.study_app.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.Image_Path;
import com.emergence.study_app.newTech.retrofit.model.Get_User.ResponseGetUser;
import com.emergence.study_app.newTech.retrofit.model.Update_profile.ResponseUpdateProfile;
import com.example.study_app.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_profile_Activity extends BaseActivity {
    PreferencesManager preferencesManager;
    EditText pr_name,pr_email;
    Button save_changes;
    CardView pr_upload;
    TextView pr_coaching,pr_mobile,pr_class;
    String str_img="";
    CircleImageView profile;
    ImageView menu_icon,notify_icon,back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_edit_profile);
         preferencesManager=new PreferencesManager(Edit_profile_Activity.this);

        pr_name=findViewById(R.id.pr_name);
        menu_icon=findViewById(R.id.menu_icon);
        notify_icon=findViewById(R.id.img_notification);
        back_icon=findViewById(R.id.back_btn);
        pr_mobile=findViewById(R.id.pr_mobile);
        profile=findViewById(R.id.pr_icon);
        pr_email=findViewById(R.id.pr_email);
        pr_coaching=findViewById(R.id.pr_coaching);
        pr_upload=findViewById(R.id.pr_upload);
        pr_class=findViewById(R.id.pr_class);
        save_changes=findViewById(R.id.save_profile);

        menu_icon.setVisibility(View.GONE);
        back_icon.setVisibility(View.VISIBLE);

        String str_id=preferencesManager.getUserId();


        getUser(str_id);

        pr_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ImagePicker.with(Edit_profile_Activity.this)
                            .cropSquare()
                            .start();//Crop image(Optional), Check Customization for more option


            }
        });

        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = pr_email.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

// onClick of button perform this simplest code.
                if (email.matches(emailPattern))
                {
                    getUpdateUser(str_id,pr_name.getText().toString(),pr_mobile.getText().toString(),pr_email.getText().toString());
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                }


            }
        });
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        notify_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Notification_Activity.class));
            }
        });



    }

    private void getUpdateUser(String id,String name, String mobile, String email) {
        showLoading();
        ApiServices apiServices1= API_Config.getApiClient_ByPost();
        Call<ResponseUpdateProfile> updateProfileCall=apiServices1.getUpdateProfile("UpdateProfile",id,str_img,name,email);
        updateProfileCall.enqueue(new Callback<ResponseUpdateProfile>() {
            @Override
            public void onResponse(Call<ResponseUpdateProfile> call, Response<ResponseUpdateProfile> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    showMessage(response.body().getMsg());

                    preferencesManager.setEmailId(email);
                    finish();
                }else {
                    showMessage(response.body().getMsg());

                }
            }

            @Override
            public void onFailure(Call<ResponseUpdateProfile> call, Throwable t) {
                hideLoading();
                showMessage(t.getMessage().toString());

            }
        });
    }

    private void getUser(String str_id) {
        showLoading();
        ApiServices Api_ser=API_Config.getApiClient_ByPost();
        Call<ResponseGetUser> userCall=Api_ser.getGetUser(str_id,"GetUser");
        userCall.enqueue(new Callback<ResponseGetUser>() {
            @Override
            public void onResponse(Call<ResponseGetUser> call, Response<ResponseGetUser> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    pr_mobile.setText(response.body().getData().getMobile());
                    pr_name.setText(response.body().getData().getName());
                    pr_email.setText(response.body().getData().getEmail());
                    pr_coaching.setText(response.body().getData().getCoaching());
                    pr_class.setText(response.body().getData().getJsonMemberClass());
                    preferencesManager.setRef_Id(response.body().getData().getStudentId());
                    Glide.with(profile)
                            .load(Image_Path.Imagepath+"students/"+response.body().getData().getImage())
                            .fitCenter()
                            .into(profile);

                    preferencesManager.setMobileNo(response.body().getData().getMobile());
                    preferencesManager.setEmailId(response.body().getData().getEmail());


                }else {
                }

            }

            @Override
            public void onFailure(Call<ResponseGetUser> call, Throwable t) {
                hideLoading();

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            try {
                //Image Uri will not be null for RESULT_OK
                Uri uri = data.getData();

                // Use Uri object instead of File to avoid storage permissions

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                str_img=encodeImagetoString(bitmap);
                profile.setImageBitmap(bitmap);



            }catch (Exception e){

            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private String encodeImagetoString(Bitmap bitmap) {
        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 5;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] byte_arr = stream.toByteArray();
        String encodedString = Base64.encodeToString(byte_arr, 0);
        return encodedString;
    }


}