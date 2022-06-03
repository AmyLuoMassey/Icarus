package project.com.icarus.ui.reporting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import project.com.icarus.R;
import project.com.icarus.db.DatabaseHandler;
import project.com.icarus.db.Item;

public class ReportingFragment extends Fragment {

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;
    ArrayList labels;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_reporting, container, false);
        CreateChart(root);
        return root;
    }


   private void CreateChart(View root){

       // initializing variable for bar chart.
       barChart = root.findViewById(R.id.idBarChart);

       // calling method to get bar entries.
       getBarEntries();

       // creating a new bar data set.
       barDataSet = new BarDataSet(barEntriesArrayList, "Value of Items in Warehouse");

       // creating a new bar data and
       // passing our bar data set.
       barData = new BarData(barDataSet);

       // below line is to set data
       // to our bar chart.
       barChart.setData(barData);

       // adding color to our bar data set.
       barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

       // setting text color.
       barDataSet.setValueTextColor(Color.BLACK);

       // setting text size
       barDataSet.setValueTextSize(16f);
       barChart.getDescription().setEnabled(false);
   }
    private void getBarEntries() {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();
        labels = new ArrayList<>();
        DatabaseHandler dbHandler = new DatabaseHandler(getContext(), null, null, 1);
        ArrayList<Item> itemList = dbHandler.loadItems();
        int count = 0;
        for (Item i : itemList) {
          //  labels.add(new BarDataSet(i.getItemName());
            barEntriesArrayList.add(new BarEntry(count,i.getPrice() * i.getUnits()));
            count++;
        }







        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        //barEntriesArrayList.add(new BarEntry(1f, 4));
       // barEntriesArrayList.add(new BarEntry(2f, 6));
       // barEntriesArrayList.add(new BarEntry(3f, 8));
      //  barEntriesArrayList.add(new BarEntry(4f, 2));
       // barEntriesArrayList.add(new BarEntry(5f, 4));
      //  barEntriesArrayList.add(new BarEntry(6f, 1));
    }
}

