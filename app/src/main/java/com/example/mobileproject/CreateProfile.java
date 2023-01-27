package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileproject.databinding.ActivityCreateProfileBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateProfile extends AppCompatActivity {

    DatePickerDialog datePicker;
    ActivityCreateProfileBinding binding;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.edtFirstName.addTextChangedListener(RegTextWatcher);
        binding.edtLastName.addTextChangedListener(RegTextWatcher);
        binding.edtPhone.addTextChangedListener(RegTextWatcher);
        binding.edtAddress.addTextChangedListener(RegTextWatcher);
        binding.edtBirthdate.addTextChangedListener(RegTextWatcher);

        binding.edtBirthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    fnInvokeDatePicker();
                }
                if(!hasFocus)
                {
                    fnFormValidaton();
                }
            }
        });

        binding.edtBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnInvokeDatePicker();
            }
        });

        binding.fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnAddToREST();
            }
        });


        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        actionBarDrawerToggle.syncState();

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.navigation_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_main_activity:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_camera_activity:
                        intent = new Intent(getApplicationContext(), ActivityCamera.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_today_activity:
                        intent = new Intent(getApplicationContext(), ActivityToday.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_calendar_activity:
                        intent = new Intent(getApplicationContext(), ActivityCalendar.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_maps_activity:
                        intent = new Intent(getApplicationContext(), ActivityMaps.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_logout:
                        signOut();
                        return true;

                    default:
                        return false;
                }

            }

        });

    }

    private void fnFormValidaton() {
    }

    private void fnInvokeDatePicker()
    {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog

        datePicker = new DatePickerDialog(CreateProfile.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                binding.edtBirthdate.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        },year,month,day);
        datePicker.show();
    }

    private TextWatcher RegTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String firstNameInput = binding.edtFirstName.getText().toString().trim();
            String lastNameInput = binding.edtLastName.getText().toString().trim();
            String phoneNumInput = binding.edtPhone.getText().toString().trim();
            String birthInput = binding.edtBirthdate.getText().toString().trim();
            String addressInput = binding.edtAddress.getText().toString().trim();

            if(!firstNameInput.isEmpty() && !lastNameInput.isEmpty() &&
                    !phoneNumInput.isEmpty() && !birthInput.isEmpty() && !addressInput.isEmpty()){
                binding.fabAddUser.setEnabled(true);
                binding.fabAddUser.setBackgroundResource(R.color.blue);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void fnAddToREST() {

        String strURL = "http://192.168.1.115/MobileProject/Database.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("respond");

                    if(success.equals("Create Profile Success")){

                        Toast.makeText(getApplicationContext(), "Respond from server: " +
                                jsonObject.getString("respond"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);



                    }



                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String firstName = binding.edtFirstName.getText().toString();
                String lastName = binding.edtLastName.getText().toString();
                String phone = binding.edtPhone.getText().toString();
                String birth = binding.edtBirthdate.getText().toString();
                String address = binding.edtAddress.getText().toString();
                String gender = "";

                if(binding.rbMale.isChecked())
                    gender = binding.rbMale.getText().toString();
                else if(binding.rbFemale.isChecked())
                    gender = binding.rbFemale.getText().toString();

                String username = NoteClass.getInstance().getUsername();
                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnCreateProfile");
                params.put("username", username);
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("phone", phone);
                params.put("birth", birth);
                params.put("address", address);
                params.put("gender", gender);
                return params;


            }

        };
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {

        Toast.makeText(getApplicationContext(), "Successfully log out!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}