package ex2;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Customer implements Runnable {
    private final int customerId;
    private final CheckoutQueue checkoutQueue;
    private final int maxShoppingTimeSeconds;

    public Customer(int customerId, CheckoutQueue checkoutQueue,
                    int maxShoppingTimeSeconds) {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Customer IDs must be positive.");
        }
        if (maxShoppingTimeSeconds < 0) {
            throw new IllegalArgumentException("Shopping time cannot be negative.");
        }

        this.customerId = customerId;
        this.checkoutQueue = Objects.requireNonNull(checkoutQueue, "checkoutQueue");
        this.maxShoppingTimeSeconds = maxShoppingTimeSeconds;
    }

    @Override
    public void run() {
        System.out.printf("Customer %d started shopping.%n", customerId);

        try {
            long maxShoppingTimeMillis = maxShoppingTimeSeconds * 1_000L;
            Thread.sleep(ThreadLocalRandom.current().nextLong(maxShoppingTimeMillis + 1));
            checkoutQueue.addCustomer(customerId);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
