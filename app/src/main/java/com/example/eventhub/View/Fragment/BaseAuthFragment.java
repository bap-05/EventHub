package com.example.eventhub.View.Fragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.eventhub.Model.AuthNavigator;

public abstract class BaseAuthFragment extends Fragment {

    private AuthNavigator authNavigator;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AuthNavigator) {
            authNavigator = (AuthNavigator) context;
            return;
        }
        Fragment parent = getParentFragment();
        if (parent instanceof AuthNavigator) {
            authNavigator = (AuthNavigator) parent;
            return;
        }
        throw new IllegalStateException("Host must implement AuthNavigator");
    }

    protected AuthNavigator getAuthNavigator() {
        if (authNavigator == null) {
            throw new IllegalStateException("AuthNavigator is not attached");
        }
        return authNavigator;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        authNavigator = null;
    }
}
