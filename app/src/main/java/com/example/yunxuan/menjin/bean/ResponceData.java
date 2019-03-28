package com.example.yunxuan.menjin.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by daiMaGe on 2019/2/20.
 */

public class ResponceData implements Serializable {
    public ArrayList<NewData> list;


    public ArrayList<NewData> getList() {
        return list;
    }

    public void setList(ArrayList<NewData> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ResponceData{" +
                "list=" + list +
                '}';
    }
}
