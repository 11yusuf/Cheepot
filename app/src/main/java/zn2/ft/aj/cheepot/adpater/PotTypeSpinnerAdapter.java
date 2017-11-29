package zn2.ft.aj.cheepot.adpater;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import zn2.ft.aj.cheepot.R;


public class PotTypeSpinnerAdapter extends ArrayAdapter<PotType> {
    private Context context;

    List<PotType> date = null;

    public PotTypeSpinnerAdapter(Context context, List<PotType> date) {
        super(context, R.layout.spinner_selected_item, date);
        this.context = context;
        this.date = date;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.spinner_selected_item, null);
        }
        ((TextView) convertView.findViewById(R.id.texto)).setText(date.get(position).getName());
        ((ImageView) convertView.findViewById(R.id.icono)).setBackgroundResource(date.get(position).getIcon());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.spinner_list_item, parent, false);
        }

        if (row.getTag() == null) {
            PotTypeHolder redSocialHolder = new PotTypeHolder();
            redSocialHolder.seticon((ImageView) row.findViewById(R.id.icono));
            redSocialHolder.setTextView((TextView) row.findViewById(R.id.texto));
            row.setTag(redSocialHolder);
        }

        PotType PotType = date.get(position);
        ((PotTypeHolder) row.getTag()).geticon().setImageResource(PotType.getIcon());
        ((PotTypeHolder) row.getTag()).getTextView().setText(PotType.getName());

        return row;
    }
    private static class PotTypeHolder {

        private ImageView icon;

        private TextView textView;

        public ImageView geticon() {
            return icon;
        }

        public void seticon(ImageView icon) {
            this.icon = icon;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }
}


