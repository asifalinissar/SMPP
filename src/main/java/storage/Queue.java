package storage;


import pduObjects.OutboundPdu;
import pduObjects.SubmitSm;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Queue {
    public static BlockingQueue<OutboundPdu> submitSmQ = new LinkedBlockingQueue<>();
    public static BlockingQueue<byte []> submitSmRspQ = new LinkedBlockingQueue<>();
    public static BlockingQueue<SubmitSm> kafkaConsumerQ = new LinkedBlockingQueue<>();
}