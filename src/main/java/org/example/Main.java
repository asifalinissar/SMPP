package org.example;


import builder.BindTransceiverBuilder;
import handler.Controller;
import pduObjects.BindTransceiver;
import pduObjects.PduHeader;
import pduObjects.PduHeaderReader;
import service.PendingRequestManager;
import service.SmppSession;
import transport.SmppConnection;
import transport.TransportHandler;

public class Main {

    public  static void main(String[] args){

        Controller controller = new Controller();
        controller.start();
    }

}