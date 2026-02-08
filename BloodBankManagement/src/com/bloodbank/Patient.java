package com.bloodbank;

public class Patient {

    String name;
    int age;
    String bloodGroup;
    String problem;
    String phone; 

    public Patient(String name, int age, String bloodGroup, String problem, String phone) {
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.problem = problem;
        this.phone = phone;
    }

    public void display() {
        System.out.println(name + " | " + age + " | " + bloodGroup + " | " + problem + " | " + phone);
    }
}
