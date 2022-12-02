package com.fpoly.foodapp.adapters;

import com.fpoly.foodapp.modules.AddRecommendModule;
import com.fpoly.foodapp.modules.RecommendedModule;
import com.fpoly.foodapp.modules.HomeVerModule;

import java.util.ArrayList;

public interface UpdateVerticalRec {
    public  void callBack(int pos , ArrayList<HomeVerModule> list);
    public  void callBackNew(int pos , ArrayList<RecommendedModule> list, ArrayList<AddRecommendModule> listNew);
}
