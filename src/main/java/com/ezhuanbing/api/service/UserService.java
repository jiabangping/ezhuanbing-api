package com.ezhuanbing.api.service;

import com.ezhuanbing.api.dao.mybatis.mapper.AdminMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.UserInfoMapper;
import com.ezhuanbing.api.exception.HttpStatus404Exception;
import com.ezhuanbing.api.exception.HttpStatus409Exception;
import com.ezhuanbing.api.model.Admin;
import com.ezhuanbing.api.model.AdminExample;
import com.ezhuanbing.api.model.UserInfo;
import com.ezhuanbing.api.model.UserInfoExample;
import com.ezhuanbing.api.tools.MD5;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Service
public class UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  @Autowired
  UserInfoMapper userMapper;

  @Autowired
  AdminMapper adminMapper;

  @Value("${ssoUrl:http://192.168.4.129:8080}")
  private String ssoUrl;

  @Value("${initialPassword:666666}")
  private String initialPassword;

  public List<UserInfo> getListByPage(Integer hospitalId, String name, int page, int rows) {
    PageHelper.startPage(page, rows, "id");

    UserInfoExample example = new UserInfoExample();
    example.createCriteria().andHospitalIdEqualTo(hospitalId).andUserNameLike("%" + name + "%");
    return userMapper.selectByExample(example);
  }

  public List<UserInfo> selectUserAndAdmin(Integer hospitalId) {

    return userMapper.selectUserAndAdmin(hospitalId);
  }

  public UserInfo findUserById(int id) {
    return userMapper.selectByPrimaryKey(id);
  }

  public void deleteUserById(int id) throws HttpStatus404Exception {
    int result = userMapper.updateUserStatus(id);
    if (result < 1) {
      throw new HttpStatus404Exception("操作失败", "update_user_status_error", "操作用户失败，可能是用户不存在", "");
    }
  }

  public UserInfo addUser(UserInfo user) throws HttpStatus409Exception {

//    String password = createPassword();
    user.setStatus("1");
    if (user.getDoctorId() == null || user.getDoctorId() == 0) {
      user.setPassword(MD5.encodeByMD5(initialPassword));

      try {
        final String uri = ssoUrl + "/auth/checkUserLoginName/" + user.getLogin();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(uri, String.class);
      } catch (Exception e) {
        LOGGER.error(e.getLocalizedMessage(), e);
        throw new HttpStatus409Exception("新建用户失败", "add_user_error", "新建用户失败，用户已存在", "");
      }
    } else {
      UserInfoExample example = new UserInfoExample();
      example.createCriteria().andLoginEqualTo(user.getLogin());
      int count = userMapper.selectCountByExample(example);
      if (count > 0) {
        throw new HttpStatus409Exception("新建用户失败", "add_user_error", "新建用户失败，用户已存在", "");
      }
    }

    int result = userMapper.insert(user);
    if (result > 0)
      return user;

    return null;
  }

  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
  void save(UserInfo user) {
    userMapper.insert(user);
  }

  public UserInfo get(int id) {
    UserInfo u = userMapper.selectByPrimaryKey(id);
    return u;
  }

  public UserInfo selectBySelective(UserInfo selective) {
    return userMapper.selectBySelective(selective);
  }

  public UserInfo updateUserName(Integer id, String name) throws HttpStatus404Exception {
    UserInfo userInfo = UserInfo.builder().id(id).userName(name).build();
    int result = userMapper.updateByPrimaryKeySelective(userInfo);
    if (result > 0) {
      return userMapper.selectByPrimaryKey(id);
    }
    throw new HttpStatus404Exception("", "", "", "");
  }

  public UserInfo resetPassword(Integer id) {

//    String password = createPassword();
    int result = userMapper
            .updateByPrimaryKeySelective(UserInfo.builder().id(id).password(MD5.encodeByMD5(initialPassword)).build());
    if (result > 0) {
      UserInfo userInfo = userMapper.selectByPrimaryKey(id);
      userInfo.setPassword(initialPassword);
      return userInfo;
    }
    return null;
  }

  public boolean updateUserNeteasetoken(String userName, String neteaseToken) {
    
    int result = userMapper.updateUserNeteaseToken(userName, neteaseToken);
    if (result > 0)
      return true;
    else {
      AdminExample example = new AdminExample();
      example.createCriteria().andLoginEqualTo(userName);
      Admin model = Admin.builder().neteasetoken(neteaseToken).build();
      int count = adminMapper.updateByExampleSelective(model, example);
      if(count > 0){
        return true;
      }else{
        return false;
      }
    }
  }

  private String createPassword() {
    Random random = new Random();
    return String.valueOf(random.nextInt(888888) + 100000);
  }

  public void updatePassword(String loginName, String oldPassword, String password)
          throws HttpStatus404Exception {
    UserInfo userInfo = userMapper.selectBySelective(UserInfo.builder().login(loginName).build());

    if (userInfo == null) {
      throw new HttpStatus404Exception("修改密码失败", "update_password_error",
              "找不到用户登陆名称 = " + loginName + " 的用户，可能是用户不存在", "");
    }

    if (!oldPassword.equalsIgnoreCase(userInfo.getPassword())) {
      throw new HttpStatus404Exception("修改密码失败", "update_password_error", "旧密码输入错误", "");
    }

    int result = userMapper.updateByPrimaryKeySelective(
            UserInfo.builder().id(userInfo.getId()).password(password).build());

    if (result < 1) {
      throw new HttpStatus404Exception("修改密码失败", "update_password_error",
              "找不到用户登陆名称 = " + loginName + " 的用户，可能是用户不存在", "");
    }
  }

  public void updateUserOnline(String admin, String loginName, Integer hospitalId, boolean online) throws HttpStatus404Exception {

    if (isNotAdmin(admin)) {
      UserInfo userInfo = UserInfo.builder().online(online ? "1" : "0").build();

      UserInfoExample example = new UserInfoExample();
      example.createCriteria().andLoginEqualTo(loginName);

      int count = userMapper.updateByExampleSelective(userInfo, example);
      if (count < 1) {
        throw new HttpStatus404Exception("修改在线状态", "update_online_error",
                "修改在线状态失败，没有更新数据", "");
      }
    } else {
      AdminExample example = new AdminExample();
      example.createCriteria().andLoginEqualTo(loginName).andHospitalIdEqualTo(hospitalId);

      Admin model = Admin.builder().login(loginName).hospitalId(hospitalId).online(online ? "1" : "0").build();

      int count = adminMapper.updateByExampleSelective(model, example);
      if (count == 0) {
        adminMapper.insert(model);
      }

    }
  }

  private boolean isNotAdmin(String admin) {
    return "0".equals(admin);
  }

  public UserInfo selectUserOrAdminByLoginName(String loginName) {

    UserInfoExample userInfoExample = new UserInfoExample();
    userInfoExample.createCriteria().andLoginEqualTo(loginName);
    List<UserInfo> userInfoList = userMapper.selectByExample(userInfoExample);

    if (userInfoList != null && userInfoList.size() > 0) {
      return userInfoList.get(0);
    }

    AdminExample adminExample = new AdminExample();
    adminExample.createCriteria().andLoginEqualTo(loginName);
    List<Admin> adminList = adminMapper.selectByExample(adminExample);

    if (adminList != null && adminList.size() > 0) {
      Admin admin = adminList.get(0);
      return UserInfo.builder().login(admin.getLogin()).hospitalId(admin.getHospitalId())
              .online(admin.getOnline()).neteaseToken(admin.getNeteasetoken()).build();
    }

    return null;
  }
}
