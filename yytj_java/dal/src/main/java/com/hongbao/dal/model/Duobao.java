package com.hongbao.dal.model;

import com.hongbao.dal.BaseEntityImpl;

public class Duobao extends BaseEntityImpl {
    private static final long serialVersionUID = 2560250242912137270L;
    private String title;
    private String imageUrl;
    private Integer totalScore;
    private Integer currentScore;
    private Integer leftScore;
    private Integer status;
    private Integer onceScore;
    private Float percent;
    private Float leftPercent;
    private String statusString;

    
    public Float getLeftPercent() {
        return leftPercent;
    }

    public void setLeftPercent(Float leftPercent) {
        this.leftPercent = leftPercent;
    }

    public Integer getLeftScore() {
        return leftScore;
    }

    public void setLeftScore(Integer leftScore) {
        this.leftScore = leftScore;
    }

    public String getStatusString() {
        if(status!=null){
            if(status==1){
                statusString = "进行中";
            }
            if(status==2){
                statusString = "已揭晓";
            }
        }
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public Integer getOnceScore() {
        return onceScore;
    }

    public void setOnceScore(Integer onceScore) {
        this.onceScore = onceScore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(Integer currentScore) {
        this.currentScore = currentScore;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
