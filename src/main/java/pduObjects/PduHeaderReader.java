package pduObjects;

import java.util.Arrays;

public class PduHeaderReader {
    public PduHeader readHeader(byte[] bytes){
        if (bytes == null || bytes.length < 16){
            throw  new IllegalArgumentException("SMPP Pdu must contain at least 16 Bytes");
        }
        PduHeader pduHeader = new PduHeader();
        pduHeader.setCommandLength(writeInt(bytes , 0));
        pduHeader.setCommandId(writeInt(bytes , 4));
        pduHeader.setCommandStatus(writeInt(bytes , 8));
        pduHeader.setSequenceNumber(writeInt(bytes , 12));

        return pduHeader;
    }
    public int writeInt(byte[] bytes , int offset){
        return ((bytes[offset]     & 0xFF) << 24)
                | ((bytes[offset + 1] & 0xFF) << 16)
                | ((bytes[offset + 2] & 0xFF) << 8)
                |  (bytes[offset + 3] & 0xFF);
    }
}