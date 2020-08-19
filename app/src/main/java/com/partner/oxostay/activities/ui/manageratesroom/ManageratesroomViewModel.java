package com.partner.oxostay.activities.ui.manageratesroom;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageratesroomViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ManageratesroomViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Manageratesroom fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}