package wyc.whatyouneed.activity;

import android.content.Context;
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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import wyc.whatyouneed.R;
import wyc.whatyouneed.entity.Client;
import wyc.whatyouneed.entity.ClientLocalStore;
import wyc.whatyouneed.entity.User;
import wyc.whatyouneed.task.Task;
import wyc.whatyouneed.task.Utility;
import wyc.whatyouneed.task.VolleyTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ClientLocalStore localStore;
    TextView tv_registration_worker, tv_registration_visitor;
    EditText et_email, et_password;
    Button btn_login;
    private String url_login = "http://njsao.pythonanywhere.com/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_login_login:
                User user = new User();
                user.setEmail(et_email.getText().toString());
                user.setPassword(et_password.getText().toString());
                volley_login();
                //new Task.LoginTask(client, user, localStore, getApplicationContext()).execute();
                break;

            case R.id.tv_login_register_visitor:
                Intent intentVisitor = new Intent(getApplicationContext(), RegisterVisitorActivity.class);
                startActivity(intentVisitor);
                break;

            case R.id.tv_login_register_worker:
                Intent intentWorker = new Intent(getApplicationContext(), RegisterWorkerActivity.class);
                startActivity(intentWorker);
                break;
        }
    }

    private void findViewById() {
        localStore = new ClientLocalStore(this);

        et_email = (EditText) findViewById(R.id.et_login_email);
        et_password = (EditText) findViewById(R.id.et_login_password);

        btn_login = (Button) findViewById(R.id.btn_login_login);
        btn_login.setOnClickListener(this);

        tv_registration_visitor = (TextView) findViewById(R.id.tv_login_register_visitor);
        tv_registration_visitor.setOnClickListener(this);

        tv_registration_worker = (TextView) findViewById(R.id.tv_login_register_worker);
        tv_registration_worker.setOnClickListener(this);

    }

    public void volley_login() {

        StringRequest postRequest = new StringRequest(Request.Method.POST, this.url_login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            wyc.whatyouneed.entity.Response result = new Gson().fromJson(response, wyc.whatyouneed.entity.Response.class);
                            handleLoginResponse(result, localStore, getApplicationContext());

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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("random_id", "Ciao");
                params.put("secret_id", "Ciao");
                params.put("grant_types", "Password");
                params.put("email", et_email.getText().toString());
                params.put("password", et_password.getText().toString());
                params.put("Authorization", localStore.getClient().getAccessToken());

                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(postRequest);
    }


    public void handleLoginResponse(wyc.whatyouneed.entity.Response responseServer, ClientLocalStore clientlocalstore, Context context) {
        switch (responseServer.getType()) {
            case "2":
                User user = responseServer.getUser();
                Client client = new Client(responseServer.getAccess_Token(), responseServer.getRefresh_Token(), "");
                clientlocalstore.storeClientData(client, user);
                Intent intent = new Intent(context, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case "3":
                new Utility().get_generic_alert_dialog(LoginActivity.this, "Wrong try again").show();
                //Toast toast = Toast.makeText(context, context.getString(R.string.toast_login_error), Toast.LENGTH_LONG);
                //toast.show();
                break;
            case "4":
                User user1 = clientlocalstore.getUser();
                Client client1 = new Client(responseServer.getAccess_Token(), responseServer.getRefresh_Token(), "");
                clientlocalstore.storeClientData(client1, user1);
                break;
            case "401":
                Intent intent1 = new Intent(context, LoginActivity.class);
                context.startActivity(intent1);
                break;
        }

    }
}