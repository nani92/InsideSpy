package com.nataliajastrzebska.insidespy.MainPage.TrackOthers;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nataliajastrzebska.insidespy.Code.Code;
import com.nataliajastrzebska.insidespy.Contact.Contact;
import com.nataliajastrzebska.insidespy.Contact.ContactDataSource;
import com.nataliajastrzebska.insidespy.ContactDetailsActivity;
import com.nataliajastrzebska.insidespy.R;
import com.nataliajastrzebska.insidespy.helpers.SmsBuilder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nataliajastrzebska on 11/02/16.
 */
public class TrackOthersFragment extends Fragment implements TrackOthersListViewAdapter.OnSendMessageClick{

    @Bind(R.id.listViewContactsToTrack)
    ListView listView;

    private ContactDataSource dataSource;
    private TrackOthersListViewAdapter adapter;
    private Context context;
    private List<Contact> contactList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_others, container, false);
        ButterKnife.bind(view);

        this.listView = (ListView) view.findViewById(R.id.listViewContactsToTrack);
        setupContactList();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    private void openDataSource() {
        this.dataSource = new ContactDataSource(context);
        this.dataSource.open();
    }
    
    private void setupContactList(){
        openDataSource();

        this.contactList = dataSource.getContactsToTrack();
        this.adapter = new TrackOthersListViewAdapter(getContext(), this.contactList, R.layout.list_item_track_others_contact);
        this.adapter.setOnSendMessageClick(this);
        this.listView.setAdapter(adapter);
        setOnItemClickListenerForListView();

        dataSource.close();
    }

    private void setOnItemClickListenerForListView() {
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayContactDetailsActivity(position);
            }
        });
    }

    private void displayContactDetailsActivity(int position) {
        Intent intent = new Intent(getContext(), ContactDetailsActivity.class);
        intent.putExtra(ContactDetailsActivity.CONTACT_ID, adapter.getItem(position).getId());
        startActivity(intent);
    }

    @Override
    public void onSendClicked(int position) {
        Toast.makeText(getContext(), getResources().getString(R.string.sending_sms_code), Toast.LENGTH_LONG).show();
        SmsBuilder.sendRequestCodeSms(contactList.get(position).getNumber(), Code.GETGPS);
    }
}
