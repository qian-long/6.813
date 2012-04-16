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
public class ViewContainer extends TabActivity {
    public static final boolean NEW = true;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tabhost);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Reusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, ViewSummaryActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("summary").setIndicator("Summary")
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, ViewChartActivity.class);
        spec = tabHost.newTabSpec("chart").setIndicator("Chart")
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ViewEditBudgetActivity.class);
        spec = tabHost.newTabSpec("edit").setIndicator("Edit")
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, ViewShareActivity.class);
        spec = tabHost.newTabSpec("share").setIndicator("Share")
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
        
        int iCnt = tabHost.getTabWidget().getChildCount();
        for(int i=0; i<iCnt; i++)
          tabHost.getTabWidget().getChildAt(i).getLayoutParams().height /= 2;  // Or the size desired
        
    }

}
