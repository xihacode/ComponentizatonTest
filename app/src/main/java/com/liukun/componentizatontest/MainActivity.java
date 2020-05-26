package com.liukun.componentizatontest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.liukun.annotation.BindPath;
import com.liukun.arouter.ARouter;

@BindPath("main/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpActivity(View view) {
        Toast.makeText(this,"ssss",Toast.LENGTH_LONG).show();
        ARouter.getInstance().jumpActivity("login/login", null);
    }
}
