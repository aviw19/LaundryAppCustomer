package com.example.laundryappcustomer;

public class Customer {
    public Customer(String name, String collegeId, String emailid, String hostelno, String roomno, Order ob) {
        Name = name;
        CollegeId = collegeId;
        this.emailid = emailid;
        this.hostelno = hostelno;
        this.roomno = roomno;
        this.ob = ob;
    }

    public Customer()
    {
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

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getHostelno() {
        return hostelno;
    }

    public void setHostelno(String hostelno) {
        this.hostelno = hostelno;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public Order getOb() {
        return ob;
    }

    public void setOb(Order ob) {
        this.ob = ob;
    }

    private String Name;
    private String CollegeId;
    private String emailid;
    private String hostelno;
    private String roomno;
    Order ob=new Order();

}
