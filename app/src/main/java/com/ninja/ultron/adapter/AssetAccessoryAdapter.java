package com.ninja.ultron.adapter;

    import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ninja.ultron.R;
    import com.ninja.ultron.entity.AssetAccessoryEntity;

    import java.util.ArrayList;
    import java.util.List;

/**
     * Created by manoj on 17-05-2017.
     */

        public class AssetAccessoryAdapter extends ArrayAdapter<AssetAccessoryEntity>{

        List<AssetAccessoryEntity> assetAccessories = new ArrayList<>();
        AssetAccessoryEntity accessory;
        public AssetAccessoryAdapter(Context context, List<AssetAccessoryEntity> assetAccessories) {
            super(context, 0, assetAccessories);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            accessory = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_accessory_row, parent, false);
            }

            TextView tvAccessoryId = (TextView) convertView.findViewById(R.id.tvAccessoryId);
            TextView tvAccessoryName = (TextView) convertView.findViewById(R.id.tvAccessoryName);

            tvAccessoryId.setText(accessory.getId());
            tvAccessoryName.setText(accessory.getName());

            return convertView;
        }
    }


