package com.nataliajastrzebska.insidespy.mainpage.SpyOnMe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nataliajastrzebska.insidespy.contact.Contact;
import com.nataliajastrzebska.insidespy.R;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nataliajastrzebska on 14/02/16.
 */
public class SpyOnMeListViewAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private List<Contact> contacts;
    private OnRemoveItemClick onRemoveItemClick;

    public SpyOnMeListViewAdapter(Context context, List<Contact> contacts, int resources) {
        super(context, resources, contacts);

        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_spy_on_me_contact, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.contactName.setText(contacts.get(position).getName());
        holder.contactNumber.setText(contacts.get(position).getNumber());

        holder.contactRemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onRemoveItemClick.onRemoveClicked(position);
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        @Bind(R.id.spyOnMeContactRemove)
        ImageView contactRemove;
        @Bind(R.id.spyOnMeContactName)
        TextView contactName;
        @Bind(R.id.spyOnMeContactNumber)
        TextView contactNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setOnRemoveItemClick(OnRemoveItemClick onRemoveItemClick) {
        this.onRemoveItemClick = onRemoveItemClick;
    }

    public interface OnRemoveItemClick {
        void onRemoveClicked(int position);
    }
}
