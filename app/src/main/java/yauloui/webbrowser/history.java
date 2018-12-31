package yauloui.webbrowser;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class history extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.list_textview,MainActivity.getHistoryList());

            ListView listView = (ListView) findViewById(R.id.historyListView);
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
                Toast.makeText(history.this,"press back to view " +url,Toast.LENGTH_LONG).show();
            }
        });
    }

        }









