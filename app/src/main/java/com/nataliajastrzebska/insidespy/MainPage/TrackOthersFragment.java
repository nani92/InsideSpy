package com.nataliajastrzebska.insidespy.MainPage;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nataliajastrzebska.insidespy.Contact.Contact;
import com.nataliajastrzebska.insidespy.Contact.ContactDataSource;
import com.nataliajastrzebska.insidespy.ContactDetailsActivity;
import com.nataliajastrzebska.insidespy.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nataliajastrzebska on 11/02/16.
 */
public class TrackOthersFragment extends Fragment {

    @Bind(R.id.listView_contactsToTrack)
    ListView listView;

    ContactDataSource dataSource;
    ArrayAdapter<Contact> adapter;
    Context context;

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


        listView = (ListView) view.findViewById(R.id.listView_contactsToTrack);
        setupContactList();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    private void openDataSource() {
        dataSource = new ContactDataSource(context);
        dataSource.open();
    }
    
    private void setupContactList(){
        openDataSource();

        List<Contact> contactList = dataSource.getContactsToTrack();
        adapter = new ArrayAdapter(getContext(), R.layout.contact_list_item, contactList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ContactDetailsActivity.class);
                intent.putExtra("id", adapter.getItem(position).getId());
                startActivity(intent);
            }
        });
        dataSource.close();
    }
}
