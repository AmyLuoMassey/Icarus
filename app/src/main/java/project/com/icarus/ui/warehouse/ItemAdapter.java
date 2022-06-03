package project.com.icarus.ui.warehouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

import project.com.icarus.R;
import project.com.icarus.db.Item;



public class ItemAdapter extends ArrayAdapter<Item> {

    private ArrayList<Item> _itemArrayList;

    public ItemAdapter(@NonNull Context context, ArrayList<Item> itemArrayList) {
        super(context, 0, itemArrayList);
        _itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.warehouse_listview, parent, false);
        }
        Item item = getItem(position);
        TextView identifier = listitemView.findViewById(R.id.txtItemIdentifier);
        TextView name = listitemView.findViewById(R.id.txtItemName);
        Integer id = (Integer)item.getItemID();
        identifier.setText(id.toString());
        name.setText(item.getItemName());
        return listitemView;
    }


    public ArrayList<Item> getItemData() {
        return _itemArrayList;
    }
}