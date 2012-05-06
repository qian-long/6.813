package edu.mit.moneyManager.model;

public class Expense {

    private double amount;
    private String date;
    private String category;
    private int id = 0;
    
    public Expense(double amount, String date, String category) {
        this.amount = amount;
        this.date = date;
        this.category = category;
    }
    
    public Expense(double amount, String date, String category, int id) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.id = id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Double getAmount() {
        return new Double(amount);
    }
    
    public String getDate() {
        return new String(date);
    }
    
    public String getCategory() {
        return new String(category);
    }
    
    public int getId() {
        return id;
    }
}
