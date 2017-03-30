package com.zhang.zs.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhang.zs.news.R;
import com.zhang.zs.news.bean.ShoppingCart;
import com.zhang.zs.news.utils.CartProvider;
import com.zhang.zs.news.view.AddAndSubView;

import java.util.List;

/**
 * Created by zs on 2016/7/11.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewhodle> {

    private final Context context;
    private final List<ShoppingCart> datas;
    private final CheckBox checkbox1;
    private final TextView prices;

    private CartProvider provider;

    public CartAdapter(Context context, List<ShoppingCart> allData, CheckBox checkbox, TextView prices) {

        this.context = context;
        this.datas = allData;
        this.checkbox1 = checkbox;
        this.prices = prices;

        provider = new CartProvider(context);

        //设置总的价格

        showTotalPrice();
        checkAll_none();

        setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                ShoppingCart shoppingCart = datas.get(position);

                shoppingCart.setIsChecked(!shoppingCart.isChecked());

                notifyItemChanged(position);


                checkAll_none();

                showTotalPrice();


            }
        });

        this.checkbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                all_none(checkbox1.isChecked());


                showTotalPrice();
            }
        });

    }

    public void all_none(boolean b) {

        for (int i = 0; i < datas.size(); i++) {

            ShoppingCart cart = datas.get(i);
            cart.setIsChecked(b);
            notifyItemChanged(i);
        }
    }


    /**
     * 删除数据
     */
    public void deleteItem() {
        for (int i = 0; i < datas.size(); i++) {
            ShoppingCart cart = datas.get(i);

            if (cart.isChecked()) {
                datas.remove(i);

                //删除本地保存的
                provider.deleteCarts(cart);
                notifyItemRemoved(i);
            }
        }

    }

    public void checkAll_none() {

        int num = 0;
        for (int i = 0; i < datas.size(); i++) {
            ShoppingCart cart = datas.get(i);
            if (!cart.isChecked()) {
                checkbox1.setChecked(false);
            } else {
                num += 1;
            }

        }

        if (num == datas.size()) {
            checkbox1.setChecked(true);
        }

    }

    private void showTotalPrice() {


        if (prices != null) {

            prices.setText("合计￥" + getTotalPrice());
        }

    }

    private float getTotalPrice() {

        float num = 0;

        if (datas != null && datas.size() > 0) {

            for (int i = 0; i < datas.size(); i++) {

                ShoppingCart cart = datas.get(i);

                if (cart.isChecked()) {
                    num = num + cart.getCount() * cart.getPrice();

                }

            }

        }

        return num;
    }


    @Override
    public Viewhodle onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_cart, null);
        return new Viewhodle(view);
    }

    @Override
    public void onBindViewHolder(Viewhodle holder, final int position) {

        holder.checkbox.setChecked(datas.get(position).isChecked());
        Glide.with(context).load(datas.get(position).getImgUrl()).into(holder.imageview);
        holder.textview_name.setText(datas.get(position).getName());
        holder.textview_prices.setText(datas.get(position).getPrice() + "");
        holder.add_sub.setCurrentNum(datas.get(position).getCount());

        holder.add_sub.setOnButtonClickListener(new AddAndSubView.OnButtonClickListener() {
            @Override
            public void onSubNumberClick(View view, int value) {

                ShoppingCart cart = datas.get(position);

                cart.setCount(value);

                provider.upCarts(cart);

                showTotalPrice();

            }

            @Override
            public void onAddNumberClick(View view, int value) {
                ShoppingCart cart = datas.get(position);

                cart.setCount(value);

                provider.upCarts(cart);

                showTotalPrice();
            }
        });


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class Viewhodle extends RecyclerView.ViewHolder {


        private CheckBox checkbox;
        private ImageView imageview;
        private TextView textview_name;
        private TextView textview_prices;
        private AddAndSubView add_sub;

        public Viewhodle(View itemView) {
            super(itemView);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            imageview = (ImageView) itemView.findViewById(R.id.imageview);
            textview_name = (TextView) itemView.findViewById(R.id.textview_name);
            textview_prices = (TextView) itemView.findViewById(R.id.textview_prices);
            add_sub = (AddAndSubView) itemView.findViewById(R.id.add_sub);


            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getLayoutPosition());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getLayoutPosition());
                    }
                }
            });

        }


    }

    /**
     * 点击某一条的监听
     */
    public interface OnItemClickListener {
        /**
         * 当某一条被点击的时候回调这个方法
         *
         * @param view
         * @param position
         */
        public void onItemClick(View view, int position);
    }

    private OnItemClickListener itemClickListener;

    /**
     * 设置点击某一条的监听
     *
     * @param itemClickListener
     */
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
