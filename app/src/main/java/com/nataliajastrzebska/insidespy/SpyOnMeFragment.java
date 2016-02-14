package com.nataliajastrzebska.insidespy;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nataliajastrzebska.insidespy.Contact.Contact;
import com.nataliajastrzebska.insidespy.Contact.ContactDataSource;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nataliajastrzebska on 09/02/16.
 */
public class SpyOnMeFragment extends Fragment {

    @Bind(R.id.listView_contacts)
    ListView listView;

    ContactDataSource dataSource;
    ArrayAdapter<Contact> adapter;
    Context context;

    private void openDataSource() {
        dataSource = new ContactDataSource(context);
        dataSource.open();
    }

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
        View view = inflater.inflate(R.layout.fragment_spy_on_me, container, false);
        ButterKnife.bind(view);

        listView = (ListView) view.findViewById(R.id.listView_contacts);
        setupContactList();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    private void setupContactList(){
        openDataSource();

        List<Contact> contactList = dataSource.getContactsSpyOnMe();
        adapter = new ArrayAdapter(getContext(), R.layout.contact_list_item, contactList);
        listView.setAdapter(adapter);

        dataSource.close();
    }
}
