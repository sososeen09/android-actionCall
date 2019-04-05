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

  /**
   * 添加目标Action
   */
  public ActionCall addTargetAction(TargetAction targetAction) {
    reset();
    this.mTargetAction = targetAction;
    return this;
  }

  /**
   * 添加条件action
   */
  public ActionCall addConditionAction(Checker checker) {
    checkTargetAction();
    if (!checker.check()) {
      mConditionCheckers.offer(checker);
    }
    return this;
  }

  /**
   * 添加结果检查
   */
  public ActionCall addResultAction(Checker checker) {
    checkTargetAction();
    mResultChecker = checker;
    return this;
  }

  private void checkTargetAction() {
    if (mTargetAction == null) {
      throw new IllegalArgumentException("must set TargetAction first!!!");
    }
  }

  /**
   * 继续执行，目前在使用过程中是与对象的生命周期方法中进行调用，后面考虑可以在对象的任意方法进行调用
   */
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

  /**
   * 初次执行
   */
  public final void goAhead() {
    checkTargetAction();
    this.mIsHandle = true;
    mCurrentCondition = mConditionCheckers.poll();

    if (mCurrentCondition != null) {
      if (mCurrentCondition.check()) {
        mTargetAction.action();
      } else {
        mCurrentCondition.doAction();
        return;
      }
    } else {
      mTargetAction.action();
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
