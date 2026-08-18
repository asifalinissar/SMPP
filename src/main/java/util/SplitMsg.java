package util;

import java.util.ArrayList;
import java.util.Set;

public class SplitMsg {

    Set<Character> extended = Set.of(
            '^', '{', '}', '\\', '[', '~', ']', '|', '€'
    );

    private static final int SINGLE_GSM_LIMIT = 160;
    private static final int MULTIPART_GSM_LIMIT = 153;

    public ArrayList<String> Split(String value){
        StringBuilder segment = new StringBuilder();
        ArrayList<String> arrayList = new ArrayList<String>();
        int test_leng = 0;
        for (char character1 : value.toCharArray()){
            test_leng += extended.contains(character1) ? 2 : 1;
        }
        if(test_leng <= SINGLE_GSM_LIMIT){
            arrayList.add(value);
            return arrayList;
        }else {
            int lengthOfMsg = 0;
            for (char character : value.toCharArray()) {
                int val_of_char = extended.contains(character) ? 2 : 1;
                if (lengthOfMsg + val_of_char > MULTIPART_GSM_LIMIT) {
                    arrayList.add(segment.toString());
                    segment.setLength(0);
                    lengthOfMsg = 0;
                }
                segment.append(character);
                lengthOfMsg = lengthOfMsg + val_of_char;
            }
            if (!segment.isEmpty()) {
                arrayList.add(segment.toString());
            }
            return arrayList;
        }
    }

}