package com.candela.workflow.bean;

public class ScheduleSign {
    /**
     * 用户id
     */
    private String resourceid;

    /**
     * 考勤日期
     */
    private String kqdate;

    /**
     *上班日期
     */
    private String signindate;

    /**
     * 下班日期
     */
    private String signoutdate;

    /**
     *上班时间
     */
    private String signintime;

    /**
     *下班时间
     */
    private String signouttime;

    /**
     *上班id
     */
    private String signinid;

    /**
     *下班id
     */
    private String signoutid;

    /**
     *状态
     */
    private int status;

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    public String getKqdate() {
        return kqdate;
    }

    public void setKqdate(String kqdate) {
        this.kqdate = kqdate;
    }

    public String getSignindate() {
        return signindate;
    }

    public void setSignindate(String signindate) {
        this.signindate = signindate;
    }

    public String getSignoutdate() {
        return signoutdate;
    }

    public void setSignoutdate(String signoutdate) {
        this.signoutdate = signoutdate;
    }

    public String getSignintime() {
        return signintime;
    }

    public void setSignintime(String signintime) {
        this.signintime = signintime;
    }

    public String getSignouttime() {
        return signouttime;
    }

    public void setSignouttime(String signouttime) {
        this.signouttime = signouttime;
    }

    public String getSigninid() {
        return signinid;
    }

    public void setSigninid(String signinid) {
        this.signinid = signinid;
    }

    public String getSignoutid() {
        return signoutid;
    }

    public void setSignoutid(String signoutid) {
        this.signoutid = signoutid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
