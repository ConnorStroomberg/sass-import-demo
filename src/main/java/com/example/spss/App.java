package com.example.spss;

import com.bedatadriven.spss.SpssDataFileReader;
import com.bedatadriven.spss.SpssVariable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Hello world!
 */
public class App
{
	public static void main(String[] args) throws IOException
	{
		String path = "/Users/connor/Documents/Projecten/OA-trial-bank/";
		String fileName = "Arden";
		SpssDataFileReader reader = new SpssDataFileReader(path + fileName + ".sav");
		FileWriter fileWriter = new FileWriter(path + fileName + ".csv");
		PrintWriter printWriter = new PrintWriter(fileWriter);

		String header = reader.getVariables().stream()
							  .map(SpssVariable::getVariableName)
							  .collect(Collectors.joining(", "));

		String typeCode = reader.getVariables().stream()
							  .map(SpssVariable::getTypeCode)
							  .map(String::valueOf)
							  .collect(Collectors.joining(", "));

		System.out.println(header);
		System.out.println(typeCode);
		printWriter.println(header);

		// Read the cases
		while (reader.readNextCase())
		{
			String rowData = IntStream.range(0, reader.getVariables().size())
									  .mapToObj(reader::getDoubleValue)
									  .map(String::valueOf)
									  .collect(Collectors.joining(", "));
			System.out.println(rowData);
			printWriter.println(rowData);
		}

		printWriter.close();
	}
}
