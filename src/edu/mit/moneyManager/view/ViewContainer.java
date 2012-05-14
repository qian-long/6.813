package edu.mit.moneyManager.view;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
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
    public static final String VIEW_SUMMARY = "view_summary";
    public static final String VIEW_CHART = "view_chart";
    public static final String VIEW_EDIT = "view_edit";
    public static final String VIEW_SHARE = "view_share";
    public static final String CURRENT_TAB = "current_tab";
    
    private static SharedPreferences settings;
    private TabHost tabHost;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tabhost);
        settings = getSharedPreferences(ViewSummaryActivity.PREFS_NAME,
                MODE_PRIVATE);
        Resources res = getResources(); // Resource object to get Drawables
        tabHost = getTabHost();  // The activity TabHost
//        tabHost = (TabHost) findViewById(R.id.view_tabhost);
//        tabHost.setup();
        
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
          tabHost.getTabWidget().getChildAt(i).getLayoutParams().height /= 2;
        
    }

    @Override
    protected void onResume() {
        super.onResume();
//        tabHost.setup();
        tabHost.setCurrentTab(settings.getInt(CURRENT_TAB, 0));
        tabHost.getTabWidget().getChildAt(2).setEnabled(settings.getBoolean(VIEW_EDIT, true));
        tabHost.getTabWidget().getChildAt(3).setEnabled(settings.getBoolean(VIEW_SHARE, true));

    }
}
