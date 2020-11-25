package com.prominentpixel.tyler.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.prominentpixel.tyler.dao.commoncrawl.CCJobTitle;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecord;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordDynamoDb;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordLinkedin;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordName;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordTwitter;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.CCRecordWhoisWrapper;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.CCRecordWrapper;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.Confidence;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.M;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.Name;
public class WrapperToDomain {
	/*
	
	public CCRecord convertWrapperToDomainWhoIsEmails(JSONObject obj) {
	//		String domain = func("domain","s")
	//		String name = func("name","m","name","s");
		
		return null;
	}*/
	
	public CCRecord convertWrapperToDomainWhoIsEmails(CCRecordWhoisWrapper loadingObject) {
        CCRecord ccRecord = new CCRecord();

        Optional.ofNullable(loadingObject.getDomain())
        	.map(d->d.getS()).ifPresent(ccRecord::setUrl);

        Optional.ofNullable(loadingObject.getEmail_domain())
        	.map(d->d.getS()).ifPresent(ccRecord::setDomain);

        Optional.ofNullable(loadingObject.getEmail())
        	.map(d->d.getS()).ifPresent(ccRecord::setEmail);

        Optional.ofNullable(loadingObject.getNumbers())
        	.map(n->n.getL())
        	.map(l->l.stream().map(n->n.getS()).collect(Collectors.toList()))
        	.ifPresent(ccRecord::setNumbers);

        Optional.ofNullable(loadingObject.getName())
        	.ifPresent(nm->{
        		if(nm.getS() != null)
        	        ccRecord.setName(nm.getS());
        		if(nm.getM() != null) {
        			Confidence confidence = nm.getM().getConfidence();
        			if(confidence != null)
        				ccRecord.setName_confidence(confidence.getN());
        			Name name = nm.getM().getName();
        			if(name != null)
            	        ccRecord.setName(name.getS());
        		}
        	});

        Optional.ofNullable(loadingObject.getJob_title())
        	.ifPresent(t->{
        		String s = t.getS();
        		M m = t.getM();

        		if(s != null)	ccRecord.setJob_title(s);
				if(m != null) {
        			Confidence confidence = m.getConfidence();
        			if(confidence != null)
        				ccRecord.setJob_title_confidence(confidence.getN());
        			Name jt = m.getJobTitle();
        			if(jt != null)
            	        ccRecord.setJob_title(jt.getS());
        		}
        	});
        
        if(true) return ccRecord;
        
        String email = null;
        String domain = null;
        List<String> numbers = null;
//        String name = null;
        Float name_confidence = null;
        String name_pattern = null;
        Float job_title_confidence = null;
        String job_title = null;
        Float twitter_confidence = null;
        String twitter_username = null;
        Float linkedIn_confidence = null;
        String linkedIn_username = null;

//        loadingObject.getTwitter().get
//        ccRecord.setName(name);
//        ccRecord.setName_confidence(name_confidence);
		ccRecord.setName_pattern(name_pattern);
//		ccRecord.setJob_title_confidence(job_title_confidence);
//        ccRecord.setJob_title(job_title);
		ccRecord.setTwitter_confidence(twitter_confidence);
		ccRecord.setTwitter_username(twitter_username);
		ccRecord.setLinkedIn_confidence(linkedIn_confidence);
		ccRecord.setLinkedIn_username(linkedIn_username);

        
        CCJobTitle ccJobTitle = new CCJobTitle();
        CCRecordName ccRecordName = new CCRecordName();
        CCRecordTwitter ccRecordTwitter=new CCRecordTwitter();
        CCRecordLinkedin ccRecordLinkedin=new CCRecordLinkedin();

//        loadingObject.getEmail();
//        ccRecord.setEmail((loadingObject.getEmail().getS()));
//        ccRecord.setDomain((loadingObject.getDomain().getS()));

        Optional.ofNullable(loadingObject)
                .map(o -> o.getJob_title())
                .ifPresent(s -> {
                    if (s.getS() != null)
                        ccJobTitle.setS(s.getS());
                });

        Optional.ofNullable(loadingObject)
                .map(o -> o.getJob_title())
                .map(t -> t.getM())
                .ifPresent(m -> {
                    if (m.getJobTitle() != null)
                        ccJobTitle.setJob_title(m.getJobTitle().getS());
                    if (m.getConfidence() != null && m.getConfidence().getN() != null)
                        ccJobTitle.setConfidence(m.getConfidence().getN());
                });
        Optional.ofNullable(loadingObject)
                .map(o -> o.getName())
                .map(t -> t.getM())
                .ifPresent(m -> {
                    if (m.getPattern() != null && m.getPattern().getS() != null)
                        ccRecordName.setPattern(m.getPattern().getS());
                    if (m.getConfidence() != null && m.getConfidence().getN() != null)
                        ccRecordName.setConfidence(m.getConfidence().getN());
                    if (m.getName() != null && m.getName().getS() != null)
                        ccRecordName.setName(m.getName().getS());
                });

        Optional.ofNullable(loadingObject)
                .map(o -> o.getName())
                .ifPresent(m -> {
                    if (m.getS() != null)
                        ccRecordName.setName(m.getS());
                });


        Optional.ofNullable(loadingObject)
                .map(o -> o.getLinkedin())
                .ifPresent(s -> {
                    if (s.getS() != null)
                        ccRecordLinkedin.setS(s.getS());
                });

        Optional.ofNullable(loadingObject)
                .map(t-> t.getLinkedin())
                .map(o -> o.getM())
                .ifPresent(s->{
                    if (s.getLinkedin() != null )
                        ccRecordLinkedin.setUsername(s.getLinkedin().getS());
                    if (s.getLinkedin() != null )
                        ccRecordLinkedin.setConfidence(s.getConfidence().getN());
                });

        Optional.ofNullable(loadingObject)
                .map(o -> o.getTwitter())
                .ifPresent(s -> {
                    if (s.getS() != null)
                        ccRecordTwitter.setS(s.getS());
                });
        Optional.ofNullable(loadingObject)
                .map(l-> l.getTwitter())
                .map(o -> o.getM())
                .ifPresent(s->{
                    if (s.getTwitter() != null )
                        ccRecordTwitter.setUsername(s.getTwitter().getS());
                    if (s.getTwitter() != null )
                        ccRecordTwitter.setConfidence(s.getConfidence().getN());
                });

      /*  for (L l : loadingObject.getReferences().getL()) {
            Url url1=new Url();
            url1.setS(l.getM().getUrl().getS());
        }
        for (L l : loadingObject.getReferences().getL()) {
            Timestamp timestamp1 = new Timestamp();
            timestamp1.setS(l.getM().getTimestamp().getS());
        }*/

//        ccRecord.setName(ccRecordName);
//        ccRecord.setJob_title(ccJobTitle);
//        ccRecord.setLinkedIns(ccRecordLinkedin);
//        ccRecord.setTwitter(ccRecordTwitter);
        return ccRecord;
    }
	
	
	

