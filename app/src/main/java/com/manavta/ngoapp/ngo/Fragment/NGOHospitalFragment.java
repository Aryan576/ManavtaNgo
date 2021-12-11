package com.manavta.ngoapp.ngo.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.manavta.ngoapp.R;
import com.manavta.ngoapp.Utilts;
import com.manavta.ngoapp.VolleySingleton;
import com.manavta.ngoapp.ngo.Adapter.NGOManageHosAdapter;
import com.manavta.ngoapp.ngo.model.HospitalNGO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NGOHospitalFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    ListView recyclerView;
    ArrayList<HospitalNGO> hospitalNGOArrayList;
    TextView tvData;
    String TAG = NGOHospitalFragment.class.getName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ngo_hospital, container, false);
        recyclerView = rootView.findViewById(R.id.recycler);
        floatingActionButton = rootView.findViewById(R.id.fab);
        tvData = rootView.findViewById(R.id.tv_nodata);
        loadData();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddHospitalNGOFragment addHospitalNGOFragment = new AddHospitalNGOFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction()
                        .add(R.id.frame, addHospitalNGOFragment, addHospitalNGOFragment.getTag())
                        .addToBackStack(addHospitalNGOFragment.getTag())
                        .commit();
            }
        });

        Log.e(TAG, "onCreateView: " );

        return  rootView;
    }



    private void loadData() {

        hospitalNGOArrayList = new ArrayList<HospitalNGO>();
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Hospital Record");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utilts.Display_Hospital_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i= 0; i<jsonArray.length();i++){
                        progressDialog.dismiss();

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String hospitaladdress = jsonObject1.getString("hospitaladdress");
                        int hospitalid  = jsonObject1.getInt("hospitalid");
                        String hospitalname = jsonObject1.getString("hospitalname");
                        String hospitalphonenumber = jsonObject1.getString("hospitalphonenumber");
                        String location = jsonObject1.getString("location");
                        String numberbeds = jsonObject1.getString("numberbeds");
                        String pincode = jsonObject1.getString("pincode");
                        String availableoxygen = jsonObject1.getString("availableoxygen");


                        HospitalNGO hospitalNGO = new HospitalNGO();
                        hospitalNGO.setHospitalname(hospitalname);
                        hospitalNGO.setHospitaladdress(hospitaladdress);
                        hospitalNGO.setHospitalid(hospitalid);
                        hospitalNGO.setHospitalname(hospitalname);
                        hospitalNGO.setHospitalphonenumber(hospitalphonenumber);
                        hospitalNGO.setLocation(location);
                        hospitalNGO.setNumberbeds(numberbeds);
                        hospitalNGO.setPincode(pincode);
                        hospitalNGO.setAvailableoxygen(availableoxygen);
                        //hospitalNGO.setImg(R.drawable.ic_hospital);
                        hospitalNGOArrayList.add(hospitalNGO);
                    }

                    if (hospitalNGOArrayList.size() >0){

                        tvData.setVisibility(View.GONE);
                    }

                    NGOManageHosAdapter mAdapter = new NGOManageHosAdapter(getActivity(),hospitalNGOArrayList);
                    /*RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());*/
                    recyclerView.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("TAG_LIST", "onResponse: "+response );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
