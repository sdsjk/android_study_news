package com.zhang.zs.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhang.zs.news.R;
import com.zhang.zs.news.bean.ShopBean;
import com.zhang.zs.news.bean.ShoppingCart;
import com.zhang.zs.news.utils.CartProvider;

import java.util.List;

/**
 * Created by zs on 2016/7/10.
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHodle> {


    private final Context context;
    private List<ShopBean.ListBean> list;

    private CartProvider provider;

    public ShopAdapter(Context context, List<ShopBean.ListBean> list) {

        this.context = context;
        this.list = list;
        provider = new CartProvider(context);
    }

    @Override
    public ViewHodle onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.item_shop, null);
        return new ViewHodle(view);
    }

    @Override
    public void onBindViewHolder(ViewHodle holder, int position) {

        holder.tv_name.setText(list.get(position).getName());
        holder.tv_price.setText(list.get(position).getPrice() + "");

        Glide
                .with(context)
                .load(list.get(position).getImgUrl())
                .into(holder.iv_icon);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeAllData() {
        list.clear();
        notifyItemRangeRemoved(0, list.size());
    }

    public void addNewData(List<ShopBean.ListBean> data) {
        list = data;
        notifyItemRangeChanged(0, list.size());
    }

    public int getDataCount() {
        return list.size();
    }

    public void addData(int dataCount, List<ShopBean.ListBean> data) {
        list.addAll(dataCount, data);

        notifyItemRangeChanged(dataCount, data.size());
    }


    class ViewHodle extends RecyclerView.ViewHolder {


        private ImageView iv_icon;
        private TextView tv_name;
        private TextView tv_price;
        private Button btn_buy;

        public ViewHodle(final View itemView) {
            super(itemView);

            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            btn_buy = (Button) itemView.findViewById(R.id.btn_buy);
            btn_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "" + tv_price.getText(), Toast.LENGTH_SHORT).show();

                    ShopBean.ListBean listBean = list.get(getLayoutPosition());

                    ShoppingCart cart = provider.conversionData(listBean);

                    provider.upCarts(cart);
                }
            });

        }
    }
}
