package edu.mit.moneyManager.listUtils;

import java.util.ArrayList;
import java.util.List;

import edu.mit.moneyManager.R;
import edu.mit.moneyManager.model.DatabaseAdapter;
import edu.mit.moneyManager.model.Expense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;

public class MainExpenseListAdapter extends ArrayAdapter<Expense> {

    private Context context;
    // private final Context parentContext;
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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, categories);
            spinner.setAdapter(adapter);
            view.setOnClickListener(null);
            view.setOnLongClickListener(null);
            view.setLongClickable(false);

        }

        return view;
    }

}
