package com.designpattern.dp5prototypepattern.deepcopy;

import java.io.Serializable;

public class Rectangle implements Serializable {
    public float l = 100;
    public float w = 100;
    public void big(){
        l *= 2;
        w *= 2;
    }
    public void small(){
        l /= 2;
        w /= 2;
    }
}
