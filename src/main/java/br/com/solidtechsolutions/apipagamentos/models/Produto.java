package br.com.solidtechsolutions.apipagamentos.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Data
@ToString
public class Produto {
    private int id;
    private String name;
    private String price;
    private String description;
}
