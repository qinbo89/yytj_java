package com.hongbao.utils;

public class ObjectContainer<T> {
    private T t;

    public ObjectContainer() {
        super();
    }
    
    public ObjectContainer(T dft) {
        super();
        this.t = dft;
    }

    public void set(T t) {
        this.t = t;
    }

    public T get() {
        return this.t;
    }
}
