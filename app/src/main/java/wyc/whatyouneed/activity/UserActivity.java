package wyc.whatyouneed.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import wyc.whatyouneed.R;
import wyc.whatyouneed.entity.User;

public class UserActivity extends AppCompatActivity {
    TextView name, surname, bday, role, city, description, notice, feedback, followers, title;
    FloatingActionButton message, add_feedback, follow, location, warning;
    ImageView avatar, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        findViewById();
    }

    private void findViewById() {
        Bundle bundle = getIntent().getExtras();
        User user = getUserBybundle(bundle);

        title = (TextView)findViewById(R.id.tv_user_toolbar_title);
        title.setText(user.getName());

        name = (TextView)findViewById(R.id.tv_user_name);
        name.setText(user.getName());

        surname = (TextView)findViewById(R.id.tv_user_surname);
        surname.setText(user.getSurname());

        bday = (TextView)findViewById(R.id.tv_user_bday);
        bday.setText(user.getBday());

        role = (TextView)findViewById(R.id.tv_user_role);
        role.setText(user.getRole());

        city = (TextView)findViewById(R.id.tv_user_city);
        city.setText(user.getCity());

        description = (TextView)findViewById(R.id.tv_user_description);
        description.setText(user.getDescription());

        notice = (TextView)findViewById(R.id.tv_user_notice);

        feedback = (TextView)findViewById(R.id.tv_user_feedback);
        followers = (TextView)findViewById(R.id.tv_user_followers);

        message = (FloatingActionButton)findViewById(R.id.fab_user_message);
        add_feedback = (FloatingActionButton)findViewById(R.id.fab_user_feedback);
        follow = (FloatingActionButton)findViewById(R.id.fab_user_follow);
        location = (FloatingActionButton)findViewById(R.id.fab_user_location);
        warning = (FloatingActionButton)findViewById(R.id.fab_user_report);

        avatar = (ImageView)findViewById(R.id.img_user_avatar);
        avatar.setImageDrawable(getResources().getDrawable(user.getAvatarID(user.getRole())));

        back = (ImageView)findViewById(R.id.img_user_ic_navigation);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private User getUserBybundle(Bundle bundle) {
        int id_user = bundle.getInt("id_user");
        String name = bundle.getString("name");
        String cognome = bundle.getString("cognome");
        String city = bundle.getString("city");
        String email = bundle.getString("email");
        String bday = bundle.getString("bday");
        String role = bundle.getString("role");
        Double rate = bundle.getDouble("rate");
        boolean active = bundle.getBoolean("status");
        String description = bundle.getString("description");
        int avatar  =bundle.getInt("avatar");
        User user = new User(id_user, name, cognome, email, "", bday, role, city, rate,
                active, description,avatar);
        return user;
    }
}
