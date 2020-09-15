package com.prominentpixel.tyler.dao.whois;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


import java.util.Date;

@DynamoDBTable(tableName="Whois")
public class WhoIsRecordFull {

    private String domainName;
    private String registrarName;
    private String contactEmail;
    private String whoisServer;
    private String nameServers;

    private Date createdDate;
    private Date updatedDate;
    private Date expiresDate;

    /*@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") //2016-01-05T20:31:03Z
    private Date standardRegCreatedDate;*/

    private String standardRegCreatedDate;

    private String standardRegUpdatedDate;
    private String standardRegExpiresDate;

    private String status;
    private String Audit_auditUpdatedDate;

    private String registrant_email;
    private String registrant_name;
    private String registrant_organization;
    private String registrant_street1;
    private String registrant_street2;
    private String registrant_street3;
    private String registrant_street4;
    private String registrant_city;
    private String registrant_state;

    private String registrant_postalCode;

    private String registrant_country;

    private String registrant_fax;
    private String registrant_faxExt;
    private String registrant_telephone;
    private String registrant_telephoneExt;

    private String administrativeContact_email;
    private String administrativeContact_name;
    private String administrativeContact_organization;
    private String administrativeContact_street1;
    private String administrativeContact_street2;
    private String administrativeContact_street3;
    private String administrativeContact_street4;
    private String administrativeContact_city;
    private String administrativeContact_state;

    private String administrativeContact_postalCode;

    private String administrativeContact_country;

    private String administrativeContact_fax;
    private String administrativeContact_faxExt;
    private String administrativeContact_telephone;
    private String administrativeContact_telephoneExt;

    @DynamoDBHashKey(attributeName="domainName")
    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @DynamoDBAttribute(attributeName="registrarName")
    public String getRegistrarName() {
        return registrarName;
    }

    public void setRegistrarName(String registrarName) {
        this.registrarName = registrarName;
    }

    @DynamoDBAttribute(attributeName="contactEmail")
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @DynamoDBAttribute(attributeName="whoisServer")
    public String getWhoisServer() {
        return whoisServer;
    }

    public void setWhoisServer(String whoisServer) {
        this.whoisServer = whoisServer;
    }

    @DynamoDBAttribute(attributeName="nameServers")
    public String getNameServers() {
        return nameServers;
    }

    public void setNameServers(String nameServers) {
        this.nameServers = nameServers;
    }

    @DynamoDBAttribute(attributeName="createdDate")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @DynamoDBAttribute(attributeName="updatedDate")
    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @DynamoDBAttribute(attributeName="expiresDate")
    public Date getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(Date expiresDate) {
        this.expiresDate = expiresDate;
    }

    @DynamoDBAttribute(attributeName="standardRegCreatedDate")
    public String getStandardRegCreatedDate() {
        return standardRegCreatedDate;
    }

    public void setStandardRegCreatedDate(String standardRegCreatedDate) {
        this.standardRegCreatedDate = standardRegCreatedDate;
    }

    @DynamoDBAttribute(attributeName="standardRegUpdatedDate")
    public String getStandardRegUpdatedDate() {
        return standardRegUpdatedDate;
    }

    public void setStandardRegUpdatedDate(String standardRegUpdatedDate) {
        this.standardRegUpdatedDate = standardRegUpdatedDate;
    }

    @DynamoDBAttribute(attributeName="standardRegExpiresDate")
    public String getStandardRegExpiresDate() {
        return standardRegExpiresDate;
    }

    public void setStandardRegExpiresDate(String standardRegExpiresDate) {
        this.standardRegExpiresDate = standardRegExpiresDate;
    }

