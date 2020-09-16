package com.prominentpixel.tyler.dao.commoncrawl.dataimport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Twitter {


    private String s;
    private M m;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public M getM() {
        return m;
    }

    public void setM(M m) {
        this.m = m;
    }

}
