package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Login extends AppCompatActivity {

    EditText username, password;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.idEdtUserName);
        password = findViewById(R.id.idEdtPassword);
        buttonLogin = findViewById(R.id.idBtnLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
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

                    Log.e("anyText",response);

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("respond");
                    Log.e("anyText",response);

                    if(success.equals("Successfully Login")){

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

                String userName = username.getText().toString();
                String passWord = password.getText().toString();


                Map<String, String> params = new HashMap< >();
                params.put("selectFn", "fnLogin");
                params.put("Username", userName);
                params.put("Password", passWord);

                return params;

            }

        };
        requestQueue.add(stringRequest);
    }
}