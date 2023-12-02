import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class ModifyFile {
    public static void main(String[] args) throws Exception {
        /*
         *	约定:
         *   	1, ModifyFileDemo.java 直接放在 src 目录
         *   	2, 1.txt 文件 直接放在 src目录
         *  说明:
         *  	可以 将main 方法中的代码 抽取出来作为一个方法来使用
         */

        // 将符合条件的字符串srcStr 替换成 replaceStr
        String srcStr = "\\d{8}-\\d{2}-\\d{3}-\\d{3}/m|[/a-z！。”“，、——\\[\\]（）：《》……A-Z？]";
        String replaceStr = "";

        // 读
        File file = new File("src/1.txt");

        FileReader in = new FileReader(file);
        BufferedReader bufIn = new BufferedReader(in);

        // 内存流, 作为临时流
        CharArrayWriter  tempStream = new CharArrayWriter();

        // 替换
        String line = null;

        while ( (line = bufIn.readLine()) != null) {
            // 替换每行中, 符合条件的字符串
            line = line.replaceAll(srcStr, replaceStr);
            // 将该行写入内存
            tempStream.write(line);
            // 添加换行符
            tempStream.append(System.getProperty("line.separator"));
        }

        // 关闭 输入流
        bufIn.close();

        // 将内存中的流 写入 文件
        FileWriter out = new FileWriter(file);
        tempStream.writeTo(out);
        out.close();
    }


/*
//1.txt --- 改前
123
	456
		789
abc
	def
		ghi


		123
	456	456	456
		789
abc
	def
		ghi		123
	456
		789
abc
	def
		ghi
*/


/*
//1.txt --- 改后
123
	张三
		789
abc
	def
		ghi


		123
	张三	张三	张三
		789
abc
	def
		ghi		123
	张三
		789
abc
	def
		ghi
*/
}