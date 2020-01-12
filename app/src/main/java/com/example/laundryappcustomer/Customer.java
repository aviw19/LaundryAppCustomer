package com.example.laundryappcustomer;

import java.util.ArrayList;

public class Customer {
    private String Name;
    private String CollegeId;
    private String emailId;
    private String hostelNo;
    private String roomNo;
    private String phoneno;
    private String orderCount;
    private String firebaseToken;
    private ArrayList<Order> OrderList;

    public Customer() {
    }

    Customer(String name, String collegeId, String emailId, String hostelno, String roomNo, String phoneno, String OrderCount,ArrayList<Order> orderList,String firebaseToken,String loggedIn) {
        Name = name;
        CollegeId = collegeId;
        this.emailId = emailId;
        this.hostelNo = hostelno;
        this.roomNo = roomNo;
        this.phoneno = phoneno;
        this.orderCount = OrderCount;
        this.OrderList = orderList;
        this.firebaseToken=firebaseToken;
    }

    public ArrayList<Order> getOrderList() {
        return OrderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        OrderList = orderList;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getHostelNo() {
        return hostelNo;
    }

    public void setHostelNo(String hostelNo) {
        this.hostelNo = hostelNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCollegeId() {
        return CollegeId;
    }

    public void setCollegeId(String collegeId) {
        CollegeId = collegeId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
