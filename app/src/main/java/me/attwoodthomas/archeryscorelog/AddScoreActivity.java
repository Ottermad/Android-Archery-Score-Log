package me.attwoodthomas.archeryscorelog;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;


import java.util.Calendar;


import me.attwoodthomas.archeryscorelog.database.helper.DatabaseHelper;


public class AddScoreActivity extends ActionBarActivity {

    // Member Variables
    protected EditText mArrow1EditText;
    protected EditText mArrow2EditText;
    protected EditText mArrow3EditText;
    protected EditText mArrow4EditText;
    protected EditText mArrow5EditText;
    protected EditText mArrow6EditText;
    protected EditText mDistanceEditText;
    protected EditText mLocationEditText;
    protected EditText mTotalArrowsEditText;
    protected Button mAddScoreButton;
    protected RadioGroup mRadioGroup;
    protected RadioGroup mIndoorOutdoorRadioGroup;
    protected Spinner mUnitsSpinner;
    protected int mArrow1;
    protected int mArrow2;
    protected int mArrow3;
    protected int mArrow4;
    protected int mArrow5;
    protected int mArrow6;

    protected DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);

        addItemsOnUnitsSpinner();

        // Connect Views
        mUnitsSpinner = (Spinner) findViewById(R.id.units_spinner);
        mArrow1EditText = (EditText) findViewById(R.id.arrowOneEditText);
        mArrow2EditText = (EditText) findViewById(R.id.arrowTwoEditText);
        mArrow3EditText = (EditText) findViewById(R.id.arrowThreeEditText);
        mArrow4EditText = (EditText) findViewById(R.id.arrowFourEditText);
        mArrow5EditText = (EditText) findViewById(R.id.arrowFiveEditText);
        mArrow6EditText = (EditText) findViewById(R.id.arrowSixEditText);
        mDistanceEditText = (EditText) findViewById(R.id.distanceEditText);
        mLocationEditText = (EditText) findViewById(R.id.locationEditText);
        //mUnitsEditText = (EditText) findViewById(R.id.unitsEditText);
        mTotalArrowsEditText = (EditText) findViewById(R.id.numberOfArrowsEditText);
        mAddScoreButton = (Button) findViewById(R.id.addScoreButton);
        mRadioGroup = (RadioGroup) findViewById(R.id.styleRadioGroup);
        mRadioGroup.check(R.id.radioButton);
        mIndoorOutdoorRadioGroup = (RadioGroup) findViewById(R.id.indoorOutdoorRadioGroup);
        mIndoorOutdoorRadioGroup.check(R.id.indoorRadioButton);

        mAddScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(AddScoreActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 2 : " + String.valueOf(mUnitsSpinner.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();*/

                try {
                    mArrow1 = Integer.parseInt(mArrow1EditText.getText().toString());
                } catch (Exception e) {
                    mArrow1 = 0;
                }
                try {
                    mArrow2 = Integer.parseInt(mArrow2EditText.getText().toString());
                } catch (Exception e) {
                    mArrow2 = 0;
                }
                try {
                    mArrow3 = Integer.parseInt(mArrow3EditText.getText().toString());
                } catch (Exception e) {
                    mArrow3 = 0;
                }
                try {
                    mArrow4 = Integer.parseInt(mArrow4EditText.getText().toString());
                } catch (Exception e) {
                    mArrow4 = 0;
                }
                try {
                    mArrow5 = Integer.parseInt(mArrow5EditText.getText().toString());
                } catch (Exception e) {
                    mArrow5 = 0;
                }
                try {
                    mArrow6 = Integer.parseInt(mArrow6EditText.getText().toString());
                } catch (Exception e) {
                    mArrow6 = 0;
                }
                int[] arrows = {
                        mArrow1,
                        mArrow2,
                        mArrow3,
                        mArrow4,
                        mArrow5,
                        mArrow6,
                };
                Calendar c = Calendar.getInstance();
                String datetime = c.get(Calendar.YEAR)+ "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":";
                if (c.get(Calendar.MINUTE) < 10) {
                    datetime = datetime + "0" + c.get(Calendar.MINUTE);
                } else {
                    datetime = datetime + c.get(Calendar.MINUTE);
                }

                int distance;
                try {
                    int numberOfArrows=Integer.parseInt(mTotalArrowsEditText.getText().toString());
                    distance = Integer.parseInt(mDistanceEditText.getText().toString());
                    String location = mLocationEditText.getText().toString();
                    //String units = mUnitsEditText.getText().toString();
                    if (location.equalsIgnoreCase("")) {
                        throw new Exception();
                    }
                    String units = String.valueOf(mUnitsSpinner.getSelectedItem());

                    String style;
                    if (mRadioGroup.getCheckedRadioButtonId() == R.id.radioButton) {
                        style = "Barebow";
                    } else {
                        style = "Sights";
                    }
                    String indoorOutdoor;
                    if (mIndoorOutdoorRadioGroup.getCheckedRadioButtonId() == R.id.indoorRadioButton) {
                        indoorOutdoor = "Indoor";
                    } else {
                        indoorOutdoor = "Outdoor";
                    }

                    mDatabaseHelper.insertScore(datetime, arrows, distance, location, style, indoorOutdoor, numberOfArrows, units);
                    Intent intent = new Intent(AddScoreActivity.this, MainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddScoreActivity.this);
                    builder.setMessage("Please enter a value for all fields")
                            .setTitle("Opps!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

    }


    // add items into units spinner dynamically
    public void addItemsOnUnitsSpinner() {

        mUnitsSpinner = (Spinner) findViewById(R.id.units_spinner);
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                R.array.units_array, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUnitsSpinner.setAdapter(dataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
