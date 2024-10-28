package br.com.solidtechsolutions.apipagamentos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
public class Produto {
    private int id;
    private String name;
    private String price;
    private String description;
    private String giftMessage;
    private String pricePerQuota;
    private String quotaQuantity;
    private String totalPrice;
    private Long idReview;

}
