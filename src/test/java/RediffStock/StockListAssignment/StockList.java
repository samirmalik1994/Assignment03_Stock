package RediffStock.StockListAssignment;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class StockList {

    public static void main(String[] args) throws EncryptedDocumentException, IOException, InterruptedException {

        Map<String, String> Testdata1 = ExcelSheetData();

        for (Entry<String, String> Map1 : Testdata1.entrySet()) {

            System.out.println("Key is: " + Map1.getKey() + " ," + "Value is: " + Map1.getValue());

        }

        Map<String, String> Testdata2 = WebtableData();
        for (Entry<String, String> Map2 : Testdata2.entrySet()) {

            System.out.println("Key is: " + Map2.getKey() + " ," + "Value is: " + Map2.getValue());

        }

        System.out.println(".....Two Hashmap Comparision for All keys........");

        System.out.println(Testdata1.keySet().equals(Testdata2.keySet()));

        System.out.println(".....Two Hashmap Comparision........");

        boolean isValid = validateValues(Testdata1, Testdata2, "BGR Energy Systems");
        System.out.println("Are values valid for key 'BGR Energy Systems'? " + isValid);
        // System.out.println(isValid);;

    }

    public static boolean validateValues(Map<String, String> testdata1, Map<String, String> testdata2, String key) {
        if (testdata1.containsKey(key) && testdata2.containsKey(key)) {
            return testdata1.get(key).equals(testdata2.get(key));
        } else {
            return false;
        }
    }

    public static Map<String, String> ExcelSheetData() throws EncryptedDocumentException, IOException {
        System.out.println("............Data from Excel sheet........");
        Map<String, String> excelmap = new HashMap<String, String>();

        FileInputStream file = new FileInputStream("C:\\Users\\10737770\\OneDrive-LTIMindtree\\Desktop\\RediffStoreData.xlsx");
        Sheet sh = WorkbookFactory.create(file).getSheet("RediffData");
        int LastRow = sh.getLastRowNum();
        for (int i = 0; i <= LastRow; i++) {
            Row row = sh.getRow(i);
            Cell KeyCell = row.getCell(0);
            String Key = KeyCell.getStringCellValue().trim();
            Cell ValueCell = row.getCell(1);
            String Value = ValueCell.getStringCellValue().trim();

            excelmap.put(Key, Value);
        }
        return excelmap;
    }

    public static Map<String, String> WebtableData() throws InterruptedException {

        System.out.println("............Data from Website sheet........");

        System.setProperty("web driver.chrome.driver", "C:\\Users\\user\\Downloads\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https:/money.rediff.com/losers/bse/daily/groupall");
        driver.manage().window().maximize();
        Thread.sleep(5000);

        Map<String, String> webtablemap = new HashMap<String, String>();

        List<WebElement> companyName = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr/td[1]"));
        List<WebElement> currentPrize = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr/td[4]"));

        for (int i = 0; i <= 20; i++) {
            String coloum1data = companyName.get(i).getText();
            String coloum2data = currentPrize.get(i).getText();
            webtablemap.put(coloum1data, coloum2data);
        }
        driver.quit();
        return webtablemap;
    }

}

