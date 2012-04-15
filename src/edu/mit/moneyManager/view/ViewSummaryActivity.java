package edu.mit.moneyManager.view;

import java.util.ArrayList;
import java.util.List;

import edu.mit.moneyManager.R;
import edu.mit.moneyManager.listUtils.CategoryItemEntry;
import edu.mit.moneyManager.listUtils.SummaryCategoryListAdapter;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewSummaryActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_summary);
        
      //top actionbar
//        Button home = (Button) findViewById(R.id.home_action);
//        Button view = (Button) findViewById(R.id.view_action);
//        Button expenses = (Button) findViewById(R.id.expense_action);
//        
//        home.setOnClickListener(new View.OnClickListener() {
//            
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), HomeActivity.class);
//                startActivity(intent);
//            }
//        });
//        
//        view.setEnabled(false);
//        view.setOnClickListener(new View.OnClickListener() {
//            
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), ViewSummaryActivity.class);
//                startActivity(intent);
//            }
//        });
        
//        expenses.setOnClickListener(new View.OnClickListener() {
//       
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), ExpenseActivity.class);
//                startActivity(intent);
//            }
//        });
        
      //view action bar
//        Button summary = (Button) findViewById(R.id.summary_action);
//        Button chart = (Button) findViewById(R.id.chart_action);
//        Button edit = (Button) findViewById(R.id.edit_action);
//        Button share = (Button) findViewById(R.id.share_action);
//    
//        summary.setEnabled(false);
//        summary.setOnClickListener(new View.OnClickListener() {
//            
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), ViewSummaryActivity.class);
//                startActivity(intent);
//            }
//        });
        
//        chart.setOnClickListener(new View.OnClickListener() {
//            
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), ViewChartActivity.class);
//                startActivity(intent);
//            }
//        });
//        
//        edit.setOnClickListener(new View.OnClickListener() {
//       
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), ViewEditBudgetActivity.class);
//                startActivity(intent);
//            }
//        });
//        
//        share.setOnClickListener(new View.OnClickListener() {
//            
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), ViewShareActivity.class);
//                startActivity(intent);
//            }
//        });
        
        //setting list adapter
        //samples data
        List<CategoryItemEntry> sample = new ArrayList<CategoryItemEntry>();
        sample.add(new CategoryItemEntry("Food", 500, 500));
        sample.add(new CategoryItemEntry("Books", 500, 500));
        sample.add(new CategoryItemEntry("Clothing", 900,799));
        sample.add(new CategoryItemEntry("Clothing", 900,799));
        sample.add(new CategoryItemEntry("Clothing", 900,799));
        sample.add(new CategoryItemEntry("Clothing", 900,799));
        sample.add(new CategoryItemEntry("Clothing", 900,799));
        sample.add(new CategoryItemEntry("Clothing", 900,799));
        sample.add(new CategoryItemEntry("Clothing", 900,799));
        sample.add(new CategoryItemEntry("Clothing", 900,799));
        sample.add(new CategoryItemEntry("Clothing", 900,799));
        sample.add(new CategoryItemEntry("Clothing", 900,799));

        SummaryCategoryListAdapter adapter = new SummaryCategoryListAdapter(this, (ArrayList<CategoryItemEntry>) sample);
        setListAdapter(adapter);
    }
    
    
}
