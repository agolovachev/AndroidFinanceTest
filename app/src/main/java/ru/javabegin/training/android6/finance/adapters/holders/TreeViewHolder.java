package ru.javabegin.training.android6.finance.adapters.holders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.javabegin.training.android6.finance.R;


// описывает компоненты, которые нужны для древовидного списка
public class TreeViewHolder extends BaseViewHolder {

    public final ImageView btnPopup;
    public final TextView tvChildCount;
    public final ViewGroup layoutShowChilds;

    public TreeViewHolder(View view) {
        super(view);

        // чтобы для каждого компонента не выполнять findViewById - сохраняем ссылки в константы
        btnPopup = (ImageView) view.findViewById(R.id.img_node_popup);
        tvChildCount = (TextView) view.findViewById(R.id.tv_node_child_count);
        layoutShowChilds = (ViewGroup) view.findViewById(R.id.layout_show_childs);

    }

}
