package br.com.solidtechsolutions.apipagamentos.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


public class Produto {
    private int id;
    private String name;
    private String price;
    private String description;
    private String giftMessage;

    public Produto(int id, String name, String price, String description, String giftMessage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.giftMessage = giftMessage;
    }

    public Produto() {
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", giftMessage='" + giftMessage + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGiftMessage() {
        return giftMessage;
    }

    public void setGiftMessage(String giftMessage) {
        this.giftMessage = giftMessage;
    }
}
