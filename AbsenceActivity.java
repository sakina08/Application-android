package com.kosalgeek.android.appitis;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.LayoutInflater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kosalgeek.android.appitis.R.layout.activity_column;


public class AbsencesActivity extends ActionBarActivity {

    private String jsonResult;
    private ListView lv;
    private String url = "http://172.16.10.25/AppITIS/etudiant.php";
    private TextView text1;
    private RadioGroup choiabs;
    private RadioButton pres;
    private RadioButton abs;
    private Button envoyer;
    private TextView mCountTextView;
    private ArrayList<RowObject> mSource;
    List<Map<String,String>> eleveList = new ArrayList<Map<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absences);
        lv = (ListView) findViewById(R.id.lv);
       // lv.setAdapter(new RadioButtonAdapter(getApplicationContext(), mSource));
        choiabs = (RadioGroup) findViewById(R.id.choiabs);
        pres =(RadioButton)findViewById(R.id.pres);
        abs =(RadioButton)findViewById(R.id.abs);
        accessWebService();
        // Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // text1 = (TextView) findViewById(R.id.textView1);

    }
private class RadioButtonAdapter extends ArrayAdapter<RowObject> {
    class ViewHolder {
        RadioGroup rbGroup;
        RadioButton btn1;
        RadioButton btn2;
    }

    private LayoutInflater mInflater;

    public RadioButtonAdapter(Context context, ArrayList<RowObject> mSource) {
        super(context, R.layout.row, mSource);
        mInflater = LayoutInflater.from(context);
    }

    public View getView(final int position, View ConvertView, ViewGroup parent){

        //System.out.println(" --- position ---"+position+" --- firstChecked[position] --- "+firstChecked[position]);

        ViewHolder holder;

        if (ConvertView == null) {
            ConvertView = mInflater.inflate(R.layout.activity_column, null);

            holder = new ViewHolder();
            holder.rbGroup = (RadioGroup) ConvertView.findViewById(R.id.choiabs);
            holder.btn1 = (RadioButton) ConvertView.findViewById(R.id.pres);

            ConvertView.setTag(holder);
        } else {
            holder = (ViewHolder) ConvertView.getTag();
        }
        holder.rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int count =0;
               switch (checkedId){
                   case R.id.pres:
                       System.out.println(" --- pres ---"+" position "+position+" isChecked[position] "+mSource.get(position));
                       count --;
                       mSource.get(position).setFirstChecked(true);
                       break;
                   case R.id.abs:
                       System.out.println(" --- abs ---"+" position "+position+" isChecked[position] "+mSource.get(position));
                       count ++;
                       mSource.get(position).setFirstChecked(false);
                       break;

               }//fin de switch
                mCountTextView.setText("There are " + getNumberOfFirstCheckedViews() + " first buttons selected");
            }

            private int getNumberOfFirstCheckedViews() {
                int count = 0;
                for (RowObject object : mSource) {
                    if (object.isFirstChecked()) {
                        count++;
                    }
                }
                return count;
            }

        });
        if (mSource.get(position).isFirstChecked()) {
            holder.btn1.setChecked(true);
            holder.btn2.setChecked(false);
        } else {
            holder.btn1.setChecked(false);
            holder.btn2.setChecked(true);
        }
        return ConvertView;
    }
}//fin class Radiobutton adaptater

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_absences, menu);
        return true;
    }

    // Async Task to access the web
    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params){

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
                //System.out.println(result);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            //text1.setText(result);
           ListDrwaer();

        }
    }// end async task

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array

        task.execute(new String[]{url});

    }

    // build hash set for list view
    private void ListDrwaer() {

        // rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
        try {
            JSONObject jsonReponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonReponse.getJSONArray("eleve");
            final ArrayList<HashMap<String,String>> MyArrlist = new ArrayList<HashMap<String, String>>();
             mSource = new ArrayList<RowObject>();
            for (int i = 0; i <jsonMainNode.length(); i++) {
              mSource.add(new RowObject(i, false));
             lv.setAdapter(new RadioButtonAdapter(getApplicationContext(), mSource));
            }
            HashMap map;
            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject c = jsonMainNode.getJSONObject(i);
                map = new HashMap<String,String>();
                map.put("nom_eleve",c.getString("nom_eleve"));
                map.put("prenom_eleve",c.getString("prenom_eleve"));
                MyArrlist.add(map);
                SimpleAdapter $adap;
                //List<Etudiant> lis = new ArrayList<Etudiant>();
                //Etudiant e = new Etudiant();
                //e.setNom("cfg");
                //e.setPrenom("hjdg");
                $adap = new SimpleAdapter(AbsencesActivity.this,MyArrlist, activity_column,new String[]{"nom_eleve","prenom_eleve"},new int[]{R.id.Colid_Eleve,R.id.nom_eleve});
                lv.setAdapter($adap);



            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error...." + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }//fin de listdrawer


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
