package transport;

import java.io.IOException;

public class TransportHandler {

    private final SmppConnection connection;
    private final PduReader pduReader;

    public TransportHandler(String host, int port) {
        this.connection =
                new SmppConnection(host, port);

        try{
            connect();
        }catch (Exception e){
            System.out.println("Error in setting the input stream");
            e.printStackTrace();
        }

        this.pduReader =
                new PduReader(connection.getInput());
    }

    public void connect() throws IOException {
        System.out.println("Connection called");
        connection.connect();
    }

    public void send(byte[] pdu) throws IOException {
        connection.sendBytes(pdu);
    }

    public byte[] receive() throws IOException {
        System.out.println(pduReader.input);
        return pduReader.readPdu();
    }

    public void close() throws IOException {
        connection.close();
    }
}