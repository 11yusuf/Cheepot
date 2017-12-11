package zn2.ft.aj.cheepot.adpater;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import zn2.ft.aj.cheepot.FeedbackActivity;
import zn2.ft.aj.cheepot.HomeActivity;
import zn2.ft.aj.cheepot.LoginActivity;
import zn2.ft.aj.cheepot.PotCreationActivity;
import zn2.ft.aj.cheepot.R;
import zn2.ft.aj.cheepot.SignUpActivity;
import zn2.ft.aj.cheepot.SplashActivity;
import zn2.ft.aj.cheepot.data.Pot;


public class PotAdapter extends RecyclerView.Adapter<PotAdapter.ViewHolder>{
    private List<Pot> mDataSet;
    private Context mContext;
    private int type;


    public PotAdapter(Context context, List<Pot> DataSet , int type){
        mDataSet = new ArrayList<Pot>();
        mDataSet = DataSet;
        mContext = context;
        this.type = type;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public LinearLayout mLinearLayout;
        public CardView view;
        public ClipData.Item currentItem;
        public Pot potClickedOn;
        public ViewHolder(CardView v , int type){
            super(v);
            final int typeEntry = type;
            view = v;
            final Context context=v.getContext();
            mTextView = (TextView) v.findViewById(R.id.tv);
            mLinearLayout = (LinearLayout) v.findViewById(R.id.ll);
            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent nextIntent ;
                    nextIntent = new Intent(context,PotCreationActivity.class);
                    nextIntent.putExtra("typeEntry", typeEntry);
                    nextIntent.putExtra("pot", potClickedOn);
                    context.startActivity(nextIntent);
                }
            });
        }
    }

    @Override
    public PotAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        CardView v = (CardView)LayoutInflater.from(mContext).inflate(R.layout.custom_view,parent,false);
        ViewHolder vh = new ViewHolder(v , type);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.mTextView.setText(mDataSet.get(position).potName);
        holder.potClickedOn = mDataSet.get(position);
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }


}