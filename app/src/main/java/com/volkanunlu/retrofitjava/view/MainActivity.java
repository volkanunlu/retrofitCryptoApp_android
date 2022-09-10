package com.volkanunlu.retrofitjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.volkanunlu.retrofitjava.R;
import com.volkanunlu.retrofitjava.adapter.RecyclerViewAdapter;
import com.volkanunlu.retrofitjava.model.CryptoModel;
import com.volkanunlu.retrofitjava.service.CryptoAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<CryptoModel> cryptoModels;  //çektiğim verileri dizide tutucam.
    private String BASE_URL="https://raw.githubusercontent.com"; //verileri çekeceğim base url.
    Retrofit retrofit; //retrofitimi tanımladım.
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);

        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json  api key

        //Retrofit & JSON

        Gson gson=new GsonBuilder().setLenient().create();  //gson yarattık

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL) //url verdim
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //Rxjava kullanacağımı belirttim
                .addConverterFactory(GsonConverterFactory.create(gson)) //json verdim
                .build(); //inşa ettim.

        loadData();
    }

    private void loadData(){

      final CryptoAPI cryptoAPI=retrofit.create(CryptoAPI.class); //servisimi oluşturmuş oluyorum.

        compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())  //hangi threadde kayıt olma işlemnin yapılacağı
                .observeOn(AndroidSchedulers.mainThread())   //veriyi alınca, gözlemlenebilir objeyi nerede gözlemleyeceğiz
                .subscribe(this::handleResponse)); //



    }
    private void handleResponse(List<CryptoModel>cryptoModelList){

        cryptoModels=new ArrayList<>(cryptoModelList);

        //RecyclerView işlemleri veri geldikten sonra devam etmesi mantıklı.

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter=new RecyclerViewAdapter(cryptoModels);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();//kullan at objemizi temizledik.
    }
}