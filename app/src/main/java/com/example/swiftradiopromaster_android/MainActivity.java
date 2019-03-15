package com.example.swiftradiopromaster_android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MainActivity extends AppCompatActivity {

    // Array of strings...

    List<String> stationName = new ArrayList<String>();

    List<String> genreArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.customlayout, stationName);


        get_json();
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        customAdapter customAdapter = new customAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Hello " + stationName.get(position),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SongPlayActivity.class);
                //EditText editText = view.findViewById(R.id.label);

                intent.putExtra("title",stationName.get(position).toString());
                intent.putExtra("songUrl",stationName.get(position).toString());
                //intent.putExtra("title","hello");
                startActivity(intent);

            }
        });
    }




    class customAdapter extends BaseAdapter{
        @Override
        public int getCount(){
            return stationName.size();
        }

        @Override
        public Object getItem(int position){
            return null;
        }

        @Override
        public long getItemId(int position){
            return 0;
        }

        @Override
        public View getView(int position, View content, ViewGroup parent)

        {
            View view = getLayoutInflater().inflate(R.layout.customlayout,null);

            TextView mtextView = view.findViewById(R.id.label);
            mtextView.setText(stationName.get(position));

            TextView genreTextView = view.findViewById(R.id.genrelabel);
            genreTextView.setText(genreArray.get(position));
            return view;
        }
    }


    // To read json from asset folder
    public void get_json()
    {
        String json;

        try{

            InputStream ins =  getResources().getAssets().open("songStation.json");
            int size = ins.available();
            byte[] buffer = new byte[size];
            ins.read(buffer);
            ins.close();
            json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            // Getting JSON Array node
            JSONArray jarray = (JSONArray) jsonObject.getJSONArray("station");

            for(int i=0; i< jarray.length(); i++)
            {
                JSONObject finalObject = jarray.getJSONObject(i);

                String trackName = finalObject.getString("name");
                String genreName = finalObject.getString("genre");
                stationName.add(trackName);
                genreArray.add(genreName);
            }

           // Log.d("tag length", String.valueOf(jarray.length()));

        }catch(IOException e){
                e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
