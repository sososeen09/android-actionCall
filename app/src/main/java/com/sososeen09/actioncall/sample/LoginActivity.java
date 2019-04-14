package com.sososeen09.actioncall.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class LoginActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);


    findViewById(R.id.btn_login).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        UserInfo.isLogin = true;
        finish();
      }
    });
  }
}
