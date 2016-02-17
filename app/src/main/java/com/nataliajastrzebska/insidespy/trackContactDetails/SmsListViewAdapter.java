package com.nataliajastrzebska.insidespy.trackContactDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.nataliajastrzebska.insidespy.R;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nataliajastrzebska on 14/02/16.
 */
public class SmsListViewAdapter extends ArrayAdapter<Sms> {

    @Bind(R.id.smsItemBody) TextView smsBody;
    @Bind(R.id.smsItemDate) TextView smsDate;

    private Context context;
    private List<Sms> smsList;

    public SmsListViewAdapter(Context context, List<Sms> smsList, int resources) {
        super(context, resources, smsList);

        this.context = context;
        this.smsList = smsList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.list_item_sms, parent, false);
        ButterKnife.bind(this, convertView);

        smsBody.setText(smsList.get(position).getBody());
        smsDate.setText(smsList.get(position).getFormatDate());

        return convertView;
    }

    @Override
    public int getCount() {
        return smsList.size();
    }
}
