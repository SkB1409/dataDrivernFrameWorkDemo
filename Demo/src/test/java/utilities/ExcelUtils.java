package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.bytebuddy.asm.Advice.Local;

public class ExcelUtils {

	private static XSSFWorkbook wb;
	private static XSSFSheet sheet;
	private static XSSFCell cell;

	public void setExcelFile(String excelFilePath, String string) throws IOException {

		/*
		 * File file = new File("//Demo//src//test//java//testData//DataSample.xlsx");
		 */
		FileInputStream inputstream = new FileInputStream(excelFilePath);

		wb = new XSSFWorkbook(inputstream); // Creating the workbook instance refers to the file

		sheet = wb.getSheet("Student_Data"); // Creating sheet object and get the sheet name

	}

	public String getCellData(int rowNumber, int cellNumber) {
		cell = sheet.getRow(rowNumber).getCell(cellNumber);

		return cell.getStringCellValue();
	}

		public int getRowCountInSheet() {
		int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
		return rowCount;
	}

	public void setCellValue(int rowNum, int cellNum, String cellValue, String excelFilePath) throws IOException {
		sheet.getRow(rowNum).createCell(cellNum).setCellValue(cellValue);

		FileOutputStream outputStream = new FileOutputStream(excelFilePath);
		wb.write(outputStream);
	}

}
