package builder;

import encoder.BindTransceiverEncoder;
import encoder.HeaderEncoder;
import pduObjects.BindTransceiver;
import pduObjects.PduHeader;

public class BindTransceiverBuilder {

    BindTransceiverEncoder bindTransceiverEncoder;
    HeaderEncoder headerEncoder;

    public BindTransceiverBuilder(){
        this.bindTransceiverEncoder = new BindTransceiverEncoder();
        this.headerEncoder = new HeaderEncoder();
    }

    public byte[] buildBindTransceiver(BindTransceiver bindTransceiver , int sequenceId){

        byte[] body = bindTransceiverEncoder.encode(bindTransceiver);
        int commandLeng = 16 + body.length;

        PduHeader header = new PduHeader();
        header.setCommandLength(commandLeng);
        header.setCommandId(0x00000009);
        header.setCommandStatus(0);
        header.setSequenceNumber(sequenceId);

        byte[] headerBytes = headerEncoder.encode(header);

        byte[] bindTransceiverPdu = new byte[headerBytes.length + body.length];

        System.arraycopy(headerBytes , 0 , bindTransceiverPdu , 0 , headerBytes.length);
        System.arraycopy(body , 0 , bindTransceiverPdu , headerBytes.length , body.length);

        return bindTransceiverPdu;

    }
}