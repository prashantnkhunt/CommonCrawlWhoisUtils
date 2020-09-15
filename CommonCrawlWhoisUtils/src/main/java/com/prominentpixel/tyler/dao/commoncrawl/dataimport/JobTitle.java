package com.prominentpixel.tyler.dao.commoncrawl.dataimport;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
public class JobTitle {


    private M m;

    public M getM() {
        return m;
    }

    public void setM(M m) {
        this.m = m;
    }

    /*private  Confidence n;
    private  JobTitles s  ;

    public Confidence getN() {
        return n;
    }

    public void setN(Confidence n) {
        this.n = n;
    }

    @DynamoDBAttribute(attributeName="job_title")
    public JobTitles getS() {
        return s;
    }

    public void setS(JobTitles s) {
        this.s = s;
    }*/
}
