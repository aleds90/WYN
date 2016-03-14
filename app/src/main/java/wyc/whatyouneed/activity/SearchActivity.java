package wyc.whatyouneed.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;

import wyc.whatyouneed.R;
import wyc.whatyouneed.entity.ClientLocalStore;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private static final long RIPPLE_DURATION = 250;
    LinearLayout lo_center;
    AutoCompleteTextView actv_name,actv_role,actv_price,actv_city;
    ImageButton ib_home,ib_search,ib_relation,ib_profile;
    SeekBar sb_price;
    Button btn_startsearch;
    ListView lv_result;
    ImageView iv_navigation;
    ClientLocalStore localStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViewById();

    }

    private void findViewById() {
        localStore = new ClientLocalStore(this);

        lo_center = (LinearLayout)findViewById(R.id.layout_search_center);

        iv_navigation = (ImageView)findViewById(R.id.img_search_ic_navigation);

        actv_name = (AutoCompleteTextView)findViewById(R.id.actv_search_name);
        actv_role = (AutoCompleteTextView)findViewById(R.id.actv_search_role);
        actv_city = (AutoCompleteTextView)findViewById(R.id.actv_search_city);
        actv_price = (AutoCompleteTextView)findViewById(R.id.actv_search_price);

        sb_price = (SeekBar)findViewById(R.id.sb_search_price);

        btn_startsearch = (Button)findViewById(R.id.btn_search_startsearch);
        btn_startsearch.setOnClickListener(this);

        lv_result = (ListView)findViewById(R.id.lv_search_resultsearch);

        ib_home = (ImageButton)findViewById(R.id.ib_search_home);
        ib_home.setOnClickListener(this);

        ib_search = (ImageButton)findViewById(R.id.ib_search_search);
        ib_search.setOnClickListener(this);

        ib_relation = (ImageButton)findViewById(R.id.ib_search_relation);
        ib_relation.setOnClickListener(this);

        ib_profile = (ImageButton)findViewById(R.id.ib_search_profile);
        ib_profile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_search_home:
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.ib_search_profile:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
                break;
            case R.id.ib_search_relation:
                Intent relationIntent = new Intent(this, RelationActivity.class);
                startActivity(relationIntent);
                break;
        }


    }
}
