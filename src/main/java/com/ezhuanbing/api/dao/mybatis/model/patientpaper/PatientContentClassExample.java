package com.ezhuanbing.api.dao.mybatis.model.patientpaper;

import java.util.ArrayList;
import java.util.List;

public class PatientContentClassExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PatientContentClassExample() {
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

        public Criteria andPatientPaperIdIsNull() {
            addCriterion("patientPaperId is null");
            return (Criteria) this;
        }

        public Criteria andPatientPaperIdIsNotNull() {
            addCriterion("patientPaperId is not null");
            return (Criteria) this;
        }

        public Criteria andPatientPaperIdEqualTo(Integer value) {
            addCriterion("patientPaperId =", value, "patientPaperId");
            return (Criteria) this;
        }

        public Criteria andPatientPaperIdNotEqualTo(Integer value) {
            addCriterion("patientPaperId <>", value, "patientPaperId");
            return (Criteria) this;
        }

        public Criteria andPatientPaperIdGreaterThan(Integer value) {
            addCriterion("patientPaperId >", value, "patientPaperId");
            return (Criteria) this;
        }

        public Criteria andPatientPaperIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("patientPaperId >=", value, "patientPaperId");
            return (Criteria) this;
        }

        public Criteria andPatientPaperIdLessThan(Integer value) {
            addCriterion("patientPaperId <", value, "patientPaperId");
            return (Criteria) this;
        }

        public Criteria andPatientPaperIdLessThanOrEqualTo(Integer value) {
            addCriterion("patientPaperId <=", value, "patientPaperId");
            return (Criteria) this;
        }

        public Criteria andPatientPaperIdIn(List<Integer> values) {
            addCriterion("patientPaperId in", values, "patientPaperId");
            return (Criteria) this;
        }

        public Criteria andPatientPaperIdNotIn(List<Integer> values) {
            addCriterion("patientPaperId not in", values, "patientPaperId");
            return (Criteria) this;
        }

        public Criteria andPatientPaperIdBetween(Integer value1, Integer value2) {
            addCriterion("patientPaperId between", value1, value2, "patientPaperId");
            return (Criteria) this;
        }

        public Criteria andPatientPaperIdNotBetween(Integer value1, Integer value2) {
            addCriterion("patientPaperId not between", value1, value2, "patientPaperId");
            return (Criteria) this;
        }

        public Criteria andContentClassTemplatesIdIsNull() {
            addCriterion("contentClassTemplatesId is null");
            return (Criteria) this;
        }

        public Criteria andContentClassTemplatesIdIsNotNull() {
            addCriterion("contentClassTemplatesId is not null");
            return (Criteria) this;
        }

        public Criteria andContentClassTemplatesIdEqualTo(Integer value) {
            addCriterion("contentClassTemplatesId =", value, "contentClassTemplatesId");
            return (Criteria) this;
        }

        public Criteria andContentClassTemplatesIdNotEqualTo(Integer value) {
            addCriterion("contentClassTemplatesId <>", value, "contentClassTemplatesId");
            return (Criteria) this;
        }

        public Criteria andContentClassTemplatesIdGreaterThan(Integer value) {
            addCriterion("contentClassTemplatesId >", value, "contentClassTemplatesId");
            return (Criteria) this;
        }

        public Criteria andContentClassTemplatesIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("contentClassTemplatesId >=", value, "contentClassTemplatesId");
            return (Criteria) this;
        }

        public Criteria andContentClassTemplatesIdLessThan(Integer value) {
            addCriterion("contentClassTemplatesId <", value, "contentClassTemplatesId");
            return (Criteria) this;
        }

        public Criteria andContentClassTemplatesIdLessThanOrEqualTo(Integer value) {
            addCriterion("contentClassTemplatesId <=", value, "contentClassTemplatesId");
            return (Criteria) this;
        }

        public Criteria andContentClassTemplatesIdIn(List<Integer> values) {
            addCriterion("contentClassTemplatesId in", values, "contentClassTemplatesId");
            return (Criteria) this;
        }

        public Criteria andContentClassTemplatesIdNotIn(List<Integer> values) {
            addCriterion("contentClassTemplatesId not in", values, "contentClassTemplatesId");
            return (Criteria) this;
        }

        public Criteria andContentClassTemplatesIdBetween(Integer value1, Integer value2) {
            addCriterion("contentClassTemplatesId between", value1, value2, "contentClassTemplatesId");
            return (Criteria) this;
        }

        public Criteria andContentClassTemplatesIdNotBetween(Integer value1, Integer value2) {
            addCriterion("contentClassTemplatesId not between", value1, value2, "contentClassTemplatesId");
            return (Criteria) this;
        }

        public Criteria andClassNameIsNull() {
            addCriterion("className is null");
            return (Criteria) this;
        }

        public Criteria andClassNameIsNotNull() {
            addCriterion("className is not null");
            return (Criteria) this;
        }

        public Criteria andClassNameEqualTo(String value) {
            addCriterion("className =", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotEqualTo(String value) {
            addCriterion("className <>", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameGreaterThan(String value) {
            addCriterion("className >", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameGreaterThanOrEqualTo(String value) {
            addCriterion("className >=", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLessThan(String value) {
            addCriterion("className <", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLessThanOrEqualTo(String value) {
            addCriterion("className <=", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLike(String value) {
            addCriterion("className like", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotLike(String value) {
            addCriterion("className not like", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameIn(List<String> values) {
            addCriterion("className in", values, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotIn(List<String> values) {
            addCriterion("className not in", values, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameBetween(String value1, String value2) {
            addCriterion("className between", value1, value2, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotBetween(String value1, String value2) {
            addCriterion("className not between", value1, value2, "className");
            return (Criteria) this;
        }

        public Criteria andClassSortIsNull() {
            addCriterion("classSort is null");
            return (Criteria) this;
        }

        public Criteria andClassSortIsNotNull() {
            addCriterion("classSort is not null");
            return (Criteria) this;
        }

        public Criteria andClassSortEqualTo(Integer value) {
            addCriterion("classSort =", value, "classSort");
            return (Criteria) this;
        }

        public Criteria andClassSortNotEqualTo(Integer value) {
            addCriterion("classSort <>", value, "classSort");
            return (Criteria) this;
        }

        public Criteria andClassSortGreaterThan(Integer value) {
            addCriterion("classSort >", value, "classSort");
            return (Criteria) this;
        }

        public Criteria andClassSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("classSort >=", value, "classSort");
            return (Criteria) this;
        }

        public Criteria andClassSortLessThan(Integer value) {
            addCriterion("classSort <", value, "classSort");
            return (Criteria) this;
        }

        public Criteria andClassSortLessThanOrEqualTo(Integer value) {
            addCriterion("classSort <=", value, "classSort");
            return (Criteria) this;
        }

        public Criteria andClassSortIn(List<Integer> values) {
            addCriterion("classSort in", values, "classSort");
            return (Criteria) this;
        }

        public Criteria andClassSortNotIn(List<Integer> values) {
            addCriterion("classSort not in", values, "classSort");
            return (Criteria) this;
        }

        public Criteria andClassSortBetween(Integer value1, Integer value2) {
            addCriterion("classSort between", value1, value2, "classSort");
            return (Criteria) this;
        }

        public Criteria andClassSortNotBetween(Integer value1, Integer value2) {
            addCriterion("classSort not between", value1, value2, "classSort");
            return (Criteria) this;
        }

        public Criteria andClassStatusIsNull() {
            addCriterion("classStatus is null");
            return (Criteria) this;
        }

        public Criteria andClassStatusIsNotNull() {
            addCriterion("classStatus is not null");
            return (Criteria) this;
        }

        public Criteria andClassStatusEqualTo(Integer value) {
            addCriterion("classStatus =", value, "classStatus");
            return (Criteria) this;
        }

        public Criteria andClassStatusNotEqualTo(Integer value) {
            addCriterion("classStatus <>", value, "classStatus");
            return (Criteria) this;
        }

        public Criteria andClassStatusGreaterThan(Integer value) {
            addCriterion("classStatus >", value, "classStatus");
            return (Criteria) this;
        }

        public Criteria andClassStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("classStatus >=", value, "classStatus");
            return (Criteria) this;
        }

        public Criteria andClassStatusLessThan(Integer value) {
            addCriterion("classStatus <", value, "classStatus");
            return (Criteria) this;
        }

        public Criteria andClassStatusLessThanOrEqualTo(Integer value) {
            addCriterion("classStatus <=", value, "classStatus");
            return (Criteria) this;
        }

        public Criteria andClassStatusIn(List<Integer> values) {
            addCriterion("classStatus in", values, "classStatus");
            return (Criteria) this;
        }

        public Criteria andClassStatusNotIn(List<Integer> values) {
            addCriterion("classStatus not in", values, "classStatus");
            return (Criteria) this;
        }

        public Criteria andClassStatusBetween(Integer value1, Integer value2) {
            addCriterion("classStatus between", value1, value2, "classStatus");
            return (Criteria) this;
        }

        public Criteria andClassStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("classStatus not between", value1, value2, "classStatus");
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