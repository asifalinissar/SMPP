package util;

import java.nio.charset.StandardCharsets;

public class Ucs2Encoder {
    public byte[] encode(String shortMessage){
        if(shortMessage ==null){
            throw new IllegalArgumentException("Message cannot be null");
        }
        return shortMessage.getBytes(StandardCharsets.UTF_16BE);
    }
}