package view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;
import controller.RgbImageFileIO;
import model.ImageData;

/**
 * JFrameViewSplit is a class that extends JFrame and implements the GuiView interface.
 * It represents a graphical user interface for image processing applications, providing a window with various
 * components for image display, feature selection, and user interaction.
 * <p>
 * The class includes methods for displaying images, histograms, and error messages, as well as handling user interactions
 * such as loading, saving, and applying image processing features. It also provides methods for toggling preview mode,
 * resetting dropdowns, and showing different menus for compression and levels adjustment.
 * <p>
 * The GUI components include buttons, sliders, labels, and panels arranged to provide a user-friendly interface
 * for interacting with image processing features.
 */
public class JFrameViewSplit extends JFrame implements GuiView {

  private Features features;
  private JPanel mainPanel;
  private JScrollPane mainScrollPane;
  private JLabel imageLabel;
  private JLabel imageLabelHist;
  private JButton fileOpenButton;
  private JButton fileSaveButton;
  //    private JTextField splitTextField;
  private JSlider splitSlider;
  //private JButton previewButton;
  private JLabel splitLabel;

  private JButton applyButton;
  private JButton cancelButton;

  private JPanel featuresPanel;
  private JPanel previewPanel;

  private JComboBox<String> dropdown;
  private JLabel loadLabel;

  private String defaultDropdownOption;

  private JPanel compressionPanel;
  private JSlider compressionSlider;
  private JPanel lvlAdjPanel;

  private JSlider lvlAdjSliderB;
  private JSlider lvlAdjSliderM;
  private JSlider lvlAdjSliderW;

  /**
   * This constructor initializes the JFrameViewSplit with the provided caption and sets up the main components
   * for the graphical user interface (GUI). The GUI includes a main panel with scroll bars, an image panel,
   * a toolbar panel with buttons and sliders, a histogram panel, a features panel, and a preview panel.
   *
   * @param caption The caption/title to be set for the JFrameViewSplit window.
   */
  public JFrameViewSplit(String caption) {
    super();
    setTitle(caption);
    setSize(1300, 900);
    // this.setMinimumSize(new Dimension(300,300));
    defaultDropdownOption = "Select an operation";
    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    // Main image
    JPanel imagePanel = new JPanel();
    imageLabel = new JLabel();
    imagePanel.add(imageLabel);
    JScrollPane imageScrollPane = new JScrollPane(imagePanel);
    mainPanel.add(imageScrollPane);
    imageScrollPane.setPreferredSize(new Dimension(800, 800));
//        imageScrollPane.setBackground(Color.darkGray);
    // tool bar
    JPanel toolBarPanel = new JPanel();
    toolBarPanel.setBorder(BorderFactory.createTitledBorder("Tool bar"));
    toolBarPanel.setSize(300, 900);
    toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(toolBarPanel);


    // histogram
    JPanel histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setPreferredSize(new Dimension(260, 260));
    toolBarPanel.add(histogramPanel);
    imageLabelHist = new JLabel();
    histogramPanel.add(imageLabelHist);
//        histogramPanel.setBackground(Color.darkGray);

    //feature Panel
    addFeaturePanel();
    toolBarPanel.add(featuresPanel);

    //Compressoin Panel
    addCompressionPanel();
    toolBarPanel.add(compressionPanel);

    //LvlAdj Panel
    addLvlAdjPanel();
    toolBarPanel.add(lvlAdjPanel);

    //PreView Panel
    addPreviewPanel();
    toolBarPanel.add(previewPanel);
  }

  private void addFeaturePanel() {

    featuresPanel = new JPanel();
    featuresPanel.setBorder(BorderFactory.createTitledBorder("Features"));
    featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.PAGE_AXIS));
//        featuresPanel.setLayout(new BorderLayout());

    JPanel loadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    fileOpenButton = new JButton("Load Image");
    JPanel loadlabelPanel = new JPanel(new FlowLayout());
    loadLabel = new JLabel("upload an image");
    loadlabelPanel.add(loadLabel);
//        loadlabelPanel.setBackground(Color.lightGray);
    loadPanel.add(fileOpenButton);
    loadPanel.add(loadlabelPanel);
    featuresPanel.add(loadPanel);

    JPanel dropdownPanel = new JPanel(new FlowLayout());
    dropdown = createDropdown();
    dropdownPanel.add(dropdown);
    featuresPanel.add(dropdownPanel);

    applyButton = new JButton("Apply");
    fileSaveButton = new JButton("Save Image");
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(applyButton);
    buttonPanel.add(Box.createHorizontalStrut(10));
    buttonPanel.add(fileSaveButton);
