package edu.mit.moneyManager.view;

import java.util.ArrayList;
import java.util.List;

import edu.mit.moneyManager.R;
import edu.mit.moneyManager.listUtils.CategoryItemEntry;
import edu.mit.moneyManager.listUtils.SummaryCategoryListAdapter;
import edu.mit.moneyManager.model.Category;
import edu.mit.moneyManager.model.DatabaseAdapter;
import android.app.Activity;
import android.app.ListActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewSummaryActivity extends ListActivity {
    public static final String TAG = "VIEW SUMMARY ACTIVITY";
    public static final String PREFS_NAME = "preferences";
    public static final String BUDGET_TOTAL = "total";
    public static final String BUDGET_REMAINING = "remaining";
    private DatabaseAdapter mDBAdapter;
    private TextView total;
    private TextView remaining;
    private static SharedPreferences settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_summary);
        mDBAdapter = new DatabaseAdapter(this);
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        total = (TextView) findViewById(R.id.total_monthly_budget_textview);
        remaining = (TextView) findViewById(R.id.remaining_monthly_budget_textview);
        total.setText(new Float(settings
                .getFloat(BUDGET_TOTAL, (float) 1000.00)).toString());
        remaining.setText(new Float(settings.getFloat(BUDGET_REMAINING,
                (float) 1000.00)).toString());
        // setting list adapter
        mDBAdapter.open();
        List<Category> categories = mDBAdapter.getCategories();
        mDBAdapter.close();
        SummaryCategoryListAdapter adapter = new SummaryCategoryListAdapter(
                this, (ArrayList<Category>) categories,
                ((TabActivity) getParent()).getTabHost());
        setListAdapter(adapter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        total.setText(new Float(settings
                .getFloat(BUDGET_TOTAL, (float) 1000.00)).toString());
        remaining.setText(new Float(settings.getFloat(BUDGET_REMAINING,
                (float) 1000.00)).toString());
        mDBAdapter.open();
        SummaryCategoryListAdapter adapter = new SummaryCategoryListAdapter(
                this, (ArrayList<Category>) mDBAdapter.getCategories(),
                ((TabActivity) getParent()).getTabHost());
        mDBAdapter.close();

        setListAdapter(adapter);
    }
}
