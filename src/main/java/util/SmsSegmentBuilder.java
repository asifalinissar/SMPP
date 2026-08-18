package util;


import pduObjects.SmsSegment;

import java.util.ArrayList;
import java.util.List;

public class SmsSegmentBuilder {

    private final ReferenceNumberGenerator referenceNumberGenerator;

    public SmsSegmentBuilder(ReferenceNumberGenerator referenceNumberGenerator){
        this.referenceNumberGenerator = referenceNumberGenerator;
    }
    public List<SmsSegment> build (List<String> message){

        List<SmsSegment> segments = new ArrayList<>();
        int referenceNumber = referenceNumberGenerator.next();
        int totalSegment = message.size();
        for (int i = 0 ; i < totalSegment ; i++){
            SmsSegment smsSegment = new SmsSegment(message.get(i) ,i+ 1,totalSegment ,  referenceNumber );
            segments.add(smsSegment);
        }
        return segments;
    }
}