package com.ezhuanbing.api.service;

import com.ezhuanbing.api.dao.mybatis.mapper.FunctionMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.RoleFunctionsMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.RoleInfoMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.UserFunctionsMapper;
import com.ezhuanbing.api.dao.mybatis.mapper.UserRolesMapper;
import com.ezhuanbing.api.exception.HttpStatus403Exception;
import com.ezhuanbing.api.exception.HttpStatus404Exception;
import com.ezhuanbing.api.exception.HttpStatus409Exception;
import com.ezhuanbing.api.main.FunctionDefinition;
import com.ezhuanbing.api.model.Function;
import com.ezhuanbing.api.model.RoleFunctions;
import com.ezhuanbing.api.model.RoleInfo;
import com.ezhuanbing.api.model.RoleInfoExample;
import com.ezhuanbing.api.model.UserFunctions;
import com.ezhuanbing.api.model.UserRoles;
import com.ezhuanbing.api.model.UserRolesExample;
import com.ezhuanbing.api.vo.RoleFunction;
import com.github.pagehelper.PageHelper;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangwl on 2016/7/16.
 */
@Service
public class RoleService {

  @Autowired
  private RoleInfoMapper roleInfoMapper;

  @Autowired
  private RoleFunctionsMapper roleFunctionsMapper;

  @Autowired
  private FunctionMapper functionMapper;

  @Autowired
  private UserRolesMapper userRolesMapper;

  @Autowired
  private UserFunctionsMapper userFunctionsMapper;

  public List<RoleFunction> getListByName(int hospitalId, String name, Integer pageIndex, Integer pageSize) throws HttpStatus403Exception {

    List<RoleFunction> roleFunctionList = new ArrayList<RoleFunction>();

    PageHelper.startPage(pageIndex, pageSize, "roleId");

    RoleInfoExample example = new RoleInfoExample();
    example.createCriteria().andHospitalIdEqualTo(hospitalId).andRoleNameLike("%" + name + "%");

    List<RoleInfo> roleInfoList = roleInfoMapper.selectByExample(example);
    for (RoleInfo roleInfo : roleInfoList) {
      List<Function> functionList = functionMapper.selectByRoleId(roleInfo.getRoleId());
      RoleFunction roleFunction = RoleFunction.builder()
              .roleId(roleInfo.getRoleId()).roleName(roleInfo.getRoleName()).functions(functionList)
              .build();
      roleFunctionList.add(roleFunction);
    }

    return roleFunctionList;
  }

  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
  public RoleFunction insertRole(int hospitalId, RoleFunction roleFunction) throws HttpStatus403Exception {


    RoleInfo roleInfo = RoleInfo.builder().roleName(roleFunction.getRoleName()).hospitalId(hospitalId).build();

    int result = roleInfoMapper.insert(roleInfo);
    if (result > 0) {

      List<Function> functions = roleFunction.getFunctions();

      insertRoleFunction(roleInfo.getRoleId(), functions);

    }
    return getRoleFunction(hospitalId, roleInfo.getRoleId());
  }

  private void insertRoleFunction(Integer roleId, List<Function> functions) {
    List<Integer> tabFunction = new ArrayList<>();
    Map<Integer, Integer[]> tabFunctionMap = new HashMap<>(FunctionDefinition.TAB_FUNCTION_MAP);

    for (Function function : functions) {
      RoleFunctions roleFunctions = RoleFunctions.builder()
              .roleId(roleId).functionId(function.getId()).build();
      roleFunctionsMapper.insert(roleFunctions);

      for (Map.Entry<Integer, Integer[]> integerEntry : tabFunctionMap.entrySet()) {
        if (ArrayUtils.contains(integerEntry.getValue(), function.getId())) {
          tabFunction.add(integerEntry.getKey());
          tabFunctionMap.remove(integerEntry.getKey());
          break;
        }
      }
    }

    for (Integer integer : tabFunction) {
      RoleFunctions roleFunctions = RoleFunctions.builder()
              .roleId(roleId).functionId(integer).build();
      roleFunctionsMapper.insert(roleFunctions);
    }
  }

  public RoleFunction getRoleFunction(int hospitalId, Integer id) throws HttpStatus403Exception {

    int count = verifyHospitalRole(hospitalId, id);
    if (count < 1) {
      throw new HttpStatus403Exception("服务器拒绝请求", "update_role_error",
              "更新角色失败，该用户的权限不允许此角色", "");
    }

    RoleInfo roleInfo = roleInfoMapper.selectByPrimaryKey(id);

    List<Function> functionList = functionMapper.selectByRoleId(roleInfo.getRoleId());

    return RoleFunction.builder()
            .roleId(roleInfo.getRoleId()).roleName(roleInfo.getRoleName()).functions(functionList)
            .build();
  }

