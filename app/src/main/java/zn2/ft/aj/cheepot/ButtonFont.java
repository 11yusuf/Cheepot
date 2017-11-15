package zn2.ft.aj.cheepot;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by yusuf on 14/11/2017.
 */

public class ButtonFont extends android.support.v7.widget.AppCompatButton{
        public ButtonFont(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Alegreya-Regular.otf"));
            this.getBackground().setAlpha(64);
        }
}
