import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Path;


public class RecursiveListerFrame extends JFrame
{

JLabel title;

JPanel titlePnl;
JPanel mainPnl;
JPanel textPnl;
JPanel buttonPnl;

JScrollPane fileListSP;
JTextArea  fileListTA;

JButton quitBtn;
JButton startBtn;

Path file;

public RecursiveListerFrame()
{

    mainPnl = new JPanel();
    mainPnl.setLayout(new BoxLayout(mainPnl,BoxLayout.Y_AXIS));

    creatTitlePnl();
    mainPnl.add(titlePnl);
    createTextPnl();
    mainPnl.add(textPnl);
    createButtonPnl();
    mainPnl.add(buttonPnl);
    add(mainPnl);

    setSize(400,400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);



}

private void creatTitlePnl()
{

    titlePnl = new JPanel();

    title = new JLabel("Recursive File Lister");
    title.setFont(new Font("Roboto", Font.PLAIN, 36));

    title.setVerticalTextPosition(JLabel.BOTTOM);
    title.setHorizontalTextPosition(JLabel.CENTER);

    titlePnl.add(title);

}
private void createTextPnl()
{

    textPnl = new JPanel();
    fileListTA = new JTextArea();
    fileListTA.setEditable(false);
    fileListSP = new JScrollPane(fileListTA);
    fileListSP.setBorder(new TitledBorder(new EtchedBorder(), "Files Found"));
    fileListSP.setPreferredSize(new Dimension(900,650));
    textPnl.add(fileListSP);
    textPnl.setBackground(Color.decode("#b3e3e5"));

}

private void createButtonPnl()
{

    buttonPnl = new JPanel();
    buttonPnl.setLayout(new GridLayout(1,2));
    buttonPnl.setBackground(Color.decode("#b3e3e5"));

    quitBtn = new JButton("Quit");
    quitBtn.setBackground(Color.decode("#c11914"));
    quitBtn.setForeground(Color.white);
    quitBtn.setFont(new Font("Roboto", Font.BOLD, 56));
    quitBtn.addActionListener((ActionEvent ae) ->
    {int quit = JOptionPane.showConfirmDialog(null,"Are you sure you want to quit?","Quit Confirm", JOptionPane.YES_NO_OPTION);
        if(quit == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    });

    buttonPnl.add(quitBtn);

    JFileChooser fileChoose = new JFileChooser();

    startBtn = new JButton("Start");
    startBtn.setBackground(Color.decode("#52a556"));
    startBtn.setForeground(Color.white);
    startBtn.setFont(new Font("Roboto", Font.BOLD, 56));

    startBtn.addActionListener((ActionEvent ae) ->
    {
        File workingDirectory = new File(System.getProperty("user.dir"));

        fileChoose.setCurrentDirectory(workingDirectory);
        fileChoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (fileChoose.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File toImport = fileChoose.getSelectedFile();
            file = toImport.toPath();
        } else {
            System.out.println("Failed to choose file. Try again.");
            System.exit(0);
        }

        displayFiles(file.toFile());


    });
    buttonPnl.add(startBtn);

}

public void displayFiles(File directoryToSearch)
{
    if(directoryToSearch != null) {
        String[] pathnames;
        File tempDirectory;
        pathnames = directoryToSearch.list();
        for (String pathname : pathnames) {
            tempDirectory = new File(directoryToSearch.getAbsolutePath() + "\\" + pathname);
            if (tempDirectory.isDirectory())
            {
                fileListTA.setText(fileListTA.getText() + "Subdirectory: " + tempDirectory.getAbsolutePath() + "\n");
                displayFiles(tempDirectory);
            }
            else
            {
                fileListTA.setText(fileListTA.getText() + pathname + "\n");
            }
        }
    }
}

}
