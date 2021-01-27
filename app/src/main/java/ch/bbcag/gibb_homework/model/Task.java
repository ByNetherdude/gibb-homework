package ch.bbcag.gibb_homework.model;

import java.util.List;

public class Task {

    private int id;
    private String title;
    private String description;
    private String dueDate;
    private List<Images> imagePaths;
    private int moduleId;

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
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

    public List<Images> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<Images> imagePaths) {
        this.imagePaths = imagePaths;
    }

    @Override
    public String toString() {
        return title;
    }
}
