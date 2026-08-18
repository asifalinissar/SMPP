package util;

import pduObjects.SmsSegment;

public class UdhBuilder {
    public byte[] build(SmsSegment smsSegment){
        int referenceNumber = smsSegment.getReferenceNumber();
        int totalSegment = smsSegment.getTotalSegments();
        int currentSegment = smsSegment.getSegmentNumber();

        return new byte[] {
                0x05,
                0x00,
                0x03,
                (byte) referenceNumber,
                (byte) totalSegment,
                (byte) currentSegment
        };
    }
}