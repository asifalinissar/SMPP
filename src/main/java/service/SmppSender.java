package service;


import pduObjects.OutboundPdu;
import storage.Queue;
import transport.TransportHandler;

import java.io.IOException;

public class SmppSender implements  Runnable{
    private  final TransportHandler transportHandler;
    private final PendingRequestManager pendingRequestManager;
    public SmppSender(TransportHandler transportHandler , PendingRequestManager pendingRequestManager){
        this.transportHandler = transportHandler;
        this.pendingRequestManager = pendingRequestManager;
    }
    @Override
    public void run(){
        while(true){
            try {
                OutboundPdu outboundPdu = Queue.submitSmQ.take();
                pendingRequestManager.add(outboundPdu);
                transportHandler.send(outboundPdu.getPdu());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}