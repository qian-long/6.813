package edu.mit.moneyManager.view;

import android.app.ActivityGroup;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TabHost;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import edu.mit.moneyManager.R;
import edu.mit.moneyManager.model.DatabaseAdapter;

/**
 * This is the home activity.
 * 
 * First time users create a budget or view budgets shared with them.
 * 
 * Returning users can view their budget and view budgets shared with them.
 */
public class HomeActivity extends ActivityGroup {
    // public static boolean NEW = true;
    public String VIEWINGOTHER = "";
    public static final String CREATED_BUDGET = "created_budget";
    public static final String VIEWING_OTHER_BUDGET = "other_budget";
    private TabHost tabhost;
    private TextView username;
    private TextView welcome;
    private Button create;
    private Button viewBtn;
    private Button expenseBtn;
    private Button shareBtn;
    private DatabaseAdapter mDBAdapter;
    private Double remainingAmt;
    private static SharedPreferences settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        VIEWINGOTHER = "";
        tabhost = ((TabActivity) getParent()).getTabHost();

        settings = getSharedPreferences(ViewSummaryActivity.PREFS_NAME,
                MODE_PRIVATE);
        mDBAdapter = new DatabaseAdapter(this);
        
        username = (TextView) getParent().findViewById(R.id.username);
        welcome = (TextView) findViewById(R.id.welcome);
        create = (Button) findViewById(R.id.create_budget);
        viewBtn = (Button) findViewById(R.id.view_budget);
        expenseBtn = (Button) findViewById(R.id.enter_expense);
        shareBtn = (Button) findViewById(R.id.share_budget);
       
        setLayout();

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tabhost.setCurrentTab(1);
                tabhost.getTabWidget().getChildTabViewAt(2).setEnabled(true);
                TextView username = (TextView) getParent().findViewById(
                        R.id.username);
                username.setText("Your budget");
                
