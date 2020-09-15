package com.prominentpixel.tyler.mapper;

import com.prominentpixel.tyler.dao.commoncrawl.*;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.CCRecordWrapper;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.L;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.Timestamp;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.Url;

import java.util.Optional;
public class WrapperToDomain {

    public CCRecord convertWrapperToDomain(CCRecordWrapper loadingObject) {
        CCRecord ccRecord = new CCRecord();
        CCJobTitle ccJobTitle = new CCJobTitle();
        CCRecordName ccRecordName = new CCRecordName();

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

        for (L l : loadingObject.getReferences().getL()) {
            Url url1=new Url();
            url1.setS(l.getM().getUrl().getS());
        }
        for (L l : loadingObject.getReferences().getL()) {
            Timestamp timestamp1 = new Timestamp();
            timestamp1.setS(l.getM().getTimestamp().getS());
        }
        return ccRecord;
    }
}

