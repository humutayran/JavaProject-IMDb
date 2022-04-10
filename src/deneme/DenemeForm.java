package deneme;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class DenemeForm extends Discover {
    private JPanel panel1;
    private JRadioButton yetişkinIçeriğiGizleRadioButton;
    private JRadioButton popülariteRadioButton;
    private JRadioButton hasılatRadioButton;
    private JScrollPane scrollPane;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel aksiyonLabel;
    private JLabel maceraLabel;
    private JLabel animasyonLabel;
    private JLabel komediLabel;
    private JLabel suçLabel;
    private JLabel belgeselLabel;
    private JLabel tarihLabel;
    private JLabel korkuLabel;
    private JLabel müzikLabel;
    private JLabel gizemLabel;
    private JLabel romantikLabel;
    private JLabel bilimkurguLabel;
    private JLabel gerilimLabel;
    private JLabel savasLabel;
    private JLabel batılıLabel;
    private JLabel dramaLabel;
    private JLabel aileLabel;
    private JLabel fantastikLabel;
    private JLabel kesfetLabel;

    public DenemeForm() {
        JFrame frame = new JFrame();
        frame.setSize(500,500);
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

        // ScrollPane ayarları
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setBackground(new Color(46, 46, 96));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(70, 70, 170);
                this.thumbLightShadowColor = new Color(40, 40, 110);

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
        //

        filmTürüListener(kesfetLabel);
        filmTürüListener(aksiyonLabel);
        filmTürüListener(aileLabel);
        filmTürüListener(animasyonLabel);
        filmTürüListener(batılıLabel);
        filmTürüListener(belgeselLabel);
        filmTürüListener(bilimkurguLabel);
        filmTürüListener(dramaLabel);
        filmTürüListener(fantastikLabel);
        filmTürüListener(gerilimLabel);
        filmTürüListener(gizemLabel);
        filmTürüListener(komediLabel);
        filmTürüListener(korkuLabel);
        filmTürüListener(maceraLabel);
        filmTürüListener(müzikLabel);
        filmTürüListener(romantikLabel);
        filmTürüListener(savasLabel);
        filmTürüListener(suçLabel);
        filmTürüListener(tarihLabel);


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        kesfetLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    kesfet("https://api.themoviedb.org/3" +
                            "/discover/movie?api_key=2f83aa9f8c12d7b99fb65e52dc811b6a&language=tr");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
}
