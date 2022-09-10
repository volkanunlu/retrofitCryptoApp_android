package com.volkanunlu.retrofitjava.service;

import com.volkanunlu.retrofitjava.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    //GET , POST , UPDATE , DELETE

    //URL BASE --> www.website.com
    //GET -> price?=xxx
    //https://raw.githubusercontent.com/
    // atilsamancioglu/K21-JSONDataSet/master/crypto.json  api key

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json") //bu adrese bir get isteği yolladık.

    Observable<List<CryptoModel>> getData();

    //  Call<List<CryptoModel>> getData();  //Bana bir call ile bir liste şeklinde crytomodel gelecek diyorum. asenkron şekilde alıcaz bu veriyi.






}
