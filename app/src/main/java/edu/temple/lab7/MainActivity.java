package edu.temple.lab7;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements WebViewFragment.GetURL {

    Button goButton; //URL Go Button
    TextView textViewURL; //URL Bar
    ArrayList <WebViewFragment> fragmentList = new ArrayList(); //Array list to hold tabs aka the WebView fragments
    Integer tab; //Keep Track of current tab/WebView in view.
    ViewPager viewPager; //ViewPager for WebView Fragments

    //Get FSPA to handle swipes and current tabs
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

        fragmentList.add(WebViewFragment.newInstance(""));

        viewPager.setAdapter(fspa);

        //View Pager for when an item is in view -> update the url text bar
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                textViewURL.setText(fragmentList.get(i).URL);
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

        switch (id){
            //New Tab Button
            case R.id.action_new:{
                //Add a new tab to the fragment list and set current tab to new tab.
                fragmentList.add(WebViewFragment.newInstance(""));
                fspa.notifyDataSetChanged();
                tab = fragmentList.size()-1;
                viewPager.setCurrentItem(tab);
                return true;
            }
            //Back Button
            case R.id.action_backward:{
                //Grab Current Tab
                tab = viewPager.getCurrentItem();
                //If there is more than one tab move backwards
                if(tab > 0){
                    tab--;
                    fspa.notifyDataSetChanged();
                    viewPager.setCurrentItem(tab);
                    return true;
                }else  if (tab == 0){ //If tab is at zero but still more than one tab loop around.
                    //Could comment this out if you just want it to do nothing instead of looping.
                    tab = fragmentList.size()-1;
                    fspa.notifyDataSetChanged();
                    viewPager.setCurrentItem(tab);
                    return true;
                }
            }
            //Forward Button
            case R.id.action_forward:{
                //Grab Current Tab
                tab = viewPager.getCurrentItem();
                //If not on last tab move forward.
                if(tab < fragmentList.size()-1){
                    tab++;
                    fspa.notifyDataSetChanged();
                    viewPager.setCurrentItem(tab);
                    return true;
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //Set the text of the url bar.
    @Override
    public void getURL(String loadedURL, WebView webViewPassed) {
        //only change url if the current webview is visible
        if(fragmentList.get(viewPager.getCurrentItem()).webView == webViewPassed) textViewURL.setText(loadedURL);
    }
}
