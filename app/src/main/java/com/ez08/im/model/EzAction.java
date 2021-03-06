package com.ez08.im.model;

/**
 * Created by Administrator on 2016/4/13.
 */
public class EzAction {

    /**
     * ezActionType : showPage
     * ezTargetXib : EZWebViewController
     * ezTargetClass : EZWebViewController
     * ezTargetData : {"ezUri":"http://news.163.com/16/0224/15/BGJNKKVU00011229.html"}
     */

    private String ezActionType;
    private String ezTargetXib;
    private String ezTargetClass;
    /**
     * ezUri : http://news.163.com/16/0224/15/BGJNKKVU00011229.html
     */

    private EzTargetDataBean ezTargetData;

    public String getEzActionType() {
        return ezActionType;
    }

    public void setEzActionType(String ezActionType) {
        this.ezActionType = ezActionType;
    }

    public String getEzTargetXib() {
        return ezTargetXib;
    }

    public void setEzTargetXib(String ezTargetXib) {
        this.ezTargetXib = ezTargetXib;
    }

    public String getEzTargetClass() {
        return ezTargetClass;
    }

    public void setEzTargetClass(String ezTargetClass) {
        this.ezTargetClass = ezTargetClass;
    }

    public EzTargetDataBean getEzTargetData() {
        return ezTargetData;
    }

    public void setEzTargetData(EzTargetDataBean ezTargetData) {
        this.ezTargetData = ezTargetData;
    }

    public static class EzTargetDataBean {
        private String ezDataUrl;

        public String getEzDataUrl() {
            return ezDataUrl;
        }

        public void setEzDataUrl(String ezDataUrl) {
            this.ezDataUrl = ezDataUrl;
        }
    }
}
