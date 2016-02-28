package wyc.whatyouneed.entity;

import android.content.Context;
import android.content.SharedPreferences;

public class ClientLocalStore {
    public static final String SP_NAME = "ClientDetails";
    SharedPreferences clientLocalDB;

    public ClientLocalStore(Context context) {
        clientLocalDB = context.getSharedPreferences(SP_NAME, 0);
    }

    /**
     * Salviamo i dati del client in queste shared Preference
     * @param client
     */
    public void storeClientData(Client client, User user){

        SharedPreferences.Editor editor = clientLocalDB.edit();
        editor.putString("access_Token", client.accessToken);
        editor.putString("refresh_Token", client.refreshToken);
        editor.putInt("id_user", user.getId_user());
        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.putString("name", user.getName());
        editor.putString("surname", user.getSurname());
        editor.putString("city", user.getCity());
        editor.putString("role", user.getRole());
        editor.putString("bday", user.getBday());
        editor.putFloat("rate", (float) user.getRate());
        editor.putBoolean("active", user.isActive());
        editor.putString("description", user.getDescription());
        editor.putInt("avatar", user.getAvatar());
        editor.commit();
    }

    /**
     * @return il client salvato in precendza
     */
    public Client getClient(){

        String access_token = clientLocalDB.getString("access_Token", "");
        String refresh_token = clientLocalDB.getString("refresh_Token", "");
        Client client = new Client(access_token, refresh_token, "");
        return client;
    }

    public User getUser(){
        int id_user     = clientLocalDB.getInt("id_user", 0);
        String email    = clientLocalDB.getString("email", "");
        String password = clientLocalDB.getString("password", "");
        String name     = clientLocalDB.getString("name", "");
        String surname  = clientLocalDB.getString("surname", "");
        String city     = clientLocalDB.getString("city", "");
        String role     = clientLocalDB.getString("role", "");
        String bday     = clientLocalDB.getString("bday", "");
        double rate     =(double)clientLocalDB.getFloat("rate", 0f);
        int avatar      = clientLocalDB.getInt("avatar",1);
        boolean active = clientLocalDB.getBoolean("active", true);
        String description = clientLocalDB.getString("description", "insert description");
        User user = new User(id_user, name, surname, email, password, bday, role, city, rate,
                active, description, avatar);
        return user;
        }

    /**
     * Cancella tutti dati salvati, in caso di un log out(?) o di un reset.
     */
    public void clearClient(){

        SharedPreferences.Editor editor = clientLocalDB.edit();
        editor.clear();
        editor.commit();
    }

    public void updateUser(User user){

        SharedPreferences.Editor editor = clientLocalDB.edit();
        editor.putInt("id_user", user.getId_user());
        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.putString("name", user.getName());
        editor.putString("surname", user.getSurname());
        editor.putString("city", user.getCity());
        editor.putString("role", user.getRole());
        editor.putString("bday", user.getBday());
        editor.putFloat("rate", (float) user.getRate());
        editor.putBoolean("active", user.isActive());
        editor.putString("description", user.getDescription());
        editor.putInt("avatar", user.getAvatar());
        editor.commit();
        }
}
