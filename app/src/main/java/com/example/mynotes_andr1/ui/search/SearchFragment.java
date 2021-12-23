package com.example.mynotes_andr1.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mynotes_andr1.R;

public class SearchFragment extends Fragment {

    public static final String TAG = "SearchFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button directorySelect=view.findViewById(R.id.folder_select);
        directorySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((NotesActivity)getActivity()).showFolders();
            }
        });
    }
}
