package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountRegistration extends AppCompatActivity {

    EditText username, password, confirmPassword, email;
    Button buttonSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_registration);

        username = findViewById(R.id.idEdtUserName);
        password = findViewById(R.id.idEdtPassword);
        confirmPassword = findViewById(R.id.idEdtConfirmPassword);
        email = findViewById(R.id.idEdtEmail);
        buttonSignup = findViewById(R.id.idBtnSignUp);

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnAddToREST();
            }
        });

    }

    private void fnAddToREST() {

        String strURL = "http://192.168.8.122/MobileProject/Database.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("respond");

                    if(success.equals("Sign Up Success!")){

                        Toast.makeText(getApplicationContext(), "Respond from server: " +
                                jsonObject.getString("respond"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
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

                String userName = username.getText().toString();
                String passWord = password.getText().toString();
                String confirmPass = confirmPassword.getText().toString();
                String eMail = email.getText().toString();


                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnSignUp");
                params.put("Username", userName);
                params.put("Password", passWord);
                params.put("Email", eMail);

                return params;

            }

        };
        requestQueue.add(stringRequest);
    }
}