# Domain-Driven Design Wallet System

A production-ready implementation of **Domain-Driven Design (DDD)** principles combined with **Event-Driven Architecture (EDA)**, demonstrating advanced patterns for building scalable and maintainable microservices.

## 🏗️ Architecture Overview

This project showcases a sophisticated wallet management system built using modern software architecture patterns:

- **Domain-Driven Design (DDD)** - Strategic and tactical patterns
- **Event-Driven Architecture (EDA)** - Asynchronous event processing
- **Outbox Pattern** - Reliable event publishing with transactional guarantees
- **Saga Pattern** - Distributed transaction management
- **Chain of Responsibility** - Flexible business logic processing

---

## 🎯 Key Features

### 1. Domain-Driven Design (DDD)

The project follows DDD tactical patterns to create a rich domain model:

#### **Bounded Context**
- **Wallet Context**: Manages wallet operations, transactions, and events
- Clear boundaries between domain logic and infrastructure concerns

#### **Domain Entities**
- `Wallet` - Core aggregate representing a user's wallet
- `WalletTransaction` - Value object for transaction details
- `WalletEvent` - Event entity for event sourcing

#### **Aggregates & Aggregate Roots**
- Wallet acts as the aggregate root, ensuring consistency boundaries
- All wallet modifications go through the aggregate root
- Encapsulation of business rules within the domain model

#### **Domain Services**
- `WalletService` - Orchestrates complex wallet operations
- Encapsulates domain logic that doesn't naturally fit within entities

---

### 2. Event-Driven Architecture (EDA)

The system employs event-driven patterns for loose coupling and scalability:

#### **Domain Events**
- `WalletEvent` - Captures state changes in the wallet domain
- Events are immutable and represent facts that occurred in the system
- Event types: `DEPOSIT`, `WITHDRAW`, `BALANCE_UPDATE`, etc.

#### **Event Payload**
- `WalletEventPayload` - Embedded payload containing event-specific data
- Structured data for event consumers

#### **Event Status Tracking**
- `EventStatus` - Tracks event lifecycle (PENDING, PUBLISHED, FAILED)
- Enables monitoring and debugging of event flows

---

### 3. Outbox Pattern

Ensures **reliable event publishing** with transactional consistency:

#### **How It Works**
1. **Transactional Write**: Domain changes and events are written to the database in a single transaction
2. **Event Table**: `WalletEvent` serves as the outbox table
3. **Event Publisher**: A scheduler reads pending events and publishes them to the message broker
4. **Guaranteed Delivery**: Events are marked as published only after successful delivery

#### **Benefits**
- ✅ No lost events - database transaction ensures atomicity
- ✅ At-least-once delivery guarantee
- ✅ No dual-write problem
- ✅ Resilient to system failures

#### **Implementation Details**
- `WalletEvent.status` tracks event publication state
- `retryCount` and `errorMessage` fields support error handling
- Scheduler polls for unpublished events and processes them

---

### 4. Saga Pattern

Manages **distributed transactions** across multiple services:

#### **Orchestration-Based Saga**
- Centralized coordinator manages the saga workflow
- Each step in the saga corresponds to a local transaction
- Compensating transactions handle failures

#### **Saga Execution Flow**
1. **Initiate**: User requests a wallet operation (e.g., withdrawal)
2. **Execute Steps**: Each step publishes events and performs local transactions
3. **Compensate on Failure**: If any step fails, compensating actions are triggered
4. **Complete**: Saga completes successfully or rolls back

#### **Event Versioning**
- `version` field in `WalletEvent` supports event versioning
- Enables saga state reconstruction and idempotency

---

### 5. Chain of Responsibility Pattern

Implements **flexible business rule processing** through feature chains:

#### **Feature Chain Structure**
Located in `/wallet/features/`:
- `DepositBalance` - Handles deposit operations
- `WithdrawBalance` - Handles withdrawal operations

#### **How It Works**
1. **Request Processing**: Each request flows through a chain of feature handlers
2. **Conditional Execution**: Each handler decides whether to process or pass to next handler
3. **Extensibility**: New features can be added without modifying existing code
4. **Separation of Concerns**: Each handler focuses on a single responsibility

#### **Benefits**
- ✅ **Open/Closed Principle**: Open for extension, closed for modification
- ✅ **Single Responsibility**: Each feature handles one aspect
- ✅ **Testability**: Individual handlers can be tested in isolation
- ✅ **Flexibility**: Chain order can be configured dynamically

#### **Example Flow**
