package com.sososeen09.actioncall.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.sososeen09.actioncall.ActionCall;
import com.sososeen09.actioncall.Checker;
import com.sososeen09.actioncall.TargetAction;

public class MainActivity extends AppCompatActivity {

  public static final String TAG = "MainActivity";
  PageView mPageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mPageView = findViewById(R.id.page_view);
    //获取订单，需要登录
    findViewById(R.id.btn_getOrder).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ActionCall.getInstance()
            .addTargetAction(new TargetAction() {
              @Override
              public void action() {
                Toast.makeText(MainActivity.this, "get order success", Toast.LENGTH_SHORT).show();
              }
            })
            .addConditionAction(new LoginChecker(MainActivity.this))
            .goAhead();
      }
    });

    //买书，需要登录并且余额充足
    findViewById(R.id.btn_buy_book).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ActionCall.getInstance()
            .addTargetAction(new TargetAction() {
              @Override
              public void action() {
                Toast.makeText(MainActivity.this, "buy book success", Toast.LENGTH_SHORT).show();
              }
            })
            .addConditionAction(new LoginChecker(MainActivity.this))
            .addConditionAction(new MoneyChecker(MainActivity.this))
            .goAhead();
      }
    });

    //进入会员页面，需要登录，开通会员之后返回刷新页面
    findViewById(R.id.btn_member).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ActionCall.getInstance()
            .addTargetAction(new TargetAction() {
              @Override
              public void action() {
                startActivity(new Intent(MainActivity.this, MemberActivity.class));
              }
            })
            .addConditionAction(new LoginChecker(MainActivity.this))
            .addResultAction(new Checker() {
              @Override
              public boolean check() {
                return UserInfo.isMember;
              }

              @Override
              public void doAction() {
                setMemberStatus();
              }
            })
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

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    Log.e(TAG, "onWindowFocusChanged: " + hasFocus);
  }

  private void setMemberStatus() {
    ((TextView) findViewById(R.id.tv_member_status))
        .setText("member status: " + (UserInfo.isMember));
  }

  private void resetStatus() {
    UserInfo.moneyMount = 0;
    UserInfo.isLogin = false;
    UserInfo.isMember = false;
    setMemberStatus();
  }


  @Override
  protected void onResume() {
    super.onResume();
    ActionCall.getInstance().continueGo();
  }

}
