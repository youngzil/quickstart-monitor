package org.quickstart.btrace;

import static com.sun.btrace.BTraceUtils.println;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
/**
 * @author youngzil@163.com
 * @description TODO
 * @createTime 2019/11/28 18:13
 */
@BTrace
public class Tracer {

  @OnMethod(clazz = "java.lang.Thread", method = "start")
  public static void onThreadStart() {
    println("tracing method start");
  }

}
