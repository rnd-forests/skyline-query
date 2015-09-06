package skylinequeries.demo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple GUI application to test algorithms.
 *
 * @author Vinh Nguyen
 */
public class Application extends JFrame {
    private final JComboBox tables = new JComboBox(new ComboBox());
    private final JTextArea viewer = new JTextArea();
    private final JTextArea logger = new JTextArea();
    private String selected, selection;

    public Application() {
        super("Skyline Query Algorithms");
        setLayout(new FlowLayout());

        final Font font = new Font("Arial", 0, 15);
        final ButtonListener buttonListener = new ButtonListener();

        JPanel panel1 = new JPanel();
        panel1.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "DATASET"));
        tables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tables.getSelectedIndex() != -1) {
                    selected = (String) tables.getSelectedItem();
                }
            }
        });
        tables.setSelectedIndex(0);
        panel1.add(tables);

        JPanel panel2 = new JPanel();
        panel2.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "ALGORITHMS"));
        final JButton bbsButton = new JButton("BBS");
        final JButton nnButton = new JButton("NN");
        bbsButton.addActionListener(buttonListener);
        nnButton.addActionListener(buttonListener);
        panel2.add(bbsButton);
        panel2.add(nnButton);

        JPanel panel3 = new JPanel();
        panel3.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "FOUND SKYLINE ENTRIES"));
        viewer.setColumns(30);
        viewer.setRows(10);
        viewer.setEditable(false);
        viewer.setFont(font);
        viewer.setBorder(new EmptyBorder(0, 25, 0, 25));
        panel3.add(new JScrollPane(viewer));

        JPanel panel4 = new JPanel();
        panel4.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "LOGS"));
        logger.setColumns(30);
        logger.setRows(15);
        logger.setEditable(false);
        logger.setFont(font);
        logger.setBorder(new EmptyBorder(10, 25, 10, 25));
        logger.append("... Choose a dataset and an algorithm" + "\n");
        panel4.add(new JScrollPane(logger));

        add(panel1);
        add(panel2);
        add(panel3);
        add(panel4);
        setSize(new Dimension(500, 650));
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        final Application testSuite = new Application();
        testSuite.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            final String command = e.getActionCommand();
            if (selected.equals("")) {
                selected = (String) tables.getSelectedItem();
            }

            switch (command) {
                case "BBS":
                    logger.append("... Executing Branch and Bound Skyline (BBS) algorithm\n");
                    logger.append("... Dataset: " + selected + "\n");
                    viewer.setText("");
                    final BBSTest bbs = new BBSTest(selected, viewer, logger);
                    bbs.execute();
                    break;
                case "NN":
                    logger.append("... Executing Nearest Neighbor (NN) algorithm\n");
                    logger.append("... Dataset: " + selected + "\n");
                    viewer.setText("");
                    final NNTest nn = new NNTest(selected, viewer, logger);
                    nn.execute();
                    break;
            }
        }
    }

    private class ComboBox extends AbstractListModel implements ComboBoxModel {
        private final String tables[] = {
                "medium-anti-correlated-2d-points-100000",
                "medium-correlated-2d-points-100000",
                "medium-uniformly-distributed-2d-points-100000",
                "small-anti-correlated-2d-points-10000",
                "small-correlated-2d-points-10000",
                "small-uniformly-distributed-2d-points-10000"
        };

        @Override
        public void setSelectedItem(Object anItem) {
            selection = (String) anItem;
        }

        @Override
        public Object getSelectedItem() {
            return selection;
        }

        @Override
        public int getSize() {
            return tables.length;
        }

        @Override
        public Object getElementAt(int index) {
            return tables[index];
        }
    }
}


