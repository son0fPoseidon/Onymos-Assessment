import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;


public class StockTradingSimulation {
    private static final Random random = new Random();

    public static void main(String[] args) {
        TradingEngine engine = new TradingEngine();
        String[] tickers = new String[1024];
        for (int i = 0; i < 1024; i++) {
            tickers[i] = "TICKER" + (i + 1);
        }

        // Create a thread pool to run orders concurrently
        ExecutorService executor = Executors.newFixedThreadPool(10); // 10 concurrent threads

        for (int i = 0; i < 100; i++) { // Simulate 100 random transactions
            executor.submit(() -> {
                Order.Type type = random.nextBoolean() ? Order.Type.BUY : Order.Type.SELL;
                String ticker = tickers[random.nextInt(tickers.length)];
                int quantity = random.nextInt(100) + 1;
                double price = 10 + (500 - 10) * random.nextDouble();
                engine.addOrder(type, ticker, quantity, Math.round(price * 100.0) / 100.0);
            });
        }

        executor.shutdown();
    }
}
