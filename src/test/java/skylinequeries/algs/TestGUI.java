package skylinequeries.algs;

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
public class TestGUI extends JFrame {

    private final JTextArea message = new JTextArea();
    private String selectedTable = "";
    private final JComboBox tables = new JComboBox(new MyComboBoxModel());
    private final JTextArea skylineViewer = new JTextArea();

    public TestGUI() {
        super("Algorithms Benchmark");
        setLayout(new FlowLayout());

        ButtonClickListener buttonClickListener = new ButtonClickListener();
        Font font = new Font("Segoe UI", 0, 15);

        JPanel datasetPanel = new JPanel();
        datasetPanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0),
                "Choose Dataset", TitledBorder.CENTER, TitledBorder.TOP));
        tables.setSelectedIndex(6);
        tables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tables.getSelectedIndex() != -1)
                    selectedTable = (String) tables.getSelectedItem();
            }
        });
        datasetPanel.add(tables);

        JPanel algorithmsPanel = new JPanel();
        algorithmsPanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0),
                "Choose Algorithm", TitledBorder.CENTER, TitledBorder.TOP));
        final JButton bbsButton = new JButton("BBS");
        final JButton nnButton = new JButton("NN");
        bbsButton.addActionListener(buttonClickListener);
        nnButton.addActionListener(buttonClickListener);
        algorithmsPanel.add(bbsButton);
        algorithmsPanel.add(nnButton);

        JPanel displayPanel = new JPanel();
        displayPanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0),
                "Skyline Entries", TitledBorder.CENTER, TitledBorder.TOP));
        skylineViewer.setColumns(28);
        skylineViewer.setRows(8);
        skylineViewer.setEditable(false);
        skylineViewer.setFont(font);
        skylineViewer.setBorder(new EmptyBorder(0, 25, 0, 25));
        displayPanel.add(new JScrollPane(skylineViewer,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));

        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0),
                "Messages", TitledBorder.CENTER, TitledBorder.TOP));
        message.setForeground(Color.RED);
        message.setColumns(29);
        message.setRows(4);
        message.setFont(font);
        message.setBorder(new EmptyBorder(0, 25, 0, 25));
        message.append("Choose a dataset and an algorithm to start." + "\n");
        statusPanel.add(message);

        add(datasetPanel);
        add(algorithmsPanel);
        add(displayPanel);
        add(statusPanel);
        setSize(new Dimension(500, 500));
        setResizable(false);
        setVisible(true);
    }

    private class MyComboBoxModel extends AbstractListModel implements ComboBoxModel {

        private final String tables[] = {
            "large-anti-correlated-2d-points-1000000",
            "large-correlated-2d-points-1000000",
            "large-uniformly-distributed-2d-points-1000000",
            "medium-anti-correlated-2d-points-100000",
            "medium-correlated-2d-points-100000",
            "medium-uniformly-distributed-2d-points-100000",
            "small-anti-correlated-2d-points-10000",
            "small-correlated-2d-points-10000",
            "small-uniformly-distributed-2d-points-10000"
        };

        String selection = null;

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

    private class ButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            final String command = e.getActionCommand();
            if (selectedTable.equals("")) selectedTable = (String) tables.getSelectedItem();

            switch (command) {
                case "BBS":
                    message.setText("Executing Branch and Bound Skyline (BBS) algorithm." + "\n");
                    message.append("Dataset: " + selectedTable + "\n");
                    skylineViewer.setText("");
                    final BBSTest bbs = new BBSTest(selectedTable, skylineViewer);
                    bbs.execute();
                    break;
                case "NN":
                    message.setText("Executing Nearest Neighbor (NN) algorithm." + "\n");
                    message.append("Dataset: " + selectedTable + "\n");
                    skylineViewer.setText("");
                    final NNTest nn = new NNTest(selectedTable, skylineViewer);
                    nn.execute();
                    break;
            }
        }
    }

    public static void main(String[] args) {
        final TestGUI testSuite = new TestGUI();
        testSuite.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
