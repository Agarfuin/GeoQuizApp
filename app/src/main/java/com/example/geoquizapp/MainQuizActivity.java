package com.example.geoquizapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.geoquizapp.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainQuizActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private String currentQuestion_type;
    private String currentQuestion_option1;
    private String currentQuestion_option2;
    private String currentQuestion_option3;
    private String currentQuestion_option4;
    private String currentQuestion_answer;
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS questionbank";
    private final List<String> questions_flags = new ArrayList<>(
            Arrays.asList(
                    "nigeria", "chad", "senegal", "hungary",
                    "lithuania", "kiribati", "ireland", "mongolia",
                    "madagascar", "croatia", "vietnam", "luxembourg",
                    "austria", "montenegro", "malaysia", "myanmar",
                    "south_africa", "qatar", "zambia", "rwanda",
                    "brazil", "kazakhstan", "maldives", "tanzania",
                    "albania", "kosovo", "angola", "iraq",
                    "ireland", "kyrgyzstan", "brunei", "barbados",
                    "azerbaijan", "panama", "kazakhstan", "bolivia",
                    "argentina", "chad", "cuba", "colombia",
                    "spain", "mexico", "belgium", "armenia",
                    "belgium", "laos", "germany", "denmark",
                    "south_korea", "japan", "thailand", "taiwan",
                    "australia", "new_zealand", "maldives", "united_kingdom",
                    "iceland", "finland", "norway", "sweden",
                    "qatar", "andorra", "burundi", "palestine",
                    "bosnia_and_herzegovina", "kosovo", "serbia", "slovakia",
                    "algeria", "tunisia", "morocco", "sudan",
                    "bulgaria", "greece", "romania", "ukraine",
                    "vietnam", "china", "taiwan", "japan",
                    "czechia", "poland", "slovenia", "france",
                    "portugal", "spain", "samoa", "mexico",
                    "germany", "belgium", "ecuador", "poland",
                    "switzerland", "sweden", "belgium", "luxembourg",
                    "romania", "ukraine", "belarus", "lithuania",
                    "kazakhstan", "turkmenistan", "kyrgyzstan", "tajikistan"
            ));

    private final List<String> questions_scenes = new ArrayList<>(
            Arrays.asList(
                    "russia", "france", "germany", "romania",
                    "united_states", "united_kingdom", "spain", "canada",
                    "saudi_arabia", "qatar", "palestine", "burundi",
                    "china", "uganda", "sudan", "indonesia",
                    "egypt", "libya", "tunisia", "chad",
                    "nepal", "norway", "uruguay", "venezuela",
                    "united_kingdom", "france", "germany", "switzerland",
                    "france", "italy", "greece", "denmark",
                    "italy", "turkey", "united_kingdom", "cyprus",
                    "japan", "south_korea", "china", "taiwan",
                    "india", "pakistan", "nepal", "mongolia",
                    "jordan", "lebanon", "syria", "iraq",
                    "turkey", "greece", "iran", "bulgaria",
                    "peru", "bolivia", "chile", "myanmar"
            ));

    private void initializeFlags(){
        db = openOrCreateDatabase("QuestionBankDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS questionbank(question_type VARCHAR, option1 VARCHAR," +
                "option2 VARCHAR, option3 VARCHAR, option4 VARCHAR, answer VARCHAR);");

        for(int i = 0; i<questions_flags.toArray().length; i+=4){
            if(i % 16 == 0){
                db.execSQL("INSERT INTO questionbank VALUES('flag','" + questions_flags.get(i) + "','" + questions_flags.get(i+1) + "','" + questions_flags.get(i+2) + "','"+questions_flags.get(i+3)+"','" + questions_flags.get(i) + "');");
            }
            else if (i % 16 == 4){
                db.execSQL("INSERT INTO questionbank VALUES('flag','" + questions_flags.get(i+1) + "','" + questions_flags.get(i) + "','" + questions_flags.get(i+2) + "','"+questions_flags.get(i+3)+"','" + questions_flags.get(i) + "');");
            }
            else if (i % 16 == 8){
                db.execSQL("INSERT INTO questionbank VALUES('flag','" + questions_flags.get(i+2) + "','" + questions_flags.get(i+1) + "','" + questions_flags.get(i) + "','"+questions_flags.get(i+3)+"','" + questions_flags.get(i) + "');");
            }
            else {
                db.execSQL("INSERT INTO questionbank VALUES('flag','" + questions_flags.get(i+3) + "','" + questions_flags.get(i+1) + "','" + questions_flags.get(i+2) + "','"+questions_flags.get(i)+"','" + questions_flags.get(i) + "');");
            }
        }
    }

    private void initializeScenes(){
        db = openOrCreateDatabase("QuestionBankDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS questionbank(question_type VARCHAR, option1 VARCHAR," +
                "option2 VARCHAR, option3 VARCHAR, option4 VARCHAR, answer VARCHAR);");

        for(int i = 0; i<questions_scenes.toArray().length; i+=4){
            if (i % 16 == 0){
                db.execSQL("INSERT INTO questionbank VALUES('scene','" + questions_scenes.get(i) + "','" + questions_scenes.get(i+1) + "','" + questions_scenes.get(i+2) + "','"+questions_scenes.get(i+3)+"','" + questions_scenes.get(i) + "');");
            }
            else if (i % 16 == 4){
                db.execSQL("INSERT INTO questionbank VALUES('scene','" + questions_scenes.get(i+1) + "','" + questions_scenes.get(i) + "','" + questions_scenes.get(i+2) + "','"+questions_scenes.get(i+3)+"','" + questions_scenes.get(i) + "');");
            }
            else if (i % 16 == 8){
                db.execSQL("INSERT INTO questionbank VALUES('scene','" + questions_scenes.get(i+2) + "','" + questions_scenes.get(i+1) + "','" + questions_scenes.get(i) + "','"+questions_scenes.get(i+3)+"','" + questions_scenes.get(i) + "');");
            }
            else {
                db.execSQL("INSERT INTO questionbank VALUES('scene','" + questions_scenes.get(i+3) + "','" + questions_scenes.get(i+1) + "','" + questions_scenes.get(i+2) + "','"+questions_scenes.get(i)+"','" + questions_scenes.get(i) + "');");
            }
        }
    }

    protected void initializeDatabase(){
        initializeFlags();
        initializeScenes();
    }

    protected void deleteRow(String countryName){
        String query = "DELETE FROM questionbank WHERE answer = '" + countryName + "';";
        db.execSQL(query);
    }

    protected void terminateDatabase(){
        db = openOrCreateDatabase("QuestionBankDB", Context.MODE_PRIVATE, null);
        db.execSQL(SQL_DELETE_ENTRIES);
        db.close();
        db = null;
    }

    protected void setQuestion(){
        Cursor current_question = db.rawQuery("SELECT * FROM questionbank ORDER BY RANDOM() LIMIT 1;", null);
        while(current_question.moveToNext()){
            currentQuestion_type = current_question.getString(0);
            currentQuestion_option1 = current_question.getString(1);
            currentQuestion_option2 = current_question.getString(2);
            currentQuestion_option3 = current_question.getString(3);
            currentQuestion_option4 = current_question.getString(4);
            currentQuestion_answer = current_question.getString(5);
        }
        current_question.close();
    }

    protected Map<String, String> getQuestion(){
        Map<String, String> currentQuestion= new HashMap<>();

        currentQuestion.put("questionType", currentQuestion_type);
        currentQuestion.put("questionOp1", currentQuestion_option1);
        currentQuestion.put("questionOp2", currentQuestion_option2);
        currentQuestion.put("questionOp3", currentQuestion_option3);
        currentQuestion.put("questionOp4", currentQuestion_option4);
        currentQuestion.put("questionAnswer", currentQuestion_answer);
        currentQuestion.put("questionImage", currentQuestion_answer + "_" + currentQuestion_type);

        return currentQuestion;
    }

    protected int getTotalQuestionLeft(){
        Cursor cursor = db.rawQuery("SELECT * FROM questionbank;", null);
        int totalQuestionLeft = cursor.getCount();
        cursor.close();
        return totalQuestionLeft;
    }

    private void initializeActionBar(){
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("GeoQuiz");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.geoquizapp.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        initializeActionBar();

        NavHostFragment.create(R.navigation.nav_graph);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainQuizActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        terminateDatabase();
        super.onDestroy();
    }
}