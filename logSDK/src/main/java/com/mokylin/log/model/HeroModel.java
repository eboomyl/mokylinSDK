package com.mokylin.log.model;

/**
 * Created by Administrator on 2015/10/13.
 */
public class HeroModel extends BaseModel {
    public String RoleLevelUp;//角色等级
    public String iEventId;//游戏事件ID
    public String iWorldId;//游戏大区ID
    public String iUin;//用户ID
    public String dtEventTime;//记录时间
    public String iRoleId;//角色id
    public String vRoleName;//角色名
    public String iPayDelta;//充值金额
    public String iNewCash;//充值元宝余量


    public String getRoleLevelUp() {
        return RoleLevelUp;
    }

    public void setRoleLevelUp(String roleLevelUp) {
        RoleLevelUp = roleLevelUp;
    }

    public String getiEventId() {
        return iEventId;
    }

    public void setiEventId(String iEventId) {
        this.iEventId = iEventId;
    }

    public String getiWorldId() {
        return iWorldId;
    }

    public void setiWorldId(String iWorldId) {
        this.iWorldId = iWorldId;
    }

    public String getiUin() {
        return iUin;
    }

    public void setiUin(String iUin) {
        this.iUin = iUin;
    }

    public String getDtEventTime() {
        return dtEventTime;
    }

    public void setDtEventTime(String dtEventTime) {
        this.dtEventTime = dtEventTime;
    }

    public String getiRoleId() {
        return iRoleId;
    }

    public void setiRoleId(String iRoleId) {
        this.iRoleId = iRoleId;
    }

    public String getvRoleName() {
        return vRoleName;
    }

    public void setvRoleName(String vRoleName) {
        this.vRoleName = vRoleName;
    }

    public String getiPayDelta() {
        return iPayDelta;
    }

    public void setiPayDelta(String iPayDelta) {
        this.iPayDelta = iPayDelta;
    }

    public String getiNewCash() {
        return iNewCash;
    }

    public void setiNewCash(String iNewCash) {
        this.iNewCash = iNewCash;
    }
}
