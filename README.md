# Wallet Management System
### Domain-Driven Design with Event-Driven Architecture

A practical implementation of DDD and EDA patterns for a wallet management system. This shows you how to build reliable microservices that don't lose data when things go wrong.

## What's This About?

This is a Spring Boot app that handles wallet operations (deposits and withdrawals) using domain-driven design. The interesting part isn't just moving money around - it's how the system guarantees consistency even when failures happen.

## The Problem We're Solving

When you deposit or withdraw money, three things need to happen:
1. Update the wallet balance
2. Record the transaction
3. Publish an event so other systems know what happened

Simple enough, right? But here's the catch: what if the database update works but publishing the event fails? Now your wallet shows the new balance, but the notification service never knew about it. No email sent. Analytics missed it. Audit logs incomplete.

That's where the **Outbox Pattern** saves you.

## How It Works

### Walking Through a Withdrawal

Someone wants to withdraw $50. Let's see what happens under the hood:

**Step 1: Feature Layer** (`WithdrawBalance`)

This is your entry point. It handles feature-specific stuff:
- Is this user allowed to withdraw right now?
- Have they hit their daily limit?
- Any fraud flags we should check?
- Business rules that are specific to withdrawals

**Step 2: Aggregate Root** (`WalletCoreBusiness`)

This is where the core business logic lives. It uses the **Fluent Interface** pattern:

```java
new WalletCoreBusiness(walletService, requestDto)
    .validateWallet()    // Check wallet exists and is valid
    .withdraw()          // Execute the withdrawal
    .setBalance()        // Calculate and set new balance
