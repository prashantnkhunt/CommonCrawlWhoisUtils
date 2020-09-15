package com.prominentpixel.tyler.dao.commoncrawl.dataimport;

import com.prominentpixel.tyler.dao.commoncrawl.CCRecordLinkedin;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordTwitter;
public class CCRecordWrapper {

    private Email  email = new Email();
    private Patterns pattern;
    private Domain domain;
    private Name name;
    private Numbers numbers;
    private References references;
    private JobTitle job_title;
    private Twitter twitter;
    private Linkedin linkedin; //can i see err msg

    public Twitter getTwitter() {
        return twitter;
    }

    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }

    public Linkedin getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(Linkedin linkedin) {
        this.linkedin = linkedin;
    }

    public References getReferences() {
        return references;
    }

    public void setReferences(References references) {
        this.references = references;
    }

    public Numbers getNumbers() {
        return numbers;
    }

    public void setNumbers(Numbers numbers) {
        this.numbers = numbers;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Patterns getPattern() {
        return pattern;
    }

    public void setPattern(Patterns pattern) {
        this.pattern = pattern;
    }

    public JobTitle getJob_title() {
        return job_title;
    }

    public void setJob_title(JobTitle job_title) {
        this.job_title = job_title;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}
