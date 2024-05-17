package com.itstep.myrestapp;
import android.app.Application;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Создайте OkHttpClient с интерсептором для добавления заголовков
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("User-Agent", "your-user-agent")
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        // Настройте Picasso для использования OkHttp3Downloader
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(client))
                .build();

        // Установите созданный экземпляр Picasso как singleton instance
        Picasso.setSingletonInstance(picasso);
    }
}
