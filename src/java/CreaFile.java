
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Fiorenza
 */
public class CreaFile {

    public void writeFbFile(HashMap<Integer, FacebookPage> uni) {
        int count = Collections.max(uni.keySet()) + 1;
        System.out.println(count);
        final Object[][] data = new Object[count][5];
        for (int i = 1; i < count; i++) {
            FacebookPage fp = uni.get(i);
            if (fp != null) {
                data[i] = new Object[]{fp.getName(), fp.getId(), fp.getUrl(), fp.getLikes(), fp.getTalkabout(), fp.getDescription(), fp.getAbout()};
            }
        }

        String[] columns = new String[]{"Nome", "Id", "Url", "Likes", "Ne parlano", "Descrizione", "About"};
        TableModel model = new DefaultTableModel(data, columns);

        // Save the data to an ODS file and open it.
        Long timestamp = System.currentTimeMillis();
        final File file = new File("C:\\Users\\Fiorenza\\Downloads\\rendiconto facebook" + timestamp + ".ods");
        try {
            SpreadSheet.createEmpty(model).saveAs(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeFbFileAlbum(Set<University> set, int count) {
        final Object[][] data = new Object[count][11];
        int i = 0;
        for (University u : set) {
            List<FacebookAlbum> fas = u.getAlbums();
            for (FacebookAlbum fa : fas) {
                data[i] = new Object[]{u.getName(), fa.getId(), fa.getName(), fa.getType(), fa.getLink(), fa.getDescription(), fa.getCreated(), fa.getCount(), fa.isCan_upload(), fa.getUpdated_time()};
                i++;
            }
        }
        String[] columns = new String[]{"Nome università", "Id", "nome album", "tipologia", "Url", "Descrizione", "data creazione", "ultimo aggiornamento", "numero di foto", "si può pubblicare"};
        TableModel model = new DefaultTableModel(data, columns);

        // Save the data to an ODS file and open it.
        Long timestamp = System.currentTimeMillis();
        final File file = new File("C:\\Users\\Fiorenza\\Downloads\\album foto fb" + timestamp + ".ods");
        try {
            SpreadSheet.createEmpty(model).saveAs(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HashMap<Integer, FacebookPage> loadFbFile(String filename, int rows, int sheetnum) throws IOException {
        File file = new File(filename);
        HashMap<Integer, FacebookPage> universities = new HashMap<Integer, FacebookPage>();
        final Sheet sheet = SpreadSheet.createFromFile(file).getSheet(sheetnum);
        for (int row = 1; row <= rows; row++) {
            FacebookPage fp = new FacebookPage();
            fp.setUniInt(row);
            //fp.setUniversity((String) sheet.getValueAt(1, row));
            fp.setId((sheet.getValueAt(3, row)).toString());
            //fp.setUrl((String) sheet.getValueAt(5, row));
            fp.setName((String) sheet.getValueAt(1, row));
            //String type = (String) sheet.getValueAt(8, row);
            //if (type != null && type.equals("profilo")) {
            //    fp.setProfile(true);
            //} else {
            //     fp.setProfile(false);
            // }
            universities.put(row, fp);
        }
        return (universities);
    }

    public HashMap<Integer, TwitterProfile> loadTwFile(String filename, int norows) throws IOException {
        File file = new File(filename);
        HashMap<Integer, TwitterProfile> universities = new HashMap<Integer, TwitterProfile>();
        final Sheet sheet = SpreadSheet.createFromFile(file).getSheet(0);

        for (int row = 0; row <= norows; row++) {  //ricordati di cambiarlo
            TwitterProfile tp = new TwitterProfile();
            tp.setUniversity((String) sheet.getValueAt(1, row));
            tp.setName((String) sheet.getValueAt(3, row));
            universities.put(row, tp);
        }
        //File outputFile = new File("fillingTest.ods");
        return (universities);
    }

    public void writeTwFile(HashMap<Integer, TwitterProfile> uni, String option) {
        int count = uni.size();
        final Object[][] data = new Object[count][6];
        for (int i = 0; i < count; i++) {
            TwitterProfile tp = uni.get(i);
            System.out.println(tp.getCounttweet());
            System.out.println(tp.getCountreply());
            if (tp != null) {
                data[i] = new Object[]{tp.getUniversity(), tp.getName(), tp.getFollowers(), tp.getFollowees(),  tp.getCountreply(),tp.getCounttweet()};
            }
        }
        String[] columns = new String[]{"Università", "Nome Twitter", "Followers", "Followees", "Descrizione", "Data di apertura", "Tweet totali"};
        //String[] columns = new String[]{"Università", "Nome Twitter", "Followers", "Followees", "Descrizione", "Data di apertura", "Tweet totali"};
        TableModel model = new DefaultTableModel(data, columns);

        // Save the data to an ODS file and open it.
        Long timestamp = System.currentTimeMillis();
        final File file = new File("C:\\Users\\Fiorenza\\Downloads\\" + option + timestamp + ".ods");
        try {
            SpreadSheet.createEmpty(model).saveAs(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        //   try {
        //       OOUtils.open(file);
        //   } catch (IOException ex) {
        //       System.out.println("oooppsss");
        //      Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
        //   }

    }

    public void writeTwResponseFile(HashMap<Integer, TwitterProfile> uni, String option) {
        int count = uni.size();
        final Object[][] data = new Object[count][3];
        for (int i = 0; i < count; i++) {
            TwitterProfile tp = uni.get(i);
            if (tp != null) {
                data[i] = new Object[]{tp.getUniversity(), tp.getName(), tp.getCountreply(), tp.getCounttweet(), tp.getFirstdate(), tp.getLastdate()};
            }
        }
        String[] columns = new String[]{"Università", "Nome Twitter", "risposte totali", "totale tweet raccolti", "dal", "al"};
        TableModel model = new DefaultTableModel(data, columns);

        // Save the data to an ODS file and open it.
        Long timestamp = System.currentTimeMillis();
        final File file = new File("C:\\Users\\Fiorenza\\Downloads\\" + option + timestamp + ".ods");
        try {
            SpreadSheet.createEmpty(model).saveAs(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            OOUtils.open(file);
        } catch (IOException ex) {
            System.out.println("oooppsss");
            Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public HashMap<Integer, TwitterProfile> searchFollowers(HashMap<Integer, TwitterProfile> pages, Twitter tw) throws TwitterException {
        IDs list = null;
        int remaining = tw.getRateLimitStatus().get("/friends/ids").getRemaining();
        System.out.println("I miei limiti:" + remaining);
        try {
            for (TwitterProfile tp : pages.values()) {
                System.out.println(remaining + "remaining hits");
                if (remaining == 0) {
                    int timeout = tw.getRateLimitStatus().get("/friends/ids").getSecondsUntilReset();
                    System.out.println("vado a nanna x " + timeout + "secondi");
                    Thread.sleep((timeout * 1000) + 30000);
                    remaining = 15;
                }
                String name = tp.getName();
                HashMap<Integer, TwitterProfile> followers = new HashMap<Integer, TwitterProfile>();
                if (!name.equals("#")) {
                    System.out.println(tp.getName());
                    try {
                        remaining = remaining - 1;
                        list = tw.getFriendsIDs(name, -1);
                        long[] ids = list.getIDs();
                        for (int i = 0; i < ids.length; i++) {
                            TwitterProfile newp = new TwitterProfile();
                            newp.setId(ids[i]);
                            followers.put(i, newp);
                        }
                        tp.setFolloweesList(followers);
                    } catch (TwitterException ex) {
                        Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pages;
    }

    public void writecompleteFollowers(HashMap<Integer, TwitterProfile> followers, Twitter tw) {
        int followerscount = 0;
        int remaining = 180;
        int timeout = 0;
        try {
            remaining = tw.getRateLimitStatus().get("/users/show/:id").getRemaining();
        } catch (TwitterException ex) {
            Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (TwitterProfile tp : followers.values()) {
            for (TwitterProfile tp1 : tp.getFolloweesList().values()) {
                if (remaining == 0) {
                    try {
                        timeout = tw.getRateLimitStatus().get("/users/show/:id").getSecondsUntilReset();
                        try {
                            System.out.println("vado a nanna x " + timeout / 60 + "minuti");
                            Thread.sleep(timeout * 1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (TwitterException ex) {
                        Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    remaining = 180;
                }
                long id = tp1.getId();
                try {
                    User u = tw.showUser(id);
                    remaining--;
                    tp1.setName(u.getScreenName());
                    tp1.setDescription(u.getDescription());
                    tp1.setLocation(u.getLocation());
                    System.out.println(tp.getName() + " " + tp.getLocation() + " " + tp.getDescription());
                } catch (TwitterException ex) {
                    Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
                }
                followerscount++;
            }
        }
        final Object[][] data = new Object[followerscount][5];
        int index = 0;
        for (TwitterProfile tp : followers.values()) {
            for (TwitterProfile tp1 : tp.getFollowersList().values()) {
                if (tp1 != null) {
                    data[index] = new Object[]{tp.getId(), tp.getUniversity(), tp1.getId(), tp1.getName(), tp1.getLocation(), tp.getDescription()};
                }
                index++;
            }
        }
        String[] columns = new String[]{"Id università", "Nome università", "Id follower", "Nome Follower", "Location", "Descrizione"};
        TableModel model = new DefaultTableModel(data, columns);

        // Save the data to an ODS file and open it.
        Long timestamp = System.currentTimeMillis();
        final File file = new File("C:\\Users\\Fiorenza\\Downloads\\datifollowers" + timestamp + ".ods");
        try {
            SpreadSheet.createEmpty(model).saveAs(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            OOUtils.open(file);
        } catch (IOException ex) {
            System.out.println("oooppsss");
            Logger.getLogger(CreaFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
