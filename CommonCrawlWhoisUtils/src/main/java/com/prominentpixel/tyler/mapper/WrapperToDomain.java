package com.prominentpixel.tyler.mapper;

import com.prominentpixel.tyler.dao.commoncrawl.*;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.*;

import java.util.Optional;
public class WrapperToDomain {

    public CCRecord convertWrapperToDomain(CCRecordWrapper loadingObject) {
        CCRecord ccRecord = new CCRecord();
        CCJobTitle ccJobTitle = new CCJobTitle();
        CCRecordName ccRecordName = new CCRecordName();
        CCRecordTwitter ccRecordTwitter=new CCRecordTwitter();
        CCRecordLinkedin ccRecordLinkedin=new CCRecordLinkedin();

        loadingObject.getEmail();
        ccRecord.setEmail((loadingObject.getEmail().getS()));
        ccRecord.setDomain((loadingObject.getDomain().getS()));

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
                .map(t-> t.getLinkedin())
                .map(o -> o.getM())
                .ifPresent(s->{
                    if (s.getLinkedin() != null )
                        ccRecordLinkedin.setUsername(s.getLinkedin().getS());
                    if (s.getLinkedin() != null )
                        ccRecordLinkedin.setConfidence(s.getConfidence().getN());
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

        for (L l : loadingObject.getReferences().getL()) {
            Url url1=new Url();
            url1.setS(l.getM().getUrl().getS());
        }
        for (L l : loadingObject.getReferences().getL()) {
            Timestamp timestamp1 = new Timestamp();
            timestamp1.setS(l.getM().getTimestamp().getS());
        }

        ccRecord.setName(ccRecordName);
        ccRecord.setJob_title(ccJobTitle);
        ccRecord.setLinkedIns(ccRecordLinkedin);
        ccRecord.setTwitter(ccRecordTwitter);
        return ccRecord;
    }
}

