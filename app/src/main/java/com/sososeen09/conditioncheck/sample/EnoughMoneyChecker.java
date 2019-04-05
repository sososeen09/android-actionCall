package com.sososeen09.conditioncheck.sample;

import android.content.Context;
import android.content.Intent;
import com.sososeen09.actioncall.Checker;

class EnoughMoneyChecker implements Checker {

  private final Context mContext;

  public EnoughMoneyChecker(Context context) {
    this.mContext = context;
  }

  @Override
  public boolean check() {
    return UserInfo.moneyMount > 0;
  }

  @Override
  public void doAction() {
    mContext.startActivity(new Intent(mContext, RechargeActivity.class));
  }
}
