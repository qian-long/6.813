package edu.mit.moneyManager.listUtils;

import java.util.ArrayList;
import java.util.List;

import edu.mit.moneyManager.R;
import edu.mit.moneyManager.model.DatabaseAdapter;
import edu.mit.moneyManager.model.Expense;
import edu.mit.moneyManager.view.ExpenseActivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;

public class MainExpenseListAdapter extends ArrayAdapter<Expense> {

    private final Context context;
    private ArrayList<Expense> expenses;
    private LayoutInflater inflator;
    //pointer to dba
    private DatabaseAdapter mDBAdapter;

    public MainExpenseListAdapter(Context context, ArrayList<Expense> expenses, DatabaseAdapter dba) {
        super(context, 0, expenses);
        this.expenses = expenses;
        this.context = context;
        this.mDBAdapter = dba;
        inflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final Expense expense = expenses.get(position);
        if (expense != null) {
            view = inflator.inflate(R.layout.list_entry_expense_add, null);
            
            Spinner spinner = (Spinner) view
                    .findViewById(R.id.category_spinner);
            mDBAdapter.open();
            List<String> categories = mDBAdapter.getCategoryNames();
            mDBAdapter.close();
            //TODO: style dropdown
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, categories);
            spinner.setAdapter(adapter);
            
            Button datePicker = (Button) view.findViewById(R.id.edit_date_btn);
            datePicker.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    ((Activity) context).showDialog(ExpenseActivity.DATE_DIALOG_ID);
                }
            });
            
            view.setOnClickListener(null);
            view.setOnLongClickListener(null);
            view.setLongClickable(false);

        }

        return view;
    }

}
