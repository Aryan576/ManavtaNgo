package com.manavta.ngoapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.manavta.ngoapp.ngo.model.PlasmaDonorNGO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class USERPlasmaDonorFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    ListView recyclerView;
    ArrayList<PlasmaDonorNGO> plasmaDonorNGOArrayList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user_list, container, false);
        recyclerView = rootView.findViewById(R.id.recycler);
        plasmaDonorNGOArrayList = new ArrayList<PlasmaDonorNGO>();
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Plasma Donor Record");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utilts.Display_Plasma_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i= 0; i<jsonArray.length();i++){
                        progressDialog.dismiss();

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String age = jsonObject1.getString("age");
                        String name = jsonObject1.getString("name");
                        int plasmaid  = jsonObject1.getInt("plasmaid");
                        String bloodgroup = jsonObject1.getString("bloodgroup");
                        String city = jsonObject1.getString("city");
                        String dateofcovidnegative = jsonObject1.getString("dateofcovidnegative");
                        String dateofcovidpositive = jsonObject1.getString("dateofcovidpositive");
                        String gender = jsonObject1.getString("gender");
                        String phonenumber = jsonObject1.getString("phonenumber");
                        String state = jsonObject1.getString("state");
                        String weight = jsonObject1.getString("weight");

                        PlasmaDonorNGO plasmaDonorNGO = new PlasmaDonorNGO();
                        plasmaDonorNGO.setAge(age);
                        plasmaDonorNGO.setBloodgroup(bloodgroup);
                        plasmaDonorNGO.setCity(city);
                        plasmaDonorNGO.setDateofcovidnegative(dateofcovidnegative);
                        plasmaDonorNGO.setDateofcovidpositive(dateofcovidpositive);
                        plasmaDonorNGO.setGender(gender);
                        plasmaDonorNGO.setPhonenumber(phonenumber);
                        plasmaDonorNGO.setState(state);
                        plasmaDonorNGO.setWeight(weight);
                        plasmaDonorNGO.setPlasmaid(plasmaid);
                        plasmaDonorNGO.setName(name);

                        plasmaDonorNGOArrayList.add(plasmaDonorNGO);
                    }


                    UserManagePlasmaDonorAdapter mAdapter = new UserManagePlasmaDonorAdapter(getActivity(),plasmaDonorNGOArrayList);
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

        return  rootView;
    }

}