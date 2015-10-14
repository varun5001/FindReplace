# FindReplace
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
