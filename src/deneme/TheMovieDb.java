package deneme;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class TheMovieDb extends Discover {
    private final String apiUrl;
    private final String apiKey;
    private String link;

    public TheMovieDb() {
        this.apiUrl = "https://api.themoviedb.org/3";
        this.apiKey = "2f83aa9f8c12d7b99fb65e52dc811b6a";

    }


    // KALDIRACAĞIM LATEST FİLMLERİ DE URL İLE ALACAM
//    public void getLatest() throws IOException {
//        URL url = new URL(apiUrl + "/movie/latest?api_key=" + apiKey +"&language=en-US");
//        JSONObject jsonObject = new JSONObject(baglanti(url));
//        String title = jsonObject.getString("original_title");
//        String overview = jsonObject.getString("overview");
//        System.out.println(title.concat("\n> ").concat(overview));
//    }

    public String linkGenerator(boolean yetiskinOlsunMu, int sirala /* 0 popülarite 1 hasılat */, int tur, int yil, int page) {
        String newUrl = getUrl();
        if (yetiskinOlsunMu) newUrl += yetiskinIcerigiGizleme();
        newUrl += siralama(sirala);
        newUrl += sayfa(page);
        if (tureGore(tur) != null) newUrl+= tureGore(tur);
        return newUrl + belirliTarih(yil);
    }


    public String linkGenerator(boolean yetiskinOlsunMu, int sirala /* 0 popülarite 1 hasılat */, int tur, int yil,int page, boolean once) {
        String newUrl = getUrl();
        if (yetiskinOlsunMu) newUrl += yetiskinIcerigiGizleme();
        newUrl += siralama(sirala);
        newUrl += sayfa(page);
        if (tureGore(tur) != null) newUrl+= tureGore(tur);
        if (once) newUrl += tarihtenOnce(yil);
        else newUrl += tarihtenSonra(yil);
        return newUrl;
    }

    public JSONArray kesfetFilmListesi(String link){
        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(baglanti(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray movies = jsonObj.getJSONArray("results");
        return movies;
    }

    public String findImage(JSONArray movies, int index) {
        JSONObject movie = movies.getJSONObject(index);
        if (movie.isNull("backdrop_path")) {
            if (movie.isNull("poster_path")) return null;
            return movie.getString("poster_path");
        }
        return movie.getString("backdrop_path");

    }
    public String findTitle(JSONArray movies, int index) {
        JSONObject movie = movies.getJSONObject(index);
        return movie.getString("title");
    }

    public String findOriginalTitle(JSONArray movies, int index) {
        JSONObject movie = movies.getJSONObject(index);
        return movie.getString("original_title");
    }


    public int kesfetListeUzunlugu(String link) {
        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(baglanti(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObj.getJSONArray("results").length();
    }

    public int sayfaSayisi(String link, boolean simdiki) {

        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(baglanti(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (simdiki)
            return jsonObj.getInt("page");
        return jsonObj.getInt("total_pages");
    }

    public Image imgParser(String link){
        URL url = null;
        try {
            url = new URL("https://image.tmdb.org/t/p/w500" + link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Image image = null;
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image.getScaledInstance(400,210,image.SCALE_AREA_AVERAGING);
    }

}
