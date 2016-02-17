package com.nataliajastrzebska.insidespy.MainPage.TrackOthers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nataliajastrzebska.insidespy.Contact.Contact;
import com.nataliajastrzebska.insidespy.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nataliajastrzebska on 14/02/16.
 */
public class TrackOthersListViewAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private List<Contact> contacts;
    private OnSendMessageClick onSendMessageClick;

    public TrackOthersListViewAdapter(Context context, List<Contact> contacts, int resources) {
        super(context, resources, contacts);

        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_track_others_contact, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.contactName.setText(contacts.get(position).getName());
        holder.contactNumber.setText(contacts.get(position).getNumber());

        holder.contactRemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onSendMessageClick.onSendClicked(position);
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        @Bind(R.id.trackOthersSendMessage)
        ImageView contactRemove;
        @Bind(R.id.trackOthersContactName)
        TextView contactName;
        @Bind(R.id.trackOthersContactNumber)
        TextView contactNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setOnSendMessageClick(OnSendMessageClick onSendMessageClick) {
        this.onSendMessageClick = onSendMessageClick;
    }

    public interface OnSendMessageClick {
        void onSendClicked(int position);
    }
}
