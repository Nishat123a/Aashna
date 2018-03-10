package aashna.com.aashna.H_HealthTips;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import aashna.com.aashna.aashna_main.ItemClickListener;
import aashna.com.aashna.R;
import aashna.com.aashna.S_SelfSafety.AnimationUtil;
import aashna.com.aashna.aashna_main.Tips;

import java.util.ArrayList;

class Ht_adapter extends RecyclerView.Adapter<Ht_adapter.ViewHolder>  {

    private ArrayList<Tips> htips;
    private Context mContext;
    int previousPosition = 0;
    private ItemClickListener clickListener;
    String description,title;

    // Pass in the country array into the constructor
    public Ht_adapter(ArrayList<Tips> htips, Context context) {
        this.htips = htips;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ht_click_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //holder.nImage.setImageResource(R.drawable.htip);
        holder.tip_brief.setText(htips.get(position).getTitle_Tip());

        switch (position) {
            case 0:
                holder.nImage.setImageResource(R.drawable.ht1);
                break;
            case 1:
                holder.nImage.setImageResource(R.drawable.ht2);
                break;
            case 2:
                holder.nImage.setImageResource(R.drawable.ht3);
                break;
            case 3:
                holder.nImage.setImageResource(R.drawable.ht4);
                break;
            case 4:
                holder.nImage.setImageResource(R.drawable.ht5);
                break;
            case 5:
                holder.nImage.setImageResource(R.drawable.ht6);
                break;
        }

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
        return htips == null ? 0 : htips.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public Tips tips(int position) {
        return htips.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /* Your holder should contain a member variable
     for any view that will be set as you render a row */
        TextView tip_brief;
        private ImageView nImage;
        Dialog dialog;


        /*constructor that accepts the entire item row
             and does the view lookups to find each subview */
        public ViewHolder(View itemView) {

            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nImage = (ImageView) itemView.findViewById(R.id.thumbnail);
            tip_brief = (TextView) itemView.findViewById(R.id.tip_brief);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);// bind the listener
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
            // call the onClick in the OnItemClickListener

            title = tips(getAdapterPosition()).getTitle_Tip();
            description = tips(getAdapterPosition()).getDesc();

            dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.tips_custom_dialogue);
            dialog.show();

            TextView title_textview = (TextView) dialog .findViewById(R.id.tip_title);
            TextView desc = (TextView) dialog.findViewById(R.id.paragraph);

            title_textview.setText(title);
            desc.setText(description);

            Button close = (Button)dialog.findViewById(R.id.close);


            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

    }
}



