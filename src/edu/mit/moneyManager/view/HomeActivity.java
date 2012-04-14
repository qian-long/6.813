package edu.mit.moneyManager.view;

import android.app.Activity;
import android.os.Bundle;
import edu.mit.moneyManager.R;

/**
 * This is the home activity.
 * 
 * First time users create a budget.
 * 
 * Returning users can view their budget and view budgest shared with them.
 */
public class HomeActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }
}
