package com.example.sudoku;

import org.litepal.crud.LitePalSupport;

public class LastRecord extends LitePalSupport {
    // 使用的时间
    private long usedTime;
    // level
    private int level;
    // 是否显示颜色 0 / 1
    private int showColor;
    // 是否实时纠错
    private int showHint;
    // 答案的布局
    private String anwser;
    // 当前的布局
    private String current;
    // 最初的布局
    private String origin;
    // 类型 4 / 9
    private int type;
    // 用户名
    private String user;
    // 草稿
    private String note;

    public long getusedTime() {
        return usedTime;
    }
    public void setusedTime(long usedTime) {
        this.usedTime = usedTime;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getShowColor() {
        return showColor;
    }
    public void setShowColor(int showColor) {
        this.showColor = showColor;
    }
    public int getshowHint() {
        return showHint;
    }
    public void setshowHint(int showHint) {
        this.showHint = showHint;
    }
    public String getAnwser() {
        return anwser;
    }
    public void setAnwser(String anwser) {
        this.anwser = anwser;
    }
    public String getCurrent() {
        return current;
    }
    public void setCurrent(String current) {
        this.current = current;
    }
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
