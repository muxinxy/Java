import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
public class jiami   {
	
	File infilename,outfilename; //声明加密和解密的位置//= new File("1.bmp");
	FileInputStream in;
	FileOutputStream out;
	jiami()
	{	
		infilename=outfilename=null;
		in=null;
		out=null;
	}
	public void setname(String a,String b)//()//= new FileInputStream(filename);
	{
		infilename = new File(a);
		outfilename=new File(b);
	}
	void write(String words) throws Throwable
	{
		File afile=new File("a.bmp");
		if(afile.exists()==false)
		{
			afile.createNewFile();
		}
		FileInputStream intemp=new FileInputStream(afile);
		char[] c = words.toCharArray();
		System.out.println(c);
		int i,j,k=0;
		int hehe;
		int asc=0;
		int temp=0;
		int flag=0;
		in = new FileInputStream(infilename);
		out=new FileOutputStream(outfilename);
		temp=in.read();
		while(temp!=-1)
		{
			if(flag==0&&k>=54)
			{
			
				for(j=0;j<words.length();j++)
				{
					if(c[j]=='$')
					{
						flag=1;
					}
					for(i=0;i<16;i++)
					{
						int flag1 = 0;
						while (flag1==0){
							if(k%3==0){
								hehe=(c[j]>>(15-i)&0x01);
								temp=hehe+(temp&0xfe);
								flag1=1;
							}
							out.write(temp);
							temp=temp-intemp.read();
							temp=in.read();
							k++;
						}

					}
				}
			}
			else
			{
				out.write(temp);
				k++;
				temp=intemp.read();
				temp=in.read();
			}
			
		};
		System.out.println("end");
	}
};
