package com.example.demo.domain;
public class Product {
    private Integer id;
    private String name;
    private int price;
    private String description;

    // 생성자
    public Product(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    // Getter & Setter (타임리프에서 데이터를 읽기 위해 필수)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
