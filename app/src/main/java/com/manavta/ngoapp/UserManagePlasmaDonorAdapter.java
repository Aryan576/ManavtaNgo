package com.manavta.ngoapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.manavta.ngoapp.ngo.model.PlasmaDonorNGO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManagePlasmaDonorAdapter extends BaseAdapter {

    private List<PlasmaDonorNGO> moviesList;
    Context context;

    public UserManagePlasmaDonorAdapter(Context context, List<PlasmaDonorNGO> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public Object getItem(int position) {
        return moviesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.plasma_donor_ngo_row, null);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        ImageView imageData = (ImageView) convertView.findViewById(R.id.img_hospital);
        TextView phone = convertView.findViewById(R.id.tv_phno);

        PlasmaDonorNGO movie = moviesList.get(position);
        title.setText("Name of donor: "+movie.getName());
        phone.setText("Blood Group of donor: "+movie.getBloodgroup());
        imageData.setImageResource(R.drawable.blood);
        Log.e("TAG***", "onBindViewHolder: " + movie.getName());
        TextView imgEdit = convertView.findViewById(R.id.tv_more);

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context, PlasmaDetailsActivity.class);

                i.putExtra("age","Age of Donor: "+movie.getAge());
                i.putExtra("bloodgroup","Blood Group: "+movie.getBloodgroup());
                i.putExtra("city",movie.getCity());
                i.putExtra("dateofcovidnegative","Date of Covid19 Negative: "+movie.getDateofcovidnegative());
                i.putExtra("dateofcovidpositive","Date of Covid19 positive: "+movie.getDateofcovidpositive());
                i.putExtra("gender","Gender: "+movie.getGender());
                i.putExtra("name","Name of Donor: "+movie.getName());
                i.putExtra("phonenumber","Contact Number: "+movie.getPhonenumber());
                i.putExtra("state",movie.getState());
                i.putExtra("weight","Weight: "+movie.getWeight());
                i.putExtra("plasmaid",movie.getPlasmaid());

                context.startActivity(i);

            }
        });

        return convertView;
    }

    private void deleteData(String id) {
        JSONObject js = new JSONObject();
        try {

            js.put("plasmaid", id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, Utilts.Delete_Plasma_URL, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG>>", response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(context, UserManagePlasmaDonorAdapter.class);
                            context.startActivity(i);


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
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjReq);

    }

}


