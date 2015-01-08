package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

/**
 * Classe utilitária para fazer o Upload da foto
 * @author Rovena Lima
 */

public class Upload {
    
    private static final Upload INSTANCE = new Upload();

    public static Upload getInstance() {
        return INSTANCE;
    }

    private Upload() {}

    /**
     * Método auxiliar para escrever um Part dentro da pasta do servidor Netbeans
     * Por algum motivo há um bug em que o <code>@MultipartConfig</code> não funciona corretamente
     * @param part
     * @throws IOException 
     */
//    public void write(Part part) throws IOException {
//        String fileName = extractFileName(part);
//        //String filePath = System.getProperty("user.home");
//
//        File fileSaveDir = new File(filePath);
//        if (!fileSaveDir.exists()) {
//            fileSaveDir.mkdir();
//        }
//                      
//        
//        part.write(fileName);        
//    }
    
    public String upload(Part part) throws IOException{
        
        File file = File.createTempFile("somefilename-", ".jpg", new File("/var/webapp/images"));

        try (InputStream input = part.getInputStream()) {
            Files.copy(input, file.toPath(), REPLACE_EXISTING); // How to obtain part is answered in http://stackoverflow.com/a/2424824
        }
        
        

        String imageFileName = file.getName();
        
        return imageFileName;
        
    }
    
    public void escreve(Part arquivo) throws IOException {
        
			String content = "This is the content to write into file";
 
			File file = new File("/home/pedro/filename.txt");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
    }

    /**
     * Método genérico que extrai o nome de um determinado Part passado pelos parâmetros
     * @param part
     * @return nome do arquivo 
     */
    public String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
    
}
