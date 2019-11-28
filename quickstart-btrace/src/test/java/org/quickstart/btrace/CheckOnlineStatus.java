package org.quickstart.btrace;

import static com.sun.btrace.BTraceUtils.println;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Duration;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;

/**
 * @author youngzil@163.com
 * @description TODO
 * @createTime 2019/11/28 18:23
 */
@BTrace
public class CheckOnlineStatus {

  //监控某一个方法的执行时间
  @OnMethod(clazz = "com.joson.btrace.service.impl.BtraceServiceImpl", method = "getCount", location = @Location(Kind.RETURN))
  public static void printMethodRunTime(@ProbeClassName String probeClassName, @Duration long duration) {

    println(probeClassName + ",duration:" + duration / 1000000 + " ms");

  }

}
