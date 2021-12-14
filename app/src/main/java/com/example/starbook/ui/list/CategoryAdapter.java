package com.example.starbook.ui.list;


import static androidx.core.content.ContextCompat.startActivity;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.starbook.MainActivity2;
import com.example.starbook.R;
import com.example.starbook.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CardViewViewHolder> {

    private ArrayList<Category>listCategory;
    private Context context;

    public CategoryAdapter(ArrayList<Category> listCategory, Context context) {
        this.listCategory = listCategory;
        this.context = context;
    }

    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_category, parent, false);
        CardViewViewHolder viewHolder = new CardViewViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewViewHolder holder, int position) {

        Category p = listCategory.get(position);
        Glide.with(context).load("https://source.unsplash.com/480x270/?"+p.getEncoded()).into(holder.imgBg);
        holder.tvDisplay.setText(p.getDisplay());

        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity2.class);
                intent.putExtra("category", p);
                startActivity(view.getContext(), intent, null);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        CardView cardItem;
        TextView tvDisplay;
        ImageView imgBg;

        public CardViewViewHolder(View itemView) {
            super(itemView);
            cardItem = (CardView)itemView.findViewById(R.id.card_item);
            imgBg = (ImageView)itemView.findViewById(R.id.img_item_bg);
            tvDisplay = (TextView)itemView.findViewById(R.id.tvDisplay);
        }
    }
}