//        buttonPanel.setBackground(Color.darkGray);
    featuresPanel.add(buttonPanel);
  }

  private JComboBox<String> createDropdown() {
    FeatureEnum[] features = FeatureEnum.values();
    int listSize = features.length + 1;
    String[] items = new String[listSize];
    items[0] = defaultDropdownOption;
    for (int i = 1; i < listSize; i++) {
      items[i] = String.valueOf(features[i - 1]);
    }
    return new JComboBox<>(items);
  }

  private void addPreviewPanel() {
    previewPanel = new JPanel();
    previewPanel.setBorder(BorderFactory.createTitledBorder("Split View"));
    previewPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    splitSlider = getSlider(0, 100, 0);
    //previewButton = new JButton("Preview");
    splitLabel = new JLabel("Split 0% : ");
    splitSlider.addChangeListener(e -> splitLabel.setText("Split " + splitSlider.getValue() + "% : "));
    previewPanel.add(splitLabel);
    previewPanel.add(splitSlider);
    //previewPanel.add(previewButton);
    cancelButton = new JButton("Cancel");
    previewPanel.add(cancelButton);
    setPanelEnabled(previewPanel, false);
  }

  private void setPanelEnabled(JPanel panel, boolean isEnabled) {
    panel.setEnabled(isEnabled);
    Component[] components = panel.getComponents();
    for (Component component : components) {
      if (component instanceof JPanel) {
        setPanelEnabled((JPanel) component, isEnabled);
      } else {
        component.setEnabled(isEnabled);
      }
    }
  }

  private void addCompressionPanel() {
    compressionPanel = new JPanel();
    compressionSlider = getSlider(0, 100, 0);
    compressionPanel = new JPanel();
    compressionPanel.setBorder(BorderFactory.createTitledBorder("Compression Percentage"));
    JLabel valueLabel = new JLabel("compress 0% : ");
    compressionSlider.addChangeListener(e -> valueLabel.setText("compress " + compressionSlider.getValue() + "% :"));
    compressionPanel.add(valueLabel, BorderLayout.SOUTH);
    compressionPanel.add(compressionSlider, BorderLayout.CENTER);
    compressionPanel.setVisible(false);
//        int result = JOptionPane.showConfirmDialog(
//                null,
//                compressionPanel,
//                "Compression Percentage",
//                JOptionPane.OK_CANCEL_OPTION
//        );
//        if (result == JOptionPane.OK_OPTION) {
//            int selectedValue = compressionSlider.getValue();
//            features.compress(selectedValue);
//        }
  }

  private void addLvlAdjPanel() {
    lvlAdjPanel = new JPanel(new GridLayout(0, 1));
    lvlAdjPanel.setBorder(BorderFactory.createTitledBorder("Level Adjust"));
    lvlAdjSliderB = getSlider(0, 253, 0);
    lvlAdjSliderM = getSlider(0, 254, 128);
    lvlAdjSliderW = getSlider(0, 255, 255);
    JLabel labelB = new JLabel("black: 0");
    JLabel labelM = new JLabel("mid: 128");
    JLabel labelW = new JLabel("white: 255");
    lvlAdjSliderB.addChangeListener(e ->
    {
      labelB.setText("black: " + lvlAdjSliderB.getValue());
      if (lvlAdjSliderB.getValue() >= lvlAdjSliderM.getValue()) {
        lvlAdjSliderM.setValue(lvlAdjSliderB.getValue() + 1);
      }
    });
    lvlAdjSliderM.addChangeListener(e ->
    {
      labelM.setText("mid: " + lvlAdjSliderM.getValue());
      if (lvlAdjSliderM.getValue() <= lvlAdjSliderB.getValue() || lvlAdjSliderM.getValue() >= lvlAdjSliderW.getValue()) {
        lvlAdjSliderM.setValue(lvlAdjSliderB.getValue() + 1);
      }
      if (lvlAdjSliderM.getValue() >= lvlAdjSliderW.getValue()) {
        lvlAdjSliderW.setValue(lvlAdjSliderM.getValue() + 1);
      }
    });
    lvlAdjSliderW.addChangeListener(e ->
    {
      labelW.setText("white: " + lvlAdjSliderW.getValue());
      if (lvlAdjSliderW.getValue() <= lvlAdjSliderM.getValue()) {
        lvlAdjSliderW.setValue(lvlAdjSliderM.getValue() + 1);
      }
    });

    JPanel panelB = new JPanel();
    panelB.add(labelB);
    panelB.add(lvlAdjSliderB);
    JPanel panelM = new JPanel();
    panelM.add(labelM);
    panelM.add(lvlAdjSliderM);
    JPanel panelW = new JPanel();
    panelW.add(labelW);
    panelW.add(lvlAdjSliderW);
    lvlAdjPanel.add(new JLabel("Enter valid black, mid, white values (0 <= b < m < w <= 255) :"));
    lvlAdjPanel.add(panelB);
    lvlAdjPanel.add(panelM);
    lvlAdjPanel.add(panelW);
    lvlAdjPanel.setVisible(false);
//        int result = JOptionPane.showConfirmDialog(
//                null, LvlAdjPanel, "Levels adjust Input", JOptionPane.OK_CANCEL_OPTION);
//        if (result == JOptionPane.OK_OPTION) {
//            features.levelsAdjust(sliderB.getValue(),sliderM.getValue(),sliderW.getValue());
//        }
  }

  @Override
  public void resetPreviewSlider() {
    splitSlider.setValue(0);
  }

  @Override
  public void resetInputSliders() {
    lvlAdjSliderB.setValue(0);
    lvlAdjSliderM.setValue(128);
    lvlAdjSliderW.setValue(255);
    compressionSlider.setValue(0);
    splitSlider.setValue(0);
  }

  @Override
  public void resetDropdown() {
    dropdown.setSelectedItem(defaultDropdownOption);
  }

  @Override
  public void togglePreview(boolean isEnabled) {
    splitSlider.setValue(0);
    setPanelEnabled(previewPanel, isEnabled);
  }

  @Override
  public void showWindow() {
    setVisible(true);
  }

  @Override
  public void showDiscardConfirmation() {
    int result = JOptionPane.showConfirmDialog(JFrameViewSplit.this,
            "Save Changes?", "Unsaved Changes",
            JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
      features.handleSaveButton();
    }
  }

  @Override
  public void addFeatures(Features features) {
    this.features = features;
    dropdown.addActionListener(evt -> {
      compressionPanel.setVisible(false);
      lvlAdjPanel.setVisible(false);
      try {
        switch (FeatureEnum.valueOf((String) Objects.requireNonNull(dropdown.getSelectedItem()))) {
          case BLUR:
            features.blur();
            break;
          case SHARPEN:
            features.sharpen();
            break;
          case SEPIA:
            features.sepia();
            break;
          case GREYSCALE:
            features.greyscale();
            break;
          case RED:
            features.red();
            break;
          case BLUE:
            features.blue();
            break;
          case GREEN:
            features.green();
            break;
          case COLOR_CORRECT:
            features.colorCorrect();
            break;
          case COMPRESS:
            features.handleCompress();
            break;
          case LEVELS_ADJUST:
            features.handleLevelsAdjust();
            break;
          case HORIZONTAL_FLIP:
            features.horizontal();
            break;
          case VERTICAL_FLIP:
            features.vertical();
            break;
          default:
            features.noOperation();
        }
      } catch (IllegalArgumentException e) {
        features.noOperation();
      }
    });

    compressionSlider.addChangeListener(evt -> features.compress(compressionSlider.getValue()));
    lvlAdjSliderB.addChangeListener(evt -> features.levelsAdjust(lvlAdjSliderB.getValue(), lvlAdjSliderM.getValue()
            , lvlAdjSliderW.getValue()));
    lvlAdjSliderM.addChangeListener(evt -> features.levelsAdjust(lvlAdjSliderB.getValue(), lvlAdjSliderM.getValue()
            , lvlAdjSliderW.getValue()));
    lvlAdjSliderW.addChangeListener(evt -> features.levelsAdjust(lvlAdjSliderB.getValue(), lvlAdjSliderM.getValue()
            , lvlAdjSliderW.getValue()));
    fileOpenButton.addActionListener(evt -> features.handleLoadButton());
    fileSaveButton.addActionListener(evt -> features.handleSaveButton());

    //previewButton.addActionListener(evt -> features.preview(splitSlider.getValue()));
    applyButton.addActionListener(evt -> features.apply());
    cancelButton.addActionListener(evt -> features.cancel());
    splitSlider.addChangeListener(evt -> features.preview(splitSlider.getValue()));
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        features.exitProgram();
      }
    });
  }

  private JSlider getSlider(int min, int max, int start) {
    JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, start);
    slider.setMajorTickSpacing(max);
