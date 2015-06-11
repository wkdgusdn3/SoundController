package com.wkdgusdn3.soundcontroller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity {

    CheckBox checkBox_musicPlay;
    CheckBox checkBox_soundUp;
    CheckBox checkBox_soundDown;
    CheckBox checkBox_soundMute;
    CheckBox checkBox_icon;
    Button button_apply;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.wkdgusdn3.soundcontroller.R.layout.activity_main);

        InfoManager.setData(getApplicationContext());

        setVariable();
        setView();
        setCheckBox();
        setClickListener();

        startService();

    }

    void setVariable() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();
    }

    void setView() {
        checkBox_musicPlay = (CheckBox)findViewById(com.wkdgusdn3.soundcontroller.R.id.main_musicPlayCheckBox);
        checkBox_soundUp = (CheckBox)findViewById(com.wkdgusdn3.soundcontroller.R.id.main_soundUpCheckBox);
        checkBox_soundDown = (CheckBox)findViewById(com.wkdgusdn3.soundcontroller.R.id.main_soundDownCheckBox);
        checkBox_soundMute = (CheckBox)findViewById(com.wkdgusdn3.soundcontroller.R.id.main_soundMuteCheckBox);
        checkBox_icon = (CheckBox)findViewById(com.wkdgusdn3.soundcontroller.R.id.main_iconCheckBox);
        button_apply = (Button)findViewById(com.wkdgusdn3.soundcontroller.R.id.main_apply);
    }

    void setCheckBox() {
        if(InfoManager.boolean_musicPlay) {
            checkBox_musicPlay.setChecked(true);
        }
        if(InfoManager.boolean_soundUp) {
            checkBox_soundUp.setChecked(true);
        }
        if(InfoManager.boolean_soundDown) {
            checkBox_soundDown.setChecked(true);
        }
        if(InfoManager.boolean_soundMute) {
            checkBox_soundMute.setChecked(true);
        }
        if(InfoManager.boolean_icon) {
            checkBox_icon.setChecked(true);
        }
    }

    void setClickListener() {

        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("MUSICPLAY", checkBox_musicPlay.isChecked());
                editor.putBoolean("SOUNDUP", checkBox_soundUp.isChecked());
                editor.putBoolean("SOUNDDOWN", checkBox_soundDown.isChecked());
                editor.putBoolean("SOUNDMUTE", checkBox_soundMute.isChecked());
                editor.putBoolean("ICON", checkBox_icon.isChecked());
                editor.commit();

                InfoManager.setData(getApplicationContext());

                startService();
            }
        });
    }

    void startService() {
        Intent soundServiceIntent = new Intent(getApplicationContext(), SoundService.class);

        stopService(soundServiceIntent);
        startService(soundServiceIntent);
    }
}