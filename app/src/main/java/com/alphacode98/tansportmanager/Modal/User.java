package com.alphacode98.tansportmanager.Modal;

public class User {
    private String name;
    private String mobile;
    private String email;
    private String address;
    private String nic;
    private String type;
    private float amount;

    public User() {
    }

    public User(String name, String mobile, String email, String address, String nic, String type) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.nic = nic;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
