package com.prominentpixel.tyler.dao.whois;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@DynamoDBTable(tableName="Whois")
public class WhoisRecord {

    private String domainName;

    private String registrant_email;

    @DynamoDBHashKey(attributeName="domainName")
    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @DynamoDBAttribute(attributeName="registrant_email")
    public String getRegistrant_email() {
        return registrant_email;
    }

    public void setRegistrant_email(String registrant_email) {
        this.registrant_email = registrant_email;
    }

}
