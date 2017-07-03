package ru.javabegin.training.android6.finance.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import ru.javabegin.training.android6.finance.R;
import ru.javabegin.training.android6.finance.activities.abstracts.BaseDrawerActivity;

public class HelpScreenActivity extends BaseDrawerActivity{

    Toolbar toolbar;
    TextView tvToolbarTitle;
    TextView tvHelpText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);

        toolbar = (Toolbar) findViewById(R.id.tlb_no_save_with_menu);
        createDrawer(toolbar);

        tvToolbarTitle = (TextView) findViewById(R.id.tv_title_of_tlb);
        tvHelpText = (TextView) findViewById(R.id.help_about_text);

        tvToolbarTitle.setText(R.string.help);
        tvHelpText.setText(R.string.help_about);
    }

}
