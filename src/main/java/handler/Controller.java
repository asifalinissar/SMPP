package  handler;

import builder.BindTransceiverBuilder;
import kafka.KafkaConfig;
import pduObjects.BindTransceiver;
import service.*;
import transport.TransportHandler;

public class Controller {

    private  static String host = "0.0.0.0";
    private  static  int port = 2775;


    PendingRequestManager pendingRequestManager = new PendingRequestManager();
    TransportHandler transportHandler = new TransportHandler(host , port);
    KafkaConfig kafkaConfig = new KafkaConfig();
    SmsMessageProcessor smsMessageProcessor = new SmsMessageProcessor();
    SmppReceiver smppReceiver = new SmppReceiver(transportHandler);
    ReceiverOperation receiverOperation = new ReceiverOperation(pendingRequestManager);

    public void start(){
        Thread startKafkaConsume = new Thread(() -> {kafkaConfig.ConnectKafka();});
        startKafkaConsume.start();
        Thread submitSmSender = new Thread(() -> {smsMessageProcessor.process();});
        submitSmSender.start();
        setUpBind();
        Thread reciverThread = new Thread(smppReceiver);
        reciverThread.start();
        Thread receiverOperationThread = new Thread(receiverOperation);
        receiverOperationThread.start();
    }

    public void setUpBind(){
        BindTransceiverBuilder builder = new BindTransceiverBuilder();
        PendingRequestManager pendingRequestManager = new PendingRequestManager();
        SmppSession smppSession = new SmppSession(transportHandler ,builder ,pendingRequestManager);
        try{
            smppSession.bind(createBindTransceiver());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public BindTransceiver createBindTransceiver() {
        BindTransceiver bind = new BindTransceiver();

        bind.setSystemId("test");
        bind.setPassword("test");

        // Common defaults for SMPP 3.4
        bind.setSystemType("");                 // Usually empty
        bind.setInterfaceVersion((byte) 0x34);  // SMPP v3.4
        bind.setAddrTon((byte) 0);              // Address TON = Unknown
        bind.setAddrNpi((byte) 0);              // Address NPI = Unknown
        bind.setAddressRange("");               // Empty range (allows any source address)

        // Your credentials
        return bind;
    }
}