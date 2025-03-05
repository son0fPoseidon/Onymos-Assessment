class OrderBook {
    volatile Order buyHead = null;
    volatile Order sellHead = null;

    public void addOrder(Order newOrder) {
        if (newOrder.type == Order.Type.BUY) {
            addBuyOrder(newOrder);
        } else {
            addSellOrder(newOrder);
        }
    }

    private void addBuyOrder(Order newOrder) {
        Order prev = null, current = buyHead;
        while (current != null && current.price > newOrder.price) {
            prev = current;
            current = current.next;
        }
        if (prev == null) {
            newOrder.next = buyHead;
            buyHead = newOrder;
        } else {
            prev.next = newOrder;
            newOrder.next = current;
        }
    }

    private void addSellOrder(Order newOrder) {
        Order prev = null, current = sellHead;
        while (current != null && current.price < newOrder.price) {
            prev = current;
            current = current.next;
        }
        if (prev == null) {
            newOrder.next = sellHead;
            sellHead = newOrder;
        } else {
            prev.next = newOrder;
            newOrder.next = current;
        }
    }
}