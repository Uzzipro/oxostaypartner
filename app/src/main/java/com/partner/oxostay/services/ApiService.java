package com.partner.oxostay.services;

import com.partner.oxostay.utils.Constants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers({"Authorization: key=AAAAxYo5PE4:APA91bEUdYF095bI4oHeG029PtptRsw2YlSATJDbSdSC5trqbUV11LI5SYx0eQqSlUCG5TNqCfhv_tH3iPjhuIs6WJ7pS-DaTB3gY0IW4uu89Sp-tJPPdFzbGvnYW48xaZRqdN7r5dhS",
            "Content-Type:application/json"})
    @POST("fcm/send")
    Call<ResponseBody> sendChatNotification(@Body RequestNotification requestNotificaton);
}
