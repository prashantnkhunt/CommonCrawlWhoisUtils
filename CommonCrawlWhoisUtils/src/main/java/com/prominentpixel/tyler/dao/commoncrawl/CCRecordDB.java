package com.prominentpixel.tyler.dao.commoncrawl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


import java.util.List;

@DynamoDBTable(tableName="CCrawl")
public class CCRecordDB {

    private String domain;

    private String contactURL;

    private List<CCRecordDynamoDb> emails;

    @DynamoDBHashKey(attributeName="domain")
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

//    @DynamoDBTypeConverted(converter = CCRecordConvertor.class)
    @DynamoDBAttribute(attributeName="emails")
    public List<CCRecordDynamoDb> getEmails() {
        return emails;
    }

    public void setEmails(List<CCRecordDynamoDb> emails) {
        this.emails = emails;
    }

    @DynamoDBAttribute(attributeName="contacturl")
    public String getContactURL() {
        return contactURL;
    }

    public void setContactURL(String contactURL) {
        this.contactURL = contactURL;
    }
}