  private int verifyHospitalRole(int hospitalId, Integer id) {
    RoleInfoExample example = new RoleInfoExample();
    example.createCriteria().andHospitalIdEqualTo(hospitalId).andRoleIdEqualTo(id);

    return roleInfoMapper.selectCountByExample(example);
  }

  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
  public void updateRole(int hospitalId, RoleFunction roleFunction) throws HttpStatus403Exception, HttpStatus404Exception {

    int count = verifyHospitalRole(hospitalId, roleFunction.getRoleId());
    if (count < 1) {
      throw new HttpStatus403Exception("服务器拒绝请求", "update_role_error",
              "更新角色失败，该用户的权限不允许此角色", "");
    }

    RoleInfo roleInfo = RoleInfo.builder().roleId(roleFunction.getRoleId()).roleName(roleFunction.getRoleName()).build();
    count = roleInfoMapper.updateByPrimaryKeySelective(roleInfo);
    if (count < 1) {
      throw new HttpStatus404Exception("服务器找不到对应资源", "update_role_error",
              "更新角色失败，服务器找不到对应资源。 角色 id = " + roleFunction.getRoleId() + " ，可能是没有该角色", "");
    }

    RoleFunctions delete = RoleFunctions.builder()
            .roleId(roleInfo.getRoleId()).build();
    roleFunctionsMapper.delete(delete);

    List<Function> functions = roleFunction.getFunctions();
    insertRoleFunction(roleInfo.getRoleId(), functions);
  }

  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
  public void deleteRole(int hospitalId, Integer id) throws HttpStatus403Exception, HttpStatus404Exception, HttpStatus409Exception {
    int count = verifyHospitalRole(hospitalId, id);
    if (count < 1) {
      throw new HttpStatus403Exception("服务器拒绝请求", "delete_role_error",
              "删除角色失败，该用户的权限不允许删除此角色", "");
    }

    UserRolesExample example = new UserRolesExample();
    example.createCriteria().andRoleIdEqualTo(id);
    count = userRolesMapper.selectCountByExample(example);
    if (count > 0) {
      throw new HttpStatus409Exception("资源冲突，或者资源被锁定", "delete_role_error",
              "该角色已有用户在使用，不允许删除", "");
    }

    count = roleInfoMapper.deleteByPrimaryKey(id);
    if (count < 1) {
      throw new HttpStatus404Exception("服务器找不到对应资源", "delete_role_error",
              "删除角色失败，服务器找不到对应资源。 角色 id = " + id + " ，可能是没有该角色", "");
    }

    RoleFunctions delete = RoleFunctions.builder()
            .roleId(id).build();
    roleFunctionsMapper.delete(delete);

  }

  public List<RoleFunction> getListByUserId(Integer userId) throws HttpStatus403Exception {

    List<RoleFunction> roleFunctionList = new ArrayList<>();

    List<RoleInfo> roleInfoList = roleInfoMapper.selectByUserId(userId);
    for (RoleInfo roleInfo : roleInfoList) {
      List<Function> functionList = functionMapper.selectByRoleId(roleInfo.getRoleId());
      RoleFunction roleFunction = RoleFunction.builder()
              .roleId(roleInfo.getRoleId()).roleName(roleInfo.getRoleName()).functions(functionList)
              .build();
      roleFunctionList.add(roleFunction);
    }

    return roleFunctionList;
  }

  public List<Function> getFunctionListByUserId(Integer id) {
    return functionMapper.selectByUserId(id);
  }

  @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
  public void updateUserRole(Integer id, List<RoleFunction> roleFunctionList, List<Function> functionList) {
    userRolesMapper.delete(UserRoles.builder().userId(id).build());

    List<UserRoles> userRolesList = new ArrayList<>(roleFunctionList.size());
    for (RoleFunction roleFunction : roleFunctionList) {
      UserRoles userRolesBuilder = UserRoles.builder().userId(id).roleId(roleFunction.getRoleId()).build();
      userRolesList.add(userRolesBuilder);
    }
    if (userRolesList.size() > 0) {
      userRolesMapper.insertList(userRolesList);
    }

    Map<Integer, Integer[]> tabFunctionMap = new HashMap<>(FunctionDefinition.TAB_FUNCTION_MAP);

    userFunctionsMapper.delete(UserFunctions.builder().userId(id).build());
    List<UserFunctions> userFunctionsList = new ArrayList<>();
    for (Function function : functionList) {
      UserFunctions userFunctions = UserFunctions.builder().userId(id).functionId(function.getId()).build();
      userFunctionsList.add(userFunctions);

      for (Map.Entry<Integer, Integer[]> integerEntry : tabFunctionMap.entrySet()) {
        if (ArrayUtils.contains(integerEntry.getValue(), function.getId())) {
          userFunctionsList.add(UserFunctions.builder().userId(id).functionId(integerEntry.getKey()).build());
          tabFunctionMap.remove(integerEntry.getKey());
          break;
        }
      }
    }
    if (userFunctionsList.size() > 0) {
      userFunctionsMapper.insertList(userFunctionsList);
    }

  }

  public List<Function> getFunctionListByRoleInfoList(int hospitalId, List<Integer> roleIds) throws HttpStatus403Exception {

    if (roleIds == null || roleIds.size() == 0) {
      return Collections.emptyList();
    }

    RoleInfoExample example = new RoleInfoExample();
    example.createCriteria().andHospitalIdEqualTo(hospitalId).andRoleIdIn(roleIds);

    int count = roleInfoMapper.selectCountByExample(example);
    if (count != roleIds.size()) {
      throw new HttpStatus403Exception("服务器拒绝请求", "get_function_error",
              "获取方法集合失败，该用户不具有访问下列角色的权限 ：" + Arrays.toString(roleIds.toArray()), "");
    }

    return functionMapper.selectAllByRoleIds(roleIds);
  }
}
