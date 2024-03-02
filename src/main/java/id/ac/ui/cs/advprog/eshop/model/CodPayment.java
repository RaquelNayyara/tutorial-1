package id.ac.ui.cs.advprog.eshop.model;
import lombok.Getter;
import java.util.Map;

@Getter
public class CodPayment extends Payment {
    public CodPayment(String id, String method, Order order, Map<String, String> paymentData, String status) {
    }

    public CodPayment(String id, String method, Order order, Map<String, String> paymentData) {
    }
}