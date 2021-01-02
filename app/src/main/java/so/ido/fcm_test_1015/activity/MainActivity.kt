package so.ido.fcm_test_1015.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import so.ido.fcm_test_1015.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private var myToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true

        getMyToken()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://us-central1-fcmtest-f9449.cloudfunctions.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val loginService: LoginService = retrofit.create(LoginService::class.java)

        button.setOnClickListener {
            val id = send_ID.text.toString()
            val password = send_Password.text.toString()
            //  var myToken ="" // Pixel_2_API_25
            val yourToken = "f3EOnmanRMOLayUPPsxtcP:APA91bFp00L4J9_DFEyvGx0wn-JrUoT7osUVOSjBspp71GZBh2wELz2XOn9J2lOVl8d34jFhmVTIFqgTdKRfS2E0o_NwSDe8Z0TwKqcn-sEUq5i1p_NWzuWttr36f9SjgR6jxKUhEDyj" // Pixel_2_API_25_2

            // Emulator Pixel_2_API_25 =
            // e5EsHSJ0SD6QAteA3iQK_n:APA91bHH6PI4uBVO6BCLKQskiBqxvVOwltz7QCF4nMJb68jdYIDySQ5Sekqa6hCgGsgapTTIlqxvZ8m2PGrfJ6Jh9MXahJmN7Rs88wXcs3YpqngL1N06tb--pqZkEQ5FuQjyx-wazIaq

            // Emulator Pixel_2_API_25_2 =
            // f3EOnmanRMOLayUPPsxtcP:APA91bFp00L4J9_DFEyvGx0wn-JrUoT7osUVOSjBspp71GZBh2wELz2XOn9J2lOVl8d34jFhmVTIFqgTdKRfS2E0o_NwSDe8Z0TwKqcn-sEUq5i1p_NWzuWttr36f9SjgR6jxKUhEDyj


            loginService.requestLogin(id, password, myToken!!, yourToken).enqueue(object :
                Callback<Login> {
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    val dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("에러")
                    dialog.setMessage("로그인에 실패했습니다. : 에러메시지  : ${t.message}" )
                    dialog.show()

                }

                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    val dialog = AlertDialog.Builder(this@MainActivity)
                    if(response.code() == 200){
                        dialog.setTitle("성공")
                        dialog.setMessage("로그인에 성공했습니다.")
                        dialog.show()
                    }
                    else {
                        dialog.setTitle("에러")
                        dialog.setMessage("로그인에 실패했습니다. : 에러코드 : ${response.code()}")
                        dialog.show()

                    }
                }
            })
        }
    }

    private fun getMyToken() {
        Thread {
            try {
                FirebaseInstanceId.getInstance().instanceId
                        .addOnCompleteListener(OnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                Log.i("TAG", "getInstanceId failed", task.exception)
                                return@OnCompleteListener
                            }
                            myToken = task.result?.token!!
                        })
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()

    }

}