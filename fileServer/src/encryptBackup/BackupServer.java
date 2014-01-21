package encryptBackup;

import java.net.*;
import java.util.Date;
import java.io.*;


public class BackupServer {

	public static void main(String[] args){
		
	try{	
		
		ServerSocket storeSocket = new ServerSocket(9001); 
		
		String filepath = "C:\\Users\\Mike\\ReceivingFolder\\"; 
		
		File f = new File(filepath);
		System.out.println("Filecheck");
		boolean fileCheck = (f.exists());
		if (!fileCheck) {
			boolean success = (new File(filepath)).mkdirs();
			if (!success) {
				System.out.println("Issue creating folder");
			}
			if (success)
				System.out.println("folder made!");
		}
		
		System.out.println("Setting folder to write and readable");
		f.setWritable(true);
		f.setReadable(true);
		
		
		
		while (true) {
		Socket socket = storeSocket.accept();
		 Date date = new Date();
	        
	     String time = date.toString();
	     
		
		FileOutputStream fos = new FileOutputStream(filepath + time);
		BufferedOutputStream out = new BufferedOutputStream(fos);
		byte[] buffer = new byte[1024];
		int count; 
		InputStream in = socket.getInputStream();
		
		while ((count = in.read(buffer)) != -1){
			fos.write(buffer,0,count);
		}
		out.flush(); 
		System.out.println("finished reading files "); 
		
		socket.close(); 
		}
				
		
	}
	catch (Exception E) 
	{
		System.out.println("Exception E:" + E); 
	}
	
	}
	
}
