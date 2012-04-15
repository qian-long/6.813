package edu.mit.moneyManager.listUtils;

import java.util.ArrayList;

import edu.mit.moneyManager.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TabHost;

public class MainExpenseListAdapter extends ArrayAdapter<ExpenseItemEntry>{

    private Context context;
//    private final Context parentContext;
    private ArrayList<ExpenseItemEntry> expenses;
    private LayoutInflater inflator;
    
    public MainExpenseListAdapter(Context context, ArrayList<ExpenseItemEntry> expenses) {
        super(context, 0, expenses);
        this.expenses = expenses;
        this.context = context;
        inflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ExpenseItemEntry expense = expenses.get(position);
        if (expense != null) {
            view = inflator.inflate(R.layout.list_entry_expense_add, null);
            view.setOnClickListener(null);
            view.setOnLongClickListener(null);
            view.setLongClickable(false);
            
            
        }
        
        return view;
    }

}
