package aashna.com.aashna.H_PCare;

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

import aashna.com.aashna.R;
import aashna.com.aashna.S_SelfSafety.AnimationUtil;

import java.util.ArrayList;

class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>  {

    ArrayList<Food_habits_pojo> foodlist;
    private Context mContext;
    int previousPosition = 0;

    // Pass in the country array into the constructor
    public FoodAdapter(ArrayList<Food_habits_pojo> foodlist, Context context) {
        this.foodlist = foodlist;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_habit_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final  int position) {

       // holder.nImage.setImageResource(R.drawable.person_img);
        holder.name.setText(foodlist.get(position).getFood());

        switch (position){
            case 0 :
                holder.nImage.setImageResource(R.drawable.pos0);
                break;
            case 1:
                holder.nImage.setImageResource(R.drawable.pos1);
                break;
            case 2:
                holder.nImage.setImageResource(R.drawable.pos2);
                break;
            case 3:
                holder.nImage.setImageResource(R.drawable.pos3);
                break;
            case 4:
                holder.nImage.setImageResource(R.drawable.pos4);
                break;
            case 5:
                holder.nImage.setImageResource(R.drawable.pos5);
                break;
            case 6:
                holder.nImage.setImageResource(R.drawable.pos6);
                break;
            case 7:
                holder.nImage.setImageResource(R.drawable.pos7);
                break;
            case 8:
                holder.nImage.setImageResource(R.drawable.pos8);
                break;
            case 9:
                holder.nImage.setImageResource(R.drawable.pos9);
                break;
            case 10:
                holder.nImage.setImageResource(R.drawable.pos10);
                break;
            case 11:
                holder.nImage.setImageResource(R.drawable.pos11);
                break;
            case 12:
                holder.nImage.setImageResource(R.drawable.pos12);
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
        return foodlist == null ? 0 : foodlist.size();
    }

    public Food_habits_pojo food(int position) {
        return foodlist.get(position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /* Your holder should contain a member variable
     for any view that will be set as you render a row */
        TextView name;
        private ImageView nImage;
        Dialog dialog;
        TextView desc;



        public ViewHolder(View itemView) {

            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nImage = (ImageView) itemView.findViewById(R.id.thumbnail);
            name = (TextView) itemView.findViewById(R.id.food_name);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);// bind the listener
        }

        @Override
        public void onClick(View view) {

            String food_name = food(getAdapterPosition()).getFood();

            dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.food_custom);
            dialog.show();

            TextView title_textview = (TextView) dialog.findViewById(R.id.tip_title);
            desc = (TextView) dialog.findViewById(R.id.paragraph);

            title_textview.setText(food_name);

            if (food_name.equals("Dairy products")) {
                desc.setText(R.string.pos0);
            } else if (food_name.equals("Legumes")){
                desc.setText(R.string.pos1);
            }else if (food_name.equals("Sweet potatoes")) {
                desc.setText(R.string.pos2);
            }else if (food_name.equals("Salmon")) {
                desc.setText(R.string.pos3);
            }else if (food_name.equals("Eggs")) {
                desc.setText(R.string.pos4);
            }else if (food_name.equals("Broccoli and dark, leafy greens")) {
                desc.setText(R.string.pos5);
            }else if (food_name.equals("Lean meat")) {
                desc.setText(R.string.pos6);
            }else if (food_name.equals("Fish liver oil")) {
                desc.setText(R.string.pos7);
            }else if (food_name.equals("Berries")) {
                desc.setText(R.string.pos8);
            }else if (food_name.equals("Whole grains")) {
                desc.setText(R.string.pos9);
            }else if (food_name.equals("Avocados")) {
                desc.setText(R.string.pos10);
            }else if (food_name.equals("Dried fruit")) {
                desc.setText(R.string.pos11);
            }else if (food_name.equals("Water")) {
                desc.setText(R.string.pos12);
            }

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