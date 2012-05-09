package edu.mit.moneyManager.listUtils;

import java.util.ArrayList;

import edu.mit.moneyManager.R;
import edu.mit.moneyManager.model.Expense;

import android.app.Dialog;
import android.content.Context;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryExpenseListAdapter extends ArrayAdapter<Expense>{

    private Context context;
    private final Context parentContext;
    private ArrayList<Expense> expenses;
    private LayoutInflater inflator;
    
    public CategoryExpenseListAdapter(Context context, ArrayList<Expense> expenses, Context parent) {
        super(context, 0, expenses);
        this.expenses = expenses;
        this.context = context;
        this.parentContext = parent;
        inflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final Expense expense = expenses.get(position);
        if (expense != null) {
            view = inflator.inflate(R.layout.list_entry_expense_edit, null);
            
            view.setOnClickListener(null);
            view.setOnLongClickListener(null);
            view.setLongClickable(false);
            
            final TextView date = (TextView) view.findViewById(R.id.expense_date);            
            final TextView amount = (TextView) view.findViewById(R.id.expense_amount);
            
            date.setText(expense.getDate());
            amount.setText(expense.getAmount().toString());

            ImageView edit = (ImageView) view.findViewById(R.id.edit_expense_btn);
            //Log.i("CategoryExpenseList adapter", (String)edit.getText());
            edit.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(parentContext);
                    Log.i("CategoryExpenseListAdapter", dialog.toString());
                    dialog.setContentView(R.layout.dialog_edit_expense);
                    dialog.setTitle("Editing Expense");
                    dialog.setCancelable(false);
                    
                    Button deleteBtn = (Button) dialog.findViewById(R.id.delete_expense_btn);
                    Button saveBtn = (Button) dialog.findViewById(R.id.save_expense_btn);
                    Button cancelBtn = (Button) dialog.findViewById(R.id.cancel_btn);
                    Button editDateBtn = (Button) dialog.findViewById(R.id.edit_date_btn);
                    
                    //TODO: prepopulate and make user input save
                    saveBtn.setOnClickListener(new View.OnClickListener() {
                        
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            EditText newAmount = (EditText)dialog.findViewById(R.id.new_amount);
                            EditText newCategory = (EditText)dialog.findViewById(R.id.new_category_name);
                            amount.setKeyListener(DigitsKeyListener.getInstance());

                            expense.setCategory("new category");
                            expense.setAmount(999);
                            date.setText("new name");
                            amount.setText("100");
                            dialog.dismiss();
//                            try {
////                                category.setTotal(new Integer(amountInput.getText().toString()));
//                                category.setName(newNameView.getText().toString());
//                                category.setTotal(Integer.parseInt(amountInput.getText().toString()));
//                                dialog.dismiss();
//
//                            }
//                            catch (NumberFormatException e) {
//                                Toast.makeText(context, "please enter a valid amount", Toast.LENGTH_SHORT);
//                            }
                        }
                    });
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    
                    deleteBtn.setOnClickListener(new View.OnClickListener() {
                        
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
                     
                    dialog.show();
                }
            });
            
        }
        return view;
    }
}
