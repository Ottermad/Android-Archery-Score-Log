package me.attwoodthomas.archeryscorelog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import java.util.HashMap;

import me.attwoodthomas.archeryscorelog.database.helper.DatabaseHelper;


public class ViewScoreActivity extends ActionBarActivity {

    // Member Variables
    protected DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
    protected TextView mLocationTextView;
    protected TextView mDateTimeTextView;
    protected TextView mDistanceTextView;
    protected TextView mArrow1TextView;
    protected TextView mArrow2TextView;
    protected TextView mArrow3TextView;
    protected TextView mArrow4TextView;
    protected TextView mArrow5TextView;
    protected TextView mArrow6TextView;
    protected TextView mTotalTextView;
    protected TextView mStyleTextView;
    protected TextView mTotalNumberOfArrowsTextView;
    protected TextView mUnitsTextView;
    protected TextView mIndoorOutdoorTextView;
    protected HashMap<String, String> mScore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);


        // Connect Variables to Views
        mLocationTextView = (TextView) findViewById(R.id.locationValueTextView);
        mDateTimeTextView = (TextView) findViewById(R.id.dateTimeValueTextView);
        mDistanceTextView = (TextView) findViewById(R.id.distanceValueTextView);
        mArrow1TextView = (TextView) findViewById(R.id.arrow1ValueTextView);
        mArrow2TextView = (TextView) findViewById(R.id.arrow2ValueTextView);
        mArrow3TextView = (TextView) findViewById(R.id.arrow3ValueTextView);
        mArrow4TextView = (TextView) findViewById(R.id.arrow4ValueTextView);
        mArrow5TextView = (TextView) findViewById(R.id.arrow5ValueTextView);
        mArrow6TextView = (TextView) findViewById(R.id.arrow6ValueTextView);
        mTotalTextView = (TextView) findViewById(R.id.totalValueTextView);
        mStyleTextView = (TextView) findViewById(R.id.styleValueTextView);
        mTotalNumberOfArrowsTextView = (TextView) findViewById(R.id.numberOfArrowsValueTextView);
        mUnitsTextView = (TextView) findViewById(R.id.distanceUnitValueTextView);
        mIndoorOutdoorTextView = (TextView) findViewById(R.id.indoorOutdoorValueTextView);

        String dateTime;
        String total;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dateTime = extras.getString("DateTime");
            total = extras.getString("Total");
            mScore = mDatabaseHelper.getScore(dateTime, total);
            mLocationTextView.setText(mScore.get("Location"));
            mDateTimeTextView.setText(mScore.get("DateTime"));
            mDistanceTextView.setText(mScore.get("Distance"));
            mArrow1TextView.setText(mScore.get("Arrow1"));
            mArrow2TextView.setText(mScore.get("Arrow2"));
            mArrow3TextView.setText(mScore.get("Arrow3"));
            mArrow4TextView.setText(mScore.get("Arrow4"));
            mArrow5TextView.setText(mScore.get("Arrow5"));
            mArrow6TextView.setText(mScore.get("Arrow6"));
            mTotalTextView.setText(mScore.get("Total") + " / " + Integer.parseInt(mScore.get("NumberOfArrows")) * 10);
            mStyleTextView.setText(mScore.get("Style"));
            mTotalNumberOfArrowsTextView.setText(mScore.get("NumberOfArrows"));
            mUnitsTextView.setText(mScore.get("Units"));
            mIndoorOutdoorTextView.setText(mScore.get("IndoorOrOutdoor"));

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        if (id == R.id.action_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewScoreActivity.this);
            builder.setMessage("Delete this score?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mDatabaseHelper.deleteScore(mScore.get("DateTime"), mScore.get("Total"));
                            Intent intent = new Intent(ViewScoreActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog dialog = builder.create();
            dialog.show();

        }

        return super.onOptionsItemSelected(item);
    }
}
