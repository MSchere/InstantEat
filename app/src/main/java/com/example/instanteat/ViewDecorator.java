package com.example.instanteat;

import android.content.Context;
import android.view.View;

import java.util.Arrays;

import backend.Decorable;

public abstract class ViewDecorator implements Decorable {

    protected View[] views;
    protected Context context;

    public ViewDecorator(Context context, View[] views) {
        super();
        this.context = context;
        this.views = Arrays.copyOf(views, views.length);
    }
}
