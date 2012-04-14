package edu.mit.moneyManager.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import edu.mit.moneyManager.R;

/**
 * This is the home activity.
 * 
 * First time users create a budget.
 * 
 * Returning users can view their budget and view budgets shared with them.
 */
public class HomeActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        Button home = (Button) findViewById(R.id.home_action);
        Button view = (Button) findViewById(R.id.view_action);
        Button expenses = (Button) findViewById(R.id.expense_action);
        
        home.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        
        view.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewSummaryActivity.class);
                startActivity(intent);
            }
        });
        
        expenses.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ExpenseActivity.class);
                startActivity(intent);
            }
        });
                
    }
}
