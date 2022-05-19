
package deneme;

import java.util.Objects;
/**
 * IMDB uygulaması için oluşturulmuş <i>Kullanici</i> clasıdır.<hr>
 * <ol>
 *  <li type="square">(f)-> <code>String kullaniciAdi</code> </li>
 *  <li type="square">(f)-> <code>String sifre</code> </li>
 *  <li type="square">(f)-> <code>ArrayList(Puan) puanlamalar</code> </li>
 *  <li type="square">(f)-> <code>ArrayList(Yorum) yorumlar</code> </li>
 *  <li type="disc">(m)-> <code>Kullanici()</code> </li>
 *  <li type="circle">(m)-> <code>setters() getters()</code> </li>
 *  <li type="circle">(m)-> <code>hash()-equals()</code> (depends kullaniciAdi ve sifre) </li>
 * </ol>
 * @author OEkrem
 */
public class Kullanici {
    private String kullaniciAdi;
    private String sifre;

    public Kullanici(String kullaniciAdi, String sifre){
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.kullaniciAdi);
        hash = 67 * hash + Objects.hashCode(this.sifre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Kullanici other = (Kullanici) obj;
        if (!Objects.equals(this.kullaniciAdi, other.kullaniciAdi)) {
            return false;
        }
        if (!Objects.equals(this.sifre, other.sifre)) {
            return false;
        }
        return true;
    }
}
