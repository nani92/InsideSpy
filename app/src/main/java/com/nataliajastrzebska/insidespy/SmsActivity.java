package com.nataliajastrzebska.insidespy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmsActivity extends AppCompatActivity implements AddNumberFragment.AddNumberInterface {

    TrustedNumberFragment trustedNumberFragment;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        trustedNumberFragment = new TrustedNumberFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerTrustedNumber, trustedNumberFragment).commit();
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
        AddNumberFragment addNumberFragment = new AddNumberFragment();
        addNumberFragment.show(getSupportFragmentManager(),"");
    }

    @Override
    public void addedNumber(Contact contact) {
        trustedNumberFragment.addContact(contact);
    }
}