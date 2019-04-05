package com.sososeen09.actioncall;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 前置条件检查管理类
 *
 * @author yunlong
 */
public class ActionCall {

  private TargetAction mTargetAction;
  private Queue<Checker> mConditionCheckers = new ArrayDeque<>();
  private boolean mIsHandle;
  private Checker mCurrentCondition;
  private Checker mResultChecker;

  private ActionCall() {
  }

  private static class ConditionCheckManagerHolder {

    private static final ActionCall INSTANCE = new ActionCall();
  }

  public static ActionCall getInstance() {
    return ConditionCheckManagerHolder.INSTANCE;
  }

  public ActionCall addTargetAction(TargetAction targetAction) {
    reset();
    this.mTargetAction = targetAction;
    return this;
  }

  public ActionCall addConditionAction(Checker checker) {
    if (mTargetAction == null) {
      throw new IllegalArgumentException("must set TargetAction first!!!");
    }
    if (!checker.check()) {
      mConditionCheckers.offer(checker);
    }
    return this;
  }

  public ActionCall addResultAction(Checker checker) {
    if (mTargetAction == null) {
      throw new IllegalArgumentException("must set TargetAction first!!!");
    }
    mResultChecker = checker;
    return this;
  }

  public final void continueGo() {
    if (!mIsHandle) {
      return;
    }

    if (mCurrentCondition != null && mCurrentCondition.check()) {
      goAhead();
      return;
    }

    if (mResultChecker != null && mResultChecker.check()) {
      mResultChecker.doAction();
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
    if (mResultChecker == null) {
      reset();
    }
  }

  private void reset() {
    mTargetAction = null;
    mConditionCheckers.clear();
    mCurrentCondition = null;
    mIsHandle = false;
    mResultChecker = null;
  }

}
