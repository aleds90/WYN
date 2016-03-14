package wyc.whatyouneed.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import wyc.whatyouneed.R;
import wyc.whatyouneed.entity.ClientLocalStore;
import wyc.whatyouneed.entity.User;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_name, tv_surname, tv_bday, tv_city, tv_role, tv_feedback, tv_followers, tv_followeed;
    TextView tv_description, tv_notice, tv_email, tv_password;
    ImageButton ib_home,ib_search,ib_relation,ib_profile;
    ImageView img_avatar;
    FloatingActionButton fab_edit, fab_gallery, fab_location;
    ClientLocalStore localStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        localStore = new ClientLocalStore(this);
        findViewById();

    }

    private void findViewById() {
        tv_name     = (TextView)findViewById(R.id.tv_profile_name);
        tv_name.setText(localStore.getUser().getName());
        tv_surname  = (TextView)findViewById(R.id.tv_profile_surname);
        tv_surname.setText(localStore.getUser().getSurname());
        tv_bday     = (TextView)findViewById(R.id.tv_profile_bday);
        tv_bday.setText(localStore.getUser().getBday());
        tv_city     = (TextView)findViewById(R.id.tv_profile_city);
        tv_city.setText(localStore.getUser().getCity());
        tv_role     = (TextView)findViewById(R.id.tv_profile_role);
        tv_role.setText(localStore.getUser().getRole());
        tv_email    = (TextView)findViewById(R.id.tv_profile_email);
        tv_email.setText(localStore.getUser().getEmail());
        tv_password = (TextView)findViewById(R.id.tv_profile_password);
        tv_password.setText(localStore.getUser().getPassword());

        tv_feedback     = (TextView)findViewById(R.id.tv_profile_feedback);
        tv_followers    = (TextView)findViewById(R.id.tv_profile_followers);
        tv_followeed    = (TextView)findViewById(R.id.tv_profile_followeed);

        tv_description  = (TextView)findViewById(R.id.tv_profile_description);
        tv_description.setText(localStore.getUser().getDescription());
        tv_notice       = (TextView)findViewById(R.id.tv_profile_notice);

        img_avatar      = (ImageView)findViewById(R.id.img_profile_avatar);
        int avatar = localStore.getUser().getAvatarID(localStore.getUser().getRole());
        img_avatar.setImageDrawable(getResources().getDrawable(avatar));


        fab_edit        = (FloatingActionButton)findViewById(R.id.fab_profile_edit);
        fab_gallery     = (FloatingActionButton)findViewById(R.id.fab_profile_gallery);
        fab_location    = (FloatingActionButton)findViewById(R.id.fab_profile_location);
        fab_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, LocationActivity.class);
                intent.putExtra("coordinate", localStore.getUser().getCity());
                startActivity(intent);
            }
        });

        ib_home    = (ImageButton)findViewById(R.id.ib_profile_home);
        ib_home.setOnClickListener(this);
        ib_search    = (ImageButton)findViewById(R.id.ib_profile_search);
        ib_search.setOnClickListener(this);
        ib_relation    = (ImageButton)findViewById(R.id.ib_profile_relation);
        ib_relation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_profile_home:
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.ib_profile_search:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.ib_profile_relation:
                Intent relationIntent = new Intent(this, RelationActivity.class);
                startActivity(relationIntent);
                break;
        }

    }
}