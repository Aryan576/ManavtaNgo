package com.manavta.ngoapp.ngo.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.android.material.textfield.TextInputEditText;
import com.manavta.ngoapp.R;
import com.manavta.ngoapp.Utilts;
import com.manavta.ngoapp.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddPlasmaDonorFragment extends Fragment {

    TextInputEditText name, age, bloodgroup, weight, city, state, phonenumber;
    Button btn_submit;
    RadioGroup gender;
    TextView dateofcovidpositive, dateofcovidnegative;
    int date, year, month;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_add_plasma_donor, container, false);
        init(rootView);
    return rootView;
    }

    private void init(View rootView) {
        
        name = rootView.findViewById(R.id.edt_name);
        age = rootView.findViewById(R.id.edit_age);
        bloodgroup = rootView.findViewById(R.id.edt_bloodgroup);
        weight = rootView.findViewById(R.id.edt_weight);
        city = rootView.findViewById(R.id.edt_city);
        state = rootView.findViewById(R.id.edt_state);
        phonenumber = rootView.findViewById(R.id.edt_phonenumber);
        btn_submit = rootView.findViewById(R.id.btn_submit);
        dateofcovidpositive = rootView.findViewById(R.id.dateofcovidpositive);
        dateofcovidnegative = rootView.findViewById(R.id.dateofcovidnegative);
        gender = rootView.findViewById(R.id.gender);


        Calendar calendar = Calendar.getInstance();
        date = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);

        dateofcovidnegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                dateofcovidnegative.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, date);
                datePickerDialog.show();

            }
        });
        dateofcovidpositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                dateofcovidpositive.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, date);
                datePickerDialog.show();

            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strname = name.getText().toString();
                String strage = age.getText().toString();
                String strbg = bloodgroup.getText().toString();
                String strweight = weight.getText().toString();
                String docp = dateofcovidpositive.getText().toString();
                String docn = dateofcovidnegative.getText().toString();
                String strcity = city.getText().toString();
                String strState = state.getText().toString();
                String phn = phonenumber.getText().toString();
                if (strname.equals("")) {
                    name.setError("Enter name");
                } else if (strage.equals("")) {
                    age.setError("Enter age");
                } else if (gender.getCheckedRadioButtonId() == -1) {
                    // no radio buttons are checked
                    Toast.makeText(getActivity(), "Please Select One!", Toast.LENGTH_SHORT).show();
                } else if (strbg.equals("")) {
                    bloodgroup.setError("Enter Blood Group");
                } else if (strweight.equals("")) {
                    weight.setError("Enter Weight");
                } else if (docp.equals("")) {
                    dateofcovidpositive.setError("Enter Date of Covid Positive");
                } else if (docn.equals("")) {
                    dateofcovidnegative.setError("Enter Date of Covid Negative");
                } else if (strcity.equals("")) {
                    city.setError("Enter City");
                } else if (strState.equals("")) {
                    state.setError("Enter State");
                } else if (phn.equals("")) {
                    phonenumber.setError("Enter Phone Number");
                } else {

                    int id = gender.getCheckedRadioButtonId();
                    RadioButton radioButton = rootView.findViewById(id);
                    String strgender = radioButton.getText().toString();

                    loadPlasmaNomorData(strname, strage, strgender, strbg, strweight, docp, docn, strcity, strState, phn);

                }


            }

            private void loadPlasmaNomorData(String strname, String strage, String strgender, String strbg, String strweight, String docp, String docn, String strcity, String strState, String phn) {

                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Add Record");
                progressDialog.show();
                JSONObject js = new JSONObject();
                try {

                    js.put("age", strage);
                    js.put("bloodgroup", strbg);
                    js.put("city", strcity);
                    js.put("dateofcovidnegative", docn);
                    js.put("dateofcovidpositive", docp);
                    js.put("gender", strgender);
                    js.put("name", strname);
                    js.put("phonenumber", phn);
                    js.put("state", strState);
                    js.put("weight", strweight);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                        Request.Method.POST, Utilts.Add_Plasma_URL, js,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("TAG>>", response.toString());

                                try {
                                    progressDialog.dismiss();
                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    String msg = jsonObject.getString("msg");
                                    Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();


                                    NGOPlasmaDonorFragment fragment = new NGOPlasmaDonorFragment();
                                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.add(R.id.frame,fragment);
                                    fragmentTransaction.commit();


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
                VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjReq);

            }
        });

    }
}