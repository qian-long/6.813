package edu.mit.moneyManager.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter {
    public static final String TAG = "DATABASE ADAPTER";
    public static final String UNCATEGORIZED = "uncategorized";
    private DbHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context mContext;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_NAME = "data";
    private static final String EXPENSES_TABLE = "expenses";
    private static final String CATEGORIES_TABLE = "categories";
    
    private static final String ROW_ID = "_id";

    // categories columns
    private static final String CATEGORY_ROW_ID = "_id";
    private static final String NAME_COLUMN = "name";
    private static final String TOTAL_COLUMN = "total";
    private static final String REMAINING_COLUMN = "remaining";

    // expenses columns
    private static final String DATE_COLUMN = "date";
    private static final String AMOUNT_COLUMN = "amount";
    private static final String CATEGORY_COLUMN = "category";

    private class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + CATEGORIES_TABLE
                    + " (" + CATEGORY_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME_COLUMN
                    + " TEXT NOT NULL, " + TOTAL_COLUMN + " REAL NOT NULL, "
                    + REMAINING_COLUMN + " REAL NOT NULL);");

//            db.execSQL("CREATE TABLE " + EXPENSES_TABLE
//                    + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE_COLUMN
//                    + " TEXT NOT NULL, " + AMOUNT_COLUMN + " REAL NOT NULL, "
//                    + CATEGORY_COLUMN + " TEXT NOT NULL, " + "FOREIGN KEY("
//                    + CATEGORY_COLUMN + ") REFERENCES " + CATEGORIES_TABLE
//                    + "(" + NAME_COLUMN + "));");
            
            db.execSQL("CREATE TABLE " + EXPENSES_TABLE
                    + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE_COLUMN
                    + " TEXT NOT NULL, " + AMOUNT_COLUMN + " REAL NOT NULL, "
                    + CATEGORY_COLUMN + " TEXT NOT NULL);");

        }

        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            /*
            if (!db.isReadOnly()) {
                // Enable foreign key constraints
                db.execSQL("PRAGMA foreign_keys=ON;");
            }
            */
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + EXPENSES_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE);
            onCreate(db);

        }

    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx
     *            the Context within which to work
     */
    public DatabaseAdapter(Context ctx) {
        this.mContext = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException
     *             if the database could be neither opened or created
     */
    public DatabaseAdapter open() throws SQLException {
        mDbHelper = new DbHelper(mContext);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    // category methods
    /**
     * 
     * @param category
     * @return rowId of category added or -1 if failed to insert
     */
    public long addCategory(Category category) {
        Log.i(TAG, "adding category");
        ContentValues initialValues = new ContentValues();

        initialValues.put(NAME_COLUMN, category.getName());
        initialValues.put(TOTAL_COLUMN, category.getTotal());
        initialValues.put(REMAINING_COLUMN, category.getRemaining());

        return mDb.insert(CATEGORIES_TABLE, null, initialValues);
    }

    public boolean updateCategory(String name, String newname, double newamt) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(NAME_COLUMN, newname);
        updatedValues.put(TOTAL_COLUMN, newamt);
        Cursor cursor = mDb.query(CATEGORIES_TABLE, new String[] { CATEGORY_ROW_ID },
                NAME_COLUMN + "=\'" + name + "\'", null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            int row = cursor.getInt(0);
            boolean success = mDb.update(CATEGORIES_TABLE, updatedValues, CATEGORY_ROW_ID + "="
                    + row, null) == 1;
            
            StringBuilder test = new StringBuilder();
            for (String existingCat: getCategoryNames()) {
                test.append(existingCat + "\n");
            }
            Log.i(TAG, "updating database " + test.toString());
            return success;
        }
        else {
            return false;
        }
        // Cursor cursor = mDb.query(EXPENSES_TABLE, new String[] { ROW_ID },
        // CATEGORY_COLUMN + "=\'" + name + "\'", null, null, null, null);

        // update column name of all expenses in that category
        // if (cursor.getCount() > 0) {
        // cursor.moveToFirst();
        // ContentValues expenseUpdate = new ContentValues();
        // updatedValues.put(CATEGORY_COLUMN, newname);
        // while (!cursor.isAfterLast()) {
        // mDb.update(EXPENSES_TABLE, expenseUpdate, whereClause, whereArgs)
        // cursor.moveToNext();
        // }
        // }
        // ContentValues expenseUpdate = new ContentValues();
        // expenseUpdate.put(CATEGORY_COLUMN, newname);
        // mDb.update(EXPENSES_TABLE, expenseUpdate, CATEGORY_COLUMN + "=\'"
        // + name + "\'", null);

        // String[] args = {name};
        // return mDb.update(CATEGORIES_TABLE, updatedValues, NAME_COLUMN +
        // "=?", args) == 1;
    }

    /**
     * 
     * @param name
     * @return true if category exists, false if not
     */
    public boolean categoryExist(String name) {
        Cursor cursor = mDb.query(CATEGORIES_TABLE, new String[] { CATEGORY_ROW_ID,
                NAME_COLUMN }, NAME_COLUMN + "=\'" + name + "\'", null, null,
                null, null);
        return (cursor.getCount() > 0);
    }

    /**
     * 
     * @param name
     * @return
     *      Category, null if doesn't exist
     */
    public Category getCategory(String name) {
        Cursor cursor = mDb.query(CATEGORIES_TABLE, new String[] { CATEGORY_ROW_ID,
                NAME_COLUMN, TOTAL_COLUMN, REMAINING_COLUMN }, NAME_COLUMN + "=\'" + name + "\'", null, null,
                null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            double total = Double.parseDouble(cursor.getString(2));
            double remaining = Double.parseDouble(cursor.getString(3));
            return new Category(name, total, remaining);
        }
        return null;
    }
    // TODO
    /**
     * Query for all expenses in <name> category. Update all expenses from the
     * <name> category to UNCATEGORIZED, then removes category
     * 
     * @param name
     * @return True if deleted, False otherwise
     */
    public boolean removeCategory(String name) {
        Cursor cursor = mDb.query(EXPENSES_TABLE, new String[] { ROW_ID },
                CATEGORY_COLUMN + "=\'" + name + "\'", null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String id = cursor.getString(0);
            ContentValues values = new ContentValues();
            values.put(CATEGORY_COLUMN, UNCATEGORIZED);
            mDb.update(EXPENSES_TABLE, values, ROW_ID + "=" + id, null);

        }
        return mDb.delete(CATEGORIES_TABLE, NAME_COLUMN + "=\'" + name + "\'",
                null) > 0;
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<Category>();
        Cursor cursor = mDb.query(CATEGORIES_TABLE, new String[] { CATEGORY_ROW_ID,
                NAME_COLUMN, TOTAL_COLUMN, REMAINING_COLUMN }, null, null,
                null, null, NAME_COLUMN);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(1);
            if (!name.equalsIgnoreCase(UNCATEGORIZED)) {
                double total = Double.parseDouble(cursor.getString(2));
                double remaining = Double.parseDouble(cursor.getString(3));
                categories.add(new Category(name, total, remaining));
            }
            cursor.moveToNext();
        }
        return categories;
    }

    /**
     * 
     * @return Total allocated for the categories
     */
    public double getCategoriesTotal() {
        List<Category> categories = this.getCategories();
        double total = 0.0;
        for (Category category : categories) {
            total += category.getTotal();
        }
        return total;
    }

    /**
     * 
     * @return List of all category names in database
     */
    public List<String> getCategoryNames() {
        List<String> names = new ArrayList<String>();
        Cursor cursor = mDb.query(CATEGORIES_TABLE,
                new String[] { NAME_COLUMN }, null, null, null, null,
                NAME_COLUMN);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            names.add(cursor.getString(0));
            cursor.moveToNext();
        }
        return names;
    }

    // expense methods
    /**
     * Create a new row with Expense. If row is successfully created return the
     * new rowId for that note, otherwise return a -1 to indicate failure.
     * 
     * Precondition: category name exists
     * 
     * @param parseId
     *            parseId of the printer
     * @return rowId or -1 if failed
     */
    public long addExpense(Expense expense) {
        Log.i(TAG, "adding expense");
        ContentValues initialValues = new ContentValues();
        initialValues.put(DATE_COLUMN, expense.getDate());
        initialValues.put(AMOUNT_COLUMN, expense.getAmount());
        initialValues.put(CATEGORY_COLUMN, expense.getCategory());

        return mDb.insert(EXPENSES_TABLE, null, initialValues);
    }

    /**
     * 
     * @param categoryName
     * @return List of expenses in this category
     */
    public List<Expense> getExpenses(String categoryName) {
        List<Expense> expenses = new ArrayList<Expense>();
        Cursor cursor = mDb.query(EXPENSES_TABLE, new String[] { ROW_ID,
                DATE_COLUMN, AMOUNT_COLUMN, CATEGORY_COLUMN }, CATEGORY_COLUMN
                + "=\'" + categoryName + "\'", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = Integer.parseInt(cursor.getString(0));
            String date = cursor.getString(1);
            double amount = Double.parseDouble(cursor.getString(2));
            String category = cursor.getString(3);
            expenses.add(new Expense(amount, date, category, id));
            cursor.moveToNext();
        }
        cursor.close();
        return expenses;
    }

    // TODO
    public boolean updateExpense(Expense oldExpense, Expense newExpense) {
        return false;
    }
}
