package www.developerakshaym.apicall;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import www.developerakshaym.m_apicall.ApiCallBuilder;


public class MainActivity extends AppCompatActivity {

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
}
