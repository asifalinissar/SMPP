package service;


import builder.PduBuilder;
import pduObjects.OutboundPdu;
import pduObjects.SmsSegment;
import pduObjects.SubmitSm;
import storage.Queue;
import util.*;

import java.util.List;

public class SmsMessageProcessor {

    public static final byte DEFAULT = 0x00;
    public static final byte UCS2 = 0x08;

    Gsm7Segmenter gsm7Segmenter;
    SmsSegmentBuilder smsSegmentBuilder;
    Gsm7UdhPacker gsm7UdhPacker;
    Gsm7Encoder gsm7Encoder;
    PduBuilder pduBuilder;
    Ucs2Segmenter ucs2Segmenter;
    Ucs2Encoder ucs2Encoder;
    Ucs2UdhPacker ucs2UdhPacker;
    ReferenceNumberGenerator referenceGenerator = new ReferenceNumberGenerator();
    SequenceNumberGenerator sequenceNumberGenerator = new SequenceNumberGenerator();

    public SmsMessageProcessor() {

        this.gsm7Segmenter = new Gsm7Segmenter();
        this.smsSegmentBuilder = new SmsSegmentBuilder(referenceGenerator);
        this.gsm7UdhPacker = new Gsm7UdhPacker();
        this.gsm7Encoder = new Gsm7Encoder();
        this.pduBuilder = new PduBuilder();
        this.ucs2Segmenter = new Ucs2Segmenter();
        this.ucs2Encoder = new Ucs2Encoder();
        this.ucs2UdhPacker = new Ucs2UdhPacker();
    }

    public void process(SubmitSm submitSm) {

        if (submitSm.getDataCoding() == DEFAULT) {

            List<String> parts = gsm7Segmenter.segment(submitSm.getShortMessage());

            if (parts.size() == 1) {
                int sequenceId = sequenceNumberGenerator.next();
                byte[] shortMessage = gsm7Encoder.encode(parts.get(0));
                byte[] pdu = pduBuilder.buildSubmitSm(submitSm, sequenceId, shortMessage);
                OutboundPdu outboundPdu = new OutboundPdu(sequenceId , 0x00000004 , pdu);
                Queue.submitSmQ.add(outboundPdu);
                return;
            }

            List<SmsSegment> segments = smsSegmentBuilder.build(parts);
            for (SmsSegment segment : segments) {
                int sequenceId = sequenceNumberGenerator.next();
                byte[] shortMessage = gsm7UdhPacker.buildUdhShortMsg(segment);
                byte[] pdu = pduBuilder.buildSubmitSm(submitSm, sequenceId, shortMessage);
                OutboundPdu outboundPdu = new OutboundPdu(sequenceId , 0x00000004 , pdu);
                Queue.submitSmQ.add(outboundPdu);
            }

            return;
        }

        if (submitSm.getDataCoding() == UCS2) {
            List<String> parts = ucs2Segmenter.segments(submitSm.getShortMessage());
            if(parts.size() == 1){
                int sequenceId = sequenceNumberGenerator.next();
                byte [] shortMsg = ucs2Encoder.encode(parts.get(0));
                byte [] pdu = pduBuilder.buildSubmitSm(submitSm , sequenceId, shortMsg);
                OutboundPdu outboundPdu = new OutboundPdu(sequenceId , 0x00000004 , pdu);
                Queue.submitSmQ.add(outboundPdu);
                return;
            }
            List<SmsSegment> segments = smsSegmentBuilder.build(parts);
            for(SmsSegment smsSegment : segments){
                int sequenceId = sequenceNumberGenerator.next();
                byte[] udhShortMsg = ucs2UdhPacker.buildShortMsg(smsSegment);
                byte[] pdu = pduBuilder.buildSubmitSm(submitSm , sequenceId , udhShortMsg);
                OutboundPdu outboundPdu = new OutboundPdu(sequenceId , 0x00000004 , pdu);
                Queue.submitSmQ.add(outboundPdu);
            }
            return;
        }

        throw new IllegalArgumentException("Unsupported data coding: " + submitSm.getDataCoding()
        );
    }

//    private int nextSequenceId() {
//        return sequenceId.updateAndGet(
//                value -> (value + 1) & 0x7FFFFFFF
//        );
//
//    }

}