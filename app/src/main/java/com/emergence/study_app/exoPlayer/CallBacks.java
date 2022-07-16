package com.emergence.study_app.exoPlayer;

public interface CallBacks {
    void callbackObserver(Object obj);
    public interface playerCallBack {
        void onItemClickOnItem(Integer albumId);
        void onPlayingEnd();
    }
}
