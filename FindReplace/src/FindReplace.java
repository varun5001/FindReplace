/*
 * Author: Varun Sriram
 * Purpose: Find and Replace content in files in give location from csv(to find the replace string)
 * Inputs: 
 * [1]Source Files Path
 * 		e.g., if files are in(D:\FindReplace)
 * 			D:
 * 				FindReplace
 * 					file1.xml
 * 					file2.xml
 * 						.
 * 						.
 * 						.
 * [2]Destination Folder Path
 * [3]CSV Location that contains find string and replace string
 * Working:
 * create a backup of all files in <sourceFolderPath>\backup
 * 		Method: createBackup(File source, File target)
 * Create findReplaceMap from CSV
 * 		Method: Map<String, String> createReplaceMap(String csvfileName)
 * open each file and do find and replace and save new file in <sourceFolderPath>\ReplacedFiles
 *      Method: findReplace(File sourceFile, File convFile, Map<String, String> findReplaceMap) 
 * 
 * 
 * 
 * */



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


public class FindReplace {

	public static void main(String[] args) throws IOException {
		
		
		String sourceFolderPath = "Files";
		String destFolderPath = sourceFolderPath + "\\ReplacedFiles";
		String findReplaceCSV = sourceFolderPath + "\\findReplace.csv";
		File folder = new File(sourceFolderPath);
		File[] listOfFiles = folder.listFiles();
		System.out.println("*****Backup Intialized******");
		
		
		for(File file:listOfFiles){
			
			if(file.isFile()){
				
				String sourceFilePath = file.getAbsolutePath();
				String bakFilePath = sourceFilePath.substring(0,sourceFilePath.lastIndexOf(File.separator)) + "\\backup\\" + file.getName().replace(sourceFilePath.substring(sourceFilePath.lastIndexOf("."), sourceFilePath.length()), ".bak");
				
				File backupFile = new File(bakFilePath);
				createBackup(file, backupFile);
			}
		}
		
		System.out.println("****Backup Completed*****");
		
		Map<String, String> findReplaceMap = new HashMap<String, String>();
		
		System.out.println("****Find Replace Map Creation Started****");
		
		findReplaceMap = createReplaceMap(findReplaceCSV);
		
		System.out.println("****Find Replace Map Created!*****");
		
		System.out.println("*****Find Replace in Files Intialized******");
		System.out.print("Find Replace in progress");
		
		for(File file:listOfFiles){
			if(file.isFile()){
				
				System.out.print(".");
				
				File convFile = new File(destFolderPath + "\\" + file.getName());
				try {
				convFile.createNewFile();
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
				findReplace(file, convFile, findReplaceMap);
			}
		}
		System.out.println("");
		System.out.println("****Find Replace Action Completed******");
		
	}



	public static void findReplace(File sourceFile, File convFile, Map<String, String> findReplaceMap) throws IOException{
		
		
		BufferedReader br = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		String line;
		try {
			br = new BufferedReader(new FileReader(sourceFile));
			fw = new FileWriter(convFile.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			while((line = br.readLine() )!= null){
				for(Map.Entry<String, String> m1: findReplaceMap.entrySet()){
			    	
				if(line.contains(m1.getKey())){
					
					line = line.replace(m1.getKey(),m1.getValue());
					
				}
				}
				
				bw.write(line+"\n");
			}
			bw.close();
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	public static Map<String, String> createReplaceMap(String csvfileName){
		Map<String, String> _findReplaceMap = new HashMap<String, String>();
		BufferedReader br = null;
		try {
			String line = "";
			String cvsSplitBy = ",";
			br = new BufferedReader(new FileReader(csvfileName));
			while ((line = br.readLine()) != null) {
				
				// use comma as separator
				String[] findReplace = line.split(cvsSplitBy);

				_findReplaceMap.put(findReplace[0], findReplace[1]);

			}
			br.close();
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return _findReplaceMap;
		
	}
	public static void createBackup(File source, File target) throws IOException{
		InputStream in = new FileInputStream(source);
	    OutputStream out = new FileOutputStream(target);
	   
	      byte[] buf = new byte[1024];
	      int len;
	 
	      while ((len = in.read(buf)) > 0) {
	          out.write(buf, 0, len);
	      }
	 
	      in.close();
	      out.close();
	}

}
