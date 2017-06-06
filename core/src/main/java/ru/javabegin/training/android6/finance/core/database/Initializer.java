package ru.javabegin.training.android6.finance.core.database;

import ru.javabegin.training.android6.finance.core.dao.impls.OperationDAOImpl;
import ru.javabegin.training.android6.finance.core.dao.impls.SourceDAOImpl;
import ru.javabegin.training.android6.finance.core.dao.impls.StorageDAOImpl;
import ru.javabegin.training.android6.finance.core.decorator.OperationSync;
import ru.javabegin.training.android6.finance.core.decorator.SourceSync;
import ru.javabegin.training.android6.finance.core.decorator.StorageSync;

public class Initializer {
    
    private static OperationSync operationSync;
    private static SourceSync sourceSync;
    private static StorageSync storageSync;


    public static OperationSync getOperationSync() {
        return operationSync;
    }


    public static SourceSync getSourceSync() {
        return sourceSync;
    }


    public static StorageSync getStorageSync() {
        return storageSync;
    }


    public static void load(String driverName, String url){

        SQLiteConnection.init(driverName, url);

        // последовательность создания объектов - важна (сначала справочные значения, потом операции)
        sourceSync = new SourceSync(new SourceDAOImpl());
        storageSync = new StorageSync(new StorageDAOImpl());
        operationSync = new OperationSync(new OperationDAOImpl(sourceSync.getIdentityMap(), storageSync.getIdentityMap()), sourceSync, storageSync);
    }
    
}
