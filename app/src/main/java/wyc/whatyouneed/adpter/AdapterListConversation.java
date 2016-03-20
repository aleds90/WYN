package wyc.whatyouneed.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wyc.whatyouneed.R;
import wyc.whatyouneed.activity.LocationActivity;
import wyc.whatyouneed.entity.ClientLocalStore;
import wyc.whatyouneed.entity.Message;
import wyc.whatyouneed.entity.User;

public class AdapterListConversation extends ArrayAdapter<Message>{

    private ClientLocalStore localStore;
    private ArrayList<Message> messages;
    private Context context;
    private ArrayList<User> users;

    public AdapterListConversation(ArrayList<Message> messages, Context context, ArrayList<User> users){
        super(context, R.layout.adapter_list_conversation, messages);
        this.messages = messages;
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.adapter_list_conversation, parent, false);
        final Message message = messages.get(position);
        final User user = users.get(position);
        localStore = new ClientLocalStore(context);

        ImageView avatar = (ImageView)view.findViewById(R.id.img_adp_listconversation_avatar);
        TextView name = (TextView)view.findViewById(R.id.tv_adp_listconversation_name);
        TextView surname = (TextView)view.findViewById(R.id.tv_adp_listconversation_surname);
        TextView message_text = (TextView)view.findViewById(R.id.tv_adp_listconversation_message);
        TextView time = (TextView)view.findViewById(R.id.tv_adp_listconversation_time);

        avatar.setImageDrawable(view.getResources().getDrawable(user.getAvatarID(user.getRole())));
        name.setText(user.getName());
        surname.setText(user.getSurname());
        message_text.setText(message.getText());
        time.setText(message.getSendetAt());


        return view;
    }
}
