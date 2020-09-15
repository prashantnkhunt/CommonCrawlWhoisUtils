package com.prominentpixel.tyler.dao.commoncrawl;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.prominentpixel.tyler.dao.commoncrawl.convertor.CCJobTitleConvertor;
import com.prominentpixel.tyler.dao.commoncrawl.convertor.CCLinkedinsConvertor;
import com.prominentpixel.tyler.dao.commoncrawl.convertor.CCRecordNameConvertor;
import com.prominentpixel.tyler.dao.commoncrawl.convertor.CCTwitterConvertor;

import java.util.List;

@DynamoDBDocument
public class CCRecord implements Comparable<CCRecord>{

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


    @Override
    public int compareTo(CCRecord other) {

        int result=0;

        //Nerrow comparison by email and domain.
        if(this.domain.compareTo(other.domain) == 0){

            result = this.email.compareTo(other.email);
        }
        else{
            result = this.domain.compareTo(other.domain);
        }
        return result;
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder(256);

        result.append("{ ");
        result.append("\"email\":"+"\""+email.toString()+"\",");
        result.append("\"domain\":"+"\""+domain.toString()+"\",");
        result.append("\"numbers\":"+numbers.toString()+",");
        result.append("\"name\":"+name.toString()+",");
        result.append("\"job_title\":"+(null != job_title ? job_title.toString() : null) +",");
        result.append("\"twitter\":"+(null != twitter ? twitter.toString() : null ) +",");
        result.append("\"linkedIns\":"+(null != linkedIns ? linkedIns.toString() : null));
        result.append("} ");
        return result.toString();
    }
}
