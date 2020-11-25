package com.prominentpixel.tyler.dao.commoncrawl;

import java.util.List;

public class CCRecord implements Comparable<CCRecord> {

    private String url;
    private String email;
    private String domain;

    private List<String> numbers;

    private String name;
    private Float name_confidence;
    private String name_pattern;

    private Float job_title_confidence;
    private String job_title;

    private Float twitter_confidence;
    private String twitter_username;

    private Float linkedIn_confidence;
    private String linkedIn_username;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getName_confidence() {
        return name_confidence;
    }

    public void setName_confidence(Float name_confidence) {
        this.name_confidence = name_confidence;
    }

    public String getName_pattern() {
        return name_pattern;
    }

    public void setName_pattern(String name_pattern) {
        this.name_pattern = name_pattern;
    }

    public Float getJob_title_confidence() {
        return job_title_confidence;
    }

    public void setJob_title_confidence(Float job_title_confidence) {
        this.job_title_confidence = job_title_confidence;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public Float getTwitter_confidence() {
        return twitter_confidence;
    }

    public void setTwitter_confidence(Float twitter_confidence) {
        this.twitter_confidence = twitter_confidence;
    }

    public String getTwitter_username() {
        return twitter_username;
    }

    public void setTwitter_username(String twitter_username) {
        this.twitter_username = twitter_username;
    }

    public Float getLinkedIn_confidence() {
        return linkedIn_confidence;
    }

    public void setLinkedIn_confidence(Float linkedIn_confidence) {
        this.linkedIn_confidence = linkedIn_confidence;
    }

    public String getLinkedIn_username() {
        return linkedIn_username;
    }

    public void setLinkedIn_username(String linkedIn_username) {
        this.linkedIn_username = linkedIn_username;
    }

    @Override
    public int compareTo(CCRecord other) {
        return ((this.domain.compareTo(other.domain) == 0) ? this.email.compareTo(other.email) : this.domain.compareTo(other.domain));
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == null){
            return false;
        }else{
            CCRecord other = (CCRecord) obj;
            return ((this.domain.compareTo(other.domain) == 0) && (this.email.compareTo(other.email) == 0));
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + email.hashCode();
        result = 31 * result + domain.hashCode();
        return result;
    }
}
