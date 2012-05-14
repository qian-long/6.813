package edu.mit.moneyManager.view;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import edu.mit.moneyManager.R;

import edu.mit.moneyManager.listUtils.MainExpenseListAdapter;
import edu.mit.moneyManager.model.DatabaseAdapter;
import edu.mit.moneyManager.model.Expense;

/**
 * This is the expenses activity.
 * 
 * Users enter in their expenses here.
 */
public class ExpenseActivity extends ListActivity {
    public static final String TAG = "EXPENSE ACTIVITY";
    private TabHost tabhost;
    private DatabaseAdapter mDBAdapter;
    private ArrayList<Expense> expenses = new ArrayList<Expense>();
    private MainExpenseListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses);
        mDBAdapter = new DatabaseAdapter(this);
        tabhost = ((TabActivity) getParent()).getTabHost();
        // list adapter, defining footer button behaviors
        ListView lv = getListView();
        LayoutInflater inflater = getLayoutInflater();
        View footer = inflater.inflate(R.layout.expenses_list_footer, null,
                false);
        lv.addFooterView(footer);

        // list adapter
        adapter = new MainExpenseListAdapter(this, expenses, mDBAdapter);
        setListAdapter(adapter);

        Button add = (Button) footer.findViewById(R.id.add_expense);
        Button save = (Button) footer.findViewById(R.id.save_expense_btn);
        Button cancel = (Button) footer.findViewById(R.id.cancel_btn);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int month = c.get(Calendar.MONTH) + 1;
                String curDate = month + "/" + c.get(Calendar.DAY_OF_MONTH)
                        + "/" + c.get(Calendar.YEAR);
                expenses.add(new Expense(0, curDate, ""));
                adapter.notifyDataSetChanged();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // saving expenses in dataase
                ProgressDialog p = ProgressDialog.show(ExpenseActivity.this, "",
                        "Saving", false);
                boolean valid = true;
                mDBAdapter.open();
                if (expenses.size() > 0) {
                    for (Expense exp : expenses) {
                        if (exp.getAmount() <= 0.0) {
                            valid = false;
                            break;
                        }
                       
                    }

                    if (valid) {
                        for (Expense exp : expenses) {

                            mDBAdapter.addExpense(exp);
                        }
                        Toast.makeText(v.getContext(), "expenses saved",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                mDBAdapter.close();

                // clearing expenses list
                if (valid) {
                    ArrayList<Expense> copy = (ArrayList<Expense>) expenses
                            .clone();
                    expenses.removeAll(copy);
                    adapter.notifyDataSetChanged();
                    tabhost.setCurrentTab(1);
                    p.dismiss();
                }
                else {
                    p.dismiss();
                    Toast.makeText(v.getContext(),
                            "Please enter a valid amount",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<Expense> copy = (ArrayList<Expense>) expenses.clone();
                expenses.removeAll(copy);
                // expenses = new ArrayList<Expense>();
                adapter.notifyDataSetChanged();
                // return to home
                tabhost.setCurrentTab(0);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<Expense> copy = (ArrayList<Expense>) expenses.clone();
        expenses.removeAll(copy);
        // expenses = new ArrayList<Expense>();
        adapter.notifyDataSetChanged();
    }
}