    @DynamoDBAttribute(attributeName="status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @DynamoDBAttribute(attributeName="Audit_auditUpdatedDate")
    public String getAudit_auditUpdatedDate() {
        return Audit_auditUpdatedDate;
    }

    public void setAudit_auditUpdatedDate(String audit_auditUpdatedDate) {
        Audit_auditUpdatedDate = audit_auditUpdatedDate;
    }

    @DynamoDBAttribute(attributeName="registrant_email")
    public String getRegistrant_email() {
        return registrant_email;
    }

    public void setRegistrant_email(String registrant_email) {
        this.registrant_email = registrant_email;
    }

    @DynamoDBAttribute(attributeName="registrant_name")
    public String getRegistrant_name() {
        return registrant_name;
    }

    public void setRegistrant_name(String registrant_name) {
        this.registrant_name = registrant_name;
    }

    @DynamoDBAttribute(attributeName="registrant_organization")
    public String getRegistrant_organization() {
        return registrant_organization;
    }


    public void setRegistrant_organization(String registrant_organization) {
        this.registrant_organization = registrant_organization;
    }

    @DynamoDBAttribute(attributeName="registrant_street1")
    public String getRegistrant_street1() {
        return registrant_street1;
    }


    public void setRegistrant_street1(String registrant_street1) {
        this.registrant_street1                                                                                                                                                                                                                                                                                                                                                                                                                                                                  = registrant_street1;
    }

    @DynamoDBAttribute(attributeName="registrant_street2")
    public String getRegistrant_street2() {
        return registrant_street2;
    }

    public void setRegistrant_street2(String registrant_street2) {
        this.registrant_street2 = registrant_street2;
    }

    @DynamoDBAttribute(attributeName="registrant_street3")
    public String getRegistrant_street3() {
        return registrant_street3;
    }

    public void setRegistrant_street3(String registrant_street3) {
        this.registrant_street3 = registrant_street3;
    }

    @DynamoDBAttribute(attributeName="registrant_street4")
    public String getRegistrant_street4() {
        return registrant_street4;
    }

    public void setRegistrant_street4(String registrant_street4) {
        this.registrant_street4 = registrant_street4;
    }

    @DynamoDBAttribute(attributeName="registrant_city")
    public String getRegistrant_city() {
        return registrant_city;
    }

    public void setRegistrant_city(String registrant_city) {
        this.registrant_city = registrant_city;
    }

    @DynamoDBAttribute(attributeName="registrant_state")
    public String getRegistrant_state() {
        return registrant_state;
    }

    public void setRegistrant_state(String registrant_state) {
        this.registrant_state = registrant_state;
    }

    @DynamoDBAttribute(attributeName="registrant_postalCode")
    public String getRegistrant_postalCode() {
        return registrant_postalCode;
    }

    public void setRegistrant_postalCode(String registrant_postalCode) {
        this.registrant_postalCode = registrant_postalCode;
    }

    @DynamoDBAttribute(attributeName="registrant_country")
    public String getRegistrant_country() {
        return registrant_country;
    }

    public void setRegistrant_country(String registrant_country) {
        this.registrant_country = registrant_country;
    }

    @DynamoDBAttribute(attributeName="registrant_fax")
    public String getRegistrant_fax() {
        return registrant_fax;
    }

    public void setRegistrant_fax(String registrant_fax) {
        this.registrant_fax = registrant_fax;
    }

    @DynamoDBAttribute(attributeName="registrant_faxExt")
    public String getRegistrant_faxExt() {
        return registrant_faxExt;
    }

    public void setRegistrant_faxExt(String registrant_faxExt) {
        this.registrant_faxExt = registrant_faxExt;
    }

    @DynamoDBAttribute(attributeName="registrant_telephone")
    public String getRegistrant_telephone() {
        return registrant_telephone;
    }

    public void setRegistrant_telephone(String registrant_telephone) {
        this.registrant_telephone = registrant_telephone;
    }

    @DynamoDBAttribute(attributeName="registrant_telephoneExt")
    public String getRegistrant_telephoneExt() {
        return registrant_telephoneExt;
    }

    public void setRegistrant_telephoneExt(String registrant_telephoneExt) {
        this.registrant_telephoneExt = registrant_telephoneExt;
    }

    @DynamoDBAttribute(attributeName="administrativeContact_email")
    public String getAdministrativeContact_email() {
        return administrativeContact_email;
    }

    public void setAdministrativeContact_email(String administrativeContact_email) {
        this.administrativeContact_email = administrativeContact_email;
    }

    @DynamoDBAttribute(attributeName="administrativeContact_name")
    public String getAdministrativeContact_name() {
        return administrativeContact_name;
    }

    public void setAdministrativeContact_name(String administrativeContact_name) {
        this.administrativeContact_name = administrativeContact_name;
    }

    @DynamoDBAttribute(attributeName="administrativeContact_organization")
    public String getAdministrativeContact_organization() {
        return administrativeContact_organization;
    }

    public void setAdministrativeContact_organization(String administrativeContact_organization) {
        this.administrativeContact_organization = administrativeContact_organization;
    }

    @DynamoDBAttribute(attributeName="administrativeContact_street1")
    public String getAdministrativeContact_street1() {
        return administrativeContact_street1;
    }

    public void setAdministrativeContact_street1(String administrativeContact_street1) {
        this.administrativeContact_street1 = administrativeContact_street1;
    }

    @DynamoDBAttribute(attributeName="administrativeContact_street2")
    public String getAdministrativeContact_street2() {
        return administrativeContact_street2;
    }

    public void setAdministrativeContact_street2(String administrativeContact_street2) {
        this.administrativeContact_street2 = administrativeContact_street2;
    }

    @DynamoDBAttribute(attributeName="administrativeContact_street3")
    public String getAdministrativeContact_street3() {
        return administrativeContact_street3;
    }

    public void setAdministrativeContact_street3(String administrativeContact_street3) {
        this.administrativeContact_street3 = administrativeContact_street3;
    }

    @DynamoDBAttribute(attributeName="administrativeContact_street4")
    public String getAdministrativeContact_street4() {
        return administrativeContact_street4;
    }

    public void setAdministrativeContact_street4(String administrativeContact_street4) {
        this.administrativeContact_street4 = administrativeContact_street4;
    }

    @DynamoDBAttribute(attributeName="administrativeContact_city")
    public String getAdministrativeContact_city() {
        return administrativeContact_city;
    }

    public void setAdministrativeContact_city(String administrativeContact_city) {
        this.administrativeContact_city = administrativeContact_city;
    }

    @DynamoDBAttribute(attributeName="administrativeContact_state")
    public String getAdministrativeContact_state() {
        return administrativeContact_state;
    }

    public void setAdministrativeContact_state(String administrativeContact_state) {
        this.administrativeContact_state = administrativeContact_state;
    }

    @DynamoDBAttribute(attributeName="administrativeContact_postalCode")
    public String getAdministrativeContact_postalCode() {
        return administrativeContact_postalCode;
    }

    public void setAdministrativeContact_postalCode(String administrativeContact_postalCode) {
        this.administrativeContact_postalCode = administrativeContact_postalCode;
    }

    @DynamoDBAttribute(attributeName="administrativeContact_country")
    public String getAdministrativeContact_country() {
        return administrativeContact_country;
    }

    public void setAdministrativeContact_country(String administrativeContact_country) {
        this.administrativeContact_country = administrativeContact_country;
    }

    @DynamoDBAttribute(attributeName="administrativeContact_fax")
    public String getAdministrativeContact_fax() {
        return administrativeContact_fax;
    }

    public void setAdministrativeContact_fax(String administrativeContact_fax) {
        this.administrativeContact_fax = administrativeContact_fax;
    }

    @DynamoDBAttribute(attributeName="administrativeContact_faxExt")
    public String getAdministrativeContact_faxExt() {
        return administrativeContact_faxExt;
    }

    public void setAdministrativeContact_faxExt(String administrativeContact_faxExt) {
        this.administrativeContact_faxExt = administrativeContact_faxExt;
    }

    @DynamoDBAttribute(attributeName="administrativeContact_telephone")
    public String getAdministrativeContact_telephone() {
        return administrativeContact_telephone;
    }

    public void setAdministrativeContact_telephone(String administrativeContact_telephone) {
        this.administrativeContact_telephone = administrativeContact_telephone;
    }

    @DynamoDBAttribute(attributeName="administrativeContact_telephoneExt")
    public String getAdministrativeContact_telephoneExt() {
        return administrativeContact_telephoneExt;
    }

    public void setAdministrativeContact_telephoneExt(String administrativeContact_telephoneExt) {
        this.administrativeContact_telephoneExt = administrativeContact_telephoneExt;
    }
}
