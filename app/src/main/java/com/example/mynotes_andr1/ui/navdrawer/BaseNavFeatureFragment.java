package com.example.mynotes_andr1.ui.navdrawer;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseNavFeatureFragment extends Fragment {
    protected abstract int getToolbarId();

    protected Toolbar toolbar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(getToolbarId());
        if (getActivity() instanceof NavDrawerHost) {
            ((NavDrawerHost)getActivity()).supplyToolbar(toolbar);
        }
    }
}
