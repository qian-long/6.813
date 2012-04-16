package edu.mit.moneyManager.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import edu.mit.moneyManager.R;
import edu.mit.moneyManager.listUtils.CategoryItemEntry;
import edu.mit.moneyManager.listUtils.EditCategoryListAdapter;
import edu.mit.moneyManager.listUtils.SummaryCategoryListAdapter;

public class ViewEditBudgetActivity extends ListActivity {
    private Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_edit_budget);
        mContext = this;
        HomeActivity.NEW=false;
        
        //adding button listener for editing total budget amount
        final TextView totalAmt = (TextView) findViewById(R.id.budget_total);
        
        Button editTotal = (Button) findViewById(R.id.edit_total);
        editTotal.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Dialog dialog = new Dialog(getParent());
                dialog.setContentView(R.layout.dialog_edit_total);
                dialog.setTitle("Edit Total Budget Amount");
                dialog.setCancelable(false);
                
                Button saveBtn = (Button) dialog.findViewById(R.id.save_total_btn);
                Button cancelBtn = (Button) dialog.findViewById(R.id.cancel_btn);
                EditText total = (EditText) dialog.findViewById(R.id.new_total);
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        // TODO put in actual user input
                        totalAmt.setText("1100");
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

        //list adapter
        ListView lv = getListView();
        LayoutInflater inflater = getLayoutInflater();
        View footer = inflater.inflate(R.layout.view_edit_list_footer, null, false);
        lv.addFooterView(footer);
        
        
        //setting list adapter
        //samples data
        final List<CategoryItemEntry> sample = new ArrayList<CategoryItemEntry>();
        sample.add(new CategoryItemEntry("Food", 500, 500));
        sample.add(new CategoryItemEntry("Books", 500, 500));
        sample.add(new CategoryItemEntry("Clothing", 900,799));
 
        Log.i("ViewEditBudgetActivity", "before adapter");
        EditCategoryListAdapter adapter = new EditCategoryListAdapter(this, (ArrayList<CategoryItemEntry>) sample, getParent());
        setListAdapter(adapter);
        
        Button addCategoryBtn = (Button) footer.findViewById(R.id.add_category_button);
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //dialog
                final Dialog dialog = new Dialog(getParent());
                dialog.setContentView(R.layout.dialog_add_category);
                dialog.setTitle("Add a new category");
                dialog.setCancelable(false);
                
                Button addBtn = (Button) dialog.findViewById(R.id.add_category_btn);
                Button cancelBtn = (Button) dialog.findViewById(R.id.cancel_btn);
                
                addBtn.setOnClickListener(new View.OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        sample.add(new CategoryItemEntry("New Category", 1000, 1000));
                        EditCategoryListAdapter adapter = new EditCategoryListAdapter(mContext, (ArrayList<CategoryItemEntry>) sample, getParent());
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
