package com.example.mobileproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotesFragment extends Fragment {

    List<NoteClass> noteClasses;
    RecyclerView recyclerView;
    View v;
    EditText note;
    Button button;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_notes, container, false);

        recyclerView = v.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        note = v.findViewById(R.id.note);
        button = v.findViewById(R.id.button);

        noteClasses = new ArrayList<>();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(note.getText().toString().equals("")){

                    Toast.makeText(getContext(),"Note cannot be empty!",Toast.LENGTH_SHORT).show();
                }

                else {
                    Date currentTime = Calendar.getInstance().getTime();
                    String date = currentTime.toString().trim();
                    NoteClass.getInstance().setDate(date);

                    retrieveData();
                    sendData();
                    retrieveData();
                }



            }
        });

        retrieveData();


        return v;
    }

    public void sendData(){


        String url = "http://192.168.1.115/MobileProject/Database.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.e("anyText",response);
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("respond");

                    if(success.equals("Create Note Success")) {

                        Toast.makeText(getContext(), "Respond from server: " +
                                jsonObject.getString("respond"), Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(getContext(), Login.class);
                        //startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                String notes = note.getText().toString();
                String date = NoteClass.getInstance().getDate();

                String username = NoteClass.getInstance().getUsername();

                Map<String, String> params = new HashMap<>();
                params.put("selectFn", "fnNote");
                params.put("Customer_Username", username);
                params.put("Notes", notes);
                params.put("Date", date);

                return params;

            }

        };

        requestQueue.add(request);
        retrieveData();
    }

    public void retrieveData() {

        String url = "http://192.168.1.115/MobileProject/getNotes.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.e("anyText", response);
                    //creating adapter object and setting it to recyclerview
                    NoteAdapter adapter = new NoteAdapter(getContext(), noteClasses);
                    recyclerView.setAdapter(adapter);
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);
                    noteClasses.clear();
                        //traversing through all the object
                        for (int i = 0; i < array.length(); i++) {


                            //getting product object from json array
                            JSONObject note = array.getJSONObject(i);


                            //adding the product to product list
                            noteClasses.add(new NoteClass(

                                    note.getString("Notes"),
                                    note.getString("Date")

                            ));

                        }


                    adapter.notifyItemInserted(noteClasses.size());




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String username = NoteClass.getInstance().getUsername();

                Map<String, String> params = new HashMap<>();
                params.put("Customer_Username", username);


                return params;

            }

        };

        requestQueue.add(request);

    }
}