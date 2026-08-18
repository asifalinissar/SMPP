package builder;

import encoder.*;
import pduObjects.PduHeader;
import pduObjects.SubmitSm;

public class PduBuilder {
    private final SubmitSmEncoder submitSmEncoder ;
    private final HeaderEncoder headerEncoder;
    public PduBuilder(){
        this.submitSmEncoder = new SubmitSmEncoder();
        this.headerEncoder = new HeaderEncoder();
    }
    public byte[] buildSubmitSm(SubmitSm submitSm , int sequence_id ,byte[] shortMessage){
        byte [] body = submitSmEncoder.encode(submitSm , shortMessage);
        int command_length = 16 + body.length;
        PduHeader pduHeader = new PduHeader();
        pduHeader.setCommandLength(command_length);
        pduHeader.setCommandId(0x00000004);
        pduHeader.setCommandStatus(0);
        pduHeader.setSequenceNumber(sequence_id);

        byte [] headerBytes = headerEncoder.encode(pduHeader);

        byte[] pdu = new byte[headerBytes.length + body.length];

        System.arraycopy(headerBytes , 0 , pdu , 0 , headerBytes.length);
        System.arraycopy(body , 0 , pdu , headerBytes.length , body.length);

        return pdu;
    }
}