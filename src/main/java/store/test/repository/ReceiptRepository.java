package store.test.repository;

import java.util.List;
import store.test.order.Order;
import store.model.payment.Receipt;

public class ReceiptRepository {

    private final Receipt receipt;

    public ReceiptRepository() {
        this.receipt = new Receipt();
    }

    public void setMembership(boolean isMembership) {
        receipt.setMembershipDiscounted(isMembership);
    }

    public List<Order> findAll() {
        return receipt.getPurchaseHistory();
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void save(Order order) {
        receipt.addOrder(order);
    }

    public void clear() {
        receipt.clear();
    }
}