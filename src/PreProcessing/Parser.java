package PreProcessing;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class Parser {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String input = "src/Dati/movieMeta.csv";
		String output = "src/Dati/movieMeta+anno.csv";
		
		
		CSVReader reader = new CSVReader(new FileReader(input ));
		
		CSVWriter writer = new CSVWriter(new FileWriter(output));

		// if the first line is the header
		String[] header = reader.readNext();
		
		String[] new_header = new String[4];
		new_header[0] = header[0];
		new_header[1] = header[1];
		new_header[2] = "Anno";
		new_header[3] = header[2];
		//write header
		writer.writeNext(new_header);
		
		// iterate over reader.readNext until it returns null
		String[] line = reader.readNext();
		while(line != null){
			
			//sistema le virgole nei titoli accorpando le stringhe che non sono la prima o l'ultima
			if(line.length > 3){
				String[] temp = new String[3];
				temp[0] = line[0];
				temp[1] = new String("");
				temp[2] = line[line.length-1];
				for(int i=1; i < line.length-1; i++){
					temp[1] = temp[1] + line[i]; 
				}
				line = temp;
			}
			
			String[] new_line = new String[4];
			new_line[0] = line[0];
			
			char[] c = line[1].toCharArray();
			String sub = null;
			int i = 0;
			for(; i < c.length; i++){
				if(c[i] == '('){
					sub = line[1].substring(i+1, i+5);
					if(isNumeric(sub) && c[i+5]==')'){
						System.out.println(sub);
						break;
					}
				}
				
			}
			
			new_line[1] = line[1].substring(0, i-1);
			
			new_line[2] = sub;
			
			new_line[3] = line[2];
			
			//scrivi la prossima linea
			writer.writeNext(new_line);
			
			line = reader.readNext();
			
			}
		
		reader.close();
		writer.close();
		
	}
	
	public static boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	}  

}
