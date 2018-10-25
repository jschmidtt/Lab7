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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();
    Button goButton;
    TextView textViewURL;
    Boolean first = true;
    WebViewFragment wf;
    ArrayList fragmentList = new ArrayList();
    Integer i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);

        textViewURL = findViewById(R.id.urlEditText);

        wf = WebViewFragment.newInstance("");

        //Main Page Home Page
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (first) {
                    first = false;
                    fm.beginTransaction().replace(R.id.container_1, wf).addToBackStack(null).commit();
                    wf.loadURL(textViewURL.getText().toString());
                } else {
                    wf.loadURL(textViewURL.getText().toString());
                }
            }
        });
    }

    //ActionBar---Inflate Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //ActionBar---ButtonPressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            //New Tab Button
            case R.id.action_new:{
                //Save current
                fragmentList.add(i,wf);
                Toast.makeText(this, i.toString(), Toast.LENGTH_LONG);
                i++;
                //Reset URL Text
                textViewURL.setText("");
                //Start Up Fresh Fragment
                wf = WebViewFragment.newInstance("");
                fm.beginTransaction().replace(R.id.container_1, wf).addToBackStack(null).commit();
                break;
            }
            //Back Button
            case R.id.action_backward:{
                fragmentList.add(i,wf);
                Toast.makeText(this, i.toString(), Toast.LENGTH_LONG);
                i--;
                wf = (WebViewFragment) fragmentList.get(i);
                fm.beginTransaction().replace(R.id.container_1, wf).addToBackStack(null).commit();
                break;
            }
            //Forward Button
            case R.id.action_forward:{
                fragmentList.add(i,wf);
                Toast.makeText(this, i.toString(), Toast.LENGTH_LONG);
                i++;
                wf = (WebViewFragment) fragmentList.get(i);
                fm.beginTransaction().replace(R.id.container_1, wf).addToBackStack(null).commit();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
