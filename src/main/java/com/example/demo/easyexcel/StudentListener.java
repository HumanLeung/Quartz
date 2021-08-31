package com.example.demo.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentListener extends AnalysisEventListener<Student> {

    private static final Logger logger = LoggerFactory.getLogger(StudentListener.class);

    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        logger.info("student = {}",student);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
