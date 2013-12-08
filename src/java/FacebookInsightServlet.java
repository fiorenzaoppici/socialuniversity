/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.googlecode.batchfb.Batcher;
import com.googlecode.batchfb.FacebookBatcher;
import com.googlecode.batchfb.Later;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Fiorenza
 */
@WebServlet(urlPatterns = {"/FacebookInsightServlet"})
public class FacebookInsightServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // reading the user input
        String token = request.getParameter("token");
        HashMap<Integer, Later<FacebookPage>> fbReq = new HashMap<Integer, Later<FacebookPage>>();
        HashMap<Integer, FacebookPage> newUniversities = new HashMap<Integer, FacebookPage>();
        Batcher batcher = new FacebookBatcher(token);
        CreaFile cf = new CreaFile();
        PrintWriter out = response.getWriter();
       // String query1 = "SELECT metric FROM insights WHERE object_id = %s AND metric = '' AND period =period('lifetime')";
        String query = "SELECT name, fan_count, talking_about_count, page_url, page_id, description,about from page WHERE page_id=%s";
        //buggy as the ur
        HashMap<Integer, FacebookPage> results = cf.loadFbFile("C:\\Users\\Fiorenza\\Downloads\\dati uni straniere.ods", 11, 0);
        int number = 0;
        for (int i : results.keySet()) {
            FacebookPage fp = results.get(i);
            String id = fp.getId();
            int index = fp.getUniInt();
            boolean type = fp.isProfile();
            String name = fp.getUniversity();

            if (!id.equals("")) {  //!type DA rimetterreeee!!
                number++;
                String newquery = String.format(query, id);
                System.out.println(newquery + "at index " + index + name);
                System.out.println();
                Later<FacebookPage> fp2 = batcher.queryFirst(newquery, FacebookPage.class);
                fbReq.put(index, fp2);
            } else {
            }
        }
        System.out.println(number);
        for (int i : fbReq.keySet()) {
            FacebookPage f1 = fbReq.get(i).get();
            if (f1 != null) {
                System.out.println(i + " " + f1.getName());
                newUniversities.put(i, f1);
            } else {
                System.out.println("problema con l'id" + i);
            }
        }
        for (int i : newUniversities.keySet()) {
            System.out.println(i + newUniversities.get(i).getName());
        }
        cf.writeFbFile(newUniversities);
    }
}

    