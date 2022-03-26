package com.econnect.Utilities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.econnect.client.databinding.FragmentProfileBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class CustomFragment<T extends ViewBinding> extends Fragment {
    protected T binding;
    private final Class<T> _tClass;

    public CustomFragment(Class<T> tClass) {
        this._tClass = tClass;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment using Java reflection

        // Get method "T.inflate(LayoutInflater inflater)"
        Method m = null;
        try {
            m = _tClass.getMethod("inflate", LayoutInflater.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Class" + _tClass.getName() + " has no method called 'inflate'");
        }

        // Call method "binding = T.inflate(inflate)"
        try {
            binding = (T) m.invoke(null, inflater);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error while inflating view " + _tClass.getName());
        }

        // Return view as usual
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addListeners();
    }

    protected abstract void addListeners();
}
