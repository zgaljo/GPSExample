package com.example.adrianzgaljic.gpsexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by adrianzgaljic on 19/10/15.
 */
public class FindFriends extends Activity {


    private ArrayAdapter<String> adapter;
    public static final String TAG = "logIspis";
    private ListView  listView;
    private ArrayList<String> friendsSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        final TextView tvResult = (TextView) findViewById(R.id.tvResults);

        friendsSearchResult = new ArrayList<String>();

        listView = (ListView) findViewById(R.id.lvFriendsSearch);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friendsSearchResult);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = adapter.getItem(position);
                String link = "http://192.168.5.93:8080/android_connect/get_color.php?user="+selectedValue;
                DBCheckUser checkUser = new DBCheckUser(link);
                checkUser.execute();
                while (checkUser.getResult()==null);
                Intent intent = new Intent(FindFriends.this, FriendProfile.class);
                intent.putExtra("user", selectedValue);
                if (checkUser.getResult().equals("red")){
                    intent.putExtra("color", Color.RED);
                } else if (checkUser.getResult().equals("green")){
                    intent.putExtra("color", Color.GREEN);
                } else if (checkUser.getResult().equals("yellow")){
                    intent.putExtra("color", Color.YELLOW);
                } else {
                    intent.putExtra("color", Color.BLUE);
                }
                Toast.makeText(FindFriends.this, selectedValue, Toast.LENGTH_SHORT).show();
                intent.putExtra("action", "add");
                startActivity(intent);
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etSearch.getText().toString();
                if (query.equals("")) {
                    Toast.makeText(FindFriends.this, "Empty input", Toast.LENGTH_SHORT);
                } else {
                    String link = "http://192.168.5.93:8080/android_connect/find_users.php?user=" + query;
                    DBCheckUser checkUser = new DBCheckUser(link);
                    checkUser.execute();
                    while (checkUser.getResult() == null) ;
                    tvResult.setText(checkUser.getResult());
                    friendsSearchResult.clear();
                    friendsSearchResult.addAll(Arrays.asList(checkUser.getResult().split("\\s+")));
                    adapter.notifyDataSetChanged();




                }
            }
        });




    }

}
