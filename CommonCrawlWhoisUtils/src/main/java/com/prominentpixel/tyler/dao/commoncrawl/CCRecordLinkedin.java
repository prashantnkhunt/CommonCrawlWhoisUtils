package com.prominentpixel.tyler.dao.commoncrawl;

public class CCRecordLinkedin {

    private Float confidence;
    private String username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "{" +
                "\"confidence\":"+this.getConfidence()+","+
                "\"username\":"+"\""+this.getUsername()+"\""
                +"}";
    }
}
