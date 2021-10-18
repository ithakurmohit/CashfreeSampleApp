package com.gocashfree.cfsdk.sample.cfsdksample.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;




public class PaymentRes {

    @SerializedName("MOBILE_APPLICATION")
    @Expose
    private MobileApplication mobileApplication;

    /**
     * No args constructor for use in serialization
     *
     */
    public PaymentRes() {
    }

    /**
     *
     * @param mobileApplication
     */
    public PaymentRes(MobileApplication mobileApplication) {
        super();
        this.mobileApplication = mobileApplication;
    }

    public MobileApplication getMobileApplication() {
        return mobileApplication;
    }

    public void setMobileApplication(MobileApplication mobileApplication) {
        this.mobileApplication = mobileApplication;
    }
    public class MobileApplication {

        @SerializedName("response")
        @Expose
        private String response;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("currentBalance")
        @Expose
        private Double currentBalance;
        @SerializedName("OrderId")
        @Expose
        private String orderId;
        @SerializedName("CfToken")
        @Expose
        private String cfToken;
        @SerializedName("TransactionAmount")
        @Expose
        private String transactionAmount;
        @SerializedName("TransactionCharge")
        @Expose
        private Integer transactionCharge;
        @SerializedName("Mobile")
        @Expose
        private String mobile;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("NotifyUrl")
        @Expose
        private String notifyUrl;
        @SerializedName("TotalAmount")
        @Expose
        private Integer totalAmount;

        /**
         * No args constructor for use in serialization
         *
         */
        public MobileApplication() {
        }

        /**
         *
         * @param totalAmount
         * @param orderId
         * @param response
         * @param currentBalance
         * @param transactionAmount
         * @param mobile
         * @param notifyUrl
         * @param message
         * @param transactionCharge
         * @param cfToken
         * @param email
         */
        public MobileApplication(String response, String message, Double currentBalance, String orderId, String cfToken, String transactionAmount, Integer transactionCharge, String mobile, String email, String notifyUrl, Integer totalAmount) {
            super();
            this.response = response;
            this.message = message;
            this.currentBalance = currentBalance;
            this.orderId = orderId;
            this.cfToken = cfToken;
            this.transactionAmount = transactionAmount;
            this.transactionCharge = transactionCharge;
            this.mobile = mobile;
            this.email = email;
            this.notifyUrl = notifyUrl;
            this.totalAmount = totalAmount;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Double getCurrentBalance() {
            return currentBalance;
        }

        public void setCurrentBalance(Double currentBalance) {
            this.currentBalance = currentBalance;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getCfToken() {
            return cfToken;
        }

        public void setCfToken(String cfToken) {
            this.cfToken = cfToken;
        }

        public String getTransactionAmount() {
            return transactionAmount;
        }

        public void setTransactionAmount(String transactionAmount) {
            this.transactionAmount = transactionAmount;
        }

        public Integer getTransactionCharge() {
            return transactionCharge;
        }

        public void setTransactionCharge(Integer transactionCharge) {
            this.transactionCharge = transactionCharge;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNotifyUrl() {
            return notifyUrl;
        }

        public void setNotifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
        }

        public Integer getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Integer totalAmount) {
            this.totalAmount = totalAmount;
        }

    }
}