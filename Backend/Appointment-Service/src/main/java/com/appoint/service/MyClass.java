package com.appoint.service;

public class MyClass {
    // Declare and initialize the private String field
    private String s = "";

    // Method to receive and process a string
    public void receiveString() {
        s = "Received string";  // Assign a new value to the string field
        System.out.println("Received string: " + s);  // Print the received string
    }

    // Method to receive and return the string without modifying it
    public String receiveString1() {
        System.out.println("Received string: " + s);  // Print the received string
        return s;  // Return the value of the string field
    }

    // Main method for testing
    public static void main(String[] args) {
        MyClass obj = new MyClass();
        obj.receiveString();  // Call the receiveString method
        obj.receiveString1();
    }
}
