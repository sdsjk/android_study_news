package com.zhang.zs.refreshlistview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<String> datas;

    public RecyclerViewAdapter(Context context,ArrayList<String> datas){
        this.context = context;
        this.datas = datas;
    }

    /**
     * 相当于getView方法里面的创建ViewHoder部分
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item,null);
        return new ViewHolder(itemView);
    }

    /**
     * 相当于getView方法的绑定数据部分
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //1.得到数据
        String conten =  datas.get(position);
        //2.设置数据
        holder.tv_title.setText(conten);
        holder.iv_icon .setImageResource(R.mipmap.ic_launcher);
    }

    /**
     * 得到总条数
     * @return
     */
    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 添加一条数据
     * @param position
     * @param content
     */
    public void addData(int position, String content) {
        datas.add(position,content);
        /**
         * 添加对应的数据跟新
         */
        notifyItemInserted(position);
    }

    public void deletData(int position) {
        datas.remove(position);
        /**
         * 删除对应的更新
         */
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_icon;
        private TextView tv_title;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "datas.get(getLayoutPosition())=="+datas.get(getLayoutPosition()), Toast.LENGTH_SHORT).show();
                }
            });
            tv_title = (TextView) itemView.findViewById(R.id.tv_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(v,getLayoutPosition(),datas.get(getLayoutPosition()));
                    }

                }
            });
        }
    }


    /**
     * 点击某条的监听
     */
    public interface OnItemClickListener{

        /**
         * 当点击某个的时候回调这个方法
         * @param view
         * @param position
         * @param content
         */
        public void onItemClick(View view,int position,String content);
    }

    private OnItemClickListener onItemClickListener;

    /**
     * 监听点击某条的监听
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
