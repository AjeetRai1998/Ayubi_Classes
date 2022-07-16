package com.emergence.study_app.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.emergence.study_app.Fragments.Notes_Fragment;
import com.emergence.study_app.Fragments.Video_Fragment;

public class Tab_Adapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public Tab_Adapter(@NonNull FragmentManager fm, Context myContext, int totalTabs) {
        super(fm);
        this.myContext = myContext;
        this.totalTabs = totalTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Video_Fragment video_fragment = new Video_Fragment();
                return video_fragment;
            case 1:
                Notes_Fragment notes_fragment = new Notes_Fragment();
                return notes_fragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
