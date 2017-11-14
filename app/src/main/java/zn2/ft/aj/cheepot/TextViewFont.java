package zn2.ft.aj.cheepot;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by yusuf on 14/11/2017.
 */

public class TextViewFont extends android.support.v7.widget.AppCompatTextView {
        public TextViewFont(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Alegreya-Regular.otf"));
        }
}
