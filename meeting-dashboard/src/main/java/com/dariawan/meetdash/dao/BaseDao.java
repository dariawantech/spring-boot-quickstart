package com.dariawan.meetdash.dao;

public class BaseDao {

    public static Integer getRowStart(int pageNumber, int rowPerPage) {
        if (pageNumber < 1) {
            return 0;
        }
        return (pageNumber - 1) * rowPerPage;
    }
}
