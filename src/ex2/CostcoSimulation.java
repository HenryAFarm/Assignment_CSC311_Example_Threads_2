package ex2;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CostcoSimulation {
    private CostcoSimulation() {
    }

    public static void main(String[] args) throws InterruptedException {
        try (Scanner scanner = new Scanner(System.in)) {
            int customerCount = readInteger(scanner, "Number of customers: ", 1);
            int cashierCount = readInteger(scanner, "Number of cashiers: ", 1);
            int maxShoppingTimeSeconds = readInteger(
                    scanner, "Maximum shopping time (seconds): ", 0);
            int maxCheckoutTimeSeconds = readInteger(
                    scanner, "Maximum checkout time (seconds): ", 0);

            runSimulation(customerCount, cashierCount,
                    maxShoppingTimeSeconds, maxCheckoutTimeSeconds);
        }
    }

    private static int readInteger(Scanner scanner, String prompt, int minimum) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value >= minimum) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // Prompt again when the input is not an integer.
            }

            System.out.printf("Enter an integer greater than or equal to %d.%n", minimum);
        }
    }

    private static void runSimulation(int customerCount, int cashierCount,
                                      int maxShoppingTimeSeconds,
                                      int maxCheckoutTimeSeconds)
            throws InterruptedException {
        CheckoutQueue checkoutQueue = new CheckoutQueue();
        AtomicInteger completedCustomers = new AtomicInteger();
        int customerWorkerCount = Math.min(customerCount,
                Math.max(1, Runtime.getRuntime().availableProcessors()));
        ExecutorService customerExecutor = Executors.newFixedThreadPool(customerWorkerCount);
        ExecutorService cashierExecutor = Executors.newFixedThreadPool(cashierCount);

        try {
            for (int cashierId = 1; cashierId <= cashierCount; cashierId++) {
                cashierExecutor.execute(new Cashier(cashierId, checkoutQueue,
                        maxCheckoutTimeSeconds, completedCustomers));
            }

            for (int customerId = 1; customerId <= customerCount; customerId++) {
                customerExecutor.execute(new Customer(customerId, checkoutQueue,
                        maxShoppingTimeSeconds));
            }

            customerExecutor.shutdown();
            awaitTermination(customerExecutor);

            checkoutQueue.close(cashierCount);
            cashierExecutor.shutdown();
            awaitTermination(cashierExecutor);

            System.out.printf("Simulation complete: %d of %d customers served.%n",
                    completedCustomers.get(), customerCount);
        } catch (InterruptedException exception) {
            customerExecutor.shutdownNow();
            cashierExecutor.shutdownNow();
            Thread.currentThread().interrupt();
            throw exception;
        }
    }

    private static void awaitTermination(ExecutorService executorService)
            throws InterruptedException {
        while (!executorService.awaitTermination(1, TimeUnit.DAYS)) {
            // Continue waiting for all submitted simulation tasks.
        }
    }
}
