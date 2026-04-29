package com.pluralsight;


public class Model {
    private static Model model;
    private final ViewSwap viewSwap;

    public Model() {
        this.viewSwap = new ViewSwap();
    }
    //If model is null, the object hasn't been created yet so it returns model.
    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewSwap getViewSwap() {
        return viewSwap;
    }
}
