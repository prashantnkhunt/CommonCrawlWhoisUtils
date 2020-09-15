package com.prominentpixel.tyler.dao.commoncrawl.dataimport;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) 	//  ignore all null fields
public class L {
    private M m;

    public M getM() {
        return m;
    }

    public void setM(M m) {
        this.m = m;
    }
}
