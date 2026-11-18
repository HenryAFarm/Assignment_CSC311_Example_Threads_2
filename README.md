# Costco Checkout Simulation

I worked on this by myself: Henry Arevalo


# Contributions

I worked on the four major classes for the package-ex2: CostcoSimulation, Customer, Producer,
and CheckoutQueue!


## Run the simulation

In IntelliJ, open `CostcoSimulation.java` and run `CostcoSimulation.main()`.

The program prompts for:

1. Number of customers (at least 1)
2. Number of cashiers (at least 1)

Each customer shops for a randomly generated duration of 1 to 5 seconds, then
joins the queue. Cashiers serve customers in the order they join, taking a
random 1 to 5 seconds to check out each customer.

Each customer shops for a random duration from zero through the maximum
shopping time, then joins the queue. Cashiers serve customers in the order
they join, with each checkout taking a random duration from zero through the
maximum checkout time. 

The program prints shopping, queue, and service events,
then reports how many customers were served.

## Ex2 package
The changes to the code and additions are located in the package ex2.
The other packages were there as I clone it from Moaath Alrajab repository

## Current scope

This version runs one simulation per launch. It does not calculate queue wait
time statistics, model a specified customer-arrival rate, compare staffing
scenarios automatically, or make a cost-versus-service recommendation.
