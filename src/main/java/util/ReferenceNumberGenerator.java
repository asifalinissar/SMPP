package util;

import java.util.concurrent.atomic.AtomicInteger;

public class ReferenceNumberGenerator {
    private final AtomicInteger counter = new AtomicInteger(0);
    public int next(){
        return  counter.updateAndGet(value ->(value + 1) & 0xFF);
    }
}