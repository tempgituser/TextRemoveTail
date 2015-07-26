import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;


public class TextRemoveTail {

	public static final String defaultInputFileName = "T:\\removetail.txt";
	public static final String defaultOutputFileName = "T:\\removetail_result.txt";
	
	/**
	 * 去除每行文字末尾的乱码英文、数字、英文符号。 
	 * 别想对英文文章用
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args != null && args.length >= 1) {
			for (String inputFileName : args) {
				String outputFileName = "T:\\" + new File(inputFileName).getName();
				System.out.println("读入文件：" + inputFileName + "，输出文件：" + outputFileName + "。");
				doRemove(inputFileName, outputFileName);
			}
		} else {
			System.out.println("读入文件：" + defaultInputFileName + "，输出文件：" + defaultOutputFileName + "。运行前自备读入文件。");
			doRemove(defaultInputFileName, defaultOutputFileName);
		}

	}
	
	public static void doRemove(String inputFileName, String outputFileName){
		
		File f = new File(inputFileName);
		if(!f.exists() || !f.canRead()){
			System.out.println("removetail文件不可读");
			return;
		}
		readFileByLines(inputFileName, outputFileName);
	}

	public static void readFileByLines(String inputFileName, String outputFileName) {
        File file = new File(inputFileName);

		File res = new File(outputFileName);
		if (res.exists()) {
			res.delete();
		}
		try {
			res.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        BufferedReader reader = null;
        int line = 1;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
//            	System.out.println("line " + line + ": " + tempString);
//                System.out.println(tempString);
            	int lastUnicodeChar = getLastUnicodeChar(tempString);
				if (lastUnicodeChar == 0) {
					continue;
				}
            	String result = tempString.substring(0, lastUnicodeChar + 1);
                System.out.println(result);
                appendTextToFileEnd(outputFileName, result+"\r\n");
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            System.out.println("转换完成" + line + "行。");
        }
    }
	
	public static int getLastUnicodeChar(String s) {
		int a = 0;
		for (a = s.length()-1; a > 0; a--) {
			int chr = s.charAt(a);
			if (chr > 256) {
				return a;
			}
//			System.out.println(a + " " + chr);
		}
		return a;
	}
	
	public static void appendTextToFileEndTesting(String fileName, String content) {
		try {
			RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
			long fileLength = randomFile.length();
			randomFile.seek(fileLength);
			randomFile.writeBytes(content);
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void appendTextToFileEnd(String fileName, String content) {
		try {
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	
//	public boolean isChinesePunctuation(char c) {
//        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
//        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
//                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
//                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
//                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
//                || ub == Character.UnicodeBlock.VERTICAL_FORMS) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//	
//	String[] specialChar = {"·","～","！","@","#","￥","%","……","&","×","（","）","-","=","——","+","、","|","【","】","『","』","；","‘","：","“","”","’","，","。","/","《","》","？","`","~","!","@","#","$","%","^","&","*","(",")","-","=","_","+","[","]","{","}","\\","|",";","'",":","\"",",",".","/","<",">","?"," "};
//	
//	public static String getLast2SpecialChar(String s){
////		if(){
////			
////		}
//		String result = s.substring(s.length() - 2, s.length());
////		Character.isLetterOrDigit(ch);
//		
//		
//		return result;
//	}
}
