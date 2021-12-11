package com.manavta.ngoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.manavta.ngoapp.ngo.NGOHomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail;
    EditText edtPassword;
    TextView tvRegister;
    Button btnLogin;
    TextView tvFP;
    String emailpat = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtEmail = findViewById(R.id.edt_mail);
        edtPassword = findViewById(R.id.edt_pass);
        tvRegister = findViewById(R.id.tv_register2);
        btnLogin = findViewById(R.id.btn_login_page);
        tvFP = findViewById(R.id.tv_fp);
        tvFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater1 = getLayoutInflater();
                View myview1 = layoutInflater1.inflate(R.layout.raw_ngo_verification, null);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                final AlertDialog alertDialog1 = builder1.create();
                alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog1.setView(myview1);
                alertDialog1.show();
                EditText edtCode = myview1.findViewById(R.id.edt_ngocode);
                edtCode.setHint("Enter Your Register EmailId");
                edtCode.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                Button btnRegi = myview1.findViewById(R.id.btn_register);
                btnRegi.setText("Submit");
                btnRegi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = edtCode.getText().toString();
                        if (email.equals("")) {
                            edtCode.setError("Please Enter Email");
                        } else if (!email.matches(emailpat)) {
                            edtEmail.setError("Enter Valid Email!!");
                        }else {
                            alertDialog1.dismiss();
                            Toast.makeText(MainActivity.this, "Your password has been sent to your email id", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                if (email.equals("")) {
                    edtEmail.setError("Enter Email!!");
                    Toast.makeText(MainActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();

                } else if (password.equals("")) {
                    edtPassword.setError("Enter Password!!");
                    Toast.makeText(MainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();

                } else {

                    loadData(email, password);

                }

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = getLayoutInflater();
                View myview = layoutInflater.inflate(R.layout.raw_custom_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.setView(myview);
                alertDialog.show();
                RadioGroup radioGroup = myview.findViewById(R.id.radio_grp);
                Button btnNext = myview.findViewById(R.id.btn_next);
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (radioGroup.getCheckedRadioButtonId() == -1) {
                            // no radio buttons are checked
                            Toast.makeText(MainActivity.this, "Please Select One!", Toast.LENGTH_SHORT).show();
                        } else {
                            // one of the radio buttons is checked
                           int getSelected = radioGroup.getCheckedRadioButtonId();

                           RadioButton selectclass = (RadioButton)myview.findViewById(getSelected);

                           String strSelect = selectclass.getText().toString();
                           if (strSelect.equals("NGO")){
                               String ngoCode = "9999";
                               alertDialog.dismiss();
                              // Toast.makeText(MainActivity.this, "NGO", Toast.LENGTH_SHORT).show();
                               LayoutInflater layoutInflater1 = getLayoutInflater();
                               View myview1 = layoutInflater1.inflate(R.layout.raw_ngo_verification, null);
                               AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                               final AlertDialog alertDialog1 = builder1.create();
                               alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                               alertDialog1.setView(myview1);
                               alertDialog1.show();
                               EditText edtCode = myview1.findViewById(R.id.edt_ngocode);
                               Button btnRegi = myview1.findViewById(R.id.btn_register);
                               btnRegi.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       String code = edtCode.getText().toString();
                                       if (code.equals("")) {
                                           edtCode.setError("Please enter NGO code");
                                       } else {

                                           if (code.equals(ngoCode)) {
                                               alertDialog1.dismiss();
                                               Intent i = new Intent(MainActivity.this, MainSignUp.class);
                                               i.putExtra("ROLE_ID", "2");
                                               startActivity(i);
                                               finish();

                                           } else {
                                               edtCode.setError("Invalid NGO code");
                                           }
                                       }
                                   }
                               });

                           }if (strSelect.equals("User")){
                                alertDialog.dismiss();
                                Intent i = new Intent(MainActivity.this, MainSignUp.class);
                                i.putExtra("ROLE_ID", "1");
                                startActivity(i);
                             //   finish();
                            }

                        }

                    }
                });

            }
        });
    }

    private void loadData(String email, String password) {

        JSONObject js = new JSONObject();
        try {
            js.put("email", email);
            js.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, Utilts.login_URL, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            Log.d("TAG&&&", response.toString());

                            String msg = jsonObject.getString("msg");
                            JSONObject jsonData = jsonObject.getJSONObject("data");

                            String roleid = jsonData.getString("roleid");
                            String email = jsonData.getString("email");
                            String firstname = jsonData.getString("firstname");
                            String lastname = jsonData.getString("lastname");
                            String address = jsonData.getString("address");
                            String dob = jsonData.getString("dob");
                            String phonenumber = jsonData.getString("phonenumber");

                            SharedPreferences sharedPreferences = getSharedPreferences("MYAPP",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email",email);
                            editor.putString("firstname",firstname);
                            editor.putString("lastname",lastname);
                            editor.putString("roleid",roleid);
                            editor.commit();

                            if (roleid.equals("2")){

                                Intent i = new Intent(MainActivity.this, NGOHomeActivity.class);
                                startActivity(i);
                                finish();

                            }else {


                                Intent i = new Intent(MainActivity.this, HomeDashActivity.class);
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