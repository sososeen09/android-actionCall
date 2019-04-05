package com.sososeen09.conditioncheck.sample;

import android.content.Context;
import android.content.Intent;
import com.sososeen09.actioncall.ConditionChecker;

class LoginConditionChecker implements ConditionChecker {

  private final Context mContext;

  public LoginConditionChecker(Context context) {
    this.mContext = context;
  }

  @Override
  public boolean check() {
    return LoginStatus.isLogin;
  }

  @Override
  public void doAction() {
    mContext.startActivity(new Intent(mContext, LoginActivity.class));
  }
}
