package project.com.icarus.ui.warehouse;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import project.com.icarus.R;
import project.com.icarus.db.DatabaseHandler;
import project.com.icarus.db.Item;
import project.com.icarus.db.Location;

public class WarehouseFragment extends Fragment  {


    ListView itemListView;
    Spinner spnLocation;
    ArrayList<Location> locationList;
    Button btnAddItem;
    Button btnItemConfirm, btnItemCancel, btnBarcode;
    EditText txtItemName, txtBarcode, txtItemDesc, txtItemPrice, txtItemUnits;
    ItemAdapter itemAdapter;
    int currentId;

    ArrayList<Item> itemList;
    ArrayList<String> locationStringList;
    ArrayList<Integer> locationIdList;
    PopupWindow popupAddItemWindow, popupBarcodeWindow, popupEditItemWindow;
    View popupAddItemView, popupBarcodeView, popupEditItemView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         View root = inflater.inflate(R.layout.fragment_warehouse, container, false);

        spnLocation = (Spinner) root.findViewById(R.id.dropdown_location);

        DatabaseHandler dbHandler = new DatabaseHandler(getContext(), null, null, 1);
        locationList = dbHandler.loadLocations();

        locationStringList = new ArrayList<>();
        locationIdList = new ArrayList<>();

        for (Location loc : locationList) {
           locationStringList.add(loc.getLocationName());
           locationIdList.add(loc.getLocationID());

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, locationStringList);
        spnLocation.setAdapter(adapter);

        spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                reloadAllData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        if(locationStringList.size()!=0){
            int locId = locationIdList.get(spnLocation.getSelectedItemPosition());
            itemList = dbHandler.loadItems(locId);
        }
        else {
            itemList = dbHandler.loadItems();
        }

        itemListView = root.findViewById(R.id.itemListView);
        itemAdapter = new ItemAdapter(getContext(), itemList);
        itemListView.setAdapter(itemAdapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item)parent.getItemAtPosition(position);
                popupEditItemView = inflater.inflate(R.layout.fragment_edit_item, null);


                // create a popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                popupEditItemWindow = new PopupWindow(popupEditItemView, width, height, focusable);

                popupEditItemWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                btnItemConfirm = (Button) popupEditItemView.findViewById(R.id.btnItemConfirm);
                btnItemCancel = (Button) popupEditItemView.findViewById(R.id.btnItemCancel);
                btnBarcode = (Button) popupEditItemView.findViewById(R.id.btnBarcode);


                txtItemName = (EditText)  popupEditItemView.findViewById(R.id.txt_itemName);
                txtItemDesc = (EditText)  popupEditItemView.findViewById(R.id.txt_itemDescription);
                txtBarcode  =(EditText)  popupEditItemView.findViewById(R.id.txt_barcode);
                txtItemPrice =(EditText)  popupEditItemView.findViewById(R.id.txt_itemPrice);
                txtItemUnits =(EditText)  popupEditItemView.findViewById(R.id.txt_itemUnits);


                txtItemName.setText(item.getItemName());
                txtBarcode.setText(item.getBarcode());
                txtItemDesc.setText(item.getDescription());
                txtItemPrice.setText( Float.toString(item.getPrice()));
                txtItemUnits.setText(Integer.toString(item.getUnits()));

                currentId = item.getItemID();
                setupBarcodeWindow(width, height, focusable, view, inflater);


