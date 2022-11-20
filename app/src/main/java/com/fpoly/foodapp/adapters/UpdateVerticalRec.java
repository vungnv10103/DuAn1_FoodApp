package com.fpoly.foodapp.adapters;

import com.fpoly.foodapp.modules.Category;
import com.fpoly.foodapp.modules.Food;
import com.fpoly.foodapp.modules.HomeVerModule;

import java.util.ArrayList;

public interface UpdateVerticalRec {
    public  void callBack(int pos , ArrayList<HomeVerModule> list);
    public  void callBackNew(int pos , ArrayList<Food> list);
}
