package ru.javabegin.training.android6.finance.adapters;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;

import ru.javabegin.training.android6.finance.activities.edit.EditStorageActivity;
import ru.javabegin.training.android6.finance.adapters.abstracts.TreeNodeListAdapter;
import ru.javabegin.training.android6.finance.adapters.holders.StorageViewHolder;
import ru.javabegin.training.android6.finance.core.database.Initializer;
import ru.javabegin.training.android6.finance.core.exceptions.CurrencyException;
import ru.javabegin.training.android6.finance.core.impls.DefaultStorage;
import ru.javabegin.training.android6.finance.core.interfaces.Storage;
import ru.javabegin.training.android6.finance.utils.AppContext;
import ru.javabegin.training.android6.finance.utils.CurrencyUtils;

// адаптер для списка счетов
public class StorageNodeAdapter extends TreeNodeListAdapter<Storage, StorageViewHolder> {

    private static final String TAG = StorageNodeAdapter.class.getName();

    public StorageNodeAdapter(int mode) {
        super(mode, Initializer.getStorageSync());
    }


    @Override
    public StorageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        super.onCreateViewHolder(parent, viewType);// обязательно нужно вызывать
        return new StorageViewHolder(itemView);
    }


    @Override
    protected void openActivityOnClick(Storage storage, int requestCode) {

        Storage s = null;

        switch (requestCode) {
            case AppContext.REQUEST_NODE_ADD_CHILD: // для редактирования создаем новый пустой объект
                s = new DefaultStorage();
                break;

            default:
                s = storage;
        }
        // если будет передан любой другой requestCode - тогда просто передавать объект в intent как есть
        Intent intent = new Intent(activityContext, EditStorageActivity.class); // какой акивити хотим вызвать
        intent.putExtra(AppContext.NODE_OBJECT, s); // помещаем выбранный объект node для передачи в активити
        activityContext.startActivityForResult(intent, requestCode, ActivityOptionsCompat.makeSceneTransitionAnimation(activityContext).toBundle()); // устанавливаем анимацию перехода

    }


    // этот метод устанавливает только специфичные данные для элемента списка
    @Override
    public void onBindViewHolder(StorageViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);// не забывать вызывать, чтобы заполнить общие компоненты

        final Storage storage = adapterList.get(position);// определяем выбранный пункт

        try {
            BigDecimal approxAmount = storage.getApproxAmount(CurrencyUtils.defaultCurrency);
            if (approxAmount != null && !approxAmount.equals(BigDecimal.ZERO)) {
                approxAmount = approxAmount.setScale(0, BigDecimal.ROUND_UP);
                holder.tvAmount.setText("~ " + String.valueOf(approxAmount) + " " + CurrencyUtils.defaultCurrency.getSymbol());
                holder.tvAmount.setVisibility(View.VISIBLE);
            }else{
                holder.tvAmount.setVisibility(View.INVISIBLE);
            }

        } catch (CurrencyException e) {
            Log.e(TAG, e.getMessage());
        }
    }


}
