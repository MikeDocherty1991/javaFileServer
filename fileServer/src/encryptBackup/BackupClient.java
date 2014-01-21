package encryptBackup;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.net.*;

public class BackupClient {

	public static void main(String[] args) {

		byte[] buffer = new byte[1024];
		String toBeZipped = "C:\\tempPics";
		String zipname = "file1.zip";
		String myPath = "C:\\tempPics\\3a2b.jpg";
		String zippedDir = "C\\tempftpClient\\" + zipname;
		
		try {

			FileOutputStream fos = new FileOutputStream(zippedDir);
			// where to put it ^
			System.out.println("Created file outstream");
			ZipOutputStream zos = new ZipOutputStream(fos);
			System.out.println("Created zipStream ontop of outstream");
			// creating zip type filter on outstream
			// what is zipped^
			zipDirectory(toBeZipped, zos);
			//zipdir(tobezipped,zos) returns zippedDir
			zos.close();

			System.out.println("Done Zipping ");
			// remember close it
			Socket localSock = new Socket("127.0.0.1", 9001);

			File myFile = new File(myPath);
			//CHANGE TO ZIPPED DIR WHEN DONE

			BufferedInputStream in1 = new BufferedInputStream(
					new FileInputStream(myFile));
			System.out.println("Reading files....");

			int count;
			byte[] buffer2 = new byte[1024];

			OutputStream out = localSock.getOutputStream();
			BufferedInputStream inward = new BufferedInputStream(
					new FileInputStream(myFile));

			while ((count = inward.read(buffer2)) >= 0) {
				out.write(buffer2, 0, count);
			}

			out.flush();
			System.out.println("Finished writing files");
			localSock.close();

		}

		catch (Exception E) {
			System.out.println("Exception E in: " + E);
		}

	}

	public static void zipDirectory(String directoryToBeZipped, ZipOutputStream zos) {
		try {
			
			//creates new folder that everything will be put into. 
			File zipDirectory = new File(directoryToBeZipped);
			// list directory content
			String[] directoryList = zipDirectory.list();
			byte[] readBuffer = new byte[2156];
			int bytesIn = 0;
			// loop through dirList, and zip the files
			
			for (int i = 0; i < directoryList.length; i++) {
				File file_ = new File(zipDirectory, directoryList[i]);
				//recursive directory checker
				if (file_.isDirectory()) {
					
					
					String filePath = file_.getPath();
					zipDirectory(filePath, zos);
					// loop again
					continue;
				}
				
				// create a FileInputStream to file_
				FileInputStream fis = new FileInputStream(file_);
				// create a new zip entry
				ZipEntry anEntry = new ZipEntry(file_.getPath());
				
				zos.putNextEntry(anEntry);
				
				
				while ((bytesIn = fis.read(readBuffer)) != -1) {
					zos.write(readBuffer, 0, bytesIn);
				}
				// close the Stream
				fis.close();
			}
		} catch (Exception e) {
			// handle exception
		}
		//zipper based loosly on : http://www.mkyong.com/java/how-to-compress-files-in-zip-format/
	}

}