package com.Diego.model;

public class OrderDetail extends BaseEntity {

    private int orderId;
    private int productId;
    private int quantity;
    private double subtotal;

    public OrderDetail() {
        super();
    }

    public OrderDetail(int id, int orderId, int productId, int quantity, double subtotal) {
        super(id);
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return super.toString() + " | Pedido: " + orderId + " | Producto: " + productId + " | Cant: " + quantity;
    }
}