package wyc.whatyouneed.entity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wyc.whatyouneed.R;

public class User implements Serializable {

    private int id_user;
    private String name,surname,email,password,bday,role,city,description;
    private double rate;
    private boolean active;
    private List<String> interests = new ArrayList<>();
    private int avatar;

    public User(int id_user, String name, String surname, String email, String password,
                String bday, String role, String city, double rate, boolean active,
                String description,int avatar) {
        this.id_user = id_user;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.bday = bday;
        this.role = role;
        this.city = city;
        this.rate = rate;
        this.active = active;
        this.description = description;
        this.avatar = avatar;
    }

    public User(){}

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getInterests() {
        interests.add(role);
        interests.add(Double.toString(rate)+"€");
        String status;
        if (isActive())
            status = "Disponibile";
        else status = "Non disponibile";
        interests.add(city);
        interests.add(status);
        return interests;
    }

    public void getDrawableAvatar(String role,int avatar,ImageView imageView,Context context,String url) {
        if (avatar == 0) {
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
        }else if (avatar==1){
            Picasso.with(context).load(url).resize(150,150).into(imageView);
        }
    }
}
