package com.nataliajastrzebska.insidespy.trackContactDetails;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.nataliajastrzebska.insidespy.R;
import com.nataliajastrzebska.insidespy.codes.CodeGetGps;
import com.nataliajastrzebska.insidespy.contact.Contact;
import com.nataliajastrzebska.insidespy.contact.ContactDataSource;
import com.nataliajastrzebska.insidespy.helpers.SmsBuilder;
import com.nataliajastrzebska.insidespy.services.LocationGpsService;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nataliajastrzebska on 13/02/16.
 */
public class ContactDetailsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final String SMS_COLUMN_NUMBER = "address";
    private final String SMS_COLUMN_DATE = "date";
    private final String SMS_COLUMN_MESSAGE = "body";

    public static final String CONTACT_ID = "id";

    private final String[] SMS_COLUMNS = new String[] {
            SMS_COLUMN_NUMBER, SMS_COLUMN_DATE, SMS_COLUMN_MESSAGE
    };

    @Bind(R.id.contactDetailsName)
    TextView tv_contactName;
    @Bind(R.id.listViewSms)
    ListView listView;
    @Bind(R.id.removeTrackContact)
    ImageView removeTrackContact;

    private Contact contact;
    private List<Sms> listOfSms;
    private SmsListViewAdapter adapter;
    private ContactDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        ButterKnife.bind(this);

        setupContact();
        tv_contactName.setText(contact.getName().toString());

        populateSMSList();

        this.adapter = new SmsListViewAdapter(this, listOfSms, R.layout.list_item_sms);
        this.listView.setAdapter(this.adapter);
        this.listView.setOnItemClickListener(this);
    }

    public void populateSMSList() {
        this.listOfSms = new ArrayList<>();
        Uri smsInboxUri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(smsInboxUri, SMS_COLUMNS, null, null, null);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()){
                String address = cursor.getString(cursor.getColumnIndex(SMS_COLUMNS[0]));
                String date = cursor.getString(cursor.getColumnIndex(SMS_COLUMNS[1]));
                String msg = cursor.getString(cursor.getColumnIndex(SMS_COLUMNS[2]));

                if(address.equals(contact.getNumber()) && msg.startsWith(SmsBuilder.ANSWER_START)) {
                    listOfSms.add(new Sms(msg, date));
                }
            }
        }

    }

    private void setupContact() {
        Long id = getIntent().getExtras().getLong(CONTACT_ID);
        this.dataSource = new ContactDataSource(this);
        this.dataSource.open();
        this.contact = dataSource.getContact(id);

        this.dataSource.close();
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
        String[] parts = answer.split(SmsBuilder.PART_SEPARATOR);
        return Float.parseFloat(parts[2].split(SmsBuilder.DATA_SEPARATOR)[0]);
    }
    private float retrieveLonFromAnswer(String answer){
        String[] parts = answer.split(SmsBuilder.PART_SEPARATOR);
        return Float.parseFloat(parts[2].split(SmsBuilder.DATA_SEPARATOR)[1]);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent service = new Intent(this, LocationGpsService.class);
        service.putExtra(CodeGetGps.EXTRA_LAT, retrieveLatFromAnswer(adapter.getItem(position).toString()));
        service.putExtra(CodeGetGps.EXTRA_LON, retrieveLonFromAnswer(adapter.getItem(position).toString()));

        startService(service);
    }

    @OnClick(R.id.removeTrackContact)
    public void onRemoveClicked() {
        this.dataSource.open();
        this.dataSource.deleteContact(contact);
        this.dataSource.close();

        finish();
    }

}
