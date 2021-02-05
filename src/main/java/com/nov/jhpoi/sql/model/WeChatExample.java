package com.nov.jhpoi.sql.model;

import java.util.ArrayList;
import java.util.List;

public class WeChatExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WeChatExample() {
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

        public Criteria andWechatidIsNull() {
            addCriterion("wechatId is null");
            return (Criteria) this;
        }

        public Criteria andWechatidIsNotNull() {
            addCriterion("wechatId is not null");
            return (Criteria) this;
        }

        public Criteria andWechatidEqualTo(String value) {
            addCriterion("wechatId =", value, "wechatid");
            return (Criteria) this;
        }

        public Criteria andWechatidNotEqualTo(String value) {
            addCriterion("wechatId <>", value, "wechatid");
            return (Criteria) this;
        }

        public Criteria andWechatidGreaterThan(String value) {
            addCriterion("wechatId >", value, "wechatid");
            return (Criteria) this;
        }

        public Criteria andWechatidGreaterThanOrEqualTo(String value) {
            addCriterion("wechatId >=", value, "wechatid");
            return (Criteria) this;
        }

        public Criteria andWechatidLessThan(String value) {
            addCriterion("wechatId <", value, "wechatid");
            return (Criteria) this;
        }

        public Criteria andWechatidLessThanOrEqualTo(String value) {
            addCriterion("wechatId <=", value, "wechatid");
            return (Criteria) this;
        }

        public Criteria andWechatidLike(String value) {
            addCriterion("wechatId like", value, "wechatid");
            return (Criteria) this;
        }

        public Criteria andWechatidNotLike(String value) {
            addCriterion("wechatId not like", value, "wechatid");
            return (Criteria) this;
        }

        public Criteria andWechatidIn(List<String> values) {
            addCriterion("wechatId in", values, "wechatid");
            return (Criteria) this;
        }

        public Criteria andWechatidNotIn(List<String> values) {
            addCriterion("wechatId not in", values, "wechatid");
            return (Criteria) this;
        }

        public Criteria andWechatidBetween(String value1, String value2) {
            addCriterion("wechatId between", value1, value2, "wechatid");
            return (Criteria) this;
        }

        public Criteria andWechatidNotBetween(String value1, String value2) {
            addCriterion("wechatId not between", value1, value2, "wechatid");
            return (Criteria) this;
        }

        public Criteria andWechatnumIsNull() {
            addCriterion("wechatNum is null");
            return (Criteria) this;
        }

        public Criteria andWechatnumIsNotNull() {
            addCriterion("wechatNum is not null");
            return (Criteria) this;
        }

        public Criteria andWechatnumEqualTo(String value) {
            addCriterion("wechatNum =", value, "wechatnum");
            return (Criteria) this;
        }

        public Criteria andWechatnumNotEqualTo(String value) {
            addCriterion("wechatNum <>", value, "wechatnum");
            return (Criteria) this;
        }

        public Criteria andWechatnumGreaterThan(String value) {
            addCriterion("wechatNum >", value, "wechatnum");
            return (Criteria) this;
        }

        public Criteria andWechatnumGreaterThanOrEqualTo(String value) {
            addCriterion("wechatNum >=", value, "wechatnum");
            return (Criteria) this;
        }

        public Criteria andWechatnumLessThan(String value) {
            addCriterion("wechatNum <", value, "wechatnum");
            return (Criteria) this;
        }

        public Criteria andWechatnumLessThanOrEqualTo(String value) {
            addCriterion("wechatNum <=", value, "wechatnum");
            return (Criteria) this;
        }

        public Criteria andWechatnumLike(String value) {
            addCriterion("wechatNum like", value, "wechatnum");
            return (Criteria) this;
        }

        public Criteria andWechatnumNotLike(String value) {
            addCriterion("wechatNum not like", value, "wechatnum");
            return (Criteria) this;
        }

        public Criteria andWechatnumIn(List<String> values) {
            addCriterion("wechatNum in", values, "wechatnum");
            return (Criteria) this;
        }

        public Criteria andWechatnumNotIn(List<String> values) {
            addCriterion("wechatNum not in", values, "wechatnum");
            return (Criteria) this;
        }

        public Criteria andWechatnumBetween(String value1, String value2) {
            addCriterion("wechatNum between", value1, value2, "wechatnum");
            return (Criteria) this;
        }

        public Criteria andWechatnumNotBetween(String value1, String value2) {
            addCriterion("wechatNum not between", value1, value2, "wechatnum");
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