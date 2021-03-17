package org.hubjs.demo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.hubjs.sdk.dispatcher.Packet;
import org.hubjs.sdk.extra.HubjsApplication;
import org.hubjs.sdk.extra.TrackHelper;

import java.util.ArrayList;
import java.util.Collections;

import timber.log.Timber;


public class SettingsActivity extends Activity {

    private void refreshUI(final Activity settingsActivity) {
        // auto track button
        Button button = (Button) findViewById(R.id.bindtoapp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackHelper.track().screens(getApplication()).with(((HubjsApplication) getApplication()).getTracker());
            }
        });

        // Dry run
        CheckBox dryRun = (CheckBox) findViewById(R.id.dryRunCheckbox);
        dryRun.setChecked(((HubjsApplication) getApplication()).getTracker().getDryRunTarget() != null);
        dryRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HubjsApplication) getApplication()).getTracker().setDryRunTarget(((CheckBox) v).isChecked() ? Collections.synchronizedList(new ArrayList<Packet>()) : null);
            }
        });

        // out out
        CheckBox optOut = (CheckBox) findViewById(R.id.optOutCheckbox);
        optOut.setChecked(((HubjsApplication) getApplication()).getTracker().isOptOut());
        optOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HubjsApplication) getApplication()).getTracker().setOptOut(((CheckBox) v).isChecked());
            }
        });

        // dispatch interval
        EditText input = (EditText) findViewById(R.id.dispatchIntervallInput);
        input.setText(Long.toString(
                ((HubjsApplication) getApplication()).getTracker().getDispatchInterval()
        ));
        input.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        try {
                            int interval = Integer.valueOf(charSequence.toString().trim());
                            ((HubjsApplication) getApplication()).getTracker()
                                    .setDispatchInterval(interval);
                        } catch (NumberFormatException e) {
                            Timber.d("not a number: %s", charSequence.toString());
                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                }

        );

        //session Timeout Input
        input = (EditText) findViewById(R.id.sessionTimeoutInput);
        input.setText(Long.toString(
                (((HubjsApplication) getApplication()).getTracker().getSessionTimeout() / 60000)
        ));
        input.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        try {
                            int timeoutMin = Integer.valueOf(charSequence.toString().trim());
                            timeoutMin = Math.abs(timeoutMin);
                            ((HubjsApplication) getApplication()).getTracker()
                                    .setSessionTimeout(timeoutMin * 60);
                        } catch (NumberFormatException e) {
                            ((EditText) settingsActivity.findViewById(R.id.sessionTimeoutInput)).setText("30");
                            Timber.d("not a number: %s", charSequence.toString());
                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                }

        );

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        refreshUI(this);
    }

}
