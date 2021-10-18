package com.gocashfree.cfsdk.sample.cfsdksample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.cashfree.pg.CFPaymentService;
import com.cashfree.pg.ui.gpay.GooglePayStatusListener;
import com.gocashfree.cfsdk.sample.cfsdksample.retrofit.GetDataService;
import com.gocashfree.cfsdk.sample.cfsdksample.Models.PaymentReq;
import com.gocashfree.cfsdk.sample.cfsdksample.Models.PaymentRes;
import com.gocashfree.cfsdk.sample.cfsdksample.retrofit.RetrofitClientInstance;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_NOTIFY_URL;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;
import static com.cashfree.pg.CFPaymentService.PARAM_PAYMENT_OPTION;
import static com.cashfree.pg.CFPaymentService.PARAM_UPI_VPA;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private String CustomerEmail,CustomerMobile;

    enum SeamlessMode {
        CARD, WALLET, NET_BANKING, UPI_COLLECT, PAY_PAL
    }

    SeamlessMode currentMode = SeamlessMode.CARD;

    private static final String TAG = "MainActivity";
    private String token="";
    private String notify_url="";
    private String OrderID="";
    private String amount="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Same request code for all payment APIs.
//        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
        Log.d(TAG, "API Response : ");
        //Prints all extras. Replace with app logic.
        if ( requestCode == CFPaymentService.REQ_CODE && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null)
            {
                showResponse(transformBundleToString(bundle));
            }
