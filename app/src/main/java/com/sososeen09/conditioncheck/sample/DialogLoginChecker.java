package com.sososeen09.conditioncheck.sample;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import com.sososeen09.actioncall.Checker;

class DialogLoginChecker implements Checker {

  private final Context mContext;
  private final Runnable mPendingTask;
  private AlertDialog mAlertDialog;
  private final Handler mHandler;

  public DialogLoginChecker(Context context, Runnable runnable) {
    this.mContext = context;
    this.mPendingTask = runnable;
    mHandler = new Handler(Looper.getMainLooper());
  }

  @Override
  public boolean check() {
    return UserInfo.isLogin;
  }

  @Override
  public void doAction() {
    if (mAlertDialog == null) {
      mAlertDialog = new AlertDialog.Builder(mContext).setMessage("login?")
          .setNegativeButton(
              "cancel", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  mAlertDialog.dismiss();
                }
              })
          .setPositiveButton("login", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //模拟登陆网络请求
              mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                  UserInfo.isLogin = true;
                  mAlertDialog.dismiss();
                  if (mPendingTask != null) {
                    mPendingTask.run();
                  }
                }
              }, 1500);
            }
          })
          .create();
    }

    mAlertDialog.show();
  }
}
