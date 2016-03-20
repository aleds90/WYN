package wyc.whatyouneed.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
        fab_edit.setOnClickListener(this);
        fab_gallery     = (FloatingActionButton)findViewById(R.id.fab_profile_gallery);
        fab_gallery.setOnClickListener(this);
        fab_location    = (FloatingActionButton)findViewById(R.id.fab_profile_location);
        fab_location.setOnClickListener(this);

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
            case R.id.fab_profile_location:
                Intent intent = new Intent(ProfileActivity.this, LocationActivity.class);
                intent.putExtra("coordinate", localStore.getUser().getCity());
                startActivity(intent);
                break;
            case R.id.fab_profile_edit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getResources().getString(R.string.title_edit_profile));
                LayoutInflater inflater = this.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_profile, null);
                EditText editName = (EditText)view.findViewById(R.id.et_dialog_name);
                editName.setText(localStore.getUser().getName());
                EditText editSurname = (EditText)view.findViewById(R.id.et_dialog_surname);
                editSurname.setText(localStore.getUser().getSurname());
                EditText editEmail = (EditText)view.findViewById(R.id.et_dialog_email);
                editEmail.setText(localStore.getUser().getEmail());
                EditText editPassword = (EditText)view.findViewById(R.id.et_dialog_password);
                editPassword.setText(localStore.getUser().getPassword());
                EditText editCity = (EditText)view.findViewById(R.id.et_dialog_city);
                editCity.setText(localStore.getUser().getCity());
                EditText editRole = (EditText)view.findViewById(R.id.et_dialog_role);
                editRole.setText(localStore.getUser().getRole());
                EditText editDescription = (EditText)view.findViewById(R.id.et_dialog_description);
                editDescription.setText(localStore.getUser().getDescription());
                EditText editNotice = (EditText)view.findViewById(R.id.et_dialog_notice);
                editNotice.setText("");
                builder.setView(view);
                builder.setPositiveButton(getResources().getString(R.string.label_corfim), null);
                builder.setNegativeButton(getResources().getString(R.string.label_cancel), null);
                builder.show();

                break;
        }

    }
}