package aashna.com.aashna.H_PCare;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import aashna.com.aashna.aashna_main.Card;
import aashna.com.aashna.R;

import java.util.List;

class Pregnancy_Care_Adapter extends RecyclerView.Adapter<Pregnancy_Care_Adapter.MyViewHolder> {

private Context mContext;
    private List<Card> Pregnancy_Care_list;
        String Title;
        Intent intent;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public ImageView thumbnail;

    public MyViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
    }
}


    public Pregnancy_Care_Adapter(Context mContext, List<Card> Pregnancy_Care_list) {
        this.mContext = mContext;
        this.Pregnancy_Care_list = Pregnancy_Care_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Card card = Pregnancy_Care_list.get(position);
        holder.title.setText(card.getName());
        // loading album cover using Glide library
        Glide.with(mContext).load(card.getThumbnail()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Title = Pregnancy_Care_list.get(position).getName();

                Log.e("ONCLICK","?????????????????????//" +Title);

                if(Title.equals("Healthy Pregnancy")){

                    Toast.makeText(mContext, "Work in Progress of " + Title + ". Please Select Food Habits", Toast.LENGTH_SHORT).show();
                }else

                if(Title.equals("Food Habits")){
                    intent = new Intent(mContext,Food_Habits.class);
                    intent.putExtra("Title", Title);
                    mContext.startActivity(intent);
                    Toast.makeText(mContext, "Clicked:" + Title, Toast.LENGTH_SHORT).show();
                }else

                if(Title.equals("Pregnancy Scan")){

                    Toast.makeText(mContext, "Work in Progress of " + Title + ". Please Select Food Habits", Toast.LENGTH_SHORT).show();
                }else

                if(Title.equals("Three Stages")){

                    Toast.makeText(mContext, "Work in Progress of " + Title + ". Please Select Food Habits", Toast.LENGTH_SHORT).show();
                }else

                if(Title.equals("Pregnancy Pains")){

                    Toast.makeText(mContext, "Work in Progress of " + Title + ". Please Select Food Habits", Toast.LENGTH_SHORT).show();
                }else

                if(Title.equals("Baby Appearance")){

                    Toast.makeText(mContext, "Work in Progress of " + Title + ". Please Select Food Habits", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return Pregnancy_Care_list.size();
    }
}