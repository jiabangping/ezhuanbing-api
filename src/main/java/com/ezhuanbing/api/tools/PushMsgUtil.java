package com.ezhuanbing.api.tools;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezhuanbing.api.conf.PushConfig;
import com.ezhuanbing.api.model.PushModel;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.Style;
import com.tencent.xinge.XingeApp;

@Component
public class PushMsgUtil {

  @Autowired
  PushConfig pushConfig;
  //XingeApp.IOSENV_PROD：正式  XingeApp.IOSENV_DEV:测试 
  //private final int PUSH_TYPE = XingeApp.IOSENV_PROD;
  /**
   * 单个设备推送透传消息
   * 
   * @param push
   * @return
   */
  public JSONObject PushSingleDeviceMessage(PushModel push) {

    JSONObject ret;
    if (push == null || push.getMobileImei() == null) {
      ret = new JSONObject("{'ret_code':-1,'err_msg':'无法找到推送对象!'}");
    } else {
      switch (push.getPhoneType()) {
        case '0':
          ret = AndroidPushMessage(push);
          break;
        case '1':
          ret = IOSPushMessage(push);
          break;
        default:
          ret = new JSONObject("{'ret_code':-1,'err_msg':'未知的推送设备!'}");
          break;
      }
    }
    
    return ret;
  }
  
  /**
   * Android单个设备推送透传消息
   * 
   * @param push
   * @return
   */
  private JSONObject AndroidPushMessage(PushModel push) {
      Message message = new Message();
      message.setType(Message.TYPE_MESSAGE);
      message.setTitle(push.getTitle());
      message.setContent(push.getContent());
      message.setExpireTime(0);
      message.setCustom(push.getCustom());
      message.setAction(push.getAction());
      // 设置是否响铃
      Style style = new Style(1);
      style = new Style(3, 1, 0, 1, 0);
      message.setStyle(style);
      XingeApp xinge = new XingeApp(pushConfig.getAndroidAccessId(), pushConfig.getAndroidSecretKey());
      JSONObject ret = xinge.pushSingleDevice(push.getMobileImei(), message);
      return ret;
  }
  
  /**
   * IOS单个设备推送透传消息
   * 
   * @param push
   * @return
   */
  private JSONObject IOSPushMessage(PushModel push) {
      MessageIOS message = new MessageIOS();
      message.setAlert(push.getContent());
      message.setBadge(push.getIosBadge());
      message.setSound("beep.wav");
      message.setCustom(push.getCustom());
      XingeApp xinge = new XingeApp(pushConfig.getIosAccessId(), pushConfig.getIosSecretKey());
      JSONObject ret = xinge.pushSingleDevice(push.getMobileImei(), message, pushConfig.getPushType());
      return ret;
  }
  
  /**
   * 单个设备推送通知
   * 
   * @param push
   * @throws Exception
   */
  public JSONObject PushSingleDeviceNotification(PushModel push) {
      
    JSONObject ret;
    if (push == null || push.getMobileImei() == null) {
      ret = new JSONObject("{'ret_code':-1,'err_msg':'无法找到推送对象!'}");
    } else {
      switch (push.getPhoneType()) {
        case '0':
          ret = AndroidPushNotification(push);
          break;
        case '1':
          ret = IOSPushNotification(push);
          break;
        default:
          ret = new JSONObject("{'ret_code':-1,'err_msg':'未知的推送设备!'}");
          break;
      }

    }
    return ret;
  }
  
  /**
   * Android单个设备推送通知
   * 
   * @param push
   * @throws Exception
   */
  private JSONObject AndroidPushNotification(PushModel push) {
      Message message = new Message();
      message.setType(Message.TYPE_NOTIFICATION);
      message.setTitle(push.getTitle());
      message.setExpireTime(0);
      message.setContent(push.getContent());
      message.setCustom(push.getCustom());
      message.setAction(push.getAction());
      // 设置是否响铃
      Style style = new Style(1);
      style = new Style(3, 1, 0, 1, 0);
      message.setStyle(style);
      XingeApp xinge = new XingeApp(pushConfig.getAndroidAccessId(), pushConfig.getAndroidSecretKey());
      JSONObject ret = xinge.pushSingleDevice(push.getMobileImei(), message);
      return ret;
  }

  /**
   * IOS单个设备推送通知
   * 
   * @param push
   * @throws Exception
   */
  private JSONObject IOSPushNotification(PushModel push) {
      MessageIOS message = new MessageIOS();
      message.setAlert(push.getContent());
      message.setBadge(push.getIosBadge());
      message.setSound("beep.wav");
      message.setCustom(push.getCustom());
      XingeApp xinge = new XingeApp(pushConfig.getIosAccessId(), pushConfig.getIosSecretKey());
      JSONObject ret = xinge.pushSingleDevice(push.getMobileImei(), message, pushConfig.getPushType());
      return ret;
  }
  
  
  
  /**
   * 所有设备推送
   * @Title: pushAllDeviceNotification 
   * @Description:
   * @param push  
   * @return void  
   * @throws
   */
  public void pushAllDeviceNotification(PushModel push) {
      // 所有安卓设备
      androidPushALLNotification(push);
      // 所有IOS设备
      iosPushALLNotification(push);
  }
  
  /**
   * 安卓所有设备推送
   * @Title: androidPushALLNotification 
   * @Description:
   * @param push
   * @return  
   * @return JSONObject  
   * @throws
   */
  private JSONObject androidPushALLNotification(PushModel push) {
      Message message = new Message();
      message.setType(Message.TYPE_NOTIFICATION);
      message.setTitle(push.getTitle());
      message.setExpireTime(0);
      message.setContent(push.getContent());
      message.setCustom(push.getCustom());
      message.setAction(push.getAction());
      // 设置是否响铃
      Style style = new Style(1);
      style = new Style(3, 1, 0, 1, 0);
      message.setStyle(style);
      XingeApp xinge = new XingeApp(pushConfig.getAndroidAccessId(),pushConfig.getAndroidSecretKey());
      return xinge.pushAllDevice(0, message);
  }

  /**
   * IOS 所有设备推送通知
   * @Title: iosPushALLNotification 
   * @Description:
   * @param push
   * @return  
   * @return JSONObject  
   * @throws
   */
  private JSONObject iosPushALLNotification(PushModel push) {
      MessageIOS message = new MessageIOS();
      message.setAlert(push.getContent());
      message.setBadge(1);
      message.setSound("beep.wav");
      message.setCustom(push.getCustom());
      XingeApp xinge = new XingeApp(pushConfig.getIosAccessId(), pushConfig.getIosSecretKey());
      return xinge.pushAllDevice(0, message, pushConfig.getPushType());
  }
}
