package edu.temple.lab7;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();
    Button goButton;
    TextView textViewURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);

        textViewURL = findViewById(R.id.urlEditText);

        //Main Page Home Page
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewFragment webViewFragment = WebViewFragment.newInstance(textViewURL.getText().toString());
                fm.beginTransaction().replace(R.id.container_1, webViewFragment).addToBackStack(null).commit();
            }
        });
    }

    //ActionBar---Inflate Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //ActionBar---Handling Menu Clicks
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_backward){
            //Action Backward
        } else if (id == R.id.action_forward){
            //Action Forward
        } else if (id == R.id.action_new){
            //Action New
        }

        return super.onContextItemSelected(item);
    }
}
