import java.io.*;
import javax.xml.bind.DatatypeConverter;

public class Converter {
    public static void toBase64(String inputFileName) {
        //if (args.length != 3) {
        //    System.out.println("Usage: java ZipToBase64 <input_zip_file_name> <output_txt_file_name> <number_of_parts>");
        //    return;
        //}
        //String inputZipFileName = args[0];
        //String outputTxtFileName = args[0];
        //int numberOfParts = Integer.parseInt(args[2]);

        try {
            InputStream inputStream = new FileInputStream(inputFileName);
            BufferedInputStream bis = new BufferedInputStream(inputStream);

            StringBuilder base64StringBuilder = new StringBuilder();
            byte[] buffer = new byte[4 * 1024]; // 3 KB buffer
            int bytesRead;

            while ((bytesRead = bis.read(buffer)) != -1) {
                byte[] tempBuffer = bytesRead == buffer.length ? buffer : java.util.Arrays.copyOf(buffer, bytesRead);
                base64StringBuilder.append(DatatypeConverter.printBase64Binary(tempBuffer));
            }

            bis.close();

            String base64String = base64StringBuilder.toString();

            int length = base64String.length();
			int partLength = 5000;//5k per file
			int numberOfParts = length / partLength + 1;

            for (int i = 0; i < numberOfParts; i++) {
                String partFileName = inputFileName + "_part_" + (i + 1) + ".txt";
                BufferedWriter bw = new BufferedWriter(new FileWriter(partFileName));

                if (i == numberOfParts - 1) {
                    // Write the remaining part of the string to the last file
                    bw.write(base64String, i * partLength, length - i * partLength);
                } else {
                    bw.write(base64String, i * partLength, partLength);
                }
                bw.close();
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }


    public static String toBin(String[] args) {
        String outputZipFileName = args[0];

        int fileCount = args.length==1 ? 1 : args.length - 1;
        //System.out.println(fileCount);
        String[] inputTxtFileNames = new String[fileCount];
        if (fileCount > 1){
            System.arraycopy(args, 1, inputTxtFileNames, 0, fileCount);
        } else inputTxtFileNames = args;




        try {
            StringBuilder base64StringBuilder = new StringBuilder();
            for (String inputTxtFileName : inputTxtFileNames) {
                //System.out.println(inputTxtFileName);
                BufferedReader br = new BufferedReader(new FileReader(inputTxtFileName));
                char[] buffer = new char[4 * 1024]; // 4 KB buffer
                int charsRead;

                while ((charsRead = br.read(buffer)) != -1) {
                    base64StringBuilder.append(buffer, 0, charsRead);
                }

                br.close();
            }

            String base64String = base64StringBuilder.toString();
            byte[] decodedBytes = DatatypeConverter.parseBase64Binary(base64String);

            FileOutputStream fos = new FileOutputStream(outputZipFileName + ".bin");
            fos.write(decodedBytes);
            fos.close();

            return "Convert to " + outputZipFileName + ".bin" + " successfully.";
        } catch (IOException e) {
            return e.getMessage();
        }
    }	
}