/*                for (String key : bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.d(TAG, key + " : " + bundle.getString(key));
                    }
                }*/
        }
    }

    public String transformBundleToString(Bundle bundle){
        String response="";
        for (String key : bundle.keySet())
        {
            response = response.concat(String.format("%s : %s\n",key,bundle.getString(key)));

        }
      return response;
    }

    public void onClick(View view) {


        /*
         * stage allows you to switch between sandboxed and production servers
         * for CashFree Payment Gateway. The possible values are
         *
         * 1. TEST: Use the Test server. You can use this service while integrating
         *      and testing the CashFree PG. No real money will be deducted from the
         *      cards and bank accounts you use this stage. This mode is thus ideal
         *      for use during the development. You can use the cards provided here
         *      while in this stage: https://docs.cashfree.com/docs/resources/#test-data
         *
         * 2. PROD: Once you have completed the testing and integration and successfully
         *      integrated the CashFree PG, use this value for stage variable. This will
         *      enable live transactions
         */
        String stage = "TEST";

        //Show the UI for doGPayPayment and phonePePayment only after checking if the apps are ready for payment
        if (view.getId() == R.id.phonePe_exists) {
            Toast.makeText(
                    MainActivity.this,
                    CFPaymentService.getCFPaymentServiceInstance().doesPhonePeExist(MainActivity.this, stage)+"",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (view.getId() == R.id.gpay_ready) {
            CFPaymentService.getCFPaymentServiceInstance().isGPayReadyForPayment(MainActivity.this, new GooglePayStatusListener() {
                @Override
                public void isReady() {
                    Toast.makeText(MainActivity.this, "Ready", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void isNotReady() {
                    Toast.makeText(MainActivity.this, "Not Ready", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        /*
         * token can be generated from your backend by calling cashfree servers. Please
         * check the documentation for details on generating the token.
         * READ THIS TO GENERATE TOKEN: https://bit.ly/2RGV3Pp
         */






       getPayToken(view,stage);

     //   Toast.makeText(MainActivity.this, ""+token, Toast.LENGTH_SHORT).show();



    }

    private Map<String, String> getInputParams(String orderID, String amount, String notify_url, String customerEmail, String customerMobile) {

        /*
         * appId will be available to you at CashFree Dashboard. This is a unique
         * identifier for your app. Please replace this appId with your appId.
         * Also, as explained below you will need to change your appId to prod
         * credentials before publishing your app.
         */
        //TODO: Get app id from your cashfee panel
        String appId = Constants.APPID;
        String orderId = orderID;
        String orderAmount = amount;
        String orderNote = "Test Order";
        String customerName = "John Doe";
        String customerPhone = CustomerMobile;
        String customeremail = CustomerEmail;

        Map<String, String> params = new HashMap<>();

        params.put(PARAM_APP_ID, appId);
        params.put(PARAM_ORDER_ID, orderId);
        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_ORDER_NOTE, orderNote);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL, customeremail);
        params.put(PARAM_ORDER_CURRENCY, "INR");
        params.put(PARAM_NOTIFY_URL, notify_url);
        return params;
    }

    private Map<String, String> getSeamlessCheckoutParams() {
        Map<String, String> params = getInputParams(OrderID, amount, notify_url, CustomerEmail, CustomerMobile);
        switch (currentMode) {
           /* case CARD:
                params.put(PARAM_PAYMENT_OPTION, "card");
                params.put(PARAM_CARD_NUMBER, "VALID_CARD_NUMBER");
                params.put(PARAM_CARD_YYYY, "YYYY");
                params.put(PARAM_CARD_MM, "MM");
                params.put(PARAM_CARD_HOLDER, "CARD_HOLDER_NAME");
                params.put(PARAM_CARD_CVV, "CVV");
                break;
            case WALLET:
                params.put(PARAM_PAYMENT_OPTION, "wallet");
                params.put(PARAM_WALLET_CODE, "4007"); // Put one of the wallet codes mentioned here https://dev.cashfree.com/payment-gateway/payments/wallets
                break;
            case NET_BANKING:
                params.put(PARAM_PAYMENT_OPTION, "nb");
                params.put(PARAM_BANK_CODE, "3333"); // Put one of the bank codes mentioned here https://dev.cashfree.com/payment-gateway/payments/netbanking
                break;*/
            case UPI_COLLECT:
                params.put(PARAM_PAYMENT_OPTION, "upi");
                params.put(PARAM_UPI_VPA, "8009393978@paytm");
                break;
       /*     case PAY_PAL:
                params.put(PARAM_PAYMENT_OPTION, "paypal");
                break;*/
        }
        return params;
    }


    private void getPayToken(View view, String stage) {


        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authentication", "dSrBhMIpTQFofjHcyq5C2sRgZ");

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        PaymentReq.MobileApplication mOBILEApplication = new PaymentReq.MobileApplication("519", "10","Cashfee","6NIuqE5hYXpjRUyG7C1a");

        final PaymentReq GetCartReq = new PaymentReq(mOBILEApplication);

        Call<PaymentRes> call = service.getpaytoken(header, GetCartReq);

        Log.d("kokres---", "---" + new Gson().toJson(GetCartReq));


        call.enqueue(new Callback<PaymentRes>() {
            @Override
            public void onResponse(Call<PaymentRes> call, Response<PaymentRes> response) {

                Log.d("kokres---", "---" + new Gson().toJson(response.body()));
                PaymentRes paymentRes = response.body();
                String msg = paymentRes.getMobileApplication().getMessage();

                if (paymentRes.getMobileApplication().getResponse().equals("Success")) {

                    token=paymentRes.getMobileApplication().getCfToken();

                    notify_url=paymentRes.getMobileApplication().getNotifyUrl();

                    OrderID=paymentRes.getMobileApplication().getOrderId();

                    amount=paymentRes.getMobileApplication().getTransactionAmount();
                    CustomerEmail=paymentRes.getMobileApplication().getEmail();
                    CustomerMobile=paymentRes.getMobileApplication().getMobile();


                    CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
                    cfPaymentService.setOrientation(0);
                    switch (view.getId()) {

                        /***
                         * This method handles the payment gateway invocation (web flow).
                         *
                         * @param context Android context of the calling activity
                         * @param params HashMap containing all the parameters required for creating a payment order
                         * @param token Provide the token for the transaction
                         * @param stage Identifies if test or production service needs to be invoked. Possible values:
                         *              PROD for production, TEST for testing.
                         * @param color1 Background color of the toolbar
                         * @param color2 text color and icon color of toolbar
                         * @param hideOrderId If true hides order Id from the toolbar
                         */
                        case R.id.web: {
                            cfPaymentService.doPayment(MainActivity.this, getInputParams(OrderID,amount,notify_url,CustomerEmail,CustomerMobile), token, stage, "#784BD2", "#FFFFFF", false);
//                 cfPaymentService.doPayment(MainActivity.this, params, token, stage);
                            break;
                        }
                        /***
                         * Same for all payment modes below.
                         *
                         * @param context Android context of the calling activity
                         * @param params HashMap containing all the parameters required for creating a payment order
                         * @param token Provide the token for the transaction
                         * @param stage Identifies if test or production service needs to be invoked. Possible values:
                         *              PROD for production, TEST for testing.
                         */
                        case R.id.upi: {
//                                cfPaymentService.selectUpiClient("com.google.android.apps.nbu.paisa.user");
                            cfPaymentService.upiPayment(MainActivity.this, getInputParams(OrderID, amount, notify_url, CustomerEmail, CustomerMobile), token, stage);
                            break;
                        }
                        case R.id.amazon: {
                            cfPaymentService.doAmazonPayment(MainActivity.this, getInputParams(OrderID, amount, notify_url, CustomerEmail, CustomerMobile), token, stage);
                            break;
                        }
                        case R.id.gpay: {
                            cfPaymentService.gPayPayment(MainActivity.this, getInputParams(OrderID, amount, notify_url, CustomerEmail, CustomerMobile), token, stage);
                            break;
                        }
                        case R.id.phonePe: {
                            cfPaymentService.phonePePayment(MainActivity.this, getInputParams(OrderID, amount, notify_url, CustomerEmail, CustomerMobile), token, stage);
                            break;
                        }
                        case R.id.web_seamless: {
                            cfPaymentService.doPayment(MainActivity.this, getSeamlessCheckoutParams(), token, stage);
                            break;
                        }
                    }



                } else if (paymentRes.getMobileApplication().getResponse().equals("Fail")) {



                }

            }


            @Override
            public void onFailure(Call<PaymentRes> call, Throwable t) {
                Log.d("kok-fail--", "---" + t.getMessage());

            }
        });

       // return token;

    }

    public void showResponse(String response){

        new MaterialAlertDialogBuilder(this)
                .setMessage(response)
                .setTitle("Payment Response")
                .setPositiveButton("OK",((dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })).show();

    }
}