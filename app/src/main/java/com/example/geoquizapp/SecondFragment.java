package com.example.geoquizapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.geoquizapp.databinding.FragmentQuestionBinding;


import java.util.Map;

public class SecondFragment extends Fragment {

    private boolean buttonPressed = false;
    private String mapsAnswer;
    private FragmentQuestionBinding binding;
    private ImageView questionImage;
    private TextView questionText;
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private Map<String, String> current_question;

    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == 1){
                        Intent intent = result.getData();
                        if(intent != null){
                            String[] mapsAnswerArray = intent.getStringArrayExtra("mapsAnswer");
                            if(mapsAnswerArray != null){
                                if(mapsAnswerArray.length > 1) {
                                    mapsAnswer = "";
                                    for(int i = 0; i < mapsAnswerArray.length; i++){
                                        mapsAnswer += mapsAnswerArray[i].substring(0, 1).toLowerCase() + mapsAnswerArray[i].substring(1) + "_";
                                    }
                                    mapsAnswer = mapsAnswer.substring(0, mapsAnswer.length()-1);
                                } else {
                                    mapsAnswer = mapsAnswerArray[0].substring(0, 1).toLowerCase() + mapsAnswerArray[0].substring(1);
                                }
                            } else {
                                mapsAnswer = "null";
                            }
                        }
                    }
                    else if(result.getResultCode() == 2){
                        NavHostFragment.findNavController(SecondFragment.this)
                                .navigate(R.id.action_SecondFragment_to_timerRunout);
                    }
                }
            }
    );

    private void setQuestionFragment(){
        Resources resources = getResources();
        MainQuizActivity activity = (MainQuizActivity) getActivity();
        current_question = activity.getQuestion();
        int resID = resources.getIdentifier(current_question.get("questionImage"), "drawable", activity.getPackageName());
        Drawable drawable = resources.getDrawable(resID);
        questionImage.setImageDrawable(drawable);

        if (current_question.get("questionType").equals("flag")) {
            questionText.setText(R.string.question_flag);
        } else {
            questionText.setText(R.string.question_view);
        }
    }

    private void setButtons(){
        if (current_question.get("questionOp1").contains("_"))
            option1.setText(current_question.get("questionOp1").replace("_", " "));
        else
            option1.setText(current_question.get("questionOp1"));
        if (current_question.get("questionOp2").contains("_"))
            option2.setText(current_question.get("questionOp2").replace("_", " "));
        else
            option2.setText(current_question.get("questionOp2"));
        if (current_question.get("questionOp3").contains("_"))
            option3.setText(current_question.get("questionOp3").replace("_", " "));
        else
            option3.setText(current_question.get("questionOp3"));
        if (current_question.get("questionOp4").contains("_"))
            option4.setText(current_question.get("questionOp4").replace("_", " "));
        else
            option4.setText(current_question.get("questionOp4"));
    }

    private void switchToMap(){
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        String temp = current_question.get("questionAnswer");
        String send = temp.substring(0, 1).toUpperCase() + temp.substring(1);
        intent.putExtra("questionAnswer", send);
        activityLauncher.launch(intent);
    }

    private void setActionBar(){
        MainQuizActivity activity = (MainQuizActivity) getActivity();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Question");
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        TextView toolbarText = (TextView) activity.findViewById(R.id.toolbar_text);
        if(toolbarText != null){
            toolbarText.setText("Remaining Questions: " + activity.getTotalQuestionLeft());
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        setActionBar();

        questionImage = binding.getRoot().findViewById(R.id.question_image);
        questionText = binding.getRoot().findViewById(R.id.question_text);
        option1 = binding.getRoot().findViewById(R.id.option1);
        option2 = binding.getRoot().findViewById(R.id.option2);
        option3 = binding.getRoot().findViewById(R.id.option3);
        option4 = binding.getRoot().findViewById(R.id.option4);

        setQuestionFragment();
        setButtons();

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainQuizActivity activity = (MainQuizActivity) getActivity();
                current_question = activity.getQuestion();

                if (current_question.get("questionAnswer").equals(option1.getText().toString().replace(" ", "_"))) {
                    buttonPressed = true;
                    switchToMap();
                } else {
                    NavHostFragment.findNavController(SecondFragment.this)
                            .navigate(R.id.action_SecondFragment_to_wrongAnswerFragment);
                }
            }
        });

        binding.option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainQuizActivity activity = (MainQuizActivity) getActivity();

                if (activity.getTotalQuestionLeft() == 0){
                    NavHostFragment.findNavController(SecondFragment.this)
                            .navigate(R.id.action_SecondFragment_to_endgameFragment);
                }else {
                    current_question = activity.getQuestion();

                    if (current_question.get("questionAnswer").equals(option2.getText().toString().replace(" ", "_"))){
                        buttonPressed = true;
                        switchToMap();

                    } else {
                        NavHostFragment.findNavController(SecondFragment.this)
                                .navigate(R.id.action_SecondFragment_to_wrongAnswerFragment);
                    }
                }
            }
        });

        binding.option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainQuizActivity activity = (MainQuizActivity) getActivity();
                current_question = activity.getQuestion();

                if (current_question.get("questionAnswer").equals(option3.getText().toString().replace(" ", "_"))){
                    buttonPressed = true;
                    switchToMap();

                } else {
                    NavHostFragment.findNavController(SecondFragment.this)
                            .navigate(R.id.action_SecondFragment_to_wrongAnswerFragment);
                }
            }
        });

        binding.option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainQuizActivity activity = (MainQuizActivity) getActivity();
                current_question = activity.getQuestion();

                if (current_question.get("questionAnswer").equals(option4.getText().toString().replace(" ", "_"))){
                    buttonPressed = true;
                    switchToMap();

                } else {
                    NavHostFragment.findNavController(SecondFragment.this)
                            .navigate(R.id.action_SecondFragment_to_wrongAnswerFragment);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MainQuizActivity mainQuizActivity = (MainQuizActivity) getActivity();
        String currentAnswer = current_question.get("questionAnswer");
        if(buttonPressed){
            if(mapsAnswer != null && mapsAnswer.equals(currentAnswer)){
                mainQuizActivity.deleteRow(mainQuizActivity.getQuestion().get("questionAnswer"));
                if (mainQuizActivity.getTotalQuestionLeft() == 0){
                    NavHostFragment.findNavController(SecondFragment.this)
                            .navigate(R.id.action_SecondFragment_to_endgameFragment);
                }
                else{
                    NavHostFragment.findNavController(SecondFragment.this)
                            .navigate(R.id.action_SecondFragment_to_correctAnswerFragment);
                }
            } else {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_wrongAnswerFragment);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}