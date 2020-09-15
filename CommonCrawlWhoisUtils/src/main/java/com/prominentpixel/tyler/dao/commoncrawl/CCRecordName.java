package com.prominentpixel.tyler.dao.commoncrawl;

public class CCRecordName {

    private Float confidence;
    private String name;
    private String pattern;

    public Float getConfidence() {
        return confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "{" +
                "\"confidence\":"+this.getConfidence()+","+
                "\"name\":"+"\""+this.getName()+"\","+
                "\"pattern\":"+"\""+this.getPattern()+"\""
                +"}";
    }
}
