package com.ezhuanbing.api.dao.mybatis.model.patient;

import java.util.ArrayList;
import java.util.List;

public class PatientExtraInfoCriteria {

    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    protected int limitStart = -1;
    protected int limitEnd = -1;
    
    public PatientExtraInfoCriteria() {
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

    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    public int getLimitEnd() {
        return limitEnd;
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

        public Criteria andPatientidIsNull() {
            addCriterion("patientId is null");
            return (Criteria) this;
        }

        public Criteria andPatientidIsNotNull() {
            addCriterion("patientId is not null");
            return (Criteria) this;
        }

        public Criteria andPatientidEqualTo(Integer value) {
            addCriterion("patientId =", value, "patientid");
            return (Criteria) this;
        }

        public Criteria andPatientidNotEqualTo(Integer value) {
            addCriterion("patientId <>", value, "patientid");
            return (Criteria) this;
        }

        public Criteria andPatientidGreaterThan(Integer value) {
            addCriterion("patientId >", value, "patientid");
            return (Criteria) this;
        }

        public Criteria andPatientidGreaterThanOrEqualTo(Integer value) {
            addCriterion("patientId >=", value, "patientid");
            return (Criteria) this;
        }

        public Criteria andPatientidLessThan(Integer value) {
            addCriterion("patientId <", value, "patientid");
            return (Criteria) this;
        }

        public Criteria andPatientidLessThanOrEqualTo(Integer value) {
            addCriterion("patientId <=", value, "patientid");
            return (Criteria) this;
        }

        public Criteria andPatientidIn(List<Integer> values) {
            addCriterion("patientId in", values, "patientid");
            return (Criteria) this;
        }

        public Criteria andPatientidNotIn(List<Integer> values) {
            addCriterion("patientId not in", values, "patientid");
            return (Criteria) this;
        }

        public Criteria andPatientidBetween(Integer value1, Integer value2) {
            addCriterion("patientId between", value1, value2, "patientid");
            return (Criteria) this;
        }

        public Criteria andPatientidNotBetween(Integer value1, Integer value2) {
            addCriterion("patientId not between", value1, value2, "patientid");
            return (Criteria) this;
        }

        public Criteria andDictionarytypenameIsNull() {
            addCriterion("dictionaryTypeName is null");
            return (Criteria) this;
        }

        public Criteria andDictionarytypenameIsNotNull() {
            addCriterion("dictionaryTypeName is not null");
            return (Criteria) this;
        }

        public Criteria andDictionarytypenameEqualTo(String value) {
            addCriterion("dictionaryTypeName =", value, "dictionarytypename");
            return (Criteria) this;
        }

        public Criteria andDictionarytypenameNotEqualTo(String value) {
            addCriterion("dictionaryTypeName <>", value, "dictionarytypename");
            return (Criteria) this;
        }

        public Criteria andDictionarytypenameGreaterThan(String value) {
            addCriterion("dictionaryTypeName >", value, "dictionarytypename");
            return (Criteria) this;
        }

        public Criteria andDictionarytypenameGreaterThanOrEqualTo(String value) {
            addCriterion("dictionaryTypeName >=", value, "dictionarytypename");
            return (Criteria) this;
        }

        public Criteria andDictionarytypenameLessThan(String value) {
            addCriterion("dictionaryTypeName <", value, "dictionarytypename");
            return (Criteria) this;
        }

        public Criteria andDictionarytypenameLessThanOrEqualTo(String value) {
            addCriterion("dictionaryTypeName <=", value, "dictionarytypename");
            return (Criteria) this;
        }

        public Criteria andDictionarytypenameLike(String value) {
            addCriterion("dictionaryTypeName like", value, "dictionarytypename");
            return (Criteria) this;
        }

        public Criteria andDictionarytypenameNotLike(String value) {
            addCriterion("dictionaryTypeName not like", value, "dictionarytypename");
            return (Criteria) this;
        }

        public Criteria andDictionarytypenameIn(List<String> values) {
            addCriterion("dictionaryTypeName in", values, "dictionarytypename");
            return (Criteria) this;
        }

        public Criteria andDictionarytypenameNotIn(List<String> values) {
            addCriterion("dictionaryTypeName not in", values, "dictionarytypename");
            return (Criteria) this;
        }

        public Criteria andDictionarytypenameBetween(String value1, String value2) {
            addCriterion("dictionaryTypeName between", value1, value2, "dictionarytypename");
            return (Criteria) this;
        }

        public Criteria andDictionarytypenameNotBetween(String value1, String value2) {
            addCriterion("dictionaryTypeName not between", value1, value2, "dictionarytypename");
            return (Criteria) this;
        }

        public Criteria andDictionarycodeIsNull() {
            addCriterion("dictionaryCode is null");
            return (Criteria) this;
        }

        public Criteria andDictionarycodeIsNotNull() {
            addCriterion("dictionaryCode is not null");
            return (Criteria) this;
        }

        public Criteria andDictionarycodeEqualTo(Integer value) {
            addCriterion("dictionaryCode =", value, "dictionarycode");
            return (Criteria) this;
        }

        public Criteria andDictionarycodeNotEqualTo(Integer value) {
            addCriterion("dictionaryCode <>", value, "dictionarycode");
            return (Criteria) this;
        }

        public Criteria andDictionarycodeGreaterThan(Integer value) {
            addCriterion("dictionaryCode >", value, "dictionarycode");
            return (Criteria) this;
        }

        public Criteria andDictionarycodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("dictionaryCode >=", value, "dictionarycode");
            return (Criteria) this;
        }

        public Criteria andDictionarycodeLessThan(Integer value) {
            addCriterion("dictionaryCode <", value, "dictionarycode");
            return (Criteria) this;
        }

        public Criteria andDictionarycodeLessThanOrEqualTo(Integer value) {
            addCriterion("dictionaryCode <=", value, "dictionarycode");
            return (Criteria) this;
        }

        public Criteria andDictionarycodeIn(List<Integer> values) {
            addCriterion("dictionaryCode in", values, "dictionarycode");
            return (Criteria) this;
        }

        public Criteria andDictionarycodeNotIn(List<Integer> values) {
            addCriterion("dictionaryCode not in", values, "dictionarycode");
            return (Criteria) this;
        }

        public Criteria andDictionarycodeBetween(Integer value1, Integer value2) {
            addCriterion("dictionaryCode between", value1, value2, "dictionarycode");
            return (Criteria) this;
        }

        public Criteria andDictionarycodeNotBetween(Integer value1, Integer value2) {
            addCriterion("dictionaryCode not between", value1, value2, "dictionarycode");
            return (Criteria) this;
        }

        public Criteria andDictionarynameIsNull() {
            addCriterion("dictionaryName is null");
            return (Criteria) this;
        }

        public Criteria andDictionarynameIsNotNull() {
            addCriterion("dictionaryName is not null");
            return (Criteria) this;
        }

        public Criteria andDictionarynameEqualTo(String value) {
            addCriterion("dictionaryName =", value, "dictionaryname");
            return (Criteria) this;
        }

        public Criteria andDictionarynameNotEqualTo(String value) {
            addCriterion("dictionaryName <>", value, "dictionaryname");
            return (Criteria) this;
        }

        public Criteria andDictionarynameGreaterThan(String value) {
            addCriterion("dictionaryName >", value, "dictionaryname");
            return (Criteria) this;
        }

        public Criteria andDictionarynameGreaterThanOrEqualTo(String value) {
            addCriterion("dictionaryName >=", value, "dictionaryname");
            return (Criteria) this;
        }

        public Criteria andDictionarynameLessThan(String value) {
            addCriterion("dictionaryName <", value, "dictionaryname");
            return (Criteria) this;
        }

        public Criteria andDictionarynameLessThanOrEqualTo(String value) {
            addCriterion("dictionaryName <=", value, "dictionaryname");
            return (Criteria) this;
        }

        public Criteria andDictionarynameLike(String value) {
            addCriterion("dictionaryName like", value, "dictionaryname");
            return (Criteria) this;
        }

        public Criteria andDictionarynameNotLike(String value) {
            addCriterion("dictionaryName not like", value, "dictionaryname");
            return (Criteria) this;
        }

        public Criteria andDictionarynameIn(List<String> values) {
            addCriterion("dictionaryName in", values, "dictionaryname");
            return (Criteria) this;
        }

        public Criteria andDictionarynameNotIn(List<String> values) {
            addCriterion("dictionaryName not in", values, "dictionaryname");
            return (Criteria) this;
        }

        public Criteria andDictionarynameBetween(String value1, String value2) {
            addCriterion("dictionaryName between", value1, value2, "dictionaryname");
            return (Criteria) this;
        }

        public Criteria andDictionarynameNotBetween(String value1, String value2) {
            addCriterion("dictionaryName not between", value1, value2, "dictionaryname");
            return (Criteria) this;
        }

        public Criteria andDictionarytypenameLikeInsensitive(String value) {
            addCriterion("upper(dictionaryTypeName) like", value.toUpperCase(), "dictionarytypename");
            return (Criteria) this;
        }

        public Criteria andDictionarynameLikeInsensitive(String value) {
            addCriterion("upper(dictionaryName) like", value.toUpperCase(), "dictionaryname");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table mb_patientextrainfo
     *
     * @mbggenerated do_not_delete_during_merge Tue Sep 13 13:38:43 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table mb_patientextrainfo
     *
     * @mbggenerated Tue Sep 13 13:38:43 CST 2016
     */
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