import java.io.*;
import java.util.*;

public class WordTable {

    public static void main(String [] args) throws IOException{
        StringBuffer buffer = new StringBuffer();
        BufferedReader bf= new BufferedReader(new FileReader("D:\\Codes\\java\\WordTable\\src\\1.txt"));

        String s = null;
        while((s = bf.readLine())!=null){//使用readLine方法，一次读一行
            buffer.append(s.trim());
        }
        String xml = buffer.toString();
        bf.close();
        String [] str=xml.split("\\s");		//以空格分割
//        System.out.println(str.length);		//输出数组的大小
        HashSet<String> set = new HashSet<>(Arrays.asList(str));	//将数组转换为set集合，去重
//        System.out.println(set.size());	//输出set中元素的个数
//        for (String str1 : set) {
//            System.out.println(str1);
//        }
        Map<String,Integer> amountWord=new HashMap<String,Integer>();
        List<String> wordList = Arrays.asList(str);
        for (String string : wordList) {
            if(!amountWord.containsKey(string)){
                amountWord.put(string,1);
            }else{
                amountWord.put(string, amountWord.get(string).intValue()+1);
            }
        }
        //这里将map.entrySet()转换成list
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(amountWord.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            //升序排序
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }

        });

        for(Map.Entry<String,Integer> mapping:list){
            System.out.println(mapping.getKey()+":"+mapping.getValue());
        }
        System.out.println("词数："+list.size());
//        System.out.println(amountWord);
        String path="D:\\Codes\\java\\WordTable\\src\\2.txt";
        File file = new File(path);
        //如果没有文件就创建
        if (!file.isFile()) {
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        for(Map.Entry<String,Integer> mapping:list){
            writer.write(mapping.getKey() + "：" + mapping.getValue() + "\r\n");
        }
        writer.close();
        String source = "祝愿祖国明天更加繁荣昌盛 香港大学生在京度佳节 \n" +
                "新华社北京１月１日电 \n" +
                "昨晚，第一次来到首都北京的５０多名香港大学生，和北京航空航天大学的同学们在《歌唱祖国 》的歌声中一起迎接１９９８年的到来。\n" +
                "此次到京的香港大学生来自香港科技大学和浸会大学，他们于１２月３０日抵京后  参观了北大、清华和抗日战争纪念馆。" +
                "在中国青年政治学院，两地大学生就学习、生活等共同关心的话题展开了交流。\n";
        String fmmResult = Fmm(source, amountWord);
        String bmmResult  = Bmm(source, amountWord);
        System.out.println(fmmResult);
        System.out.println(bmmResult);
    }

    public static String Fmm(String source, Map<String, Integer> amountWord) {
        String[] targets = new String[source.length()];
        String target = "";
        String tem="";
        int MaxLen = source.length();
        int temLen = MaxLen;
        int primarylen = 0;
        while (true) {
            if (temLen>=0 && temLen<=MaxLen) {
                tem = source.substring(primarylen, temLen);
                if (amountWord.containsKey(tem) || temLen - primarylen == 1) {
                    primarylen = temLen;
                    temLen = MaxLen;
                    if (primarylen == MaxLen)
                        target = target + tem;
                    else
                        target = target + tem + "/";
                } else
                    temLen--;
            } else
                temLen--;

            if (primarylen == MaxLen)
                break;
        }
        return target;
    }

    public static String Bmm(String source, Map<String, Integer> amountWord) {
        String[] targets = new String[source.length()];
        String target="";
        String tem="";
        int MaxLen = source.length();
        int temLen = MaxLen;
        int primarylen = 0;
        int i=0;
        while (true) {
            if (temLen>=0 && temLen<=MaxLen) {
                tem = source.substring(primarylen, temLen);
                if (amountWord.containsKey(tem)||temLen-primarylen==1) {
                    if (temLen == MaxLen){
                        targets[i] = tem;
                    }
                    else{
                        tem = tem+"/";
                        targets[i] = tem;
                    }
                    temLen = primarylen;
                    primarylen = 0;
                    i++;
                } else
                    primarylen++;
            } else
                primarylen++;
            if (temLen == 0)
                break;
        }

        for(int j=i-1;j>=0;j--)
            target+=targets[j];
        return target;
    }
}
