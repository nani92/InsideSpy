package com.nataliajastrzebska.insidespy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nataliajastrzebska on 08/02/16.
 */
public class ContactDataSource {

    // Database fields
    private SQLiteDatabase database;
    private ContactSQLiteHelper dbHelper;
    private String[] allColumns = { ContactSQLiteHelper.COLUMN_ID, ContactSQLiteHelper.COLUMN_NUMBER };

    public ContactDataSource(Context context) {
        dbHelper = new ContactSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Contact createContact(String number) {
        ContentValues values = new ContentValues();
        values.put(ContactSQLiteHelper.COLUMN_NUMBER, number);
        long insertId = database.insert(ContactSQLiteHelper.TABLE_CONTACTS, null,
                values);
        Cursor cursor = database.query(ContactSQLiteHelper.TABLE_CONTACTS,
                allColumns, ContactSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Contact newComment = cursorToContact(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(Contact contact) {
        long id = contact.getId();
        database.delete(ContactSQLiteHelper.TABLE_CONTACTS, ContactSQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<Contact>();

        Cursor cursor = database.query(ContactSQLiteHelper.TABLE_CONTACTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contact contact = cursorToContact(cursor);
            contacts.add(contact);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return contacts;
    }

    private Contact cursorToContact(Cursor cursor) {
        Contact contact = new Contact();
        contact.setId(cursor.getLong(0));
        contact.setNumber(cursor.getString(1));
        return contact;
    }
}

