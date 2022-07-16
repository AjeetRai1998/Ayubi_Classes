package com.emergence.study_app.liveVideo;

public class LiveEvents {

    String id,liveuserid,name,topic,classes,duration,subject,desc,url,livedatetime,datetime;

    public LiveEvents(String id,String liveuserid, String name, String topic, String classes, String duration, String subject, String desc, String url, String livedatetime, String datetime) {
        this.id = id;
        this.liveuserid=liveuserid;
        this.name = name;
        this.topic = topic;
        this.classes = classes;
        this.duration = duration;
        this.subject = subject;
        this.desc = desc;
        this.url = url;
        this.livedatetime = livedatetime;
        this.datetime = datetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getLiveuserid() {
        return liveuserid;
    }

    public void setLiveuserid(String liveuserid) {
        this.liveuserid = liveuserid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLivedatetime() {
        return livedatetime;
    }

    public void setLivedatetime(String livedatetime) {
        this.livedatetime = livedatetime;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
