package com.manavta.ngoapp.ngo.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.manavta.ngoapp.R;
import com.manavta.ngoapp.ngo.Fragment.NGOPlasmaDonorFragment;
import com.manavta.ngoapp.Utilts;
import com.manavta.ngoapp.VolleySingleton;
import com.manavta.ngoapp.ngo.model.PlasmaDonorNGO;
import com.manavta.ngoapp.ngo.Fragment.UpdatePlasmaFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NGOManagePlasmaDonorAdapter extends BaseAdapter {

    private List<PlasmaDonorNGO> moviesList;
    Context context;

    public NGOManagePlasmaDonorAdapter(Context context, List<PlasmaDonorNGO> moviesList) {
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
        convertView = layoutInflater.inflate(R.layout.hospital_ngo_list_row, null);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        ImageView imageData = (ImageView) convertView.findViewById(R.id.img_hospital);
        TextView phone = convertView.findViewById(R.id.tv_phno);

        PlasmaDonorNGO movie = moviesList.get(position);
        title.setText("Name: "+movie.getName());
        phone.setText("Blood Group: "+movie.getBloodgroup());
        imageData.setImageResource(R.drawable.blood);
        Log.e("TAG***", "onBindViewHolder: " + movie.getName());
        ImageView imgEdit = convertView.findViewById(R.id.img_edit);
        ImageView imgDelete = convertView.findViewById(R.id.img_delete);


        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = moviesList.get(position).getPlasmaid();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.splash_screen);
                builder.setTitle("Manavta NGO");
                builder.setMessage("Are you sure, you wants to Delete?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteData(id,dialog);
                        dialog.dismiss();


                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                builder.show();

            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdatePlasmaFragment updatePlasmaFragment = new UpdatePlasmaFragment();
                Bundle bundle = new Bundle();


               
                bundle.putString("age",movie.getAge());
                bundle.putString("bloodgroup",movie.getBloodgroup());
                bundle.putString("city",movie.getCity());
                bundle.putString("dateofcovidnegative",movie.getDateofcovidnegative());
                bundle.putString("dateofcovidpositive",movie.getDateofcovidpositive());
                bundle.putString("gender",movie.getGender());
                bundle.putString("name",movie.getName());
                bundle.putString("phonenumber",movie.getPhonenumber());
                bundle.putString("state",movie.getState());
                bundle.putString("weight",movie.getWeight());
                bundle.putInt("plasmaid",movie.getPlasmaid());

                updatePlasmaFragment.setArguments(bundle);
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.frame, updatePlasmaFragment).addToBackStack(null).commit();

            }
        });

        return convertView;
    }

    private void deleteData(int id, DialogInterface dialog) {

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE,
                Utilts.Delete_Plasma_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String msg = jsonObject.getString("msg");
                    dialog.dismiss();
                    Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();

                    NGOPlasmaDonorFragment fragment = new NGOPlasmaDonorFragment();
                    FragmentManager manager = ((AppCompatActivity)
                            context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);


        /*JSONObject js = new JSONObject();
        try {

            js.put("plasmaid", id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.DELETE, Utilts.Delete_Plasma_URL, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG>>", response.toString());
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(context, NGOManagePlasmaDonorAdapter.class);
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

            *//**
             * Passing some request headers
             *//*
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
*/
    }

}


