package Util;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class Xls_Reader {

		public static void main(String[] args) throws Exception {
			String path = "C:\\Java WorkSpace\\DataDrivenFrameWork\\src\\test\\resources\\Excel\\StocksData.xlsx";
			FileInputStream file = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet =  workbook.getSheetAt(0);
			int rows = sheet.getLastRowNum();
			int columns = sheet.getRow(1).getLastCellNum();
			System.out.println(rows);
		}
}
