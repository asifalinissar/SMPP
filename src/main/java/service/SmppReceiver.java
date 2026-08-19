package service;

import storage.Queue;
import transport.TransportHandler;

import java.io.IOException;

public class SmppReceiver implements Runnable{
    private final TransportHandler transportHandler ;
    public SmppReceiver(TransportHandler transportHandler){
        this.transportHandler = transportHandler;
    }
    @Override
    public void run(){
        while(true) {
            try {
                byte[] rsp = transportHandler.receive();
                Queue.submitSmRspQ.put(rsp);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }catch (IOException e){
                e.printStackTrace();
                break;
            }
        }
    }
}