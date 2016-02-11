package com.nataliajastrzebska.insidespy;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

import android.support.v4.app.DialogFragment;
import android.widget.EditText;

/**
 * Created by nataliajastrzebska on 09/02/16.
 */
public class AddNumberFragment extends DialogFragment{

    AddNumberInterface addNumberInterface;
    AlertDialog dialog;

    @Bind(R.id.inputName)
    EditText inputName;
    @Bind(R.id.inputNumber)
    EditText inputNumber;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        addNumberInterface = (AddNumberInterface) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.fragment_add_number, null);
        ButterKnife.bind(this, view);

        createAlertDialog(view);

        return dialog;
    }

    private void createAlertDialog(View view){
        AlertDialog.Builder builder =  new  AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(getResources().getText(R.string.add_number_dialog_title));

        builder.setPositiveButton(getResources().getText(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        addTrustedNumber();
                        dialog.dismiss();
                    }
                }
        );

        builder.setNegativeButton(getResources().getText(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }
        );

        dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
            }
        });
    }

    private void addTrustedNumber() {
        Contact contact = new Contact();
        contact.setNumber(inputNumber.getText().toString());

        addNumberInterface.addedNumber(contact);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        ButterKnife.unbind(this);
    }

    private Boolean validate() {
        return isValidNumber() && isValidName();
    }

    private Boolean isValidNumber() {
        return !TextUtils.isEmpty(inputNumber.getText().toString()) && Patterns.PHONE.matcher(inputNumber.getText().toString()).matches();
    }

    private Boolean isValidName() {
        return !TextUtils.isEmpty(inputName.getText().toString());
    }

    @OnTextChanged(R.id.inputNumber)
    public void inputNamberTextChanged() {
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(validate());
    }

    @OnTextChanged(R.id.inputName)
    public void inpputNameTextChanged() {
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(validate());
    }

    public interface AddNumberInterface {
        void addedNumber(Contact contact);
    }
}
