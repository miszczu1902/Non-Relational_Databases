import kafka.producers.HotelProducer;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        HotelProducer.initProducer();
    }
}
