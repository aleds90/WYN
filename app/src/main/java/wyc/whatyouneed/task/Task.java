package wyc.whatyouneed.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yalantis.flipviewpager.utils.FlipSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import wyc.whatyouneed.R;
import wyc.whatyouneed.activity.HomeActivity;
import wyc.whatyouneed.activity.LoginActivity;
import wyc.whatyouneed.entity.Client;
import wyc.whatyouneed.entity.ClientLocalStore;
import wyc.whatyouneed.entity.Response;
import wyc.whatyouneed.entity.User;

public class Task {
    final static String URL_LOGIN_REQUEST = "http://njsao.pythonanywhere.com/login";
    final static String URL_REGISTRATION_REQUEST = "http://njsao.pythonanywhere.com/insert_user";

    public static String performPostCall(String requestURL,HashMap<String,
            String> postDataParams, String refreshToken, String connType) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod(connType);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            if (requestURL == "http://njsao.pythonanywhere.com/login") {
                conn.setRequestProperty("Authorization", refreshToken);
            }
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }

            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String getPostDataString(HashMap<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));

        }
        return result.toString();
    }

    public static class LoginTask extends AsyncTask<String, Void, Void> {
        private Client client;
        private User user;
        private ClientLocalStore clientlocalstore;
        private Context context;
        private HashMap<String, String> hashMap = new HashMap<String, String>();
        private Response response;
        private String result;

        public LoginTask(Client client, User user, ClientLocalStore clientlocalstore, Context context) {
            this.client = client;
            this.user = user;
            this.clientlocalstore = clientlocalstore;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            hashMap.put("random_id", client.getRANDOM_ID());
            hashMap.put("secret_id", client.getSECRET_ID());
            hashMap.put("grant_types", client.getGRANT_TYPES());
            hashMap.put("email", user.getEmail());
            hashMap.put("password", user.getPassword());
        }

        @Override
        protected Void doInBackground(String... params) {
            result = Task.performPostCall(URL_LOGIN_REQUEST, hashMap, client.getRefreshToken(),"POST");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            response = new Gson().fromJson(result, Response.class);
            handleLoginResponse(response, clientlocalstore, context);
        }

        public void handleLoginResponse(Response responseServer, ClientLocalStore clientlocalstore, Context context) {
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
                    //new Utility().get_generic_alert_dialog(context, "Wrong try again").show();
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

    public static class RegistrationVisitorTask extends AsyncTask<String, Void, Void> {

        private User user;
        private Context context;
        private HashMap<String, String> hashMap = new HashMap<String, String>();
        private String result;

        public RegistrationVisitorTask(User user, Context context) {
            this.user = user;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            hashMap.put("email", user.getEmail());
            hashMap.put("password", user.getPassword());
            hashMap.put("name", user.getName());
            hashMap.put("surname", user.getSurname());
        }

        @Override
        protected Void doInBackground(String... params) {
            result = Task.performPostCall(URL_REGISTRATION_REQUEST, hashMap,"","POST");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            handleLoginResponse(result, context);
        }

        private void handleLoginResponse(String responseServer, Context context) {
            switch (responseServer) {
                case "OK":
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    break;
                default:
                    Toast.makeText(context,  context.getResources().getText(
                            R.string.toast_registration_error), Toast.LENGTH_LONG).show();
                    break;
            }
        }

    }

    public static class RegistrationWorkerTask extends AsyncTask<String, Void, Void> {

        private User user;
        private Context context;
        private HashMap<String, String> hashMap = new HashMap<String, String>();
        private String result;

        public RegistrationWorkerTask(User user, Context context) {
            this.user = user;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            hashMap.put("email", user.getEmail());
            hashMap.put("password", user.getPassword());
            hashMap.put("name", user.getName());
            hashMap.put("surname", user.getSurname());
            hashMap.put("bday", user.getBday());
            hashMap.put("role", user.getRole());
            hashMap.put("city", user.getCity());
            hashMap.put("rate", String.valueOf(user.getRate()));
            hashMap.put("avatar", String.valueOf(user.getAvatar()));
            hashMap.put("description", user.getDescription());
        }

        @Override
        protected Void doInBackground(String... params) {
            result = Task.performPostCall(URL_REGISTRATION_REQUEST, hashMap,"","POST");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            handleLoginResponse(result, context);
        }

        private void handleLoginResponse(String responseServer, Context context) {
            switch (responseServer) {
                case "OK":
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    break;
                default:
                    Toast.makeText(context,  context.getResources().getText(
                            R.string.toast_registration_error), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }


}



