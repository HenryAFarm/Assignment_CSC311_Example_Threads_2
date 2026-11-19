package ex2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CheckoutQueue {
    private static final int SHUTDOWN_SIGNAL = -1;
    private final BlockingQueue<QueueEntry> customers;

    public CheckoutQueue() {
        customers = new LinkedBlockingQueue<>();
    }

    public CheckoutQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Queue capacity must be positive.");
        }

        customers = new ArrayBlockingQueue<>(capacity);
    }

    public void addCustomer(int customerId) throws InterruptedException {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Customer IDs must be positive.");
        }

        customers.put(new QueueEntry(customerId, System.nanoTime()));
        System.out.printf("Customer %d joined the checkout queue.%n", customerId);
    }

    public QueueEntry takeNextCustomer() throws InterruptedException {
        return customers.take();
    }

    public void close(int cashierCount) throws InterruptedException {
        if (cashierCount < 0) {
            throw new IllegalArgumentException("Cashier count cannot be negative.");
        }

        for (int i = 0; i < cashierCount; i++) {
            customers.put(new QueueEntry(SHUTDOWN_SIGNAL, 0));
        }
    }

    public static class QueueEntry {
        private final int customerId;
        private final long enqueueTimeNanos;

        private QueueEntry(int customerId, long enqueueTimeNanos) {
            this.customerId = customerId;
            this.enqueueTimeNanos = enqueueTimeNanos;
        }

        public int getCustomerId() {
            return customerId;
        }

        public long getEnqueueTimeNanos() {
            return enqueueTimeNanos;
        }

        public boolean isShutdownSignal() {
            return customerId == SHUTDOWN_SIGNAL;
        }
    }
}
