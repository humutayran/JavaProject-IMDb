package deneme;

import org.json.JSONArray;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;

public class KesfetPenceresi {
    private JPanel panel1;
    private JRadioButton yetiskinIcerigiGizleRadioButton;
    private JRadioButton populariteRadioButton;
    private JRadioButton hasilatRadioButton;
    private JScrollPane scrollPane;
    private JPanel leftPanel;
    private JLabel aksiyonLabel;
    private JLabel maceraLabel;
    private JLabel animasyonLabel;
    private JLabel komediLabel;
    private JLabel sucLabel;
    private JLabel belgeselLabel;
    private JLabel tarihLabel;
    private JLabel korkuLabel;
    private JLabel muzikLabel;
    private JLabel gizemLabel;
    private JLabel romantikLabel;
    private JLabel bilimkurguLabel;
    private JLabel gerilimLabel;
    private JLabel savasLabel;
    private JLabel batiliLabel;
    private JLabel dramaLabel;
    private JLabel aileLabel;
    private JLabel fantastikLabel;
    private JLabel kesfetLabel;
    private JTextField tarihAyarla;
    private JPanel scrollPanePanel;
    private JPanel rightPanel;
    private JPanel rightTopPanel;
    private JPanel rightBottomPanel;
    private JButton araKucuk;
    private JButton araEsit;
    private JButton araBuyuk;
    private JLabel sayfaSayisi;
    private JTextField sayfaSimdiki;
    private JLabel birSayfaArtır;
    private JLabel birSayfaAzalt;
    private JPanel filmPaneli = new JPanel(new GridLayout(0, 2, 10, 25));
    private JPanel contentPane = new JPanel();
    private TheMovieDb theMovieDb = new TheMovieDb();
    private int siralama = 0, tur = 0, yil;
    private boolean includeAdult = true;
    private JLabel kesfetLabels[] = new JLabel[] {kesfetLabel, aksiyonLabel, maceraLabel, komediLabel, sucLabel, belgeselLabel,
    animasyonLabel, dramaLabel, aileLabel, fantastikLabel, tarihLabel, korkuLabel, muzikLabel, gizemLabel, romantikLabel,
    bilimkurguLabel, gerilimLabel, savasLabel, batiliLabel};
    private String currentLink =
            "https://api.themoviedb.org/3/discover/movie?api_key=2f83aa9f8c12d7b99fb65e52dc811b6a&language=tr";
    private JLabel currentLabel;
    private int sayfa = 1;
    private Boolean sonAramaTarih = null;

    public KesfetPenceresi() {
        JFrame frame = new JFrame();
        frame.setSize(1500,800);
        frame.add(panel1);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        populariteRadioButton.setSelected(true);

        currentLink = theMovieDb.linkGenerator(includeAdult, siralama, tur, yil, sayfa);
        filmPaneliLayers();
        scrollPaneSettings();
        solPanelItemleri();
        filmPanelOtomasyon();
        filterWRadioButton();
        yilaGoreArama();
        tarihTextArea();
        sayfaDegis();

        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        for (int i = 0; i < 19; i++) {
            kesfetListeners(kesfetLabels[i], i);
        }


    }

