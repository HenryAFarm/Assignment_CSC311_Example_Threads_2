package ex2;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Cashier implements Runnable {
    private final int cashierId;
    private final CheckoutQueue checkoutQueue;
    private final int maxCheckoutTimeSeconds;
    private final AtomicInteger completedCustomers;

    public Cashier(int cashierId, CheckoutQueue checkoutQueue,
                   int maxCheckoutTimeSeconds, AtomicInteger completedCustomers) {
        if (cashierId <= 0) {
            throw new IllegalArgumentException("Cashier IDs must be positive.");
        }
        if (maxCheckoutTimeSeconds < 0) {
            throw new IllegalArgumentException("Checkout time cannot be negative.");
        }

        this.cashierId = cashierId;
        this.checkoutQueue = Objects.requireNonNull(checkoutQueue, "checkoutQueue");
        this.maxCheckoutTimeSeconds = maxCheckoutTimeSeconds;
        this.completedCustomers = Objects.requireNonNull(
                completedCustomers, "completedCustomers");
    }

    @Override
    public void run() {
        try {
            while (true) {
                int customerId = checkoutQueue.takeNextCustomer();
                if (customerId == CheckoutQueue.SHUTDOWN_SIGNAL) {
                    System.out.printf("Cashier %d finished for the day.%n", cashierId);
                    return;
                }

                System.out.printf("Cashier %d started serving customer %d.%n",
                        cashierId, customerId);
                long maxCheckoutTimeMillis = maxCheckoutTimeSeconds * 1_000L;
                Thread.sleep(ThreadLocalRandom.current().nextLong(maxCheckoutTimeMillis + 1));
                completedCustomers.incrementAndGet();
                System.out.printf("Cashier %d finished serving customer %d.%n",
                        cashierId, customerId);
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
