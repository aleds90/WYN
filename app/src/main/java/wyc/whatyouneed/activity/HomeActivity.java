package wyc.whatyouneed.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yalantis.flipviewpager.adapter.BaseFlipAdapter;
import com.yalantis.flipviewpager.utils.FlipSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import wyc.whatyouneed.R;
import wyc.whatyouneed.entity.ClientLocalStore;
import wyc.whatyouneed.entity.User;
import wyc.whatyouneed.task.Task;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    final static String URL_USERS_FLIP_LIST_REQUEST = "http://njsao.pythonanywhere.com/get_users/";
    private static final long RIPPLE_DURATION = 250;
    ImageButton ib_home,ib_search,ib_relation,ib_profile;
    ImageView iv_navigation;
    ListView lv_users;
    ClientLocalStore localStore;
    Toolbar tb_top;
    FrameLayout lo_home;
    WaveSwipeRefreshLayout lo_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById();
        startingTasks();
    }

    private void startingTasks() {
        new UsersFipListTask(localStore.getUser(),getApplicationContext(), lv_users).execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_home_search:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.ib_home_relation:
                Intent relationIntent = new Intent(this, RelationActivity.class);
                startActivity(relationIntent);
                break;
            case R.id.ib_home_profile:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
                break;
        }

    }

    private void findViewById() {
        localStore = new ClientLocalStore(this);

        lo_home = (FrameLayout)findViewById(R.id.layout_home);
        tb_top =(Toolbar)findViewById(R.id.tb_home_top);
        iv_navigation = (ImageView)findViewById(R.id.img_home_ic_navigation);

        ib_home = (ImageButton)findViewById(R.id.ib_home_home);
        ib_home.setOnClickListener(this);

        ib_search = (ImageButton)findViewById(R.id.ib_home_search);
        ib_search.setOnClickListener(this);

        ib_relation = (ImageButton)findViewById(R.id.ib_home_relation);
        ib_relation.setOnClickListener(this);

        ib_profile = (ImageButton)findViewById(R.id.ib_home_profile);
        ib_profile.setOnClickListener(this);

        lv_users = (ListView)findViewById(R.id.lv_home_users);

        lo_refresh = (WaveSwipeRefreshLayout)findViewById(R.id.lo_home_refresh);
        lo_refresh.setWaveColor(getResources().getColor(R.color.blue));
        lo_refresh.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new UsersFipListTask(localStore.getUser(),getApplicationContext(), lv_users).execute();
            }
        });
    }

   class ListFlipUser extends BaseFlipAdapter<User> {
        private final int PAGES = 3;
        private int[] IDS_INTEREST = {R.id.interest_1, R.id.interest_2, R.id.interest_3, R.id.interest_4};

        public ListFlipUser(Context context, List<User> items, FlipSettings settings) {
            super(context, items, settings);
        }

        @Override
        public View getPage(int position, View convertView, ViewGroup parent, final User friend1, User friend2) {
            final FriendsHolder holder;
            if (convertView == null) {
                holder = new FriendsHolder();
                convertView = getLayoutInflater().inflate(R.layout.adpter_friends_merge_page, parent, false);
                holder.leftAvatar = (TextView) convertView.findViewById(R.id.first);
                holder.rightAvatar = (TextView) convertView.findViewById(R.id.second);
                holder.infoPage = getLayoutInflater().inflate(R.layout.adpter_friends_info, parent, false);
                holder.nickName = (TextView) holder.infoPage.findViewById(R.id.nickname);
                holder.surname = (TextView) holder.infoPage.findViewById(R.id.surname);
                for (int id : IDS_INTEREST)
                    holder.interests.add((TextView) holder.infoPage.findViewById(id));
                convertView.setTag(holder);
            } else {
                holder = (FriendsHolder) convertView.getTag();
            }

            switch (position) {
                case 1:
                    String role = friend1.getRole();
                    getDrawableAvatar(role, holder.leftAvatar, getApplicationContext());
                    holder.leftAvatar.setText(friend1.getRole());
                    holder.leftAvatar.setTextColor(getResources().getColor(R.color.blue));
                    if (friend2 != null) {
                        String role2 = friend2.getRole();
                        getDrawableAvatar(role2, holder.rightAvatar, getApplicationContext());
                        holder.rightAvatar.setText(friend2.getRole());
                        holder.rightAvatar.setTextColor(getResources().getColor(R.color.blue));
                    }
                    break;
                default:
                    fillHolder(holder, position == 0 ? friend1 : friend2);
                    holder.infoPage.setTag(holder);
                    return holder.infoPage;
            }
            return convertView;
        }

        @Override
        public int getPagesCount() {
            return PAGES;
        }

        private void fillHolder(FriendsHolder holder, User friend) {
            if (friend == null)
                return;
            Iterator<TextView> iViews = holder.interests.iterator();
            Iterator<String> iInterests = friend.getInterests().iterator();
            while (iViews.hasNext() && iInterests.hasNext())
                iViews.next().setText(iInterests.next());
            final User currentUser = friend;
            holder.infoPage.setBackgroundColor(getResources().getColor(R.color.blue));
            holder.nickName.setText(friend.getName());
            holder.surname.setText(friend.getSurname());
        }

        class FriendsHolder {
            TextView leftAvatar;
            TextView rightAvatar;
            View infoPage;
            List<TextView> interests = new ArrayList<>();
            TextView nickName;
            TextView surname;
        }

        public void getDrawableAvatar(String role, TextView imageView, Context context) {
                    if (role.equals("Animatore"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_clown));
                    else if (role.equals("Barista") || role.equals("Barman") || role.equals("Cameriere"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_waiter));
                    else if (role.equals("Barbiere") || role.equals("Estetista") || role.equals("Parrucchiere")
                            || role.equals("HairStyler") || role.equals("Make Up Artist") || role.equals("Sarto"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_hairdresser));
                    else if (role.equals("Baby sitter"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_waitress));
                    else if (role.equals("Conducente") || role.equals("Tassista"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_driver));
                    else if (role.equals("Cuoco") || role.equals("Pasticciere"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_chef));
                    else if (role.equals("Wedding Planner"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_planner));
                    else if (role.equals("Designer") || role.equals("Grafico pubblicitario") ||
                            role.equals("Pittore"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_artist));
                    else if (role.equals("Dietista") || role.equals("Fisioterapista") || role.equals("Infermiere")
                            || role.equals("Nutrizionista") || role.equals("Nutrizionista animale")
                            || role.equals("Veterinario"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_doctor));
                    else if (role.equals("Elettricista") || role.equals("Idraulico") || role.equals("Muratore"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_fixer));
                    else if (role.equals("Fotografo") || role.equals("Video-Maker") || role.equals("Social-Media Manager"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_photograph));
                    else if (role.equals("Guardia del corpo"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_bodyguard));
                    else if (role.equals("Guida Turistica") || role.equals("Guida"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_guide));
                    else if (role.equals("Giardiniere"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_lumberjack));
                    else if (role.equals("Maestro di sci"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_ski));
                    else if (role.equals("Fioraio"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_florist));
                    else if (role.equals("Modello"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_actor));
                    else if (role.equals("Preparatore sportivo") || role.equals("Procuratore sportivo") || role.equals("Personal Trainer"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_atletic));
                    else if (role.equals("Programmatore"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_nerd));
                    else if (role.equals("Tutor per ripetizioni"))
                        imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_profession_teacher));
        }
    }

    public class UsersFipListTask extends AsyncTask<Void, Void, Void> {

        private User user;
        private Context context;
        private HashMap<String, String> hashMap = new HashMap<String, String>();
        private String result;
        private ArrayList<User> users;
        private ListView listView;

        public UsersFipListTask(User user, Context context,ListView listView) {
            this.user = user;
            this.context = context;
            this.listView = listView;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            result = Task.performPostCall("http://njsao.pythonanywhere.com/get_users/?email=a", hashMap,"","GET");
//            try {
//                URLConnection urlConnection = new URL("http://njsao.pythonanywhere.com/get_users/?email=a").openConnection();
//                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
//                InputStream response = urlConnection.getInputStream();
//                System.out.println(response);
//            } catch (java.io.IOException e) {
//                e.printStackTrace();
//            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);
                String message = jsonObject.getString("message_count");
                JSONArray usersArray = jsonObject.getJSONArray("users");
                int count = Integer.parseInt(message);
                users = new ArrayList<>();
                for (int i = 0; i < usersArray.length(); i++) {
                    User user = null;
                    try {
                        user = new Gson().fromJson(usersArray.get(i).toString(), User.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    users.add(user);
                }
                FlipSettings settings = new FlipSettings.Builder().defaultPage(1).build();
                listView.setAdapter(new ListFlipUser(getApplicationContext() , users, settings));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}

