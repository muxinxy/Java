import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TFIDF {
    public Map<String,Integer> getAmountWord(){
        Map<String,Integer> amountWord=new HashMap<String,Integer>();
        try {
            InputStream is = new FileInputStream("D:\\Codes\\java\\WordTable\\src\\2.txt");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is));
            String str = null;
            while (true) {
                str = reader.readLine();
                if(str!=null)
                {
                    String word = str.split("：")[0];
                    Integer factor  = Integer.parseInt(str.split("：")[1]);
                    amountWord.put(word, factor);
                }
                else
                    break;
            }

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amountWord;
    }
}
