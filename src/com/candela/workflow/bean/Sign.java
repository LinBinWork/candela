package com.candela.workflow.bean;

/**
 * @Author linbin
 * @Create 2019-11-15-15:52
 */
public class Sign {
    private String id;
    private String userId;
    private String userType;
    private String signDateBefore;
    private String signDateAfter;
    private String signTime;
    private String signFrom;
    private String signBefore;
    private String SignAfter;
    private String status;

    public Sign() {
    }

    public Sign(String id, String userId, String userType, String signDateBefore, String signTime, String signFrom) {
        this.id = id;
        this.userId = userId;
        this.userType = userType;
        this.signDateBefore = signDateBefore;
        this.signTime = signTime;
        this.signFrom = signFrom;
    }

    public String getSignDateAfter() {
        return signDateAfter;
    }

    public void setSignDateAfter(String signDateAfter) {
        this.signDateAfter = signDateAfter;
    }

    public String getSignBefore() {
        return signBefore;
    }

    public void setSignBefore(String signBefore) {
        this.signBefore = signBefore;
    }

    public String getSignAfter() {
        return SignAfter;
    }

    public void setSignAfter(String signAfter) {
        SignAfter = signAfter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSignDateBefore() {
        return signDateBefore;
    }

    public void setSignDateBefore(String signDateBefore) {
        this.signDateBefore = signDateBefore;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getSignFrom() {
        return signFrom;
    }

    public void setSignFrom(String signFrom) {
        this.signFrom = signFrom;
    }

    @Override
    public String toString() {
        return "Sign{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userType='" + userType + '\'' +
                ", signDate='" + signDateBefore + '\'' +
                ", signTime='" + signTime + '\'' +
                ", signFrom='" + signFrom + '\'' +
                ", signBefore='" + signBefore + '\'' +
                ", SignAfter='" + SignAfter + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
