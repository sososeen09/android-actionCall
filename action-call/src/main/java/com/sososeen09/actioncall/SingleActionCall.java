package com.sososeen09.actioncall;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 前置条件检查管理类
 *
 * @author yunlong
 */
public class SingleActionCall {

  private TargetAction mTargetAction;
  private Queue<ConditionChecker> mConditionCheckers = new ArrayDeque<>();
  private boolean mIsHandle;
  private ConditionChecker mCurrentCondition;

  private SingleActionCall() {
  }

  private static class ConditionCheckManagerHolder {

    private static final SingleActionCall INSTANCE = new SingleActionCall();
  }

  public static SingleActionCall getInstance() {
    return ConditionCheckManagerHolder.INSTANCE;
  }

  public SingleActionCall addTargetAction(TargetAction targetAction) {
    reset();
    this.mTargetAction = targetAction;
    return this;
  }

  public SingleActionCall addConditionAction(ConditionChecker conditionChecker) {
    if (mTargetAction == null) {
      throw new IllegalArgumentException("must set TargetAction first!!!");
    }
    if (!conditionChecker.check()) {
      mConditionCheckers.offer(conditionChecker);
    }
    return this;
  }

  public final void continueGo() {
    if (!mIsHandle) {
      reset();
      return;
    }

    if (mCurrentCondition == null || mCurrentCondition.check()) {
      goAhead();
      return;
    }

    reset();
  }

  public final void goAhead() {
    this.mIsHandle = true;
    mCurrentCondition = mConditionCheckers.poll();

    if (mCurrentCondition != null) {
      if (mCurrentCondition.check()) {
        if (mTargetAction != null) {
          mTargetAction.action();
        }
      } else {
        mCurrentCondition.doAction();
        return;
      }
    } else {
      if (mTargetAction != null) {
        mTargetAction.action();
      }
    }
    reset();
  }

  private void reset() {
    mTargetAction = null;
    mConditionCheckers.clear();
    mCurrentCondition = null;
    mIsHandle = false;
  }

}
