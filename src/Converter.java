import java.io.*;
import javax.xml.bind.DatatypeConverter;

public class Converter {

    public static void toBase64(File inputZipFile, int numOfFileParts) throws IOException {

        long inputZipFileSizeInByte = inputZipFile.length();

        String inputFileName = inputZipFile.getAbsolutePath();

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFileName));

        byte[] buffer = new byte[3 * 1024]; // 3 B buffer
        int fileCount = 0;
        long bytesConverted = 0;
        BufferedWriter bw = new BufferedWriter(new FileWriter(inputZipFile.getAbsolutePath() + "_part_" + fileCount + ".txt", true));
        while (bis.read(buffer) != -1) {
            if (bytesConverted >= (inputZipFileSizeInByte / numOfFileParts) ){
                bytesConverted = 0;
                bw.close();
                fileCount++;
                bw = new BufferedWriter(new FileWriter(inputZipFile.getAbsolutePath() + "_part_" + fileCount + ".txt", true));
            }

            String str = DatatypeConverter.printBase64Binary(buffer);
            bw.write(str);
            bw.flush();
            bytesConverted = bytesConverted + buffer.length;
        }

        bw.close();
        bis.close();
    }

	
    public static void toZip(String outputZipFileName, File[] inputTxtFiles) throws IOException {

        if(outputZipFileName.isEmpty()){
            outputZipFileName = inputTxtFiles[0].getParent() + "\\UnnamedCombined.zip"; 
        } else{
            outputZipFileName = inputTxtFiles[0].getParent() + "\\" + outputZipFileName;
          }

        FileOutputStream fos = new FileOutputStream(outputZipFileName, true);

        for (File inputTxtFile : inputTxtFiles) {
            BufferedReader br = new BufferedReader(new FileReader(inputTxtFile));
            char[] buffer = new char[4]; // bug:��ȡһ��ת��һ�λ����ת������ȫ�����������ȡ��buffer����4������������һ����˵��base64��ת��������ÿ�����ֽ�ת��Ϊ4���ַ�

            while ((br.read(buffer)) != -1) {
                String str = new String(buffer);
                byte[] decodedBytes = DatatypeConverter.parseBase64Binary(str);
                fos.write(decodedBytes);
                fos.flush();
            }

            br.close();
        }

        fos.close();
    }
}