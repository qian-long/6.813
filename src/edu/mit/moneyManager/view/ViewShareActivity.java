package edu.mit.moneyManager.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import edu.mit.moneyManager.R;
import edu.mit.moneyManager.listUtils.CategoryItemEntry;
import edu.mit.moneyManager.listUtils.SharedListAdapter;

public class ViewShareActivity extends ListActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_share);
        
        ListView lv = getListView();
        LayoutInflater inflater = getLayoutInflater();
        View footer = inflater.inflate(R.layout.view_share_footer, null, false);
        lv.addFooterView(footer);
        
        final ArrayList<String> sharedUsers = new ArrayList<String>();
        sharedUsers.add("darthvader@gmail.com");
        final SharedListAdapter adapter = new SharedListAdapter(this, sharedUsers);
        setListAdapter(adapter);
        
        lv.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                final Dialog dialog = new Dialog(getParent());
                dialog.setContentView(R.layout.dialog_delete_user);
                dialog.setTitle("Review User Sharing");
                dialog.setCancelable(false);
                
                Button saveBtn = (Button) dialog.findViewById(R.id.save_shared_user_btn);
                Button deleteBtn = (Button) dialog.findViewById(R.id.delete_shared_user_btn);
                
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });
                
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        sharedUsers.remove("darthvader@gmail.com");
                        ArrayAdapter<String> adapter = new SharedListAdapter(ViewShareActivity.this, sharedUsers);
                        setListAdapter(adapter);
                        dialog.dismiss();
                    }
                });
                
                dialog.show();
            }
            
        });
        
        Button addSharedUserBtn = (Button) footer.findViewById(R.id.add_shareduser_button);
        addSharedUserBtn.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //dialog
                final Dialog dialog = new Dialog(getParent());
                dialog.setContentView(R.layout.dialog_add_user);
                dialog.setTitle("Share with another user");
                dialog.setCancelable(false);
                
                Button addBtn = (Button) dialog.findViewById(R.id.add_share_user_btn);
                Button cancelBtn = (Button) dialog.findViewById(R.id.cancel_share_btn);
                
                addBtn.setOnClickListener(new View.OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        sharedUsers.add("darthvader@gmail.com");
                        ArrayAdapter<String> adapter = new SharedListAdapter(ViewShareActivity.this, sharedUsers);
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
    
    public TextView getGenericView() {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 64);

        TextView textView = new TextView(ViewShareActivity.this);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setPadding(35, 0, 0, 0);
        return textView;
    }
}
