package edu.mit.moneyManager.view;

import edu.mit.moneyManager.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This activity is where the user registers.
 *
 */
public class RegisterActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        
        Button buttonRegister = (Button) findViewById(R.id.register_button);
        EditText username = (EditText) findViewById(R.id.register_username);
        username.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        
        TextView switchToLogin = (TextView) findViewById(R.id.login_help);
        SpannableString contentUnderline = new SpannableString("Already have an account? Login!");  
        contentUnderline.setSpan(new UnderlineSpan(), 0, contentUnderline.length(), 0);  
        switchToLogin.setText(contentUnderline);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(v.getContext(), MMTabWidget.class);
                startActivity(intent);
                
            }
        });
        switchToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
            
        });
        
    }
}
