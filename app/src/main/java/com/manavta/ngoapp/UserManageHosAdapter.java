package com.manavta.ngoapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.manavta.ngoapp.ngo.model.HospitalNGO;

import java.util.List;

public class UserManageHosAdapter extends BaseAdapter {

    private List<HospitalNGO> moviesList;
    Context context;

    public UserManageHosAdapter(Context context, List<HospitalNGO> moviesList) {
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

        HospitalNGO movie = moviesList.get(position);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+movie.getHospitalphonenumber()));
                context.startActivity(intent);
            }
        });

        title.setText(movie.getHospitalname());
        phone.setText(movie.getHospitalphonenumber());
        imageData.setImageResource(R.drawable.hospital);
        Log.e("TAG***", "onBindViewHolder: " + movie.getImg());
        TextView imgEdit = convertView.findViewById(R.id.tv_more);
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, MoreDetailActivity.class);
                i.putExtra("HOSPITAL_NAME","Hospital Name: "+movie.getHospitalname());
                i.putExtra("HOSPITAL_BEDS","Available Beds: "+movie.getNumberbeds());
                i.putExtra("HOSPITAL_PHONE","Hospital ContactNumber: "+movie.getHospitalphonenumber());
                i.putExtra("HOSPITAL_ADDRESS","Hospital Address: "+movie.getHospitaladdress());
                i.putExtra("HOSPITAL_PIN",movie.getPincode());
                i.putExtra("HOSPITAL_ID",movie.getHospitalid());
                context.startActivity(i);
            }
        });

        return convertView;
    }


    }


