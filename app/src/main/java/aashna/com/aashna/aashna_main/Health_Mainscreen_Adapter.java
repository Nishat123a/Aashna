package aashna.com.aashna.aashna_main;

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
import aashna.com.aashna.H_AskQuestion.AskQuestion;
import aashna.com.aashna.H_DoctorAppointment.DoctorAppointmentActivity;
import aashna.com.aashna.H_HealthTips.Health_tips_Activity;
import aashna.com.aashna.H_PCare.Pregnancy_Care_Activity;
import aashna.com.aashna.H_PTracker.PTrackerActivity;
import aashna.com.aashna.H_WaterReminder.WaterReminderActivity;
import aashna.com.aashna.R;

import java.util.List;

/**
 * Created by Dell on 2/22/2018.
 */

class Health_Mainscreen_Adapter extends RecyclerView.Adapter<Health_Mainscreen_Adapter.MyViewHolder> {

private Context mContext;
private List<Card> healthCardList;
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


    public Health_Mainscreen_Adapter(Context mContext, List<Card> healthCardList) {
        this.mContext = mContext;
        this.healthCardList = healthCardList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Card card = healthCardList.get(position);
        holder.title.setText(card.getName());
        // loading album cover using Glide library
        Glide.with(mContext).load(card.getThumbnail()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Title = healthCardList.get(position).getName();

                Log.e("ONCLICK","?????????????????????//" +Title);

                if(Title == "Period Tracker"){
                    intent = new Intent(mContext,PTrackerActivity.class);
                    mContext.startActivity(intent);
                }

                if(Title == "Pregnancy Care"){
                    Intent intent = new Intent(mContext,Pregnancy_Care_Activity.class);
                    mContext.startActivity(intent);
                    Toast.makeText(mContext, "Please Select Food Habits" , Toast.LENGTH_SHORT).show();
                }

                if(Title == "Health Tips"){
                    Intent intent = new Intent(mContext,Health_tips_Activity.class);
                    mContext.startActivity(intent);
                    Toast.makeText(mContext, "Please Select Health Tips" , Toast.LENGTH_SHORT).show();
                }

                if(Title == "Doctor Appointment"){
                    intent = new Intent(mContext,DoctorAppointmentActivity.class);
                    mContext.startActivity(intent);
                }

                if(Title == "Water Reminder"){
                    intent = new Intent(mContext,WaterReminderActivity.class);
                    mContext.startActivity(intent);
                }

                if(Title == "Ask a Question"){
                    intent = new Intent(mContext,AskQuestion.class);
                    mContext.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return healthCardList.size();
    }
}