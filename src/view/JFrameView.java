//package view;
//
//import java.awt.*;
//import java.awt.event.ItemEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.awt.image.BufferedImage;
//import java.io.File;
//
//import javax.swing.*;
//import javax.swing.filechooser.FileNameExtensionFilter;
//
//import controller.Features;
//import controller.RgbImageFileIO;
//import model.ImageData;
//
//public class JFrameView extends JFrame implements GuiView{
//  private Features features;
//  private JPanel mainPanel;
//  private JScrollPane mainScrollPane;
//  private JLabel imageLabel;
//  private JLabel imageLabelHist;
//  private JButton fileOpenButton;
//  private JButton fileSaveButton;
//  private JButton blurButton;
//  private JButton sepiaButton;
//  private JButton sharpenButton;
//  private JButton greyscaleButton;
//  private JButton redButton;
//  private JButton blueButton;
//  private JButton greenButton;
//  private JButton colorCorrectButton;
//  private JButton levelsAdjustButton;
//  private JButton horizontalButton;
//  private JButton verticalButton;
//  private JButton compressButton;
//  private JCheckBox splitCheckBox;
//  private JTextField splitTextField;
//  private JButton splitButton;
//  private JLabel splitLabel;
//
//  public JFrameView(String caption){
//    super();
//    setTitle(caption);
//    setSize(1300, 900);
//    // this.setMinimumSize(new Dimension(300,300));
//
//    mainPanel = new JPanel();
//    //for elements to be arranged vertically within this panel
//    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
//    //scroll bars around this main panel
//    mainScrollPane = new JScrollPane(mainPanel);
//    add(mainScrollPane);
//
//    // Main image
//    imageLabel = new JLabel();
//    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
//    mainPanel.add(imageScrollPane);
//    imageScrollPane.setPreferredSize(new Dimension(800, 800));
//
//    // tool bar
//    JPanel toolBarPanel = new JPanel();
//    toolBarPanel.setBorder(BorderFactory.createTitledBorder("Tool bar"));
//    toolBarPanel.setSize(300,900);
//    toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.PAGE_AXIS));
//    mainPanel.add(toolBarPanel);
//
//    // histogram
//    JPanel histogramPanel = new JPanel();
//    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
//    histogramPanel.setPreferredSize(new Dimension(260, 260));
//    toolBarPanel.add(histogramPanel);
//    imageLabelHist = new JLabel();
//    histogramPanel.add(imageLabelHist);
//
//    //file open
//    JPanel featuresPanel = new JPanel();
//    featuresPanel.setBorder(BorderFactory.createTitledBorder("Features"));
//    featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.PAGE_AXIS));
//    toolBarPanel.add(featuresPanel);
//
//    fileOpenButton = new JButton("Load Image");
//    fileOpenButton.setActionCommand("Open file");
//    featuresPanel.add(fileOpenButton);
//
//    //file save
//    fileSaveButton = new JButton("Save Image");
//    fileSaveButton.setActionCommand("Save file");
//    featuresPanel.add(fileSaveButton);
//
//    //blur
//    blurButton = new JButton("Blur");
//    blurButton.setActionCommand("blur");
//    featuresPanel.add(blurButton);
//
//    //Sharpen
//    sharpenButton = new JButton("Sharpen");
//    sharpenButton.setActionCommand("sharpen");
//    featuresPanel.add(sharpenButton);
//
//    //sepia
//    sepiaButton = new JButton("sepia");
//    sepiaButton.setActionCommand("sepia");
//    featuresPanel.add(sepiaButton);
//
//    //Greyscale
//    greyscaleButton = new JButton("Greyscale");
//    greyscaleButton.setActionCommand("Greyscale");
//    featuresPanel.add(greyscaleButton);
//
//    //red
//    redButton = new JButton("red");
//    redButton.setActionCommand("red");
//    featuresPanel.add(redButton);
//
//    //green
//    greenButton = new JButton("green");
//    greenButton.setActionCommand("green");
//    featuresPanel.add(greenButton);
//
//    //blue
//    blueButton = new JButton("blue");
//    blueButton.setActionCommand("blue");
//    featuresPanel.add(blueButton);
//
//    //color-correct
//    colorCorrectButton = new JButton("color-correct");
//    colorCorrectButton.setActionCommand("color-correct");
//    featuresPanel.add(colorCorrectButton);
//
//    //levels-adjust
//    levelsAdjustButton = new JButton("levels-adjust");
//    levelsAdjustButton.setActionCommand("levels-adjust");
//    featuresPanel.add(levelsAdjustButton);
//
//    //horizontal
//    horizontalButton = new JButton("horizontal");
//    horizontalButton.setActionCommand("horizontal");
//    featuresPanel.add(horizontalButton);
//
//    //vertical
//    verticalButton = new JButton("vertical");
//    verticalButton.setActionCommand("vertical");
//    featuresPanel.add(verticalButton);
//
//    //compress
//    compressButton = new JButton("compress");
//    compressButton.setActionCommand("compress");
//    featuresPanel.add(compressButton);
//
//    splitCheckBox = new JCheckBox("Enable Split View");
//    splitTextField = new JTextField(15);
//    splitButton = new JButton("Apply split");
//    splitTextField.setEnabled(false);
//    splitButton.setEnabled(false);
//    splitLabel = new JLabel("Split view: Disabled");
//
//    featuresPanel.add(splitCheckBox);
//    featuresPanel.add(splitTextField);
//    featuresPanel.add(splitButton);
//    featuresPanel.add(splitLabel);
//  }
//
//  @Override
//  public void showWindow(){
//    setVisible(true);
//  }
//
//  @Override
//  public void showDiscardConfirmation(){
//    int result = JOptionPane.showConfirmDialog(JFrameView.this,
//            "Save Changes?", "Unsaved Changes",
//            JOptionPane.YES_NO_OPTION);
//    if (result==JOptionPane.YES_OPTION){
//      features.handleSaveButton();
//    }
//  }
//
//  @Override
//  public void addFeatures(Features features) {
//    this.features = features;
//
//    fileOpenButton.addActionListener(evt -> features.handleLoadButton());
//    fileSaveButton.addActionListener(evt -> features.handleSaveButton());
//
//    blurButton.addActionListener(evt -> features.blur());
//    sharpenButton.addActionListener(evt -> features.sharpen());
//    sepiaButton.addActionListener(evt -> features.sepia());
//    greyscaleButton.addActionListener(evt -> features.greyscale());
//    redButton.addActionListener(evt -> features.red());
//    greenButton.addActionListener(evt -> features.green());
//    blueButton.addActionListener(evt -> features.blue());
//    colorCorrectButton.addActionListener(evt -> features.colorCorrect());
//    horizontalButton.addActionListener(evt -> features.horizontal());
//    verticalButton.addActionListener(evt -> features.vertical());
//
//    levelsAdjustButton.addActionListener(evt -> features.handleLevelsAdjust());
//    compressButton.addActionListener(evt -> features.handleCompress());
//
//    splitCheckBox.addItemListener(it -> features.handleSplitToggle(
//            it.getStateChange()== ItemEvent.SELECTED));
//    splitButton.addActionListener(evt -> features.applySplit(splitTextField.getText()));
//
//    addWindowListener(new WindowAdapter() {
//      @Override
//      public void windowClosing(WindowEvent e) {
//        features.exitProgram();
//      }
//    });
//  }
//
//  @Override
//  public void showCompressMenu() {
//    JTextField textField = getjTextFieldWithNumberInput();
//    JPanel panel = new JPanel();
//    panel.add(new JLabel("Enter compression ratio 0 to 100:"));
//    panel.add(textField);
//    int result = JOptionPane.showConfirmDialog(null, panel, "Compression Input",
//            JOptionPane.OK_CANCEL_OPTION);
//    if (result == JOptionPane.OK_OPTION) {
////      double compressionRatio = Double.parseDouble(textField.getText());
////      features.compress(compressionRatio);
//      features.compress(textField.getText());
//    }
//  }
//
//  private static JTextField getjTextFieldWithNumberInput() {
//    JTextField textField = new JTextField(10);
//    // Set a custom input verifier to enforce numeric input
//    textField.setInputVerifier(new InputVerifier() {
//      @Override
//      public boolean verify(JComponent input) {
//        JTextField textField = (JTextField) input;
//        String text = textField.getText();
//
//        try {
//          // Try to parse the input as a double
//          Double.parseDouble(text);
//          return true; // Input is a valid number
//        } catch (NumberFormatException e) {
//          return false; // Input is not a valid number
//        }
//      }
//    });
//    return textField;
//  }
//
//  @Override
//  public void showLvlAdjMenu() {
//    JTextField textFieldB = getjTextFieldWithNumberInput();
//    JTextField textFieldM = getjTextFieldWithNumberInput();
//    JTextField textFieldW = getjTextFieldWithNumberInput();
//    JPanel panel = new JPanel();
//    panel.add(new JLabel("Enter valid black, mid, white values (0 < b < m < w < 256) :"));
//    panel.add(textFieldB);
//    panel.add(textFieldM);
//    panel.add(textFieldW);
//    int result = JOptionPane.showConfirmDialog(null, panel, "Levels adjust Input", JOptionPane.OK_CANCEL_OPTION);
//    if (result == JOptionPane.OK_OPTION) {
////      int b = Integer.parseInt(textFieldB.getText());
////      int m = Integer.parseInt(textFieldM.getText());
////      int w = Integer.parseInt(textFieldW.getText());
////      features.levelsAdjust(b,m,w);
//      features.levelsAdjust(textFieldB.getText(),textFieldM.getText(),textFieldW.getText());
//    }
//  }
//
//  @Override
//  public void showLoadMenu() {
//    final JFileChooser fchooser = new JFileChooser(".");
//    FileNameExtensionFilter filter = new FileNameExtensionFilter(
//            "JPG, PNG & PPM Images", "jpg", "png", "ppm");
//    fchooser.setFileFilter(filter);
//    int retvalue = fchooser.showOpenDialog(JFrameView.this);
//    if (retvalue == JFileChooser.APPROVE_OPTION) {
//      File f = fchooser.getSelectedFile();
//      features.loadImage(f.getAbsolutePath());
//    }
//  }
//
//  @Override
//  public void showSaveMenu() {
//    final JFileChooser fchooser = new JFileChooser(".");
//    int retvalue = fchooser.showSaveDialog(JFrameView.this);
//    if (retvalue == JFileChooser.APPROVE_OPTION) {
//      File f = fchooser.getSelectedFile();
//      features.saveImage(f.getAbsolutePath());
//    }
//  }
//
//  @Override
//  public void displayImage(ImageData imageData) {
//    BufferedImage image = RgbImageFileIO.convertImgDataToBuffImg(imageData);
//    imageLabel.setIcon(new ImageIcon(image));
//  }
//
//  @Override
//  public void displayHistogram(ImageData imageData) {
//    BufferedImage image = RgbImageFileIO.convertImgDataToBuffImg(imageData);
//    imageLabelHist.setIcon(new ImageIcon(image));
//  }
//
//  @Override
//  public void toggleSplit(boolean supportSplit){
//    splitButton.setEnabled(supportSplit);
//    splitTextField.setEnabled(supportSplit);
//  }
//
//  @Override
//  public void displayError(String message){
//    JOptionPane.showMessageDialog(JFrameView.this, message,
//            "Error", JOptionPane.ERROR_MESSAGE);
//  }
//
//  @Override
//  public void setSplitLabel(String message){
//    splitLabel.setText(message);
//  }
//
//  @Override
//  public void setSplitInput(String message){
//    splitTextField.setText(message);
//  }
//}
