package transport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PduReader {

    InputStream input;

    public PduReader(InputStream inputStream){
        this.input = inputStream;
    }

    public byte[] readPdu() throws  IOException {

        byte[] lengthBytes  = readFully(4);

        int commandLength =
                ((lengthBytes[0] & 0xFF) << 24) |
                        ((lengthBytes[1] & 0xFF) << 16) |
                        ((lengthBytes[2] & 0xFF) << 8) |
                        (lengthBytes[3] & 0xFF);

        if (commandLength < 16) {
            throw new IOException(
                    "Invalid SMPP command length: "
                            + commandLength
            );
        }

        int remainingLength = commandLength  - 4;
        byte[] pdu = new byte[commandLength];
        System.arraycopy(lengthBytes , 0  , pdu , 0 , lengthBytes.length);
        byte[] remainingPdu = readFully(remainingLength);
        System.arraycopy(remainingPdu, 0, pdu, 4, remainingLength);
        return pdu;
    }

    public byte[] readFully(int len) throws IOException {
        byte[] buffer = new byte[len];
        int offset = 0;
        while (offset < len){
            int read = input.read(buffer , offset , len - offset );

            if(read == -1){
                throw new IOException("Connection Closed while Reading PDU");
            }
            offset += read;
        }
        return  buffer;
    }
}