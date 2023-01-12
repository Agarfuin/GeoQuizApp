package com.example.geoquizapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.geoquizapp.databinding.FragmentTimerRunoutBinding;

public class TimerRunoutFragment extends Fragment {

    private FragmentTimerRunoutBinding binding;

    private void setActionBar(){
        MainQuizActivity activity = (MainQuizActivity) getActivity();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("No Time Left");
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
        binding = FragmentTimerRunoutBinding.inflate(inflater, container, false);

        setActionBar();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.timerRunoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainQuizActivity activity = (MainQuizActivity) getActivity();
                activity.setQuestion();
                NavHostFragment.findNavController(TimerRunoutFragment.this)
                        .navigate(R.id.action_timerRunout_to_SecondFragment);
            }
        });
    }
}