package wyc.whatyouneed.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import wyc.whatyouneed.R;
import wyc.whatyouneed.adpter.AdapterListUser;
import wyc.whatyouneed.entity.User;

public class RelationActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton ib_home,ib_search,ib_relation,ib_profile;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);
        findViewById();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void findViewById() {

        ib_home    = (ImageButton)findViewById(R.id.ib_relation_home);
        ib_home.setOnClickListener(this);
        ib_search    = (ImageButton)findViewById(R.id.ib_relation_search);
        ib_search.setOnClickListener(this);
        ib_profile    = (ImageButton)findViewById(R.id.ib_relation_profile);
        ib_profile.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_relation_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_relation_home:
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.ib_relation_search:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.ib_relation_profile:
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
                break;
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_relation_test, container, false);
            if (getArguments().getInt(ARG_SECTION_NUMBER)==1) {
                System.out.println("sono nel primo");
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

                final ListView listView = (ListView) rootView.findViewById(R.id.section_label);
                listView.setAdapter(new AdapterListUser(list, getContext()));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        User current = (User)listView.getAdapter().getItem(position);
                        Toast.makeText(getContext(), current.getName(), Toast.LENGTH_LONG).show();

                    }
                });
            }else
            {
                System.out.println("sono nel secondo");
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



                final ListView listView = (ListView) rootView.findViewById(R.id.section_label);
                listView.setAdapter(new AdapterListUser(list, getContext()));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        User current = (User)listView.getAdapter().getItem(position);
                        Toast.makeText(getContext(), current.getName(), Toast.LENGTH_LONG).show();

                    }
                });
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.label_contacts);
                case 1:
                    return getResources().getString(R.string.label_chat);
            }
            return null;
        }
    }
}
