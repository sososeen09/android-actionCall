package com.sososeen09.actioncall.sample;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.sososeen09.actioncall.ActionCall;
import com.sososeen09.actioncall.TargetAction;

public class PageView extends LinearLayout {

  private static final String TAG = "PageView";

  public PageView(Context context) {
    this(context, null);
  }

  public PageView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public PageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    View viewRoot = LayoutInflater.from(context).inflate(R.layout.layout_page_view, this, false);
    viewRoot.findViewById(R.id.btn_buy_book).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        buyBook();
      }
    });

    addView(viewRoot);
  }

  @Override
  public void onWindowFocusChanged(boolean hasWindowFocus) {
    super.onWindowFocusChanged(hasWindowFocus);
    Log.e(TAG, "onWindowFocusChanged: " + hasWindowFocus);
  }

  private void buyBook() {
    ActionCall.getInstance()
        .addTargetAction(new TargetAction() {
          @Override
          public void action() {
            Toast.makeText(getContext(), "buy book 2 success", Toast.LENGTH_SHORT).show();
          }
        })
        .addConditionAction(new DialogLoginChecker(getContext(), new Runnable() {
          @Override
          public void run() {
            ActionCall.getInstance().continueGo();
          }
        }))
        .addConditionAction(new MoneyChecker(getContext()))
        .goAhead();
  }


}
