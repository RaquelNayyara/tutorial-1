package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.CodPayment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.VoucherPayment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Product> products;
    List<Payment> payments;
    Order order;

    @BeforeEach
    void setup() {
        paymentRepository = new PaymentRepository();

        payments = new ArrayList<>();
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

        order = new Order("dbd4aff4-9a7f-4603-92c2-eaf529271cc9",
                products, 1708560000L, "Safira Sudrajat");

        Payment payment1 = new Payment(
                "a0f81308-9911-40c5-8da4-fa3194485aa1",
                "",
                order,
                null
        ),
                payment2 = new Payment(
                        "b0f81308-9911-40c5-8da4-fa3194485aa1",
                        "",
                        order,
                        null
                );
        payments.add(payment1);
        payments.add(payment2);

        Map<String, String> voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment voucherPayment = new VoucherPayment(
                "c0f81308-9911-40c5-8da4-fa3194485aa1",
                PaymentMethod.VOUCHER.getValue(),
                order,
                voucherPaymentData
        );

        Map<String, String> codPaymentData = new HashMap<>();
        codPaymentData.put("Address", "Depok");
        codPaymentData.put("deliveryFee", "10000");
        Payment codPayment = new CodPayment(
                "d0f81308-9911-40c5-8da4-fa3194485aa1",
                PaymentMethod.COD.getValue(),
                order,
                codPaymentData
        );

        payments.add(voucherPayment);
        payments.add(codPayment);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(1);
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getOrder(), findResult.getOrder());
        assertSame(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
    }

    @Test
    void testSaveCreateVoucher() {
        Payment payment = payments.get(2);
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payments.get(2).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getOrder(), findResult.getOrder());
        assertSame(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());
    }

    @Test
    void testSaveCreateCashOnDelivery() {
        Payment payment = payments.get(3);
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payments.get(3).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getOrder(), findResult.getOrder());
        assertSame(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
        assertEquals(PaymentMethod.COD.getValue(), payment.getMethod());
    }

    @Test
    void testSaveCreatePaymentDuplicatedId(){
        Payment payment = payments.get(1);
        paymentRepository.save(payment);
        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.save(payment);
        });
    }

    @Test
    void testSaveCreateCashOnDeliveryPaymentDuplicatedId() {
        Payment payment = payments.get(3);
        paymentRepository.save(payment);
        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.save(payment);
        });
    }

    @Test
    void testSaveCreateVoucherPaymentDuplicatedId() {
        Payment payment = payments.get(2);
        paymentRepository.save(payment);
        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.save(payment);
        });
    }

    @Test
    void testFindByIdIfIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payments.get(1).getId(), findResult.getId());
        assertSame(payments.get(1).getPaymentData(), findResult.getPaymentData());
        assertEquals(payments.get(1).getMethod(), findResult.getMethod());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
        assertEquals(payments.get(1).getOrder(), findResult.getOrder());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        assertNull(paymentRepository.findById("zczc"));
    }

    @Test
    void testGetAllPayments() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> allPayments = paymentRepository.getAllPayments();
        assertEquals(4, allPayments.size());
    }
}