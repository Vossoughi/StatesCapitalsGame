// Author: Kaveh Vossoughi
// Date  : 11/14/16
// Homework assignment : 7
// Objective: This is the game Activity of this application.
// Five question will be asked from player randomly. The
// questions are unique and are about states and their capitals.
// The maximum score is 100 and each question has 10 points.
//****************************************************************
package vossoughi.us_statesgame;

import android.app.Activity;
import android.content.*;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.*;

public class GameActivity extends Activity
{
   Intent i;
   Context con;
   ArrayList<Integer> randNums;
   String player, subTable[][];
   Cursor c;
   int questionInx, stateOrCapital, score;
   TextView tvQueNum, tvQue1, tvAns1, tvQue2, tvAns2, tvScore;
   EditText ed;
   Button submitBtn , stateBtn, capitalBtn;
   //****************************onCreate()****************************
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_game);
      tvQueNum = (TextView) findViewById(R.id.textView2);
      tvQue1 = (TextView) findViewById(R.id.textView3);
      tvAns1 = (TextView) findViewById(R.id.textView4);
      tvQue2 = (TextView) findViewById(R.id.textView5);
      tvAns2 = (TextView) findViewById(R.id.textView6);
      tvScore = (TextView) findViewById(R.id.textView8);
      tvScore.setText(Integer.toString(score));
      ed = (EditText) findViewById(R.id.editText2);
      stateBtn = (Button) findViewById(R.id.button3);
      capitalBtn = (Button) findViewById(R.id.button4);
      submitBtn = (Button) findViewById(R.id.button5);

      questionInx = 0;
      score = 0;
      Random rand = new Random();
      randNums = new ArrayList<>();
      while (randNums.size() < 5)
      {
         int num = rand.nextInt(50) + 1;
         if (!randNums.contains(num))
            randNums.add(num);
      }

      c = SplashActivity.c;
      subTable = new String[5][2];
      for (int i = 0; i < 5; i++)
      {
         c.moveToFirst();
         int cursorStop = randNums.get(i);
         for (int j = 0; j < cursorStop; j++)
         {
            c.moveToNext();
         }
         subTable[i][0] = c.getString(1);
         subTable[i][1] = c.getString(2);
      }

      setTitle("US states game");
      con = getApplicationContext();
      i = getIntent();
      player = i.getStringExtra("name");
      questionPart1();
   }
   //*************************StateOrCapital()*************************
   public void StateOrCapital(View v)
   {
      switch(v.getId())
      {
         case R.id.button3:
            answerPart1(1);
            break;
         case R.id.button4:
            answerPart1(2);
            break;
         case R.id.button5:
            answerPart2(ed.getText().toString());
            submitBtn.setEnabled(false);
            ed.setEnabled(false);
            break;
         case R.id.button6:
            tvAns1.setText("");
            tvAns2.setText("");
            tvQue2.setText("");
            ed.setVisibility(View.INVISIBLE);
            stateBtn.setEnabled(true);
            capitalBtn.setEnabled(true);
            submitBtn.setVisibility(View.INVISIBLE);
            submitBtn.setEnabled(true);
            ed.setEnabled(true);
            questionInx++;
            if (questionInx < 5)
            {
               questionPart1();
            }
            else
            {
               tvQueNum.setText("");
               tvQue1.setText("");
               stateBtn.setVisibility(View.INVISIBLE);
               capitalBtn.setVisibility(View.INVISIBLE);
               i = new Intent(con, ShowScoresActivity.class);
               i.putExtra("name", player);
               i.putExtra("score", score);
               i.putExtra("Source", 2);
               startActivity(i);
            }
            break;
      }
   }
   //**************************questionPart1()**************************
   public void questionPart1()
   {
      tvQueNum.setText("Question " + Integer.toString(questionInx + 1));
      Random rand = new Random();
      stateOrCapital = rand.nextInt(2) + 1;
      tvQue1.setText(subTable[questionInx][stateOrCapital - 1]);
   }
   //***************************answerPart1()***************************
   public void answerPart1(int answer)
   {
      if (answer == stateOrCapital)
      {
         tvAns1.setTextColor(Color.GREEN);
         tvAns1.setText("Correct");
         score += 10;
         tvScore.setText(Integer.toString(score));
         questionPart2();
      }
      else
      {
         tvAns1.setTextColor(Color.RED);
         tvAns1.setText("Wrong");
      }
      stateBtn.setEnabled(false);
      capitalBtn.setEnabled(false);
   }
   //**************************questionPart2()**************************
   public void questionPart2()
   {
      if (stateOrCapital == 1)
         tvQue2.setText("What is its capital name?");
      else
         tvQue2.setText("What is its state name?");

      ed.setVisibility(View.VISIBLE);
      submitBtn.setVisibility(View.VISIBLE);
   }
   //***************************answerPart2()***************************
   public void answerPart2(String answer)
   {
      tvAns2.setVisibility(View.VISIBLE);
      if (answer.equals(subTable[questionInx][stateOrCapital % 2]))
      {
         tvAns2.setTextColor(Color.GREEN);
         tvAns2.setText("Correct");
         score += 10;
         tvScore.setText(Integer.toString(score));
      }
      else
      {
         tvAns2.setTextColor(Color.RED);
         tvAns2.setText("Wrong");
      }
   }
}
