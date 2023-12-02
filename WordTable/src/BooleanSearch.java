import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.*;

public class BooleanSearch {
    private Map<Integer, Map<String, Integer>> text = new HashMap<>();
    //行数
    private List<String> listLine = new ArrayList<>();
    private Map<Integer, Integer> textNum = new HashMap<>();
    private Map<String, Integer> yuliaoku = new HashMap<>();

    private void readOutput() throws IOException {
        String fileName = "D:\\Codes\\java\\WordTable\\src\\2.txt";
        //FileInputStream字节输入流；InputStreamReader将字节流转化为字符流；BufferedReader缓冲方式读取
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] input = line.trim().split("：");
            if (input.length != 2)
                continue;
            yuliaoku.put(input[0], Integer.valueOf(input[1]));
        }
        br.close();
    }

    private void readText() throws IOException {
        /********读取语料库每一行,存到List中********/
        String fileName = "D:\\Codes\\java\\WordTable\\src\\yuliaoku.txt";
        //FileInputStream字节输入流；InputStreamReader将字节流转化为字符流；BufferedReader缓冲方式读取
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "GBK"));
        String line;
        while ((line = br.readLine()) != null) {
            listLine.add(line);
        }
        br.close();
        for (int i = 0; i < listLine.size(); i++) {
            Map<String, Integer> words = new HashMap<>();
            String[] arrays = listLine.get(i).trim().split(" +");//每个文本分词并统计其词频
            textNum.put(i, arrays.length);
            for (String s : arrays) {
                if (s.indexOf("19980") != -1)
                    continue;
                String[] endString = s.split("/");
                s = endString[0];
                int count = words.containsKey(s) ? words.get(s) : 0;
                words.put(s, count + 1);
            }
            text.put(i, words);
        }
    }

    private void tf_IDFSearch(String line) throws IOException {
        readOutput();
        List<String> inputWords = new ArrayList<>();
        //BMM分词
        for (int j = line.length(); j >= 0 ; j--) {
            for(int i=0; i<j; i++) {
                String string = line.substring(i, j);
                if (yuliaoku.containsKey(string) && !string.equals(" ")) {
                    inputWords.add(string);
                    j -= string.length()-1;
                    break;
                } else if (i == j-1 && !string.equals(" ")) {
                    inputWords.add(string);
                    j -= string.length()-1;
                }
            }
        }
        /*********获取TF值---第i个网页，第j个词的TF值********/
        Map<Integer, Map<Integer, Double>> wordTF = new HashMap<>();
        for (int i = 0; i < listLine.size(); i++) {
            Map<String, Integer> map = text.get(i);
            Map map1 = new HashMap();

            for (int j = 0; j < inputWords.size(); j++) {
                int num = map.containsKey(inputWords.get(j)) ?  map.get(inputWords.get(j)) : 0;
                double aaa = (double)num / map.size();
                map1.put(j, aaa);
                wordTF.put(i, map1);
            }
        }

        /**********获取IDF值**********/
        System.out.println("------------------------------------------");
        System.out.println("输入的分词：" + inputWords);
        System.out.println("------------------------------------------");
        double[] wordIDF = new double[inputWords.size()];
        for (int i = 0; i < inputWords.size(); i++) {
            int idf = 0;
            for (int j = 0; j < listLine.size(); j++) {
                if (listLine.get(j).contains(inputWords.get(i))) {
                    idf ++;
                }
            }
            if (idf == 0) {
                System.out.println( inputWords.get(i) + "在所有网页中未出现，无法进行 网页数/词频 运算！");
                System.exit(-1);
            }
            double aaa = listLine.size() / idf;
            wordIDF[i] = Math.log(aaa);
        }
        /************第i个网页获得的TF-IDF计算如下：******/
        Map<Integer, Double> tf_idf = new HashMap<>();
        for (int i = 0; i < listLine.size(); i++) {
            double result = 0.0;
            for (int j = 0; j < inputWords.size(); j++) {
                Double map = wordTF.get(i).get(j);
                result += map * wordIDF[j];
            }
            tf_idf.put(i, result);
        }

        List<Map.Entry<Integer, Double>> entryList = new ArrayList<>(tf_idf.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for (int i = 0; i < 10; i++) {
            int a = entryList.get(i).getKey();
            System.out.println("文本为：" + listLine.get(a));
            System.out.println("TF-IDF值为：" + entryList.get(i).getValue());
            System.out.println("---------------------------------------------------------------------------------------------------------");
        }

    }


    private void boolSearch(String input) throws ScriptException {
        // 字符串转条件表达式
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByExtension("js");
        String[] words = input.trim().split(" +");
        String[] word3 = new String[3];
        for (int i = 0; i < words.length; i+=2) {
            word3[i/2] = words[i];
            words[i] = "word.containsKey(word3[" + i/2 + "])";
        }
        input = String.join(" ", words);
        input = input.replaceAll(" and ", " && ");
        input = input.replaceAll(" or ", " || ");
        input = input.replaceAll(" not ", " && ! ");
        engine.put("word3", word3);

        int num = 0;
        for (int i=0; i<text.size(); i++) {

            Map<String, Integer> word;
            word = text.get(i);

            engine.put("word", word);
            Boolean result = (Boolean) engine.eval(input);// 字符串转条件表达式
            if (result) {
                //符合就输出相应文本
                System.out.println(listLine.get(i));
                num ++;
            }
            if (num == 10)
                break;
        }
    }

    public static void main(String[] args) throws IOException, ScriptException {
        BooleanSearch booleanSearch = new BooleanSearch();
        booleanSearch.readText();
        Scanner scanner = new Scanner(System.in);
        String input;
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();

            input.getBytes("UTF-8");
            /*****布尔检索*******/
            //booleanSearch.boolSearch(input);//需要用到撤销注释
            /*****基于TF-IDF的检索系统*******/
            booleanSearch.tf_IDFSearch(input);
        }
    }
}

