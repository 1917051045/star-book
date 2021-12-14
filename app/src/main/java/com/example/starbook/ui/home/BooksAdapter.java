package com.example.starbook.ui.home;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starbook.BookDetailsActivity;
import com.example.starbook.MainActivity;
import com.example.starbook.R;
import com.example.starbook.model.Book;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.CardViewViewHolder> {

    private ArrayList<Book>listBook;
    private Context context;

    public BooksAdapter(ArrayList<Book> listBook, Context context) {
        this.listBook = listBook;
        this.context = context;
    }

    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_hotlist, parent, false);
        CardViewViewHolder viewHolder = new CardViewViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewViewHolder holder, int position) {

        Book p = listBook.get(position);

        Glide.with(context).load(p.getCover()).override(500,1000).into(holder.imgcover);
        if (p.getTitle().length() > 64)
            holder.tvTitle.setText(p.getTitle().substring(0, 64) + "...");
        else
            holder.tvTitle.setText(p.getTitle());

        if (p.getAuthor().length() > 28)
            holder.tvAuthor.setText(p.getAuthor().substring(0, 28) + "...");
        else
            holder.tvAuthor.setText(p.getAuthor());

        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BookDetailsActivity.class);
                intent.putExtra("book", p);
                startActivity(view.getContext(), intent, null);
            }
        });

        holder.btnFavorite.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Favorite "+listBook.get(position).getTitle(),Toast.LENGTH_SHORT).show();
            }
        }));

        holder.btnshare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Share "+listBook.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        }));


        }

    @Override
    public int getItemCount() {
        return listBook.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        CardView cardItem;
        ImageView imgcover;
        TextView tvTitle, tvAuthor;
        Button btnFavorite, btnshare;

        public CardViewViewHolder(View itemView) {
            super(itemView);
            cardItem = (CardView)itemView.findViewById(R.id.card_item);
            imgcover = (ImageView)itemView.findViewById(R.id.img_item_cover);
            tvTitle = (TextView)itemView.findViewById(R.id.tv_item_title);
            tvAuthor = (TextView)itemView.findViewById(R.id.tv_item_author);
            btnshare = (Button)itemView.findViewById(R.id.btn_set_share);
            btnFavorite = (Button)itemView.findViewById(R.id.btn_set_favorite);
        }
    }
}
