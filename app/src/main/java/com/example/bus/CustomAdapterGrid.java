package com.example.bus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapterGrid extends BaseAdapter {
 Context context;
 private List<CustomGrid> list  = null;
 private LayoutInflater inflater;

 public CustomAdapterGrid(Context context, List<CustomGrid> list) {
  this.context = context;
  this.list = list;
 }

 @Override
 public int getCount() {
  return list.size();
 }

 @Override
 public Object getItem(int position) {
  return list.get(position);
 }

 @Override
 public long getItemId(int position) {
  return position;
 }

 @Override
 public View getView(int position, View convertView, ViewGroup parent) {
  if(convertView == null){
   inflater = LayoutInflater.from(parent.getContext());
   convertView = inflater.inflate(R.layout.custom_grid,parent,false);
  }
  CircleImageView imageView = (CircleImageView)convertView.findViewById(R.id.seatImageid);
  TextView textView = (TextView)convertView.findViewById(R.id.seatTextViewId);

  Picasso.get().load(list.get(position).getSeatImage()).placeholder(R.drawable.seat).into(imageView);
  textView.setText(list.get(position).getSeatName().toString());

  return convertView;
 }
}
