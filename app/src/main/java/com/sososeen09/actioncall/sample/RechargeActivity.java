package com.sososeen09.actioncall.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class RechargeActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recharge);

    findViewById(R.id.btn_recharge).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        UserInfo.moneyMount = 100;
        finish();
      }
    });
  }
}
