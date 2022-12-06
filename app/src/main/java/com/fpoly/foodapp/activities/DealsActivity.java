package com.fpoly.foodapp.activities;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.fpoly.foodapp.R;
import com.fpoly.foodapp.Utility.NetworkChangeListener;
import com.fpoly.foodapp.adapters.Account_ViewPageAdapter;
import com.fpoly.foodapp.ui.account.Sub_Fragment.Fragment_NewDeals;
import com.fpoly.foodapp.ui.account.Sub_Fragment.Fragment_YourDeals;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DealsActivity extends AppCompatActivity {
    TabLayout tabLayout;
    Account_ViewPageAdapter viewPageAdapter;
    ViewPager2 viewPager2;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);

        tabLayout = findViewById(R.id.acc_TabLayout);
        viewPager2 = findViewById(R.id.acc_ViewPager);
        viewPageAdapter = new Account_ViewPageAdapter(this);

        viewPageAdapter.addFragment(new Fragment_NewDeals());
        viewPageAdapter.addFragment(new Fragment_YourDeals());

        viewPager2.setAdapter(viewPageAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("ƯU ĐÃI CỦA BẠN");
                        break;

                    case 1:
                        tab.setText("ƯU ĐÃI MỚI");
                        break;
                }
            }
        }).attach();
    }
    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}
