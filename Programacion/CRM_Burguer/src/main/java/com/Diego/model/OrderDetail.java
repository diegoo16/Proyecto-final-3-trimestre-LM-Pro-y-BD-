package com.Diego.model;

public class OrderDetail {
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double subtotal;

    public OrderDetail(){}

    public OrderDetail(int id,int orderId,int productId,
                       int quantity,double subtotal){
        this.id=id;
        this.orderId=orderId;
        this.productId=productId;
        this.quantity=quantity;
        this.subtotal=subtotal;
    }

    public int getId(){ return id; }
    public void setId(int id){ this.id=id; }

    public int getOrderId(){ return orderId; }
    public void setOrderId(int orderId){ this.orderId=orderId; }

    public int getProductId(){ return productId; }
    public void setProductId(int productId){ this.productId=productId; }

    public int getQuantity(){ return quantity; }
    public void setQuantity(int quantity){ this.quantity=quantity; }

    public double getSubtotal(){ return subtotal; }
    public void setSubtotal(double subtotal){ this.subtotal=subtotal; }
}