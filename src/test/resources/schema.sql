/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/7/13 13:40:50                           */
/*==============================================================*/


drop table if exists TR_roleFunctions;

drop table if exists TR_roleInfo;

drop table if exists TR_userFunctions;

drop table if exists TR_userInfo;

drop table if exists TR_accessRecord;

drop table if exists TR_distract;

drop table if exists TR_gridColumns;

drop table if exists TR_hospitalRule;

drop table if exists TR_operationRecord;

drop table if exists TR_transferDeviant;

drop table if exists TR_transferInfo;

drop table if exists TR_transferRule;

drop table if exists TR_userColumn;

/*==============================================================*/
/* Table: TR_roleFunctions                                      */
/*==============================================================*/
create table TR_roleFunctions
(
   roleId               int not null COMMENT '角色标识',
   functionId           int not null COMMENT '功能标识',
   primary key (roleID, functionId)
);

/* alter table TR_roleFunctions comment '角色功能关联表'; */


/*==============================================================*/
/* Table: TR_roleInfo                                           */
/*==============================================================*/
create table TR_roleInfo
(
   roleId               int not null auto_increment COMMENT '角色标识',
   roleName             varchar(100) COMMENT '角色名称',
   status               char(1) COMMENT '有效状态',
   primary key (roleId)
);

/* alter table TR_roleInfo comment '角色信息表'; */

/*==============================================================*/
/* Table: TR_userFunctions                                      */
/*==============================================================*/
create table TR_userFunctions
(
   userId               int not null COMMENT '用户ID',
   functionId           int not null COMMENT '功能ID',
   primary key (userId, functionId)
);

/* alter table TR_userFunctions comment '用户功能关联表'; */

/*==============================================================*/
/* Table: TR_userInfo                                           */
/*==============================================================*/
create table TR_userInfo
(
   id                   int not null auto_increment COMMENT '用户ID',
   userName             varchar(20) COMMENT '用户名',
   password             varchar(50) COMMENT '用户密码',
   hospitalID           int COMMENT '所在医院',
   phoneNum             varchar(20) COMMENT '电话号码',
   departmentID         int COMMENT '所在科室',
   idCard               varchar(50) COMMENT '身份证',
   age                  int(11) COMMENT '年龄',
   sex                  char(1) COMMENT '性别',
   position             varchar(50) COMMENT '职称',
   status               char(1) COMMENT '状态(0:无效，1：有效)',
   primary key (id)
);

/* alter table TR_userInfo comment '用户信息表'; */

/*==============================================================*/
/* Table: TR_accessRecord                                       */
/*==============================================================*/
create table TR_accessRecord
(
   id                   int not null auto_increment COMMENT '记录标识',
   userId               int COMMENT '用户ID',
   accessUrl            varchar(40) COMMENT '访问URl',
   accessCont           varchar(255) COMMENT '操作内容',
   accessDate           timestamp COMMENT '操作时间',
   primary key (id)
);

/* alter table TR_accessRecord comment '用户操作日志表'; */

/*==============================================================*/
/* Table: TR_distract                                           */
/*==============================================================*/
create table TR_distract
(
   id                   int not null auto_increment COMMENT '转移标识',
   trId                 int COMMENT '转诊标识',
   originalHospital     int COMMENT '原医院',
   targetHospital       int COMMENT '目标医院',
   originalDepartment   int COMMENT '原科室',
   targetDepartment     int COMMENT '目标科室',
   distractRefuse       varchar(255) COMMENT '转移说明',
   distractTime         timestamp COMMENT '转移时间',
   primary key (id)
);

/* alter table TR_distract comment '转移信息'; */

/*==============================================================*/
/* Table: TR_gridColumns                                        */
/*==============================================================*/
create table TR_gridColumns
(
   id                   int not null auto_increment COMMENT '标识',
   tableName            varchar(20) not null COMMENT '表名称',
   columnEnName         varchar(40) not null COMMENT '列英文名称',
   columnCnName         varchar(40) not null COMMENT '列中文名称',
   isShow               char COMMENT '是否显示(0:是，1:否)',
   primary key (id)
);

