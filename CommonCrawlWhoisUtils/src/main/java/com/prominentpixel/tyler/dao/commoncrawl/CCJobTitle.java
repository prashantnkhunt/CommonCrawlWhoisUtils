package com.prominentpixel.tyler.dao.commoncrawl;

public class CCJobTitle {

    private Float confidence;
    private String job_title;
    private String s;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public Float getConfidence() {
        return confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    @Override
    public String toString() {
        return "{" +
                "\"confidence\":" + this.getConfidence() + "," +
                "\"job_title\":" + "\"" + this.getJob_title() + "\""
                + "}";
    }
}
