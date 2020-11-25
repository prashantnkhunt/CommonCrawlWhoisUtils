package com.prominentpixel.tyler.processor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class ReaderWriter {
    public ReaderWriter(String filePath, Semaphore semaphore){
        LinkedBlockingQueue<ProcessorRecord> linkedBlockingQueue=new LinkedBlockingQueue<>();
        
        new Thread(new ReaderThread(linkedBlockingQueue,filePath)).start();
        new Thread(new WriterThread(linkedBlockingQueue,filePath,semaphore)).start();
    }
}
