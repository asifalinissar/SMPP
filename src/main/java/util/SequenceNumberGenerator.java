package util;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SequenceNumberGenerator {
    private final AtomicInteger counter = new AtomicInteger(0);

    public int next(){
        return counter.updateAndGet(
                value -> value == 0x7FFFFFFF
                        ? 1
                        : value + 1
        );
    }
}