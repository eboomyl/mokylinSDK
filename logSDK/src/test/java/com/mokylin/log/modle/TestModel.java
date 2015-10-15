package com.mokylin.log.modle;

import com.mokylin.log.model.HeroModel;

/**
 * Created by Administrator on 2015/10/14.
 */
public class TestModel {
    public static void main(String [] args){
        HeroModel heroModel =  new HeroModel();
        heroModel.setDtEventTime("1");
        heroModel.setiEventId("2");
        heroModel.setiNewCash("3");
        heroModel.setiPayDelta("4");

        System.out.println( heroModel.getDtoToJson());


        System.out.println(heroModel.getDtoToStringMap().toString());


    }
}
