package adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.joker.thanglong.R;
import com.joker.thanglong.Ultil.DialogUtil;
import com.kelin.scrollablepanel.library.PanelAdapter;

import java.util.ArrayList;

import Entity.EntityClass;

/**
 * Created by joker on 6/20/17.
 */

public class AdapterTimeTable extends PanelAdapter {
    public static final int TIEU_DE = 0;
    public static final int CA_HOC = 1;
    public static final int NGAY_HOC = 2;
    public static final int MON_HOC = 3;
    public static final String[] color = {"#FFEBE8","#81C784","#4FC3F7","#DCE775","#9FA8DA"
            , "#C5E1A5","#E6EE9C","#FFF59D","#D7CCC8","#CE93D8","#F48FB1","#FFAB91","#FFAB91","#FFAB91","#FFAB91"
            ,"#C5E1A5","#C5E1A5","#C5E1A5","#FFF59D","#FFF59D","#FFF59D","#FFF59D"};
    private int[] day = new int[]{2,3,4,5,6,7,8};
    protected int shift[] = new int[]{1,2,3,4,5,6,7,8,9,10};
    private ArrayList<EntityClass> items;
    private Activity context;

    public AdapterTimeTable(ArrayList<EntityClass> items, Activity context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getRowCount() {
        return (shift.length)+1;
    }

    @Override
    public int getColumnCount() {
        return day.length;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        int viewType = getItemViewType(row, column);
        switch (viewType){
            case TIEU_DE:
//                TieuDeHolder tieuDeHolder;
//                tieuDeHolder.title.
                break;
            case CA_HOC:
                ShiftHolder cahocHolder = (ShiftHolder) holder;
                if (row>0){
                    cahocHolder.idCahoc.setText("Ca "+shift[row-1]);
                }
                break;
            case NGAY_HOC:
                DayHolder thuHocHolder = (DayHolder) holder;
                if (column>0){
                    thuHocHolder.txtDay.setText("Thứ "+ day[column - 1]);
                }
                break;
            case MON_HOC:
                DisplayDetailClass((DetailClassHolder) holder,row,column);
                break;
            default:
                break;
        }

    }

    private void DisplayDetailClass(DetailClassHolder holder, int row, int column) {
        for (int i = 0; i < items.size(); i++) {
                if (row>= items.get(i).getStart() && row <=items.get(i).getEnd()
                        && column == items.get(i).getDay()-1)
                {
                    final int finalI = i;
                    holder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogUtil.showInfoClass(context,items.get(finalI));
                        }
                    });
                    holder.view.setBackgroundColor(Color.parseColor(color[i]));
                    if (items.get(i).getEnd() - items.get(i).getStart() <=2 ){
                        if (row == items.get(i).getStart()){
                            holder.tenMon.setText(items.get(i).getName());
                        }else if (row == items.get(i).getStart()+1){
                            holder.phongHoc.setText(items.get(i).getRoom().getCode());
                        }
                    }else {
                        if (row == items.get(i).getStart()){
                            holder.tenMon.setText(items.get(i).getName());
                        }else if (row == items.get(i).getStart()+1){
                            holder.phongHoc.setText(items.get(i).getRoom().getCode());
                        }
                    }

                }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TIEU_DE:
                return new TittleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.title,parent,false));
            case CA_HOC:
                return new ShiftHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shift,parent,false));
            case NGAY_HOC:
                return new DayHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day,parent,false)) ;
            case MON_HOC:
                return new DetailClassHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_shift,parent,false));
            default:
                break;
        }
        return new DetailClassHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_shift,parent,false));
    }

    @Override
    public int getItemViewType(int row, int column) {
        if (row==0 && column==0){
            return TIEU_DE;
        }
        if (row == 0){
            return NGAY_HOC;
        }
        if (column==0){
            return CA_HOC;
        }
        return MON_HOC;
    }

    private static class DayHolder extends RecyclerView.ViewHolder {
        private TextView txtDay;

        public DayHolder(View itemView) {
            super(itemView);
            txtDay = (TextView) itemView.findViewById(R.id.txtDay);
        }

    }

    private static class TittleHolder extends RecyclerView.ViewHolder {
        private TextView title;
        public TittleHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    private static class ShiftHolder extends RecyclerView.ViewHolder {
        private TextView idCahoc;

        public ShiftHolder(View itemView) {
            super(itemView);
            idCahoc = (TextView) itemView.findViewById(R.id.txtShift);
        }

    }

    private static class DetailClassHolder extends RecyclerView.ViewHolder {
        private FrameLayout guestLayout;
        private TextView tenMon;
        private TextView phongHoc;
        public View view;

        public DetailClassHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            guestLayout = (FrameLayout) itemView.findViewById(R.id.guest_layout);
            tenMon = (TextView) itemView.findViewById(R.id.txtNameClass);
            phongHoc = (TextView) itemView.findViewById(R.id.txtRoom);
        }

    }


}
