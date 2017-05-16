package jswanson.paintracker;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.Calendar;

/**
 * Created by jswan on 5/15/2017.
 */

public class GridAdapter extends ArrayAdapter {
    private static final String TAG = GridAdapter.class.getSimpleName();
    private LayoutInflater myInflater;
    private List<Date> daysOfMonth;
    private Calendar currentDate;
    private List<Date> painExists;
    public GridAdapter(Context context, List<Date> daysOfMonth, Calendar currentDate, List<Date> painExists) {
        super(context, R.layout.single_cell_layout);
        this.daysOfMonth = daysOfMonth;
        this.currentDate = currentDate;
        this.painExists = painExists;
        myInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Date date = daysOfMonth.get(position);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentYear = currentDate.get(Calendar.YEAR);
        View view = convertView;
        if(view == null){
            view = myInflater.inflate(R.layout.single_cell_layout, parent, false);
        }
        if(month == currentMonth && year == currentYear){
            view.setBackgroundColor(Color.parseColor("#FF5733"));
        }else{
            view.setBackgroundColor(Color.parseColor("#cccccc"));
        }
        TextView cell = (TextView)view.findViewById(R.id.event_id);
        TextView painIndicator = (TextView)view.findViewById(R.id.event_id);
        Calendar painCalendar = Calendar.getInstance();
        for(int i = 0; i < painExists.size(); i ++){
            painCalendar.setTime(painExists.get(i));
            if(day == painCalendar.get(Calendar.DAY_OF_MONTH) &&
                    month == painCalendar.get(Calendar.MONTH) + 1 && year == painCalendar.get(Calendar.YEAR)){
                painIndicator.setBackgroundColor(Color.parseColor("#FF4081"));
            }
        }
        return view;
    }

    @Override
    public int getCount(){
        return daysOfMonth.size();
    }

    @Nullable
    @Override
    public Object getItem(int position){
        return daysOfMonth.get(position);
    }

    @Override
    public int getPosition(Object item){
        return daysOfMonth.indexOf(item);
    }
}
