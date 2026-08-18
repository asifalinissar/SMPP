package util;

import pduObjects.SmsSegment;

public class Ucs2UdhPacker {

    UdhBuilder udhBuilder;
    Ucs2Encoder ucs2Encoder;

    public Ucs2UdhPacker(){
        this.udhBuilder = new UdhBuilder();
        this.ucs2Encoder = new Ucs2Encoder();
    }

    public byte[] buildShortMsg(SmsSegment smsSegment){

        byte [] udhData = udhBuilder.build(smsSegment);
        byte [] msgEncoded = ucs2Encoder.encode(smsSegment.getMessage());

        byte[] udhPackedShortMsg = new byte[udhData.length + msgEncoded.length];

        System.arraycopy(udhData , 0  , udhPackedShortMsg , 0 , udhData.length);
        System.arraycopy(msgEncoded , 0 , udhPackedShortMsg , udhData.length, msgEncoded.length);

        return udhPackedShortMsg;
    }
}