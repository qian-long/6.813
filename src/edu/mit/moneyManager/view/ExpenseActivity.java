package edu.mit.moneyManager.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import edu.mit.moneyManager.R;
import edu.mit.moneyManager.listUtils.CategoryExpenseListAdapter;
import edu.mit.moneyManager.listUtils.CategoryItemEntry;
import edu.mit.moneyManager.listUtils.ExpenseItemEntry;
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
    public static final int DATE_DIALOG_ID = 0;
    private TabHost tabhost;
    private DatabaseAdapter mDBAdapter;

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
        final ArrayList<Expense> samples = new ArrayList<Expense>();
        samples.add(new Expense(100, "5/6/2012", "food"));
        final MainExpenseListAdapter adapter = new MainExpenseListAdapter(this,
                samples, mDBAdapter);
        setListAdapter(adapter);

        Button add = (Button) footer.findViewById(R.id.add_expense);
        Button save = (Button) footer.findViewById(R.id.save_expense_btn);
        Button cancel = (Button) footer.findViewById(R.id.cancel_btn);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // samples.add(new ExpenseItemEntry(new GregorianCalendar(2012,
                // 4, 15), 100, "food"));
                samples.add(new Expense(100, "", ""));
                setListAdapter(adapter);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // Intent intent = new Intent(v.getContext(),
                // ViewSummaryActivity.class);
                // startActivity(intent);
                Toast.makeText(v.getContext(), "expenses saved",
                        Toast.LENGTH_SHORT).show();

                tabhost.setCurrentTab(1);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // Intent intent = new Intent(v.getContext(),
                // HomeActivity.class);
                // startActivity(intent);
                tabhost.setCurrentTab(0);

            }
        });
    }

    // datepicker dialog
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            Log.i(TAG, "creating date picker dialog");
            Calendar c = Calendar.getInstance();
            return new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            Toast.makeText(view.getContext(),  new StringBuilder()
                            .append(monthOfYear + 1).append("-")
                            .append(dayOfMonth).append("-")
                            .append(year).append(" "), Toast.LENGTH_SHORT).show();
                        }
                    }, c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

}
