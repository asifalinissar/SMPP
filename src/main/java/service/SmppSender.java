package service;


import pduObjects.OutboundPdu;
import storage.Queue;
import transport.TransportHandler;

import java.awt.desktop.SystemSleepEvent;
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
                System.out.println(outboundPdu.getSequenceNumber() + "Added to the Pending Manager");
                transportHandler.send(outboundPdu.getPdu());

                StringBuilder hex = new StringBuilder();
                for (byte b : outboundPdu.getPdu()) {
                    hex.append(String.format("%02X ", b));
                }
                System.out.println(hex.toString());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}