package jswanson.paintracker;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by jswan on 5/15/2017.
 */

public class CustomCalendar extends LinearLayout{

    private static String TAG = CustomCalendar.class.getSimpleName();
    private TextView currentDate;
    private GridView calendarGridView;
    private static final int MAX_COLUMN = 42;
    private int year;
    private int month;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar c = Calendar.getInstance(Locale.ENGLISH);
    private Context context;
    private GridAdapter myAdapter;

    public CustomCalendar(Context context) {
        super(context);
    }
    public CustomCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeUILayout();
        setUpCalendarAdapter();
        setGridCellClickEvents();
        Log.d(TAG, "I need to call this method");
    }
    public CustomCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initializeUILayout(){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        currentDate = (TextView)view.findViewById(R.id.display_current_date);
        calendarGridView = (GridView)view.findViewById(R.id.calendar_grid);
    }

    private void setGridCellClickEvents(){
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //Add new Activity
                Toast.makeText(context, "clicked" + position, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpCalendarAdapter(){

        List<Date> dayValueInCells = new ArrayList<Date>();
        DbHandler myDBhandeler = new DbHandler(context);
        List<Date> painDates = getPainDates(c, myDBhandeler);
        Calendar mCal = (Calendar)c.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while(dayValueInCells.size() < MAX_COLUMN){
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG, "Number of date " + dayValueInCells.size());
        String sDate = dateFormat.format(mCal.getTime());
        currentDate.setText(sDate);
        myAdapter = new GridAdapter(context, dayValueInCells, mCal, painDates);
        calendarGridView.setAdapter(myAdapter);
    }

    private List<Date> getPainDates(Calendar c, DbHandler myDbHandler){
        List<Date> painDates = new LinkedList<Date>();
        for(int i = 1; i <= c.getMaximum(Calendar.MONTH); i ++){
            if(myDbHandler.getTimes(c.YEAR, c.MONTH, c.DAY_OF_MONTH) != null){
                painDates.add(c.getTime());
            }
        }
        return painDates;
    }

}
