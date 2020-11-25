package com.prominentpixel.tyler.dao.commoncrawl.dataimport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CCRecordWhoisWrapper {

	private Domain domain;
	private NumbersWhoIs numbers;
	private Name name;
//	@com.fasterxml.jackson.annotation.JsonValue("email_domain")
	private Domain email_domain;
	private Location location;
    private JobTitle job_title;
    private Email email = new Email();

    private Patterns pattern;
    private References references;
    private Twitter twitter ;
//	private Numbers numbers;
    private Linkedin linkedin;
	public Domain getDomain() {
		return domain;
	}
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	public NumbersWhoIs getNumbers() {
		return numbers;
	}
	public void setNumbers(NumbersWhoIs numbers) {
		this.numbers = numbers;
	}
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	public Domain getEmail_domain() {
		return email_domain;
	}
	public void setEmail_domain(Domain email_domain) {
		this.email_domain = email_domain;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public JobTitle getJob_title() {
		return job_title;
	}
	public void setJob_title(JobTitle job_title) {
		this.job_title = job_title;
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
	public References getReferences() {
		return references;
	}
	public void setReferences(References references) {
		this.references = references;
	}
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
    
}
