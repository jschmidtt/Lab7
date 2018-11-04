package edu.temple.lab7;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class WebViewFragment extends Fragment {

    View v; //View
    WebView webView; //WebView
    Context context; //Context/Parent
    Button backButton,forwardButton; //Back and Forward Button
    String URL; //My URL

    public static String WEB_KEY = "web_key"; //web_key
    public static String URL_KEY = "url_key"; //url_key
    public WebViewFragment() {
        // Required empty public constructor
    }

    //Grab the context/parent
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    //Grab URL from bundle
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        URL = bundle.getString(WEB_KEY);
    }

    //Create a new WebViewFragment with the URL from the parent's URL Bar/Edit Text
    public static WebViewFragment newInstance(String URL){
        //Create instances of the Fragment
        WebViewFragment webViewFragment = new WebViewFragment();

        //Passing Arguments && Creating Bundle
        Bundle bundle = new Bundle();
        bundle.putString(WebViewFragment.WEB_KEY, URL);
        webViewFragment.setArguments(bundle);

        return webViewFragment;
    }

    //Set up the WebView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_web_view, container, false);

        backButton = v.findViewById(R.id.backButton);
        forwardButton = v.findViewById(R.id.forwardButton);

        webView = v.findViewById(R.id.webView);
        webView.setWebViewClient(new HelloWebViewClient());

        //If this WebView was previously loaded grab the old URL and load it.
        if(savedInstanceState != null){
            URL = savedInstanceState.getString(URL_KEY);
        }

        loadUrlFromTextView();

        //Handle WebView Back Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(webView.canGoBack()) webView.goBack();
            }
        });

        //Handle WebView Forward Button
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoForward()) webView.goForward();
            }
        });

        return v;
    }


    //URL METHOD
    public void loadURL(String url){
        URL = url;
        loadUrlFromTextView();
    }

    //Above and this method need to be cleaned, should be only one method here
    private void loadUrlFromTextView() {
        webView.loadUrl(URL);
    }

    //WebView client to handle overriding default browser and updating the URL for the parent's url bar
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            ((GetURL)context).getURL(url, view);
            URL = url;
        }

    }

    //Interface for parent for updating the URL Bar
    interface GetURL {
        void getURL(String loadedURL, WebView webViewPassed);
    }

    //If this WebView is being pushed out of memory save the URL for future loading upon return
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(URL_KEY, URL);
        super.onSaveInstanceState(outState);
    }
}
