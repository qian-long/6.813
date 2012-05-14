package edu.mit.moneyManager.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.mit.moneyManager.R;

/**
 * Screen that shows chart breakdown of expenses.
 */
public class ViewChartActivity extends Activity {
    private static final String HEADER_TEXT = "Total Monthly Budget: $";
    private static SharedPreferences settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_chart);
        settings = getSharedPreferences(ViewSummaryActivity.PREFS_NAME, MODE_PRIVATE);
        TextView total = (TextView) findViewById(R.id.total_budget);
        total.setText(HEADER_TEXT + settings.getFloat(ViewSummaryActivity.BUDGET_TOTAL, 0));
        
    }
}
