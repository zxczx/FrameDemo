package com.example.framedemo.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.framedemo.R;
import com.example.framedemo.ui.home.HomeFragment;
import com.example.framedemo.ui.my.MyFragment;

import org.jetbrains.annotations.NotNull;

public class MainPagerAdapter extends FragmentStateAdapter {


    private static final int[] TAB_ICONS = {R.drawable.tab_bottom_home, R.drawable.tab_bottom_my};
    private static final int[] TAB_TITLE = {R.string.home, R.string.my};

    public MainPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return MyFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return TAB_ICONS.length;
    }

    public static View getTabView(Context context, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bottom_tab, null);
        TextView title = view.findViewById(R.id.title);
        ImageView icon = view.findViewById(R.id.icon);
        title.setText(TAB_TITLE[position]);
        icon.setImageResource(TAB_ICONS[position]);
        return view;
    }
}
