import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


class TradingEngine {
    private final OrderBook[] orderBooks = new OrderBook[1024];
    private final AtomicInteger orderCounter = new AtomicInteger(1);

    private final ReentrantLock[] locks = new ReentrantLock[1024];

    public TradingEngine() {
        for (int i = 0; i < 1024; i++) {
            orderBooks[i] = new OrderBook();
            locks[i] = new ReentrantLock();
        }
    }

    public void addOrder(Order.Type type, String ticker, int quantity, double price) {
        int tickerIndex = Integer.parseInt(ticker.replace("TICKER", "")) - 1;
        locks[tickerIndex].lock();
        try {
            Order newOrder = new Order(type, ticker, quantity, price, orderCounter.getAndIncrement());
            matchOrder(newOrder, orderBooks[tickerIndex]);
        } finally {
            locks[tickerIndex].unlock();
        }
    }

    public void matchOrder(Order newOrder, OrderBook orderBook) {
        if (newOrder.type == Order.Type.BUY) {
            while (newOrder.quantity > 0 && orderBook.sellHead != null && newOrder.price >= orderBook.sellHead.price) {
                Order bestSell = orderBook.sellHead;
                int quantity = Math.min(newOrder.quantity, bestSell.quantity);
                System.out.printf("Trade executed: %d %s at %.2f%n", quantity, newOrder.ticker, bestSell.price);

                newOrder.quantity -= quantity;
                bestSell.quantity -= quantity;

                if (bestSell.quantity == 0) {
                    orderBook.sellHead = bestSell.next;
                }
            }
            if (newOrder.quantity > 0) {
                orderBook.addOrder(newOrder);
            }
        } else { 
            while (newOrder.quantity > 0 && orderBook.buyHead != null && newOrder.price <= orderBook.buyHead.price) {
                Order bestBuy = orderBook.buyHead;
                int quantity = Math.min(newOrder.quantity, bestBuy.quantity);
                System.out.printf("Trade executed: %d %s at %.2f%n", quantity, newOrder.ticker, bestBuy.price);

                newOrder.quantity -= quantity;
                bestBuy.quantity -= quantity;

                if (bestBuy.quantity == 0) {
                    orderBook.buyHead = bestBuy.next;
                }
            }
            if (newOrder.quantity > 0) {
                orderBook.addOrder(newOrder);
            }
        }
    }
}