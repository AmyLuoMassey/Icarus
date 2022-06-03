package project.com.icarus.db;

public class Item {
    // fields
    private int itemID;
    private String itemName;
    private String barcodeID;
    private String createdDate;
    private int locationId;
    private float price;
    private int units;
    private String itemDescription;
    private int createdBy;
    private int isActive;
    // constructors
    public Item() {}
    public Item(String itemname, String barcode, String itemdescription, int locationId, float price, int units,  String  createddate, int createdBy) {
        this.itemName = itemname;
        this.barcodeID = barcode;
        this.itemDescription = itemdescription;
        this.locationId = locationId;
        this.price = price;
        this.units = units;
        this.createdBy = createdBy;
        this.createdDate = createddate;
        this.isActive = 1;
    }
    // properties
    public void setItemID(int itemid) {
        this.itemID = itemid;
    }
    public int getItemID() {
        return this.itemID;
    }

    public void setItemName(String itemname) {
        this.itemName = itemname;
    }
    public String getItemName() {
        return this.itemName;
    }
    public void setBarcode(String barcode) {
        this.barcodeID = barcode;
    }
    public String getBarcode() {
        return this.barcodeID;
    }

    public void setLocationId(int loc_id) {
        this.locationId = loc_id;
    }
    public int getLocationId() {
        return this.locationId;
    }

    public void setUnits(int count) {
        this.units = count;
    }
    public int getUnits() {
        return this.units;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    public float getPrice() {
        return this.price;
    }

    public void setDescription(String itemdescription) {
        this.itemDescription = itemdescription;
    }
    public String getDescription() {
        return this.itemDescription;
    }

    public void setCreatedDate(String createddate) {
        this.createdDate = createddate;
    }
    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedBy(int createdby) {
        this.createdBy = createdBy;
    }
    public int getCreatedBy() {
        return this.createdBy;
    }

    public void setActive(int isactive) {
        this.isActive = isactive;
    }
    public int getActive() {
        return this.isActive;
    }

}