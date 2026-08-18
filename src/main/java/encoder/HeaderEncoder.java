package encoder;

import pduObjects.PduHeader;
import util.PduWriter;

public class HeaderEncoder {

    public byte[] encode (PduHeader pduHeader){
        PduWriter writer = new PduWriter();
        writer.writeInt(pduHeader.getCommandLength());
        writer.writeInt(pduHeader.getCommandId());
        writer.writeInt(pduHeader.getCommandStatus());
        writer.writeInt(pduHeader.getSequenceNumber());

        return writer.toByteArray();
    }
}