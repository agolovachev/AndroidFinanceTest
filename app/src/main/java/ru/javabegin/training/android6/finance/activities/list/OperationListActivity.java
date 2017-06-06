package ru.javabegin.training.android6.finance.activities.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.Button;

import ru.javabegin.training.android6.finance.R;
import ru.javabegin.training.android6.finance.activities.abstracts.BaseListActivity;
import ru.javabegin.training.android6.finance.activities.edit.operation.EditConvertOperationActivity;
import ru.javabegin.training.android6.finance.activities.edit.operation.EditIncomeOperationActivity;
import ru.javabegin.training.android6.finance.activities.edit.operation.EditOutcomeOperationActivity;
import ru.javabegin.training.android6.finance.activities.edit.operation.EditTransferOperationActivity;
import ru.javabegin.training.android6.finance.adapters.OperationListAdapter;
import ru.javabegin.training.android6.finance.core.impls.operations.ConvertOperation;
import ru.javabegin.training.android6.finance.core.impls.operations.IncomeOperation;
import ru.javabegin.training.android6.finance.core.impls.operations.OutcomeOperation;
import ru.javabegin.training.android6.finance.core.impls.operations.TransferOperation;
import ru.javabegin.training.android6.finance.core.interfaces.Operation;
import ru.javabegin.training.android6.finance.fragments.BaseNodeListFragment;
import ru.javabegin.training.android6.finance.listeners.BaseNodeActionListener;
import ru.javabegin.training.android6.finance.transitions.TransitionSlide;
import ru.javabegin.training.android6.finance.utils.AppContext;

// содержит список операций
public class OperationListActivity extends BaseListActivity<Operation, BaseNodeListFragment> {

    private Button btnAddIncome;
    private Button btnAddOutcome;
    private Button btnAddTransfer;
    private Button btnAddConvert;


    public OperationListActivity() {

        BaseNodeListFragment<Operation, OperationListAdapter, BaseNodeActionListener> fragment = new BaseNodeListFragment<>();
        fragment.setAdapter(new OperationListAdapter());

        // обязательно надо проинициализировать
        init(fragment, R.layout.activity_operation_list, R.id.tlb_operation_list_actions);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarTitle(getResources().getString(R.string.operations));

        setTransition(new TransitionSlide(this, TransitionSlide.Direction.RIGHT_LEFT));

        createDrawer(toolbar);


        btnAddIncome = (Button) findViewById(R.id.btn_add_income);
        btnAddOutcome = (Button) findViewById(R.id.btn_add_outcome);
        btnAddTransfer = (Button) findViewById(R.id.btn_add_transfer);
        btnAddConvert = (Button) findViewById(R.id.btn_add_convert);


        // каждая кнопка создает свой операцию нужного типа
        btnAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAddOperationActivity(EditIncomeOperationActivity.class, new IncomeOperation());
            }
        });

        btnAddOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAddOperationActivity(EditOutcomeOperationActivity.class, new OutcomeOperation());
            }
        });


        btnAddTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAddOperationActivity(EditTransferOperationActivity.class, new TransferOperation());
            }
        });


        btnAddConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAddOperationActivity(EditConvertOperationActivity.class, new ConvertOperation());
            }
        });


    }




    // какой активити хотим вызвать для добавления новой операции (в зависимости от типа операции)
    private void runAddOperationActivity(Class activityClass, Operation operation) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra(AppContext.NODE_OBJECT, operation); // помещаем выбранный объект operation для передачи в активити
        intent.putExtra(AppContext.OPERATION_ACTION, AppContext.OPERATION_ADD);
        startActivityForResult(intent, AppContext.REQUEST_NODE_ADD, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }





}


