package me.attwoodthomas.archeryscorelog;

import android.app.ListFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import me.attwoodthomas.archeryscorelog.database.helper.DatabaseHelper;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        if (id == R.id.action_new) {
            Intent intent = new Intent(MainActivity.this, AddScoreActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public static class ScoreListFragment extends ListFragment {

        public static ArrayList<HashMap<String, String>> mScores;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.list_fragment, container, false);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
            mScores = databaseHelper.getAllScores();

            String[] keys = {"DateTime", "Total"};
            int[] ids = {android.R.id.text1, android.R.id.text2};

            SimpleAdapter adapter = new SimpleAdapter(getActivity(), mScores, android.R.layout.simple_list_item_2, keys, ids);
            setListAdapter(adapter);

        }

        public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);
            Intent intent = new Intent(getActivity(), ViewScoreActivity.class);
            intent.putExtra("DateTime", mScores.get(position).get("DateTime"));
            intent.putExtra("Total", mScores.get(position).get("Total"));
            startActivity(intent);
        }


    }
}
