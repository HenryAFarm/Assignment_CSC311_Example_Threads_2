package ex2;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Customer implements Runnable {
    private final int customerId;
    private final CheckoutQueue checkoutQueue;

    public Customer(int customerId, CheckoutQueue checkoutQueue) {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Customer IDs must be positive.");
        }

        this.customerId = customerId;
        this.checkoutQueue = Objects.requireNonNull(checkoutQueue, "checkoutQueue");
    }

    @Override
    public void run() {
        System.out.printf("Customer %d started shopping.%n", customerId);

        try {
            int shoppingTimeSeconds = ThreadLocalRandom.current().nextInt(1, 6);
            Thread.sleep(shoppingTimeSeconds * 1_000L);

            System.out.printf("Customer %d finished shopping.%n", customerId);
            checkoutQueue.addCustomer(customerId);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
