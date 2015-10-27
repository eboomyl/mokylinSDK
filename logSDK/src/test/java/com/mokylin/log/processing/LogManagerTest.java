package com.mokylin.log.processing;

import com.mokylin.log.model.HeroModel;

/**
 * Created by Administrator on 2015/9/21.
 */
public class LogManagerTest {
    public static void main(String[] args) throws Exception {
        LogManager.auth("yuanlin","yuanlin");
        int date_count = 100000;
        HeroModel heroModel =  new HeroModel();
        heroModel.setDtEventTime("20151010080221");
        heroModel.setiEventId("21232131");
        heroModel.setiNewCash("3222");
        heroModel.setiPayDelta("41123");
        heroModel.setiRoleId("testRoleID");
        heroModel.setvRoleName("古剑奇谭");


        for(int i = 0;i<date_count;i++){
            LogManager.Type("login"+i).setLogModel(heroModel).set("test2", "value1").set("test2", 123).commit();
        }
        LogManager.stop();
        System.out.println("11111");
        System.out.println(LogManager.getMilliTimeSpan());
    }
}
