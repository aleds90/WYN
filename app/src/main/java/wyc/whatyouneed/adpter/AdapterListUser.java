package wyc.whatyouneed.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wyc.whatyouneed.R;
import wyc.whatyouneed.entity.User;

public class AdapterListUser extends ArrayAdapter<User>{

    private ArrayList<User> users;
    private Context context;

    public AdapterListUser(ArrayList<User> users, Context context){
        super(context, R.layout.adapter_list_user, users);
        this.users = users;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.adapter_list_user, parent, false);
        final User user = users.get(position);

        ImageView avatar = (ImageView)view.findViewById(R.id.img_adp_listuser_avatar);
        avatar.setImageDrawable(view.getResources().getDrawable(user.getAvatarID(user.getRole())));

        TextView name = (TextView)view.findViewById(R.id.tv_adp_listuser_name);
        name.setText(user.getName());

        TextView surname = (TextView)view.findViewById(R.id.tv_adp_listuser_surname);
        surname.setText(user.getSurname());

        TextView role = (TextView)view.findViewById(R.id.tv_adp_listuser_role);
        role.setText(user.getRole());

        TextView city = (TextView)view.findViewById(R.id.tv_adp_listuser_city);
        city.setText(user.getCity());

        return view;
    }
}
