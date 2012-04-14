package edu.mit.moneyManager.view;

import edu.mit.moneyManager.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        
        Button buttonLogin = (Button) findViewById(R.id.login_button);
        
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intent);
                
            }
        });
    }
}
