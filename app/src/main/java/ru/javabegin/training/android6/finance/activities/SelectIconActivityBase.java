package ru.javabegin.training.android6.finance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import ru.javabegin.training.android6.finance.R;
import ru.javabegin.training.android6.finance.transitions.TransitionSlide;
import ru.javabegin.training.android6.finance.core.interfaces.TreeNode;
import ru.javabegin.training.android6.finance.fragments.IconListFragment;
import ru.javabegin.training.android6.finance.utils.AppContext;

// активити, с помощью которого выбираем иконку для справочных значений
public class SelectIconActivityBase<T extends TreeNode> extends AppCompatActivity implements IconListFragment.SelectIconListener {// VerticalAnimationActivity устанавливает анимацию при открытии или закрытии {

    private ImageView imgClose;

    private IconListFragment iconListFragment;

    private TransitionSlide transition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_icon);

        transition = new TransitionSlide(this, TransitionSlide.Direction.BOTTOM_TOP);

        initToolbar();
        initFragment();

    }

    public void setTransition(TransitionSlide transition) {
        this.transition = transition;
    }

    private void initFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        iconListFragment = new IconListFragment();
        fragmentTransaction.replace(R.id.icon_list_fragment, iconListFragment);
        fragmentTransaction.commit();
    }



    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tlb_no_save_btn);
        setSupportActionBar(toolbar);

        imgClose = (ImageView) findViewById(R.id.img_close_select_icon);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition.finishWithTransition();
            }
        });
    }


    @Override
    public void onIconSelected(String name) {

        Intent intent = new Intent();
        intent.putExtra(AppContext.ICON_NAME, name);// обратно передаем имя выбранной иконки
        setResult(RESULT_OK, intent);

        transition.finishWithTransition();

    }
}
