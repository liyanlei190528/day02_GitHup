package com.example.day_gouwuche.api;




import com.example.day_gouwuche.bean.CaiBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MyServer {
    public String url = "http://www.qubaobei.com/ios/cf/";
    @GET("dish_list.php?stage_id=1&limit=20&page=1")
    Observable<CaiBean> getData();
}
