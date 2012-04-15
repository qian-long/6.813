package edu.mit.moneyManager.view;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.TextView;
import edu.mit.moneyManager.R;

/**
 * This is the home activity.
 * 
 * First time users create a budget or view budgets shared with them.
 * 
 * Returning users can view their budget and view budgets shared with them.
 */
public class HomeActivity extends Activity {
    public static boolean NEW = true;
    public static String VIEWINGOTHER = "";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        VIEWINGOTHER="";
        TextView welcome = (TextView) findViewById(R.id.welcome);
        Button create = (Button) findViewById(R.id.create_budget);
        if (!NEW){
            welcome.setText("You have $225 remaining this month");
            create.setText("Enter Expense");
        }
        
        ExpandableListView budgetsView = (ExpandableListView) findViewById(R.id.sharedBudgets);
        final ExpandableListAdapter adapter = new BudgetExpandableListAdapter();
        budgetsView.setAdapter(adapter);
        
        budgetsView.setOnChildClickListener(new OnChildClickListener(){
            
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(v.getContext(), ViewSummaryActivity.class);
                VIEWINGOTHER = (String) adapter.getChild(groupPosition, childPosition);
                startActivity(intent);
                return true;
            }
            
        });
        
        
        create.setOnClickListener(new View.OnClickListener(){
            
            @Override
            public void onClick(View v){
                if (NEW){
//                Intent intent = new Intent(v.getContext(), ViewEditBudgetActivity.class);
//                startActivity(intent);
//                Intent intent = new Intent(v.getContext(), MMTabWidget.class);
//                intent.putExtra("tabIndex", 1);
//                startActivity(intent);
                    TabHost tabhost = ((TabActivity)getParent()).getTabHost();
                    tabhost.setCurrentTab(1);
                }
                else{
//                    Intent intent = new Intent(v.getContext(), ExpenseActivity.class);
//                    Intent intent = new Intent(v.getContext(), MMTabWidget.class);
//                    intent.putExtra("tabIndex", 2);
//                    startActivity(intent);
                    TabHost tabhost = ((TabActivity)getParent()).getTabHost();
                    tabhost.setCurrentTab(1);
                }
            }
            
        });  
        
    }
    
    class BudgetExpandableListAdapter extends BaseExpandableListAdapter{
        private String[] groups = {"Shared Budgets"};
        private String[][] children = {{ "LukeSkywalker's Budget",
            "PrincessLeia's Budget", "R2D2's Budget"}};
        
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
        public boolean isChildSelectable(int groupPosition,
                int childPosition) {
            return true;
        }
        
    }
}
