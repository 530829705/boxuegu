package cn.edu.gdmec.android.boxuegu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.activity.ExercisesDetailActivity;
import cn.edu.gdmec.android.boxuegu.bean.ExercisesBean;

/**
 * Created by student on 17/12/25.
 */

public class ExercisesAdapter extends BaseAdapter {
    private Context mContext;
    private List<ExercisesBean> ebl;
    public ExercisesAdapter(Context context){
        this.mContext = context;
    }
    /*
    设置数据更新界面
     */
    public void setData(List<ExercisesBean> ebl){
        this.ebl = ebl;
        notifyDataSetChanged();
    }
    /*
    获取item的总数
     */
    @Override
    public int getCount() {
        return ebl == null ? 0 : ebl.size();
    }
    /*
    根据position得到对应item的对象
     */
    @Override
    public ExercisesBean getItem(int position) {
        return ebl == null ? null : ebl.get(position);
    }
    /*
    根据position得到对应item的id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }
    /*
    得到相应position对应的item视图，position是当前item的位置
    convertview参数就是退出屏幕的item的view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        //复用convertView
        if (convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.exercises_list_item,null);
            vh.title = (TextView) convertView.findViewById(R.id.tv_title);
            vh.content = (TextView) convertView.findViewById(R.id.tv_content);
            vh.order = (TextView) convertView.findViewById(R.id.tv_order);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        //获取position对应的item的数据对象
        final ExercisesBean bean = getItem(position);
        if (bean != null){
            vh.order.setText(position +1 +"");
            vh.title.setText(bean.title);
            vh.content.setText(bean.content);
            vh.order.setBackgroundResource(bean.background);
        }
        //每个item的点击事件
        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (bean == null)
                    return;
                    //跳转到习题详情页面
                    Intent intent = new Intent(mContext,
                            ExercisesDetailActivity.class);
                    //把章节id传递到习题详情页面
                    intent.putExtra("id",bean.id);
                    //把标题传递到习题详情页面
                    intent.putExtra("title",bean.title);
                    mContext.startActivity(intent);
                }
        });
        return convertView;
    }
    class ViewHolder{
        public TextView title,content;
        public TextView order;
    }
}
