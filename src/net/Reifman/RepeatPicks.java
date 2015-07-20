package net.Reifman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class RepeatPicks {

	public static boolean repeat(String last, String first) throws IOException{
		File f = new File("playersDrafted.txt");
		boolean repeated = false;
		try {
			Scanner reader = new Scanner(f);
			//First, check if the player has been drafted already or not.
			while(reader.hasNext()){
				String tempLast = reader.next();
				String tempFirst = reader.next();
				if(tempLast.equalsIgnoreCase(last) && tempFirst.equalsIgnoreCase(first)){ //player has already been drafted
					repeated = true;
				}
			}
			reader.close();
			if(repeated == false){
				try {
					FileWriter writer = new FileWriter("playersDrafted.txt",true);
					writer.write(last +" ");
					writer.write(first+"\n");
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			f.createNewFile();
			try {
				FileWriter writer = new FileWriter("playersDrafted.txt",true);
				writer.write(last + " ");
				writer.write(first+ "\n");
				writer.close();
			} catch (IOException t) {
				// TODO Auto-generated catch block
				t.printStackTrace();
			}
		}
		
		return repeated;
	}
}
