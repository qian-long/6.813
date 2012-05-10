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
//        HomeActivity.NEW = false;
        mDBAdapter = new DatabaseAdapter(this);
        settings = getSharedPreferences(ViewSummaryActivity.PREFS_NAME,
                MODE_PRIVATE);

        final TextView totalAmt = (TextView) findViewById(R.id.budget_total);
        final float total = settings.getFloat(ViewSummaryActivity.BUDGET_TOTAL,
                (float) 0.0);
        totalAmt.setText(new Float(total).toString());
        final TextView unallocated = (TextView) findViewById(R.id.amount_unallocated);
        mDBAdapter.open();
        //amount unallocated = total - sum of category totals
        unallocated.setText(new Float(total - mDBAdapter.getCategoriesTotal())
                .toString());
        mDBAdapter.close();
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
                        

                        mDBAdapter.open();
                        float allocated = (float) mDBAdapter
                                .getCategoriesTotal();
                        mDBAdapter.close();
                        if (total.getText().toString().length() == 0
                                | total.getText().toString().equals("")) {
                            Toast.makeText(
                                    mContext,
                                    "Please enter a valid amount",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            float input = Float.parseFloat(total.getText()
                                    .toString());
                            if (input > allocated) {
                                Editor editor = settings.edit();
                                editor.putFloat(
                                        ViewSummaryActivity.BUDGET_TOTAL, input);
                                totalAmt.setText(total.getText().toString());
                                editor.commit();
                                mDBAdapter.open();
                                unallocated.setText(new Float(input
                                        - mDBAdapter.getCategoriesTotal())
                                        .toString());
                                mDBAdapter.close();
                                dialog.dismiss();

                            } else {
                                Toast.makeText(
                                        mContext,
                                        "Total is less than sum of category totals, please enter a larger amount",
                                        Toast.LENGTH_SHORT).show();
                            }
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

        // list adapter
        ListView lv = getListView();
        LayoutInflater inflater = getLayoutInflater();
        View footer = inflater.inflate(R.layout.view_edit_list_footer, null,
                false);
        lv.addFooterView(footer);

        // setting list adapter
        mDBAdapter.open();
        List<Category> categories = mDBAdapter.getCategories();
        mDBAdapter.close();
        Log.i("ViewEditBudgetActivity", "before adapter");
        EditCategoryListAdapter adapter = new EditCategoryListAdapter(this,
                (ArrayList<Category>) categories, getParent(), mDBAdapter, unallocated, total);
        setListAdapter(adapter);

        final Button addCategoryBtn = (Button) footer
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
                        float inpAmount = Float.parseFloat(newAmount.getText()
                                .toString());
                        float unallocatedAmt = settings.getFloat(
                                ViewSummaryActivity.BUDGET_TOTAL,
                                Float.MIN_VALUE)
                                - (float) mDBAdapter.getCategoriesTotal();
                        unallocated.setText(new Float(unallocatedAmt)
                                .toString());
                        if (inpAmount <= unallocatedAmt) {
                            mDBAdapter.addCategory(new Category(name.getText()
                                    .toString(), inpAmount, inpAmount));

                            EditCategoryListAdapter adapter = new EditCategoryListAdapter(
                                    mContext, (ArrayList<Category>) mDBAdapter
                                            .getCategories(), getParent(), mDBAdapter, unallocated, total);
                            setListAdapter(adapter);
                            float newUnallocatedAmt = settings.getFloat(
                                    ViewSummaryActivity.BUDGET_TOTAL,
                                    Float.MIN_VALUE)
                                    - (float) mDBAdapter.getCategoriesTotal();
                            unallocated.setText(new Float(newUnallocatedAmt)
                                    .toString());
                            mDBAdapter.close();
                            if (newUnallocatedAmt - 0.0 < 0.000001) {
                                addCategoryBtn.setEnabled(false);
                            }
                            else {
                                addCategoryBtn.setEnabled(true);
                            }
                            dialog.dismiss();
                        } else {
                            Toast.makeText(
                                    mContext,
                                    "Not enough unallocated. Please enter a lower amount",
                                    Toast.LENGTH_SHORT).show();
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
}
