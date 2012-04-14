package edu.mit.moneyManager.listUtils;

/**
 * Model for each category in the summary view. 
 * 
 * Includes category name, total amount, and remaining amount
 */
public class CategoryItemEntry {
    private String name;
    private int total;
    private int remaining;
    
    public CategoryItemEntry(String name, int total, int remaining) {
        this.name = name;
        this.total = total;
        this.remaining = remaining;
    }
    
    public String getName() {
        return new String(name);
    }
    
    public String getTotalAmount() {
        return new Integer(total).toString();
    }
    
    public String getRemainingAmount() {
        return new Integer(remaining).toString();
    }
}
