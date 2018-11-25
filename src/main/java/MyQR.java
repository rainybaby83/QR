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
import java.awt.print.Printable;
import java.util.Hashtable;

public class MyQR extends JPanel  implements Printable {
    private JLabel[] txt = new JLabel[4];
    private JLabel imgLable = new JLabel();
    private static int qrXY = 72;


    MyQR() {
        this.setLayout(null);
        this.setBackground(Color.white);
        this.setBackground(Color.white);
//        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.gray));
        JLabel[] labels = new JLabel[4];
        labels[0] = new JLabel("编号:");
        labels[1] = new JLabel("名称:");
        labels[2] = new JLabel("产权:");
        labels[3] = new JLabel("部门:");

        for (int i = 0; i < 4; i++) {
            labels[i].setBounds(10, Const.TOP_MARGIN + Const.LABEL_HEIGHT * i,
                    25, Const.LABEL_HEIGHT);
            labels[i].setFont(Const.SONG_9);
            labels[i].setVerticalTextPosition(JLabel.TOP);
//            labels[i].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));

            txt[i] = new JLabel();
            txt[i].setVerticalTextPosition(JLabel.TOP);
//            txt[i].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
            txt[i].setFont(Const.SONG_9);
            txt[i].setBounds(labels[i].getX() + labels[i].getWidth(), Const.TOP_MARGIN + Const.LABEL_HEIGHT * i,
                    Const.LABEL_WIDTH, Const.LABEL_HEIGHT);
            txt[i].setBackground(Color.white);
            this.add(labels[i]);
            this.add(txt[i]);
        }
        //部门字段太长，单独维护高度
        txt[3].setBounds(txt[2].getX(), txt[2].getY() + Const.LABEL_HEIGHT,
                Const.LABEL_WIDTH, Const.LABEL_HEIGHT * 3);
        txt[3].setVerticalAlignment(JLabel.TOP);

        imgLable.setBounds(txt[3].getX() + txt[3].getWidth() - 5, txt[0].getY(), qrXY, qrXY);
        imgLable.setBackground(Color.white);
        this.add(imgLable);
    }


    void setText(String... str) {
        for (int i = 0; i < 3; i++) {
            txt[i].setText(str[i]);
        }
        txt[3].setText("<html><body>" + str[3] + "</body></html>");
    }


    //传入字符串，转化为二维码
    void setQR(String string) {
        BufferedImage bufferedImage = createImage(string);
        imgLable.setIcon(new ImageIcon(bufferedImage));
    }


    //二维码数据转换为图片
    private static BufferedImage createImage(String content) {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = null;
        BufferedImage image = null;

        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrXY, qrXY, hints);
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


    //重写打印方法
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)  {
        this.paint(graphics);
        return Printable.PAGE_EXISTS;
    }

}

