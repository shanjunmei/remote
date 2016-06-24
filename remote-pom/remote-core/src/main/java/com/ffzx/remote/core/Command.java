package com.ffzx.remote.core;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
* @ClassName: Command 
* @Description: command 
* @author 李淼淼  445052471@qq.com
* @date 2016年5月31日 上午11:06:09
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Command {
	String value() default "";
}
