package com.example.laundryappcustomer;

public class Requests {
    private Customer User;
    private String Comments;
    private String Status;
    private String Weight="0";
    private String OrderID;
    private String Service;
    private String PaymentStatus;
    private String Price;

    public Requests(Customer user, String comments, String status, String weight,String OrderId,String service,String paymentstatus,String Price) {
        User = user;
        this.Comments = comments;
        this.Status = status;
        Weight = weight;
        this.OrderID = OrderId;
        Service = service;
        PaymentStatus=paymentstatus;
        this.Price=Price;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public Requests() {
    }

    public Customer getUser() {
        return User;
    }

    public void setUser(Customer user) {
        User = user;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        this.Comments = comments;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }
    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
