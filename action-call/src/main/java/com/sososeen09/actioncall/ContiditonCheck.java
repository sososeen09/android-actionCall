package com.sososeen09.actioncall;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 希望在后续功能中添加对某个任意方法执行的监听
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface ContiditonCheck {

  String value();
}
