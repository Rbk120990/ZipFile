package fileCompression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.testng.annotations.Test;

public class Zip {

	protected String input_file="D:\\Bala";
	File file=new File(input_file);
	
	@Test
	public void main() throws IOException {
		boolean file_exist=file.exists(); 
		if(file_exist == true) {
		System.out.println("Input string is an existing filepath");
		boolean isfile=file.isFile();
		if(isfile==true) {
			System.out.println("Input string is File");
			zip_File();
		}
		else
		{
			System.out.println("Input string is Directory");
			zip_Directory();
		}
		}else{
			System.out.println("Input string is not an existing filepath");
		}
	}
	
	public void zip_File() throws IOException {
		FileOutputStream fos=new FileOutputStream("D:\\EGPAF\\output.zip");
		ZipOutputStream zos=new ZipOutputStream(fos);
		FileInputStream fis=new FileInputStream(file);
		ZipEntry zin=new ZipEntry(file.getName());
		zos.putNextEntry(zin);
		byte[] bytes=new byte[1024];
		int length;
		while((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }
        zos.close();
        fis.close();
        fos.close();
	}
	
	public void zip_Directory() throws IOException {
		FileOutputStream fos=new FileOutputStream("C:\\Users\\Balakrishnan.r\\Desktop\\zipdirectory.zip");
		ZipOutputStream zos=new ZipOutputStream(fos);
		zipFile(file, file.getName(), zos);
		zos.close();
		fos.close();
	}
	
	public void zipFile(File file, String fileName, ZipOutputStream zos ) throws IOException {
		if (file.isHidden()) {
            return;
        }
        if (file.isDirectory()) {
            if (fileName.endsWith("/")) {
                zos.putNextEntry(new ZipEntry(fileName));
                zos.closeEntry();
            } else {
                zos.putNextEntry(new ZipEntry(fileName + "/"));
                zos.closeEntry();
            }
            File[] children = file.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zos);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zos.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }
        fis.close();
    }
}
