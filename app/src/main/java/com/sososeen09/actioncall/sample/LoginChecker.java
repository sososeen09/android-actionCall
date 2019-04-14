package com.sososeen09.actioncall.sample;

import android.content.Context;
import android.content.Intent;
import com.sososeen09.actioncall.Checker;

class LoginChecker implements Checker {

  private final Context mContext;

  public LoginChecker(Context context) {
    this.mContext = context;
  }

  @Override
  public boolean check() {
    return UserInfo.isLogin;
  }

  @Override
  public void doAction() {
    mContext.startActivity(new Intent(mContext, LoginActivity.class));
  }
}
