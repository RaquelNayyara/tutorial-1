package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.CodPayment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.VoucherPayment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @Spy
    @InjectMocks
    PaymentServiceImpl paymentService;
    @Mock
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setup() {
        payments = new ArrayList<>();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("e45d7d21-fd29-4533-a569-abbe0819579a");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        Order order = new Order("dbd4aff4-9a7f-4603-92c2-eaf529271cc9",
                products, 1708560000L, "Safira Sudrajat");

        Map<String, String> voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP00000000AAA");
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
                "c0f81308-9911-40c5-8da4-fa3194485aa1",
                PaymentMethod.COD.getValue(),
                order,
                codPaymentData
        );

        payments.add(voucherPayment);
        payments.add(codPayment);
    }

    @Test
    void testAddCashOnDeliveryPayment() {
        Payment codPayment = payments.get(1);
        doReturn(codPayment).when(paymentRepository).save(any(Payment.class));
        codPayment = paymentService.addPayment(
                codPayment.getOrder(),
                codPayment.getMethod(),
                codPayment.getPaymentData()
        );

        doReturn(codPayment).when(paymentRepository).findById(codPayment.getId());
        Payment result = paymentService.getPayment(codPayment.getId());

        assertEquals(codPayment.getId(), result.getId());
        assertEquals(codPayment.getMethod(), result.getMethod());
        assertEquals(codPayment.getOrder(), result.getOrder());
        assertEquals(codPayment.getPaymentData(), result.getPaymentData());
        assertEquals(PaymentMethod.COD.getValue(), result.getMethod());
        verify(paymentService, times(1)).addPayment(any(
                Order.class), any(String.class), any(Map.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testAddVoucherPayment() {
        Payment voucherPayment = payments.get(0);
        doReturn(voucherPayment).when(paymentRepository).save(any(Payment.class));
        voucherPayment = paymentService.addPayment(
                voucherPayment.getOrder(),
                voucherPayment.getMethod(),
                voucherPayment.getPaymentData()
        );

        doReturn(voucherPayment).when(paymentRepository).findById(voucherPayment.getId());
        Payment result = paymentService.getPayment(voucherPayment.getId());

        assertEquals(voucherPayment.getId(), result.getId());
        assertEquals(voucherPayment.getMethod(), result.getMethod());
        assertEquals(voucherPayment.getOrder(), result.getOrder());
        assertEquals(voucherPayment.getPaymentData(), result.getPaymentData());
        assertEquals(PaymentMethod.VOUCHER.getValue(), result.getMethod());
        verify(paymentService, times(1)).addPayment(any(
                Order.class), any(String.class), any(Map.class));
    }

    @Test
    void testUpdateStatusCashOnDeliveryPayment() {
        Payment codPayment = payments.get(1);
        doReturn(codPayment).when(paymentRepository).save(any(Payment.class));
        codPayment = paymentService.addPayment(
                codPayment.getOrder(),
                codPayment.getMethod(),
                codPayment.getPaymentData()
        );

        doReturn(codPayment).when(paymentRepository).findById(codPayment.getId());
        Payment result = paymentService.getPayment(codPayment.getId());

        assertEquals(result.getStatus(), PaymentStatus.PENDING.getValue());
        paymentService.setStatus(result, PaymentStatus.SUCCESS.getValue());
        assertEquals(result.getStatus(), PaymentStatus.SUCCESS.getValue());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getOrder().getStatus());
        assertEquals(PaymentMethod.COD.getValue(), result.getMethod());

        paymentService.setStatus(result, PaymentStatus.REJECTED.getValue());
        assertEquals(result.getStatus(), PaymentStatus.REJECTED.getValue());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
        assertEquals(PaymentMethod.COD.getValue(), result.getMethod());
    }

    @Test
    void testUpdateStatusVoucherPayment() {
        Payment voucherPayment = payments.get(0);
        doReturn(voucherPayment).when(paymentRepository).save(any(Payment.class));
        voucherPayment = paymentService.addPayment(
                voucherPayment.getOrder(),
                voucherPayment.getMethod(),
                voucherPayment.getPaymentData()
        );

        doReturn(voucherPayment).when(paymentRepository).findById(voucherPayment.getId());
        Payment result = paymentService.getPayment(voucherPayment.getId());

        assertEquals(result.getStatus(), PaymentStatus.PENDING.getValue());
        paymentService.setStatus(result, PaymentStatus.SUCCESS.getValue());
        assertEquals(result.getStatus(), PaymentStatus.SUCCESS.getValue());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getOrder().getStatus());
        assertEquals(PaymentMethod.VOUCHER.getValue(), result.getMethod());

        paymentService.setStatus(result, PaymentStatus.REJECTED.getValue());
        assertEquals(result.getStatus(), PaymentStatus.REJECTED.getValue());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
        assertEquals(PaymentMethod.VOUCHER.getValue(), result.getMethod());
    }

    @Test
    void testUpdateStatusCashOnDeliveryPaymentInvalidStatus() {
        assertEquals(payments.get(1).getStatus(), PaymentStatus.PENDING.getValue());
        assertEquals(PaymentMethod.COD.getValue(), payments.get(1).getMethod());
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payments.get(0), "MEOW");
        });
    }

    @Test
    void testUpdateStatusVoucherPaymentInvalidStatus() {
        assertEquals(payments.get(0).getStatus(), PaymentStatus.PENDING.getValue());
        assertEquals(PaymentMethod.VOUCHER.getValue(), payments.get(0).getMethod());
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payments.get(0), "MEOW");
        });
    }

    @Test
    void testUpdateStatusCashOnDeliveryPaymentUnregisteredPayment() {
        Payment codPayment = payments.get(1);
        doReturn(null).when(paymentRepository).findById(codPayment.getId());
        assertThrows(NoSuchElementException.class, () -> {
            paymentService.setStatus(codPayment, PaymentStatus.SUCCESS.getValue());
        });
    }

    @Test
    void testUpdateStatusVoucherPaymentUnregisteredPayment() {
        Payment voucherPayment = payments.get(0);
        doReturn(null).when(paymentRepository).findById(voucherPayment.getId());
        assertThrows(NoSuchElementException.class, () -> {
            paymentService.setStatus(voucherPayment, PaymentStatus.SUCCESS.getValue());
        });
    }

    @Test
    void testFindByIdIfIdFound() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertEquals(payment.getOrder(), result.getOrder());
        assertEquals(payment.getPaymentData(), result.getPaymentData());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        doReturn(null).when(paymentRepository).findById("zczc");
        assertNull(paymentService.getPayment("zczc"));
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).getAllPayments();
        List<Payment> result = paymentService.getAllPayments();
        assertEquals(payments, result);
    }

    @Test
    void testCreateVoucherPaymentInvalidMethod() {
        Payment voucherPayment = payments.get(0);
        Mockito.lenient().doReturn(voucherPayment).when(paymentRepository).save(any(Payment.class));
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.addPayment(
                    voucherPayment.getOrder(),
                    "MEOW",
                    voucherPayment.getPaymentData()
            );
        });
    }

    @Test
    void testCreateCashOnDeliveryPaymentInvalidMethod() {
        Payment codPayment = payments.get(1);
        Mockito.lenient().doReturn(codPayment).when(paymentRepository).save(any(Payment.class));
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.addPayment(
                    codPayment.getOrder(),
                    "MEOW",
                    codPayment.getPaymentData()
            );
        });
    }
}