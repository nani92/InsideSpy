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

import com.nataliajastrzebska.insidespy.Code.CodeGet;
import com.nataliajastrzebska.insidespy.Code.CodeGetGps;
import com.nataliajastrzebska.insidespy.Contact.Contact;
import com.nataliajastrzebska.insidespy.Contact.ContactDataSource;
import com.nataliajastrzebska.insidespy.Services.LocationGpsService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nataliajastrzebska on 13/02/16.
 */
public class ContactDetailsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final String SMS_COLUMN_NUMBER = "address";
    private final String SMS_COLUMN_DATE = "date";
    private final String SMS_COLUMN_MESSAGE = "body";

    private final String[] SMS_COLUMNS = new String[] {
            SMS_COLUMN_NUMBER, SMS_COLUMN_DATE, SMS_COLUMN_MESSAGE
    };

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

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfSms);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    public void populateSMSList() {
        listOfSms = new ArrayList<>();
        Uri smsInboxUri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(smsInboxUri, SMS_COLUMNS, null, null, null);
        if (cursor.getCount() > 0) {
            String count = Integer.toString(cursor.getCount());
            Log.e("Count", count);
            while (cursor.moveToNext()){
                String address = cursor.getString(cursor.getColumnIndex(SMS_COLUMNS[0]));
                String date = cursor.getString(cursor.getColumnIndex(SMS_COLUMNS[1]));
                String msg = cursor.getString(cursor.getColumnIndex(SMS_COLUMNS[2]));

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
        Intent service = new Intent(this, LocationGpsService.class);
        service.putExtra(CodeGetGps.EXTRA_LAT, retrieveLatFromAnswer(adapter.getItem(position)));
        service.putExtra(CodeGetGps.EXTRA_LON, retrieveLonFromAnswer(adapter.getItem(position)));

        startService(service);
    }

}
