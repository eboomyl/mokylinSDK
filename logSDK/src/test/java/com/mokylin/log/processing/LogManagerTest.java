package com.mokylin.log.processing;

import com.mokylin.log.model.HeroModel;
import com.mokylin.log.model.LogModel;
import com.mokylin.log.performance.CounterTracer;
import com.mokylin.log.performance.SimpleTracer;
import com.mokylin.log.util.StringMap;

/**
 * Created by Administrator on 2015/9/21.
 */
public class LogManagerTest {
    public static void main(String[] args) throws InterruptedException {
        LogManager.auth("yuanlin","yuanlin");
        int date_count = 100000;

        for(int i = 0;i<date_count;i++){
            LogManager.Type("login"+i).set("test2", "value1").set("test2", 123).commit();
        }
        System.out.println("11111");
        LogManager.stop();
        System.out.println(LogManager.getMilliTimeSpan());
    }
}
interface IHero extends HeroModel {
    int getOperatorID();
    int getServerID();
    String getUin();

}
