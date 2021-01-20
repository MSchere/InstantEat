package backend;

import android.content.Context;
import android.view.View;

public class ViewEnablerDecorator extends ViewDecorator{
    Boolean enabled;
    public ViewEnablerDecorator(Context context, View[] views, Boolean enabled){
        super(context, views);
        this.enabled = enabled;
    }

    @Override
    public void decorate() {
        for (View view : this.views) {
            view.setEnabled(this.enabled);
        }
    }
}
