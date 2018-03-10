package aashna.com.aashna.S_ContributeToNGO;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import aashna.com.aashna.R;
import aashna.com.aashna.S_SelfSafety.AnimationUtil;

class C_NGO_Adapter extends RecyclerView.Adapter<C_NGO_Adapter.ViewHolder>  {

    ArrayList<C_ngo_pojo> c_ngo_list;
    private Context mContext;
    int previousPosition = 0;

    // Pass in the country array into the constructor
    public C_NGO_Adapter(ArrayList<C_ngo_pojo> c_ngo_list, Context context) {
        this.c_ngo_list = c_ngo_list;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ngo_contribute_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        holder.ngo_name.setText(c_ngo_list.get(position).getNgo_name());
        holder.acct.setText(c_ngo_list.get(position).getAccount());

        holder.ptm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Contribute through PAYTM", Toast.LENGTH_SHORT).show();
            }
        });

        holder.mk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Contribute through MOBIKWIK", Toast.LENGTH_SHORT).show();
            }
        });

        holder.bhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Contribute through BHIM", Toast.LENGTH_SHORT).show();
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
        return c_ngo_list == null ? 0 : c_ngo_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /* Your holder should contain a member variable
     for any view that will be set as you render a row */
        TextView ngo_name,acct,mk,ptm,bhim;
       // ImageView ptm,bhim;
       // Button mk,ptm,bhim;


        /*constructor that accepts the entire item row
             and does the view lookups to find each subview */
        public ViewHolder(View itemView) {

            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            ptm = (TextView) itemView.findViewById(R.id.paytm_btn);
            mk = (TextView) itemView.findViewById(R.id.other);
            bhim = (TextView) itemView.findViewById(R.id.bhim_btm);
            ngo_name = (TextView) itemView.findViewById(R.id.ngo_name);
            acct = (TextView) itemView.findViewById(R.id.ngo_acc);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);// bind the listener
        }

        @Override
        public void onClick(View view) {

        }

    }
}