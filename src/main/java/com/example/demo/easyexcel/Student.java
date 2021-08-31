package com.example.demo.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

@Data
//@ContentRowHeight()
//@HeadRowHeight
public class Student {


    @ColumnWidth(20)
    @ExcelProperty(value = {"学生信息表","学生姓名"},index = 0)
    private String name;

    @ColumnWidth(20)
    @ExcelProperty(value = {"学生信息表","学生性别"},index = 2)
    private String gender;

    @ExcelProperty(value = {"学生信息表","出生日期"},index = 1)
    @ColumnWidth(20)
    @DateTimeFormat("yyyy-MM-dd")
    private Date birthday;

    @ExcelProperty(value="ID",index = 3)
    @ExcelIgnore
    private String id;

}
