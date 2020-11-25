package com.prominentpixel.tyler.processor;

import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;


public class BulkProcessorActionListener implements BulkProcessor.Listener {

    private String fileName;

    private int failedCount = 0;

    Logger logger = Logger.getLogger(BulkProcessorActionListener.class.getName());

    public BulkProcessorActionListener(){
        this.fileName = "UNKNOWN";
    }
    public BulkProcessorActionListener(String fileName){
        this.fileName = fileName;
    }
    public static volatile int counter = 0;

    @Override
    public void beforeBulk(long executionId, BulkRequest bulkRequest) {
        int numberOfActions = bulkRequest.numberOfActions();
        counter++;
        logger.info("Going to execute bulk "+executionId+" with "+numberOfActions+" requests"+ ",active request = "+counter);
    }

    @Override
    public void afterBulk(long executionId, BulkRequest bulkRequest, BulkResponse bulkResponse) {
        counter--;
        if (bulkResponse.hasFailures() && !bulkResponse.buildFailureMessage().contains("Document contains at least one immense term in field") && !bulkResponse.buildFailureMessage().contains(" nested documents has exceeded the allowed limit of")) {
            failedCount++;
            logger.error("Bulk "+executionId+" executed with failures, Message : "  +  bulkResponse.buildFailureMessage()+ ",active request = "+counter);
        } else {
            logger.info("Bulk "+executionId+" completed in "+bulkResponse.getTook().getMillis()+",active request = "+counter);
        }
    }

    @Override
    public void afterBulk(long executionId, BulkRequest bulkRequest, Throwable throwable) {
        counter--;
        failedCount++;
        logger.error("Failed to execute bulk execution id:"+executionId, throwable);
    }

    public int getFailedCount() {
        return failedCount;
    }
}
