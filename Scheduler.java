

import java.io.File;

import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Scheduler {

	
	private static final Logger LOGGER = Logger.getLogger(Scheduler.class);
	

	public static void main(String[] args) throws SQLException  {

		
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(LOGGER.getClass() + "Schedular--> Main Method::Enter");
			}
				
			try{			
				
					//Blank workbook
					XSSFWorkbook workbook = new XSSFWorkbook(); 
					
					//Create a blank sheet
					XSSFSheet sheet = workbook.createSheet("Data");
					
					
					JobName job = new JobName();
					
					List<String> IdList=null;
					List<String> StatusList=null;
					
					List<String> updateIdList=null;
					List<String> updateDateList=null;
				
					
					String fileName="Data.xlsx";
					String rootPath = "C:/jboss-as-7.1.1.Final";
					
					System.out.println("Root Path is "+rootPath);
					
					String relativePath = "/ExportExcelData";
				
				    java.util.Date date = new java.util.Date();
				    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");    
				    String today = (dateFormat.format(date)).toString();
					new StringBuilder();
					Map<String,List<String>> map =null;
					//This data needs to be written (Object[])
					Map<String, Object[]> data = new TreeMap<String, Object[]>();
					FileOutputStream out = null;
					File file = null;
					map= job.getContainerData();
					
					for(Entry<String,List<String>> entry : map.entrySet()) {
						 
							 String key= entry.getKey();
							 
						     if(key.equalsIgnoreCase("Id")) {
						    	 
						    	 contIdList=entry.getValue();
						     } else if(key.equalsIgnoreCase("Status")) {
						    
						    	 contStatusList=entry.getValue();
						     }
						     
						     else if(key.equalsIgnoreCase("updateId")) {
								    
						    	 updateIdList=entry.getValue();
						     }
						     else if(key.equalsIgnoreCase("updateDate")) {
								    
						    	 updateDateList=entry.getValue();
						     }
						     				     
					  }
									
					data.put("1", new Object[] {"ID","STATUS_ID",UDATE_ID","UPDATE_DATE"});
					
					for(int i=2; i<(contIdList.size()+2);i++){
					
						data.put(String.valueOf(i), new Object[] 
								{IdList.get(i-2),StatusList.get(i-2),updateIdList.get(i-2),
								 updateDateList.get(i-2)});
					}
					
					//Iterate over data and write to sheet
					Set<String> keyset = data.keySet();
					int rownum = 0;
					for (String key : keyset)
					{
					    Row row = sheet.createRow(rownum++);
					    Object [] objArr = data.get(key);
					    int cellnum = 0;
					    for (Object obj : objArr)
					    {
					       Cell cell = row.createCell(cellnum++);
					       if(obj instanceof String)
					            cell.setCellValue((String)obj);
					        else if(obj instanceof Integer)
					            cell.setCellValue((Integer)obj);
					    }
					}
					try 
						{
							
							//String rootPath = System.getProperty("jboss.home.dir");
						    
						    if (LOGGER.isDebugEnabled()) {
								LOGGER.debug(LOGGER.getClass() + "Path Created for Excel Export"+rootPath + File.separator + relativePath+File.separator +today);
							}
						    
						    System.out.println(rootPath + File.separator + relativePath+File.separator +today);
						    
						    file = new File(rootPath + File.separator + relativePath+File.separator +today );
						    
					        if(!file.exists()){
					        	file.mkdirs();
					        	
					        }
					        
					        //Write the workbook in file system
						    out = new FileOutputStream(new File(rootPath + File.separator + relativePath + File.separator + today +File.separator+fileName));
						    workbook.write(out);
						   
						   SendEmail email = new SendEmail();
						   email.sendEmailWithAttachment();
						   
						    System.out.println("Data.xlsx written successfully on disk.");
						   
						    if (LOGGER.isDebugEnabled()) {
								LOGGER.debug(LOGGER.getClass() + "Data written successfully on disk");
							}
					} 
					catch (Exception e){
						
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug(LOGGER.getClass() + "In catch block while writing Excel file in Server::Schedular Java file");
						}
						    e.printStackTrace();
					}
					finally{
						 out.flush();
						 out.close();
						 
					}
			}	
			catch (Exception e) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(LOGGER.getClass() + "In catch block while generating Excel file in Server::  Schedular Java file");
				}
				 e.printStackTrace();
			}
				
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(LOGGER.getClass() + "Schedular--> Main Method::Exit");
			}		
	
	    }

	}


