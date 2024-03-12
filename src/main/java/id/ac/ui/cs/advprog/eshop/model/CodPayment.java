package id.ac.ui.cs.advprog.eshop.model;
import lombok.Getter;
import java.util.Map;

@Getter
public class CodPayment extends Payment {
    public CodPayment(String id, String method, Order order, Map<String, String> paymentData, String status) {
        super(id, method, order, paymentData, status);
    }

    public CodPayment(String id, String method, Order order, Map<String, String> paymentData) {
        super(id, method, order, paymentData);
    }

    @Override
    public void setPaymentData(Map<String, String> paymentData) {
        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException("Payment data cannot be empty");
        }

        if (paymentData.get("Address") == null || paymentData.get("Address").isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }

        if (paymentData.get("deliveryFee") == null || paymentData.get("deliveryFee").isEmpty()) {
            throw new IllegalArgumentException("Delivery fee cannot be empty");
        }

        this.paymentData = paymentData;
    }
}