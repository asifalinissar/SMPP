package encoder;

import util.PduWriter;
import pduObjects.SubmitSm;

public class SubmitSmEncoder {

    public byte[] encode(SubmitSm submitSm , byte[] shortMessageIn) {

        int smLength = shortMessageIn.length;

        System.out.println("length of each segment: " + smLength);

        PduWriter pduWriter = new PduWriter();

        pduWriter.writeCString(submitSm.getServiceType());

        pduWriter.writeByte(submitSm.getSourceAddrTon());
        pduWriter.writeByte(submitSm.getSourceAddrNpi());
        pduWriter.writeCString(submitSm.getSourceAddr());

        pduWriter.writeByte(submitSm.getDestAddrTon());
        pduWriter.writeByte(submitSm.getDestAddrNpi());
        pduWriter.writeCString(submitSm.getDestAddr());

        pduWriter.writeByte(submitSm.getEsmClass());
        pduWriter.writeByte(submitSm.getProtocolId());
        pduWriter.writeByte(submitSm.getPriorityFlag());

        pduWriter.writeCString(submitSm.getScheduledDeliveryTime());
        pduWriter.writeCString(submitSm.getValidityPeriod());

        pduWriter.writeByte(submitSm.getRegisteredDelivery());
        pduWriter.writeByte(submitSm.getReplaceIfPresentFlag());

        pduWriter.writeByte(submitSm.getDataCoding());
        pduWriter.writeByte(submitSm.getSmDefaultMsgId());

        pduWriter.writeByte((byte) smLength);
        pduWriter.writeBytes(shortMessageIn);

        return pduWriter.toByteArray();
    }
}