
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Fiorenza
 */
public class InspectTwitterExcel {

    public static String dateParser(String date) throws ParseException {
        GregorianCalendar gc = convertToHourDate(date);
        int day = gc.get(Calendar.DAY_OF_MONTH);
        int month = gc.get(Calendar.MONTH) + 1;
        int year = gc.get(Calendar.YEAR);
        String parseddate = day + "/" + month + "/" + year;
        return parseddate;
    }

    public static GregorianCalendar convertToHourDate(String sdate) throws ParseException {
        Date newdate = new SimpleDateFormat("hh:mm dd/MM/yyyy").parse(sdate);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(newdate);
        return gc;
    }

    public static GregorianCalendar convertToDate(String sdate) throws ParseException {
        String converted = dateParser(sdate);
        Date newdate = new SimpleDateFormat("dd/MM/yyyy").parse(converted);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(newdate);
        return gc;
    }

    public static boolean isSameDay(String date1, String date2) throws ParseException {
        if (!date2.equals("")) {
            GregorianCalendar gc1 = convertToDate(date1);
            GregorianCalendar gc2 = convertToDate(date2);
            if (gc1.equals(gc2)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static int dayDiff(GregorianCalendar start, GregorianCalendar end) {
        long mstart = start.getTimeInMillis();
        long msend = end.getTimeInMillis();
        long diff = mstart - msend;
        int daydiff = (int) (diff / (1000 * 60 * 60 * 24));
        System.out.println("differenza giorni" + daydiff);
        return daydiff;
    }

    public static void main(String[] args) throws ParseException, IOException, IOException {
        File file = new File("C:\\Users\\Fiorenza\\Downloads\\Dati Finali\\Dati Twitter 30-09-2013\\excel");
        //File file = new File("C:\\Users\\Fiorenza\\Downloads\\Dati Politecnici esteri 19-10-2013\\Twitter");
        File[] files = file.listFiles();
        //creazione del file da leggere
        Long timestamp = System.currentTimeMillis();
        Date d = new Date(timestamp);
        FileOutputStream result = new FileOutputStream("riepilogo comunicazionetwitter_italiani+carrozza+UMG.xls");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet rsheet = workbook.createSheet("Foglio 1");
        String[] columns = new String[]{"Università", "Data primo post", "Data ultimo post", "Numero menzioni", "numero retweet", "numero link", "Messaggi totali", "Numero hashtag", "Messaggi scritti in giorni festivi", "varianza", "Hashtag", "Hashtag", "Hashtag", "Hashtag", "Hashtag"};
        ;
        Row header = rsheet.createRow(0);
        for (int col = 0; col < columns.length; col++) {
            Cell headercell = header.createCell(col);
            headercell.setCellValue(columns[col]);
        }

        //questo dizionario contiene le parole indesiderate.
        ArrayList<String> dictionary = new ArrayList<String>();
        dictionary.add("#polimi");
        dictionary.add("#polito");
        dictionary.add("#epfl");
        dictionary.add("#ethz");
        dictionary.add("#eth");
        dictionary.add("#politecnico");

        for (int i = 0; i < files.length; i++) {
            File current = files[i];
            try {
                String filename = current.getName();
                System.out.println(filename);
                int totalmentions = 0;
                int retweets = 0;
                int urls = 0;

                if (!current.getName().equals("Thumbs.db")) {
                    String extension = "";
                    int startext = filename.lastIndexOf(".");
                    if (startext > 0) {
                        extension = filename.substring(startext + 1);
                    }
                    Workbook cw;
                    Sheet sheet = null;
                    FileInputStream fis = new FileInputStream(current);
                    if (extension.equals("xlsx")) {
                        cw = new XSSFWorkbook(fis);
                        sheet = cw.getSheetAt(0);
                    } else if (extension.equals("ods")) {
                        cw = new HSSFWorkbook(fis);
                        sheet = cw.getSheetAt(0);
                    }


                    //strutture per la memorizzazione degli hastag e del numero di post al giorno
                    ArrayList<Hashtag> terms = new ArrayList<Hashtag>();
                    ArrayList<Integer> frequencies = new ArrayList<Integer>();

                    int count = 1;
                    int postfreq = 1; //contatore: post al giorno
                    int realcount = 0;
                    int totalefestivi = 0;

                    Row metadatarow = sheet.getRow(1);
                    String handle = metadatarow.getCell(0).getStringCellValue();
                    handle = handle.replace("@", "");
                    String startdate = metadatarow.getCell(1).getStringCellValue();
                    String startdatenew = dateParser(startdate);

                    String value = "";
                    Number ferialestringa;
                    String previousmessage = "";
                    String previousdate = "";

                    while (true) {
                        Row currentrow = sheet.getRow(count);
                        if (currentrow == null && count % 2 != 0) { //non ci sono più righe piene
                            break;
                        }
                        boolean duplicate = false;
                        value = currentrow.getCell(0).getStringCellValue();
                        //System.out.println(value);
                        if (count % 2 == 0) {  //è un messaggio
                            if (!value.equals("")) {
                                if (value.equals(previousmessage)) {
                                    System.out.println("duplicate found");
                                    duplicate = true;
                                } else {
                                    int parziale = 0;
                                    int parzialeurl = 0;
                                    if (!duplicate) {
                                        if (count != 0) {
                                            realcount++; //conteggio dei post non duplicati
                                        }
                                        //ricerca degli item
                                        String[] pezzi = value.split("\\s+|\\s*,\\s*|\\s*\\.\\s*|\\s*:\\s*");
                                        for (int j = 0; j < pezzi.length; j++) {
                                            if (pezzi[j].startsWith("@")) {
                                                parziale++;
                                            } else if (pezzi[j].startsWith("RT") || pezzi[j].startsWith("MT")) {
                                                retweets++;
                                            } else if (pezzi[j].startsWith("http") || pezzi[j].startsWith("https")) {
                                                parzialeurl++;
                                            } else if (pezzi[j].startsWith("#")) {
                                                boolean found = false;
                                                String newfound = pezzi[j];
                                                for (Hashtag ht : terms) {
                                                    if (ht.getTag().equalsIgnoreCase(newfound)) {
                                                        ht.setOccurrency(ht.getOccurrency() + 1);
                                                        found = true;
                                                        break;
                                                    }
                                                }
                                                if (!found) {
                                                    terms.add(new Hashtag(newfound));
                                                }
                                            }
                                        }
                                    }
                                    totalmentions = totalmentions + parziale;
                                    urls = urls + parzialeurl;
                                }
                            }
                            previousmessage = value;
                        } else {  //parametri
                            if (!duplicate) {
                                String currentdate = currentrow.getCell(1).getStringCellValue();
                                //conteggio dei post per la data specifica

                                if (isSameDay(currentdate, previousdate)) {
                                    postfreq++;
                                } else {
                                    if (count != 1) {
                                        frequencies.add(postfreq);
                                    }
                                    postfreq = 1;
                                    previousdate = currentdate;
                                }
                                ferialestringa = currentrow.getCell(5).getNumericCellValue();  // è 1 se il post è stato pubblicato in un giorno feriale e 0 se in un festivo
                                if (ferialestringa.intValue() == 0 && !duplicate) {
                                    totalefestivi++;
                                }
                            }
                        }
                        count++;
                    }
                    int last = count - 2;
                    Row lastrow = sheet.getRow(last);
                    String enddate = lastrow.getCell(1).getStringCellValue();
                    String enddatenew = dateParser(enddate);
                    ArrayList<Hashtag> filteredterms = new ArrayList<Hashtag>();
                    for (Hashtag ht : terms) {
                        //System.out.println(ht.getTag().toLowerCase());
                        String lowercaseht = ht.getTag().toLowerCase();
                        if (!dictionary.contains(lowercaseht)) { //se il termine non è contenuto nel dizionario di parole da escludere, aggiungilo
                            filteredterms.add(ht);
                        }
                    }
                    Collections.sort(filteredterms);
                    double average = (double) realcount / dayDiff(convertToDate(startdate), convertToDate(enddate));
                    average = Math.round(average * 100);
                    average = average / 100;
                    System.out.println("media" + average);
                    double mediumsquareerror = 0;
                    for (int f : frequencies) {
                        double squaredeviance = (f - average) * (f - average);
                        mediumsquareerror = mediumsquareerror + squaredeviance;
                    }
                    double variance = mediumsquareerror / realcount;
                    variance = Math.round(variance * 100);
                    variance = variance / 100;
                    System.out.println("varianza (post/al giorno)2: " + variance);
                    List<Hashtag> finals = null;
                    if (filteredterms.size() > 20) {
                        finals = filteredterms.subList(0, 20);
                    }
                    System.out.println(frequencies.size());
                    Row writerow = rsheet.createRow(i + 1);
                    //itero tutti i valori
                    Cell c1 = writerow.createCell(0);
                    c1.setCellValue(handle);
                    Cell c2 = writerow.createCell(1);
                    c2.setCellValue(enddatenew);
                    Cell c3 = writerow.createCell(2);
                    c3.setCellValue(startdatenew);
                    Cell c4 = writerow.createCell(3);
                    c4.setCellValue(totalmentions);
                    Cell c5 = writerow.createCell(4);
                    c5.setCellValue(retweets);
                    Cell c6 = writerow.createCell(5);
                    c6.setCellValue(urls);
                    Cell c7 = writerow.createCell(6);
                    c7.setCellValue(realcount);
                    Cell c8 = writerow.createCell(7);
                    c8.setCellValue(terms.size());
                    Cell c9 = writerow.createCell(8);
                    c9.setCellValue(totalefestivi);
                    Cell cvar = writerow.createCell(9);
                    cvar.setCellValue(variance);
                    if (finals != null) {
                        for (int j = 0; j < finals.size(); j++) {
                            Cell c = writerow.createCell(10 + j);
                            c.setCellValue(finals.get(j).toString());
                        }
                    } else {
                        for (int j = 0; j < filteredterms.size(); j++) {
                            Cell c = writerow.createCell(10 + j);
                            c.setCellValue(filteredterms.get(j).toString());
                        }
                    }


                }
            } catch (IOException ex) {
                System.out.println(ex);
            }

        }
        workbook.write(result);
        result.flush();
        result.close();
        //final File res = new File("C:\\Users\\Fiorenza\\Downloads\\ riepilogo comunicazione twitter" + timestamp + ".ods");
    }
}