/* alter table TR_gridColumns comment 'grid列管理表'; */

/*==============================================================*/
/* Table: TR_hospitalRule                                       */
/*==============================================================*/
create table TR_hospitalRule
(
   id                   int not null auto_increment COMMENT '规则标识',
   hospitalId           int COMMENT '医院标识',
   responseDay          int COMMENT '回复天数',
   primary key (id)
);

/* alter table TR_hospitalRule comment '医院规则'; */

/*==============================================================*/
/* Table: TR_operationRecord                                    */
/*==============================================================*/
create table TR_operationRecord
(
   id                   int not null auto_increment COMMENT '操作标识',
   trId                 int COMMENT '转诊标识',
   operationId          int COMMENT '操作人标识',
   operationStatus      char(2) COMMENT '操作状态（0：增加，1：修改，2:删除，3:拒绝，4:转移)',
   operationTime        timestamp COMMENT '操作时间',
   primary key (id)
);

/* alter table TR_operationRecord comment '操作记录'; */

/*==============================================================*/
/* Table: TR_transferDeviant                                    */
/*==============================================================*/
create table TR_transferDeviant
(
   id                   int  not null auto_increment COMMENT '转诊异常标识',
   trId                 int COMMENT '转诊Id',
   deviantUserId        int COMMENT '异常处理人',
   deviantContent       varchar(255) COMMENT '异常处理内容',
   deviantStatus        char COMMENT '异常状态(0：未处理，1：已处理)',
   deviantTime          timestamp COMMENT '异常处理时间',
   deviantType          char COMMENT '异常类型(0:超时)',
   deviantCreateTime    timestamp COMMENT '异常生成时间',
   primary key (id)
);

/* alter table TR_transferDeviant comment '转诊异常信息表'; */

/*==============================================================*/
/* Table: TR_transferInfo                                       */
/*==============================================================*/
create table TR_transferInfo
(
   trId                 int not null auto_increment COMMENT '转诊标识',
   patientId            int COMMENT '患者id',
   trHospital           int COMMENT '目标医院',
   trDepartment         int COMMENT '目标科室',
   trApplyTime          timestamp COMMENT '转诊申请时间',
   trApplyExplain       varchar(255) COMMENT '转诊申请说明',
   trStatus             char(2) COMMENT '转诊状态（0：待确认，1：已拒绝，2：已撤销，3：已确认，4：已转入，9：已作废）',
   switchToTime         timestamp COMMENT '接诊时间',
   switcgToBed          varchar(20) COMMENT '住院床位',
   switcgToRegister     int(10) COMMENT '挂号',
   switcgToRefuse       varchar(255) COMMENT '转入拒绝说明',
   trDirection          char COMMENT '转诊方向（0：向上，1：向下)',
   consultationOrderId  varchar(50) COMMENT '下向上：会诊Id',
   ptrid                int COMMENT '上向下：已接收的转诊Id',
   isEffective      		char COMMENT '是否生效',
   latestReplyTime      timestamp COMMENT '最晚回复时间',
   primary key (trId)
);

/* alter table TR_transferInfo comment '转诊信息'; */

/*==============================================================*/
/* Table: TR_transferRule                                       */
/*==============================================================*/
create table TR_transferRule
(
   id                   int not null auto_increment,
   primary key (id)
);

/* alter table TR_transferRule comment '转诊规则'; */

/*==============================================================*/
/* Table: TR_userColumn                                         */
/*==============================================================*/
create table TR_userColumn
(
   id                   int not null COMMENT 'grid列ID',
   userId               int not null COMMENT '用户ID',
   primary key (id, userId)
);

/* alter table TR_userColumn comment '用户自定义列关联表'; */
