/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import twitter4j.Paging;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Fiorenza
 */
@WebServlet(urlPatterns = {"/TwitterResponseServlet"})
public class TwitterResponseServlet extends HttpServlet {

     private RequestToken req = null;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = new PrintWriter(response.getWriter());
        CreaFile cf = new CreaFile();
        HashMap<Integer, TwitterProfile> results = cf.loadTwFile("C:\\Users\\Fiorenza\\Downloads\\dati uni straniere twitter.ods", 12);
        HashMap<Integer, TwitterProfile> completeresults = new HashMap<Integer, TwitterProfile>();
        PrintWriter pw = new PrintWriter("C:\\Users\\Fiorenza\\Downloads\\lista retwittati stranieri.txt");
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey("kx2iZZn0gyJ2yfXqjlvuvg");
        builder.setOAuthConsumerSecret("hGViYGBdvcfgH6UNLK3ASow5OhFISh9j4iuGTJMqA");
        Configuration c = builder.build();
        TwitterFactory factory = new TwitterFactory(c);
        Twitter tw = factory.getInstance();
        String tokenverifier = request.getParameter("oauth_verifier");
        String requestoken = request.getParameter("oauth_token");
        AccessToken at = null;
        Paging paging = new Paging();
        paging.count(200);
       
        int countreply = 0;
        String lastdate = "";
        String firstdate = "";
       
        if (tokenverifier == null && requestoken == null) {
            try {
                req = tw.getOAuthRequestToken();
                String url = req.getAuthorizationURL();
                out.println(url);
            } catch (TwitterException ex) {
                Logger.getLogger(TwitterAuthServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                at = tw.getOAuthAccessToken(req, tokenverifier);
                System.out.println(at);
                RateLimitStatus rtl = tw.getRateLimitStatus().get("/statuses/user:timeline");
                System.out.println(rtl);
                int i = 0;
                int counttweets;
                String message;
                for (TwitterProfile tp : results.values()) {
                    countreply = 0;
                    counttweets = 0;
                    String name = tp.getName();
                    pw.println(name+":");
                    if (!name.startsWith("#")) {
                        System.out.println(name);
                        ResponseList<Status> rls = tw.getUserTimeline(name, paging);
                        for ( Status s : rls){
                            counttweets++;
                            lastdate = s.getCreatedAt().toString();
                            message = s.getText();
                            if (counttweets == 1){
                                firstdate = s.getCreatedAt().toString();
                            }
                            Boolean isrt = s.isRetweet();
                            String user = s.getUser().toString();
                            if(isrt){
                                countreply++;
                                pw.println("ha risposto così"+ message + " a " + user + " alle ore " + lastdate);
                            }
                        }
                        tp.setCountreply(countreply);
                        tp.setCounttweet(counttweets);
                        tp.setFirstdate(firstdate);
                        tp.setLastdate(lastdate);
                        completeresults.put(i, tp);
                    } else {
                        System.out.println("nessun nome disp");
                        completeresults.put(i, tp);
                    }
                    i++;
                    System.out.println(lastdate);
                System.out.println("numero di tweet recuperati "+ i);
                System.out.println("risposte "+ countreply );
                }
                cf.writeTwResponseFile(completeresults, "censimento");
                pw.flush();
                pw.close();
                //HashMap<Integer, TwitterProfile> followee = cf.searchFollowers(results, tw);
                //cf.writecompleteFollowers(followee, tw);
            } catch (TwitterException ex) {
                Logger.getLogger(TwitterAuthServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("http://127.0.0.1:8084/Tesi2/");
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Getting responses";
    }// </editor-fold>
}
