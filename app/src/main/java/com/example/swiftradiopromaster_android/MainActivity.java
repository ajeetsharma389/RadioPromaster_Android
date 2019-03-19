package com.example.swiftradiopromaster_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.example.swiftradiopromaster_android.MySingleton.*;


public class MainActivity extends AppCompatActivity {

    // Array of strings...

    List<String> stationName = new ArrayList<String>();

    List<String> genreArray = new ArrayList<String>();
    MySingleton mySingleton;

    //List<getJsonData> finalArray = new ArrayList<getJsonData>();

    public  getJsonData getJsonDataobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.customlayout, stationName);

        final MySingleton mySingleton = getInstance();
        get_json();
        ListView listView = findViewById(R.id.mobile_list);
        customAdapter customAdapter = new customAdapter();


        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Hello " + position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SongPlayActivity.class);

                Object objectCliked = finalArray.get(position);
                String title = ((getJsonData) objectCliked).getTrackName();

                intent.putExtra("title",title);
                intent.putExtra("songUrl",((getJsonData) objectCliked).getStreamURL());
                int index = position;
                intent.putExtra("clickedIndex",index);
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
            json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            // Getting JSON Array node
            JSONArray jarray = jsonObject.getJSONArray("station");

            for(int i=0; i< jarray.length(); i++)
            {
                JSONObject finalObject = jarray.getJSONObject(i);

                String trackName = finalObject.getString("name");
                String genreName = finalObject.getString("genre");
                String streamUrl = finalObject.getString("streamURL");
                stationName.add(trackName);
                genreArray.add(genreName);
                getJsonDataobj = new getJsonData(trackName,genreName,streamUrl);
                finalArray.add(getJsonDataobj);
            }


        }catch(IOException e){
                e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
