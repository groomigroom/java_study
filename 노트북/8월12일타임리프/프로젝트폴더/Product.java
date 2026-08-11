package com.example.demo.domain;
public class Product {
    private Integer id;
    private String name;
    private Integer price;

    public Product(Integer id, String name, Integer price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getter, Setter
    public Integer getId() { return id; }
    public String getName() { return name; }
    public Integer getPrice() { return price; }
}
