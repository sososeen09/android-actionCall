package com.sososeen09.conditioncheck.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.sososeen09.conditioncheck.R;

public class MemberActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_member);

    findViewById(R.id.btn_open).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        UserInfo.isMember = true;
        finish();
      }
    });
  }
}
