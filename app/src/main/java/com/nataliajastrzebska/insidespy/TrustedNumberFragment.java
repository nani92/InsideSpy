package com.nataliajastrzebska.insidespy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nataliajastrzebska on 09/02/16.
 */
public class TrustedNumberFragment extends Fragment {

    @Bind(R.id.listView_contacts)
    ListView listView;

    ContactDataSource dataSource;
    ArrayAdapter<Contact> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSource = new ContactDataSource(getContext());
        dataSource.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trusted_numbers, container, false);
        ButterKnife.bind(view);

        listView = (ListView) view.findViewById(R.id.listView_contacts);
        setupContactList();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
        dataSource.close();
    }

    public void addContact(Contact contact) {
        dataSource.createContact(contact.getNumber());

        adapter.add(contact);
        adapter.notifyDataSetChanged();
    }

    private void setupContactList(){
        List<Contact> contactList = dataSource.getAllContacts();
        adapter = new ArrayAdapter(getContext(), R.layout.contact_list_item, contactList);
        listView.setAdapter(adapter);
    }
}
