package aashna.com.aashna.H_HealthTips;

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

class Health_tips_Adapter extends RecyclerView.Adapter<Health_tips_Adapter.MyViewHolder> {

    private Context mContext;
    private List<Card> health_tips_list;
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


    public Health_tips_Adapter(Context mContext, List<Card> health_tips_list) {
        this.mContext = mContext;
        this.health_tips_list = health_tips_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Card card = health_tips_list.get(position);
        holder.title.setText(card.getName());
        // loading album cover using Glide library
        Glide.with(mContext).load(card.getThumbnail()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Title = health_tips_list.get(position).getName();

                Log.e("ONCLICK","?????????????????????//" +Title);

                if(Title.equals("Health Tips")){
                    intent = new Intent(mContext,Ht_click_Activity.class);
                    intent.putExtra("Title", Title);
                    mContext.startActivity(intent);
                    Toast.makeText(mContext, "Clicked:" + Title, Toast.LENGTH_SHORT).show();
                }else

                if(Title.equals("Beauty Tips")){

                    Toast.makeText(mContext, "Work in Progress of " + Title + ". Please Select Health Tips", Toast.LENGTH_SHORT).show();
                }else

                if(Title.equals("Fashion & Style Tips")){

                    Toast.makeText(mContext, "Work in Progress of " + Title + ". Please Select Health Tips", Toast.LENGTH_SHORT).show();
                }else

                if(Title.equals("Makeup Tips")){

                    Toast.makeText(mContext, "Work in Progress of " + Title + ". Please Select Health Tips", Toast.LENGTH_SHORT).show();
                }else

                if(Title.equals("Skin Care Tips")){

                    Toast.makeText(mContext, "Work in Progress of " + Title + ". Please Select Health Tips", Toast.LENGTH_SHORT).show();
                }else

                if(Title.equals("Diet & Fitness")){

                    Toast.makeText(mContext, "Work in Progress of " + Title + ". Please Select Health Tips", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return health_tips_list.size();
    }
}