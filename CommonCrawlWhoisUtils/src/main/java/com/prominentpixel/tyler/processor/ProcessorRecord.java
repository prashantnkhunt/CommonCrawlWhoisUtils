package com.prominentpixel.tyler.processor;

import com.prominentpixel.tyler.dao.commoncrawl.CCRecord;

public class ProcessorRecord {

	private String id;
    private boolean isEof;
    private CCRecord ccRecord;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CCRecord getCcRecord() {
        return ccRecord;
    }

    public void setCcRecord(CCRecord ccRecord) {
        this.ccRecord = ccRecord;
    }

    public boolean isEof() {
        return isEof;
    }

    public void setEof(boolean eof) {
        isEof = eof;
    }
}
