package com.jara.retrofit_rxjava_okhttp_demo.bean;

import java.io.Serializable;

/**
 * Created by jara on 2017-6-21.
 */

public class Splash implements Serializable {
    private static final long serialVersionUID = 558604625163705697L;
    public int id;
    public String burl;
    public String surl;
    public int type;
    public String click_url;
    public String savePath;
    public String title;

    public Splash(String burl, String surl, String click_url, String savePath) {
        this.burl = burl;
        this.surl = surl;
        this.click_url = click_url;
        this.savePath = savePath;
    }

    @Override
    public String toString() {
        return "Splash{" +
            "id=" + id +
                    ", burl='" + burl + '\'' +
                    ", surl='" + surl + '\'' +
                    ", type=" + type +
                    ", click_url='" + click_url + '\'' +
                    ", savePath='" + savePath + '\'' +
                    '}';
    }
}
