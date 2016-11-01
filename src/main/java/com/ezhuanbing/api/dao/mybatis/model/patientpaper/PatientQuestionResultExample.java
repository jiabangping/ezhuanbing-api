package com.ezhuanbing.api.dao.mybatis.model.patientpaper;

import java.util.ArrayList;
import java.util.List;

public class PatientQuestionResultExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PatientQuestionResultExample() {
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

        public Criteria andPatientQuestionsIdIsNull() {
            addCriterion("patientQuestionsId is null");
            return (Criteria) this;
        }

        public Criteria andPatientQuestionsIdIsNotNull() {
            addCriterion("patientQuestionsId is not null");
            return (Criteria) this;
        }

        public Criteria andPatientQuestionsIdEqualTo(Integer value) {
            addCriterion("patientQuestionsId =", value, "patientQuestionsId");
            return (Criteria) this;
        }

        public Criteria andPatientQuestionsIdNotEqualTo(Integer value) {
            addCriterion("patientQuestionsId <>", value, "patientQuestionsId");
            return (Criteria) this;
        }

        public Criteria andPatientQuestionsIdGreaterThan(Integer value) {
            addCriterion("patientQuestionsId >", value, "patientQuestionsId");
            return (Criteria) this;
        }

        public Criteria andPatientQuestionsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("patientQuestionsId >=", value, "patientQuestionsId");
            return (Criteria) this;
        }

        public Criteria andPatientQuestionsIdLessThan(Integer value) {
            addCriterion("patientQuestionsId <", value, "patientQuestionsId");
            return (Criteria) this;
        }

        public Criteria andPatientQuestionsIdLessThanOrEqualTo(Integer value) {
            addCriterion("patientQuestionsId <=", value, "patientQuestionsId");
            return (Criteria) this;
        }

        public Criteria andPatientQuestionsIdIn(List<Integer> values) {
            addCriterion("patientQuestionsId in", values, "patientQuestionsId");
            return (Criteria) this;
        }

        public Criteria andPatientQuestionsIdNotIn(List<Integer> values) {
            addCriterion("patientQuestionsId not in", values, "patientQuestionsId");
            return (Criteria) this;
        }

        public Criteria andPatientQuestionsIdBetween(Integer value1, Integer value2) {
            addCriterion("patientQuestionsId between", value1, value2, "patientQuestionsId");
            return (Criteria) this;
        }

        public Criteria andPatientQuestionsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("patientQuestionsId not between", value1, value2, "patientQuestionsId");
            return (Criteria) this;
        }

        public Criteria andOptionTemplatesIdIsNull() {
            addCriterion("optionTemplatesId is null");
            return (Criteria) this;
        }

        public Criteria andOptionTemplatesIdIsNotNull() {
            addCriterion("optionTemplatesId is not null");
            return (Criteria) this;
        }

        public Criteria andOptionTemplatesIdEqualTo(Integer value) {
            addCriterion("optionTemplatesId =", value, "optionTemplatesId");
            return (Criteria) this;
        }

        public Criteria andOptionTemplatesIdNotEqualTo(Integer value) {
            addCriterion("optionTemplatesId <>", value, "optionTemplatesId");
            return (Criteria) this;
        }

        public Criteria andOptionTemplatesIdGreaterThan(Integer value) {
            addCriterion("optionTemplatesId >", value, "optionTemplatesId");
            return (Criteria) this;
        }

        public Criteria andOptionTemplatesIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("optionTemplatesId >=", value, "optionTemplatesId");
            return (Criteria) this;
        }

        public Criteria andOptionTemplatesIdLessThan(Integer value) {
            addCriterion("optionTemplatesId <", value, "optionTemplatesId");
            return (Criteria) this;
        }

        public Criteria andOptionTemplatesIdLessThanOrEqualTo(Integer value) {
            addCriterion("optionTemplatesId <=", value, "optionTemplatesId");
            return (Criteria) this;
        }

        public Criteria andOptionTemplatesIdIn(List<Integer> values) {
            addCriterion("optionTemplatesId in", values, "optionTemplatesId");
            return (Criteria) this;
        }

        public Criteria andOptionTemplatesIdNotIn(List<Integer> values) {
            addCriterion("optionTemplatesId not in", values, "optionTemplatesId");
            return (Criteria) this;
        }

        public Criteria andOptionTemplatesIdBetween(Integer value1, Integer value2) {
            addCriterion("optionTemplatesId between", value1, value2, "optionTemplatesId");
            return (Criteria) this;
        }

        public Criteria andOptionTemplatesIdNotBetween(Integer value1, Integer value2) {
            addCriterion("optionTemplatesId not between", value1, value2, "optionTemplatesId");
            return (Criteria) this;
        }

        public Criteria andOptionNumberIsNull() {
            addCriterion("optionNumber is null");
            return (Criteria) this;
        }

        public Criteria andOptionNumberIsNotNull() {
            addCriterion("optionNumber is not null");
            return (Criteria) this;
        }

        public Criteria andOptionNumberEqualTo(String value) {
            addCriterion("optionNumber =", value, "optionNumber");
            return (Criteria) this;
        }

        public Criteria andOptionNumberNotEqualTo(String value) {
            addCriterion("optionNumber <>", value, "optionNumber");
            return (Criteria) this;
        }

        public Criteria andOptionNumberGreaterThan(String value) {
            addCriterion("optionNumber >", value, "optionNumber");
            return (Criteria) this;
        }

        public Criteria andOptionNumberGreaterThanOrEqualTo(String value) {
            addCriterion("optionNumber >=", value, "optionNumber");
            return (Criteria) this;
        }

        public Criteria andOptionNumberLessThan(String value) {
            addCriterion("optionNumber <", value, "optionNumber");
            return (Criteria) this;
        }

        public Criteria andOptionNumberLessThanOrEqualTo(String value) {
            addCriterion("optionNumber <=", value, "optionNumber");
            return (Criteria) this;
        }

        public Criteria andOptionNumberLike(String value) {
            addCriterion("optionNumber like", value, "optionNumber");
            return (Criteria) this;
        }

        public Criteria andOptionNumberNotLike(String value) {
            addCriterion("optionNumber not like", value, "optionNumber");
            return (Criteria) this;
        }

        public Criteria andOptionNumberIn(List<String> values) {
            addCriterion("optionNumber in", values, "optionNumber");
            return (Criteria) this;
        }

        public Criteria andOptionNumberNotIn(List<String> values) {
            addCriterion("optionNumber not in", values, "optionNumber");
            return (Criteria) this;
        }

        public Criteria andOptionNumberBetween(String value1, String value2) {
            addCriterion("optionNumber between", value1, value2, "optionNumber");
            return (Criteria) this;
        }

        public Criteria andOptionNumberNotBetween(String value1, String value2) {
            addCriterion("optionNumber not between", value1, value2, "optionNumber");
            return (Criteria) this;
        }

        public Criteria andOptionTypeIsNull() {
            addCriterion("optionType is null");
            return (Criteria) this;
        }

        public Criteria andOptionTypeIsNotNull() {
            addCriterion("optionType is not null");
            return (Criteria) this;
        }

        public Criteria andOptionTypeEqualTo(Integer value) {
            addCriterion("optionType =", value, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeNotEqualTo(Integer value) {
            addCriterion("optionType <>", value, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeGreaterThan(Integer value) {
            addCriterion("optionType >", value, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("optionType >=", value, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeLessThan(Integer value) {
            addCriterion("optionType <", value, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeLessThanOrEqualTo(Integer value) {
            addCriterion("optionType <=", value, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeIn(List<Integer> values) {
            addCriterion("optionType in", values, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeNotIn(List<Integer> values) {
            addCriterion("optionType not in", values, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeBetween(Integer value1, Integer value2) {
            addCriterion("optionType between", value1, value2, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("optionType not between", value1, value2, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionPictureIsNull() {
            addCriterion("optionPicture is null");
            return (Criteria) this;
        }

        public Criteria andOptionPictureIsNotNull() {
            addCriterion("optionPicture is not null");
            return (Criteria) this;
        }

        public Criteria andOptionPictureEqualTo(String value) {
            addCriterion("optionPicture =", value, "optionPicture");
            return (Criteria) this;
        }

        public Criteria andOptionPictureNotEqualTo(String value) {
            addCriterion("optionPicture <>", value, "optionPicture");
            return (Criteria) this;
        }

        public Criteria andOptionPictureGreaterThan(String value) {
            addCriterion("optionPicture >", value, "optionPicture");
            return (Criteria) this;
        }

        public Criteria andOptionPictureGreaterThanOrEqualTo(String value) {
            addCriterion("optionPicture >=", value, "optionPicture");
            return (Criteria) this;
        }

        public Criteria andOptionPictureLessThan(String value) {
            addCriterion("optionPicture <", value, "optionPicture");
            return (Criteria) this;
        }

        public Criteria andOptionPictureLessThanOrEqualTo(String value) {
            addCriterion("optionPicture <=", value, "optionPicture");
            return (Criteria) this;
        }

        public Criteria andOptionPictureLike(String value) {
            addCriterion("optionPicture like", value, "optionPicture");
            return (Criteria) this;
        }

        public Criteria andOptionPictureNotLike(String value) {
            addCriterion("optionPicture not like", value, "optionPicture");
            return (Criteria) this;
        }

        public Criteria andOptionPictureIn(List<String> values) {
            addCriterion("optionPicture in", values, "optionPicture");
            return (Criteria) this;
        }

        public Criteria andOptionPictureNotIn(List<String> values) {
            addCriterion("optionPicture not in", values, "optionPicture");
            return (Criteria) this;
        }

        public Criteria andOptionPictureBetween(String value1, String value2) {
            addCriterion("optionPicture between", value1, value2, "optionPicture");
            return (Criteria) this;
        }

        public Criteria andOptionPictureNotBetween(String value1, String value2) {
            addCriterion("optionPicture not between", value1, value2, "optionPicture");
            return (Criteria) this;
        }

        public Criteria andOptientResultIsNull() {
            addCriterion("optientResult is null");
            return (Criteria) this;
        }

        public Criteria andOptientResultIsNotNull() {
            addCriterion("optientResult is not null");
            return (Criteria) this;
        }

        public Criteria andOptientResultEqualTo(String value) {
            addCriterion("optientResult =", value, "optientResult");
            return (Criteria) this;
        }

        public Criteria andOptientResultNotEqualTo(String value) {
            addCriterion("optientResult <>", value, "optientResult");
            return (Criteria) this;
        }

        public Criteria andOptientResultGreaterThan(String value) {
            addCriterion("optientResult >", value, "optientResult");
            return (Criteria) this;
        }

        public Criteria andOptientResultGreaterThanOrEqualTo(String value) {
            addCriterion("optientResult >=", value, "optientResult");
            return (Criteria) this;
        }

        public Criteria andOptientResultLessThan(String value) {
            addCriterion("optientResult <", value, "optientResult");
            return (Criteria) this;
        }

        public Criteria andOptientResultLessThanOrEqualTo(String value) {
            addCriterion("optientResult <=", value, "optientResult");
            return (Criteria) this;
        }

        public Criteria andOptientResultLike(String value) {
            addCriterion("optientResult like", value, "optientResult");
            return (Criteria) this;
        }

        public Criteria andOptientResultNotLike(String value) {
            addCriterion("optientResult not like", value, "optientResult");
            return (Criteria) this;
        }

        public Criteria andOptientResultIn(List<String> values) {
            addCriterion("optientResult in", values, "optientResult");
            return (Criteria) this;
        }

        public Criteria andOptientResultNotIn(List<String> values) {
            addCriterion("optientResult not in", values, "optientResult");
            return (Criteria) this;
        }

        public Criteria andOptientResultBetween(String value1, String value2) {
            addCriterion("optientResult between", value1, value2, "optientResult");
            return (Criteria) this;
        }

        public Criteria andOptientResultNotBetween(String value1, String value2) {
            addCriterion("optientResult not between", value1, value2, "optientResult");
            return (Criteria) this;
        }

        public Criteria andOptientStatusIsNull() {
            addCriterion("optientStatus is null");
            return (Criteria) this;
        }

        public Criteria andOptientStatusIsNotNull() {
            addCriterion("optientStatus is not null");
            return (Criteria) this;
        }

        public Criteria andOptientStatusEqualTo(Integer value) {
            addCriterion("optientStatus =", value, "optientStatus");
            return (Criteria) this;
        }

        public Criteria andOptientStatusNotEqualTo(Integer value) {
            addCriterion("optientStatus <>", value, "optientStatus");
            return (Criteria) this;
        }

        public Criteria andOptientStatusGreaterThan(Integer value) {
            addCriterion("optientStatus >", value, "optientStatus");
            return (Criteria) this;
        }

        public Criteria andOptientStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("optientStatus >=", value, "optientStatus");
            return (Criteria) this;
        }

        public Criteria andOptientStatusLessThan(Integer value) {
            addCriterion("optientStatus <", value, "optientStatus");
            return (Criteria) this;
        }

        public Criteria andOptientStatusLessThanOrEqualTo(Integer value) {
            addCriterion("optientStatus <=", value, "optientStatus");
            return (Criteria) this;
        }

        public Criteria andOptientStatusIn(List<Integer> values) {
            addCriterion("optientStatus in", values, "optientStatus");
            return (Criteria) this;
        }

        public Criteria andOptientStatusNotIn(List<Integer> values) {
            addCriterion("optientStatus not in", values, "optientStatus");
            return (Criteria) this;
        }

        public Criteria andOptientStatusBetween(Integer value1, Integer value2) {
            addCriterion("optientStatus between", value1, value2, "optientStatus");
            return (Criteria) this;
        }

        public Criteria andOptientStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("optientStatus not between", value1, value2, "optientStatus");
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