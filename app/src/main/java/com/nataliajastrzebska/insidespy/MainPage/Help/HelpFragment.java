package com.nataliajastrzebska.insidespy.mainpage.Help;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.nataliajastrzebska.insidespy.R;
import com.nataliajastrzebska.insidespy.codes.Code;
import com.nataliajastrzebska.insidespy.helpers.SmsBuilder;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nataliajastrzebska on 19/02/16.
 */
public class HelpFragment extends Fragment {

    @Bind(R.id.codeGet)
    TextView textViewCodeGet;
    @Bind(R.id.codeGetAns)
    TextView textViewCodeGetAns;
    @Bind(R.id.codeGetGps)
    TextView textViewCodeGetGps;
    @Bind(R.id.codeGetGpsAns)
    TextView textViewCodeGetGpsAns;
    @Bind(R.id.codeVibrate)
    TextView textViewCodeVibrate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(this, view);

        setupTextViews();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    private void setupTextViews() {
        this.textViewCodeGet.setText(SmsBuilder.CODE_START + Code.GET.toString());
        this.textViewCodeGetAns.setText(SmsBuilder.ANSWER_START +Code.GET.toString());
        this.textViewCodeGetGps.setText(SmsBuilder.CODE_START + Code.GETGPS.toString());
        this.textViewCodeGetGpsAns.setText(SmsBuilder.ANSWER_START + Code.GETGPS.toString());
        this.textViewCodeVibrate.setText(SmsBuilder.CODE_START + Code.VIBRATE);
    }

}
