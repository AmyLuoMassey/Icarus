package project.com.icarus.ui.Reporting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import project.com.icarus.R;

public class ReportingFragment extends Fragment {

    private ReportingViewModel reportingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportingViewModel =
                new ViewModelProvider(this).get(ReportingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reporting, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        reportingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}