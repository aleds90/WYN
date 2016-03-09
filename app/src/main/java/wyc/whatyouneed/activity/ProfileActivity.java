package wyc.whatyouneed.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import wyc.whatyouneed.R;
import wyc.whatyouneed.entity.ClientLocalStore;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_name, tv_surname, tv_bday, tv_city, tv_role, tv_feedback, tv_followers, tv_followeed;
    TextView tv_description, tv_notice, tv_email, tv_password;
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
        tv_role.setText(localStore.getUser().getName());
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

        fab_edit        = (FloatingActionButton)findViewById(R.id.fab_profile_edit);
        fab_gallery     = (FloatingActionButton)findViewById(R.id.fab_profile_gallery);
        fab_location    = (FloatingActionButton)findViewById(R.id.fab_profile_location);
    }

    @Override
    public void onClick(View v) {

    }
}