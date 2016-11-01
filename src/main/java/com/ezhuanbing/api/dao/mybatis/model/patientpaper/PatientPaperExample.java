package com.ezhuanbing.api.dao.mybatis.model.patientpaper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientPaperExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PatientPaperExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPatientIdIsNull() {
            addCriterion("patientId is null");
            return (Criteria) this;
        }

        public Criteria andPatientIdIsNotNull() {
            addCriterion("patientId is not null");
            return (Criteria) this;
        }

        public Criteria andPatientIdEqualTo(Integer value) {
            addCriterion("patientId =", value, "patientId");
            return (Criteria) this;
        }

        public Criteria andPatientIdNotEqualTo(Integer value) {
            addCriterion("patientId <>", value, "patientId");
            return (Criteria) this;
        }

        public Criteria andPatientIdGreaterThan(Integer value) {
            addCriterion("patientId >", value, "patientId");
            return (Criteria) this;
        }

        public Criteria andPatientIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("patientId >=", value, "patientId");
            return (Criteria) this;
        }

        public Criteria andPatientIdLessThan(Integer value) {
            addCriterion("patientId <", value, "patientId");
            return (Criteria) this;
        }

        public Criteria andPatientIdLessThanOrEqualTo(Integer value) {
            addCriterion("patientId <=", value, "patientId");
            return (Criteria) this;
        }

        public Criteria andPatientIdIn(List<Integer> values) {
            addCriterion("patientId in", values, "patientId");
            return (Criteria) this;
        }

        public Criteria andPatientIdNotIn(List<Integer> values) {
            addCriterion("patientId not in", values, "patientId");
            return (Criteria) this;
        }

        public Criteria andPatientIdBetween(Integer value1, Integer value2) {
            addCriterion("patientId between", value1, value2, "patientId");
            return (Criteria) this;
        }

        public Criteria andPatientIdNotBetween(Integer value1, Integer value2) {
            addCriterion("patientId not between", value1, value2, "patientId");
            return (Criteria) this;
        }

        public Criteria andPatientPaperDateTimeIsNull() {
            addCriterion("patientPaperDateTime is null");
            return (Criteria) this;
        }

        public Criteria andPatientPaperDateTimeIsNotNull() {
            addCriterion("patientPaperDateTime is not null");
            return (Criteria) this;
        }

        public Criteria andPatientPaperDateTimeEqualTo(Date value) {
            addCriterion("patientPaperDateTime =", value, "patientPaperDateTime");
            return (Criteria) this;
        }

        public Criteria andPatientPaperDateTimeNotEqualTo(Date value) {
            addCriterion("patientPaperDateTime <>", value, "patientPaperDateTime");
            return (Criteria) this;
        }

        public Criteria andPatientPaperDateTimeGreaterThan(Date value) {
            addCriterion("patientPaperDateTime >", value, "patientPaperDateTime");
            return (Criteria) this;
        }

        public Criteria andPatientPaperDateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("patientPaperDateTime >=", value, "patientPaperDateTime");
            return (Criteria) this;
        }

        public Criteria andPatientPaperDateTimeLessThan(Date value) {
            addCriterion("patientPaperDateTime <", value, "patientPaperDateTime");
            return (Criteria) this;
        }

        public Criteria andPatientPaperDateTimeLessThanOrEqualTo(Date value) {
            addCriterion("patientPaperDateTime <=", value, "patientPaperDateTime");
            return (Criteria) this;
        }

        public Criteria andPatientPaperDateTimeIn(List<Date> values) {
            addCriterion("patientPaperDateTime in", values, "patientPaperDateTime");
            return (Criteria) this;
        }

        public Criteria andPatientPaperDateTimeNotIn(List<Date> values) {
            addCriterion("patientPaperDateTime not in", values, "patientPaperDateTime");
            return (Criteria) this;
        }

        public Criteria andPatientPaperDateTimeBetween(Date value1, Date value2) {
            addCriterion("patientPaperDateTime between", value1, value2, "patientPaperDateTime");
            return (Criteria) this;
        }

        public Criteria andPatientPaperDateTimeNotBetween(Date value1, Date value2) {
            addCriterion("patientPaperDateTime not between", value1, value2, "patientPaperDateTime");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorIsNull() {
            addCriterion("paperDoctor is null");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorIsNotNull() {
            addCriterion("paperDoctor is not null");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorEqualTo(String value) {
            addCriterion("paperDoctor =", value, "paperDoctor");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorNotEqualTo(String value) {
            addCriterion("paperDoctor <>", value, "paperDoctor");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorGreaterThan(String value) {
            addCriterion("paperDoctor >", value, "paperDoctor");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorGreaterThanOrEqualTo(String value) {
            addCriterion("paperDoctor >=", value, "paperDoctor");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorLessThan(String value) {
            addCriterion("paperDoctor <", value, "paperDoctor");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorLessThanOrEqualTo(String value) {
            addCriterion("paperDoctor <=", value, "paperDoctor");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorLike(String value) {
            addCriterion("paperDoctor like", value, "paperDoctor");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorNotLike(String value) {
            addCriterion("paperDoctor not like", value, "paperDoctor");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorIn(List<String> values) {
            addCriterion("paperDoctor in", values, "paperDoctor");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorNotIn(List<String> values) {
            addCriterion("paperDoctor not in", values, "paperDoctor");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorBetween(String value1, String value2) {
            addCriterion("paperDoctor between", value1, value2, "paperDoctor");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorNotBetween(String value1, String value2) {
            addCriterion("paperDoctor not between", value1, value2, "paperDoctor");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorIdIsNull() {
            addCriterion("paperDoctorId is null");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorIdIsNotNull() {
            addCriterion("paperDoctorId is not null");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorIdEqualTo(Integer value) {
            addCriterion("paperDoctorId =", value, "paperDoctorId");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorIdNotEqualTo(Integer value) {
            addCriterion("paperDoctorId <>", value, "paperDoctorId");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorIdGreaterThan(Integer value) {
            addCriterion("paperDoctorId >", value, "paperDoctorId");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("paperDoctorId >=", value, "paperDoctorId");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorIdLessThan(Integer value) {
            addCriterion("paperDoctorId <", value, "paperDoctorId");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorIdLessThanOrEqualTo(Integer value) {
            addCriterion("paperDoctorId <=", value, "paperDoctorId");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorIdIn(List<Integer> values) {
            addCriterion("paperDoctorId in", values, "paperDoctorId");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorIdNotIn(List<Integer> values) {
            addCriterion("paperDoctorId not in", values, "paperDoctorId");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorIdBetween(Integer value1, Integer value2) {
            addCriterion("paperDoctorId between", value1, value2, "paperDoctorId");
            return (Criteria) this;
        }

        public Criteria andPaperDoctorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("paperDoctorId not between", value1, value2, "paperDoctorId");
            return (Criteria) this;
        }

        public Criteria andPaperDeptIsNull() {
            addCriterion("paperDept is null");
            return (Criteria) this;
        }

        public Criteria andPaperDeptIsNotNull() {
            addCriterion("paperDept is not null");
            return (Criteria) this;
        }

        public Criteria andPaperDeptEqualTo(String value) {
            addCriterion("paperDept =", value, "paperDept");
            return (Criteria) this;
        }

        public Criteria andPaperDeptNotEqualTo(String value) {
            addCriterion("paperDept <>", value, "paperDept");
            return (Criteria) this;
        }

        public Criteria andPaperDeptGreaterThan(String value) {
            addCriterion("paperDept >", value, "paperDept");
            return (Criteria) this;
        }

        public Criteria andPaperDeptGreaterThanOrEqualTo(String value) {
            addCriterion("paperDept >=", value, "paperDept");
            return (Criteria) this;
        }

        public Criteria andPaperDeptLessThan(String value) {
            addCriterion("paperDept <", value, "paperDept");
            return (Criteria) this;
        }

        public Criteria andPaperDeptLessThanOrEqualTo(String value) {
            addCriterion("paperDept <=", value, "paperDept");
            return (Criteria) this;
        }

        public Criteria andPaperDeptLike(String value) {
            addCriterion("paperDept like", value, "paperDept");
            return (Criteria) this;
        }

        public Criteria andPaperDeptNotLike(String value) {
            addCriterion("paperDept not like", value, "paperDept");
            return (Criteria) this;
        }

        public Criteria andPaperDeptIn(List<String> values) {
            addCriterion("paperDept in", values, "paperDept");
            return (Criteria) this;
        }

        public Criteria andPaperDeptNotIn(List<String> values) {
            addCriterion("paperDept not in", values, "paperDept");
            return (Criteria) this;
        }

        public Criteria andPaperDeptBetween(String value1, String value2) {
            addCriterion("paperDept between", value1, value2, "paperDept");
            return (Criteria) this;
        }

        public Criteria andPaperDeptNotBetween(String value1, String value2) {
            addCriterion("paperDept not between", value1, value2, "paperDept");
            return (Criteria) this;
        }

        public Criteria andPaperDeptIdIsNull() {
            addCriterion("paperDeptId is null");
            return (Criteria) this;
        }

        public Criteria andPaperDeptIdIsNotNull() {
            addCriterion("paperDeptId is not null");
            return (Criteria) this;
        }

        public Criteria andPaperDeptIdEqualTo(Integer value) {
            addCriterion("paperDeptId =", value, "paperDeptId");
            return (Criteria) this;
        }

        public Criteria andPaperDeptIdNotEqualTo(Integer value) {
            addCriterion("paperDeptId <>", value, "paperDeptId");
            return (Criteria) this;
        }

        public Criteria andPaperDeptIdGreaterThan(Integer value) {
            addCriterion("paperDeptId >", value, "paperDeptId");
            return (Criteria) this;
        }

        public Criteria andPaperDeptIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("paperDeptId >=", value, "paperDeptId");
            return (Criteria) this;
        }

        public Criteria andPaperDeptIdLessThan(Integer value) {
            addCriterion("paperDeptId <", value, "paperDeptId");
            return (Criteria) this;
        }

        public Criteria andPaperDeptIdLessThanOrEqualTo(Integer value) {
            addCriterion("paperDeptId <=", value, "paperDeptId");
            return (Criteria) this;
        }

        public Criteria andPaperDeptIdIn(List<Integer> values) {
            addCriterion("paperDeptId in", values, "paperDeptId");
            return (Criteria) this;
        }

        public Criteria andPaperDeptIdNotIn(List<Integer> values) {
            addCriterion("paperDeptId not in", values, "paperDeptId");
            return (Criteria) this;
        }

        public Criteria andPaperDeptIdBetween(Integer value1, Integer value2) {
            addCriterion("paperDeptId between", value1, value2, "paperDeptId");
            return (Criteria) this;
        }

        public Criteria andPaperDeptIdNotBetween(Integer value1, Integer value2) {
            addCriterion("paperDeptId not between", value1, value2, "paperDeptId");
            return (Criteria) this;
        }

        public Criteria andPaperTitleIsNull() {
            addCriterion("paperTitle is null");
            return (Criteria) this;
        }

        public Criteria andPaperTitleIsNotNull() {
            addCriterion("paperTitle is not null");
            return (Criteria) this;
        }

        public Criteria andPaperTitleEqualTo(String value) {
            addCriterion("paperTitle =", value, "paperTitle");
            return (Criteria) this;
        }

        public Criteria andPaperTitleNotEqualTo(String value) {
            addCriterion("paperTitle <>", value, "paperTitle");
            return (Criteria) this;
        }

        public Criteria andPaperTitleGreaterThan(String value) {
            addCriterion("paperTitle >", value, "paperTitle");
            return (Criteria) this;
        }

        public Criteria andPaperTitleGreaterThanOrEqualTo(String value) {
            addCriterion("paperTitle >=", value, "paperTitle");
            return (Criteria) this;
        }

        public Criteria andPaperTitleLessThan(String value) {
            addCriterion("paperTitle <", value, "paperTitle");
            return (Criteria) this;
        }

        public Criteria andPaperTitleLessThanOrEqualTo(String value) {
            addCriterion("paperTitle <=", value, "paperTitle");
            return (Criteria) this;
        }

        public Criteria andPaperTitleLike(String value) {
            addCriterion("paperTitle like", value, "paperTitle");
            return (Criteria) this;
        }

        public Criteria andPaperTitleNotLike(String value) {
            addCriterion("paperTitle not like", value, "paperTitle");
            return (Criteria) this;
        }

        public Criteria andPaperTitleIn(List<String> values) {
            addCriterion("paperTitle in", values, "paperTitle");
            return (Criteria) this;
        }

        public Criteria andPaperTitleNotIn(List<String> values) {
            addCriterion("paperTitle not in", values, "paperTitle");
            return (Criteria) this;
        }

        public Criteria andPaperTitleBetween(String value1, String value2) {
            addCriterion("paperTitle between", value1, value2, "paperTitle");
            return (Criteria) this;
        }

        public Criteria andPaperTitleNotBetween(String value1, String value2) {
            addCriterion("paperTitle not between", value1, value2, "paperTitle");
            return (Criteria) this;
        }

        public Criteria andPaperStatusIsNull() {
            addCriterion("paperStatus is null");
            return (Criteria) this;
        }

        public Criteria andPaperStatusIsNotNull() {
            addCriterion("paperStatus is not null");
            return (Criteria) this;
        }

        public Criteria andPaperStatusEqualTo(Integer value) {
            addCriterion("paperStatus =", value, "paperStatus");
            return (Criteria) this;
        }

        public Criteria andPaperStatusNotEqualTo(Integer value) {
            addCriterion("paperStatus <>", value, "paperStatus");
            return (Criteria) this;
        }

        public Criteria andPaperStatusGreaterThan(Integer value) {
            addCriterion("paperStatus >", value, "paperStatus");
            return (Criteria) this;
        }

        public Criteria andPaperStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("paperStatus >=", value, "paperStatus");
            return (Criteria) this;
        }

        public Criteria andPaperStatusLessThan(Integer value) {
            addCriterion("paperStatus <", value, "paperStatus");
            return (Criteria) this;
        }

        public Criteria andPaperStatusLessThanOrEqualTo(Integer value) {
            addCriterion("paperStatus <=", value, "paperStatus");
            return (Criteria) this;
        }

        public Criteria andPaperStatusIn(List<Integer> values) {
            addCriterion("paperStatus in", values, "paperStatus");
            return (Criteria) this;
        }

        public Criteria andPaperStatusNotIn(List<Integer> values) {
            addCriterion("paperStatus not in", values, "paperStatus");
            return (Criteria) this;
        }

        public Criteria andPaperStatusBetween(Integer value1, Integer value2) {
            addCriterion("paperStatus between", value1, value2, "paperStatus");
            return (Criteria) this;
        }

        public Criteria andPaperStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("paperStatus not between", value1, value2, "paperStatus");
            return (Criteria) this;
        }

        public Criteria andPlanDetailIdIsNull() {
            addCriterion("planDetailId is null");
            return (Criteria) this;
        }

        public Criteria andPlanDetailIdIsNotNull() {
            addCriterion("planDetailId is not null");
            return (Criteria) this;
        }

        public Criteria andPlanDetailIdEqualTo(Integer value) {
            addCriterion("planDetailId =", value, "planDetailId");
            return (Criteria) this;
        }

        public Criteria andPlanDetailIdNotEqualTo(Integer value) {
            addCriterion("planDetailId <>", value, "planDetailId");
            return (Criteria) this;
        }

        public Criteria andPlanDetailIdGreaterThan(Integer value) {
            addCriterion("planDetailId >", value, "planDetailId");
            return (Criteria) this;
        }

        public Criteria andPlanDetailIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("planDetailId >=", value, "planDetailId");
            return (Criteria) this;
        }

        public Criteria andPlanDetailIdLessThan(Integer value) {
            addCriterion("planDetailId <", value, "planDetailId");
            return (Criteria) this;
        }

        public Criteria andPlanDetailIdLessThanOrEqualTo(Integer value) {
            addCriterion("planDetailId <=", value, "planDetailId");
            return (Criteria) this;
        }

        public Criteria andPlanDetailIdIn(List<Integer> values) {
            addCriterion("planDetailId in", values, "planDetailId");
            return (Criteria) this;
        }

        public Criteria andPlanDetailIdNotIn(List<Integer> values) {
            addCriterion("planDetailId not in", values, "planDetailId");
            return (Criteria) this;
        }

        public Criteria andPlanDetailIdBetween(Integer value1, Integer value2) {
            addCriterion("planDetailId between", value1, value2, "planDetailId");
            return (Criteria) this;
        }

        public Criteria andPlanDetailIdNotBetween(Integer value1, Integer value2) {
            addCriterion("planDetailId not between", value1, value2, "planDetailId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}