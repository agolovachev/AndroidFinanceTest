package ru.javabegin.training.android6.finance.core.dao.interfaces;

import java.util.List;

import ru.javabegin.training.android6.finance.core.enums.OperationType;
import ru.javabegin.training.android6.finance.core.interfaces.Operation;

public interface OperationDAO extends CommonDAO<Operation> {

    List<Operation> getList(OperationType operationType);// получить список операций определенного типа

}
