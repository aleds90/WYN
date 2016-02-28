package wyc.whatyouneed.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import wyc.whatyouneed.R;
import wyc.whatyouneed.entity.User;
import wyc.whatyouneed.task.Task;

public class RegisterWorkerActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name, surname, email, password, birthday, city, rate;
    Button register, back;
    CheckBox terms;
    Spinner role;
    DatePickerDialog dateDialog;
    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_worker);
        findViewById();
        setDateDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et_registration_worker_birthday:
                dateDialog.show();
                break;
            case R.id.btn_registration_worker_back:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_registration_worker_registration:
                if (name.getText().toString().equals("")||surname.getText().toString().equals("")
                        ||email.getText().toString().equals("")||password.getText().toString().equals("")
                        ||city.getText().toString().equals("")||rate.getText().toString().equals("")
                        ||birthday.getText().toString().equals("")||role.getSelectedItem().toString().equals("")
                        ||!terms.isChecked())
                    Toast.makeText(getApplicationContext(), getResources().getText(
                            R.string.toast_registration_error), Toast.LENGTH_LONG).show();
                else {
                    User newUser = new User();
                    newUser.setEmail(email.getText().toString());
                    newUser.setPassword(password.getText().toString());
                    newUser.setName(name.getText().toString());
                    newUser.setSurname(surname.getText().toString());
                    newUser.setCity(city.getText().toString());
                    newUser.setRate(Double.parseDouble(rate.getText().toString()));
                    newUser.setBday(birthday.getText().toString());
                    newUser.setRole(role.getSelectedItem().toString());
                    newUser.setAvatar(0);
                    newUser.setDescription("");
                    new Task.RegistrationWorkerTask(newUser, getApplicationContext()).execute();
                }
                break;

        }
    }

    private void findViewById() {
        name        = (EditText) findViewById(R.id.et_registration_worker_name);
        surname     = (EditText) findViewById(R.id.et_registration_worker_surname);
        email       = (EditText) findViewById(R.id.et_registration_worker_email);
        password    = (EditText) findViewById(R.id.et_registration_worker_password);
        city        = (EditText) findViewById(R.id.et_registration_worker_city);
        rate        = (EditText) findViewById(R.id.et_registration_worker_rate);
        terms       = (CheckBox)findViewById(R.id.cb_registration_worker_terms);
        role        = (Spinner)findViewById(R.id.sp_registration_worker_role);

        birthday    = (EditText) findViewById(R.id.et_registration_worker_birthday);
        birthday.setInputType(InputType.TYPE_NULL);
        birthday.requestFocus();
        birthday.setOnClickListener(this);

        register    = (Button) findViewById(R.id.btn_registration_worker_registration);
        register.setOnClickListener(this);

        back        = (Button) findViewById(R.id.btn_registration_worker_back);
        back.setOnClickListener(this);

    }

    private void setDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-dd-MM", Locale.ITALY);
        dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                birthday.setText(dateFormat.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
