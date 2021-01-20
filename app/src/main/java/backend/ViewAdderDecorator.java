package backend;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ViewAdderDecorator extends ViewDecorator{

    public ViewAdderDecorator(Context context, View[] views){
        super(context, views);
    }

    @Override
    public void decorate() {
        for (View view : this.views) {
            ((ViewGroup) view.getParent()).addView(view);
        }
    }
}
