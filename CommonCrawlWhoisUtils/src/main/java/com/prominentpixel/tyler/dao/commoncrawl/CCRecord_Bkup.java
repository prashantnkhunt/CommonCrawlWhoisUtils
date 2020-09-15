package com.prominentpixel.tyler.dao.commoncrawl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.prominentpixel.tyler.dao.commoncrawl.convertor.CCJobTitleConvertor;
import com.prominentpixel.tyler.dao.commoncrawl.convertor.CCLinkedinsConvertor;
import com.prominentpixel.tyler.dao.commoncrawl.convertor.CCRecordNameConvertor;
import com.prominentpixel.tyler.dao.commoncrawl.convertor.CCTwitterConvertor;

import java.util.List;

@DynamoDBTable(tableName="CCrawl")
public class CCRecord_Bkup {

    private String email;
    private String domain;

    private List<String> numbers;
    private CCRecordName name;

    private CCJobTitle job_title;
    private CCRecordTwitter twitter;
    private CCRecordLinkedin linkedIns;

    @DynamoDBTypeConverted(converter = CCJobTitleConvertor.class)
    @DynamoDBAttribute(attributeName="job_title")
    public CCJobTitle getJob_title() {
        return job_title;
    }

    public void setJob_title(CCJobTitle job_title) {
        this.job_title = job_title;
    }
    @DynamoDBAttribute(attributeName="email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBHashKey(attributeName="domain")
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }


    @DynamoDBAttribute(attributeName="numbers")
    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    @DynamoDBTypeConverted(converter = CCRecordNameConvertor.class)
    @DynamoDBAttribute(attributeName="name")
    public CCRecordName getName() {
        return name;
    }

    public void setName(CCRecordName name) {
        this.name = name;
    }

    @DynamoDBTypeConverted(converter = CCTwitterConvertor.class)
    @DynamoDBAttribute(attributeName="twitter")
    public CCRecordTwitter getTwitter() {
        return twitter;
    }

    public void setTwitter(CCRecordTwitter twitter) {
        this.twitter = twitter;
    }

    @DynamoDBTypeConverted(converter = CCLinkedinsConvertor.class)
    @DynamoDBAttribute(attributeName="linkedIns")
    public CCRecordLinkedin getLinkedIns() {
        return linkedIns;
    }

    public void setLinkedIns(CCRecordLinkedin linkedIns) {
        this.linkedIns = linkedIns;
    }
}
