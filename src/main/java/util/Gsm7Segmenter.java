package util;

import java.util.ArrayList;
import java.util.List;

public class Gsm7Segmenter {

    private static final int SINGLE_LIMIT = 160;
    private static final int MULTIPART_LIMIT = 153;
    private final Gsm7Encoder encoder = new Gsm7Encoder();


    public List<String> segment(String message){
        if(message == null){
            throw  new IllegalArgumentException("Message cannot be null");
        }
        int totalSegments = calculateSegment(message);
        if (totalSegments <= SINGLE_LIMIT){
            List<String> result = new ArrayList<>();
            result.add(message);
            return result;
        }
        return splitMultiPart(message);
    }
    private int calculateSegment(String message){
        int totalLen = 0 ;
        for(char c : message.toCharArray()){
            totalLen= totalLen + encoder.septetCount(c);
        }
        return totalLen;
    }
    private List<String> splitMultiPart(String message){
        List<String> listOFSegments = new ArrayList<>();
        StringBuilder segment = new StringBuilder();
        int currentSeptets = 0;
        for(char c : message.toCharArray()){
            int lenOfChar = encoder.septetCount(c);
            if(currentSeptets + lenOfChar > MULTIPART_LIMIT){
                listOFSegments.add(segment.toString());
                segment.setLength(0);
                currentSeptets = 0 ;
            }
            segment.append(c);
            currentSeptets += lenOfChar;
        }
        if(!segment.isEmpty()){
            listOFSegments.add(segment.toString());
        }
        for(String str : listOFSegments){
            System.out.println("length of segmented String"+ str.length());
        }
        return listOFSegments;
    }
}