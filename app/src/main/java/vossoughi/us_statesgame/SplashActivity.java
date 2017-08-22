// Author: Kaveh Vossoughi
// Date  : 11/14/16
// Homework assignment : 7
// Objective: This is a Splash activity. It takes user's
// name and give her/him option to play the game or see
// the scores. It populates the database contains the
// states information.
//****************************************************************
package vossoughi.us_statesgame;

import android.app.Activity;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;

import java.io.*;
import java.util.*;

public class SplashActivity extends Activity
{
   static SQLiteDatabase db;
   static Cursor c, c2;
   Context con;
   Intent i;
   EditText ed;
   //****************************onCreate()****************************
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_splash);
      ed = (EditText) findViewById(R.id.editText);

      db = openOrCreateDatabase("game.db",SQLiteDatabase.CREATE_IF_NECESSARY, null);
      db.setVersion(3);

      String sqlcmd;
      if (!isTableExists("us_states"))
      {
         sqlcmd = "create table us_states(_id integer primary key autoincrement," +
               " state text not null, capital text not null);";
         db.execSQL(sqlcmd);

         File sdCard = Environment.getExternalStorageDirectory();
         File dir = new File(sdCard.getAbsolutePath() + "/myFiles");
         File file = new File(dir, "US_States");
         ContentValues cv = new ContentValues();
         con = getApplicationContext();
         try
         {
            Scanner scan = new Scanner(file);
            Scanner lineScan;
            int offset = 0;
            String line, state, capital;
            while (scan.hasNextLine())
            {
               line = scan.nextLine();
               lineScan = new Scanner(line).useDelimiter("\\s{2,}");
               if (offset > 1)
               {
                  while (lineScan.hasNext())
                  {
                     state = lineScan.next();
                     capital = lineScan.next();
                     cv.put("state", state);
                     cv.put("capital", capital);
                     db.insert("us_states", null, cv);;
                  }
               }
               offset++;
            }
         } catch (IOException e) {}

         // To be replaced
         cv.put("state", "California");
         cv.put("capital", "Sacramento");
         db.insert("us_states", null, cv);
         cv.put("state", "Nevada");
         cv.put("capital", "Carson City");
         db.insert("us_states", null, cv);
         cv.put("state", "Ohio");
         cv.put("capital", "Columbus");
         db.insert("us_states", null, cv);
         cv.put("state", "Texas");
         cv.put("capital", "Austin");
         db.insert("us_states", null, cv);
         for (int i = 0; i < 50; i++)
         {
            cv.put("state", "Texas");
            cv.put("capital", "Austin");
            db.insert("us_states", null, cv);
         }
         // to be replace

      }

      if (!isTableExists("scores"))
      {
         sqlcmd = "create table scores(_id integer primary key autoincrement," +
               " name text not null, score integer not null);";
         db.execSQL(sqlcmd);
      }
      c = db.query("us_states", null, null, null, null, null, null);
      c2 = db.query("scores", null, null, null, null, null, "score desc");
   }
   //**************************buttonListener()**************************
   public void buttonListener(View v)
   {
      switch(v.getId())
      {
         case R.id.button:
            i = new Intent(con, GameActivity.class);
            i.putExtra("name", ed.getText().toString());
            break;
         case R.id.button2:
            i = new Intent(con, ShowScoresActivity.class);
            break;
      }
      i.putExtra("Source", 1);
      startActivity(i);
   }
   //***************************isTableExists()***************************
   public boolean isTableExists(String tableName)
   {
      boolean tableExists = false;
      try
      {
         c = db.query(tableName, null, null, null, null, null, null);
         tableExists = true;
      }
      catch (SQLiteException e) {}

      return tableExists;
   }
}
