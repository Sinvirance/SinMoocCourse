package top.course.generator.util;


import top.course.generator.enums.EnumGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/25 23:13
 * @Description: 操作数据库及表信息工具类
 */

public class DbUtil {

    /**
     * 获取数据库连接
     * @return 数据库连接对象
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/coursemooc";
            String user = "sinmooc";
            String pass = "ABcd13578965@";
            conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 获取指定表中文名
     * @param tableName 数据表名
     * @return 数据表中文名
     */
    public static String getTableComment(String tableName) throws Exception {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select table_comment from information_schema.tables Where table_name = '" + tableName + "'");
        String tableNameCH = "";
        if (rs != null) {
            while(rs.next()) {
                tableNameCH = rs.getString("table_comment");
                break;
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        System.out.println("表名：" + tableNameCH);
        return tableNameCH;
    }

    /**
     * 获得指定数据表所有列信息
     * @param tableName 数据表名
     * @return Field对象列表
     */
    public static List<Field> getColumnByTableName(String tableName) throws Exception {
        List<Field> fieldList = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("show full columns from `" + tableName + "`");
        if (rs != null) {
            while(rs.next()) {
                String columnName = rs.getString("Field");
                String type = rs.getString("Type");
                String comment = rs.getString("Comment");
                String nullAble = rs.getString("Null");
                Field field = new Field();
                field.setName(columnName);
                field.setNameHump(lineToHump(columnName));
                field.setNameBigHump(lineToBigHump(columnName));
                field.setType(type);
                field.setJavaType(DbUtil.sqlTypeToJavaType(rs.getString("Type")));
                field.setComment(comment);
                /* 设置数据表字段中文名 */
                if (comment.contains("|")) {
                    field.setNameCn(comment.substring(0, comment.indexOf("|")));
                } else {
                    field.setNameCn(comment);
                }
                /* 获取数据表字段是否可为空 */
                /* 若不可为null，获取最大长度 */
                field.setNullAble("YES".equals(nullAble));
                if (type.toUpperCase().contains("varchar".toUpperCase())) {
                    String lengthStr = type.substring(type.indexOf("(") + 1, type.length() - 1);
                    field.setLength(Integer.valueOf(lengthStr));
                } else {
                    field.setLength(0);
                }
                /* 通过数据表注释获取数据表字段是否枚举字段 */
                /* 若是枚举字段从注释中获取枚举类名 */
                if (comment.contains("枚举")) {
                    field.setEnums(true);
                    // 以课程等级为例：从注释中的“枚举[CourseLevelEnum]”，得到COURSE_LEVEL
                    int start = comment.indexOf("[");
                    int end = comment.indexOf("]");
                    String enumsName = comment.substring(start + 1, end);
                    String enumsConst = EnumGenerator.toUnderline(enumsName);
                    field.setEnumsConst(enumsConst);
                } else {
                    field.setEnums(false);
                }
                fieldList.add(field);
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        System.out.println("列信息：" + fieldList);
        return fieldList;
    }

    /**
     * 数据表字段名下划线转小驼峰变量名：course_id => courseId
     * @param str 待转换的数据表字段名
     * @return 转化后的变量名
     */
    public static String lineToHump(String str){
        Pattern linePattern = Pattern.compile("_(\\w)");
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 数据表字段名下划线转大驼峰变量名：course_id => CourseId
     * @param str 待转换的数据表字段名
     * @return 转化后的变量名
     */
    public static String lineToBigHump(String str){
        String s = lineToHump(str);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 数据库类型转为Java类型
     * @param sqlType 数据表各字段类型
     * @return Java类型
     */
    public static String sqlTypeToJavaType(String sqlType) {
        if (sqlType.toUpperCase().contains("varchar".toUpperCase())
                || sqlType.toUpperCase().contains("char".toUpperCase())
                || sqlType.toUpperCase().contains("text".toUpperCase())) {
            return "String";
        } else if (sqlType.toUpperCase().contains("datetime".toUpperCase())) {
            return "Date";
        } else if (sqlType.toUpperCase().contains("int".toUpperCase())) {
            return "Integer";
        } else if (sqlType.toUpperCase().contains("long".toUpperCase())) {
            return "Long";
        } else if (sqlType.toUpperCase().contains("decimal".toUpperCase())) {
            return "BigDecimal";
        } else {
            return "String";
        }
    }
}

