package com.example.myapplication.Viewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.WeatherHolder> {

    private Context context;
    private ArrayList<WeatherCards> weathers;

    public CardAdapter(Context context, ArrayList<WeatherCards> weathers) {
        this.context = context;
        this.weathers = weathers;
    }

    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cards,parent,false);
        return new WeatherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder holder, int position) {
        WeatherCards weather = weathers.get(position);
        holder.setDetails(weather);
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    class WeatherHolder extends RecyclerView.ViewHolder{
        private TextView txtCityCard, txtWeatherCard, txtTimeCard;
        WeatherHolder(View itemView){
            super(itemView);
            txtCityCard = itemView.findViewById(R.id.txtCityCard);
            txtWeatherCard = itemView.findViewById(R.id.txtWeatherCard);
            txtTimeCard = itemView.findViewById(R.id.txtTimeCard);
        }

        void setDetails(WeatherCards weatherCards){
            txtCityCard.setText(String.format("City is: %s", weatherCards.getCity()));
            txtWeatherCard.setText(String.format("weather is: %s", weatherCards.getWeather()));
            txtTimeCard.setText(String.format("Time is: %s", weatherCards.getTime()));
        }
    }

}
