package ex2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Henry Arevalo
 *
 */

/*
The Producer-Consumer pattern appears everywhere—from Costco checkout lines where customers (producers)
join queues and cashiers (consumers) serve them, to Netflix buffering video.
In this lab, transform abstract Producer-Consumer code into a Costco checkout simulator.
You'll learn why synchronization prevents race conditions,
how ArrayBlockingQueue coordinates threads, and how simulation enables data-driven business decisions.


You're provided working code (ex2 package) where Producers generate numbers and
Consumers sum them using synchronized BlockingBuffer.
Transform this into: Customer class (shops then joins queue),
Cashier class (serves customers),
CheckoutQueue (holds customer IDs),
and CostcoSimulation (prompts for parameters).
 */







public class BlockingBufferTest {
    public static void main(String[] args) throws InterruptedException {
        // create new thread pool
        ExecutorService executorService = Executors.newCachedThreadPool();

        // create SynchronizedBuffer to store ints
        Buffer sharedLocation = new BlockingBuffer();

        executorService.execute(new Producer(sharedLocation));
        executorService.execute(new Consumer(sharedLocation));

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }
}

