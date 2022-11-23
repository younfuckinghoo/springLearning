package com.hy.basejava.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CodeGenerate {

    public static void main(String[] args) throws IOException {
// 数据样例
//        字段	名称	数据类型	主键	非空
//        ID	ID	VARCHAR(32)	√	√
//        FORM_ID	实例化表单ID	VARCHAR(32)
//        TEMPLATE_ID	表单模板ID	VARCHAR(32)
//        TEMPLATE_NO	表单模板编号	VARCHAR(10)
//        TEMPLATE_NAME	表单模板名称	VARCHAR(200)
//        PROJECT_ID	项目号（结构）	VARCHAR(20)
//        SYSTEM_NAME	系统	VARCHAR(50)
//        LOCATION	位置	VARCHAR(50)
//        POSITION	部位	VARCHAR(50)
//        CHECK_PART	检查部件（非结构）	VARCHAR(50)

        String filePath = "C:\\Users\\Administrstor\\Desktop\\tables";
        File dir = new File(filePath);
        File[] files = dir.listFiles();
        File javaDIr = new File(dir, "/java");
        if (!javaDIr.exists()){
            javaDIr.mkdir();
        }
        File jsonDIr = new File(dir, "/json");
        if (!jsonDIr.exists()){
            jsonDIr.mkdir();
        }
        File sqlDIr = new File(dir, "/sql");
        if (!sqlDIr.exists()){
            sqlDIr.mkdir();
        }

        for (File file : files) {
            String content = FileUtil.inputStreamReadFile(file);

            String name = file.getName();
            String[] nameArr = name.split("\\.");

            String[] rows = content.split("\n");
            StringBuilder sqlStr = new StringBuilder("CREATE TABLE  "+nameArr[0]+"  \n" +
                    "   (");
            StringBuilder sqlCommentStr = new StringBuilder();
            StringBuilder mapperStr = new StringBuilder("");
            StringBuilder baseColumnSqlStr = new StringBuilder("");
            StringBuffer jsonStr = new StringBuffer("{");
            StringBuffer classStr = new StringBuffer("package com.noah.mes.craft.entity;\n" +
                    "import lombok.AllArgsConstructor;\n" +
                    "import java.util.Date;\n" +
                    "import lombok.Data;\n" +
                    "import lombok.NoArgsConstructor;\n" +
                    "\n" +
                    "import java.io.Serializable;\n" +
                    "import io.swagger.annotations.ApiModel;\n" +
                    "import io.swagger.annotations.ApiModelProperty;\n" +
                    "import org.apache.ibatis.type.JdbcType;\n" +
                    "import tk.mybatis.mapper.annotation.ColumnType;\n" +
                    "import javax.persistence.Column;\n" +
                    "import javax.persistence.Id;\n" +
                    "import javax.persistence.Table;\n\n\n" +
                    "@NoArgsConstructor\n" +
                    "@AllArgsConstructor\n" +
                    "@Data\n" +
                    "@Table(name = \""+nameArr[0]+"\")\n" +
                    "@ApiModel(description = \""+StrUtil.toCamelCase(nameArr[0])+"\")\n" +
                    "public class "+ StrUtil.toCamelCase(nameArr[0])+"  implements Serializable {\n" +
                    " private static final long serialVersionUID = 1L;\n");
            List<String> pk = new ArrayList<>();
            mapperStr.append(nameArr[0]+"\n");
            baseColumnSqlStr.append(nameArr[0]+"\n");

            for (String row : rows) {
                String[] cols = row.split("\t");
                for (String col : cols) {
                    col=col.replace(" ","");
                }
                String jdbcType ="VARCHAR";
                String type = "String";
                if (cols[2].toUpperCase().indexOf("NUMBER")>-1) {
                    type = "Integer";
                    jdbcType = "DECIMAL";
                }else if (cols[2].toUpperCase().indexOf("DATE")>-1){
                    jdbcType = "TIMESTAMP";
                    type = "Date";
                }
                classStr.append("@ColumnType(jdbcType=JdbcType."+jdbcType+")\n");
                classStr.append("@Column(name = \""+cols[0]+"\")\n");
                classStr.append("@ApiModelProperty(value =\""+cols[1]+"\",required = false)\n");
                classStr.append("private "+ type +" "+StrUtil.toCamelCase(cols[0].toLowerCase())+";\n\n");
                jsonStr.append("\""+StrUtil.toCamelCase(cols[0].toLowerCase())+"\":\"\",\n");
                String notNull = "";

                try {
                    if (StrUtil.isNotBlank(cols[3])) {
                        pk.add(cols[0]);
                    }
                }catch (RuntimeException e){

                }
                try {
                    notNull = StrUtil.isNotBlank(cols[3])?" NOT NULL ENABLE ":"";
                }catch (RuntimeException e){

                }
                sqlStr.append(cols[0]+" "+cols[2]+" "+notNull+",\n");
                sqlCommentStr.append("comment on column "+nameArr[0]+"."+cols[0]+" is '"+cols[1]+"';\n");
                baseColumnSqlStr.append(cols[0]+",");
                mapperStr.append(String.format("<result property=\"%1$s\" column=\"%2$s\" jdbcType=\"%3$s\"/>\n",StrUtil.toCamelCase(cols[0].toLowerCase()),cols[0],jdbcType));
            }
            classStr.append("}");
            jsonStr.append("}");

            sqlStr.append(" PRIMARY KEY ("+ CollectionUtil.join(pk,",") +")" +
                    ");\n");
            sqlStr.append(sqlCommentStr);
//            System.out.println(classStr.toString());
//            System.out.println(jsonStr.toString());
//            System.out.println(sqlStr.toString());
            System.out.println(mapperStr.toString());
            System.out.println(baseColumnSqlStr.toString());


//            File javaFile = new File(javaDIr, StrUtil.toCamelCase(nameArr[0]) + ".java");
//            File jsonFile = new File(jsonDIr, StrUtil.toCamelCase(nameArr[0])+ ".json");
//            File sqlFile = new File(sqlDIr, nameArr[0]+ ".sql");
//            FileWriter javaFileWriter = new FileWriter(javaFile);
//            javaFileWriter.write(classStr.toString());
//            javaFileWriter.flush();
//            javaFileWriter.close();
//            FileWriter jsonFileWriter = new FileWriter(jsonFile);
//            jsonFileWriter.write(jsonStr.toString());
//            jsonFileWriter.flush();
//            jsonFileWriter.close();
//            FileWriter sqlFileWriter = new FileWriter(sqlFile);
//            sqlFileWriter.write(sqlStr.toString());
//            sqlFileWriter.flush();
//            sqlFileWriter.close();
        }


    }


    public List<File> getFiles(String filePath){
        Map<String, File> allFile = FileUtil.getAllFile(filePath);
        List<File> fileList = allFile.values().stream().collect(Collectors.toList());
        return fileList;
    }



    public String readFile(File file) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(file);
        int available = fileInputStream.available();
        byte[] bytes = new byte[available];
        int read = fileInputStream.read(bytes);
        return new String(bytes);
    }





}
