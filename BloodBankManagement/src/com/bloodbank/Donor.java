package com.bloodbank;

public class Donor {

    String name;
    int age;
    String bloodGroup;
    String phone;

    public Donor(String name, int age, String bloodGroup, String phone) {
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
    }

    public void display() {
        System.out.println(name + " | " + age + " | " + bloodGroup + " | " + phone);
    }
}
