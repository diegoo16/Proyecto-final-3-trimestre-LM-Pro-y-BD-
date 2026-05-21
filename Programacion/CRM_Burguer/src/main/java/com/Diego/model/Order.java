package com.Diego.model;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private LocalDateTime date;
    private int customerId;
    private int employeeId;
    private double total;

    public Order(){}

    public Order(int id, LocalDateTime date,
                 int customerId,int employeeId,double total){
        this.id=id;
        this.date=date;
        this.customerId=customerId;
        this.employeeId=employeeId;
        this.total=total;
    }

    public int getId(){ return id; }
    public void setId(int id){ this.id=id; }

    public LocalDateTime getDate(){ return date; }
    public void setDate(LocalDateTime date){ this.date=date; }

    public int getCustomerId(){ return customerId; }
    public void setCustomerId(int customerId){ this.customerId=customerId; }

    public int getEmployeeId(){ return employeeId; }
    public void setEmployeeId(int employeeId){ this.employeeId=employeeId; }

    public double getTotal(){ return total; }
    public void setTotal(double total){ this.total=total; }
}