



import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.*;

public class MainApp extends JFrame {
    private MyPanel imgPanel;
    private JTextArea richtext = new JTextArea();
    private MyIconButton btnResolve, btnPrint;
    private PrinterJob job;

    private MainApp() {
        super();
        initApp();
        addComponentListener();
        job = PrinterJob.getPrinterJob();

    }


    /**
     * 初始化
     */
    private void initApp() {
        String lookAndFeel = WindowsLookAndFeel.class.getName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception ignored) {
        }

        this.setLayout(null);
        this.setSize(new Dimension(1000, 730));
        this.frameMoveCenter();

        richtext.setBounds(20,20,500,600);
        richtext.setBackground(Color.white);
        this.add(richtext);

        btnResolve = new MyIconButton(Const.ICON_RESOLVE, Const.ICON_RESOLVE_ENABLED, Const.ICON_RESOLVE);
        btnResolve.setLocation(richtext.getX() + richtext.getWidth() + 25, richtext.getY());
        this.add(btnResolve);

        btnPrint = new MyIconButton(Const.ICON_PRINT, Const.ICON_PRINT_ENABLED, Const.ICON_PRINT);
        btnPrint.setLocation(btnResolve.getX(), btnResolve.getY() + btnResolve.getHeight() + 20 );
        this.add(btnPrint);

        imgPanel = new MyPanel();
        imgPanel.setBounds(btnResolve.getX() + btnResolve.getWidth() + 25, 20, 240, richtext.getHeight());
        imgPanel.j.setBounds(imgPanel.getX(),imgPanel.getY(),imgPanel.getWidth()+20,imgPanel.getHeight());
        this.add(imgPanel.j);

    }


    /**
     * 添加监听
     */
    private void addComponentListener() {
        btnResolve.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //组件状态可用、并且左键点击，才可以执行代码
                if ((btnResolve.isEnabled()) && (e.getButton() == MouseEvent.BUTTON1)) {
                    clickBtnResolve();
                }
            }
        });

        btnPrint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //组件状态可用、并且左键点击，才可以执行代码
                if ((btnPrint.isEnabled()) && (e.getButton() == MouseEvent.BUTTON1)) {
                    clickBtnPrint();
                }
            }
        });

    }

    /**
     * 解析
     */
    private void clickBtnResolve() {
        imgPanel.removeAll();
        //1 整理字符，去除tab
        String longstr = richtext.getText().trim();
        longstr = StringUtils.replace(longstr, "\t","，");
        richtext.setText(longstr);

        //2、分组，二维数组
        //3、解析
        String shortStr[] = StringUtils.split(longstr, "\n");
        String unitStr[][] = new String[shortStr.length][];
        int countQR = 0;
        for (int i = 0; i < shortStr.length; i++) {
            unitStr[i] = StringUtils.split(shortStr[i], "，");
            if (unitStr[i].length >= 4) {
                MyQR qr = new MyQR();
                // 顺序：编号 名称  产权  部门
                qr.setText(unitStr[i][1],unitStr[i][0],unitStr[i][3],unitStr[i][2]);
                qr.setQR(unitStr[i][1]);
                qr.setBounds(0, (int) Const.PAPER_HEIGHT * i, Const.PAPER_WIDTH, Const.PAPER_HEIGHT);
                imgPanel.add(qr);
                countQR++;
            }
        }

        //调整下拉框高度
        if (imgPanel.getHeight() < Const.PAPER_HEIGHT * countQR) {
            imgPanel.setPreferredSize(new Dimension(imgPanel.getWidth(), (int) (Const.PAPER_HEIGHT * countQR)));
        }
        imgPanel.j.updateUI();
    }


    /**
     * 打印
     */
     private void clickBtnPrint() {
         boolean ok = job.printDialog();
         if (ok) {
             Paper paper = new Paper();
             PageFormat pf = new PageFormat();
             paper.setSize(Const.PAPER_WIDTH, Const.PAPER_HEIGHT);
             paper.setImageableArea(0,0,paper.getWidth(),paper.getHeight());
             pf.setPaper(paper);

             Book printBook = new Book();
             try {
                 int count = imgPanel.getComponentCount();
                 for (int i = 0; i < count; i++) {
                     Object obj = imgPanel.getComponent(i);
                     if (obj instanceof MyQR) {
                       printBook.append((MyQR)obj, pf);
                     }
                 }
                 job.setPageable(printBook);
                 job.print();

             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
    }


    /**
     * 移动到中间
     */
    private void frameMoveCenter() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - this.getWidth()) / 2,
                (tk.getScreenSize().height - this.getHeight()) / 2);
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainApp window = new MainApp();
                window.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getClass().getSimpleName() + "，" + e.getMessage());
            }
        });
    }


}
