package com.hm.digital.equipment.dahua.vo;

import java.util.List;

import com.google.gson.Gson;

public class AlarmMessagesVo {

  /**
   * totalCount : 1
   * nextPage : -1
   * results : [{"alarmCode":"123456","deviceName":"xxx","deviceCode":"xxxx","channelCode":"xxx","channelName":"xx","alarmStatus":1,"alarmDate":"20191011T103020Z","alarmGrade":1,"alarmType":"1","alarmImages":[{"imgUrl":"http://ip/1.jpg","imgSize":1024}],"handleUser":"abc","handleDate":"20191011T103020Z","handleMessage":"abc","handleStatus":1,"alarmAddress":"组织1-通道1"}]
   */

  private int totalCount;
  private int nextPage;
  private List<ResultsBean> results;
  public  AlarmMessagesVo objectFromData(String str) {

    return new Gson().fromJson(str, AlarmMessagesVo.class);
  }
  public int getTotalCount() { return totalCount;}
  public void setTotalCount(int totalCount) { this.totalCount = totalCount;}
  public int getNextPage() { return nextPage;}
  public void setNextPage(int nextPage) { this.nextPage = nextPage;}
  public List<ResultsBean> getResults() { return results;}
  public void setResults(List<ResultsBean> results) { this.results = results;}
  public  class ResultsBean {
    /**
     * alarmCode : 123456
     * deviceName : xxx
     * deviceCode : xxxx
     * channelCode : xxx
     * channelName : xx
     * alarmStatus : 1
     * alarmDate : 20191011T103020Z
     * alarmGrade : 1
     * alarmType : 1
     * alarmImages : [{"imgUrl":"http://ip/1.jpg","imgSize":1024}]
     * handleUser : abc
     * handleDate : 20191011T103020Z
     * handleMessage : abc
     * handleStatus : 1
     * alarmAddress : 组织1-通道1
     */

    private String alarmCode;
    private String deviceName;
    private String deviceCode;
    private String channelCode;
    private String channelName;
    private int alarmStatus;
    private String alarmDate;
    private int alarmGrade;
    private String alarmType;
    private String handleUser;
    private String handleDate;
    private String handleMessage;
    private int handleStatus;
    private String alarmAddress;
    private List<AlarmImagesBean> alarmImages;
    public  ResultsBean objectFromData(String str) {

      return new Gson().fromJson(str, ResultsBean.class);
    }
    public String getAlarmCode() { return alarmCode;}
    public void setAlarmCode(String alarmCode) { this.alarmCode = alarmCode;}
    public String getDeviceName() { return deviceName;}
    public void setDeviceName(String deviceName) { this.deviceName = deviceName;}
    public String getDeviceCode() { return deviceCode;}
    public void setDeviceCode(String deviceCode) { this.deviceCode = deviceCode;}
    public String getChannelCode() { return channelCode;}
    public void setChannelCode(String channelCode) { this.channelCode = channelCode;}
    public String getChannelName() { return channelName;}
    public void setChannelName(String channelName) { this.channelName = channelName;}
    public int getAlarmStatus() { return alarmStatus;}
    public void setAlarmStatus(int alarmStatus) { this.alarmStatus = alarmStatus;}
    public String getAlarmDate() { return alarmDate;}
    public void setAlarmDate(String alarmDate) { this.alarmDate = alarmDate;}
    public int getAlarmGrade() { return alarmGrade;}
    public void setAlarmGrade(int alarmGrade) { this.alarmGrade = alarmGrade;}
    public String getAlarmType() { return alarmType;}
    public void setAlarmType(String alarmType) { this.alarmType = alarmType;}
    public String getHandleUser() { return handleUser;}
    public void setHandleUser(String handleUser) { this.handleUser = handleUser;}
    public String getHandleDate() { return handleDate;}
    public void setHandleDate(String handleDate) { this.handleDate = handleDate;}
    public String getHandleMessage() { return handleMessage;}
    public void setHandleMessage(String handleMessage) { this.handleMessage = handleMessage;}
    public int getHandleStatus() { return handleStatus;}
    public void setHandleStatus(int handleStatus) { this.handleStatus = handleStatus;}
    public String getAlarmAddress() { return alarmAddress;}
    public void setAlarmAddress(String alarmAddress) { this.alarmAddress = alarmAddress;}
    public List<AlarmImagesBean> getAlarmImages() { return alarmImages;}
    public void setAlarmImages(List<AlarmImagesBean> alarmImages) { this.alarmImages = alarmImages;}
    public  class AlarmImagesBean {
      /**
       * imgUrl : http://ip/1.jpg
       * imgSize : 1024
       */

      private String imgUrl;
      private int imgSize;
      public  AlarmImagesBean objectFromData(String str) {

        return new Gson().fromJson(str, AlarmImagesBean.class);
      }
      public String getImgUrl() { return imgUrl;}
      public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl;}
      public int getImgSize() { return imgSize;}
      public void setImgSize(int imgSize) { this.imgSize = imgSize;}
    }
  }
}
