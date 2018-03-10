package aashna.com.aashna.H_AskQuestion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import aashna.com.aashna.R;
import aashna.com.aashna.S_SelfSafety.AnimationUtil;

import java.util.ArrayList;


class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder>  {

    ArrayList<FAQ_pojo> faqlist;
    private Context mContext;
    int previousPosition = 0;

    // Pass in the country array into the constructor
    public FAQAdapter( ArrayList<FAQ_pojo> faqlist, Context context) {
        this.faqlist = faqlist;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final  int position) {

        holder.nImage.setImageResource(R.drawable.person_img);
        holder.name.setText(faqlist.get(position).getName());
        holder.cat.setText(faqlist.get(position).getCategory());
        holder.ques.setText(faqlist.get(position).getQuestion());

        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Reply", Toast.LENGTH_SHORT).show();
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Liked", Toast.LENGTH_SHORT).show();
            }
        });

        //Getting the position of items in recyclerview
        if(position > previousPosition){ //we are scrolling DOWN

            AnimationUtil.animate(holder, true);

        }else{  //we are scrolling UP

            AnimationUtil.animate(holder, false);
        }

        previousPosition = position;
    }

    @Override
    public int getItemCount() {
        return faqlist == null ? 0 : faqlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /* Your holder should contain a member variable
     for any view that will be set as you render a row */
        TextView name,cat,ques;
        private ImageView nImage,reply,like;



        /*constructor that accepts the entire item row
             and does the view lookups to find each subview */
        public ViewHolder(View itemView) {

            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nImage = (ImageView) itemView.findViewById(R.id.thumbnail);
            name = (TextView) itemView.findViewById(R.id.name);
            cat = (TextView) itemView.findViewById(R.id.cat);
            ques = (TextView) itemView.findViewById(R.id.question);
            reply = (ImageView) itemView.findViewById(R.id.reply);
            like = (ImageView) itemView.findViewById(R.id.like);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);// bind the listener
        }

        @Override
        public void onClick(View view) {
        }

    }
}