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

    @Bind(R.id.smsBody)
    TextView smsBody;
    @Bind(R.id.smsDate)
    TextView smsDate;

    private Context context;
    private List<Sms> smses;

    public SmsListViewAdapter(Context context, List<Sms> smses, int resources) {
        super(context, resources, smses);

        this.context = context;
        this.smses = smses;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item_track_others_contact, parent, false);
        ButterKnife.bind(this, convertView);

        smsBody.setText(this.smses.get(position).getBody());
        smsDate.setText(this.smses.get(position).getDate());

        return convertView;
    }


}
