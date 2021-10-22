package www.developerakshaym.apicall;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import www.developerakshaym.m_apicall.ApiCallBuilder;
import www.developerakshaym.m_apicall.Method;



/*public class MainActivity extends AppCompatActivity {

    private TextView tv_result;
    private String TimoutInSeconds="1000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_result=findViewById(R.id.tv_result);

    }

    private HashMap<String, String> getParam() {
        HashMap<String, String> param=new HashMap<>();
        return param;
    }

  public void onContinue(View view) {
        ApiCallBuilder.build(this)
                .isShowProgressBar(true)
                .setUrl("https://jsonplaceholder.typicode.com/todos/1")
                .setParam(getParam())//https://uniquehometutors.com/tutorapp/api/get_student_banners
                .setConnectionTimout(TimoutInSeconds)
                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        try {


                            Log.e("responsi", response);
                            tv_result.setText(response);
                        }catch (Exception e){
                            Toast.makeText(MainActivity.this, "exception: "+e, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void Failed(String error) {
                        Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                        tv_result.setText(error);
                    }
                });
    }
}*/

public class MainActivity extends AppCompatActivity {

    private TextView tv_result;
    private final String AWS_TOKEN="845bb279-d4ed-4be2-8183-a7a9436c5a02";
    private final String API_Key="e1364ce1-b205-4f2c-8d95-b2fe10e444ac";
    private final String Base_Url="https://api.loginradius.com/identity/v2/auth/access_token/validate";
    private final String Base_Url2="https://api.loginradius.com/identity/v2/auth/login";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_result=findViewById(R.id.tv_result);

    }

    public HashMap<String,String>getAWSHeader(){
        HashMap<String,String>param=new HashMap<>();
       // param.put("access_token",AWS_TOKEN);

           param.put("Content-Type","application/json");
     //   param.put("apiKey",API_Key);

        //   param.put("X-EBAY-C-MARKETPLACE-ID","EBAY_IN");
     //   param.put("X-EBAY-C-ENDUSERCTX","affiliateCampaignId=<ePNCampaignId>,affiliateReferenceId=<referenceId>");
        return param;
    }
    public HashMap<String, String> getParams(){
        HashMap<String,String>param=new HashMap<>();

        param.put("password","Akshay@123");

        param.put("email","akki@gmail.com");


        //   param.put("Content-Type","application/json");
        //   param.put("X-EBAY-C-MARKETPLACE-ID","EBAY_IN");
        //   param.put("X-EBAY-C-ENDUSERCTX","affiliateCampaignId=<ePNCampaignId>,affiliateReferenceId=<referenceId>");
        return param;
    }

    public void onContinue(View view) {
       ApiCallBuilder.build(this).setMethod(Method.GET)
                .setUrl(Base_Url)
                .setParam(getAWSHeader())
                .isShowProgressBar(true)
                .setTimeOut(10)
                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        Log.e("Response",response);
                        tv_result.setText(response);
                    }

                    @Override
                    public void Failed(String error) {
                        Log.e("error",error);
                        tv_result.setText(error);
                    }
                });

    }

    public void onLogin(View view) {
        Log.e("sdcsc", String.valueOf(getParams()));
//        OkHttpClient okHttpClient = new OkHttpClient()
//                .newBuilder()
//                .connectTimeout(120, TimeUnit.SECONDS)
//                .readTimeout(120, TimeUnit.SECONDS)
//                .writeTimeout(120, TimeUnit.SECONDS)
//                .build();


        String s= "{email: akki@gmail.com password: Akshay@123}";
        HashMap<String,String> map= new HashMap<>();
        map.put("",s);
        ApiCallBuilder.build(this).setMethod(Method.POST)
                .setUrl("https://api.loginradius.com/identity/v2/auth/login?apikey"+API_Key)
                .setHeader(getAWSHeader())
                .setParam(getParams())
                .isShowProgressBar(true)
                .setTimeOut(10)

                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        Log.e("Response",response);
                        tv_result.setText(response);
                    }

                    @Override
                    public void Failed(String error) {
                        Log.e("error",error);
                        tv_result.setText(error);
                        //ErrorResponse errorResponse = new Gson().fromJson(response.errorBody.charStream(),ErrorResponse.class)

                    }
                });
    }
}

