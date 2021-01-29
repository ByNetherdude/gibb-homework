package ch.bbcag.gibb_homework.model;

public class Task {

    private int id;
    private int modulId;
    private String title;
    private String description;
    private String dueDate;
    private String imageFile;

    private Modul modul;

    public int getModulId() {
        return modulId;
    }

    public void setModulId(int modulId) {
        this.modulId = modulId;
    }

    public Task() {}

    public Task(int id, int modulId, String title, String description, String dueDate, Modul modul, String imageFile) {
        this.id = id;
        this.modulId = modulId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.modul = modul;
        this.imageFile = imageFile;
    }

    public void setModul(Modul modul) {
        this.modul = modul;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getRelatedModuleNumber() {
        return modul.getNumber();
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

    @Override
    public String toString() {
        return title;
    }
}
