package com.Diego.model;

public class Product extends BaseEntity {

    private String name;
    private double price;
    private String category;
    private int stock;

    public Product() {
        super();
    }

    public Product(int id, String name, double price, String category, int stock) {
        super(id);
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return super.toString() + " | " + name + " | " + price + "€ | Stock: " + stock;
    }
}