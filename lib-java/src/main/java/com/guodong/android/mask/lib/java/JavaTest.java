package com.guodong.android.mask.lib.java;

import com.guodong.android.mask.api.Hide;

/**
 * Created by guodongAndroid on 2021/12/29.
 */
public class JavaTest {

    private int a = -1;

    @Hide
    public JavaTest() {
    }

    public JavaTest(int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }

    public void test() {
        setA(1000);
    }

    @Hide
    public void setA(int a) {
        this.a = a;
    }
}
