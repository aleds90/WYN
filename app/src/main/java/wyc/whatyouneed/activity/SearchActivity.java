package wyc.whatyouneed.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

import wyc.whatyouneed.R;
import wyc.whatyouneed.adpter.AdapterListUser;
import wyc.whatyouneed.entity.ClientLocalStore;
import wyc.whatyouneed.entity.User;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private static final long RIPPLE_DURATION = 250;
    FrameLayout lo_center;
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


    //// TODO: 14/03/2016 da togliere
    private ArrayList<User> setListViewExample() {

        final ArrayList<User> list = new ArrayList<>();
        User user = new User();
        user.setName("Alessandro");
        user.setSurname("Di Stefano");
        user.setCity("Roma");
        user.setRole("Programmatore");

        User user1 = new User();
        user1.setName("Marco");
        user1.setSurname("Di Stefano");
        user1.setCity("Napoli");
        user1.setRole("Pittore");

        User user2 = new User();
        user2.setName("Marco");
        user2.setSurname("Santoni");
        user2.setCity("Roma");
        user2.setRole("Programmatore");

        User user3 = new User();
        user3.setName("Luca");
        user3.setSurname("Geonoa");
        user3.setCity("Milano");
        user3.setRole("Pittore");

        list.add(user);
        list.add(user1);
        list.add(user3);
        list.add(user2);
        list.add(user2);
        list.add(user2);
        list.add(user2);
        list.add(user2);


        return list;
    }

    private void findViewById() {
        localStore = new ClientLocalStore(this);
        lo_center = (FrameLayout)findViewById(R.id.layout_search_center);
        iv_navigation = (ImageView)findViewById(R.id.img_search_ic_navigation);

        actv_name = (AutoCompleteTextView)findViewById(R.id.actv_search_name);
        actv_role = (AutoCompleteTextView)findViewById(R.id.actv_search_role);
        actv_city = (AutoCompleteTextView)findViewById(R.id.actv_search_city);
        actv_price = (AutoCompleteTextView)findViewById(R.id.actv_search_price);

        sb_price = (SeekBar)findViewById(R.id.sb_search_price);
        sb_price.setMax(300);
        sb_price.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                actv_price.setText(sb_price.getProgress()+"€");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


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
            case R.id.btn_search_startsearch:
                if (btn_startsearch.getText() == (getResources().getString(R.string.label_startsearch))) {
                    lo_center.setVisibility(View.GONE);
                    btn_startsearch.setText(getResources().getString(R.string.label_backtofilter));
                    lv_result.setAdapter(new AdapterListUser(setListViewExample(), getApplicationContext()));
                    lv_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            User itemUser = (User) lv_result.getAdapter().getItem(position);
                            Intent intent = new Intent(SearchActivity.this, UserActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            passUserByIntent(intent, itemUser);
                            getApplicationContext().startActivity(intent);
                        }
                    });
                }else {
                    lo_center.setVisibility(View.VISIBLE);
                    btn_startsearch.setText(getResources().getString(R.string.label_startsearch));
                }

                break;
        }




    }
    private void passUserByIntent(Intent intent, User user) {
        intent.putExtra("id_user", user.getId_user());
        intent.putExtra("name", user.getName());
        intent.putExtra("cognome", user.getSurname());
        intent.putExtra("email", user.getEmail());
        intent.putExtra("city", user.getCity());
        intent.putExtra("role", user.getRole());
        intent.putExtra("bday", user.getBday());
        intent.putExtra("rate", user.getRate());
        intent.putExtra("status", user.isActive());
        intent.putExtra("description", user.getDescription());
        intent.putExtra("avatar", user.getAvatar());
    }
}