//        slider.setMinorTickSpacing(1);
    slider.setPaintTicks(true);
//        slider.setPaintLabels(true);
    return slider;
  }


  @Override
  public void showCompressMenu() {
    compressionPanel.setVisible(true);
    features.compress(compressionSlider.getValue());
  }

  @Override
  public void showLvlAdjMenu() {
    lvlAdjPanel.setVisible(true);
    features.levelsAdjust(lvlAdjSliderB.getValue(), lvlAdjSliderM.getValue(), lvlAdjSliderW.getValue());
  }

  @Override
  public void showLoadMenu() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG, PNG & PPM Images", "jpg", "png", "ppm");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(JFrameViewSplit.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      features.loadImage(f.getAbsolutePath());
      loadLabel.setText(f.getName());
    }
  }

  @Override
  public void showSaveMenu() {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(JFrameViewSplit.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      features.saveImage(f.getAbsolutePath());
    }
  }

  @Override
  public void displayImage(ImageData imageData) {
    if (imageData.getData()[0].length == 0) {
      displayError("No Image Present");
    } else {
      BufferedImage image = RgbImageFileIO.convertImgDataToBuffImg(imageData);
      imageLabel.setIcon(new ImageIcon(image));
    }
  }

  @Override
  public void displayHistogram(ImageData imageData) {
    BufferedImage image = RgbImageFileIO.convertImgDataToBuffImg(imageData);
    imageLabelHist.setIcon(new ImageIcon(image));
  }

//    @Override
//    public void toggleSplit(boolean supportSplit){
//        previewButton.setEnabled(supportSplit);
//        splitTextField.setEnabled(supportSplit);
//    }

  @Override
  public void displayError(String message) {
    JOptionPane.showMessageDialog(JFrameViewSplit.this, message,
            "Error", JOptionPane.ERROR_MESSAGE);
  }

}
