package edu.mit.moneyManager.view;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import edu.mit.moneyManager.R;

/**
 * This is the home activity.
 * 
 * First time users create a budget.
 * 
 * Returning users can view their budget and view budgets shared with them.
 */
public class HomeActivity extends TabActivity {
    public static final boolean NEW = true;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, ExpenseActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("artists").setIndicator("Artists")
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, ViewCategoryActivity.class);
        spec = tabHost.newTabSpec("albums").setIndicator("Albums")
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ViewChartActivity.class);
        spec = tabHost.newTabSpec("songs").setIndicator("Songs",
                          res.getDrawable(R.drawable.ic_launcher))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
        
        /*Button home = (Button) findViewById(R.id.home_action);
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
        });*/
                
    }
}
