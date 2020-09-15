package com.prominentpixel.tyler.dao.commoncrawl.dataimport;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Name {
    String s;

    @JsonInclude(JsonInclude.Include.NON_NULL) //ignore null field on this property only
             String jobTitle;

    M m;

    public M getM() {
        return m;
    }

    public void setM(M m) {
        this.m = m;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
