package com.prominentpixel.tyler.dao.commoncrawl.dataimport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Str {
    String s;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
