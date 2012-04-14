package edu.mit.moneyManager.listUtils;

import java.util.ArrayList;

import edu.mit.moneyManager.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoryListAdapter extends ArrayAdapter<CategoryItemEntry>{

    private Context context;
    private ArrayList<CategoryItemEntry> categories;
    private LayoutInflater inflator;
    
    public CategoryListAdapter(Context context, ArrayList<CategoryItemEntry> categories) {
        super(context, 0, categories);
        this.categories = categories;
        this.context = context;
        inflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final CategoryItemEntry category = categories.get(position);
        if (category != null) {
            view = inflator.inflate(R.layout.list_entry_category_summary, null);
            
            view.setOnClickListener(null);
            view.setOnLongClickListener(null);
            view.setLongClickable(false);
            
            TextView categoryName = (TextView) view.findViewById(R.id.view_summary_category);            
            TextView total = (TextView) view.findViewById(R.id.category_total);
            TextView remaining = (TextView) view.findViewById(R.id.category_remaining);
            
            categoryName.setText(category.getName());
            total.setText(category.getTotalAmount());
            remaining.setText(category.getRemainingAmount());

            
        }
        return view;
    }

}
