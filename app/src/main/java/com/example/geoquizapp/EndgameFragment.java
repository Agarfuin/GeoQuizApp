package com.example.geoquizapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.geoquizapp.databinding.FragmentEndgameBinding;

public class EndgameFragment extends Fragment {

    private FragmentEndgameBinding binding;

    private void setActionBar(){
        MainQuizActivity activity = (MainQuizActivity) getActivity();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Endgame");
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        TextView toolbarText = (TextView) activity.findViewById(R.id.toolbar_text);
        if(toolbarText != null){
            toolbarText.setText("");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEndgameBinding.inflate(inflater, container, false);

        setActionBar();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.endgameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // after on CLick we are using finish to close and then just after that
                // we are calling startactivity(getIntent()) to open our application
                Intent intent = new Intent(getActivity(), MainQuizActivity.class);
                startActivity(intent);
                getActivity().finish();

                // this basically provides animation
                getActivity().overridePendingTransition(0, 0);
            }
        });
    }
}