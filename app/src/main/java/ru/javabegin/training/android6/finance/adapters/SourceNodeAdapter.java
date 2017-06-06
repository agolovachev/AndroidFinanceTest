package ru.javabegin.training.android6.finance.adapters;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.ViewGroup;

import java.util.List;

import ru.javabegin.training.android6.finance.activities.edit.EditSourceActivity;
import ru.javabegin.training.android6.finance.adapters.abstracts.TreeNodeListAdapter;
import ru.javabegin.training.android6.finance.adapters.holders.SourceViewHolder;
import ru.javabegin.training.android6.finance.core.database.Initializer;
import ru.javabegin.training.android6.finance.core.impls.DefaultSource;
import ru.javabegin.training.android6.finance.core.interfaces.Source;
import ru.javabegin.training.android6.finance.utils.AppContext;

// адаптер для заполнения списка категорий
public class SourceNodeAdapter extends TreeNodeListAdapter<Source, SourceViewHolder> {

    private static final String TAG = SourceNodeAdapter.class.getName();

    public SourceNodeAdapter(int mode) {
        super(mode, Initializer.getSourceSync(), Initializer.getSourceSync().getAll());
    }

    public SourceNodeAdapter(int mode, List<Source> initialList) {
        super(mode, Initializer.getSourceSync(), initialList);

    }


    @Override
    public SourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        super.onCreateViewHolder(parent, viewType);// обязательно нужно вызывать
        return new SourceViewHolder(itemView);
    }

    @Override
    protected void openActivityOnClick(Source source, int requestCode) {

        Source s = null;

        // если будет передан любой другой requestCode - тогда просто передавать объект в intent как есть
        switch (requestCode){
            case AppContext.REQUEST_NODE_ADD_CHILD: // для редактирования создаем новый пустой объект
                s = new DefaultSource();
                s.setOperationType(source.getOperationType());
                break;

            default:s=source;
        }



        Intent intent = new Intent(activityContext, EditSourceActivity.class); // какой акивити хотим вызвать
        intent.putExtra(AppContext.NODE_OBJECT, s); // помещаем выбранный объект node для передачи в активити
        activityContext.startActivityForResult(intent, requestCode, ActivityOptionsCompat.makeSceneTransitionAnimation(activityContext).toBundle()); // устанавливаем анимацию перехода

    }




}
