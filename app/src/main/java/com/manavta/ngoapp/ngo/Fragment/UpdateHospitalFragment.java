package com.manavta.ngoapp.ngo.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.HashMap;
import java.util.Map;

public class UpdateHospitalFragment extends Fragment {

    TextInputEditText hospitalname, numberofbeds, hospitalphno, address, pincode;
    Button btn_submit;
    RadioGroup oxygen;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_update_hospital, container, false);
        hospitalname = rootView.findViewById(R.id.edt_hospitalname);
        numberofbeds = rootView.findViewById(R.id.edit_numberofbeds);
        hospitalphno = rootView.findViewById(R.id.edt_hospitalphno);
        address = rootView.findViewById(R.id.edt_address);
        pincode = rootView.findViewById(R.id.edt_pincode);
        btn_submit = rootView.findViewById(R.id.btn_submit);
        oxygen = rootView.findViewById(R.id.oxygen);

        
        String strName = getArguments().getString("HOSPITAL_NAME");
        String strBed = getArguments().getString("HOSPITAL_BEDS");
        String strPhone = getArguments().getString("HOSPITAL_PHONE");
        String strAdd = getArguments().getString("HOSPITAL_ADDRESS");
        String strPin = getArguments().getString("HOSPITAL_PIN");
        int strId = getArguments().getInt("HOSPITAL_ID", 0);
        String strOxygen = getArguments().getString("HOSPITAL_OXYGEN");
        RadioButton radioBtn1 = rootView.findViewById(R.id.radio_yes);
        RadioButton radioBtn2 = rootView.findViewById(R.id.radio_no);

        if (radioBtn1.getText().toString().equals(strOxygen)) {

            oxygen.check(R.id.radio_yes);
        } else {
            oxygen.check(R.id.radio_no);

        }
        hospitalname.setText(strName);
        numberofbeds.setText(strBed);
        hospitalphno.setText(strPhone);
        address.setText(strAdd);
        pincode.setText(strPin);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hn = hospitalname.getText().toString();
                String nob = numberofbeds.getText().toString();
                String hp = hospitalphno.getText().toString();
                String add = address.getText().toString();
                String pin = pincode.getText().toString();
                if (hn.equals("")) {
                    hospitalname.setError("Enter HospitalName....");
                } else if (nob.equals("")) {
                    numberofbeds.setError("Enter No of beds");

                } else if (oxygen.getCheckedRadioButtonId() == -1) {
                    // no radio buttons are checked
                    Toast.makeText(getActivity(), "Please Select One!", Toast.LENGTH_SHORT).show();
                } else if (hp.equals("")) {
                    hospitalphno.setError("Enter phone Number");
                } else if (add.equals("")) {
                    address.setError("Enter Address");
                } else if (pin.equals("")) {
                    pincode.setError("Enter Pincode");
                } else {

                    int id = oxygen.getCheckedRadioButtonId();
                    RadioButton radioButton = rootView.findViewById(id);
                    String stroxygen = radioButton.getText().toString();

                    loadData(hn, nob, hp, add, pin, strId, stroxygen);
                }
            }
        });

        return rootView;
    }

    private void loadData(String hn, String nob, String hp, String add, String pin, int strId, String stroxygen) {


        JSONObject js = new JSONObject();
        try {

            js.put("hospitaladdress", add);
            js.put("hospitalname", hn);
            js.put("hospitalphonenumber", hp);
            js.put("numberbeds", nob);
            js.put("pincode", pin);
            js.put("location", "");
            js.put("lat", "");
            js.put("log", "");
            js.put("availableoxygen", stroxygen);
            js.put("hospitalid", strId);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.PUT, Utilts.Update_Hospital_URL, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG>>", response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                            //finish();

                            NGOHospitalFragment fragment = new NGOHospitalFragment();
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            manager.beginTransaction()
                                    .replace(R.id.frame, fragment)
                                    .addToBackStack(null)
                                    .commit();


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
}