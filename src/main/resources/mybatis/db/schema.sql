
/*==============================================================*/
/* Table: Patient                                               */
/*==============================================================*/
drop table if exists mb_patient;
create table mb_patient
(
   id                   int not null auto_increment,
   loginName            varchar(50),
   password             varchar(20),
   name                 varchar(50),
   sex                  char(1),
   photo                varchar(20),
   birthday             timestamp comment '生日',
   idCard               varchar(18),
   height               int,
   weight               int,
   address              varchar(200),
   extraInfoId          int comment '附加信息id',
   primary key (id)
);

drop table if exists mb_patientfollowupplan;

/*==============================================================*/
/* Table: mb_patientfollowupplan                                      */
/*==============================================================*/
create table mb_patientfollowupplan
(
   id                   int not null auto_increment,
   doctorId             int,
   patientId            int,
   planName             varchar(100),
   planType             int,
   startDate            timestamp,
   endDate              timestamp,
   status               char,
   primary key (id)
);

drop table if exists mb_patientfollowupplanDetail;

/*==============================================================*/
/* Table: mb_patientfollowupplanDetail                                            */
/*==============================================================*/
create table mb_patientfollowupplanDetail
(
   id                   int not null,
   planId               int,
   templateId           int comment '模板id',
   questionnaireId      int comment '问卷id',
   status               char comment '0:新建,1:已发送给患者,2:患者已填写,3:随访结束',
   planOrder            int comment '计划次序',
   planResult           int comment '随访结果',
   planMark             varchar(100) comment '随访备注',
   primary key (id)
);

-- 导出  表 ehuizhen_develop.mb_patientcontentclass 结构
CREATE TABLE IF NOT EXISTS `mb_patientcontentclass` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patientPaperId` int(11) NOT NULL COMMENT '患者问卷Id',
  `contentClassTemplatesId` int(11) NOT NULL COMMENT '内容分类Id',
  `className` varchar(100) NOT NULL COMMENT '分类名称',
  `classSort` int(11) DEFAULT NULL COMMENT '分类排序',
  `classStatus` int(11) DEFAULT NULL COMMENT '分类状态',
  `leaf` int(11) DEFAULT NULL COMMENT '1:叶子，0:非叶子',
  `pid` int(11) DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3013 DEFAULT CHARSET=utf8mb4 COMMENT='患者问卷分类';


-- 导出  表 ehuizhen_develop.mb_patientpaper 结构
CREATE TABLE IF NOT EXISTS `mb_patientpaper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patientId` int(11) NOT NULL COMMENT '患者Id',
  `patientPaperDateTime` timestamp NULL DEFAULT NULL COMMENT '问卷创建时间',
  `paperDoctor` varchar(100) NOT NULL COMMENT '问卷创建医生',
  `paperDoctorId` int(11) NOT NULL COMMENT '问卷创建医生Id',
  `paperDept` varchar(50) DEFAULT NULL COMMENT '医生所属科室',
  `paperDeptId` int(11) DEFAULT NULL COMMENT '医生所属科室Id',
  `paperTitle` varchar(100) NOT NULL COMMENT '问卷标题',
  `paperSummary` text COMMENT '问卷摘要',
  `paperStatus` int(11) DEFAULT NULL COMMENT '问卷状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2001 DEFAULT CHARSET=utf8mb4 COMMENT='患者问卷';



-- 导出  表 ehuizhen_develop.mb_patientquestions 结构
CREATE TABLE IF NOT EXISTS `mb_patientquestions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patientContentClassId` int(11) DEFAULT NULL COMMENT '患者问卷分类Id',
  `qstType` int(11) DEFAULT NULL COMMENT '问题类型',
  `qstTitle` char(200) DEFAULT NULL COMMENT '问题题目',
  `qstPicture` text COMMENT '问题图片',
  `qstStatus` int(11) DEFAULT NULL COMMENT '问题状态',
  `qstAnswerDateTime` timestamp NULL DEFAULT NULL COMMENT '问题回答时间',
  `qstAnswer` text COMMENT '问题答案',
  `questionsTemplatesId` int(11) DEFAULT NULL COMMENT '题目Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='患者问卷题目';




drop table if exists `mb_paperimage`;
create table `mb_paperimage`(
 `id` int(11) not null auto_increment primary key,
 `paperId` int(11) not null comment '文件id',
 `imageName` varchar(30) not null comment '图片名称'
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='问卷图片';



