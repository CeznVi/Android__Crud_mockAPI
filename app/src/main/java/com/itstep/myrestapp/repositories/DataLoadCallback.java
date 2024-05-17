package com.itstep.myrestapp.repositories;

import java.util.List;

public interface DataLoadCallback<T> {
    void onDataLoaded(List<T> data);
    void onError(Throwable t);
}