    public CCRecordDynamoDb convertWrapperToDomainWhoIsEmailsDynamoDB(CCRecordWrapper loadingObject) {
    	CCRecordDynamoDb ccRecord = new CCRecordDynamoDb();
        CCJobTitle ccJobTitle = new CCJobTitle();
        CCRecordName ccRecordName = new CCRecordName();
        CCRecordTwitter ccRecordTwitter=new CCRecordTwitter();
        CCRecordLinkedin ccRecordLinkedin=new CCRecordLinkedin();

        loadingObject.getEmail();
        ccRecord.setEmail((loadingObject.getEmail().getS()));
        ccRecord.setDomain((loadingObject.getDomain().getS()));

        Optional.ofNullable(loadingObject)
                .map(o -> o.getJob_title())
                .ifPresent(s -> {
                    if (s.getS() != null)
                        ccJobTitle.setS(s.getS());
                });

        Optional.ofNullable(loadingObject)
                .map(o -> o.getJob_title())
                .map(t -> t.getM())
                .ifPresent(m -> {
                    if (m.getJobTitle() != null)
                        ccJobTitle.setJob_title(m.getJobTitle().getS());
                    if (m.getConfidence() != null && m.getConfidence().getN() != null)
                        ccJobTitle.setConfidence(m.getConfidence().getN());
                });
        Optional.ofNullable(loadingObject)
                .map(o -> o.getName())
                .map(t -> t.getM())
                .ifPresent(m -> {
                    if (m.getPattern() != null && m.getPattern().getS() != null)
                        ccRecordName.setPattern(m.getPattern().getS());
                    if (m.getConfidence() != null && m.getConfidence().getN() != null)
                        ccRecordName.setConfidence(m.getConfidence().getN());
                    if (m.getName() != null && m.getName().getS() != null)
                        ccRecordName.setName(m.getName().getS());
                });

        Optional.ofNullable(loadingObject)
                .map(o -> o.getName())
                .ifPresent(m -> {
                    if (m.getS() != null)
                        ccRecordName.setName(m.getS());
                });


        Optional.ofNullable(loadingObject)
                .map(o -> o.getLinkedin())
                .ifPresent(s -> {
                    if (s.getS() != null)
                        ccRecordLinkedin.setS(s.getS());
                });

        Optional.ofNullable(loadingObject)
                .map(t-> t.getLinkedin())
                .map(o -> o.getM())
                .ifPresent(s->{
                    if (s.getLinkedin() != null )
                        ccRecordLinkedin.setUsername(s.getLinkedin().getS());
                    if (s.getLinkedin() != null )
                        ccRecordLinkedin.setConfidence(s.getConfidence().getN());
                });

        Optional.ofNullable(loadingObject)
                .map(o -> o.getTwitter())
                .ifPresent(s -> {
                    if (s.getS() != null)
                        ccRecordTwitter.setS(s.getS());
                });
        Optional.ofNullable(loadingObject)
                .map(l-> l.getTwitter())
                .map(o -> o.getM())
                .ifPresent(s->{
                    if (s.getTwitter() != null )
                        ccRecordTwitter.setUsername(s.getTwitter().getS());
                    if (s.getTwitter() != null )
                        ccRecordTwitter.setConfidence(s.getConfidence().getN());
                });

      /*  for (L l : loadingObject.getReferences().getL()) {
            Url url1=new Url();
            url1.setS(l.getM().getUrl().getS());
        }
        for (L l : loadingObject.getReferences().getL()) {
            Timestamp timestamp1 = new Timestamp();
            timestamp1.setS(l.getM().getTimestamp().getS());
        }*/

        ccRecord.setName(ccRecordName);
        ccRecord.setJob_title(ccJobTitle);
        ccRecord.setLinkedIns(ccRecordLinkedin);
        ccRecord.setTwitter(ccRecordTwitter);
        return ccRecord;
    }
}

