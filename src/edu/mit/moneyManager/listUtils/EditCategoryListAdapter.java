package edu.mit.moneyManager.listUtils;

import java.util.ArrayList;
import java.util.List;

import edu.mit.moneyManager.R;
import edu.mit.moneyManager.model.Category;
import edu.mit.moneyManager.model.DatabaseAdapter;
import edu.mit.moneyManager.view.ViewEditBudgetActivity;

import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
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
import android.widget.Toast;

public class EditCategoryListAdapter extends ArrayAdapter<Category> {
    public static final String TAG = "EditCategoryListAdapter.java";
    private Context context;
    private Context parentContext;
    private ArrayList<Category> categories;
    private LayoutInflater inflator;
    // pointer to unallocatedView
    private TextView unallocatedView;
    private double totalAmt;
    // pointer to database
    private DatabaseAdapter dba;

    public EditCategoryListAdapter(Context context,
            ArrayList<Category> categories, Context parent,
            DatabaseAdapter dba, TextView view, float total) {
        super(context, 0, categories);
        this.categories = categories;
        this.context = context;
        this.parentContext = parent;
        this.dba = dba;
        this.unallocatedView = view;
        this.totalAmt = total;
        inflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Log.i(TAG, parent.toString());
        final Category category = categories.get(position);
        if (category != null) {
            view = inflator.inflate(R.layout.list_entry_category_edit, null);

            view.setOnClickListener(null);
            view.setOnLongClickListener(null);
            view.setLongClickable(false);

            final TextView categoryName = (TextView) view
                    .findViewById(R.id.category_name);
            final TextView total = (TextView) view
                    .findViewById(R.id.category_amount);

            categoryName.setText(category.getName());
            total.setText(new Double(category.getTotal()).toString());

            ImageView edit = (ImageView) view
                    .findViewById(R.id.edit_category_button);
            edit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    final Dialog dialog = new Dialog(parentContext);
                    dialog.setContentView(R.layout.dialog_edit_category);
                    dialog.setTitle("Editing " + category.getName()
                            + " Category");
                    dialog.setCancelable(false);

                    Button saveBtn = (Button) dialog
                            .findViewById(R.id.save_category_btn);
                    Button cancelBtn = (Button) dialog
                            .findViewById(R.id.cancel_btn);

                    final EditText newCategoryNameView = (EditText) dialog
                            .findViewById(R.id.new_category_name);
                    final EditText newAmountView = (EditText) dialog
                            .findViewById(R.id.new_category_amount);
                    newCategoryNameView.setText(category.getName());
                    newAmountView.setText(category.getTotal().toString());
                    newAmountView.setInputType(InputType.TYPE_CLASS_NUMBER
                            | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    saveBtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            // TODO, actually change in database
                            String newCategory = newCategoryNameView.getText()
                                    .toString();
                            double newamt = Double.parseDouble(newAmountView
                                    .getText().toString());

                            dba.open();
                            boolean exists = dba.categoryExist(newCategory);
                            dba.close();
                            if (!newCategory.equals(category.getName())
                                    && exists) {
                                Toast.makeText(
                                        v.getContext(),
                                        "Category name already exists, please enter another name",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                Log.i(TAG, category.getName() + " "
                                        + newCategory + " " + newamt);
                                dba.open();
                                dba.updateCategory(category.getName(),
                                        newCategory, newamt);
                                // update amount unallocated
                                // amount unallocated = total - sum of category
                                // totals
                                unallocatedView.setText(new Float(totalAmt
                                        - dba.getCategoriesTotal()).toString());
                                Log.i(TAG, "updateing unallocated view: " + new Float(totalAmt
                                        - dba.getCategoriesTotal()).toString());
                                dba.close();
                                category.setName(newCategory);
                                category.setTotal(newamt);
                                notifyDataSetChanged();
                                dialog.dismiss();

                            }

                        }
                    });
                    cancelBtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
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
