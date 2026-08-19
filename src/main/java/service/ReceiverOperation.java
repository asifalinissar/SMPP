package service;

import pduObjects.PduHeader;
import pduObjects.PduHeaderReader;
import storage.Queue;

import java.io.IOException;

public class ReceiverOperation implements  Runnable{
    private final PendingRequestManager pendingRequestManager;
    PduHeaderReader pduHeaderReader = new PduHeaderReader();

    public ReceiverOperation(PendingRequestManager pendingRequestManager){
        this.pendingRequestManager = pendingRequestManager;
    }
    @Override
    public void run(){
        while(true) {
            try {
                byte[] rspBytes = Queue.submitSmRspQ.take();
                PduHeader pduHeader = pduHeaderReader.readHeader(rspBytes);
                int commandId = pduHeader.getCommandId();
                if(commandId == 0x80000004){
                    pendingRequestManager.remove(pduHeader.getSequenceNumber());
                    System.out.println(pduHeader.getSequenceNumber() + "Removed from Pending request");
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}