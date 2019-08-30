package com.yascn.smartpark.base;

public class BaseModel {
    public void onDestory(){}
   public  DataResultListener dataResultListener;
  public  interface DataResultListener<T>{
        void dataSuccess(T t);
        void setQueryStatus(String status);

    }
}
