package com.prominentpixel.tyler.dao.commoncrawl.dataimport;

import com.fasterxml.jackson.annotation.JsonProperty;
public class M {
    private Name jobTitle;
    private Confidence confidence;
    private Timestamp timestamp;
    private Url url;
    private Name name;
    private Pattern f;
    private Pattern pattern;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @JsonProperty("name")
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @JsonProperty("job_title")
    public Name getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(Name jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Confidence getConfidence() {
        return confidence;
    }

    public void setConfidence(Confidence confidence) {
        this.confidence = confidence;
    }

    public Pattern getF() {
        return f;
    }

    public void setF(Pattern f) {
        this.f = f;
    }
}
