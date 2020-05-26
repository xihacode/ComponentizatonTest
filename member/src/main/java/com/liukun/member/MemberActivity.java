package com.liukun.member;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.liukun.annotation.BindPath;

@BindPath("member/member")
public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem);
    }
}
