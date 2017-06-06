package ru.javabegin.training.android6.finance.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.javabegin.training.android6.finance.adapters.abstracts.TreeNodeListAdapter;
import ru.javabegin.training.android6.finance.core.interfaces.TreeNode;
import ru.javabegin.training.android6.finance.listeners.TreeNodeActionListener;


public class TreeNodeListFragment<T extends TreeNode> extends BaseNodeListFragment<T, TreeNodeListAdapter, TreeNodeActionListener> {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState); // потом уже вызывать родительский метод
        return view;
    }

    public void insertChildNode(TreeNode node) {
        adapter.insertChildNode(node);
    }


}
