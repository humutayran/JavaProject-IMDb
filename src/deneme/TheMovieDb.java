package deneme;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TheMovieDb extends Discover {
    private final String apiUrl;
    private final String apiKey;
    private String link;

    public TheMovieDb() {
        this.apiUrl = "https://api.themoviedb.org/3";
        this.apiKey = "2f83aa9f8c12d7b99fb65e52dc811b6a";

    }

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

    public String araLinkGenerator(String aranilacak, boolean yetiskinOlsunMu, int yil, int page, boolean once) {
        String newUrl = "https://api.themoviedb.org/3/search/movie?api_key=2f83aa9f8c12d7b99fb65e52dc811b6a&language=tr";
        newUrl += sorgu(aranilacak);
        if (yetiskinOlsunMu) newUrl += yetiskinIcerigiGizleme();
        newUrl += sayfa(page);
        if (once) newUrl += tarihtenOnce(yil);
        else newUrl += tarihtenSonra(yil);
        return newUrl;
    }

    public String araLinkGenerator(String aranilacak, boolean yetiskinOlsunMu, int yil, int page) {
        String newUrl = "https://api.themoviedb.org/3/search/movie?api_key=2f83aa9f8c12d7b99fb65e52dc811b6a&language=tr";
        newUrl += sorgu(aranilacak);
        if (yetiskinOlsunMu) newUrl += yetiskinIcerigiGizleme();
        newUrl += sayfa(page);
        return newUrl + belirliTarih(yil);
    }

    public ArrayList<String> genres(JSONArray movies, int index) {
        JSONObject movie = movies.getJSONObject(index);
        JSONArray turler = movie.getJSONArray("genre_ids");
        ArrayList<String> genres = new ArrayList<String>();
        for (int i = 0; i < turler.length(); i++) {
            int tur = turler.getInt(i);
            switch (tur) {
                case 12 : genres.add("macera"); break;
                case 35 : genres.add("komedi"); break;
                case 80 : genres.add("suç"); break;
                case 99 : genres.add("belgesel"); break;
                case 16 : genres.add("animasyon"); break;
                case 18 : genres.add("drama"); break;
                case 10751 : genres.add("aile"); break;
                case 14 : genres.add("fantastik"); break;
                case 36 : genres.add("tarih"); break;
                case 27 : genres.add("korku"); break;
                case 10402 : genres.add("müzikal"); break;
                case 9648 : genres.add("gizem"); break;
                case 10749 : genres.add("romantik"); break;
                case 878 : genres.add("bilim kurgu"); break;
                case 53 : genres.add("gerilim"); break;
                case 10752 : genres.add("savaş"); break;
                case 37 : genres.add("batılı"); break;
            }
        }
        return genres;
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
        if (movie.isNull("poster_path")) {
            if (movie.isNull("backdrop_path")) return null;
            return movie.getString("backdrop_path");
        }
        return movie.getString("poster_path");

    }
    public String findTitle(JSONArray movies, int index) {
        JSONObject movie = movies.getJSONObject(index);
        String title = movie.getString("title").trim();
        if (title.length() > 30) {
            title = "<html>" + title + "</html>";
            StringBuilder str = new StringBuilder(title);
            int ayrilacak = title.indexOf(" ", 20);
            if (ayrilacak == -1) {
                str.insert(25, "<br/>");
                return str.toString();
            }
            System.out.println("boşluk indexi: " + str.indexOf(" ") + " ayrilacak: " + ayrilacak);
            str.deleteCharAt(ayrilacak);
            str.insert(ayrilacak, "<br/>");

            if (str.substring(ayrilacak + 5).length() > 30) {
                int ayrilacak2 = title.indexOf(" ", ayrilacak+10);
                System.out.println(title + ayrilacak2);
                if (ayrilacak2 == -1) {
                    str.insert(50, "<br/>");
                    return str.toString();
                }
                str.deleteCharAt(ayrilacak2 + 4);
                str.insert(ayrilacak2 + 4, "<br/>");
            }
            return str.toString();
        }
        return title;
    }

    public String filmTitle(JSONArray movies, int index) {
        JSONObject movie = movies.getJSONObject(index);
        String title = movie.getString("title").trim();
        return title;
    }

    public ImageIcon backdropPic(JSONArray movies, int index) {
        JSONObject movie = movies.getJSONObject(index);
        if (movie.isNull("backdrop_path")) {
            if (movie.isNull("poster_path")) return null;
            return new ImageIcon(imgParser(movie.getString("poster_path"), false));
        }
        return new ImageIcon(imgParser(movie.getString("backdrop_path"), false));
    }

    public int findId(JSONArray movies, int index) {
        JSONObject movie = movies.getJSONObject(index);
        return movie.getInt("id");
    }



    public String findOriginalTitle(JSONArray movies, int index) {
        JSONObject movie = movies.getJSONObject(index);
        return movie.getString("original_title");
    }

    public String findOverview(JSONArray movies, int index) {
        JSONObject movie = movies.getJSONObject(index);
        return movie.getString("overview");
    }

    public String findReleaseDate(JSONArray movies, int index) {
        JSONObject movie = movies.getJSONObject(index);
        return movie.getString("release_date");
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

    public Image imgParser(String link, boolean i){
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
        if (i) return image.getScaledInstance(240,360, image.SCALE_SMOOTH);
        return image;
    }
}
