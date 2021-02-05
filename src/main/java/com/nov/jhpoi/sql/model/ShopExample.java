package com.nov.jhpoi.sql.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ShopExample() {
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

        public Criteria andShopidIsNull() {
            addCriterion("shopId is null");
            return (Criteria) this;
        }

        public Criteria andShopidIsNotNull() {
            addCriterion("shopId is not null");
            return (Criteria) this;
        }

        public Criteria andShopidEqualTo(String value) {
            addCriterion("shopId =", value, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidNotEqualTo(String value) {
            addCriterion("shopId <>", value, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidGreaterThan(String value) {
            addCriterion("shopId >", value, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidGreaterThanOrEqualTo(String value) {
            addCriterion("shopId >=", value, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidLessThan(String value) {
            addCriterion("shopId <", value, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidLessThanOrEqualTo(String value) {
            addCriterion("shopId <=", value, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidLike(String value) {
            addCriterion("shopId like", value, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidNotLike(String value) {
            addCriterion("shopId not like", value, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidIn(List<String> values) {
            addCriterion("shopId in", values, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidNotIn(List<String> values) {
            addCriterion("shopId not in", values, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidBetween(String value1, String value2) {
            addCriterion("shopId between", value1, value2, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidNotBetween(String value1, String value2) {
            addCriterion("shopId not between", value1, value2, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopnameidIsNull() {
            addCriterion("shopNameId is null");
            return (Criteria) this;
        }

        public Criteria andShopnameidIsNotNull() {
            addCriterion("shopNameId is not null");
            return (Criteria) this;
        }

        public Criteria andShopnameidEqualTo(String value) {
            addCriterion("shopNameId =", value, "shopnameid");
            return (Criteria) this;
        }

        public Criteria andShopnameidNotEqualTo(String value) {
            addCriterion("shopNameId <>", value, "shopnameid");
            return (Criteria) this;
        }

        public Criteria andShopnameidGreaterThan(String value) {
            addCriterion("shopNameId >", value, "shopnameid");
            return (Criteria) this;
        }

        public Criteria andShopnameidGreaterThanOrEqualTo(String value) {
            addCriterion("shopNameId >=", value, "shopnameid");
            return (Criteria) this;
        }

        public Criteria andShopnameidLessThan(String value) {
            addCriterion("shopNameId <", value, "shopnameid");
            return (Criteria) this;
        }

        public Criteria andShopnameidLessThanOrEqualTo(String value) {
            addCriterion("shopNameId <=", value, "shopnameid");
            return (Criteria) this;
        }

        public Criteria andShopnameidLike(String value) {
            addCriterion("shopNameId like", value, "shopnameid");
            return (Criteria) this;
        }

        public Criteria andShopnameidNotLike(String value) {
            addCriterion("shopNameId not like", value, "shopnameid");
            return (Criteria) this;
        }

        public Criteria andShopnameidIn(List<String> values) {
            addCriterion("shopNameId in", values, "shopnameid");
            return (Criteria) this;
        }

        public Criteria andShopnameidNotIn(List<String> values) {
            addCriterion("shopNameId not in", values, "shopnameid");
            return (Criteria) this;
        }

        public Criteria andShopnameidBetween(String value1, String value2) {
            addCriterion("shopNameId between", value1, value2, "shopnameid");
            return (Criteria) this;
        }

        public Criteria andShopnameidNotBetween(String value1, String value2) {
            addCriterion("shopNameId not between", value1, value2, "shopnameid");
            return (Criteria) this;
        }

        public Criteria andShopmoneyIsNull() {
            addCriterion("shopMoney is null");
            return (Criteria) this;
        }

        public Criteria andShopmoneyIsNotNull() {
            addCriterion("shopMoney is not null");
            return (Criteria) this;
        }

        public Criteria andShopmoneyEqualTo(String value) {
            addCriterion("shopMoney =", value, "shopmoney");
            return (Criteria) this;
        }

        public Criteria andShopmoneyNotEqualTo(String value) {
            addCriterion("shopMoney <>", value, "shopmoney");
            return (Criteria) this;
        }

        public Criteria andShopmoneyGreaterThan(String value) {
            addCriterion("shopMoney >", value, "shopmoney");
            return (Criteria) this;
        }

        public Criteria andShopmoneyGreaterThanOrEqualTo(String value) {
            addCriterion("shopMoney >=", value, "shopmoney");
            return (Criteria) this;
        }

        public Criteria andShopmoneyLessThan(String value) {
            addCriterion("shopMoney <", value, "shopmoney");
            return (Criteria) this;
        }

        public Criteria andShopmoneyLessThanOrEqualTo(String value) {
            addCriterion("shopMoney <=", value, "shopmoney");
            return (Criteria) this;
        }

        public Criteria andShopmoneyLike(String value) {
            addCriterion("shopMoney like", value, "shopmoney");
            return (Criteria) this;
        }

        public Criteria andShopmoneyNotLike(String value) {
            addCriterion("shopMoney not like", value, "shopmoney");
            return (Criteria) this;
        }

        public Criteria andShopmoneyIn(List<String> values) {
            addCriterion("shopMoney in", values, "shopmoney");
            return (Criteria) this;
        }

        public Criteria andShopmoneyNotIn(List<String> values) {
            addCriterion("shopMoney not in", values, "shopmoney");
            return (Criteria) this;
        }

        public Criteria andShopmoneyBetween(String value1, String value2) {
            addCriterion("shopMoney between", value1, value2, "shopmoney");
            return (Criteria) this;
        }

        public Criteria andShopmoneyNotBetween(String value1, String value2) {
            addCriterion("shopMoney not between", value1, value2, "shopmoney");
            return (Criteria) this;
        }

        public Criteria andShoptimeIsNull() {
            addCriterion("shopTime is null");
            return (Criteria) this;
        }

        public Criteria andShoptimeIsNotNull() {
            addCriterion("shopTime is not null");
            return (Criteria) this;
        }

        public Criteria andShoptimeEqualTo(Date value) {
            addCriterion("shopTime =", value, "shoptime");
            return (Criteria) this;
        }

        public Criteria andShoptimeNotEqualTo(Date value) {
            addCriterion("shopTime <>", value, "shoptime");
            return (Criteria) this;
        }

        public Criteria andShoptimeGreaterThan(Date value) {
            addCriterion("shopTime >", value, "shoptime");
            return (Criteria) this;
        }

        public Criteria andShoptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("shopTime >=", value, "shoptime");
            return (Criteria) this;
        }

        public Criteria andShoptimeLessThan(Date value) {
            addCriterion("shopTime <", value, "shoptime");
            return (Criteria) this;
        }

        public Criteria andShoptimeLessThanOrEqualTo(Date value) {
            addCriterion("shopTime <=", value, "shoptime");
            return (Criteria) this;
        }

        public Criteria andShoptimeIn(List<Date> values) {
            addCriterion("shopTime in", values, "shoptime");
            return (Criteria) this;
        }

        public Criteria andShoptimeNotIn(List<Date> values) {
            addCriterion("shopTime not in", values, "shoptime");
            return (Criteria) this;
        }

        public Criteria andShoptimeBetween(Date value1, Date value2) {
            addCriterion("shopTime between", value1, value2, "shoptime");
            return (Criteria) this;
        }

        public Criteria andShoptimeNotBetween(Date value1, Date value2) {
            addCriterion("shopTime not between", value1, value2, "shoptime");
            return (Criteria) this;
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
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