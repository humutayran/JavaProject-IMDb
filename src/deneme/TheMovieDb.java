package deneme;

import javax.swing.*;

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

    public String linkGenerator(JRadioButton radioButton) {
        return link;
    }
}
