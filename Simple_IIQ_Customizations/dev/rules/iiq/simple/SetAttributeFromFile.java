package rules.iiq.simple;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connector.common.Util;

public class SetAttributeFromFile {

	public static void main(String[] args) throws Exception {
		setAttributesFromFile();
	}

	public static void setAttributesFromFile() throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream("F:\\gitHub Projects\\SailPoint IIQ Projects\\artifacts\\empFeed.csv"), "UTF-8"));

		// Get the file header
		String recordInFile = reader.readLine();
		System.out.println("File Header: " + recordInFile + "\n");

		// Store the header fields to List
		// Using java Arrays.asList 
		// List<String> listOfFieldsInHeader = Arrays.asList(recordInFile.split(","));
		// Using SailPoint convertStringToList method
		List<String> listOfFieldsInHeader = Util.convertStringToList(recordInFile, ",");
		System.out.println("List of Fields in Header: " + listOfFieldsInHeader.size() + "\n");

		// Store the rest of the file data into a list
		List<String> listOfEmpRecords = new ArrayList<String>();

		System.out.println("User Records in File:");
		// Read the rest of the employee records in the file
		while (recordInFile != null) {
			recordInFile = reader.readLine();
			if (recordInFile != null) {
				System.out.println(recordInFile);
				// Save the employee records in List
				listOfEmpRecords.add(recordInFile);
			}
		}
		reader.close();

		// Get the index of employee number from file header
		int indexOfEmpNum = listOfFieldsInHeader.indexOf("EMPLOYEE_NUMBER");
		System.out.println("\nIndex of Employee Number in the Header: " + indexOfEmpNum);

		// Get all the employee numbers and store it into List
		List<String> listOfEmpNums = new ArrayList<String>();

		// For each Record in File Save its employee number in list
		for (String record : listOfEmpRecords) {
			// Store the record values to a list
			// List<String> recordValues = Arrays.asList(record.split(","));
			List<String> recordValues = Util.convertStringToList(record, ",");
			String empNum = recordValues.get(indexOfEmpNum);
			listOfEmpNums.add(empNum);
		}

		System.out.println("\nSize of Employee Number List: " + listOfEmpNums.size());

		Map<String, Map> mapOfEmpNumToRecord = new HashMap<String, Map>();

		for (String empRecord : listOfEmpRecords) {
			// Store in List values of Record
			// List<String> listOfValuesInEmpRecord = Arrays.asList(empRecord.split(","));
			List<String> listOfValuesInEmpRecord = Util.convertStringToList(empRecord, ",");

			System.out.println("\nList of Fields in Header: " + listOfFieldsInHeader.size());
			System.out.println("\nList of Values In Emp Record: " + listOfValuesInEmpRecord.size());

			// Map of Attribute Name to Value
			Map<String, String> mapOfAttrNametoValue = new HashMap<String, String>();
			for (int i = 0; i < listOfFieldsInHeader.size(); i++) {
				// Skip Employee Number Attribute
				if (!listOfFieldsInHeader.get(i).equalsIgnoreCase("EMPLOYEE_NUMBER")) {
					mapOfAttrNametoValue.put(listOfFieldsInHeader.get(i), listOfValuesInEmpRecord.get(i));
					System.out.println(mapOfAttrNametoValue);
				}
			}
			// Map of Employee Number to Record
			mapOfEmpNumToRecord.put(listOfValuesInEmpRecord.get(indexOfEmpNum), mapOfAttrNametoValue);
		}
		System.out.println("\nMap of Emp Number to Record: \n" + mapOfEmpNumToRecord);
	}
}
