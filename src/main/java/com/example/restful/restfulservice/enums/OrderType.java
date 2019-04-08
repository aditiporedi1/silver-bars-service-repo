package com.example.restful.restfulservice.enums;

public enum OrderType {

        SELL("SELL"),
        BUY("BUY");

        private final String value;

        private OrderType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

}
