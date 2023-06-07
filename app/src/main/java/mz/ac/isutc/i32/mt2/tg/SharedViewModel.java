package mz.ac.isutc.i32.mt2.tg;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<Object> data = new MutableLiveData<>();

    public void setData(Object newData){
        data.setValue(newData);
    }

    public LiveData<Object> getData(){
        return data;
    }
}


