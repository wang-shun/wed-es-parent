package dto;

import java.util.Date;
import java.util.List;

/**
 * Created by huachao on 2/29/16.
 */
public class Wrapper {

    private String name;
    private int no;
    private List<String> strList;
    private int[] intList;
    private Date date;
    private Internal[] internals;

    public Internal[] getInternals() {
        return internals;
    }

    public void setInternals(Internal[] internals) {
        this.internals = internals;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public List<String> getStrList() {
        return strList;
    }

    public void setStrList(List<String> strList) {
        this.strList = strList;
    }

    public int[] getIntList() {
        return intList;
    }

    public void setIntList(int[] intList) {
        this.intList = intList;
    }
}
