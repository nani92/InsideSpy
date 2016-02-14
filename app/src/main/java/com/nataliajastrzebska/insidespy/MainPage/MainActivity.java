package com.nataliajastrzebska.insidespy.MainPage;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.nataliajastrzebska.insidespy.Contact.Contact;
import com.nataliajastrzebska.insidespy.Contact.ContactDataSource;
import com.nataliajastrzebska.insidespy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements AddNumberDialogFragment.AddNumberInterface {

    MainPagerAdapter mainPagerAdapter;

    @Bind(R.id.vpPager)
    ViewPager viewPager;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mainPagerAdapter);
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

    @OnClick(R.id.fab_add)
    public void onFabAddClicked() {
        AddNumberDialogFragment addNumberFragment = new AddNumberDialogFragment();
        addNumberFragment.show(getSupportFragmentManager(),"");
    }

    @Override
    public void addedNumber(Contact contact) {
        ContactDataSource contactDataSource = new ContactDataSource(this);
        contactDataSource.open();
        contactDataSource.createContact(contact);
        contactDataSource.close();

        mainPagerAdapter.notifyDataSetChanged();
    }
}