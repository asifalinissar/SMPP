package util;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class PduWriter {

    private final ByteArrayOutputStream out;

    public PduWriter(){
        out = new ByteArrayOutputStream();
    }
    public void writeByte(byte value){
        out.write(value);
    }
    public void writeCString(String value){
        byte [] bytes = value.getBytes(StandardCharsets.US_ASCII);
        try {
            out.write(bytes);
            out.write(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void writeInt(int value){
        byte[] bytes  = new byte[4];
        bytes[0] = (byte) ((value >> 24) & 0xFF);
        bytes[1] = (byte) ((value >> 16) & 0xFF);
        bytes[2] = (byte) ((value >> 8) & 0xFF);
        bytes[3] = (byte) (value & 0xFF);

        writeBytes(bytes);
    }
    public void writeBytes(byte[] value){
        if (value != null){
            try {
                out.write(value);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public byte[] toByteArray() {
        return  out.toByteArray();
    }
}