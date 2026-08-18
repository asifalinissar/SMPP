package pduObjects;

public class SmsSegment {

    private final String message;
    private final int segmentNumber;
    private final int totalSegments;
    private final int referenceNumber;

    public SmsSegment(
            String message,
            int segmentNumber,
            int totalSegments,
            int referenceNumber
    ) {
        this.message = message;
        this.segmentNumber = segmentNumber;
        this.totalSegments = totalSegments;
        this.referenceNumber = referenceNumber;
    }

    public String getMessage() {
        return message;
    }

    public int getSegmentNumber() {
        return segmentNumber;
    }

    public int getTotalSegments() {
        return totalSegments;
    }

    public int getReferenceNumber() {
        return referenceNumber;
    }
}