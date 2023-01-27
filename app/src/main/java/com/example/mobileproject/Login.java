package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button buttonLogin;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.idEdtUserName);
        password = findViewById(R.id.idEdtPassword);
        buttonLogin = findViewById(R.id.idBtnLogin);
        textView = findViewById(R.id.account);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountRegistration.class);
                startActivity(intent);
            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnAddToREST();
            }
        });


    }

    private void fnAddToREST() {

        String strURL = "http://192.168.1.115/MobileProject/Database.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    //Toast.makeText(getApplicationContext(), "Getting some respond here" , Toast.LENGTH_SHORT).show();
                    Log.e("anyText",response);

                    if (response.equals("Username does not exist in Database"))
                    {
                        Toast.makeText(getApplicationContext(),"Username does not exist!", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                    }
                    else{
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String userName = username.getText().toString();
                            String passWord = password.getText().toString();

                            NoteClass.getInstance().setUsername(userName);

                            String username = obj.getString("Username");
                            String password = obj.getString("Password");
                            String id = obj.getString("ID");
                            String email = obj.getString("Email");

                            if(userName.equals(username) && passWord.equals(password)){
                                Toast.makeText(getApplicationContext(),"Successfully Login!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }else if(userName != username || passWord != password)
                                Toast.makeText(getApplicationContext(),"Incorrect Password", Toast.LENGTH_SHORT).show();


                        }
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