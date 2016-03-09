package wyc.whatyouneed.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import wyc.whatyouneed.R;
import wyc.whatyouneed.entity.Client;
import wyc.whatyouneed.entity.ClientLocalStore;
import wyc.whatyouneed.entity.User;
import wyc.whatyouneed.task.Task;
import wyc.whatyouneed.task.VolleyTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ClientLocalStore localStore;
    TextView tv_registration_worker,tv_registration_visitor;
    EditText et_email,et_password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_login_login:
                User user = new User();
                user.setEmail(et_email.getText().toString());
                user.setPassword(et_password.getText().toString());
                Client client = new Client("Ciao", "Ciao", "Password");
                //LOGIN_TASK("http://njsao.pythonanywhere.com/login",client,user);
                new Task.LoginTask(client, user, localStore,getApplicationContext()).execute();
                break;

            case R.id.tv_login_register_visitor:
                Intent intentVisitor =  new Intent(getApplicationContext(), RegisterVisitorActivity.class);
                startActivity(intentVisitor);
                break;

            case R.id.tv_login_register_worker:
                Intent intentWorker =  new Intent(getApplicationContext(), RegisterWorkerActivity.class);
                startActivity(intentWorker);
                break;
        }
    }

    private void findViewById() {
        localStore = new ClientLocalStore(this);

        et_email = (EditText)findViewById(R.id.et_login_email);
        et_password = (EditText)findViewById(R.id.et_login_password);

        btn_login = (Button)findViewById(R.id.btn_login_login);
        btn_login.setOnClickListener(this);

        tv_registration_visitor = (TextView)findViewById(R.id.tv_login_register_visitor);
        tv_registration_visitor.setOnClickListener(this);

        tv_registration_worker = (TextView)findViewById(R.id.tv_login_register_worker);
        tv_registration_worker.setOnClickListener(this);

    }

    public void LOGIN_TASK(String url, final Client client, final User user){

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            System.out.println(jsonResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("random_id", client.getRANDOM_ID());
                params.put("secret_id",client.getSECRET_ID());
                params.put("grant_types", client.getGRANT_TYPES());
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(postRequest);
    }

}
