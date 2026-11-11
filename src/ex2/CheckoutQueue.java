package ex2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CheckoutQueue {
    public static final int SHUTDOWN_SIGNAL = -1;

    private final BlockingQueue<Integer> customerIds = new LinkedBlockingQueue<>();

    public void addCustomer(int customerId) throws InterruptedException {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Customer IDs must be positive.");
        }

        customerIds.put(customerId);
        System.out.printf("Customer %d joined the checkout queue.%n", customerId);
    }

    public int takeNextCustomer() throws InterruptedException {
        return customerIds.take();
    }

    public void close(int cashierCount) throws InterruptedException {
        if (cashierCount < 0) {
            throw new IllegalArgumentException("Cashier count cannot be negative.");
        }

        for (int i = 0; i < cashierCount; i++) {
            customerIds.put(SHUTDOWN_SIGNAL);
        }
    }
}
