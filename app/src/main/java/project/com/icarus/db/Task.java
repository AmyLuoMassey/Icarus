package project.com.icarus.db;

import java.util.Date;

public class Task {
    // fields
    private int taskID;
    private String taskName;
    private String createdDate;
    private Date scheduledDate;
    private String taskDescription;
    private int createdBy;
    private int userId;
    private int isActive;

    // constructors

    public Task() {}
    public Task(String taskname, Date scheduleddate, String taskdescription, int userId, String  createddate, int createdBy ) {
        this.taskName = taskname;
        this.scheduledDate = scheduleddate;
        this.taskDescription = taskdescription;
        this.createdBy = createdBy;
        this.createdDate = createddate;
        this.userId = userId;
        this.isActive = 1;
    }
    // properties
    public void setTaskID(int taskid) {
        this.taskID = taskid;
    }
    public int getTaskID() {
        return this.taskID;
    }

    public void setUserID(int userid) {
        this.userId = userid;
    }
    public int getUserID() {
        return this.userId;
    }

    public void setTaskName(String taskname) {
        this.taskName = taskname;
    }
    public String getTaskName() {
        return this.taskName;
    }
    public void setSceduledDate(Date scheduleddate) {
        this.scheduledDate = scheduleddate;
    }
    public Date getScheduledDate() {
        return this.scheduledDate;
    }
    public void setDescription(String taskdescription) {
        this.taskDescription = taskdescription;
    }
    public String getDescription() {
        return this.taskDescription;
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