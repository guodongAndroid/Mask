package com.guodong.android.mask.app;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.guodong.android.mask.lib.java.JavaTest;
import com.guodong.android.mask.lib.kotlin.KotlinTest;

/**
 * Created by guodongAndroid on 2021/12/29.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testJavaLib();
        testKotlinLib();
    }

    private void testKotlinLib() {
        KotlinTest test = KotlinTest.newKotlinTest();
        Log.e(TAG, "testKotlinLib: before --> " + test.getA1());
        test.test();
        Log.e(TAG, "testKotlinLib: after --> " + test.getA1());
    }

    private void testJavaLib() {
        JavaTest test = new JavaTest(-100);
        Log.e(TAG, "testJavaLib: before --> " + test.getA());
        test.test();
        Log.e(TAG, "testJavaLib: after --> " + test.getA());
    }
}
