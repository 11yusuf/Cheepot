package zn2.ft.aj.cheepot.adpater;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import zn2.ft.aj.cheepot.R;

/**
 * Created by lenovo-pc on 15/12/2017.
 */

public class PartnerAdapter extends ArrayAdapter<String> {
    private  Context context;
    private String[] values;

    public PartnerAdapter (Context context, String[] values) {
        super(context, R.layout.activity_partners_list, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_partners_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.namePartner);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.partnerLogo);
        textView.setText(values[position]);

        String s = values[position];

        System.out.println(s);

        if (s.equals("Sasio")) {
            imageView.setImageResource(R.drawable.sasio);
            imageView.setScaleType(ImageView.ScaleType.FIT_START);}
        else if (s.equals("Mabrouk") ){
            imageView.setImageResource(R.drawable.mabrouk);
            imageView.setScaleType(ImageView.ScaleType.FIT_START);
        }else {
            textView.setTextColor(Color.parseColor("#4A89DC"));
            textView.setGravity(Gravity.CENTER);
            imageView.setImageResource(R.drawable.ic_money_bag);
            imageView.getLayoutParams().height = 200;
            imageView.getLayoutParams().width = 150;
            imageView.setScaleType(ImageView.ScaleType.FIT_START);
        }

        return rowView;
    }

}
