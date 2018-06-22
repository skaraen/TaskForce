package com.example.karaens.taskforce;

public class Task_element {

     int code;
     String heading;
     String dscrptn;

    public Task_element(int code, String heading, String dscrptn) {
        this.code = code;
        this.heading = heading;
        this.dscrptn = dscrptn;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDscrptn() {
        return dscrptn;
    }

    public void setDscrptn(String dscrptn) {
        this.dscrptn = dscrptn;
    }
}
