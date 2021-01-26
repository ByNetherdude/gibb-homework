package ch.bbcag.gibb_homework.model;

public class Modul {

    private String code;
    private String name;
    private int semester;

    public Modul(String code, String name, int semester){
        this.code = code;
        this.name = name;
        this.semester = semester;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
