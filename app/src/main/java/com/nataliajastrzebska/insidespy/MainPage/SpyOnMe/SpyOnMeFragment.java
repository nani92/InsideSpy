package com.nataliajastrzebska.insidespy.mainpage.SpyOnMe;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nataliajastrzebska.insidespy.contact.Contact;
import com.nataliajastrzebska.insidespy.contact.ContactDataSource;
import com.nataliajastrzebska.insidespy.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nataliajastrzebska on 09/02/16.
 */
public class SpyOnMeFragment extends Fragment implements SpyOnMeListViewAdapter.OnRemoveItemClick{

    @Bind(R.id.listViewContactsSpyOnMe)
    ListView listView;

    private ContactDataSource dataSource;
    private SpyOnMeListViewAdapter adapter;
    private Context context;
    private List<Contact> contactList;

    private void openDataSource() {
        this.dataSource = new ContactDataSource(context);
        this.dataSource.open();
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
        ButterKnife.bind(this, view);

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

        this.contactList = dataSource.getContactsSpyOnMe();
        this.adapter = new SpyOnMeListViewAdapter(context, contactList, R.layout.list_item_spy_on_me_contact);
        this.adapter.setOnRemoveItemClick(this);
        this.listView.setAdapter(adapter);

        this.dataSource.close();
    }

    @Override
    public void onRemoveClicked(int position) {
        openDataSource();
        dataSource.deleteContact(this.contactList.get(position));
        this.dataSource.close();

        this.contactList.remove(position);
        this.adapter.notifyDataSetChanged();
    }
}
