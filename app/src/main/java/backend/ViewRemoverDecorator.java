package backend;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ViewRemoverDecorator extends ViewDecorator{

    public ViewRemoverDecorator(Context context, View[] views){
        super(context, views);
    }

    @Override
    public void decorate() {
        for (View view : this.views) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }
}
