package encoder;

import pduObjects.BindTransceiver;
import util.PduWriter;

public class BindTransceiverEncoder {

    public byte [] encode(BindTransceiver bindTransceiver){

        PduWriter bindWriter = new PduWriter();

        bindWriter.writeCString(bindTransceiver.getSystemId());
        bindWriter.writeCString(bindTransceiver.getPassword());
        bindWriter.writeCString(bindTransceiver.getSystemType());
        bindWriter.writeByte(bindTransceiver.getInterfaceVersion());
        bindWriter.writeByte(bindTransceiver.getAddrTon());
        bindWriter.writeByte(bindTransceiver.getAddrNpi());
        bindWriter.writeCString(bindTransceiver.getAddressRange());

        return bindWriter.toByteArray();
    }
}