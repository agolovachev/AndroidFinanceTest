package ru.javabegin.training.android6.finance.core.interfaces;

import java.util.Calendar;

import ru.javabegin.training.android6.finance.core.enums.OperationType;

public interface Operation extends IconNode {

    long getId();

    void setId(long id);

    OperationType getOperationType();

    Calendar getDateTime();

    String getDescription();

}
