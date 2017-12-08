package zn2.ft.aj.cheepot.adpater;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

import zn2.ft.aj.cheepot.FeedbackActivity;
import zn2.ft.aj.cheepot.LoginActivity;
import zn2.ft.aj.cheepot.R;
import zn2.ft.aj.cheepot.SignUpActivity;



public class PotAdapter extends RecyclerView.Adapter<PotAdapter.ViewHolder>{
    private String[] mDataSet;
    private Context mContext;
    private Random mRandom = new Random();


    public PotAdapter(Context context,String[] DataSet){
        mDataSet = DataSet;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public LinearLayout mLinearLayout;
        public View view;
        public ClipData.Item currentItem;
        public ViewHolder(View v){
            super(v);
            view = v;
            mTextView = (TextView) v.findViewById(R.id.tv);
            mLinearLayout = (LinearLayout) v.findViewById(R.id.ll);
            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent nextIntent ;
                    Context context=v.getContext();
                    nextIntent = new Intent(context, FeedbackActivity.class);
                    context.startActivity(nextIntent);
                }
            });
        }
    }

    @Override
    public PotAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_view,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.mTextView.setText(mDataSet[position]);

    }

    @Override
    public int getItemCount(){
        return mDataSet.length;
    }


}