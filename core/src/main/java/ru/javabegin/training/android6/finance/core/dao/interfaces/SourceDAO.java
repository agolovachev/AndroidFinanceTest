package ru.javabegin.training.android6.finance.core.dao.interfaces;

import java.util.List;

import ru.javabegin.training.android6.finance.core.enums.OperationType;
import ru.javabegin.training.android6.finance.core.interfaces.Source;

public interface SourceDAO extends CommonDAO<Source> {

    List<Source> getList(OperationType operationType);// получить список корневых элементов деревьев для определенного типа операции
    int getRefCount(Source source);


}
