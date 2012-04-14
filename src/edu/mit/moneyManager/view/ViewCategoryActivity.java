package edu.mit.moneyManager.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import edu.mit.moneyManager.R;

/**
 * Screen that shows details of a Category
 *
 */
public class ViewCategoryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_category);
        
        //main action bar
        Button home = (Button) findViewById(R.id.home_action);
        Button view = (Button) findViewById(R.id.view_action);
        Button expenses = (Button) findViewById(R.id.expense_action);
        
        home.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        
        view.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewSummaryActivity.class);
                startActivity(intent);
            }
        });
        
        expenses.setOnClickListener(new View.OnClickListener() {
       
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ExpenseActivity.class);
                startActivity(intent);
            }
        });
        
        //view action bar
        Button summary = (Button) findViewById(R.id.summary_action);
        Button chart = (Button) findViewById(R.id.chart_action);
        Button edit = (Button) findViewById(R.id.edit_action);
        Button share = (Button) findViewById(R.id.share_action);
        
        summary.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewSummaryActivity.class);
                startActivity(intent);
            }
        });
        
        chart.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewChartActivity.class);
                startActivity(intent);
            }
        });
        
        edit.setOnClickListener(new View.OnClickListener() {
       
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewEditBudgetActivity.class);
                startActivity(intent);
            }
        });
        
        share.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewShareActivity.class);
                startActivity(intent);
            }
        });
    }
}
