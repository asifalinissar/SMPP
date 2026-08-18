package service;

import pduObjects.OutboundPdu;

import java.util.concurrent.ConcurrentHashMap;

public class PendingRequestManager {

    private final ConcurrentHashMap<Integer, OutboundPdu> pendingRequests =
            new ConcurrentHashMap<>();

    public void add(OutboundPdu outboundPdu){
        pendingRequests.put(outboundPdu.getSequenceNumber() , outboundPdu);
    }
    public OutboundPdu remove(int sequenceId){
        return pendingRequests.remove(sequenceId);
    }

    public OutboundPdu get(int sequenceNumber) {
        return pendingRequests.get(sequenceNumber);
    }

}