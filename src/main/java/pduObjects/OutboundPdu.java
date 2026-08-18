package pduObjects;

public class OutboundPdu {

    private final int sequenceNumber;
    private final int commandId;
    private final byte[] pdu;

    public OutboundPdu(
            int sequenceNumber,
            int commandId,
            byte[] pdu
    ) {
        this.sequenceNumber = sequenceNumber;
        this.commandId = commandId;
        this.pdu = pdu;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public int getCommandId() {
        return commandId;
    }

    public byte[] getPdu() {
        return pdu;
    }
}