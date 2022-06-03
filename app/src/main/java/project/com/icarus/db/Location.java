package project.com.icarus.db;

public class Location {
    // fields
    private int locationID;
    private String locationName;
    private String createdDate;
    private String locationDescription;
    private int createdBy;
    private int isActive;

    // constructors
    public Location() {}
    public Location(String locationname,  String locationdescription, String  createddate, int createdBy ) {

        this.locationName = locationname;
        this.locationDescription = locationdescription;
        this.createdBy = createdBy;
        this.createdDate = createddate;
        this.isActive = 1;
    }
    // properties
    public void setLocationID(int locationid) {
        this.locationID = locationid;
    }
    public int getLocationID() {
        return this.locationID;
    }

    public void setLocationName(String locationname) {
        this.locationName = locationname;
    }
    public String getLocationName() {
        return this.locationName;
    }
    public void setDescription(String locationdescription) {
        this.locationDescription = locationdescription;
    }
    public String getDescription() {
        return this.locationDescription;
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