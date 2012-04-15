package edu.mit.moneyManager.view;

import edu.mit.moneyManager.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * This activity is where the user logs in.
 * 
 * Login
 *
 */
public class LoginActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        Button buttonLogin = (Button) findViewById(R.id.login_button);
        EditText username = (EditText) findViewById(R.id.login_username);
        username.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        TextView switchToRegister = (TextView) findViewById(R.id.register_help);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intent);
                
            }
        });
        
        switchToRegister.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(intent);
                
            }
            
        });
        
    }
}
