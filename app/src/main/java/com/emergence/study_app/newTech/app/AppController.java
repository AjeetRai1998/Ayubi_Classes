package com.emergence.study_app.newTech.app;

import android.app.Application;

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /*CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/montserrat_medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .disableCustomViewInflation()
                .build());*/
    }

}
