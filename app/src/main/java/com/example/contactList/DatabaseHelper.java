package com.example.contactList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Persons";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "person";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_FIRST_NAME = "firstName";
    public static final String COLUMN_LAST_NAME = "lastName";
    public static final String COLUMN_PHONE_NUMBER = "phoneNumber";
    public static final String COLUMN_ADDRESS = "address";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT person_pk PRIMARY KEY AUTOINCREMENT, " + COLUMN_FIRST_NAME + " varchar(200) NOT NULL, " + COLUMN_LAST_NAME + " varchar(200) NOT NULL, " + COLUMN_PHONE_NUMBER + " varchar(200) NOT NULL, " +  COLUMN_ADDRESS + " varchar(200) NOT NULL);";
        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


    public ArrayList<Person> loadPersons() {

        ArrayList persons = new ArrayList();
        String sql = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){
            Person person = new Person();

            person.setId(cursor.getInt(0));
            person.setFirstName(cursor.getString(1));
            person.setLastName(cursor.getString(2));
            person.setPhoneNumber(cursor.getString(3));
            person.setAddress(cursor.getString(4));
            persons.add(person);

        }
        cursor.close();
        db.close();
        return  persons;
    }


    public void addPerson(Person person){

        ContentValues values = new ContentValues();

        values.put(COLUMN_FIRST_NAME,person.getFirstName());
        values.put(COLUMN_LAST_NAME,person.getLastName());
        values.put(COLUMN_PHONE_NUMBER,person.getPhoneNumber());
        values.put(COLUMN_ADDRESS,person.getAddress());


        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public Person viewContact(int id){
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID +"="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        Person person = new Person();

        while (cursor.moveToNext()){

            person.setId(cursor.getInt(0));
            person.setFirstName(cursor.getString(1));
            person.setLastName(cursor.getString(2));
            person.setPhoneNumber(cursor.getString(3));
            person.setAddress(cursor.getString(4));


        }
        return  person;
    }

    public  void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_ID + "=" + id;
        db.delete(TABLE_NAME,where,null);
        db.close();
    }

    public  void updatePerson(Person person){
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlId = COLUMN_ID + " = " + person.getId();

        ContentValues values = new ContentValues();

        values.put(COLUMN_FIRST_NAME,person.getFirstName());
        values.put(COLUMN_LAST_NAME,person.getLastName());
        values.put(COLUMN_ADDRESS,person.getAddress());
        values.put(COLUMN_PHONE_NUMBER,person.getPhoneNumber());

        db.update(TABLE_NAME,values,sqlId,null);
        db.close();

    }
}