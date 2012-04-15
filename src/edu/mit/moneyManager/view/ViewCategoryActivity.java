package edu.mit.moneyManager.view;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import edu.mit.moneyManager.R;
import edu.mit.moneyManager.listUtils.CategoryExpenseListAdapter;
import edu.mit.moneyManager.listUtils.ExpenseItemEntry;

/**
 * Screen that shows details of a Category
 *
 */
public class ViewCategoryActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_category);
        
        //list adapter
        ListView lv = getListView();
        LayoutInflater inflater = getLayoutInflater();
        View header = inflater.inflate(R.layout.view_category_list_header, null, false);
        lv.addHeaderView(header);
        
        //setting list adapter with sample data
        final ArrayList<ExpenseItemEntry> sampleExpenses = new ArrayList<ExpenseItemEntry>();
        sampleExpenses.add(new ExpenseItemEntry(new GregorianCalendar(2012, 4, 15), 100, "food"));
        sampleExpenses.add(new ExpenseItemEntry(new GregorianCalendar(2012, 4, 14), 900, "food"));
        sampleExpenses.add(new ExpenseItemEntry(new GregorianCalendar(2012, 3, 15), 89.90, "food"));
        sampleExpenses.add(new ExpenseItemEntry(new GregorianCalendar(2012, 2, 18), 89.67, "food"));
        
        CategoryExpenseListAdapter adapter = new CategoryExpenseListAdapter(this, sampleExpenses, this);
        setListAdapter(adapter);



    }
}
