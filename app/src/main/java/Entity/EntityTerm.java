package Entity;

/**
 * Created by joker on 6/21/17.
 */

public class EntityTerm {
    private int id;
    private int code;
    private String symbol;
    private String name;
    private String year;
    private String start;
    private String end;

    public EntityTerm() {
    }

    public EntityTerm(int id, int code, String symbol, String name, String year, String start, String end) {
        this.id = id;
        this.code = code;
        this.symbol = symbol;
        this.name = name;
        this.year = year;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
