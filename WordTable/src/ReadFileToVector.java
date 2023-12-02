import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
class ReadFileToVector {
    /**
     * @param str
     * @author:
     * <p>Description：从文件中读取一行内容，把读取的内容放入vector中返回
     */
    public static Vector ReadFile(String str)
    {
        String word=null;
        Vector vectors=new Vector();
        int i=0;
        try {
            FileInputStream fi=new FileInputStream(str);
            BufferedReader  input=new BufferedReader (new InputStreamReader(fi));
            while((word=input.readLine())!=null){
                //            System.out.println(input.readLine());
                vectors.add(word);
                //          System.out.println(vectors.get(i));
                //           i++;
            }
            System.out.println("加载字典单词总数是:"+vectors.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vectors;

    }
   /* public static void main(String[] args) {
        ReadFileToVector.ReadFile("D:\\dic.txt");

    }*/

}