package yauloui.webbrowser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class favourites extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);


                 ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.list_textview,MainActivity.getFavesList());

            final ListView listView = (ListView) findViewById(R.id.favListView);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView list_textview = (TextView) view.findViewById(R.id.ListTextView);
                    String url = list_textview.getText().toString();
                    MainActivity.loadUrl(url); // if this is used user needs to use phone back button to return to
                    //web browser (which displays the clicked link (work around maybe invoke back action?)

                 //   setContentView(R.layout.activity_main2);//this option loads blank layout

                    // this option probably wipes the saved favourites and history
//                    Intent intent = new Intent(favourites.this, MainActivity.class);
//                    intent.putExtra("url", url); // except for ActivityA value = mIdFromA
//                    startActivity(intent);
                    Toast.makeText(favourites.this,"press back to view " +url,Toast.LENGTH_LONG).show();
                }
            });
        }
    }

      //  restore(savedInstanceState);







