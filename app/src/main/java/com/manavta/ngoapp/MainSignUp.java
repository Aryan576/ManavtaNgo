package com.manavta.ngoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainSignUp extends AppCompatActivity {

    EditText edtFirstName;
    EditText edtLastName;
    EditText edtEmail;
    EditText edtPassword;
    TextView edtDob;
    EditText edtAddress;
    EditText edtContactNo;
    Button btnRegister;
    String emailpat = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    int date, year, month;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign_up);

        edtFirstName = findViewById(R.id.edt_name2);
        edtLastName = findViewById(R.id.in_last_name2);
        edtEmail = findViewById(R.id.edt_email2);
        edtPassword = findViewById(R.id.edt_password2);
        edtDob = findViewById(R.id.in_dob);
        edtAddress = findViewById(R.id.edt_address2);
        edtContactNo = findViewById(R.id.edt_contact2);
        btnRegister = findViewById(R.id.btn_register);

        Intent i = getIntent();
        id = i.getStringExtra("ROLE_ID");

        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        edtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainSignUp.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                edtDob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, date);
                datePickerDialog.show();

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fn = edtFirstName.getText().toString();
                String ln = edtLastName.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String dob = edtDob.getText().toString();
                String address = edtAddress.getText().toString();
                String contact = edtContactNo.getText().toString();

                if (fn.equals("")) {
                    edtFirstName.setError("Enter Firstname!!");
                    Toast.makeText(MainSignUp.this, "Enter Firstname", Toast.LENGTH_SHORT).show();

                } else if (ln.equals("")) {
                    edtLastName.setError("Enter Lastname!!");
                    Toast.makeText(MainSignUp.this, "Enter Lastname", Toast.LENGTH_SHORT).show();

                } else if (email.equals("")) {
                    edtEmail.setError("Enter Email!!");
                    Toast.makeText(MainSignUp.this, "Enter Email", Toast.LENGTH_SHORT).show();

                } else if (!email.matches(emailpat)) {
                    edtEmail.setError("Enter Valid Email!!");
                    Toast.makeText(MainSignUp.this, "Enter valid Email", Toast.LENGTH_SHORT).show();

                } else if (password.equals("")) {
                    edtPassword.setError("Enter Password!!");
                    Toast.makeText(MainSignUp.this, "Enter Password", Toast.LENGTH_SHORT).show();

                }
                else if (dob.equals("")) {
                    edtDob.setError("Enter Date of Birth!!");
                    Toast.makeText(MainSignUp.this, "Enter Date of Birth", Toast.LENGTH_SHORT).show();

                } else if (address.equals("")) {
                    edtAddress.setError("Enter Address!!");
                    Toast.makeText(MainSignUp.this, "Enter Address", Toast.LENGTH_SHORT).show();

                } else if (contact.equals("")) {
                    edtContactNo.setError("Enter Contact No!!");
                    Toast.makeText(MainSignUp.this, "Enter Contact No", Toast.LENGTH_SHORT).show();
                } else {
                    loadData(fn, ln, email, password, dob, address, contact);
                }

            }
        });
    }

    private void loadData(String fn, String ln, String email, String password,
                          String dob, String address, String contact) {


        JSONObject js = new JSONObject();
        try {

            js.put("firstname", fn);
            js.put("lastname", ln);
            js.put("gender", "male");
            js.put("phonenumber", contact);
            js.put("email", email);
            js.put("dob",dob);
            js.put("password", password);
            js.put("address", address);
            js.put("roleid",id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, Utilts.signup_URL, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String msg = jsonObject.getString("msg");
                            if (msg.equals("You Are Already Rgister")){
                                Toast.makeText(MainSignUp.this, ""+msg, Toast.LENGTH_SHORT).show();

                            }else {
                                Intent i = new Intent(MainSignUp.this, MainActivity.class);
                                startActivity(i);
                                finish();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjReq);


    }
}