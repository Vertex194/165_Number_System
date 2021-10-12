package dk.yj;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Member {
    private long id;
    private String skill;
    private String phone;
    private String datetime;
    private String note;
    public Member() {
    }

    public Member(long id, String skill, String phone, String datetime, String note) {
        this.id = id;
        this.skill = skill;
        this.phone = phone;
        this.datetime = datetime;
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDatetime() {
        return datetime;
    }
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    public void setDatetime(long now) {
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        datetime = sdf.format(date);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
