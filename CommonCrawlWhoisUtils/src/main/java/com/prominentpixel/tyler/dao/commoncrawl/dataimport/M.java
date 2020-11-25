package com.prominentpixel.tyler.dao.commoncrawl.dataimport;

import com.fasterxml.jackson.annotation.JsonProperty;
public class M {
	private Confidence confidence;
	private Name name;
	private Str country;
	private Str city;
//	private Str job_title;

	private Name jobTitle;
    private Timestamp timestamp;
    private Url url;
    private Pattern f;
    private Pattern pattern;
    private Name twitter;
    private Name linkedin;
    private String s;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public Name getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(Name linkedin) {
        this.linkedin = linkedin;
    }

    public Name getTwitter() {
        return twitter;
    }

    public void setTwitter(Name twitter) {
        this.twitter = twitter;
    }

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

	public Str getCountry() {
		return country;
	}

	public void setCountry(Str country) {
		this.country = country;
	}

	public Str getCity() {
		return city;
	}

	public void setCity(Str city) {
		this.city = city;
	}

}
