import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class jiemi {
File infilename;
public String gettext(String a) throws IOException
{
	String text="";
	int temp=0;
	int i;
	int key=0;
	char tempchar;
	File filea=new File(a);
	FileInputStream in = new FileInputStream(filea);
	long k = in.skip(54);
	do
	{
		key=0;
		for(i=0;i<16;i++)
		{
			int flag = 0;
			key=key*2;
			while (flag==0){
				if(k%3==0){
					temp=in.read();
					temp=temp%2;
					key+=temp;
					flag=1;
					k++;
				}
				else {
					temp=in.read();
					k++;
				}
			}
		}
		tempchar=(char)key;
		System.out.println(tempchar);
		text=text+tempchar;
	}while(key!='$');
	return text;
	}

}
