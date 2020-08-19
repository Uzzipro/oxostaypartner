package com.partner.oxostay.activities.ui.amenities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AmenitiesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AmenitiesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Amenities fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}