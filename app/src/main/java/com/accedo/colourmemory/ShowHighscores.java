package com.accedo.colourmemory;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by o.lopez.cienfuegos on 20/03/2016.
 */
public class ShowHighscores extends Activity {

    TableLayout tl;
    TableRow tableRow;
    TextView rank;
    TextView name;
    TextView score;
    private int rankNumber = 1;
    public List<HighscoresData> highscores = new LinkedList<HighscoresData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores_layout);

        tl = (TableLayout)findViewById(R.id.table_layout);
        tableRow = new TableRow(this);
        rank = new TextView(this);
        name = new TextView(this);
        score = new TextView(this);

        rank.setPadding(40, 0, 40, 0);
        rank.setGravity(Gravity.CENTER_HORIZONTAL);
        name.setPadding(40, 0, 40, 0);
        name.setGravity(Gravity.CENTER_HORIZONTAL);
        score.setPadding(40, 0, 40, 0);
        score.setGravity(Gravity.CENTER_HORIZONTAL);
        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

        getHighscores();
    }

    private void getHighscores(){
        HighscoreDatabase admin = new HighscoreDatabase(getApplicationContext(), "administracion",
                null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        //HighscoresData hd = null;
        final Cursor fila = bd.rawQuery("SELECT * FROM highscores ORDER BY score DESC", null);
        if (fila != null && fila.getCount() > 0) {
            fila.moveToFirst();
            do {
                tableRow = new TableRow(this);
                rank = new TextView(this);
                rank.setPadding(40, 0, 40, 0);
                rank.setGravity(Gravity.CENTER_HORIZONTAL);
                name = new TextView(this);
                name.setPadding(40, 0, 40, 0);
                name.setGravity(Gravity.CENTER_HORIZONTAL);
                score = new TextView(this);
                score.setPadding(40, 0, 40, 0);
                score.setGravity(Gravity.CENTER_HORIZONTAL);
                //hd = new HighscoresData();
                //hd.setName(fila.getString(1));
                //hd.setScore(fila.getString(2));
                rank.setText(rankNumber + "");
                name.setText(fila.getString(1));
                score.setText(fila.getString(2));
                //tableRow.addView(rank);
                tableRow.addView(rank);
                tableRow.addView(name);
                tableRow.addView(score);
                tl.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
                rankNumber++;
            }while (fila.moveToNext());
            bd.close();
        }
    }
}
