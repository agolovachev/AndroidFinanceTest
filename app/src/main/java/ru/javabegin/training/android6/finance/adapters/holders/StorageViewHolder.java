package ru.javabegin.training.android6.finance.adapters.holders;

import android.view.View;
import android.widget.TextView;

import ru.javabegin.training.android6.finance.R;

// описывает компоненты, которые являются специфичными для списка счетов
public class StorageViewHolder extends TreeViewHolder {


    public final TextView tvAmount;


    public StorageViewHolder(View view) {
        super(view);
        tvAmount = (TextView) view.findViewById(R.id.tv_node_amount);
        tvAmount.setVisibility(View.VISIBLE);

    }

}