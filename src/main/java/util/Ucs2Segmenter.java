package util;

import java.util.ArrayList;
import java.util.List;

public class Ucs2Segmenter {

    private static final int SINGLE_LIMIT = 70;
    private static final int MULTIPART_LIMIT = 67;

    public List<String> segments(String shortMsg){
        if(shortMsg == null){
            throw new IllegalArgumentException("Message cannot be null");
        }
        if(shortMsg.length() <= SINGLE_LIMIT){
            List<String> result = new ArrayList<>();
            result.add(shortMsg);
            return  result;
        }
        return splitMultipart(shortMsg);
    }
    private List<String> splitMultipart(String shortMsg){
        List<String> segments  = new ArrayList<>();
        for(int  start = 0 ; start < shortMsg.length() ; start += MULTIPART_LIMIT){
            int end = Math.min(start+MULTIPART_LIMIT , shortMsg.length());
            segments.add(shortMsg.substring(start , end));
        }
        return segments;
    }
}