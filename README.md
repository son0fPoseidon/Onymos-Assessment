# README

---

## Code Structure
```plaintext
Onymos/
├── Order.java               # Order class
├── OrderBook.java           # Manages linked lists of buy and sell orders
├── TradingEngine.java       # trading engine and order matching logic
└── StockTradingSimulation.java # Simulates random stock transactions
```

### Class Descriptions
1. **Order.java**
    - Represents an individual buy or sell order.
    - Contains attributes like `type`, `ticker`, `quantity`, `price`, and `orderId`.

2. **OrderBook.java**
    - Maintains linked lists for buy and sell orders.
    - Provides methods to add orders while maintaining order priority.
    - Uses `ReentrantLock` to handle concurrent access safely.

3. **TradingEngine.java**
    - Executes trades if buy and sell orders match the price criteria.

4. **StockTradingSimulation.java**
    - Generates random simultaneous stock transactions for testing the trading engine.

---

## How to Run
### 1. Compile All Java Files
```bash
cd Onymos
javac *.java
```

### 2. Run the Simulation
```bash
java StockTradingSimulation
```

### Expected Output
```plaintext
Trade executed: 1 TICKER426 at 290.77
Trade executed: 16 TICKER625 at 209.35
Trade executed: 11 TICKER270 at 171.26
Trade executed: 37 TICKER397 at 191.32
```

---

## Time Complexity Analysis
1. In both `addBuyOrder` and `addSellOrder`, the function traverses the linked list to find the correct insertion point. This takes at most **O(n)** time. When matching orders, it iterates through existing buy or sell orders linearly until a suitable match is found or the list is exhausted.
2. Also Each matched trade modifies the head of the list (`buyHead` or `sellHead`), which is an **O(1)** operation.
---
