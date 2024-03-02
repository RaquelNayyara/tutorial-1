package id.ac.ui.cs.advprog.eshop.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

class CodPaymentTest {
    Map<String, String> paymentData;
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

        paymentData.put("Address", "Depok");
        paymentData.put("deliveryFee", "10000");
    }

    @Test
    void testCreateCodPaymentPendingStatus() {
        Payment payment = new CodPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData
        );
        assertSame(order, payment.getOrder());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals("e45d7d21-fd29-4533-a569-abbe0819579a", payment.getId());
        assertEquals(PaymentMethod.COD.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
    }

    @Test
    void testCreateCodPaymentSuccessStatus() {
        Payment payment = new CodPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData,
                PaymentStatus.SUCCESS.getValue()
        );
        assertSame(order, payment.getOrder());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals("e45d7d21-fd29-4533-a569-abbe0819579a", payment.getId());
        assertEquals(PaymentMethod.COD.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreateCodPaymentRejectedStatus() {
        Payment payment = new CodPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData,
                PaymentStatus.REJECTED.getValue()
        );
        assertSame(order, payment.getOrder());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals("e45d7d21-fd29-4533-a569-abbe0819579a", payment.getId());
        assertEquals(PaymentMethod.COD.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreateCodPaymentInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CodPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData,
                    "MEOW"
            );
        });
    }

    @Test
    void testCreateCodPaymentNullStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CodPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData,
                    null
            );
        });
    }

    @Test
    void testSetCodPaymentStatStatusToSuccess() {
        Payment payment = new CodPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData
        );
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetCodPaymentStatStatusToRejected() {
        Payment payment = new CodPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData
        );
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetCodPaymentStatStatusToPending() {
        Payment payment = new CodPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData
        );
        payment.setStatus(PaymentStatus.PENDING.getValue());
        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
    }

    @Test
    void testSetCodPaymentStatStatusToInvalid() {
        Payment payment = new CodPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData
        );
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("MEOW");
        });
    }

    @Test
    void testSetCodPaymentStatStatusToNull() {
        Payment payment = new CodPayment(
                "e45d7d21-fd29-4533-a569-abbe0819579a",
                PaymentMethod.COD.getValue(),
                order,
                paymentData
        );
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus(null);
        });
    }

    @Test
    void testCreateCodPaymentWithNullOrder() {
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CodPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    null,
                    paymentData
            );
        });
    }

    @Test
    void testCreateCodPaymentWithEmptyPaymentData() {
        paymentData.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CodPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData
            );
        });
    }

    @Test
    void testCreateCodPaymentWithNullPaymentData() {
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CodPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    null
            );
        });
    }

    @Test
    void testCreateCodPaymentInvalidAddress() {
        paymentData.put("Address", "");
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CodPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData
            );
        });
    }

    @Test
    void testCreateCodPaymentInvalidDeliveryFee() {
        paymentData.put("deliveryFee", "");
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CodPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData
            );
        });
    }

    @Test
    void testCreateCodPaymentWithNullAddress() {
        paymentData.put("Address", null);
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CodPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData
            );
        });
    }

    @Test
    void testCreateCodPaymentWithNullDeliveryFee() {
        paymentData.put("deliveryFee", null);
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CodPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData
            );
        });
    }

    @Test
    void testCreateCodPaymentWithInvalidAddressAndDeliveryFee() {
        paymentData.put("Address", "");
        paymentData.put("deliveryFee", "");
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CodPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData
            );
        });
    }

    @Test
    void testCreateCodPaymentWithNullAddressAndDeliveryFee() {
        paymentData.put("Address", null);
        paymentData.put("deliveryFee", null);
        assertThrows(IllegalArgumentException.class, () -> {
            @SuppressWarnings("unused")
            Payment payment = new CodPayment(
                    "e45d7d21-fd29-4533-a569-abbe0819579a",
                    PaymentMethod.COD.getValue(),
                    order,
                    paymentData
            );
        });
    }
}