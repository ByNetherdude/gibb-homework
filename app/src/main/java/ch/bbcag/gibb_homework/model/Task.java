package ch.bbcag.gibb_homework.model;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Serializable {

    private int id;
    private int moduleId;
    private String title;
    private String description;
    private String dueDate;
    private int dueDateTimestamp;

    public int getDueDateTimestamp() {
        return dueDateTimestamp;
    }

    public void setDueDateTimestamp(int dueDateTimestamp) {
        this.dueDateTimestamp = dueDateTimestamp;
    }

    private String imageFile;
    private boolean isDone = false;

    private Module module;

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public Task() {}

    public Task(int id, int moduleId, String title, String description, String dueDate, Module module, String imageFile) {
        this.id = id;
        this.moduleId = moduleId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.module = module;
        this.imageFile = imageFile;
        this.dueDateTimestamp = parseDueDate(dueDate);
    }

    public String getModuleColor() {
        return this.module.getColor();
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getRelatedModuleNumber() {
        return module.getNumber();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isDone() { return isDone; }

    public void setDone(boolean done) { isDone = done; }

    public int parseDueDate(String dueDate) {
        // magic number=
        // millisec * sec * min * hours
        // 1000 * 60 * 60 * 24 = 86400000
        long MAGIC=86400000L;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yy");
            Date parsedDate = dateFormat.parse(dueDate);
            long currentTime = parsedDate.getTime();
            currentTime = currentTime / MAGIC;
            return (int) currentTime;
        } catch(Exception e) { //this generic but you can control another types of exception
            Log.d("ERROR", "Cant parse dueDate", e);
        }

        return 0;
    }

    @Override
    public String toString() {
        return title;
    }
}
