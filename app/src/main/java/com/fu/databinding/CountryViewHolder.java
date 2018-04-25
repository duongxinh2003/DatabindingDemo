package com.fu.databinding;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fu.databinding.databinding.ItemRecyclerViewBinding;

/**
 * Created by ADMIN on 4/25/2018.
 */

public class CountryViewHolder extends RecyclerView.ViewHolder {
    private ItemRecyclerViewBinding binding;
    public CountryViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public void bind(Country country) {
        binding.setCountry(country);
    }
}
