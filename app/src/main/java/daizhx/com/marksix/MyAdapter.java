package daizhx.com.marksix;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;

import java.util.ArrayList;

/**
 * Created by jiutong on 16/1/8.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private ArrayList<String[]> dataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView order;
        public TextView data;
        public TextView result;
        public ViewHolder(View itemView) {
            super(itemView);
            order = (TextView) itemView.findViewById(R.id.order);
            data = (TextView) itemView.findViewById(R.id.data);
            result = (TextView) itemView.findViewById(R.id.result);
        }
    }

    public MyAdapter(ArrayList<String[]> dataSet){
        this.dataSet = dataSet;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String[] ss = dataSet.get(position);
        if(ss.length == 3){
            holder.order.setText(ss[0]);
            holder.data.setText(ss[1]);
            holder.result.setText(ss[2]);
        }
    }


    @Override
    public int getItemCount() {
        if(dataSet != null) {
            return dataSet.size();
        }
        return 0;
    }

}
