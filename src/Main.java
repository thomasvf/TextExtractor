import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Main {

	public static PrintWriter stdout = new PrintWriter(
			new OutputStreamWriter(System.out, StandardCharsets.UTF_8),
			true);

	public static void main(String[] args){
		String fileType = args[0];
		String path = args[1];

		printExtractedText(fileType, path);

		//stdout.println("\u22A2 ");
	}


	private static void printExtractedText(String fileType, String path){
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

		/*String fileType = "PDF";
		String path = "enem.pdf";*/
		TextExtractor tx = new TextExtractor(fileType, path);

		try {
			@SuppressWarnings("all")
			String extractedText = tx.getExtractedText();

			stdout.println(extractedText);
			/*PrintWriter out = new PrintWriter("filename.txt");
			out.print(extractedText);
			out.close();*/
		} catch (Exception e) {
			stdout.println("FAILED EXTRACTING TEXT");
		}
	}
}