                //set view tab to summary
                Editor editor = settings.edit();
                editor.putInt(ViewContainer.CURRENT_TAB, 0);
                editor.commit();

            }
        });
        expenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabhost.setCurrentTab(2);
            }
        });
        
       
        shareBtn.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Editor editor = settings.edit();
                editor.putInt(ViewContainer.CURRENT_TAB, 3);
                editor.commit();
                tabhost.setCurrentTab(1);
            }
        });
        ExpandableListView budgetsView = (ExpandableListView) findViewById(R.id.sharedBudgets);
        final ExpandableListAdapter adapter = new BudgetExpandableListAdapter();
        budgetsView.setAdapter(adapter);

        budgetsView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                VIEWINGOTHER = (String) adapter.getChild(groupPosition,
                        childPosition);

                username.setText(VIEWINGOTHER + " [Press Home to go back to your budget]");
                
                //disable and blackout expenses tab when viewing other
                tabhost.getTabWidget().getChildTabViewAt(2).setEnabled(false);
                tabhost.getTabWidget().getChildTabViewAt(2).setVisibility(View.INVISIBLE);

                //disable edit and share tabs in view tabhost
                Editor editor = settings.edit();
                editor.putBoolean(ViewContainer.VIEW_EDIT, false);
                editor.putBoolean(ViewContainer.VIEW_SHARE, false);
                editor.putBoolean(VIEWING_OTHER_BUDGET, true);
                editor.commit();
                tabhost.setCurrentTab(1);
                Toast.makeText(v.getContext(), "You are currently viewing " + VIEWINGOTHER, Toast.LENGTH_SHORT).show();
                return true;
            }

        });

        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TabHost tabhost = ((TabActivity) getParent()).getTabHost();

              //disable and blackout expenses tab when first starting
                tabhost.getTabWidget().getChildTabViewAt(2).setEnabled(false);
                tabhost.getTabWidget().getChildTabViewAt(2).setVisibility(View.INVISIBLE);
                //enable view expense tab
                tabhost.getTabWidget().getChildTabViewAt(1).setEnabled(true);
                tabhost.getTabWidget().getChildTabViewAt(1).setVisibility(View.VISIBLE);
                tabhost.setCurrentTab(1);
                

            }

        });

    }

    public void replaceContentView(String id, Intent newIntent) {
        View view = getLocalActivityManager().startActivity(id,
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView();
        this.setContentView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("HomeActivity", "Calling onResume()");
        username.setText("Your Budget");
        

        //enable edit and share
        /*
        Editor editor = settings.edit();
        editor.putBoolean(ViewContainer.VIEW_SUMMARY, true);
        editor.putBoolean(ViewContainer.VIEW_CHART, true);
        editor.putBoolean(ViewContainer.VIEW_EDIT, true);
        editor.putBoolean(ViewContainer.VIEW_SHARE, true);
        editor.putInt(ViewContainer.CURRENT_TAB, 0);
        editor.commit();
        */
        
        if (settings.getBoolean(CREATED_BUDGET, false)) {
            //budget created, enable view and expense tabs
            tabhost.getTabWidget().getChildTabViewAt(1).setEnabled(true);
            tabhost.getTabWidget().getChildTabViewAt(1).setVisibility(View.VISIBLE);
            
            Editor editor = settings.edit();
            editor.putInt(ViewContainer.CURRENT_TAB, 0);
            editor.putBoolean(ViewContainer.VIEW_EDIT, true);
            editor.putBoolean(ViewContainer.VIEW_CHART, true);
            editor.putBoolean(ViewContainer.VIEW_SHARE, true);
            editor.putBoolean(ViewContainer.VIEW_SUMMARY, true);
            editor.putBoolean(VIEWING_OTHER_BUDGET, false);

            editor.commit();
            tabhost.getTabWidget().getChildTabViewAt(2).setEnabled(true);
            tabhost.getTabWidget().getChildTabViewAt(2).setVisibility(View.VISIBLE);
        }
        else {
            //budget not created, disable blackout view and expense tabs
            tabhost.getTabWidget().getChildTabViewAt(1).setEnabled(false);
            tabhost.getTabWidget().getChildTabViewAt(1).setVisibility(View.INVISIBLE);
            
            Editor editor = settings.edit();
            editor.putInt(ViewContainer.CURRENT_TAB, 2);
            editor.putBoolean(ViewContainer.VIEW_EDIT, true);
            editor.putBoolean(ViewContainer.VIEW_CHART, false);
            editor.putBoolean(ViewContainer.VIEW_SHARE, false);
            editor.putBoolean(ViewContainer.VIEW_SUMMARY, false);
            
            editor.commit();
            
            tabhost.getTabWidget().getChildTabViewAt(2).setEnabled(false);
            tabhost.getTabWidget().getChildTabViewAt(2).setVisibility(View.INVISIBLE);
        }
        
        Toast.makeText(this, "You are currently viewing your budget", Toast.LENGTH_SHORT).show();
        setLayout();

    }
    
    /**
     * Calculates and sets remaining total
     * Determines if user has created a budget and enables the appropriate buttons.
     */
    private void setLayout() {
//        if (tabhost.getTabWidget() == null) {
//            Toast.makeText(this, "tabhost.getTabWidget null", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(this, tabhost.getTabWidget().getChildCount(), Toast.LENGTH_SHORT).show();
//
//        }
        mDBAdapter.open();
        remainingAmt = mDBAdapter.getTotalRemaining()
                + (double) settings.getFloat(ViewSummaryActivity.BUDGET_TOTAL,
                        (float) 0.0) - mDBAdapter.getCategoriesTotal();
        if (mDBAdapter.getCategoryNames().size() == 0) {
            remainingAmt = (double) settings.getFloat(
                    ViewSummaryActivity.BUDGET_TOTAL, (float) 0.0);
        }
        mDBAdapter.close();
        if (settings.contains(ViewSummaryActivity.BUDGET_TOTAL)) {
            welcome.setText("You have $" + String.format("%.02f", remainingAmt)
                    + " remaining this month");
            if (remainingAmt < 0) {
                welcome.setTextColor(Color.parseColor("#990000"));
            }
            else {
                welcome.setTextColor(Color.WHITE);

            }
            create.setVisibility(View.GONE);
            expenseBtn.setVisibility(View.VISIBLE);
            viewBtn.setVisibility(View.VISIBLE);
            shareBtn.setVisibility(View.VISIBLE);
        } else {
            welcome.setText("Welcome");
            create.setVisibility(View.VISIBLE);
            expenseBtn.setVisibility(View.GONE);
            viewBtn.setVisibility(View.GONE);
            shareBtn.setVisibility(View.GONE);
        }
        

    }

    class BudgetExpandableListAdapter extends BaseExpandableListAdapter {
        private String[] groups = { "Shared Budgets" };
        private String[][] children = { { "LukeSkywalker's Budget",
                "PrincessLeia's Budget", "R2D2's Budget" } };

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                boolean isLastChild, View convertView, ViewGroup parent) {
            TextView textView = this.getGenericView();
            textView.setText(getChild(groupPosition, childPosition).toString());
            return textView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return children[groupPosition];
        }

        @Override
        public int getGroupCount() {
            return groups.length;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 64);

            TextView textView = new TextView(HomeActivity.this);
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            textView.setPadding(75, 0, 0, 0);
            return textView;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                View convertView, ViewGroup parent) {
            TextView textView = this.getGenericView();
            textView.setText("Budgets Shared With You:");

            return textView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }
}
