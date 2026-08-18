package storage;


import pduObjects.OutboundPdu;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Queue {
    public static BlockingQueue<OutboundPdu> submitSmQ = new LinkedBlockingQueue<>();
}