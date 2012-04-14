package edu.mit.moneyManager.view;

import edu.mit.moneyManager.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * This activity is where the user logs in.
 * 
 * Registration or Login
 *
 */
public class LoginActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
}
