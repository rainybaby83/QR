import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.Hashtable;

public class MyQR extends JPanel  implements Printable {
    private JLabel[] labels = new JLabel[4];
    private JLabel[] txt = new JLabel[4];
    private JLabel imgLable = new JLabel();
    private static final int xx = 80, yy = 80;
    private static final int HEIGHT = 20;
    private static final int BASE_HEIGHT = 35;


    public MyQR() {
        this.setLayout(null);
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.gray));
        labels[0] = new JLabel("编号：");
        labels[1] = new JLabel("名称：");
        labels[2] = new JLabel("产权：");
        labels[3] = new JLabel("部门：");

        for (int i = 0; i < 4; i++) {
            txt[i] = new JLabel();
            labels[i].setBounds(20, BASE_HEIGHT + HEIGHT * i, 40, HEIGHT);
            txt[i].setBounds(55, BASE_HEIGHT + HEIGHT * i, 120, HEIGHT);
            txt[i].setBackground(Color.white);

//            labels[i].setVerticalAlignment(JLabel.TOP);
//            txt[i].setVerticalAlignment(JLabel.TOP);

            this.add(labels[i]);
            this.add(txt[i]);
        }
        //部门字段太长，单独维护高度
        txt[3].setBounds(txt[2].getX(), txt[2].getY() + HEIGHT, 80, HEIGHT * 2);
        txt[3].setVerticalAlignment(JLabel.TOP);

        imgLable.setBounds(150, txt[0].getY(), xx, yy);
        imgLable.setBackground(Color.white);
        this.add(imgLable);
    }


    public void setText(String... str) {
        for (int i = 0; i < 3; i++) {
            txt[i].setText(str[i]);
        }
        txt[3].setText("<html><body>" + str[3] + "</body></html>");
    }

    public void setQR(String string) {
        BufferedImage bufferedImage = createImage(string);
        imgLable.setIcon(new ImageIcon(bufferedImage));
    }


    private static BufferedImage createImage(String content) {

        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = null;
        BufferedImage image = null;


        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, xx, yy, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return image;
    }


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Paper paper = new Paper();
        paper.setSize(2.3622047 * 72, 1.5748031 * 72);
        paper.setImageableArea(0,0,paper.getWidth(),paper.getHeight());
        pageFormat.setPaper(paper);
        this.paint(graphics);
//        Graphics2D g2 = (Graphics2D) graphics;



        if (pageIndex == 0) {
            this.print(graphics);
            return Printable.PAGE_EXISTS;
        } else {
            return Printable.NO_SUCH_PAGE;
        }
    }
}

