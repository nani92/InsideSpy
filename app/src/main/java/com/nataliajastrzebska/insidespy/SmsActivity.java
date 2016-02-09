package com.nataliajastrzebska.insidespy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SmsActivity extends Activity {

    @Bind(R.id.btn_add)
    Button buttonAdd;
    @Bind(R.id.listView_contacts)
    ListView listView;
    @Bind(R.id.et_inputNumber)
    EditText inputNumber;


    ContactDataSource dataSource;
    ArrayAdapter<Contact> adapter;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dataSource = new ContactDataSource(this);
        dataSource.open();

        List<Contact> contactList = dataSource.getAllContacts();
        adapter = new ArrayAdapter<Contact>(this, R.layout.contact_list_item, contactList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        dataSource.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        dataSource.close();
    }

    @OnClick(R.id.btn_add)
    public void onButtonOkClicked() {
        dataSource.createContact(inputNumber.getText().toString());
        adapter.notifyDataSetChanged();
    }

    @OnTextChanged(R.id.et_inputNumber)
    public void onInputNumber() {
        buttonAdd.setEnabled(false);

        if (inputNumber.getText().toString().length() > 0) {
            buttonAdd.setEnabled(true);
        }
    }

}