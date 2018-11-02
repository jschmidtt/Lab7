package edu.temple.lab7;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
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

public class MainActivity extends AppCompatActivity implements WebViewFragment.GetURL {

    FragmentManager fm = getSupportFragmentManager();
    Button goButton;
    TextView textViewURL;
    Boolean first = true;
    WebViewFragment wf;
    ArrayList fragmentList = new ArrayList();
    Integer i=0;

    ViewPager viewPager;
    WebViewFragment fragments[];

    //Get FSPA
    FragmentStatePagerAdapter fspa = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int i) {
            return fragments[i];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);

        textViewURL = findViewById(R.id.urlEditText);

        viewPager = findViewById(R.id.viewPager);

        fragments = new WebViewFragment[2];

        fragments[0] = WebViewFragment.newInstance("");
        fragments[1] = WebViewFragment.newInstance("");

        viewPager.setAdapter(fspa);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                getURL(fragments[i].URL);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //Main Page Home Page
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = viewPager.getCurrentItem();
                fragments[i].loadURL(textViewURL.getText().toString());
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

            }
            //Back Button
            case R.id.action_backward:{

            }
            //Forward Button
            case R.id.action_forward:{

            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getURL(String loadedURL) {
        textViewURL.setText(loadedURL);
    }
}
