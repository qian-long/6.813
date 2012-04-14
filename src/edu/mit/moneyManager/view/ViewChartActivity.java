package edu.mit.moneyManager.view;

import android.app.Activity;
import android.os.Bundle;
import edu.mit.moneyManager.R;

/**
 * Screen that shows chart breakdown of expenses.
 * 
 */
public class ViewChartActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_chart);
    }
}
