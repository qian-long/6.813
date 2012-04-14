package edu.mit.moneyManager.view;

import android.app.Activity;
import android.os.Bundle;
import edu.mit.moneyManager.R;

/**
 * This is the expenses activity.
 * 
 * Users enter in their expenses here.
 */
public class ExpenseActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses);
    }
}
