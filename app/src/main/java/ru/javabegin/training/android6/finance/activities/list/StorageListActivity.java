package ru.javabegin.training.android6.finance.activities.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;

import ru.javabegin.training.android6.finance.R;
import ru.javabegin.training.android6.finance.activities.abstracts.TreeListActivity;
import ru.javabegin.training.android6.finance.activities.edit.EditStorageActivity;
import ru.javabegin.training.android6.finance.adapters.StorageNodeAdapter;
import ru.javabegin.training.android6.finance.core.database.Initializer;
import ru.javabegin.training.android6.finance.core.exceptions.CurrencyException;
import ru.javabegin.training.android6.finance.core.impls.DefaultStorage;
import ru.javabegin.training.android6.finance.core.interfaces.Storage;
import ru.javabegin.training.android6.finance.fragments.TreeNodeListFragment;
import ru.javabegin.training.android6.finance.utils.AppContext;
import ru.javabegin.training.android6.finance.utils.CurrencyUtils;
import ru.javabegin.training.android6.finance.utils.LocaleUtils;

// список счетов
public class StorageListActivity extends TreeListActivity<Storage> {

    protected static final String TAG = StorageListActivity.class.getName();


    private TextView tvTotalBalance;


    public StorageListActivity() {

        TreeNodeListFragment<Storage> fragment = new TreeNodeListFragment<>();


        // обязательно надо проинициализировать
        init(fragment, R.layout.activity_storage_list, R.id.tlb_tree_list_actions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // обязательно нужно вызывать

        tvTotalBalance = (TextView) findViewById(R.id.tv_total_balance);

        refreshTotalBalance();

        fragment.setAdapter(new StorageNodeAdapter(mode));

        setToolbarTitle(getResources().getString(R.string.storages));

        initListeners();


    }

    @Override
    protected void showRootNodes() {
        super.showRootNodes();

        fragment.refreshList(Initializer.getStorageSync().getAll());
    }

    protected void initListeners() {
        super.initListeners();// обязательно нужно вызывать

        // при нажатии на кнопку добавления элемента
        iconAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Storage storage = new DefaultStorage();
                try {
                    storage.addCurrency(CurrencyUtils.defaultCurrency, BigDecimal.ZERO);// при создании нового storage - автоматически прописваем валюту по-умолчанию
                } catch (CurrencyException e) {
                    Log.e(TAG, e.getMessage());
                }

                Intent intent = new Intent(StorageListActivity.this, EditStorageActivity.class); // какой акивити хотим вызвать
                intent.putExtra(AppContext.NODE_OBJECT, storage); // помещаем выбранный объект node для передачи в активити
                startActivityForResult(intent, AppContext.REQUEST_NODE_ADD, ActivityOptionsCompat.makeSceneTransitionAnimation(StorageListActivity.this).toBundle()); // REQUEST_NODE_EDIT - индикатор, кто является инициатором); // REQUEST_NODE_ADD - индикатор, кто является инициатором

            }
        });

    }

    // обновить общую сумму по всем счетам
    public void refreshTotalBalance() {
        if (mode == AppContext.EDIT_MODE) {
            if (!Initializer.getStorageSync().getAll().isEmpty()) {
                tvTotalBalance.setVisibility(View.VISIBLE); // показываем элемент только при обычном просмотре справочников (не из редактируемой операции)
                tvTotalBalance.setText(getResources().getString(R.string.total_balance) + " ~ " +  Initializer.getStorageSync().getTotalBalance(CurrencyUtils.defaultCurrency).setScale(0, BigDecimal.ROUND_UP).toString() + " " + CurrencyUtils.defaultCurrency.getSymbol(LocaleUtils.defaultLocale));
            } else {
                tvTotalBalance.setVisibility(View.GONE); // если нет доступных счетов - не показываем баланс
            }
        }
    }

    // при добавлении, изменении удаления любого счета - сразу обновляем общий баланс по всем счетам
    @Override
    public void onAdd(Storage node) {
        super.onAdd(node);
        refreshTotalBalance();
    }

    @Override
    public void onDelete(Storage node) {
        super.onDelete(node);
        refreshTotalBalance();
    }

    @Override
    public void onUpdate(Storage node) {
        super.onUpdate(node);
        refreshTotalBalance();
    }
}
