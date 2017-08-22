// Author: Kaveh Vossoughi
// Date  : 11/14/16
// Homework assignment : 7
// Objective: This is a custom CursorAdaptor. The
// purpose to extend CursorAdaptor is to define a
// Cursor which will be able to pass data from two
// column using two TextViews.
//****************************************************************
package vossoughi.us_statesgame;

import android.content.Context;
import android.database.Cursor;
import android.view.*;
import android.widget.*;

public class ScoresCursorAdapter extends CursorAdapter
{
   //***********************ScoresCursorAdapter()***********************
   public ScoresCursorAdapter(Context context, Cursor cursor)
   {
      super(context, cursor, 0);
   }
   //*****************************newView()*****************************
   @Override
   public View newView(Context context, Cursor cursor, ViewGroup parent)
   {
      return LayoutInflater.from(context).inflate(R.layout.names_socres, parent, false);
   }
   //*****************************bindView()*****************************
   @Override
   public void bindView(View view, Context context, Cursor cursor)
   {
      TextView tvName = (TextView) view.findViewById(R.id.tvNames);
      TextView tvScore = (TextView) view.findViewById(R.id.tvScores);

      String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
      int score = cursor.getInt(cursor.getColumnIndexOrThrow("score"));

      tvName.setText(name);
      tvScore.setText(String.valueOf(score));
   }
}
