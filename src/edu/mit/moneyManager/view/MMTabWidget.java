package edu.mit.moneyManager.view;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import edu.mit.moneyManager.R;

/**
 * This is the home activity.
 * 
 * First time users create a budget or view budgets shared with them.
 * 
 * Returning users can view their budget and view budgets shared with them.
 */
public class MMTabWidget extends TabActivity {
    public static final boolean NEW = true;
    public static String VIEWINGOTHER = "";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Reusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, HomeActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("home").setIndicator("Home")
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, ViewSummaryActivity.class);
        spec = tabHost.newTabSpec("view").setIndicator("View")
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ExpenseActivity.class);
        spec = tabHost.newTabSpec("expenses").setIndicator("Expenses",
                          res.getDrawable(R.drawable.ic_launcher))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
        
    }

}
