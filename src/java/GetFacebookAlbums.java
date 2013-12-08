
import com.googlecode.batchfb.Batcher;
import com.googlecode.batchfb.FacebookBatcher;
import com.googlecode.batchfb.PagedLater;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetFacebookAlbums extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // reading the user input
        String token = request.getParameter("token");
        int totalofAlbums = 0;
        HashMap<University, PagedLater<FacebookAlbum>> fbReq = new HashMap<University,PagedLater<FacebookAlbum>>();
        //HashMap<University, FacebookPage> newUniversities = new HashMap<Integer, FacebookPage>();
        Batcher batcher = new FacebookBatcher(token);
        CreaFile cf = new CreaFile();
        //String query = "select name, photo_count, link, location, created from album where owner=%s";
        HashMap<Integer, FacebookPage> results = cf.loadFbFile("your/path", 12, 0);
        for (int i : results.keySet()) {
            FacebookPage fp = results.get(i);
            String id = (String)fp.getId();
            University u = new University();
            u.setName(fp.getName());
            if (!id.equals("")) {
                PagedLater<FacebookAlbum> albums = batcher.paged(id+"/albums",FacebookAlbum.class);
                fbReq.put(u, albums);
            }
        }
        for (University u : fbReq.keySet()) {           
            String name = u.getName();
            System.out.println(name);
            List<FacebookAlbum> albums = fbReq.get(u).get();
            u.setAlbums(albums);
            for(FacebookAlbum fa : albums){   
                totalofAlbums++;
            }
        }
        cf.writeFbFileAlbum(fbReq.keySet(), totalofAlbums);
    }
}
