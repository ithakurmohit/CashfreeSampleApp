package com.gocashfree.cfsdk.sample.cfsdksample.retrofit;

import com.gocashfree.cfsdk.sample.cfsdksample.Models.PaymentReq;
import com.gocashfree.cfsdk.sample.cfsdksample.Models.PaymentRes;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface GetDataService {

@POST("/mobile/pg/CashfreeToken")
Call<PaymentRes> getpaytoken(@HeaderMap HashMap<String,String>hashMap, @Body PaymentReq call_backreq);

}
