package project.com.icarus.db;

import static project.com.icarus.helpers.Helpers.fromDate;
import static project.com.icarus.helpers.Helpers.toDate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;


public class DatabaseHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "icarus.db";
    private final File DB_FILE;
    private SQLiteDatabase mDataBase;
    private final Context mContext;

    //initialize the database
    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        DB_FILE = context.getDatabasePath(DATABASE_NAME);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String USER_TABLE = "CREATE TABLE User(userID INTEGER PRIMARY KEY, userName TEXT, userRole TEXT, createdBy INTEGER, createdDate TEXT, password TEXT, isActive INTEGER)";
        String ITEM_TABLE = "CREATE TABLE Item(itemID INTEGER PRIMARY KEY, itemName TEXT, barcodeID TEXT, itemDescription TEXT, locationID INTEGER, price REAL, units INTEGER, createdBy INTEGER, createdDate TEXT , isActive INTEGER)";
        String TASK_TABLE = "CREATE TABLE Task (taskID INTEGER PRIMARY KEY, taskName TEXT, scheduledDate TEXT, taskDescription TEXT, userId INTEGER, createdBy INTEGER, createdDate TEXT , isActive INTEGER)";
        String LOCATION_TABLE = "CREATE TABLE Location (locationID INTEGER PRIMARY KEY, locationName TEXT, locationDescription TEXT, createdBy INTEGER, createdDate TEXT , isActive INTEGER)";
        db.execSQL(USER_TABLE);
        db.execSQL(TASK_TABLE);
        db.execSQL(ITEM_TABLE);
        db.execSQL(LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

    public ArrayList<Item> loadItems(int locationId) {

        String query = "Select * FROM Item Where isActive = 1 and locationID = " + locationId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Item> itemList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Item item = new Item();
            item.setItemID(Integer.parseInt(cursor.getString(0)));
            item.setItemName(cursor.getString(1));
            item.setBarcode(cursor.getString(2));
            item.setDescription(cursor.getString(3));
            item.setLocationId(Integer.parseInt(cursor.getString(4)));
            item.setPrice(Float.parseFloat(cursor.getString(5)));
            item.setUnits(Integer.parseInt(cursor.getString(6)));
            item.setCreatedBy(Integer.parseInt(cursor.getString(7)));
            item.setCreatedDate(cursor.getString(8));
            item.setActive(1);
            itemList.add(item);
        }
        cursor.close();
        db.close();
        return itemList;
    }

    public ArrayList<Item> loadItems() {

        String query = "Select * FROM Item Where isActive = 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Item> itemList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Item item = new Item();
            item.setItemID(Integer.parseInt(cursor.getString(0)));
            item.setItemName(cursor.getString(1));
            item.setBarcode(cursor.getString(2));
            item.setDescription(cursor.getString(3));
            item.setLocationId(Integer.parseInt(cursor.getString(4)));
            item.setPrice(Float.parseFloat(cursor.getString(5)));
            item.setUnits(Integer.parseInt(cursor.getString(6)));
            item.setCreatedBy(Integer.parseInt(cursor.getString(7)));
            item.setCreatedDate(cursor.getString(8));
            item.setActive(1);
            itemList.add(item);
        }
        cursor.close();
        db.close();
        return itemList;
    }

    public boolean checkPassword(String userName, String password) {
        try {
            String query = "Select * FROM User Where userName = " + "\"" + userName + "\"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            String pw = Encrypter.decrypt(cursor.getString(5));
            if (pw.equals(password)) {
                return true;
            } else {
                return false;
            }
        }
        catch(Exception ex){
            return false;
        }
    }

    public boolean checkAdmin(String userName) {
        try {
            String query = "Select * FROM User Where userName = " + "\"" + userName + "\"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            String role = cursor.getString(2);
            if (role.equals("Administrator")) {
                return true;
            } else {
                return false;
            }
        }
        catch(Exception ex){
            return false;
        }
    }

    public int getUserId(String userName) {
        try {
            String query = "Select userId FROM User Where userName = " + "\"" + userName + "\"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            int userId = Integer.parseInt(cursor.getString(0));
            return userId;
        }

        catch(Exception ex){
            return 0;
        }
    }

    public ArrayList<Task> loadTasks() {
        try {
            String query = "Select * FROM Task Where isActive = 1";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            ArrayList<Task> taskList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Task task = new Task();
                task.setTaskID(Integer.parseInt(cursor.getString(0)));
                task.setTaskName(cursor.getString(1));
                task.setSceduledDate(toDate(cursor.getString(2)));
                task.setDescription(cursor.getString(3));
                task.setUserID(Integer.parseInt(cursor.getString(4)));
                task.setCreatedBy(Integer.parseInt(cursor.getString(5)));
                task.setCreatedDate(cursor.getString(6));

                task.setActive(1);
                taskList.add(task);


            }
            cursor.close();
            db.close();
            return taskList;
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<Task> loadTasks(String userName) {
        try {

            int user = getUserId(userName);
            String query = "Select * FROM Task Where isActive = 1 and userId = " + user;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            ArrayList<Task> taskList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Task task = new Task();
                task.setTaskID(Integer.parseInt(cursor.getString(0)));
                task.setTaskName(cursor.getString(1));
                task.setSceduledDate(toDate(cursor.getString(2)));
                task.setDescription(cursor.getString(3));
                task.setUserID(Integer.parseInt(cursor.getString(4)));
                task.setCreatedBy(Integer.parseInt(cursor.getString(5)));
                task.setCreatedDate(cursor.getString(6));

                task.setActive(1);
                taskList.add(task);


            }
            cursor.close();
            db.close();
            return taskList;
        } catch (Exception ex) {
            return null;
        }
    }
    public ArrayList<Location> loadLocations() {

        String query = "Select * FROM Location Where isActive = 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Location> locationList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Location location = new Location();
            location.setLocationID(Integer.parseInt(cursor.getString(0)));
            location.setLocationName(cursor.getString(1));
            location.setDescription(cursor.getString(2));
            location.setCreatedBy(Integer.parseInt(cursor.getString(3)));
            location.setCreatedDate(cursor.getString(4));
            location.setActive(1);
            locationList.add(location);
        }
        cursor.close();
        db.close();
        return locationList;
    }
    public ArrayList<User> loadUsers() {

        String query = "Select * FROM User Where isActive = 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<User> userList = new ArrayList<>();
        while (cursor.moveToNext()) {
            User user = new User();
            user.setUserID(Integer.parseInt(cursor.getString(0)));
            user.setUserName(cursor.getString(1));
            user.setUserRole(cursor.getString(2));
            user.setCreatedBy(Integer.parseInt(cursor.getString(3)));
            user.setCreatedDate(cursor.getString(4));
            user.setActive(1);
            userList.add(user);
        }
        cursor.close();
        db.close();
        return userList;
    }


    public boolean addItem(Item item) {
       Item _item = item;
        try {
            if (_item != null) {

                ContentValues values = new ContentValues();
                values.put("itemName", _item.getItemName());
                values.put("barcodeID", _item.getBarcode());
                values.put("itemDescription", _item.getDescription());
                values.put("locationID", _item.getLocationId());
                values.put("price", _item.getPrice());
                values.put("units", _item.getUnits());
                values.put("createdBy", _item.getCreatedBy());
                values.put("createdDate", _item.getCreatedDate());
                values.put("isActive", 1);
                SQLiteDatabase db = this.getWritableDatabase();
                db.insert("Item", null, values);
                db.close();
                return true;
            }
        }
        catch(Exception ex) {
            return false;
        }
        return false;
    }


    public boolean addLocation(Location location) {

        Location _location = location;
        try {
            if (_location != null) {
                ContentValues values = new ContentValues();
                values.put("locationID",(String)null);
                values.put("locationName", _location.getLocationName());
                values.put("locationDescription", _location.getDescription());
                values.put("createdBy", _location.getCreatedBy());
                values.put("createdDate", _location.getCreatedDate());
                values.put("isActive", 1);
                SQLiteDatabase db = this.getWritableDatabase();
                db.insert("Location", null, values);
                db.close();
                return true;
            }
        }
        catch(Exception ex) {
            return false;
        }
        return false;
    }

    public boolean addTask(Task task) {
        Task _task = task;
        try {
            if(_task != null) {
                ContentValues values = new ContentValues();
                values.put("taskName", _task.getTaskName());
                values.put("taskDescription", _task.getDescription());
                values.put("createdBy", _task.getCreatedBy());
                values.put("createdDate", _task.getCreatedDate());
                values.put("scheduledDate", fromDate(_task.getScheduledDate()));
                values.put("userId", _task.getUserID());
                values.put("isActive", 1);
                SQLiteDatabase db = this.getWritableDatabase();
                db.insert("Task", null, values);
                db.close();
                return true;
            }
        }
        catch(Exception ex){
            return false;
        }
        return false;

    }

    public boolean addUser(User user)  {
        User _user = user;
        try {
            if (_user != null) {
                String pw = Encrypter.encrypt(_user.getPassword());

                ContentValues values = new ContentValues();
                values.put("userName", _user.getUserName());
                values.put("userRole", _user.getUserRole());
                values.put("createdBy", _user.getCreatedBy());
                values.put("createdDate", _user.getCreatedDate());
                values.put("password", pw);
                values.put("isActive", 1);
                SQLiteDatabase db = this.getWritableDatabase();
                db.insert("User", null, values);
                db.close();
                return true;
            }
        }
        catch(Exception ex){
            return false;
        }
        return false;
    }

    public ArrayList<Item> findItem(String column, String search) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM Item WHERE " + column + " like '%" + search + "%' AND isActive = 1", null);

        ArrayList<Item> itemList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Item item = new Item();
            item.setItemID(Integer.parseInt(cursor.getString(0)));
            item.setItemName(cursor.getString(1));
            item.setBarcode(cursor.getString(2));
            item.setDescription(cursor.getString(3));
            item.setLocationId(Integer.parseInt(cursor.getString(4)));
            item.setPrice(Float.parseFloat(cursor.getString(5)));
            item.setUnits(Integer.parseInt(cursor.getString(6)));
            item.setCreatedBy(Integer.parseInt(cursor.getString(7)));
            item.setCreatedDate(cursor.getString(8));
            item.setActive(1);
            itemList.add(item);
        }
        cursor.close();
        db.close();
        return itemList;

    }

    public ArrayList<Task> findTasks(String column, String search) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("Select * FROM Task WHERE " + column + " like '%" + search + "%' AND isActive = 1", null);

            ArrayList<Task> taskList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Task task = new Task();
                task.setTaskID(Integer.parseInt(cursor.getString(0)));
                task.setTaskName(cursor.getString(1));
                task.setSceduledDate(toDate(cursor.getString(2)));
                task.setDescription(cursor.getString(3));
                task.setUserID(Integer.parseInt(cursor.getString(4)));
                task.setCreatedBy(Integer.parseInt(cursor.getString(5)));
                task.setCreatedDate(cursor.getString(6));
                task.setActive(1);
                taskList.add(task);
            }
            cursor.close();
            db.close();
            return taskList;
        }
        catch(Exception ex) {
            return  null;
        }
    }
    public ArrayList<Location> findLocations(String column, String search) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM Location WHERE " + column + " like '%" + search + "%' AND isActive = 1", null);

        ArrayList<Location> locationList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Location location = new Location();
            location.setLocationID(Integer.parseInt(cursor.getString(0)));
            location.setLocationName(cursor.getString(1));
            location.setDescription(cursor.getString(2));
            location.setCreatedBy(Integer.parseInt(cursor.getString(3)));
            location.setCreatedDate(cursor.getString(4));
            location.setActive(1);
            locationList.add(location);
        }
        cursor.close();
        db.close();
        return locationList;
    }
    public ArrayList<User> findUsers(String column, String search) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM User WHERE " + column + " like '%" + search + "%' AND isActive = 1", null);
        ArrayList<User> userList = new ArrayList<>();
        while (cursor.moveToNext()) {
            User user = new User();
            user.setUserID(Integer.parseInt(cursor.getString(0)));
            user.setUserName(cursor.getString(1));
            user.setUserRole(cursor.getString(2));
            user.setCreatedBy(Integer.parseInt(cursor.getString(3)));
            user.setCreatedDate(cursor.getString(4));
            user.setActive(1);
            userList.add(user);
        }
        cursor.close();
        db.close();
        return userList;
    }

    public boolean disableRecord(int ID, String table, boolean disable) {
        Cursor cursor;
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String identifier = "";
        // sets whether we're enabling or disabling a record.
        boolean toDisable = disable;

        switch (table) {
            case "item":
                cursor = db.rawQuery("Select * FROM Item WHERE item_id" + "= '" + String.valueOf(ID) + "'", null);
                identifier = "itemId";
                break;
            case "task":
                cursor = db.rawQuery("Select * FROM Task WHERE task_id" + "= '" + String.valueOf(ID) + "'", null);
                identifier = "taskId";
                break;
            case "Location":
                cursor = db.rawQuery("Select * FROM Location WHERE location_id" + "= '" + String.valueOf(ID) + "'", null);
                identifier = "locationId";
                break;
            case "User":
                cursor = db.rawQuery("Select * FROM User WHERE user_id" + "= '" + String.valueOf(ID) + "'", null);
                identifier = "userId";
                break;
            default:
                cursor = db.rawQuery("Select 1", null);
        }
        if (cursor.moveToFirst()) {
            ContentValues args = new ContentValues();
            args.put("isActive", toDisable);
            db.update(table, args, identifier + "=" + ID, null);

            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }



    public boolean deleteRecord(int ID, String table) {
        Cursor cursor;
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();

        switch (table) {
            case "item":
                cursor = db.rawQuery("Select * FROM Item WHERE item_id" + "= '" + String.valueOf(ID) + "'", null);
                Item item = new Item();
                if (cursor.moveToFirst()) {
                    item.setItemID(Integer.parseInt(cursor.getString(0)));
                    db.delete("Item", "itemId=?",
                            new String[]{
                                    String.valueOf(item.getItemID())
                            });
                    cursor.close();
                    result = true;
                }
            case "task":
                cursor = db.rawQuery("Select * FROM Task WHERE task_id" + "= '" + String.valueOf(ID) + "'", null);
                Task task = new Task();
                if (cursor.moveToFirst()) {
                    task.setTaskID(Integer.parseInt(cursor.getString(0)));
                    db.delete("Task", "taskId=?",
                            new String[]{
                                    String.valueOf(task.getTaskID())
                            });
                    cursor.close();
                    result = true;
                }
            case "Location":
                cursor = db.rawQuery("Select * FROM Location WHERE location_id" + "= '" + String.valueOf(ID) + "'", null);
                Location location = new Location();
                if (cursor.moveToFirst()) {
                    location.setLocationID(Integer.parseInt(cursor.getString(0)));
                    db.delete("Location", "locationId=?",
                            new String[]{
                                    String.valueOf(location.getLocationID())
                            });
                    cursor.close();
                    result = true;
                }
            case "User":
                cursor = db.rawQuery("Select * FROM User WHERE user_id" + "= '" + String.valueOf(ID) + "'", null);
                User user = new User();
                if (cursor.moveToFirst()) {
                    user.setUserID(Integer.parseInt(cursor.getString(0)));
                    db.delete("User", "UserId=?",
                            new String[]{
                                    String.valueOf(user.getUserID())
                            });
                    cursor.close();
                    result = true;
                }
        }
        db.close();
        return result;
    }
    public boolean updateItem(Item item) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Item _item = item;
            ContentValues values = new ContentValues();
            values.put("itemName", _item.getItemName());
            values.put("barcodeID", _item.getBarcode());
            values.put("itemDescription", _item.getDescription());
            values.put("locationID",_item.getLocationId());
            values.put("price", _item.getPrice());
            values.put("units", _item.getUnits());
            values.put("createdBy", _item.getCreatedBy());
            values.put("createdDate", _item.getCreatedDate());
            values.put("isActive", 1);


            db.update("Item", values, "itemId = " + _item.getItemID(), null);
            db.close();
            return true;
        }
        catch(Exception ex) {
            db.close();
            return false;
        }

    }
    public boolean updateTask(Task task) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Task _task = task;

            ContentValues values = new ContentValues();

            values.put("taskName", _task.getTaskName());
            values.put("taskDescription", _task.getDescription());
            values.put("userId",_task.getUserID());
            values.put("createdBy", _task.getCreatedBy());
            values.put("createdDate", _task.getCreatedDate());

            values.put("scheduledDate", fromDate(_task.getScheduledDate()));
            values.put("isActive", 1);

            db.update("Task", values, "taskId = " + _task.getTaskID(), null);
            db.close();
            return true;
        }
        catch(Exception ex) {
            db.close();
            return false;
        }

    }

    public boolean updateLocation(Location location) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Location _location = location;
            ContentValues values = new ContentValues();
            values.put("locationName", _location.getLocationName());
            values.put("locationDescription", _location.getDescription());
            values.put("createdBy", _location.getCreatedBy());
            values.put("createdDate", _location.getCreatedDate());
            values.put("isActive", 1);

            db.update("Location", values, "locationId = " + _location.getLocationID(), null);
            db.close();
            return true;
        }
        catch(Exception ex) {
            db.close();
            return false;
        }

    }
    public boolean updateUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            User _user = user;
            ContentValues values = new ContentValues();
            values.put("userName", _user.getUserName());
            values.put("userRole", _user.getUserRole());
            values.put("createdBy", _user.getCreatedBy());
            values.put("createdDate", _user.getCreatedDate());
            values.put("isActive", 1);

            db.update("User", values, "userId = " + _user.getUserID(), null);
            db.close();
            return true;
        }
        catch(Exception ex) {
            db.close();
            return false;
        }

    }





}

