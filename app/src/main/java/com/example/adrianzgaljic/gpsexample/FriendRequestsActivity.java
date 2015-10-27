package com.example.adrianzgaljic.gpsexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by adrianzgaljic on 20/10/15.
 */
public class FriendRequestsActivity extends Activity {

    ArrayAdapter<String> adapter;
    public static final String TAG = "logIspis";
    ListView listView;
    ArrayList<String> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests);
        array = new ArrayList<String>();
        array = UserInfo.getFriendRequests();

        listView = (ListView) findViewById(R.id.lvFriendsRequests);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = (String) adapter.getItem(position);
                String link = "http://192.168.5.93:8080/android_connect/get_color.php?user=" + selectedValue;
                DBCheckUser checkUser = new DBCheckUser(link);
                checkUser.execute();
                while (checkUser.getResult() == null) ;
                Intent intent = new Intent(FriendRequestsActivity.this, FriendProfile.class);
                intent.putExtra("user", selectedValue);
                if (checkUser.getResult().equals("red")) {
                    intent.putExtra("color", Color.RED);
                } else if (checkUser.getResult().equals("green")) {
                    intent.putExtra("color", Color.GREEN);
                } else if (checkUser.getResult().equals("yellow")) {
                    intent.putExtra("color", Color.YELLOW);
                } else {
                    intent.putExtra("color", Color.BLUE);
                }
                Toast.makeText(FriendRequestsActivity.this, selectedValue, Toast.LENGTH_SHORT).show();
                intent.putExtra("action", "accept");
                startActivity(intent);
            }
        });
    }
}
