package edu.mit.moneyManager.view;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import edu.mit.moneyManager.R;
import edu.mit.moneyManager.listUtils.CategoryExpenseListAdapter;
import edu.mit.moneyManager.listUtils.CategoryItemEntry;
import edu.mit.moneyManager.listUtils.ExpenseItemEntry;
import edu.mit.moneyManager.listUtils.MainExpenseListAdapter;

/**
 * This is the expenses activity.
 * 
 * Users enter in their expenses here.
 */
public class ExpenseActivity extends ListActivity {
    private TabHost tabhost;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses);
        tabhost =  ((TabActivity)getParent()).getTabHost();
        //list adapter, defining footer button behaviors
        ListView lv = getListView();
        LayoutInflater inflater = getLayoutInflater();
        View footer = inflater.inflate(R.layout.expenses_list_footer, null, false);
        lv.addFooterView(footer);
        
        //list adapter
        final ArrayList<ExpenseItemEntry> samples = new ArrayList<ExpenseItemEntry>();
        samples.add(new ExpenseItemEntry(new GregorianCalendar(2012, 4, 15), 100, "food"));
        final MainExpenseListAdapter adapter = new MainExpenseListAdapter(this, samples);
        setListAdapter(adapter);
        
        Button add = (Button)footer.findViewById(R.id.add_expense);
        Button save = (Button) footer.findViewById(R.id.save_expense_btn);
        Button cancel = (Button) footer.findViewById(R.id.cancel_btn);
        add.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                samples.add(new ExpenseItemEntry(new GregorianCalendar(2012, 4, 15), 100, "food"));
                setListAdapter(adapter);

            }
        });
        
        save.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Intent intent = new Intent(v.getContext(), ViewSummaryActivity.class);
//                startActivity(intent);
                Toast.makeText(v.getContext(), "expenses saved", Toast.LENGTH_SHORT).show();

                tabhost.setCurrentTab(1);

            }
        });
        
        cancel.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Intent intent = new Intent(v.getContext(), HomeActivity.class);
//                startActivity(intent);
                tabhost.setCurrentTab(0);

            }
        });
    }
}
