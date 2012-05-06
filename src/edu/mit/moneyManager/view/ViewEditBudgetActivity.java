package edu.mit.moneyManager.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.mit.moneyManager.R;
import edu.mit.moneyManager.listUtils.CategoryItemEntry;
import edu.mit.moneyManager.listUtils.EditCategoryListAdapter;
import edu.mit.moneyManager.listUtils.SummaryCategoryListAdapter;
import edu.mit.moneyManager.model.Category;
import edu.mit.moneyManager.model.DatabaseAdapter;

public class ViewEditBudgetActivity extends ListActivity {
    public static final String BUDGET_UNALLOCATED = "unallocated";
    private Context mContext;
    private SharedPreferences settings;
    private DatabaseAdapter mDBAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_edit_budget);
        mContext = this;
        HomeActivity.NEW = false;
        mDBAdapter = new DatabaseAdapter(this);
        settings = getSharedPreferences(ViewSummaryActivity.PREFS_NAME,
                MODE_PRIVATE);

        // adding button listener for editing total budget amount
        final TextView totalAmt = (TextView) findViewById(R.id.budget_total);
        totalAmt.setText(new Float(settings.getFloat(
                ViewSummaryActivity.BUDGET_TOTAL, (float) 0.0)).toString());
        ImageView editTotal = (ImageView) findViewById(R.id.edit_total);
        editTotal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Dialog dialog = new Dialog(getParent());
                dialog.setContentView(R.layout.dialog_edit_total);
                dialog.setTitle("Edit Total Budget Amount");
                dialog.setCancelable(false);

                Button saveBtn = (Button) dialog
                        .findViewById(R.id.save_total_btn);
                Button cancelBtn = (Button) dialog
                        .findViewById(R.id.cancel_btn);
                final EditText total = (EditText) dialog
                        .findViewById(R.id.new_total);
                total.setInputType(InputType.TYPE_CLASS_NUMBER
                        | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                saveBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO put in actual user input
                        float input = Float.parseFloat(total.getText()
                                .toString());

                        mDBAdapter.open();
                        float allocated = (float) mDBAdapter
                                .getCategoriesTotal();
                        mDBAdapter.close();
                        if (input > allocated) {
                            Editor editor = settings.edit();
                            editor.putFloat(ViewSummaryActivity.BUDGET_TOTAL,
                                    input);
                            totalAmt.setText(total.getText().toString());
                            editor.commit();
                            dialog.dismiss();

                        } else {
                            Toast.makeText(
                                    mContext,
                                    "Total is less than sum of category totals, please enter a larger amount",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        // list adapter
        ListView lv = getListView();
        LayoutInflater inflater = getLayoutInflater();
        View footer = inflater.inflate(R.layout.view_edit_list_footer, null,
                false);
        lv.addFooterView(footer);

        // setting list adapter
        // samples data
        final List<CategoryItemEntry> sample = new ArrayList<CategoryItemEntry>();
        sample.add(new CategoryItemEntry("Food", 500, 500));
        sample.add(new CategoryItemEntry("Books", 500, 500));
        sample.add(new CategoryItemEntry("Clothing", 900, 799));

        mDBAdapter.open();
        final List<Category> categories = mDBAdapter.getCategories();
        mDBAdapter.close();
        Log.i("ViewEditBudgetActivity", "before adapter");
        EditCategoryListAdapter adapter = new EditCategoryListAdapter(this,
                (ArrayList<Category>) categories, getParent());
        setListAdapter(adapter);

        Button addCategoryBtn = (Button) footer
                .findViewById(R.id.add_category_button);
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // dialog
                final Dialog dialog = new Dialog(getParent());
                dialog.setContentView(R.layout.dialog_add_category);
                dialog.setTitle("Add a new category");
                dialog.setCancelable(false);

                Button addBtn = (Button) dialog
                        .findViewById(R.id.add_category_btn);
                Button cancelBtn = (Button) dialog
                        .findViewById(R.id.cancel_btn);
                final EditText name = (EditText) dialog
                        .findViewById(R.id.new_category_name);
                final EditText newAmount = (EditText) dialog
                        .findViewById(R.id.new_amount);
                newAmount.setInputType(InputType.TYPE_CLASS_NUMBER
                        | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                addBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mDBAdapter.open();
                        float amount = Float.parseFloat(newAmount.getText()
                                .toString());
                        mDBAdapter.addCategory(new Category(name.getText()
                                .toString(), amount, amount));
                        
                        EditCategoryListAdapter adapter = new EditCategoryListAdapter(
                                mContext, (ArrayList<Category>) mDBAdapter.getCategories(),
                                getParent());
                        mDBAdapter.close();
                        setListAdapter(adapter);
                        dialog.dismiss();
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {

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
}