                btnItemConfirm.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View popupView) {

                        if (!txtItemName.getText().equals("")){
                            DatabaseHandler dbHandler = new DatabaseHandler(getContext(), null, null, 1);
                            int units;
                            float price;
                            try {
                                 units = Integer.parseInt(txtItemUnits.getText().toString());
                                 price = Float.parseFloat(txtItemPrice.getText().toString());

                            }
                            catch(NumberFormatException ex){
                                units = 0;
                                price = 0;
                            }

                            Item item = new Item(txtItemName.getText().toString(),txtBarcode.getText().toString(),txtItemDesc.getText().toString(),locationIdList.get(spnLocation.getSelectedItemPosition()),price,units,"",0);
                            item.setItemID(currentId);
                            boolean result = dbHandler.updateItem(item);
                            if(result){
                                reloadAllData();
                                Toast.makeText(getContext(), "Item edited successfully", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getContext(), "Item editing failed. Please contact your administrator.", Toast.LENGTH_LONG).show();
                            }
                            popupEditItemWindow.dismiss();

                        }
                        else {
                            Toast.makeText(getContext(), "Please enter Item Name.", Toast.LENGTH_LONG).show();
                        }



                    }
                });
                btnItemCancel.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View popupView) {
                        popupEditItemWindow.dismiss();
                    }
                });


            }
        });






        btnAddItem = (Button) root.findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window

                popupAddItemView = inflater.inflate(R.layout.fragment_add_item, null);


                // create a popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                popupAddItemWindow = new PopupWindow(popupAddItemView, width, height, focusable);

                popupAddItemWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                btnItemConfirm = (Button) popupAddItemView.findViewById(R.id.btnItemConfirm);
                btnItemCancel = (Button) popupAddItemView.findViewById(R.id.btnItemCancel);
                btnBarcode = (Button) popupAddItemView.findViewById(R.id.btnBarcode);
                txtItemPrice =(EditText)  popupAddItemView.findViewById(R.id.txt_itemPrice);
                txtItemUnits =(EditText)  popupAddItemView.findViewById(R.id.txt_itemUnits);

                txtItemName = (EditText)  popupAddItemView.findViewById(R.id.txt_itemName);
                txtItemDesc = (EditText)  popupAddItemView.findViewById(R.id.txt_itemDescription);
                txtBarcode  =(EditText)  popupAddItemView.findViewById(R.id.txt_barcode);

                setupBarcodeWindow(width, height, focusable, v, inflater);


                btnItemConfirm.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View popupView) {

                        if (!txtItemName.getText().equals("")) {
                            int units;
                            float price;
                            try {
                                units = Integer.parseInt(txtItemUnits.getText().toString());
                                price = Float.parseFloat(txtItemPrice.getText().toString());

                            }
                            catch(NumberFormatException ex){
                                units = 0;
                                price = 0;
                            }


                            DatabaseHandler dbHandler = new DatabaseHandler(getContext(), null, null, 1);
                            Item item = new Item(txtItemName.getText().toString(),txtBarcode.getText().toString(),txtItemDesc.getText().toString(),locationIdList.get(spnLocation.getSelectedItemPosition()),price,units,"",0);

                            boolean result = dbHandler.addItem(item);
                            if (result) {
                                reloadAllData();
                                Toast.makeText(getContext(), "Item added successfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "Item creation failed. Please contact your administrator.", Toast.LENGTH_LONG).show();
                            }
                            popupAddItemWindow.dismiss();

                        }
                        else {
                            Toast.makeText(getContext(), "Please enter Item Name.", Toast.LENGTH_LONG).show();
                        }



                    }
                });
                btnItemCancel.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View popupView) {
                        popupAddItemWindow.dismiss();
                    }
                });

            }
        });



        return root;
    }
    public void setupBarcodeWindow(int width, int height, boolean focusable, View view,  LayoutInflater inflater){
        btnBarcode.setOnClickListener(new View.OnClickListener() {

            public void onClick(View popupView) {
                if(popupBarcodeWindow !=null){

                    popupBarcodeWindow = new PopupWindow(popupBarcodeView, width, height, focusable);
                    popupBarcodeWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                }
                else {
                    popupBarcodeView = inflater.inflate(R.layout.fragment_barcode_holder, null);

                    popupBarcodeWindow = new PopupWindow(popupBarcodeView, width, height, focusable);
                    popupBarcodeWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                }


            }
        });
    }

    public void closePopup(){
        if(popupBarcodeWindow !=null) {
            popupBarcodeWindow.dismiss();


        }
    }

    public void setBarcode(String barcode){
        txtBarcode.setText(barcode);
    }

    private void reloadAllData(){
        // get new modified random data
        DatabaseHandler dbHandler = new DatabaseHandler(getContext(), null, null, 1);
        if(locationStringList.size()!=0){
            int locId = locationIdList.get(spnLocation.getSelectedItemPosition());
            itemList = dbHandler.loadItems(locId);
        }
        else {
            itemList = dbHandler.loadItems();
        }

        // update data in adapter
        itemAdapter.getItemData().clear();
        itemAdapter.getItemData().addAll(itemList);

        itemAdapter.notifyDataSetChanged();
    }



}