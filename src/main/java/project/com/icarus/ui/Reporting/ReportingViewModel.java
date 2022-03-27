package project.com.icarus.ui.Reporting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReportingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ReportingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is reporting fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}