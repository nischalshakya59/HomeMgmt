package homemgmtsystem.dto;

public class EventMgmtDTO {

    private int eventid;
    private String eventname;
    private String todaydate;
    private String eventdate;
    private int diffdate;
    
    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getTodaydate() {
        return todaydate;
    }

    public void setTodaydate(String todaydate) {
        this.todaydate = todaydate;
    }

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }

    public int getDiffdate() {
        return diffdate;
    }

    public void setDiffdate(int diffdate) {
        this.diffdate = diffdate;
    }

}
