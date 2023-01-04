package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateProfile extends AppCompatActivity {

    DatePickerDialog datePicker;
    ActivityCreateProfileBinding binding;


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

        String strURL = "http://192.168.8.122/MobileProject/Database.php";
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


                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnCreateProfile");
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
}