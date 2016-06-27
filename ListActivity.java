package com.kosalgeek.android.appitis;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ListActivity2Activity extends ActionBarActivity {
    final String LOG = "MainActivity";
    private Button btnabs;
    private Button btnctext;
    private Button btnnote;
    private Button btncom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity2);
        btnabs = (Button) findViewById((R.id.btnabs));
       // btnctext = (Button) findViewById((R.id.btnctext));
       // btnnote= (Button) findViewById((R.id.btnnote));
        //btncom = (Button) findViewById((R.id.btncom));
        btnabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ListActivity2Activity.this,AbsencesActivity.class);

                startActivity(in);
               // finish();

            }
        });
        btnctext = (Button) findViewById((R.id.btnctext));
        btnctext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ListActivity2Activity.this,cahtextActivity.class);
                startActivity(in);
                //finish();
            }
        });
        btnnote = (Button) findViewById((R.id.btnnote));
        btnnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(ListActivity2Activity.this,NoteActivity.class);
                startActivity(in);
            }
        });
        btncom = (Button) findViewById((R.id.btncom));
        btncom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(ListActivity2Activity.this,CommentaireActivity.class);
                startActivity(in);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
