package com.view.kevin.myview.activity.bean;

/**
 * Created by Kevin on 2017/6/22.
 */

public class TextbookInfo {
    private int id;
    private String name;//textbook name
    private String level;//一年级
    private String subLevel;//上
    private int lessonId;
    private String lessonName;//Unit 2 Face - Story Time
    private String lessonName_CN;//第二单元 脸 - 故事时间
    private String lessonURI;
    private int status;//正在学习：1，没有学习：0

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSubLevel() {
        return subLevel;
    }

    public void setSubLevel(String subLevel) {
        this.subLevel = subLevel;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonName_CN() {
        return lessonName_CN;
    }

    public void setLessonName_CN(String lessonName_CN) {
        this.lessonName_CN = lessonName_CN;
    }

    public String getLessonURI() {
        return lessonURI;
    }

    public void setLessonURI(String lessonURI) {
        this.lessonURI = lessonURI;
    }

}
