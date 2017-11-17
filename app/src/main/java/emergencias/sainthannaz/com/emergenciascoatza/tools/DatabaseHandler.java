package emergencias.sainthannaz.com.emergenciascoatza.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.util.ArrayList;
import java.util.List;

import emergencias.sainthannaz.com.emergenciascoatza.model.InnerTables;
import emergencias.sainthannaz.com.emergenciascoatza.model.Numbers;
import emergencias.sainthannaz.com.emergenciascoatza.model.SubNumbers;


/**
 * Created by Gabriel on 06/11/2017.
 */

public class DatabaseHandler  extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    public static final String TABLE_METAS = "metas";
    public static final String TABLE_SUBMETAS = "submetas";

    // Contacts Table Columns names
    public static final String KEY_ID = "card_id";
    public static final String KEY_TITLE = "card_title";
    public static final String KEY_DESCRIPTION = "card_description";
    public static final String KEY_PICTURE = "card_picture";
    public static final String KEY_CATEGORY = "card_category";
    public static final String KEY_UPDATE = "card_update";

    public static final String KEY_PHONE_ID = "phone_card_id";
    public static final String KEY_ADDRESS = "card_address";
    public static final String KEY_PHONE = "card_phone";
    public static final String KEY_MAP = "card_map";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_META_TABLE = "CREATE TABLE " + TABLE_METAS + "("
                + KEY_ID + " INTEGER ," + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," + KEY_PICTURE + " TEXT," + KEY_CATEGORY + " TEXT," + KEY_UPDATE + " TEXT" +")";

        String CREATE_SUBMETA_TABLE = "CREATE TABLE " + TABLE_SUBMETAS + "("
                + KEY_PHONE_ID + " INTEGER , " + KEY_ID + " INTEGER," + KEY_ADDRESS + " TEXT,"
                + KEY_PHONE + " TEXT," + KEY_MAP + " TEXT," + KEY_UPDATE + " TEXT" +")";

        db.execSQL(CREATE_META_TABLE);
        System.out.println("Base de datos creada...META");
        db.execSQL(CREATE_SUBMETA_TABLE);
        System.out.println("Base de datos creada...SUBMETA");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_METAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBMETAS);
        // Create tables again
        onCreate(db);
    }


    /**
     * MY CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding card data
    public void addCarddata(int card_id, String card_title, String card_description, String card_picture, String card_category, String card_update) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, card_id);
        values.put(KEY_TITLE, card_title);
        values.put(KEY_DESCRIPTION, card_description);
        values.put(KEY_PICTURE, card_picture);
        values.put(KEY_CATEGORY, card_category);
        values.put(KEY_UPDATE, card_update);

        // Inserting Row
        db.insert(TABLE_METAS, null, values);
        db.close(); // Closing database connection
    }

    // Adding new contact
    public void addSubdata(int phone_card_id, int card_id, String card_address, String card_phone, String card_map, String card_update) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PHONE_ID, phone_card_id);
        values.put(KEY_ID, card_id);
        values.put(KEY_ADDRESS, card_address);
        values.put(KEY_PHONE, card_phone);
        values.put(KEY_MAP, card_map);
        values.put(KEY_UPDATE, card_update);

        // Inserting Row
        db.insert(TABLE_SUBMETAS, null, values);
        db.close(); // Closing database connection
    }

    public void DeleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_METAS);
        db.close(); // Closing database connection
    }

    public void DeleteSubData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_SUBMETAS);
        db.close(); // Closing database connection
    }

    // Getting single number
    public Numbers getInfoMeta(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_METAS, new String[] { KEY_ID,
                        KEY_TITLE, KEY_DESCRIPTION, KEY_PICTURE, KEY_CATEGORY, KEY_UPDATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Numbers numbers = new Numbers(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5));
        return numbers;
    }

    SubNumbers getInfoSubMeta(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SUBMETAS, new String[] { KEY_PHONE_ID, KEY_ID,
                        KEY_ADDRESS, KEY_PHONE, KEY_MAP, KEY_UPDATE }, KEY_PHONE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        SubNumbers subNumbers = new SubNumbers(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5));
        return subNumbers;
    }

    // Getting All Emergency Numbers
    public List<Numbers> getAllContacts() {
        List<Numbers> numbersList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_METAS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Numbers numbers = new Numbers();
                numbers.setCard_id(Integer.parseInt(cursor.getString(0)));
                numbers.setCard_title(cursor.getString(1));
                numbers.setCard_description(cursor.getString(2));
                numbers.setCard_picture(cursor.getString(3));
                numbers.setCard_category(cursor.getString(4));
                numbers.setCard_update(cursor.getString(5));
                // Adding contact to list
                numbersList.add(numbers);
            } while (cursor.moveToNext());
        }

        // return contact list
        return numbersList;
    }

    // Getting All Emergency Numbers
    public List<SubNumbers> getAllSpecificSubData(int id) {
        List<SubNumbers> subdataList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SUBMETAS + " WHERE " + KEY_ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubNumbers subNumbers = new SubNumbers();
                subNumbers.setCard_phone_id(Integer.parseInt(cursor.getString(0)));
                subNumbers.setCard_id(Integer.parseInt(cursor.getString(1)));
                subNumbers.setCard_address(cursor.getString(2));
                subNumbers.setCard_phone(cursor.getString(3));
                subNumbers.setCard_map(cursor.getString(4));
                subNumbers.setCard_update(cursor.getString(5));
                // Adding contact to list
                subdataList.add(subNumbers);
            } while (cursor.moveToNext());
        }

        // return contact list
        return subdataList;
    }

    // Getting All Emergency Numbers
    public List<SubNumbers> getAllSubData() {
        List<SubNumbers> subdataList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SUBMETAS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubNumbers subNumbers = new SubNumbers();
                subNumbers.setCard_phone_id(Integer.parseInt(cursor.getString(0)));
                subNumbers.setCard_id(Integer.parseInt(cursor.getString(1)));
                subNumbers.setCard_address(cursor.getString(2));
                subNumbers.setCard_phone(cursor.getString(3));
                subNumbers.setCard_map(cursor.getString(4));
                subNumbers.setCard_update(cursor.getString(5));
                // Adding contact to list
                subdataList.add(subNumbers);
            } while (cursor.moveToNext());
        }

        // return contact list
        return subdataList;
    }

    // Getting All Emergency Numbers
    public List<InnerTables> getAllInnerTable() {
        List<InnerTables> subInnerDataList = new ArrayList<>();
        // Select All Query

        String selectQuery = "SELECT " + TABLE_METAS + "." + KEY_ID + ", " + TABLE_METAS + "." + KEY_TITLE + ", " + TABLE_METAS + "." + KEY_DESCRIPTION + ", " + TABLE_METAS + "." + KEY_CATEGORY + ", " +  TABLE_SUBMETAS + "." + KEY_PHONE_ID + ", " + TABLE_SUBMETAS + "." + KEY_MAP + " FROM " + TABLE_METAS + " INNER JOIN " + TABLE_SUBMETAS + " ON " + TABLE_METAS + "." + KEY_ID + " = " + TABLE_SUBMETAS + "." + KEY_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InnerTables innerNumbers = new InnerTables();
                innerNumbers.setCard_id(Integer.parseInt(cursor.getString(0)));
                innerNumbers.setCard_title(cursor.getString(1));
                innerNumbers.setCard_description(cursor.getString(2));
                innerNumbers.setCard_category(cursor.getString(3));
                innerNumbers.setCard_unique_id(Integer.parseInt(cursor.getString(4)));
                innerNumbers.setCard_map(cursor.getString(5));
                // Adding contact to list
                subInnerDataList.add(innerNumbers);
            } while (cursor.moveToNext());
        }

        // return contact list
        return subInnerDataList;
    }
}
