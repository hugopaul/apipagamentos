package br.com.solidtechsolutions.apipagamentos.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String quotasPurchased;

    private String quotasTotals;

    private String price;

    @Column(length = 1000)
    private String description;

    @ElementCollection
    private List<String> features;

    private String image;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private List<Review> reviews;
}

