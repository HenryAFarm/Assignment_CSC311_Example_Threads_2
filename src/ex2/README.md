# Costco Checkout Simulation

I worked on this by myself: Henry Arevalo


# Contributions

I worked on the three major classes for the package-ex2: CostcoSimulation, Customer, Producer,
and CheckoutQueue!


## Run the simulation

In IntelliJ, open `CostcoSimulation.java` and run `CostcoSimulation.main()`.

The program prompts for:

1. Number of customers (at least 1)
2. Number of cashiers (at least 1)
3. Maximum customers waiting in the shared checkout queue (at least 1)

Each customer shops for a randomly generated duration of 1 to 5 seconds, then
joins the bounded queue. If it is full, customers wait until a cashier makes
room. Cashiers serve customers in the order they join, taking a random 1 to 5
seconds to check out each customer. The queue size counts waiting customers,
not customers currently being served.

The program prints shopping, queue, and service events,
then reports how many customers were served and the average time customers
waited in line before a cashier became available.

## Ex2 package
The changes to the code and additions are located in the package ex2.
The other packages were there as I clone it from Moaath Alrajab repository

## Current scope

This version runs one simulation per launch. 