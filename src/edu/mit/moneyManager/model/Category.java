package edu.mit.moneyManager.model;

public class Category {

    private String name;
    private double total;
    private double remaining;
    
    /**
     * 
     * @param name
     * @param total
     */
    public Category(String name, double total, double remaining) {
        this.name = name;
        this.total = total;
        this.remaining = remaining;
    }
    
    public String getName() {
        return new String(name);
    }
    
    public Double getTotal() {
        return new Double(total);
    }
    
    /*
    public Double getRemaining(List<Double> amounts) {
        double sum = 0.0;
        for (Double amount: amounts) {
            sum += amount;
        }    
        return total - sum;
    }
    */
    public Double getRemaining() {
        return new Double(remaining);
    }
}
