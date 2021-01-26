package ch.bbcag.gibb_homework.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task {

    private String title;
    private String description;
    private List<String> imagePaths;
    private Date dueDate;

    public Task(){
        this.imagePaths = new ArrayList<>();
    }

    public Task(String title, String description, List<String> imgPaths, Date dueDate){
        this.title = title;
        this.description = description;
        this.imagePaths = imgPaths;
        this.dueDate = dueDate;
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

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
