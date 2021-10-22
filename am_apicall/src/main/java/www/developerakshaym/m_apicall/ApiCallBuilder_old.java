package www.developerakshaym.m_apicall;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiCallBuilder_old {
    int cacheSize = 10 * 1024 * 1024;
    private static final String TAG = "ApiCallBuilder";
    private Request.Builder builder;
    private MultipartBody.Builder Multipartbuilder;
    private Context mContext;
    private ProgressDialogBuilder progress;
    private String mUrl="";
    private Method method=Method.GET;
    private HttpUrl.Builder httpBuilder;

    public static ApiCallBuilder_old build(Context context){
        return new ApiCallBuilder_old(context);
    }

    public ApiCallBuilder_old(Context mContext) {
        this.mContext = mContext;
        Multipartbuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder=new Request.Builder();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public ApiCallBuilder_old setMethod(Method method){
        this.method=method;
        return this;
    }
    public ApiCallBuilder_old setUrl(String url){
        this.mUrl=url;
        httpBuilder = HttpUrl.parse(url).newBuilder();
        return this;
    }

    public ApiCallBuilder_old isShowProgressBar(boolean b){
        if (b&&mContext!=null){
           progress=new ProgressDialogBuilder(mContext)
                    .setProgressStyle(ProgressStyle.STYLE_3);
        }
        return this;
    }

    public ApiCallBuilder_old isShowProgressBar(boolean b, ProgressStyle style){
        if (b&&mContext!=null){
           progress=new ProgressDialogBuilder(mContext)
                    .setProgressStyle(style);
        }
        return this;
    }
    public ApiCallBuilder_old setParam(HashMap<String,String> map){
        for (Map.Entry<String, String> entry : map.entrySet()) {
            switch (method){
                case POST:
                    Multipartbuilder.addFormDataPart(entry.getKey(),entry.getValue());
                    break;
                case GET:
                    httpBuilder.addQueryParameter(entry.getKey(),entry.getValue());
                    break;
            }

        }
        return this;
    }
    public ApiCallBuilder_old setFilePathArray(String key, ArrayList<String> filePaths){
        for (int i = 0; i < filePaths.size(); i++) {
            Uri resimUri = Uri.parse(filePaths.get(i));
            File file = new File(resimUri.getPath());
            Multipartbuilder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }
        return this;
    }
    public ApiCallBuilder_old setFileUriArray(String key, ArrayList<Uri> filePaths){
        for (int i = 0; i < filePaths.size(); i++) {
            File file = new File(filePaths.get(i).getPath());
            Multipartbuilder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }
        return this;
    }
    public ApiCallBuilder_old setFile(String key, Uri imageUri){
        if (imageUri!=null) {
            File file = new File(imageUri.getPath());
            Multipartbuilder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }
        return this;
    }
    public ApiCallBuilder_old setFile(String key, File file){
        if (file.exists()) {
            Multipartbuilder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }
        return this;
    }
    public ApiCallBuilder_old setFile(String key, String path){
        File file = new File(path);
        if (file.exists()) {
            Multipartbuilder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }
        return this;
    }
    public void execute(final onResponse callback) {
        if (mUrl.isEmpty()) {
            callback.Failed("Url not set.");
            return;
        }else if (!mUrl.contains("http")){
            callback.Failed("Expected URL scheme 'http' or 'https' but no colon was found");
            return;
        }
        if (progress!=null) progress.show();
        Request request;
        switch (method){
            case GET:
                builder.url(httpBuilder.build());
                request = builder.build();
                break;
            case POST:
                if (Multipartbuilder.getClass().getFields().length==0){
                    Multipartbuilder.addFormDataPart("","");
                }
                RequestBody requestBody = Multipartbuilder.build();
                request = new Request.Builder()
                        .url(mUrl)
                        .post(requestBody)
                        .build();
                break;
            default:
                callback.Failed("Unexpected value: " + method);
                throw new IllegalStateException("Unexpected value: " + method);
        }

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        if (progress != null)
                            progress.dismiss();
                        callback.Failed(e.getLocalizedMessage());
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        if (progress != null)
                            progress.dismiss();
                        if (response.isSuccessful()){
                            try {
                                callback.Success(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
            }
        });
    }
    public interface onResponse{
        void Success(String response);
        void Failed(String error);
    }
}
