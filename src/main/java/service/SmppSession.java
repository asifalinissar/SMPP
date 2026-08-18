package service;

import builder.BindTransceiverBuilder;
import pduObjects.BindTransceiver;
import pduObjects.OutboundPdu;
import pduObjects.PduHeader;
import pduObjects.PduHeaderReader;
import storage.Queue;
import transport.TransportHandler;

import java.io.IOException;

public class SmppSession {

    private static final int BIND_TRANSCEIVER_RESP = 0x80000009;
    private static final int ESME_ROK = 0x00000000;

    private final TransportHandler transportHandler;
    private final BindTransceiverBuilder bindBuilder;
    private final PendingRequestManager pendingRequestManager;

    private boolean bound = false;

    public SmppSession(TransportHandler transportHandler , BindTransceiverBuilder bindBuilder, PendingRequestManager pendingRequestManager){
        this.transportHandler = transportHandler;
        this.bindBuilder = bindBuilder;
        this.pendingRequestManager = pendingRequestManager;
    }
    public void bind(BindTransceiver bindTransceiver) throws IOException {
//        currently hardcoded after that we will do the dependency injection of the sequence Id
        int sequence_id = 1;
        byte[] bindPdu = bindBuilder.buildBindTransceiver(bindTransceiver , sequence_id);

        transportHandler.connect();
        transportHandler.send(bindPdu);
        byte[] resp = transportHandler.receive();

        PduHeaderReader pduHeaderReader = new PduHeaderReader();
        PduHeader pduHeader = pduHeaderReader.readHeader(resp);

        if (pduHeader.getCommandId() != BIND_TRANSCEIVER_RESP) {
            throw new IllegalStateException(
                    "Unexpected SMPP response"
            );
        }

        if (pduHeader.getSequenceNumber() != sequence_id) {
            throw new IllegalStateException(
                    "Sequence number mismatch"
            );
        }

        if (pduHeader.getCommandStatus() != ESME_ROK) {
            throw new IllegalStateException(
                    "SMPP bind failed: "
                            + pduHeader.getCommandStatus()
            );
        }

        bound = true;

        startSender();
    }

    public boolean isBound() {
        return bound;
    }

    public  void startSender(){
        Thread senderThread = new Thread(new SmppSender(transportHandler , pendingRequestManager));
        senderThread.start();
    }
}