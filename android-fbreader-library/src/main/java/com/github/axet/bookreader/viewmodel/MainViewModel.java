package com.github.axet.bookreader.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
//import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainViewModel extends ViewModel {
   // public CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<Boolean> eventShowBookMark = new MutableLiveData<>();
    public MutableLiveData<Boolean> eventAddBookMark = new MutableLiveData<>();
    public MutableLiveData<Boolean> eventOpenNote = new MutableLiveData<>();
    public MutableLiveData<Boolean> eventFont = new MutableLiveData<>();
    public MutableLiveData<Boolean> eventShowMucLuc = new MutableLiveData<>();
    public MutableLiveData<Boolean> eventCloseBookMark = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> eventLoadFrNote = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> eventSearchN = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> eventShowViewMucLuc = new MutableLiveData<>(false);
    public MutableLiveData<Integer> eventPageBook = new MutableLiveData<>(1);
    public MutableLiveData<Integer> attachmentId = new MutableLiveData<>(0);
    @Override
    protected void onCleared() {
    //    compositeDisposable.clear();
        super.onCleared();
    }
}
