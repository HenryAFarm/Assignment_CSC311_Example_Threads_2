package ex2;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Cashier implements Runnable {
    private final int cashierId;
    private final CheckoutQueue checkoutQueue;
    private final AtomicInteger completedCustomers;
    private final AtomicLong totalWaitTimeNanos;

    public Cashier(int cashierId, CheckoutQueue checkoutQueue,
                   AtomicInteger completedCustomers, AtomicLong totalWaitTimeNanos) {
        if (cashierId <= 0) {
            throw new IllegalArgumentException("Cashier IDs must be positive.");
        }

        this.cashierId = cashierId;
        this.checkoutQueue = Objects.requireNonNull(checkoutQueue, "checkoutQueue");
        this.completedCustomers = Objects.requireNonNull(
                completedCustomers, "completedCustomers");
        this.totalWaitTimeNanos = Objects.requireNonNull(
                totalWaitTimeNanos, "totalWaitTimeNanos");
    }

    @Override
    public void run() {
        try {
            while (true) {
                CheckoutQueue.QueueEntry customer = checkoutQueue.takeNextCustomer();
                int customerId = customer.getCustomerId();
                if (customer.isShutdownSignal()) {
                    System.out.printf("Cashier %d finished for the day.%n", cashierId);
                    return;
                }

                totalWaitTimeNanos.addAndGet(
                        System.nanoTime() - customer.getEnqueueTimeNanos());
                System.out.printf("Cashier %d started serving customer %d.%n",
                        cashierId, customerId);
                int checkoutTimeSeconds = ThreadLocalRandom.current().nextInt(1, 6);
                Thread.sleep(checkoutTimeSeconds * 1_000L);
                completedCustomers.incrementAndGet();
                System.out.printf("Cashier %d finished serving customer %d.%n",
                        cashierId, customerId);
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
