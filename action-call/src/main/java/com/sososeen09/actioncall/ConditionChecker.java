package com.sososeen09.actioncall;

/**
 * 用于检查条件是否满足
 * @author yunlong
 */
public interface ConditionChecker {


  /**
   * 检查条件是否满足
   * @return 如果返回 true 就继续执行下去，false 就执行 {{@link #doAction()}} 方法
   */
  boolean check();

  /**
   * 在不满足条件校验时执行该方法，用户获得可以满足的条件
   */
  void doAction();
}
