import javax.swing.*;

public class MyPanel extends JPanel {
    JScrollPane j ;

    MyPanel() {
        super();
        j = new JScrollPane(this);
        j.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        j.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        j.setWheelScrollingEnabled(true);
        j.setEnabled(true);
        j.getVerticalScrollBar().setUnitIncrement(40);
        createExample();
    }

    private void createExample() {
        MyQR qr = new MyQR();
        // 顺序：编号 名称  产权  部门
        qr.setText("DN123456","电脑","示例","示例");
        qr.setQR("DN123456");
        qr.setBounds(0, 0, 240, 160);
        this.add(qr);
    }



}
