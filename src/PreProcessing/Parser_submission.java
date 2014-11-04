package PreProcessing;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class Parser_submission {

	public static void main(String[] args) throws IOException {
		String input = "src/Dati/Submission/sampleSubmission.csv";
		String output = "src/Dati/Submission/sampleSubmission2.csv";
		
		
		CSVReader reader = new CSVReader(new FileReader(input ));
		
		CSVWriter writer = new CSVWriter(new FileWriter(output));
		
		String[] header = reader.readNext();
		
		writer.writeNext(header);
		
		String[] line = reader.readNext();
		while(line != null){
			String[] new_line = new String[2];
			new_line[0] = line[0];
			new_line[1] = line[1]+" "+line[2]+" "+line[3]+" "+line[4]+" "+line[5];
			
			writer.writeNext(new_line);
			line = reader.readNext();
		}
		
		reader.close();
		writer.close();
		System.out.println("fine");
	}

}
