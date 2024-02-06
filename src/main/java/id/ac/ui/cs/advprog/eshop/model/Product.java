package id.ac.ui.cs.advprog.eshop.model;

import java.util.UUID;
import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class Product {
    private String productId;
    private String productName;
    private int productQuantity;

    public Product() {
        this.productId = UUID.randomUUID().toString();
    }
}

