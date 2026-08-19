package encoder;

import util.PduWriter;
import pduObjects.SubmitSm;

public class SubmitSmEncoder {

    public byte[] encode(SubmitSm submitSm , byte[] shortMessageIn , boolean flagMultipart) {

        int smLength = shortMessageIn.length;
        int esmClass = flagMultipart ? (submitSm.getEsmClass() | 0x40) : submitSm.getEsmClass();
        System.out.println("length of each segment: " + smLength);

        PduWriter pduWriter = new PduWriter();

        pduWriter.writeCString(submitSm.getServiceType());

        pduWriter.writeByte(submitSm.getSourceAddrTon());
        pduWriter.writeByte(submitSm.getSourceAddrNpi());
        pduWriter.writeCString(submitSm.getSourceAddr());

        pduWriter.writeByte(submitSm.getDestAddrTon());
        pduWriter.writeByte(submitSm.getDestAddrNpi());
        pduWriter.writeCString(submitSm.getDestAddr());

        pduWriter.writeByte((byte) esmClass);
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