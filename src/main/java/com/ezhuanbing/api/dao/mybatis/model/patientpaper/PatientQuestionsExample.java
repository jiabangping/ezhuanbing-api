package com.ezhuanbing.api.dao.mybatis.model.patientpaper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientQuestionsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PatientQuestionsExample() {
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

        public Criteria andPatientContentClassIdIsNull() {
            addCriterion("patientContentClassId is null");
            return (Criteria) this;
        }

        public Criteria andPatientContentClassIdIsNotNull() {
            addCriterion("patientContentClassId is not null");
            return (Criteria) this;
        }

        public Criteria andPatientContentClassIdEqualTo(Integer value) {
            addCriterion("patientContentClassId =", value, "patientContentClassId");
            return (Criteria) this;
        }

        public Criteria andPatientContentClassIdNotEqualTo(Integer value) {
            addCriterion("patientContentClassId <>", value, "patientContentClassId");
            return (Criteria) this;
        }

        public Criteria andPatientContentClassIdGreaterThan(Integer value) {
            addCriterion("patientContentClassId >", value, "patientContentClassId");
            return (Criteria) this;
        }

        public Criteria andPatientContentClassIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("patientContentClassId >=", value, "patientContentClassId");
            return (Criteria) this;
        }

        public Criteria andPatientContentClassIdLessThan(Integer value) {
            addCriterion("patientContentClassId <", value, "patientContentClassId");
            return (Criteria) this;
        }

        public Criteria andPatientContentClassIdLessThanOrEqualTo(Integer value) {
            addCriterion("patientContentClassId <=", value, "patientContentClassId");
            return (Criteria) this;
        }

        public Criteria andPatientContentClassIdIn(List<Integer> values) {
            addCriterion("patientContentClassId in", values, "patientContentClassId");
            return (Criteria) this;
        }

        public Criteria andPatientContentClassIdNotIn(List<Integer> values) {
            addCriterion("patientContentClassId not in", values, "patientContentClassId");
            return (Criteria) this;
        }

        public Criteria andPatientContentClassIdBetween(Integer value1, Integer value2) {
            addCriterion("patientContentClassId between", value1, value2, "patientContentClassId");
            return (Criteria) this;
        }

        public Criteria andPatientContentClassIdNotBetween(Integer value1, Integer value2) {
            addCriterion("patientContentClassId not between", value1, value2, "patientContentClassId");
            return (Criteria) this;
        }

        public Criteria andQstTypeIsNull() {
            addCriterion("qstType is null");
            return (Criteria) this;
        }

        public Criteria andQstTypeIsNotNull() {
            addCriterion("qstType is not null");
            return (Criteria) this;
        }

        public Criteria andQstTypeEqualTo(Integer value) {
            addCriterion("qstType =", value, "qstType");
            return (Criteria) this;
        }

        public Criteria andQstTypeNotEqualTo(Integer value) {
            addCriterion("qstType <>", value, "qstType");
            return (Criteria) this;
        }

        public Criteria andQstTypeGreaterThan(Integer value) {
            addCriterion("qstType >", value, "qstType");
            return (Criteria) this;
        }

        public Criteria andQstTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("qstType >=", value, "qstType");
            return (Criteria) this;
        }

        public Criteria andQstTypeLessThan(Integer value) {
            addCriterion("qstType <", value, "qstType");
            return (Criteria) this;
        }

        public Criteria andQstTypeLessThanOrEqualTo(Integer value) {
            addCriterion("qstType <=", value, "qstType");
            return (Criteria) this;
        }

        public Criteria andQstTypeIn(List<Integer> values) {
            addCriterion("qstType in", values, "qstType");
            return (Criteria) this;
        }

        public Criteria andQstTypeNotIn(List<Integer> values) {
            addCriterion("qstType not in", values, "qstType");
            return (Criteria) this;
        }

        public Criteria andQstTypeBetween(Integer value1, Integer value2) {
            addCriterion("qstType between", value1, value2, "qstType");
            return (Criteria) this;
        }

        public Criteria andQstTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("qstType not between", value1, value2, "qstType");
            return (Criteria) this;
        }

        public Criteria andQstTitleIsNull() {
            addCriterion("qstTitle is null");
            return (Criteria) this;
        }

        public Criteria andQstTitleIsNotNull() {
            addCriterion("qstTitle is not null");
            return (Criteria) this;
        }

        public Criteria andQstTitleEqualTo(String value) {
            addCriterion("qstTitle =", value, "qstTitle");
            return (Criteria) this;
        }

        public Criteria andQstTitleNotEqualTo(String value) {
            addCriterion("qstTitle <>", value, "qstTitle");
            return (Criteria) this;
        }

        public Criteria andQstTitleGreaterThan(String value) {
            addCriterion("qstTitle >", value, "qstTitle");
            return (Criteria) this;
        }

        public Criteria andQstTitleGreaterThanOrEqualTo(String value) {
            addCriterion("qstTitle >=", value, "qstTitle");
            return (Criteria) this;
        }

        public Criteria andQstTitleLessThan(String value) {
            addCriterion("qstTitle <", value, "qstTitle");
            return (Criteria) this;
        }

        public Criteria andQstTitleLessThanOrEqualTo(String value) {
            addCriterion("qstTitle <=", value, "qstTitle");
            return (Criteria) this;
        }

        public Criteria andQstTitleLike(String value) {
            addCriterion("qstTitle like", value, "qstTitle");
            return (Criteria) this;
        }

        public Criteria andQstTitleNotLike(String value) {
            addCriterion("qstTitle not like", value, "qstTitle");
            return (Criteria) this;
        }

        public Criteria andQstTitleIn(List<String> values) {
            addCriterion("qstTitle in", values, "qstTitle");
            return (Criteria) this;
        }

        public Criteria andQstTitleNotIn(List<String> values) {
            addCriterion("qstTitle not in", values, "qstTitle");
            return (Criteria) this;
        }

        public Criteria andQstTitleBetween(String value1, String value2) {
            addCriterion("qstTitle between", value1, value2, "qstTitle");
            return (Criteria) this;
        }

        public Criteria andQstTitleNotBetween(String value1, String value2) {
            addCriterion("qstTitle not between", value1, value2, "qstTitle");
            return (Criteria) this;
        }

        public Criteria andQstStatusIsNull() {
            addCriterion("qstStatus is null");
            return (Criteria) this;
        }

        public Criteria andQstStatusIsNotNull() {
            addCriterion("qstStatus is not null");
            return (Criteria) this;
        }

        public Criteria andQstStatusEqualTo(Integer value) {
            addCriterion("qstStatus =", value, "qstStatus");
            return (Criteria) this;
        }

        public Criteria andQstStatusNotEqualTo(Integer value) {
            addCriterion("qstStatus <>", value, "qstStatus");
            return (Criteria) this;
        }

        public Criteria andQstStatusGreaterThan(Integer value) {
            addCriterion("qstStatus >", value, "qstStatus");
            return (Criteria) this;
        }

        public Criteria andQstStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("qstStatus >=", value, "qstStatus");
            return (Criteria) this;
        }

        public Criteria andQstStatusLessThan(Integer value) {
            addCriterion("qstStatus <", value, "qstStatus");
            return (Criteria) this;
        }

        public Criteria andQstStatusLessThanOrEqualTo(Integer value) {
            addCriterion("qstStatus <=", value, "qstStatus");
            return (Criteria) this;
        }

        public Criteria andQstStatusIn(List<Integer> values) {
            addCriterion("qstStatus in", values, "qstStatus");
            return (Criteria) this;
        }

        public Criteria andQstStatusNotIn(List<Integer> values) {
            addCriterion("qstStatus not in", values, "qstStatus");
            return (Criteria) this;
        }

        public Criteria andQstStatusBetween(Integer value1, Integer value2) {
            addCriterion("qstStatus between", value1, value2, "qstStatus");
            return (Criteria) this;
        }

        public Criteria andQstStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("qstStatus not between", value1, value2, "qstStatus");
            return (Criteria) this;
        }

        public Criteria andQstAnswerDateTimeIsNull() {
            addCriterion("qstAnswerDateTime is null");
            return (Criteria) this;
        }

        public Criteria andQstAnswerDateTimeIsNotNull() {
            addCriterion("qstAnswerDateTime is not null");
            return (Criteria) this;
        }

        public Criteria andQstAnswerDateTimeEqualTo(Date value) {
            addCriterion("qstAnswerDateTime =", value, "qstAnswerDateTime");
            return (Criteria) this;
        }

        public Criteria andQstAnswerDateTimeNotEqualTo(Date value) {
            addCriterion("qstAnswerDateTime <>", value, "qstAnswerDateTime");
            return (Criteria) this;
        }

        public Criteria andQstAnswerDateTimeGreaterThan(Date value) {
            addCriterion("qstAnswerDateTime >", value, "qstAnswerDateTime");
            return (Criteria) this;
        }

        public Criteria andQstAnswerDateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("qstAnswerDateTime >=", value, "qstAnswerDateTime");
            return (Criteria) this;
        }

        public Criteria andQstAnswerDateTimeLessThan(Date value) {
            addCriterion("qstAnswerDateTime <", value, "qstAnswerDateTime");
            return (Criteria) this;
        }

        public Criteria andQstAnswerDateTimeLessThanOrEqualTo(Date value) {
            addCriterion("qstAnswerDateTime <=", value, "qstAnswerDateTime");
            return (Criteria) this;
        }

        public Criteria andQstAnswerDateTimeIn(List<Date> values) {
            addCriterion("qstAnswerDateTime in", values, "qstAnswerDateTime");
            return (Criteria) this;
        }

        public Criteria andQstAnswerDateTimeNotIn(List<Date> values) {
            addCriterion("qstAnswerDateTime not in", values, "qstAnswerDateTime");
            return (Criteria) this;
        }

        public Criteria andQstAnswerDateTimeBetween(Date value1, Date value2) {
            addCriterion("qstAnswerDateTime between", value1, value2, "qstAnswerDateTime");
            return (Criteria) this;
        }

        public Criteria andQstAnswerDateTimeNotBetween(Date value1, Date value2) {
            addCriterion("qstAnswerDateTime not between", value1, value2, "qstAnswerDateTime");
            return (Criteria) this;
        }

        public Criteria andQuestionsTemplatesIdIsNull() {
            addCriterion("questionsTemplatesId is null");
            return (Criteria) this;
        }

        public Criteria andQuestionsTemplatesIdIsNotNull() {
            addCriterion("questionsTemplatesId is not null");
            return (Criteria) this;
        }

        public Criteria andQuestionsTemplatesIdEqualTo(Integer value) {
            addCriterion("questionsTemplatesId =", value, "questionsTemplatesId");
            return (Criteria) this;
        }

        public Criteria andQuestionsTemplatesIdNotEqualTo(Integer value) {
            addCriterion("questionsTemplatesId <>", value, "questionsTemplatesId");
            return (Criteria) this;
        }

        public Criteria andQuestionsTemplatesIdGreaterThan(Integer value) {
            addCriterion("questionsTemplatesId >", value, "questionsTemplatesId");
            return (Criteria) this;
        }

        public Criteria andQuestionsTemplatesIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("questionsTemplatesId >=", value, "questionsTemplatesId");
            return (Criteria) this;
        }

        public Criteria andQuestionsTemplatesIdLessThan(Integer value) {
            addCriterion("questionsTemplatesId <", value, "questionsTemplatesId");
            return (Criteria) this;
        }

        public Criteria andQuestionsTemplatesIdLessThanOrEqualTo(Integer value) {
            addCriterion("questionsTemplatesId <=", value, "questionsTemplatesId");
            return (Criteria) this;
        }

        public Criteria andQuestionsTemplatesIdIn(List<Integer> values) {
            addCriterion("questionsTemplatesId in", values, "questionsTemplatesId");
            return (Criteria) this;
        }

        public Criteria andQuestionsTemplatesIdNotIn(List<Integer> values) {
            addCriterion("questionsTemplatesId not in", values, "questionsTemplatesId");
            return (Criteria) this;
        }

        public Criteria andQuestionsTemplatesIdBetween(Integer value1, Integer value2) {
            addCriterion("questionsTemplatesId between", value1, value2, "questionsTemplatesId");
            return (Criteria) this;
        }

        public Criteria andQuestionsTemplatesIdNotBetween(Integer value1, Integer value2) {
            addCriterion("questionsTemplatesId not between", value1, value2, "questionsTemplatesId");
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