import java.util.*;
import java.io.*;
public class PositiveMatch {
    /**
     * <p>Description：最大正向匹配算法
     * @throws IOException
     * @author：
     */
    public static void main(String[] args) throws IOException {
        int maxlen=11;
        String str="祝愿祖国明天更加繁荣昌盛 香港大学生在京度佳节 \n" +
                "新华社北京１月１日电 \n" +
                "昨晚，第一次来到首都北京的５０多名香港大学生，和北京航空航天大学的同学们在《歌唱祖国 》的歌声中一起迎接１９９８年的到来。\n" +
                "此次到京的香港大学生来自香港科技大学和浸会大学，他们于１２月３０日抵京后  参观了北大、清华和抗日战争纪念馆。在中国青年政治学院，两地大学生就学习、生活等共同关心的话题展开了交流。\n";
        Vector dictionary=new Vector();

        dictionary=ReadFileToVector.ReadFile("D:\\Codes\\java\\WordTable\\src\\2.txt");//从文件中加载词典，结果放到vector向量中

        int i=0,j=0;
        System.out.println(""+str.length());
        for(i=0;i<str.length();)
        {
            if(str.length()-i==1)
            { System.out.print(str.substring(i));
                break;
            }
            else if (str.length()-i<maxlen)
                maxlen=str.length()-i;

            j=i+maxlen-1;
            String key=str.substring(i, j);
            if (dictionary.contains(key))
            {System.out.print(key+"/");
                i=i+key.length();
                continue;}
            while(key.length()>1)
            {  j--;
                key=str.substring(i,j);
                if (dictionary.contains(key))
                {System.out.print(key+"/");
                    i=i+key.length();
                    break;}

                else  if(key.length()==1)
                { System.out.print(key+"/");
    	     /* try{
    		     FileOutputStream bw=new FileOutputStream("D:\\dic2.txt",true);
    		     bw.write(("\r\n"+key).getBytes()); 
    		       }catch(IOException e){}*/
                    i=i+key.length();
                    break;
                }
            }

        }
    }
}