package ru.javabegin.training.android6.finance.utils;

import android.content.Context;

import ru.javabegin.training.android6.finance.core.enums.OperationType;
import ru.javabegin.training.android6.finance.objects.LocalizedOperationType;

public class OperationTypeUtils {

    public static LocalizedOperationType incomeType;
    public static LocalizedOperationType outcomeType;
    public static LocalizedOperationType transferType;
    public static LocalizedOperationType convertType;

    
    public static void init(Context context){
        incomeType = new LocalizedOperationType(OperationType.INCOME, context);
        outcomeType = new LocalizedOperationType(OperationType.OUTCOME, context);
        transferType = new LocalizedOperationType(OperationType.TRANSFER, context);
        convertType = new LocalizedOperationType(OperationType.CONVERT, context);
        
    }




}
