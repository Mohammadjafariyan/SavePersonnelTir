package main.java.models;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Setting")
public class SettingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String ExcelOutputPath;

    private int syear;
    private int smonth;
    private int sday;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExcelOutputPath() {
        return ExcelOutputPath;
    }

    public void setExcelOutputPath(String excelOutputPath) {
        ExcelOutputPath = excelOutputPath;
    }

    public int getSyear() {
        return syear;
    }

    public void setSyear(int syear) {
        this.syear = syear;
    }

    public int getSmonth() {
        return smonth;
    }

    public void setSmonth(int smonth) {
        this.smonth = smonth;
    }

    public int getSday() {
        return sday;
    }

    public void setSday(int sday) {
        this.sday = sday;
    }
}
