package com.mokylin.log.model;

/**
 * Created by Administrator on 2015/10/8.
 */
public interface HeroModel extends LogModel {
    String getIEventId();
    String getDtEventTime();
    long getIOperatorId();
    long getServerId();
    long getIWorldId();
    String getIUin();
    String getIRoleId();
    String getVRoleName();
    long getIRoleLevel();

}
