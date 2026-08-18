package transport;

import java.io.IOException;

public class TransportHandler {

    private final SmppConnection connection;
    private final PduReader pduReader;

    public TransportHandler(String host, int port) {

        this.connection =
                new SmppConnection(host, port);

        this.pduReader =
                new PduReader(connection.getInput());
    }

    public void connect() throws IOException {
        connection.connect();
    }

    public void send(byte[] pdu) throws IOException {
        connection.sendBytes(pdu);
    }

    public byte[] receive() throws IOException {
        return pduReader.readPdu();
    }

    public void close() throws IOException {
        connection.close();
    }
}