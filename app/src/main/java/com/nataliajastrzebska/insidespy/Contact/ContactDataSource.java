package com.nataliajastrzebska.insidespy.Contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nataliajastrzebska on 08/02/16.
 */
public class ContactDataSource {

    private SQLiteDatabase database;
    private ContactSQLiteHelper dbHelper;
    private String[] allColumns = {
            ContactSQLiteHelper.COLUMN_ID,
            ContactSQLiteHelper.COLUMN_NUMBER,
            ContactSQLiteHelper.COLUMN_TYPE,
            ContactSQLiteHelper.COLUMN_NAME
    };

    public ContactDataSource(Context context) {
        dbHelper = new ContactSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Long createContact(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(ContactSQLiteHelper.COLUMN_NUMBER, contact.getNumber());
        values.put(ContactSQLiteHelper.COLUMN_TYPE, contact.getType().toString());
        values.put(ContactSQLiteHelper.COLUMN_NAME, contact.getName().toString());
        long insertId = database.insert(ContactSQLiteHelper.TABLE_CONTACTS, null,
                values);
        Cursor cursor = database.query(ContactSQLiteHelper.TABLE_CONTACTS,
                allColumns, ContactSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Contact newContact = cursorToContact(cursor);
        cursor.close();
        return newContact.getId();
    }

    public Contact getContact(Long id) {
        for (Contact contact : getAllContacts()) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    public void deleteContact(Contact contact) {
        long id = contact.getId();
        database.delete(ContactSQLiteHelper.TABLE_CONTACTS, ContactSQLiteHelper.COLUMN_ID + " = " + id, null);
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

        cursor.close();
        return contacts;
    }

    public List<Contact> getContactsSpyOnMe() {
        List<Contact> contacts = new ArrayList<Contact>();

        for(Contact contact : getAllContacts()) {

            if (contact.getType() == Contact.Type.SPY) {
                contacts.add(contact);
            }
        }

        return contacts;
    }

    public List<Contact> getContactsToTrack() {
        List<Contact> contacts = new ArrayList<Contact>();

        for(Contact contact : getAllContacts()) {

            if (contact.getType() == Contact.Type.TRACK) {
                contacts.add(contact);
            }
        }

        return contacts;
    }

    private Contact cursorToContact(Cursor cursor) {
        Contact contact = new Contact();
        contact.setId(cursor.getLong(0));
        contact.setNumber(cursor.getString(1));
        contact.setType(Contact.Type.fromString(cursor.getString(2)));
        contact.setName(cursor.getString(3));
        return contact;
    }
}

