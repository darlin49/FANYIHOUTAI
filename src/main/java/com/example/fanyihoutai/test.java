package com.example.fanyihoutai;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {
    @Test
    public  void main()  {


        String data = "序号\t开课学期\t课程编号\t课程名称\t成绩\t成绩标识\t学分\t总学时\t绩点\t补重学期\t考核方式\t考试性质\t课程属性\t课程性质\t通选课类别\n" +
                "1\t2024-2025-1\t03022010\t马克思主义基本原理概论\t70\t\t3\t48\t2\t\t考试\t正常考试\t必修\t通识必修课\t\n" +
                "2\t2024-2025-1\t03071701\t人工智能科普讲座\t93\t\t1\t16\t4.3\t\t考查\t正常考试\t限选\t通识选修课\t\n" +
                "3\t2024-2025-1\t05021002\t专业英语\t78\t\t1\t16\t2.8\t\t考试\t正常考试\t选修\t专业选修课\t\n" +
                "4\t2024-2025-1\t08031194\t嵌入式系统实验\t优\t\t1\t20\t4.5\t\t考查\t正常考试\t必修\t专业实践课\t\n" +
                "5\t2024-2025-1\t08065107\t算法分析与设计\t79\t\t2\t32\t2.9\t\t考试\t正常考试\t必修\t专业核心课\t\n" +
                "6\t2024-2025-1\t08065108\t软件工程\t73\t\t2\t32\t2.3\t\t考试\t正常考试\t必修\t专业核心课\t\n" +
                "7\t2024-2025-1\t08065170\t大数据技术\t78\t\t2\t32\t2.8\t\t考试\t正常考试\t选修\t专业选修课\t\n" +
                "8\t2024-2025-1\t08065173\tLinux操作系统实验\t良\t\t2\t40\t3.5\t\t考查\t正常考试\t必修\t专业实践课\t\n" +
                "9\t2024-2025-1\tX0000357\t操作系统\t81\t\t3\t48\t3.1\t\t考试\t正常考试\t必修\t专业核心课\t\n" +
                "10\t2024-2025-1\tX0000365\t嵌入式系统\t76\t\t3\t48\t2.6\t\t考试\t正常考试\t选修\t专业选修课\t\n" +
                "11\t2024-2025-1\tX0000490\tJava软件开发实战\t优\t\t2\t40\t4.5\t\t考查\t正常考试\t必修\t专业实践课\t解析这个字符串";

        String[] rows = data.split("\n");
        List<CourseParser.Course> courses = new ArrayList<>();

        // 跳过表头
        for (int i = 1; i < rows.length; i++) {
            String[] columns = rows[i].split("\t");
            System.out.printf(Arrays.toString(columns));
//            CourseParser.Course course = new CourseParser.Course(
//                    Integer.parseInt(columns[0]), // 序号
//                    columns[1], // 开课学期
//                    columns[2], // 课程编号
//                    columns[3], // 课程名称
//                    columns[4], // 成绩
//                    columns[5], // 成绩标识
//                    Integer.parseInt(columns[6]), // 学分
//                    Integer.parseInt(columns[7]),// 总学时

        for (String row:rows) {

        }

    }

}}