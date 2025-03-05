class Order {
    enum Type { BUY, SELL }
    Type type;
    String ticker;
    int quantity;
    double price;
    int orderId;
    Order next;

    public Order(Type type, String ticker, int quantity, double price, int orderId) {
        this.type = type;
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
        this.orderId = orderId;
        this.next = null;
    }

    @Override
    public String toString() {
        return String.format("%s %d %s @ %.2f (ID: %d)", type, quantity, ticker, price, orderId);
    }
}