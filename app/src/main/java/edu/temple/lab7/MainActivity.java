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
    ArrayList <WebViewFragment> fragmentList = new ArrayList();
    Integer i=0;

    ViewPager viewPager;
    WebViewFragment fragments[];

    //Get FSPA
    FragmentStatePagerAdapter fspa = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);

        textViewURL = findViewById(R.id.urlEditText);

        viewPager = findViewById(R.id.viewPager);

        //fragments = new WebViewFragment[2];

        //fragments[0] = WebViewFragment.newInstance("");
        //fragments[1] = WebViewFragment.newInstance("");

        fragmentList.add(WebViewFragment.newInstance(""));

        viewPager.setAdapter(fspa);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                getURL(fragmentList.get(i).URL);
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
                fragmentList.get(i).loadURL(textViewURL.getText().toString());
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
        int tab = fragmentList.size()-1;

        switch (id){
            //New Tab Button
            case R.id.action_new:{
                fragmentList.add(WebViewFragment.newInstance(""));
                fspa.notifyDataSetChanged();
                tab++;
                viewPager.setCurrentItem(tab);
                break;
            }
            //Back Button
            case R.id.action_backward:{
                if(tab > 0){
                    tab--;
                    fspa.notifyDataSetChanged();
                    viewPager.setCurrentItem(tab);
                    break;
                }
            }
            //Forward Button
            case R.id.action_forward:{

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getURL(String loadedURL) {
        textViewURL.setText(loadedURL);
    }
}
