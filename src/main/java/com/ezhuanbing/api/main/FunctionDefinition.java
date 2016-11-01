package com.ezhuanbing.api.main;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangwl on 2016/8/1.
 */
public final class FunctionDefinition {

  public static final int ADMINISTRATION = 1000;
  public static final String ADMINISTRATION_TITLE = "管理";

  public static final int GET_USER = 1101;
  public static final String GET_USER_TITLE = "获取用户信息";

  public static final int DELETE_USER = 1102;
  public static final String DELETE_USER_TITLE = "禁用/启用用户";

  public static final int ADD_USER = 1103;
  public static final String ADD_USER_TITLE = "添加用户";

  public static final int UPDATE_USER = 1104;
  public static final String UPDATE_USER_TITLE = "修改用户";

  public static final int GET_USER_ROLE = 1105;
  public static final String GET_USER_ROLE_TITLE = "获取用户权限";

  public static final int UPDATE_USER_ROLE = 1106;
  public static final String UPDATE_USER_ROLE_TITLE = "修改用户权限";

  public static final int RESET_PASSWORD = 1107;
  public static final String RESET_PASSWORD_TITLE = "重置用户密码";


  public static final int ADD_ROLE = 1201;
  public static final String ADD_ROLE_TITLE = "添加角色";

  public static final int UPDATE_ROLE = 1202;
  public static final String UPDATE_ROLE_TITLE = "修改角色";

  public static final int DELETE_ROLE = 1203;
  public static final String DELETE_ROLE_TITLE = "删除角色";

  public static final int GET_ROLE = 1204;
  public static final String GET_ROLE_TITLE = "获取角色信息";


  public static final int UPDAET_PARAM = 1301;
  public static final String UPDAET_PARAM_TITLE = "参数设定";


  public static final int REPORT = 2000;
  public static final String REPORT_TITLE = "报表";

  public static final int GET_REPORT = 2101;
  public static final String GET_REPORT_TITLE = "查看报表";


  public static final int TRANSFER_IN = 3000;
  public static final String TRANSFER_IN_TITLE = "转入";

  public static final int GET_IN_ORDER = 3101;
  public static final String GET_IN_ORDER_TITLE = "获取转入订单信息";

  public static final int CONFIRM_IN_ORDER = 3102;
  public static final String CONFIRM_IN_ORDER_TITLE = "转入订单确认";

  public static final int REFUSE_IN_ORDER = 3103;
  public static final String REFUSE_IN_ORDER_TITLE = "转入订单拒绝";

  public static final int CONFIRM_PATIENT_IN = 3104;
  public static final String CONFIRM_PATIENT_IN_TITLE = "患者入院确认";


  public static final int TRANSFER_OUT = 4000;
  public static final String TRANSFER_OUT_TITLE = "转出";

  public static final int GET_OUT_ORDER = 4101;
  public static final String GET_OUT_ORDER_TITLE = "获取转出订单信息";

  public static final int CREATE_OUT_ORDER = 4102;
  public static final String CREATE_OUT_ORDER_TITLE = "新增转出订单";

  public static final int REVOKE_OUT_ORDER = 4103;
  public static final String REVOKE_OUT_ORDER_TITLE = "撤销转出订单";

  public static final int PRINT_ORDER = 4104;
  public static final String PRINT_ORDER_TITLE = "打印转诊凭据";

  public static final int PRINT_CONSULTATION = 5000;
  public static final String PRINT_CONSULTATION_TITLE = "打印会诊";

  public static final int PRINT_CONSULTATION_ADVICE = 5101;
  public static final String PRINT_CONSULTATION_ADVICE_TITLE = "打印会诊意见";

  public static final Map<Integer, Integer[]> TAB_FUNCTION_MAP = initMap();

  private static Map<Integer, Integer[]> initMap() {
    Map<Integer, Integer[]> tabFunctionMap = new HashMap<>(4);
    tabFunctionMap.put(FunctionDefinition.ADMINISTRATION,
            new Integer[]{FunctionDefinition.GET_USER,
                    FunctionDefinition.DELETE_USER,
                    FunctionDefinition.ADD_USER,
                    FunctionDefinition.UPDATE_USER,
                    FunctionDefinition.GET_USER_ROLE,
                    FunctionDefinition.UPDATE_USER_ROLE,
                    FunctionDefinition.RESET_PASSWORD,
                    FunctionDefinition.ADD_ROLE,
                    FunctionDefinition.UPDATE_ROLE,
                    FunctionDefinition.DELETE_ROLE,
                    FunctionDefinition.GET_ROLE,
                    FunctionDefinition.UPDAET_PARAM});
    tabFunctionMap.put(FunctionDefinition.REPORT,
            new Integer[]{FunctionDefinition.GET_REPORT});
    tabFunctionMap.put(FunctionDefinition.TRANSFER_IN,
            new Integer[]{FunctionDefinition.GET_IN_ORDER,
                    FunctionDefinition.CONFIRM_IN_ORDER,
                    FunctionDefinition.REFUSE_IN_ORDER,
                    FunctionDefinition.CONFIRM_PATIENT_IN});
    tabFunctionMap.put(FunctionDefinition.TRANSFER_OUT,
            new Integer[]{FunctionDefinition.GET_OUT_ORDER,
                    FunctionDefinition.CREATE_OUT_ORDER,
                    FunctionDefinition.REVOKE_OUT_ORDER,
                    FunctionDefinition.PRINT_ORDER});
    tabFunctionMap.put(FunctionDefinition.PRINT_CONSULTATION,
            new Integer[]{FunctionDefinition.PRINT_CONSULTATION_ADVICE});
    return tabFunctionMap;
  }

}
