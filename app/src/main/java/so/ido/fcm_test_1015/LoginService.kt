package so.ido.fcm_test_1015

import retrofit2.Call
import retrofit2.http.*

interface LoginService {

    @FormUrlEncoded
    @POST("/sendNotification/")
    fun requestLogin(
        @Field("user_id") user_id:String,
        @Field("user_pw") user_pw:String,
        @Field("myToken") myToken:String,
        @Field("yourToken") yourToken:String
    ) : Call<Login>




}