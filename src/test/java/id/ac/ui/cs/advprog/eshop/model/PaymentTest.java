package id.ac.ui.cs.advprog.eshop.model;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PaymentTest {
    private Map<String, String> paymentData;
    private Order order;
    private List<Product> products;

    @BeforeEach
    void setup() {
        this.paymentData = new HashMap<>();

        products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("e45d7d21-fd29-4533-a569-abbe0819579a");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        Product product2 = new Product();
        product2.setProductId("8a76b99c-a0b3-46d2-a688-4c1831b21119");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        products.add(product2);

        order= new Order(
                "dbd4aff4-9a7f-4603-92c2-eaf529271cc9",
                products,
                1708560000L,
                "Safira Sudrajat"
        );
    }

    void loadCashOnDeliveryPaymentData() {
        paymentData.put("address", "Depok");
        paymentData.put("deliveryFee", "10000");
    }

    void loadVoucherCodePaymentData() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }


    @Test
    void testCreatePaymentWithNoOrder() {
        loadCashOnDeliveryPaymentData();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.COD.getValue(), null, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithNoPaymentMethod() {
        loadCashOnDeliveryPaymentData();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", null, order, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithEmptyPaymentData() {
        paymentData.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.COD.getValue(), order, paymentData);
        });
    }

    @Test
    void testCreatePaymentWithInvalidPaymentMethod() {
        loadCashOnDeliveryPaymentData();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", "MEOW", order, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithDefaultStatus() {
        loadCashOnDeliveryPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.COD.getValue(), order, paymentData);
        assertSame(order, payment.getOrder());
        assertEquals("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", payment.getId());
        assertEquals(PaymentMethod.COD.getValue(), payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithSuccessStatus() {
        loadCashOnDeliveryPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.COD.getValue(), order, paymentData, PaymentStatus.SUCCESS.getValue());
        assertSame(order, payment.getOrder());
        assertEquals("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", payment.getId());
        assertEquals(PaymentMethod.COD.getValue(), payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithInvalidStatus() {
        loadCashOnDeliveryPaymentData();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.COD.getValue(), order, paymentData, "MEOW");
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithRejectedStatus() {
        loadCashOnDeliveryPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.COD.getValue(), order, paymentData, PaymentStatus.REJECTED.getValue());
        assertSame(order, payment.getOrder());
        assertEquals("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", payment.getId());
        assertEquals(PaymentMethod.COD.getValue(), payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetPaymentStatusToRejected() {
        loadCashOnDeliveryPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.COD.getValue(), order, paymentData);
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testSetPaymentStatusToInvalidStatus() {
        loadCashOnDeliveryPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.COD.getValue(), order, paymentData);
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("MEOW");
        });
        paymentData.clear();
    }

    @Test
    void testSetPaymentStatusToSuccess() {
        loadCashOnDeliveryPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.COD.getValue(), order, paymentData);
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testSetPaymentStatusToWaitingConfirmation() {
        loadCashOnDeliveryPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.COD.getValue(), order, paymentData);
        payment.setStatus(PaymentStatus.WAITING_PAYMENT.getValue());
        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithVoucherButPaymentDataIncorrect() {
        loadCashOnDeliveryPaymentData();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.VOUCHER.getValue(), order, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithCashOnDeliveryButPaymentDataIncorrect() {
        loadVoucherCodePaymentData();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.COD.getValue(), order, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithCashOnDeliveryButPaymentDataHasNoAddress() {
        loadCashOnDeliveryPaymentData();
        paymentData.remove("Address");
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.COD.getValue(), order, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithCashOnDeliveryButPaymentDataHasNoDeliveryFee() {
        loadCashOnDeliveryPaymentData();
        paymentData.remove("deliveryFee");
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.COD.getValue(), order, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithVoucherButPaymentDataHasNoVoucherCode() {
        loadVoucherCodePaymentData();
        paymentData.remove("voucherCode");
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.VOUCHER.getValue(), order, paymentData);
        });
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithVoucherSuccess() {
        loadVoucherCodePaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.VOUCHER.getValue(), order, paymentData);
        assertSame(order, payment.getOrder());
        assertEquals("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", payment.getId());
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        paymentData.clear();
    }

    @Test
    void testCreatePaymentWithCashOnDeliverySuccess() {
        loadCashOnDeliveryPaymentData();
        Payment payment = new Payment("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", PaymentMethod.COD.getValue(), order, paymentData);
        assertSame(order, payment.getOrder());
        assertEquals("a3e3e3e3-9a7f-4603-92c2-eaf529271cc9", payment.getId());
        assertEquals(PaymentMethod.COD.getValue(), payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.WAITING_PAYMENT.getValue(), payment.getStatus());
        paymentData.clear();
    }
}