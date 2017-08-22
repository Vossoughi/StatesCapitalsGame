// Author: Kaveh Vossoughi
// Date  : 11/14/16
// Homework assignment : 7
// Objective: This Activity displays the players scores.
// The scores are from the highest to lowest. The screen
// can be scrolled if there are too many records on the table.
//****************************************************************
package vossoughi.us_statesgame;

import android.app.Activity;
import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;

public class ShowScoresActivity extends Activity
{
   Intent i;
   String player;
   int score;
   Cursor c2;
   ListView lv;
   //****************************onCreate()****************************
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_show_scores);
      c2 = SplashActivity.c2;
      i = getIntent();
      int intentSource = i.getIntExtra("Source", 1);

      if (intentSource == 2)
      {
         player = i.getStringExtra("name");
         score = i.getIntExtra("score", 0);

         ContentValues cv = new ContentValues();
         cv.put("name", player);
         cv.put("score", score);
         SplashActivity.db.insert("scores", null, cv);
      }
      setTitle("Scores");

      ScoresCursorAdapter adapter = new ScoresCursorAdapter(this, c2);
      lv = (ListView) findViewById(R.id.listView);
      lv.setAdapter(adapter);
      lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
   }
}
