package com.sososeen09.conditioncheck.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.sososeen09.actioncall.SingleActionCall;
import com.sososeen09.actioncall.TargetAction;
import com.sososeen09.conditioncheck.R;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //获取订单，需要登录
    findViewById(R.id.btn_getOrder).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        SingleActionCall.getInstance()
            .addTargetAction(new TargetAction() {
              @Override
              public void action() {
                Toast.makeText(MainActivity.this, "get order success", Toast.LENGTH_SHORT).show();
              }
            })
            .addConditionAction(new LoginConditionChecker(MainActivity.this))
            .goAhead();
      }
    });

    //买书，需要登录并且余额充足
    findViewById(R.id.btn_buy_book).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        SingleActionCall.getInstance()
            .addTargetAction(new TargetAction() {
              @Override
              public void action() {
                Toast.makeText(MainActivity.this, "buy book success", Toast.LENGTH_SHORT).show();
              }
            })
            .addConditionAction(new LoginConditionChecker(MainActivity.this))
            .addConditionAction(new EnoughMoneyChecker(MainActivity.this))
            .goAhead();
      }
    });

    //reset status
    findViewById(R.id.btn_reset).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        resetStatus();
      }
    });
  }

  private void resetStatus() {
    UserInfo.moneyMount = 0;
    LoginStatus.isLogin = false;
  }


  @Override
  protected void onResume() {
    super.onResume();
    SingleActionCall.getInstance().continueGo();
  }

}
