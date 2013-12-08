/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import twitter4j.IDs;
import twitter4j.RateLimitStatus;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Fiorenza
 */
public class TwitterAuthServlet extends HttpServlet {

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
       
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey("XXXXXXXXXXXXXXXXXXXxxx");
        builder.setOAuthConsumerSecret("XXXXXXXXXXXXXXXXXXXXX");
        Configuration c = builder.build();
        TwitterFactory factory = new TwitterFactory(c);
        Twitter tw = factory.getInstance();
        String tokenverifier = request.getParameter("oauth_verifier");
        String requestoken = request.getParameter("oauth_token");
        AccessToken at = null;
       
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
                RateLimitStatus rtl = tw.getRateLimitStatus().get("/users/show/:id");
                System.out.println(rtl);
                int i = 0;
                for (TwitterProfile tp : results.values()) {
                    String name = tp.getName();
                    System.out.println(name);
                    if (!name.equals("#")) {
                        User complete = tw.showUser(name);
                        tp.setName(complete.getScreenName());
                        tp.setFollowers(complete.getFollowersCount());
                        tp.setFollowees(complete.getFriendsCount());
                        tp.setDescription(complete.getDescription());
                        tp.setNoTweets(complete.getStatusesCount());
                        tp.setEstablished(complete.getCreatedAt());
                        System.out.println("Dati: " + complete.getDescription() + " " + complete.getScreenName());
                        completeresults.put(i, tp);
                    } else {
                        System.out.println("nessun nome disp");
                        completeresults.put(i, tp);
                    }
                    i++;
                }
                cf.writeTwFile(completeresults, "censimento");
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
        return "Short description";
    }// </editor-fold>
}
