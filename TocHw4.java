/*                                                                           
	Name       : 翁子鈞
	Student ID : F74019041
	Description: This code is used for searching the road spread intense,and find out max and min real price
*/ 
import java.lang.Object;

import org.json.*;

import java.util.regex.*;
import java.net.*;
import java.io.*;
public class TocHw4{

	
	public static void main(String[] args)throws Exception,JSONException
	{
		String uRL,roadName=" ",area;
		int test,test2,test3,test4,j=0,m=0;
		
		try
		{

			//Read Command Line
			//
			uRL=args[0];
			URL website = new URL(uRL);
			BufferedReader in= new BufferedReader(new InputStreamReader(website.openStream(),"UTF-8"));
			
			//Read JSON file
			//
			JSONObject json = new JSONObject();
			JSONTokener realPrice= new JSONTokener(in);
			JSONArray findAnswer = new JSONArray(realPrice);
			//Start
			//
			String location;
			int totalPrice,month;
			String[] data=new String[10000];
			String[] data2=new String[10000];
			int[] aboutTime=new int[10000];
			int[][] aboutTime2=new int[2000][50];
			int[] flag=new int[10000];
			for(int i=0;i<findAnswer.length();i++)
			{
				json=findAnswer.getJSONObject(i);
				area=json.getString("鄉鎮市區");
				location=json.getString("土地區段位置或建物區門牌");
				month=json.getInt("交易年月");
				totalPrice=json.getInt("總價元");
				//
				//
				test=location.indexOf("路");
				test2=location.indexOf("大道");
				test3=location.indexOf("街");
	
				if(test!=-1)
				{
					data[j]=location.substring(0,test+1);
					aboutTime[j]=month;
					j++;
					test=0;
				
				}
								
				if(test2!=-1)
				{
					data[j]=location.substring(0,test2+2);
					aboutTime[j]=month;
					j++;
					test2=0;
				}
				
				
				if(test3!=-1)
				{
					data[j]=location.substring(0,test3+1);
					aboutTime[j]=month;
					j++;			
					test3=0;
				}
				if(test==-1 && test2==-1 && test3==-1)
				{
					test4=location.indexOf("巷");
					if(test4!=-1)
					{
						data[j]=location.substring(0,test4+1);
						aboutTime[j]=month;
						j++;
						test4=0;
					}
				}
				
			}
			
			//Count roadname appear mostly
			//
			int i=0, k=0, n=0,x=0,u=0;
			for(i=0;i<j;i++)
			{	
				for(k=0;k<i;k++)
				{
					if(data[i].equals(data[k]))
					{
						for(n=0;data2[n].length()!=0;n++)
						{
							if( data2[n].equals(data[i]))
								break;
						}
						
						for(x=0;aboutTime2[n][x]!=0;x++)
						{
							if(aboutTime2[n][x]==aboutTime[i])
								break;
						}
						if(aboutTime2[n][x]==0)
						{
							aboutTime2[n][x]=aboutTime[i];
							flag[n]++;
						}
						break;
					}	
				}
				if(k==i)
				{
					data2[m]=new String(data[i]);
					aboutTime2[m][0]=aboutTime[i];
					flag[m]=1;
					m++;
				}
				
			}
			
			//Find out Max RoadName
			//
			int maxRoad=0,z=0;
			int[] number=new int[3];
			
			for(z=0;data2[z]!=null;z++)
			{
				if(maxRoad<flag[z])
				{
					maxRoad=flag[z];
					number[0]=z;
				}

			}
			//If there are many RoadName appear 
			//
			for(z=number[0]+1;data2[z]!=null;z++)
			{
				if(flag[z]==maxRoad)
				{
					u++;
					number[u]=z;
				}
			}
			
			//Find out Max and min
			//
			int expensive=0,cheap=100000000,w=0;
			for(u=0;number[u]!=0;u++)
			{
				roadName=data2[number[u]];
				
				for(z=0;z<findAnswer.length();z++)
				{
					json=findAnswer.getJSONObject(z);
					location=json.getString("土地區段位置或建物區門牌");
					totalPrice=json.getInt("總價元");
					if(location.contains(roadName))
					{
						for(w=0;w<50;w++)
						{
							if(totalPrice<cheap)
								cheap=totalPrice;
							if(totalPrice>expensive)
								expensive=totalPrice;
						}

					}
				
				}
				System.out.println(roadName+", 最高成交價: "+expensive+", 最低成交價: "+cheap);
			}
			
		}
		catch(Exception e)
		{
			System.out.println("Somewhere be error");
			e.printStackTrace();
		}
		
		
		
	}
}
