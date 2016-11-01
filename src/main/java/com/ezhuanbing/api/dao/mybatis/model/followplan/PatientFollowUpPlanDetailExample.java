package com.ezhuanbing.api.dao.mybatis.model.followplan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientFollowUpPlanDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PatientFollowUpPlanDetailExample() {
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

        public Criteria andPlanIdIsNull() {
            addCriterion("planId is null");
            return (Criteria) this;
        }

        public Criteria andPlanIdIsNotNull() {
            addCriterion("planId is not null");
            return (Criteria) this;
        }

        public Criteria andPlanIdEqualTo(Integer value) {
            addCriterion("planId =", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotEqualTo(Integer value) {
            addCriterion("planId <>", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdGreaterThan(Integer value) {
            addCriterion("planId >", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("planId >=", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdLessThan(Integer value) {
            addCriterion("planId <", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdLessThanOrEqualTo(Integer value) {
            addCriterion("planId <=", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdIn(List<Integer> values) {
            addCriterion("planId in", values, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotIn(List<Integer> values) {
            addCriterion("planId not in", values, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdBetween(Integer value1, Integer value2) {
            addCriterion("planId between", value1, value2, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotBetween(Integer value1, Integer value2) {
            addCriterion("planId not between", value1, value2, "planId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNull() {
            addCriterion("templateId is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("templateId is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(Integer value) {
            addCriterion("templateId =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(Integer value) {
            addCriterion("templateId <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(Integer value) {
            addCriterion("templateId >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("templateId >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(Integer value) {
            addCriterion("templateId <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(Integer value) {
            addCriterion("templateId <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<Integer> values) {
            addCriterion("templateId in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<Integer> values) {
            addCriterion("templateId not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(Integer value1, Integer value2) {
            addCriterion("templateId between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("templateId not between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andQuestionnaireIdIsNull() {
            addCriterion("questionnaireId is null");
            return (Criteria) this;
        }

        public Criteria andQuestionnaireIdIsNotNull() {
            addCriterion("questionnaireId is not null");
            return (Criteria) this;
        }

        public Criteria andQuestionnaireIdEqualTo(Integer value) {
            addCriterion("questionnaireId =", value, "questionnaireId");
            return (Criteria) this;
        }

        public Criteria andQuestionnaireIdNotEqualTo(Integer value) {
            addCriterion("questionnaireId <>", value, "questionnaireId");
            return (Criteria) this;
        }

        public Criteria andQuestionnaireIdGreaterThan(Integer value) {
            addCriterion("questionnaireId >", value, "questionnaireId");
            return (Criteria) this;
        }

        public Criteria andQuestionnaireIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("questionnaireId >=", value, "questionnaireId");
            return (Criteria) this;
        }

        public Criteria andQuestionnaireIdLessThan(Integer value) {
            addCriterion("questionnaireId <", value, "questionnaireId");
            return (Criteria) this;
        }

        public Criteria andQuestionnaireIdLessThanOrEqualTo(Integer value) {
            addCriterion("questionnaireId <=", value, "questionnaireId");
            return (Criteria) this;
        }

        public Criteria andQuestionnaireIdIn(List<Integer> values) {
            addCriterion("questionnaireId in", values, "questionnaireId");
            return (Criteria) this;
        }

        public Criteria andQuestionnaireIdNotIn(List<Integer> values) {
            addCriterion("questionnaireId not in", values, "questionnaireId");
            return (Criteria) this;
        }

        public Criteria andQuestionnaireIdBetween(Integer value1, Integer value2) {
            addCriterion("questionnaireId between", value1, value2, "questionnaireId");
            return (Criteria) this;
        }

        public Criteria andQuestionnaireIdNotBetween(Integer value1, Integer value2) {
            addCriterion("questionnaireId not between", value1, value2, "questionnaireId");
            return (Criteria) this;
        }

        public Criteria andPlanMarkIsNull() {
            addCriterion("planMark is null");
            return (Criteria) this;
        }

        public Criteria andPlanMarkIsNotNull() {
            addCriterion("planMark is not null");
            return (Criteria) this;
        }

        public Criteria andPlanMarkEqualTo(String value) {
            addCriterion("planMark =", value, "planMark");
            return (Criteria) this;
        }

        public Criteria andPlanMarkNotEqualTo(String value) {
            addCriterion("planMark <>", value, "planMark");
            return (Criteria) this;
        }

        public Criteria andPlanMarkGreaterThan(String value) {
            addCriterion("planMark >", value, "planMark");
            return (Criteria) this;
        }

        public Criteria andPlanMarkGreaterThanOrEqualTo(String value) {
            addCriterion("planMark >=", value, "planMark");
            return (Criteria) this;
        }

        public Criteria andPlanMarkLessThan(String value) {
            addCriterion("planMark <", value, "planMark");
            return (Criteria) this;
        }

        public Criteria andPlanMarkLessThanOrEqualTo(String value) {
            addCriterion("planMark <=", value, "planMark");
            return (Criteria) this;
        }

        public Criteria andPlanMarkLike(String value) {
            addCriterion("planMark like", value, "planMark");
            return (Criteria) this;
        }

        public Criteria andPlanMarkNotLike(String value) {
            addCriterion("planMark not like", value, "planMark");
            return (Criteria) this;
        }

        public Criteria andPlanMarkIn(List<String> values) {
            addCriterion("planMark in", values, "planMark");
            return (Criteria) this;
        }

        public Criteria andPlanMarkNotIn(List<String> values) {
            addCriterion("planMark not in", values, "planMark");
            return (Criteria) this;
        }

        public Criteria andPlanMarkBetween(String value1, String value2) {
            addCriterion("planMark between", value1, value2, "planMark");
            return (Criteria) this;
        }

        public Criteria andPlanMarkNotBetween(String value1, String value2) {
            addCriterion("planMark not between", value1, value2, "planMark");
            return (Criteria) this;
        }

        public Criteria andIsActiveIsNull() {
            addCriterion("isActive is null");
            return (Criteria) this;
        }

        public Criteria andIsActiveIsNotNull() {
            addCriterion("isActive is not null");
            return (Criteria) this;
        }

        public Criteria andIsActiveEqualTo(String value) {
            addCriterion("isActive =", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotEqualTo(String value) {
            addCriterion("isActive <>", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveGreaterThan(String value) {
            addCriterion("isActive >", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveGreaterThanOrEqualTo(String value) {
            addCriterion("isActive >=", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveLessThan(String value) {
            addCriterion("isActive <", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveLessThanOrEqualTo(String value) {
            addCriterion("isActive <=", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveLike(String value) {
            addCriterion("isActive like", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotLike(String value) {
            addCriterion("isActive not like", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveIn(List<String> values) {
            addCriterion("isActive in", values, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotIn(List<String> values) {
            addCriterion("isActive not in", values, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveBetween(String value1, String value2) {
            addCriterion("isActive between", value1, value2, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotBetween(String value1, String value2) {
            addCriterion("isActive not between", value1, value2, "isActive");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIsNull() {
            addCriterion("planOrder is null");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIsNotNull() {
            addCriterion("planOrder is not null");
            return (Criteria) this;
        }

        public Criteria andPlanOrderEqualTo(Integer value) {
            addCriterion("planOrder =", value, "planOrder");
            return (Criteria) this;
        }

        public Criteria andPlanOrderNotEqualTo(Integer value) {
            addCriterion("planOrder <>", value, "planOrder");
            return (Criteria) this;
        }

        public Criteria andPlanOrderGreaterThan(Integer value) {
            addCriterion("planOrder >", value, "planOrder");
            return (Criteria) this;
        }

        public Criteria andPlanOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("planOrder >=", value, "planOrder");
            return (Criteria) this;
        }

        public Criteria andPlanOrderLessThan(Integer value) {
            addCriterion("planOrder <", value, "planOrder");
            return (Criteria) this;
        }

        public Criteria andPlanOrderLessThanOrEqualTo(Integer value) {
            addCriterion("planOrder <=", value, "planOrder");
            return (Criteria) this;
        }

        public Criteria andPlanOrderIn(List<Integer> values) {
            addCriterion("planOrder in", values, "planOrder");
            return (Criteria) this;
        }

        public Criteria andPlanOrderNotIn(List<Integer> values) {
            addCriterion("planOrder not in", values, "planOrder");
            return (Criteria) this;
        }

        public Criteria andPlanOrderBetween(Integer value1, Integer value2) {
            addCriterion("planOrder between", value1, value2, "planOrder");
            return (Criteria) this;
        }

        public Criteria andPlanOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("planOrder not between", value1, value2, "planOrder");
            return (Criteria) this;
        }

        public Criteria andPlanResultIsNull() {
            addCriterion("planResult is null");
            return (Criteria) this;
        }

        public Criteria andPlanResultIsNotNull() {
            addCriterion("planResult is not null");
            return (Criteria) this;
        }

        public Criteria andPlanResultEqualTo(Integer value) {
            addCriterion("planResult =", value, "planResult");
            return (Criteria) this;
        }

        public Criteria andPlanResultNotEqualTo(Integer value) {
            addCriterion("planResult <>", value, "planResult");
            return (Criteria) this;
        }

        public Criteria andPlanResultGreaterThan(Integer value) {
            addCriterion("planResult >", value, "planResult");
            return (Criteria) this;
        }

        public Criteria andPlanResultGreaterThanOrEqualTo(Integer value) {
            addCriterion("planResult >=", value, "planResult");
            return (Criteria) this;
        }

        public Criteria andPlanResultLessThan(Integer value) {
            addCriterion("planResult <", value, "planResult");
            return (Criteria) this;
        }

        public Criteria andPlanResultLessThanOrEqualTo(Integer value) {
            addCriterion("planResult <=", value, "planResult");
            return (Criteria) this;
        }

        public Criteria andPlanResultIn(List<Integer> values) {
            addCriterion("planResult in", values, "planResult");
            return (Criteria) this;
        }

        public Criteria andPlanResultNotIn(List<Integer> values) {
            addCriterion("planResult not in", values, "planResult");
            return (Criteria) this;
        }

        public Criteria andPlanResultBetween(Integer value1, Integer value2) {
            addCriterion("planResult between", value1, value2, "planResult");
            return (Criteria) this;
        }

        public Criteria andPlanResultNotBetween(Integer value1, Integer value2) {
            addCriterion("planResult not between", value1, value2, "planResult");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNull() {
            addCriterion("sendTime is null");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNotNull() {
            addCriterion("sendTime is not null");
            return (Criteria) this;
        }

        public Criteria andSendTimeEqualTo(Date value) {
            addCriterion("sendTime =", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotEqualTo(Date value) {
            addCriterion("sendTime <>", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThan(Date value) {
            addCriterion("sendTime >", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sendTime >=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThan(Date value) {
            addCriterion("sendTime <", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThanOrEqualTo(Date value) {
            addCriterion("sendTime <=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIn(List<Date> values) {
            addCriterion("sendTime in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotIn(List<Date> values) {
            addCriterion("sendTime not in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeBetween(Date value1, Date value2) {
            addCriterion("sendTime between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotBetween(Date value1, Date value2) {
            addCriterion("sendTime not between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andFeedbackTimeIsNull() {
            addCriterion("feedbackTime is null");
            return (Criteria) this;
        }

        public Criteria andFeedbackTimeIsNotNull() {
            addCriterion("feedbackTime is not null");
            return (Criteria) this;
        }

        public Criteria andFeedbackTimeEqualTo(Date value) {
            addCriterion("feedbackTime =", value, "feedbackTime");
            return (Criteria) this;
        }

        public Criteria andFeedbackTimeNotEqualTo(Date value) {
            addCriterion("feedbackTime <>", value, "feedbackTime");
            return (Criteria) this;
        }

        public Criteria andFeedbackTimeGreaterThan(Date value) {
            addCriterion("feedbackTime >", value, "feedbackTime");
            return (Criteria) this;
        }

        public Criteria andFeedbackTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("feedbackTime >=", value, "feedbackTime");
            return (Criteria) this;
        }

        public Criteria andFeedbackTimeLessThan(Date value) {
            addCriterion("feedbackTime <", value, "feedbackTime");
            return (Criteria) this;
        }

        public Criteria andFeedbackTimeLessThanOrEqualTo(Date value) {
            addCriterion("feedbackTime <=", value, "feedbackTime");
            return (Criteria) this;
        }

        public Criteria andFeedbackTimeIn(List<Date> values) {
            addCriterion("feedbackTime in", values, "feedbackTime");
            return (Criteria) this;
        }

        public Criteria andFeedbackTimeNotIn(List<Date> values) {
            addCriterion("feedbackTime not in", values, "feedbackTime");
            return (Criteria) this;
        }

        public Criteria andFeedbackTimeBetween(Date value1, Date value2) {
            addCriterion("feedbackTime between", value1, value2, "feedbackTime");
            return (Criteria) this;
        }

        public Criteria andFeedbackTimeNotBetween(Date value1, Date value2) {
            addCriterion("feedbackTime not between", value1, value2, "feedbackTime");
            return (Criteria) this;
        }

        public Criteria andPushMessageIsNull() {
            addCriterion("pushMessage is null");
            return (Criteria) this;
        }

        public Criteria andPushMessageIsNotNull() {
            addCriterion("pushMessage is not null");
            return (Criteria) this;
        }

        public Criteria andPushMessageEqualTo(String value) {
            addCriterion("pushMessage =", value, "pushMessage");
            return (Criteria) this;
        }

        public Criteria andPushMessageNotEqualTo(String value) {
            addCriterion("pushMessage <>", value, "pushMessage");
            return (Criteria) this;
        }

        public Criteria andPushMessageGreaterThan(String value) {
            addCriterion("pushMessage >", value, "pushMessage");
            return (Criteria) this;
        }

        public Criteria andPushMessageGreaterThanOrEqualTo(String value) {
            addCriterion("pushMessage >=", value, "pushMessage");
            return (Criteria) this;
        }

        public Criteria andPushMessageLessThan(String value) {
            addCriterion("pushMessage <", value, "pushMessage");
            return (Criteria) this;
        }

        public Criteria andPushMessageLessThanOrEqualTo(String value) {
            addCriterion("pushMessage <=", value, "pushMessage");
            return (Criteria) this;
        }

        public Criteria andPushMessageLike(String value) {
            addCriterion("pushMessage like", value, "pushMessage");
            return (Criteria) this;
        }

        public Criteria andPushMessageNotLike(String value) {
            addCriterion("pushMessage not like", value, "pushMessage");
            return (Criteria) this;
        }

        public Criteria andPushMessageIn(List<String> values) {
            addCriterion("pushMessage in", values, "pushMessage");
            return (Criteria) this;
        }

        public Criteria andPushMessageNotIn(List<String> values) {
            addCriterion("pushMessage not in", values, "pushMessage");
            return (Criteria) this;
        }

        public Criteria andPushMessageBetween(String value1, String value2) {
            addCriterion("pushMessage between", value1, value2, "pushMessage");
            return (Criteria) this;
        }

        public Criteria andPushMessageNotBetween(String value1, String value2) {
            addCriterion("pushMessage not between", value1, value2, "pushMessage");
            return (Criteria) this;
        }

        public Criteria andIsPayIsNull() {
            addCriterion("isPay is null");
            return (Criteria) this;
        }

        public Criteria andIsPayIsNotNull() {
            addCriterion("isPay is not null");
            return (Criteria) this;
        }

        public Criteria andIsPayEqualTo(Integer value) {
            addCriterion("isPay =", value, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayNotEqualTo(Integer value) {
            addCriterion("isPay <>", value, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayGreaterThan(Integer value) {
            addCriterion("isPay >", value, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayGreaterThanOrEqualTo(Integer value) {
            addCriterion("isPay >=", value, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayLessThan(Integer value) {
            addCriterion("isPay <", value, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayLessThanOrEqualTo(Integer value) {
            addCriterion("isPay <=", value, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayIn(List<Integer> values) {
            addCriterion("isPay in", values, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayNotIn(List<Integer> values) {
            addCriterion("isPay not in", values, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayBetween(Integer value1, Integer value2) {
            addCriterion("isPay between", value1, value2, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsPayNotBetween(Integer value1, Integer value2) {
            addCriterion("isPay not between", value1, value2, "isPay");
            return (Criteria) this;
        }

        public Criteria andIsLastRecordIsNull() {
            addCriterion("isLastRecord is null");
            return (Criteria) this;
        }

        public Criteria andIsLastRecordIsNotNull() {
            addCriterion("isLastRecord is not null");
            return (Criteria) this;
        }

        public Criteria andIsLastRecordEqualTo(Byte value) {
            addCriterion("isLastRecord =", value, "isLastRecord");
            return (Criteria) this;
        }

        public Criteria andIsLastRecordNotEqualTo(Byte value) {
            addCriterion("isLastRecord <>", value, "isLastRecord");
            return (Criteria) this;
        }

        public Criteria andIsLastRecordGreaterThan(Byte value) {
            addCriterion("isLastRecord >", value, "isLastRecord");
            return (Criteria) this;
        }

        public Criteria andIsLastRecordGreaterThanOrEqualTo(Byte value) {
            addCriterion("isLastRecord >=", value, "isLastRecord");
            return (Criteria) this;
        }

        public Criteria andIsLastRecordLessThan(Byte value) {
            addCriterion("isLastRecord <", value, "isLastRecord");
            return (Criteria) this;
        }

        public Criteria andIsLastRecordLessThanOrEqualTo(Byte value) {
            addCriterion("isLastRecord <=", value, "isLastRecord");
            return (Criteria) this;
        }

        public Criteria andIsLastRecordIn(List<Byte> values) {
            addCriterion("isLastRecord in", values, "isLastRecord");
            return (Criteria) this;
        }

        public Criteria andIsLastRecordNotIn(List<Byte> values) {
            addCriterion("isLastRecord not in", values, "isLastRecord");
            return (Criteria) this;
        }

        public Criteria andIsLastRecordBetween(Byte value1, Byte value2) {
            addCriterion("isLastRecord between", value1, value2, "isLastRecord");
            return (Criteria) this;
        }

        public Criteria andIsLastRecordNotBetween(Byte value1, Byte value2) {
            addCriterion("isLastRecord not between", value1, value2, "isLastRecord");
            return (Criteria) this;
        }

        public Criteria andAchiveTimeIsNull() {
            addCriterion("achiveTime is null");
            return (Criteria) this;
        }

        public Criteria andAchiveTimeIsNotNull() {
            addCriterion("achiveTime is not null");
            return (Criteria) this;
        }

        public Criteria andAchiveTimeEqualTo(Date value) {
            addCriterion("achiveTime =", value, "achiveTime");
            return (Criteria) this;
        }

        public Criteria andAchiveTimeNotEqualTo(Date value) {
            addCriterion("achiveTime <>", value, "achiveTime");
            return (Criteria) this;
        }

        public Criteria andAchiveTimeGreaterThan(Date value) {
            addCriterion("achiveTime >", value, "achiveTime");
            return (Criteria) this;
        }

        public Criteria andAchiveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("achiveTime >=", value, "achiveTime");
            return (Criteria) this;
        }

        public Criteria andAchiveTimeLessThan(Date value) {
            addCriterion("achiveTime <", value, "achiveTime");
            return (Criteria) this;
        }

        public Criteria andAchiveTimeLessThanOrEqualTo(Date value) {
            addCriterion("achiveTime <=", value, "achiveTime");
            return (Criteria) this;
        }

        public Criteria andAchiveTimeIn(List<Date> values) {
            addCriterion("achiveTime in", values, "achiveTime");
            return (Criteria) this;
        }

        public Criteria andAchiveTimeNotIn(List<Date> values) {
            addCriterion("achiveTime not in", values, "achiveTime");
            return (Criteria) this;
        }

        public Criteria andAchiveTimeBetween(Date value1, Date value2) {
            addCriterion("achiveTime between", value1, value2, "achiveTime");
            return (Criteria) this;
        }

        public Criteria andAchiveTimeNotBetween(Date value1, Date value2) {
            addCriterion("achiveTime not between", value1, value2, "achiveTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeIsNull() {
            addCriterion("nextTime is null");
            return (Criteria) this;
        }

        public Criteria andNextTimeIsNotNull() {
            addCriterion("nextTime is not null");
            return (Criteria) this;
        }

        public Criteria andNextTimeEqualTo(Date value) {
            addCriterion("nextTime =", value, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeNotEqualTo(Date value) {
            addCriterion("nextTime <>", value, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeGreaterThan(Date value) {
            addCriterion("nextTime >", value, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("nextTime >=", value, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeLessThan(Date value) {
            addCriterion("nextTime <", value, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeLessThanOrEqualTo(Date value) {
            addCriterion("nextTime <=", value, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeIn(List<Date> values) {
            addCriterion("nextTime in", values, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeNotIn(List<Date> values) {
            addCriterion("nextTime not in", values, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeBetween(Date value1, Date value2) {
            addCriterion("nextTime between", value1, value2, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeNotBetween(Date value1, Date value2) {
            addCriterion("nextTime not between", value1, value2, "nextTime");
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