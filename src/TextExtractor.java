import java.io.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class TextExtractor {
	
	private String fileType;
	private File file; 
	
	public static final String PDF_FILE = "PDF";
	public static final String DOC_FILE = "DOC";
	public static final String DOCX_FILE = "DOCX";
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public void setFile(String file_path){
		this.setFile(new File(file_path));
	}

	
	public TextExtractor(String fileType, String file_path){
		setFileType(fileType);
		setFile(file_path);
	}
	
	public TextExtractor(String fileType, File file){
		setFileType(fileType);
		setFile(file);
	}
	
	public String getExtractedText() throws Exception{
		String fileType = getFileType();
		String extractedText = "";
		// Currently extracting from doc the same wai it extracts from DOCX
		if(fileType.equals(PDF_FILE)){
			extractedText = extractFromPDF();
		}else if(fileType.equals(DOCX_FILE)){
			extractedText = extractFromDOCX();
		}else if(fileType.equals(DOC_FILE)){
			extractedText = extractFromDOC();
		}else{
			throw new Exception("Unknown file type.");
		}
		
		return extractedText;
	}


	@SuppressWarnings("all")
	private String extractFromPDF() throws Exception{
		String extractedText = "";
		
		File pdfFile = this.getFile();
		PDDocument doc;
		try {
			doc = PDDocument.load(pdfFile);

			extractedText =  new PDFTextStripper().getText(doc);
			doc.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Unable to extract text.");

		}
		
		return extractedText;
	}
	
	private String extractFromDOCX() throws Exception{
		String extractedText = "";
		
        try {
			FileInputStream fs = new FileInputStream(this.getFile());
            XWPFDocument docx = new XWPFDocument(fs);
            XWPFWordExtractor docxExtractor = new XWPFWordExtractor(docx);
            extractedText = docxExtractor.getText();
            
            docxExtractor.close();
            fs.close();
            docx.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Unable to extract text.");
		}
        
		return extractedText;
	}
	
	private String extractFromDOC() throws Exception{
		String extractedText = "";
		NPOIFSFileSystem fs = null;
		WordExtractor docExtractor = null;
		
		try {
			fs = new NPOIFSFileSystem(getFile());
			docExtractor = new WordExtractor(fs.getRoot());

			extractedText = docExtractor.getText();
			fs.close();
			docExtractor.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Unable to extract text.");
		}
		
		return extractedText;
		
	}
	
}