    protected void sayfaDegis() {
        sayfaSayisi.setForeground(new Color(187,187,187));
        sayfaSayisi.setText(String.valueOf(theMovieDb.sayfaSayisi(currentLink,false)));
        sayfaSimdiki.setText(String.valueOf(theMovieDb.sayfaSayisi(currentLink,true)));
        sayfaSimdiki.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (Integer.parseInt(sayfaSimdiki.getText()) > 500) {
                        JOptionPane.showMessageDialog(null, "Sayfa sayısı maksimum  500 olmalı.",
                                "HATALI ARAMA", JOptionPane.INFORMATION_MESSAGE);
                    } else if (Integer.parseInt(sayfaSimdiki.getText()) < 1) {
                        JOptionPane.showMessageDialog(null, "1'den daha düşük sayı giremezsin.",
                                "HATALI ARAMA", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        sayfa = Integer.parseInt(sayfaSimdiki.getText());
                        if (sonAramaTarih == null) refreshFilmPaneli();
                        else if (sonAramaTarih) refreshFilmPaneli(true);
                        else refreshFilmPaneli();
                    }
                }
            }
        });
        birSayfaAzalt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (sayfa > 1) {
                    sayfa -= 1;
                    if (sonAramaTarih == null) refreshFilmPaneli();
                    else if (sonAramaTarih) refreshFilmPaneli(true);
                    else refreshFilmPaneli();
                }
            }

        });
        birSayfaArtır.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (sayfa < 500) {
                    sayfa += 1;
                    if (sonAramaTarih == null) refreshFilmPaneli();
                    else if (sonAramaTarih) refreshFilmPaneli(true);
                    else refreshFilmPaneli();
                }
            }
        });
    }

    protected void filterWRadioButton() {
        populariteRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                siralama = 0;
                refreshFilmPaneli();
            }
        });
        hasilatRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                siralama = 1;
                refreshFilmPaneli();
            }
        });
        yetiskinIcerigiGizleRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                includeAdult = !includeAdult;
                refreshFilmPaneli();
            }
        });
    }
    //Tür ScrollPane Ayarları
    protected void scrollPaneSettings() {
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().setBackground(new Color(46, 46, 96));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(11, 11, 35, 181);
            }
            @Override
            protected JButton createIncreaseButton(int orientation)  {
                return createZeroButton();
            }
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }
        });
    }

    // ScrollBar butonlarını kaldırmak için
    protected JButton createZeroButton() {
        JButton button = new JButton("zero button");
        Dimension zeroDim = new Dimension(0,0);
        button.setPreferredSize(zeroDim);
        button.setMinimumSize(zeroDim);
        button.setMaximumSize(zeroDim);
        return button;
    }

    // Tür seçim efektleri
    protected Font d = new Font("JetBrain MONO", Font.BOLD, 12);
    protected Font f = new Font("JetBrain MONO", Font.BOLD, 15);
    private void filmTürüListener(JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                label.setFont(f);
                label.setForeground(new Color(155, 155, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                label.setFont(d);
                label.setForeground(new Color(255, 255, 255));
            }
        });
    }
    protected Font kE = new Font("JetBrain MONO", Font.BOLD, 14);
    protected Font kY = new Font("JetBrain MONO", Font.BOLD, 16);
    private void kesfetListener(JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                label.setFont(kY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                label.setFont(kE);
            }
        });
    }

    public void solPanelItemleri() {
        kesfetListener(kesfetLabel);
        for (int i = 1; i < 19; i++) {
            filmTürüListener(kesfetLabels[i]);
        }
    }

    public void kesfetListeners(JLabel label, int i) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentLabel != null) {currentLabel.setOpaque(false); currentLabel.setBorder(BorderFactory.createEmptyBorder());}
                currentLabel = label;
                if (currentLabel != kesfetLabel){
                    label.setBackground(new Color(200,50,25));
                    label.setBorder(BorderFactory.createMatteBorder(1,1,0,0,Color.red));
                    label.setOpaque(true);
                }
                sayfa = 1;
                tur = i;
                refreshFilmPaneli();
                System.out.println(currentLink);
            }
        });
    }

    //

    public void refreshFilmPaneli() {
        currentLink = theMovieDb.linkGenerator(includeAdult, siralama, tur, yil, sayfa);
        sayfaSimdiki.setText(String.valueOf(sayfa));
        filmPaneli.removeAll();
        System.out.println(currentLink);
        filmPanelOtomasyon();
    }
    public void refreshFilmPaneli(boolean once) {
        currentLink = theMovieDb.linkGenerator(includeAdult, siralama, tur, yil, sayfa, once);
        filmPaneli.removeAll();
        System.out.println(currentLink);
        filmPanelOtomasyon();
    }


    public void filmPanelOtomasyon() {
        new Thread(() -> {
            System.out.println(currentLink);
            int panelSayisi = new TheMovieDb().kesfetListeUzunlugu(currentLink);
            JSONArray movies = new TheMovieDb().kesfetFilmListesi(currentLink);
            for (int i = 0; i < panelSayisi; i++) {
                String currentImage = new TheMovieDb().findImage(movies, i);
                if (currentImage == null) continue;
                JLabel label = new JLabel(new ImageIcon(new TheMovieDb().imgParser(currentImage)));
                JPanel panel = new JPanel(new BorderLayout());
                JLabel textLabel = new JLabel(new TheMovieDb().findTitle(movies, i));
                panel.setBorder(BorderFactory.createMatteBorder(3,3,3,3,new Color(255, 254, 163)));
                panel.setBackground(new Color(107, 107, 107));

                textLabel.setFont(f);
                textLabel.setForeground(new Color(255, 255, 255));
                textLabel.setHorizontalAlignment(JLabel.CENTER);
                textLabel.setVerticalAlignment(JLabel.NORTH);

                Fader fader = new Fader( new Color(26,26,28), 10, 15 );
                fader.add(panel);
                panel.add(label, BorderLayout.NORTH);
                panel.add(textLabel, BorderLayout.CENTER);
                filmPaneli.add(panel);
                filmPaneli.revalidate();
                filmPaneli.repaint();
            }
        }).start();
    }

    public void makePanelsClickable(JPanel panel) {
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void filmPaneliLayers() {
        JPanel holderPanel = new JPanel(new BorderLayout());
        holderPanel.add(filmPaneli, BorderLayout.NORTH);
        holderPanel.add(Box.createGlue(), BorderLayout.CENTER);
        filmPaneli.setBackground(new Color(56, 56, 58, 255));
        JScrollPane filmScrollPane = new JScrollPane(holderPanel);
        filmScrollPane.setBorder(BorderFactory.createEmptyBorder());
        filmScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(filmScrollPane, BorderLayout.CENTER);
        rightPanel.add(contentPane, BorderLayout.CENTER);
    }

    public void yilaGoreArama() {
        Border empty = new EmptyBorder(0, 6, 0, 0);
        araEsit.setBorder(BorderFactory.createEmptyBorder());
        araBuyuk.setBorder(BorderFactory.createEmptyBorder());
        araKucuk.setBorder(BorderFactory.createEmptyBorder());

        araEsit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tarihAyarla.getText().isBlank()) {
                        yil = 0;
                        tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white),empty));
                    }
                    else {
                        yil = Integer.parseInt(tarihAyarla.getText());
                        tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.green),empty));
                    }
                    sonAramaTarih = null;
                    refreshFilmPaneli();
                } catch (Exception exception) {
                    tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.red),empty));
                    JOptionPane.showMessageDialog(null, "Yalnızca rakam kullanarak arama yapabilirsiniz.",
                            "HATALI ARAMA", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        araBuyuk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tarihAyarla.getText().isBlank()) {
                        yil = 0;
                        sonAramaTarih = null;
                        tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white),empty));
                        refreshFilmPaneli();
                    }
                    else {
                        yil = Integer.parseInt(tarihAyarla.getText());
                        sonAramaTarih = false;
                        tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.green),empty));
                        refreshFilmPaneli(false);
                    }
                } catch (Exception exception) {
                    tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.red),empty));
                    JOptionPane.showMessageDialog(null, "Yalnızca rakam kullanarak arama yapabilirsiniz.",
                            "HATALI ARAMA", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        araKucuk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tarihAyarla.getText().isBlank()) {
                        yil = 0;
                        tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white),empty));
                        refreshFilmPaneli();
                    }
                    else {
                        yil = Integer.parseInt(tarihAyarla.getText());
                        sonAramaTarih = true;
                        tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.green),empty));
                        refreshFilmPaneli(true);
                    }
                    System.out.println(currentLink);
                } catch (Exception exception) {
                    tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.red),empty));
                    JOptionPane.showMessageDialog(null, "Yalnızca rakam kullanarak arama yapabilirsiniz.",
                            "HATALI ARAMA", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }

    public void tarihTextArea() {
        tarihAyarla.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                String tarih = tarihAyarla.getText();
                if (tarih.length() > 3) {
                    tarihAyarla.setText(tarih.substring(0, tarih.length() - 1));
                }
            }
        });
    }



}
