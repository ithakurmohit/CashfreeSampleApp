package com.gocashfree.cfsdk.sample.cfsdksample.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;




public class PaymentReq {

    @SerializedName("MOBILE_APPLICATION")
    @Expose
    private MobileApplication mobileApplication;

    /**
     * No args constructor for use in serialization
     *
     */
    public PaymentReq() {
    }

    /**
     *
     * @param mobileApplication
     */
    public PaymentReq(MobileApplication mobileApplication) {
        super();
        this.mobileApplication = mobileApplication;
    }

    public MobileApplication getMobileApplication() {
        return mobileApplication;
    }

    public void setMobileApplication(MobileApplication mobileApplication) {
        this.mobileApplication = mobileApplication;
    }
    public static class MobileApplication {

        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("upiApp")
        @Expose
        private String upiApp;
        @SerializedName("tokenNumber")
        @Expose
        private String tokenNumber;

        /**
         * No args constructor for use in serialization
         *
         */
        public MobileApplication() {
        }

        /**
         *
         * @param upiApp
         * @param amount
         * @param tokenNumber
         * @param userId
         */
        public MobileApplication(String userId, String amount, String upiApp, String tokenNumber) {
            super();
            this.userId = userId;
            this.amount = amount;
            this.upiApp = upiApp;
            this.tokenNumber = tokenNumber;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getUpiApp() {
            return upiApp;
        }

        public void setUpiApp(String upiApp) {
            this.upiApp = upiApp;
        }

        public String getTokenNumber() {
            return tokenNumber;
        }

        public void setTokenNumber(String tokenNumber) {
            this.tokenNumber = tokenNumber;
        }

    }
}