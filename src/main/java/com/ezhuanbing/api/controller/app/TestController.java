package com.ezhuanbing.api.controller.app;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.ezhuanbing.api.dao.mybatis.model.Group;

import net.sf.json.JSONObject;

@RestController
public class TestController {
  
  @RequestMapping(value = "/api/v1/test", method = RequestMethod.POST)
  public void test(MultipartRequest data){
    MultipartFile file1 = data.getFile("file1");
    System.out.println("file1's size is:" + file1.getSize());
    MultipartFile file2 = data.getFile("file2");
    System.out.println("file2's size is:" + file2.getSize());
    MultipartFile file3 = data.getFile("file3");
    System.out.println("file3's size is:" + file3.getSize());
    HttpServletRequest request = (HttpServletRequest)data;
    String result = request.getParameter("g");
    Group group = (Group)JSONObject.toBean(JSONObject.fromObject(result), Group.class);
    System.out.println(group.getId());
    System.out.println("test ok!");
  }
}