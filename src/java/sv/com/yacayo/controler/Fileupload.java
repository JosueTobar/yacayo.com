package sv.com.yacayo.controler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.mail.Part;

/**
 *
 * @author david.poncefgkss
 */
@ManagedBean
@RequestScoped
public class Fileupload {
    
private Part uploadedFile;

	private String folder = "c:\\files";

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	
//	public void saveFile(){
//		
//		try (InputStream input = uploadedFile.getInputStream()) {
//			String fileName = uploadedFile.getSubmittedFileName();
//	        Files.copy(input, new File(folder, fileName).toPath());
//	    }
//	    catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	}
    
}
