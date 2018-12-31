package yauloui.webbrowser;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final String CURRENT_SITE = "currentSite";
    private String currentSite;
    private final String homeURL = "https://www.google.com";
    private static List<String> favesList = new ArrayList<String>();
    private static List<String> historyList = new ArrayList<String>();
    private String fave;
    private WebView webview = null;
    private EditText enterurl = null;
    static WebView staticWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        webview = (WebView) findViewById(R.id.webview);
        staticWebView= webview ;
        web(webview);
        loadURL(savedInstanceState);
        Log.d("MainActivity", "create: currentsite ="+currentSite);
    }

    public static void loadUrl(String url){
        staticWebView.loadUrl(url);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
         webview = (WebView) findViewById(R.id.webview);

        Bundle bundle = new Bundle();

        webview.saveState(bundle);

        outState.putBundle("webViewState", bundle);
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        Log.d("rotate", "onRestoreInstanceState: called ");
//        webview = (WebView) findViewById(R.id.webview);
//
//        super.onRestoreInstanceState(savedInstanceState);
//        if (savedInstanceState != null) {
//
//            webview.restoreState(savedInstanceState.getBundle("webViewState"));
//
//        }else{
//            Log.d("rotate", "onRestoreInstanceState: null ");
//
//            webview.loadUrl(homeURL);
//        }
//        // restore(webview, savedInstanceState);
//    }

    private void loadURL(Bundle savedInstanceState){
       webview = (WebView) findViewById(R.id.webview);

        if (savedInstanceState != null) {

            webview.restoreState(savedInstanceState.getBundle("webViewState"));

        }
//        else if(getCallingActivity()!=null && getCallingActivity().getClassName().equals("favourites")){
//            String favourite = getIntent().getStringExtra("url");
//            webview.loadUrl(favourite);
//        }
        else{
            webview.loadUrl(homeURL);
        }
    }

    public void onBackPressed() {
        webview = (WebView) findViewById(R.id.webview);
        if(getApplicationContext().equals("favourites")){
            setContentView(R.layout.activity_main2);
            //return;
        }
          else if (webview.canGoBack()) {
            webview.goBack();
            return;
        }
        else {

              // Otherwise defer to system default behavior.
              super.onBackPressed();
          }
    }





//    public void loadInitialLayout(){
//        setContentView(R.layout.activity_main);
//        web();
//    }

    public void web(WebView webview) {
         enterurl = (EditText) findViewById(R.id.enterURL);
        // getSupportActionBar().hide();
        webview.setWebViewClient(new WebViewClient());
      //  go(webview, enterurl);
        home(webview, homeURL);
        back(webview);
        forward(webview);
        keyboardGo(webview, enterurl);
        clear(enterurl);
        webviewSettings(webview);
        addFaves(webview);
       // showFaves(view);
    }



    public void webviewSettings(WebView webview){

        webview.getSettings().setJavaScriptEnabled(true);
        //makes pages display as mobile sites not desktop - https://stackoverflow.com/questions/21166228/android-webview-not-loading-mobile-site
        webview.getSettings().setLoadWithOverviewMode(true);
         webview.getSettings().setUseWideViewPort(true);

         webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview.setScrollbarFadingEnabled(false);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void clear(final EditText enterurl) {
        enterurl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.d("MainActivity", "onClick: should clear ");
                    enterurl.setText("");

                }
                return false;
            }
        });
    }

    public void keyboardGo(final WebView webview, final EditText enterurl) {

        enterurl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_GO) {
                    Log.d("MainActivity", "onClick: keygo clicked");
                    String url = "https://www." + enterurl.getText().toString();
                    webview.loadUrl(url);
                    return true;
                }
                return false;
            }
        });
    }



    public void home(final WebView webview, final String homeURL) {

        Button home = (Button) findViewById(R.id.homebtn);
        webview.setWebViewClient(new WebViewClient());
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("MainActivity", "onClick: home clicked");
                webview.loadUrl(homeURL);
            }
        });
    }

    public void back(final WebView webview) {
        Button back = (Button) findViewById(R.id.backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (webview.canGoBack()) {
                    webview.goBack();
                }
            }
        });
    }

    public void forward(final WebView webview) {
        Button forward = (Button) findViewById(R.id.forwardbtn);
        forward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (webview.canGoForward()) {
                    webview.goForward();
                }
            }
        });
    }

    public void history(final WebView webview) {

        String historyUrl = "";
        WebBackForwardList mWebBackForwardList = webview.copyBackForwardList();
        if (mWebBackForwardList.getCurrentIndex() > 0)
            historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex() - 1).getUrl();

        Log.d("MainActivity", "onResume:" + historyUrl);


    }



    public void addFaves(final WebView webview){

        ImageButton faveBtn = (ImageButton)findViewById(R.id.favebtn);

        faveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fave = webview.getUrl();
                favesList.add(fave);
                Log.d("fave", "onPageFinished: "+fave);
                for(int i = 0; i< favesList.size(); i++){
                    Log.d("faveArray", "faveslist:"+ i+"="+ favesList.get(i));
                }
            }
        });
    }

    public static List getFavesList()
    {
        return favesList;
    }

    public void showHistory(View view) {
        String historyUrl = "";
        WebBackForwardList myWebBackForwardList = webview.copyBackForwardList();
        for(int i=0;i<myWebBackForwardList.getSize();i++) {
            historyUrl = myWebBackForwardList.getItemAtIndex(i).getUrl();
            historyList.add(historyUrl);
        }

        Intent intent = new Intent(MainActivity.this, history.class);
        startActivity(intent);

    }

    public static List getHistoryList()
    {

        return historyList;
    }

    public void showFaves(View view)
    {
        Intent intent = new Intent(MainActivity.this, favourites.class);
        startActivity(intent);
    }


}