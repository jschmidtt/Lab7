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

    View v;
    WebView webView;
    TextView urlTextView;
    Context context;
    Button backButton,forwardButton;

    String URL;

    public static String WEB_KEY = "web_key";

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        URL = bundle.getString(WEB_KEY);
    }

    public static WebViewFragment newInstance(String URL){
        //Create instances of the Fragment
        WebViewFragment webViewFragment = new WebViewFragment();

        //Passing Arguments && Creating Bundle
        Bundle bundle = new Bundle();
        bundle.putString(WebViewFragment.WEB_KEY, URL);
        webViewFragment.setArguments(bundle);

        return webViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_web_view, container, false);

        backButton = v.findViewById(R.id.backButton);
        forwardButton = v.findViewById(R.id.forwardButton);

        webView = v.findViewById(R.id.webView);
        webView.setWebViewClient(new HelloWebViewClient());

        if(savedInstanceState != null){
            URL = savedInstanceState.getString("URL_KEY");
        }

        loadUrlFromTextView();

        //Handle Webview Back Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(webView.canGoBack()) webView.goBack();
            }
        });

        //Handle Webview Forward Button
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

    private void loadUrlFromTextView() {
        new Thread(){
            public void run(){
                //Grab url from urlTextView
                String sb = URL;
                Message msg = Message.obtain();
                msg.obj = sb;
                responseHandler.sendMessage(msg);
            }
        }.start();
    }

    //Handler
    Handler responseHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT).show();
            webView.loadUrl((String) msg.obj);
            return false;
        }
    });

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            ((GetURL)context).getURL(url);
            URL = url;
        }

    }

    interface GetURL {
        void getURL(String loadedURL);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("URL_KEY", URL);
        super.onSaveInstanceState(outState);
    }
}
