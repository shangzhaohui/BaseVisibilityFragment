package com.demo.fragmentdemo.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.demo.fragmentdemo.R;
import com.library.base.BaseVisibilityFragment;

/**
 * @author shangzhaohui
 * @date 2021/11/24
 */
public class ExampleFragment extends BaseVisibilityFragment {
   public static final String KEY ="input";

    public static ExampleFragment newInstance(String input) {
        Bundle args = new Bundle();
        args.putString(KEY,input);
        ExampleFragment fragment = new ExampleFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.example_layout, container,false);
        TextView textView= view.findViewById(R.id.output);
        ExampleViewModel viewModel = new ViewModelProvider(requireActivity()).get(ExampleViewModel.class);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.selectItem("hahahahaha");
            }
        });
        if (getArguments()!= null){
            String input = requireArguments().getString(KEY);
            textView.setText(input);
        }
        return view;
    }

    @Override
    public void onVisibilityChanged(boolean visible) {

    }
}
