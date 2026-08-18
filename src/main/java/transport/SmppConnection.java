package transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SmppConnection {

    public InputStream getInput() {
        return input;
    }

    private final String host;
    private final int  port;

    private Socket socket;
    private InputStream input;
    private OutputStream output;

    public SmppConnection(String host , int port){
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(host , port);
        input = socket.getInputStream();
        output = socket.getOutputStream();
    }

    public void sendBytes(byte [] pdu) throws  IOException{
        output.write(pdu);
        output.flush();
    }

    public void close() throws IOException {

        if (socket != null) {
            socket.close();
        }
    }

}