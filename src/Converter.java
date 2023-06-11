import java.io.*;
import javax.xml.bind.DatatypeConverter;

public class Converter {
    public static void toBase64(File inputZipFile) throws IOException {

        String inputFileName = inputZipFile.getAbsolutePath();

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
			int partLength = 25000000;//50k per file
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
        }

	
    public static void toZip(String outputZipFileName, File[] inputTxtFiles) {

        if(outputZipFileName.isEmpty()){
            outputZipFileName = inputTxtFiles[0].getParent() + "\\UnnamedCombined.zip"; 
        } else{
            outputZipFileName = inputTxtFiles[0].getParent() + "\\" + outputZipFileName;
          }

        try {
            FileOutputStream fos = new FileOutputStream(outputZipFileName, true);

            for (File inputTxtFile : inputTxtFiles) {
                BufferedReader br = new BufferedReader(new FileReader(inputTxtFile));
                char[] buffer = new char[4]; // bug:读取一段转换一段会存在转换不完全而出错（如果读取的buffer不是4的整倍数），一般来说，base64的转换方法是每三个字节转换为4个字符

                while ((br.read(buffer)) != -1) {
                    String str = new String(buffer);
                    byte[] decodedBytes = DatatypeConverter.parseBase64Binary(str);
                    fos.write(decodedBytes);
                    fos.flush();
                }

                br.close();
            }

            fos.close();
        } catch (IOException e) {
            System.err.println("文件读取/写入错误: " + e.getMessage());
        }
    }	
}