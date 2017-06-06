package ru.javabegin.training.android6.finance.core.interfaces;


import ru.javabegin.training.android6.finance.core.enums.OperationType;

public interface Source<T extends Source> extends TreeNode<T>{

    OperationType getOperationType();

    void setOperationType(OperationType type);

}
