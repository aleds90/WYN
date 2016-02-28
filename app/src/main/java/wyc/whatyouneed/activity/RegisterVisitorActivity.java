package wyc.whatyouneed.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import wyc.whatyouneed.R;
import wyc.whatyouneed.entity.User;
import wyc.whatyouneed.task.Task;

public class RegisterVisitorActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name, surname, email, password;
    Button register, back;
    CheckBox terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_visitor);
        findViewById();
    }

    private void findViewById() {
        name        = (EditText) findViewById(R.id.et_registration_visitor_name);
        surname     = (EditText) findViewById(R.id.et_registration_visitor_surname);
        email       = (EditText) findViewById(R.id.et_registration_visitor_email);
        password    = (EditText) findViewById(R.id.et_registration_visitor_password);
        terms       = (CheckBox)findViewById(R.id.cb_registration_visitor_terms);

        register    = (Button) findViewById(R.id.btn_registration_visitor_registration);
        register.setOnClickListener(this);

        back        = (Button) findViewById(R.id.btn_registration_visitor_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registration_visitor_registration:
                if (name.getText().toString().equals("")||surname.getText().toString().equals("")
                        ||email.getText().toString().equals("")||password.getText().toString().equals("")
                        ||!terms.isChecked())
                    Toast.makeText(getApplicationContext(), getResources().getText(
                            R.string.toast_registration_error), Toast.LENGTH_LONG).show();
                else {
                    User newUser = new User();
                    newUser.setEmail(email.getText().toString());
                    newUser.setPassword(password.getText().toString());
                    newUser.setName(name.getText().toString());
                    newUser.setSurname(surname.getText().toString());
                    new Task.RegistrationVisitorTask(newUser, getApplicationContext()).execute();
                }
                break;
            case R.id.btn_registration_visitor_back:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }

    }
}
