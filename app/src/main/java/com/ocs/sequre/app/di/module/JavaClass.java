package com.ocs.sequre.app.di.module;

import com.ocs.sequre.presentation.viewmodel.DataViewModel;

public class JavaClass {
    private void print() {
        DataViewModel dataViewModel = new DataViewModel(null);
        dataViewModel.data().subscribe(System.out::print, Throwable::printStackTrace);
    }
}
