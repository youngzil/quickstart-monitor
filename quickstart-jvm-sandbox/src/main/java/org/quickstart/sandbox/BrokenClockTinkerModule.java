package org.quickstart.sandbox;


import javax.annotation.Resource;

/**
 * @author youngzil@163.com
 * @description TODO
 * @createTime 2019/11/27 15:56
 */
@MetaInfServices(Module.class)
@Information(id = "broken-clock-tinker")
public class BrokenClockTinkerModule implements Module {

  @Resource
  private ModuleEventWatcher moduleEventWatcher;

  @Command("repairCheckState")
  public void repairCheckState() {

    new EventWatchBuilder(moduleEventWatcher)
        .onClass("com.taobao.demo.Clock")
        .onBehavior("checkState")
        .onWatch(new AdviceListener() {

          /**
           * 拦截{@code com.taobao.demo.Clock#checkState()}方法，当这个方法抛出异常时将会被
           * AdviceListener#afterThrowing()所拦截
           */
          @Override
          protected void afterThrowing(Advice advice) throws Throwable {

            // 在此，你可以通过ProcessController来改变原有方法的执行流程
            // 这里的代码意义是：改变原方法抛出异常的行为，变更为立即返回；void返回值用null表示
            ProcessController.returnImmediately(null);
          }
        });

  }

}