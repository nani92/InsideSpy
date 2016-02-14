package com.nataliajastrzebska.insidespy;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nataliajastrzebska.insidespy.Contact.Contact;
import com.nataliajastrzebska.insidespy.Contact.ContactDataSource;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nataliajastrzebska on 13/02/16.
 */
public class ContactDetailsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.contactDetails_name)
    TextView tv_contactName;

    @Bind(R.id.listView_sms)
    ListView listView;

    Contact contact;
    List<String> listOfSms;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        ButterKnife.bind(this);

        setupContact();
        tv_contactName.setText(contact.getName().toString());

        populateSMSList();

        adapter = new ArrayAdapter(this, R.layout.contact_list_item, listOfSms);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    public void populateSMSList() {
        listOfSms = new ArrayList<>();
        Uri mSmsinboxQueryUri = Uri.parse("content://sms/inbox");
        Cursor cursor1 = getContentResolver().query(mSmsinboxQueryUri,
                new String[] { "_id", "thread_id", "address", "person", "date",
                        "body", "type" }, null, null, null);
        startManagingCursor(cursor1);
        String[] columns = new String[] { "address", "person", "date", "body","type" };
        if (cursor1.getCount() > 0) {
            String count = Integer.toString(cursor1.getCount());
            Log.e("Count",count);
            while (cursor1.moveToNext()){
                String address = cursor1.getString(cursor1.getColumnIndex(columns[0]));
                String name = cursor1.getString(cursor1.getColumnIndex(columns[1]));
                String date = cursor1.getString(cursor1.getColumnIndex(columns[2]));
                String msg = cursor1.getString(cursor1.getColumnIndex(columns[3]));
                String type = cursor1.getString(cursor1.getColumnIndex(columns[4]));

                if(address.equals(contact.getNumber()) && msg.startsWith("spyAns_getgps()")) {
                    listOfSms.add(msg);
                }
            }
        }

    }

    private void setupContact() {
        Long id = getIntent().getExtras().getLong("id");
        ContactDataSource dataSource = new ContactDataSource(this);
        dataSource.open();
        this.contact = dataSource.getContact(id);

        dataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }

    private float retrieveLatFromAnswer(String answer){
        String[] parts = answer.split("_");
        return Float.parseFloat(parts[2].split(";")[0]);
    }
    private float retrieveLonFromAnswer(String answer){
        String[] parts = answer.split("_");
        return Float.parseFloat(parts[2].split(";")[1]);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent service = new Intent(this, LocationService.class);
        service.putExtra("lat", retrieveLatFromAnswer(adapter.getItem(position)));
        service.putExtra("lon", retrieveLonFromAnswer(adapter.getItem(position)));
        startService(service);
    }
}
