package edu.mit.moneyManager.view;

import android.app.Activity;
import android.os.Bundle;
import edu.mit.moneyManager.R;

/**
 * Screen that shows details of a Category
 *
 */
public class ViewCategoryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_category);
    }
}
