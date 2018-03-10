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

import com.bumptech.glide.Glide;

import aashna.com.aashna.R;
import aashna.com.aashna.S_ContributeToNGO.C_NGO;
import aashna.com.aashna.S_ReportIssue.ReportIssueActivity;
import aashna.com.aashna.S_RequestNGO.RequestNGOActivity;
import aashna.com.aashna.S_SelfSafety.Contacts;

import java.util.List;

class Safety_Mainscreen_Adapter extends RecyclerView.Adapter<Safety_Mainscreen_Adapter.MyViewHolder> {

    private Context mContext;
    private List<Card> safetyCardList;
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


    public Safety_Mainscreen_Adapter(Context mContext, List<Card> safetyCardList) {
        this.mContext = mContext;
        this.safetyCardList = safetyCardList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Card card = safetyCardList.get(position);
        holder.title.setText(card.getName());
        // loading album cover using Glide library
        Glide.with(mContext).load(card.getThumbnail()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Title = safetyCardList.get(position).getName();

                Log.e("ONCLICK","?????????????????????//" +Title);

                if(Title == "Request NGO"){
                    intent =  new Intent(mContext, RequestNGOActivity.class);
                    mContext.startActivity(intent);
                }

                if(Title == "Self Safety"){
                    intent =  new Intent(mContext, Contacts.class);
                    mContext.startActivity(intent);
                }

                if(Title == "Report Issue"){
                    intent =  new Intent(mContext, ReportIssueActivity.class);
                    mContext.startActivity(intent);
                }

                if(Title == "Contribute NGO"){
                    intent =  new Intent(mContext, C_NGO.class);
                    mContext.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return safetyCardList.size();
    }
}