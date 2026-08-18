package util;



public class MessageEncoder {
    Gsm7Encoder gsm7Encoder = new Gsm7Encoder();
    Ucs2Encoder ucs2Encoder = new Ucs2Encoder();
    public static final byte DEFAULT = 0x00;
    public static final byte UCS2 = 0x08;
    public byte[] encode(byte dataEncoding , String shortMessage){
        if (dataEncoding == DEFAULT){
            return gsm7Encoder.encode(shortMessage);
        } else if (dataEncoding == UCS2) {
            return ucs2Encoder.encode(shortMessage);
        }else {
            throw new IllegalArgumentException(
                    "Unsupported data_coding: 0x"
                            + String.format("%02X", dataEncoding)
            );
        }
    }
}