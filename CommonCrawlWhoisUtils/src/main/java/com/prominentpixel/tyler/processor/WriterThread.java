package com.prominentpixel.tyler.processor;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

import com.prominentpixel.tyler.utils.ESUtils;

public class WriterThread implements Runnable {
	private static final Logger logger = Logger.getLogger(WriterThread.class);

    private LinkedBlockingQueue<ProcessorRecord> linkedBlockingQueue;
    private BulkIndexCombined bulkIndexCombined;
    private Semaphore semaphore;
    private String fileName;

    public WriterThread(LinkedBlockingQueue<ProcessorRecord> linkedBlockingQueue, String fileName , Semaphore semaphore) {
        this.linkedBlockingQueue = linkedBlockingQueue;
        this.semaphore = semaphore;
        this.fileName = fileName;
        this.bulkIndexCombined = new BulkIndexCombined(fileName);
    }

    @Override
    public void run() {
            try {

                while (true){
                        ProcessorRecord customRecord = linkedBlockingQueue.take();
                        if (customRecord.isEof()) {
                            bulkIndexCombined.commitIndex();
                            break;
                        }
                        bulkIndexCombined.indexData(customRecord);
                }

                if(bulkIndexCombined.getFailedCount()==0){      // write in output only if file complete successfully
                    ESUtils.writeToOutputFile(this.fileName);
                } else {
                    ESUtils.writeToFailFile(this.fileName);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                logger.error("Exception Generated::", exception);
            } finally {
                semaphore.release();
//                System.exit(0);
            }
        }
}
