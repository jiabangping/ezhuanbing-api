package com.ezhuanbing.api.controller.app;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebUrlController {

  String[] systemInfo = new String[] {"www.163.com", "www.sohu.com"};

  @RequestMapping(value = "/api/v1/staticurl", method = RequestMethod.GET)
  //@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
  public Map<String, Object> getStaticWebUrl() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("help", "www.baidu.com");
    map.put("about", "www.sina.con.cn");
    map.put("systemNotice", systemInfo);
    map.put("register", "www.taobao.com");
    return map;
  }
}
