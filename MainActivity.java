package com.kosalgeek.android.appitis;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
   // final String LOG = "MainActivity";
    EditText textuser,textmdp;
    Button butncon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        textuser = (EditText) findViewById((R.id.textuser));
        textmdp = (EditText) findViewById((R.id.textmdp));
        butncon = (Button) findViewById((R.id.butncon));
        butncon.setOnClickListener( this);

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        HashMap postData = new HashMap();
        String login = textuser.getText().toString();
        String mdp = textmdp.getText().toString();
        postData.put("butncon","Conexion");
        postData.put("mobile", "android");
        postData.put("txtUsername",login);
        postData.put("txtPassword",mdp);
        PostResponseAsyncTask task = new PostResponseAsyncTask(MainActivity.this,postData,new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                //Log.d(LOG, s);
               // s = s.trim();

                if(s.contains("success")){
                    Toast.makeText(MainActivity.this,"successfuly login", Toast.LENGTH_LONG).show();
                    Intent in = new Intent(MainActivity.this,ListActivity2Activity.class);
                    //finish();
                    startActivity(in);

                }
                else{
                    Toast.makeText(MainActivity.this, "veillez vous connecter avec les bons identifiants", Toast.LENGTH_LONG).show();
                }
            }

        });
        task.execute("http://172.16.10.25/AppITIS/conexion.php");
    }
}
