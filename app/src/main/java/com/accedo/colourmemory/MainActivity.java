package com.accedo.colourmemory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    private Button highscores;
    private GridView cardsBoard;
    private Context context;
    private CardAdapter adapter;
    private TextView currentHighscore;
    private boolean onTimeout;
    private AppCompatActivity appCompatActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        highscores = (Button) findViewById(R.id.highscores);
        cardsBoard = (GridView) findViewById(R.id.cards);
        currentHighscore = (TextView) findViewById(R.id.currentHighscore);

        context = getBaseContext();
        onTimeout = false;

        startGame();
    }

    public void startGame(){
        final CardsController cardsController = new CardsController(context);
        adapter = new CardAdapter(this, cardsController.getImageId());

        cardsBoard.setAdapter(adapter);

        currentHighscore.setText(cardsController.getCurrentHighscore() + "");

        cardsBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!onTimeout) {
                    int response = cardsController.viewCard(position);
                    if (response == 0) {
                        update();
                    } else if (response == -1 || response == 1) {
                        update();
                        onTimeout = true;
                        timeout(cardsController, response);
                    } /*else if (response == 1) {
                        update();
                        onTimeout = true;
                        timeout(cardsController, response);
                    }*/ else if (response == 2) {
                        //LayoutInflater inflater = getLayoutInflater();
                        update();
                        onTimeout = true;
                        timeout(cardsController, response);
                        //builder.setView(inflater.inflate(R.layout.alert_dialog_layout, null))

                    }
                }
            }
        });

        highscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHighscores();
            }
        });
    }

    public void timeout(final CardsController cardsController, final int response){
        new CountDownTimer(1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if(response == -1) {
                    cardsController.hideCards();
                    update();
                    currentHighscore.setText(cardsController.getCurrentHighscore() + "");
                }else if(response == 1) {
                    cardsController.removeCards();
                    update();
                    currentHighscore.setText(cardsController.getCurrentHighscore() + "");
                }else if(response == 2){
                    cardsController.removeCards();
                    update();
                    currentHighscore.setText(cardsController.getCurrentHighscore() + "");
                    showDialog(cardsController);
                }
                onTimeout = false;
            }
        }.start();
    }

    public void endGameTimeout() {
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startGame();
            }
        }.start();
    }

    public void update(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void showDialog(final CardsController cardsController) {
        final EditText name = new EditText(MainActivity.this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //final LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        builder.setView(name)
                .setTitle(R.string.new_highscore)
                .setMessage(getString(R.string.input_name) + "\nHighscore: "
                        + cardsController.getCurrentHighscore())
                .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (name.getText().toString() != null && !name.getText().toString().equals("")) {
                            addHighscore(name.getText().toString(),
                                    cardsController.getCurrentHighscore());
                                    showHighscores();
                                    endGameTimeout();
                        } else {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Error!")
                                    .setMessage("Must put a valid name")
                                    .setPositiveButton(getString(R.string.done),
                                            new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            showDialog(cardsController);
                                        }
                                    }).create().show();
                        }
                    }
                }).create().show();
    }

    public void addHighscore(String name, int score) {
        HighscoreDatabase admin = new HighscoreDatabase(context, "administracion",
                null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        final Cursor fila = bd.rawQuery("select * from highscores where score > ? and name == ?", new String[]{score + "", name});
        if (fila != null && fila.getCount() > 0) {
            fila.moveToFirst();
            /*ContentValues registro = new ContentValues();
            registro.put("name", name);
            registro.put("score", score);
            bd.insert("highscores", null, registro);*/
        } else {
            ContentValues registro = new ContentValues();
            registro.put("name", name);
            registro.put("score", score);
            bd.insert("highscores", null, registro);
        }
        bd.close();
    }

    public void isHighscore(String score){
        HighscoreDatabase admin = new HighscoreDatabase(context, "administracion",
                null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();

        final Cursor fila = bd.rawQuery("select * from highscores WHERE score > ", new String[]{score});
        if (fila != null && fila.getCount() > 0) {
            fila.moveToFirst();
        }
        bd.close();
    }

    public void showHighscores(){
        Intent intent = new Intent(context, ShowHighscores.class);
        startActivity(intent);
    }
}
