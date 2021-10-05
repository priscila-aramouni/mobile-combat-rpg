package com.example.overwatch;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.sip.SipSession;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.HeroViewHolder> {
    private ArrayList<Hero> heroList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemCLick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class HeroViewHolder extends RecyclerView.ViewHolder {
        public ImageView heroPixel;
        public TextView heroName;
        public TextView heroType;

        public HeroViewHolder(@NonNull final View itemView) {
            super(itemView);
            heroPixel = itemView.findViewById(R.id.hero_pixel);
            heroName = itemView.findViewById(R.id.hero_name);
            heroType = itemView.findViewById(R.id.hero_type);

            itemView.setOnClickListener(new View.OnClickListener() {
                boolean selected = false;
                @Override
                public void onClick(View view) {
                    Log.d("selected", ""+selected);
                    if(!selected) {
                        view.setBackgroundColor(Color.parseColor("#FA9C1D"));
                        heroName.setTextColor(Color.WHITE);
                        selected = true;
                    } else {
                        view.setBackgroundColor(Color.parseColor("#AFAFAF"));
                        heroName.setTextColor(Color.BLACK);
                        selected = false;
                    }

                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                            mListener.onItemCLick(position);
                    }
                }
            });
        }
    }

    public HeroAdapter(ArrayList<Hero> hList) {
        heroList = hList;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_item, parent, false);
        HeroViewHolder hvh = new HeroViewHolder(view);

        return hvh;
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        Hero currentHero = heroList.get(position);

        holder.heroPixel.setImageResource(currentHero.getPixelResourceID());
        holder.heroName.setText(currentHero.getName());
        holder.heroType.setText(currentHero.getType());
    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }
}
