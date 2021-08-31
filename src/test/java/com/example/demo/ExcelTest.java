package com.example.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.example.demo.easyexcel.Student;
import com.example.demo.easyexcel.StudentListener;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class ExcelTest {

    @Test
    public void test01(){
        ExcelReaderBuilder readWorkBook = EasyExcel.read("src/main/resources/1.xlsx", Student.class,new StudentListener());
        ExcelReaderSheetBuilder sheet = readWorkBook.sheet();
       sheet.doRead();
    }

    @Test
    public void test02(){
       ExcelWriterBuilder writeWorkBook = EasyExcel.write("src/main/resources/2.xlsx",Student.class);
       ExcelWriterSheetBuilder sheet = writeWorkBook.sheet();
       List<Student> students = initData();
       sheet.doWrite(students);
    }
    private static List<Student> initData() {
        ArrayList<Student> students = new ArrayList<>();
        Student data = new Student();
        for (int i = 0; i < 10 ; i++) {
            data.setName("Yoga");
            data.setGender("Boy");
            data.setBirthday(new Date());
            students.add(data);
        }
        return students;
    }
}
