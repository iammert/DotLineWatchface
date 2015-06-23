package co.mobiwise.dotlinewatchface.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.mobiwise.dotlinewatchface.R;
import co.mobiwise.dotlinewatchface.ui.SquareColorView;
import model.DotLinePreferences;

/**
 * Created by mertsimsek on 22/06/15.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>{

    public interface OnItemClickListener{
        public void onItemClicked(View view, int position);
    }

    private int[] mColorArray;
    private Context mContext;
    private OnItemClickListener mListener;

    private ViewHolder currentHolder;

    private DotLinePreferences mPreferences;

    public MyRecyclerAdapter(int[] mColorArray, Context mContext) {
        this.mColorArray = mColorArray;
        this.mContext = mContext;
        mPreferences = DotLinePreferences.newInstance(mContext);
    }

    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_watch_colors,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.ViewHolder viewHolder, int i) {
        viewHolder.colorView.setColor(mColorArray[i]);
    }

    @Override
    public int getItemCount() {
        return mColorArray.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @InjectView(R.id.colorview)
        SquareColorView colorView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClicked(v, getPosition());
                if(currentHolder!=null)
                    currentHolder.colorView.setSelected(false);
                currentHolder = this;
                this.colorView.setSelected(true);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener mListener){
        this.mListener = mListener;
    }

}
