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

public class JFrameViewSplit extends JFrame implements GuiView{

    private Features features;
    private JPanel mainPanel;
    private JScrollPane mainScrollPane;
    private JLabel imageLabel;
    private JLabel imageLabelHist;
    private JButton fileOpenButton;
    private JButton fileSaveButton;
    private JTextField splitTextField;
    private JButton previewButton;
    private JLabel splitLabel;

    private JButton applyButton;
    private JButton cancelButton;

    private JPanel featuresPanel;
    private JPanel previewPanel;

    private JComboBox<String> dropdown;
    private JLabel loadLabel;

    //private JButton blurButton;
//    private JButton sepiaButton;
//    private JButton sharpenButton;
//    private JButton greyscaleButton;
//    private JButton redButton;
//    private JButton blueButton;
//    private JButton greenButton;
//    private JButton colorCorrectButton;
//    private JButton levelsAdjustButton;
//    private JButton horizontalButton;
//    private JButton verticalButton;
//    private JButton compressButton;
//    private JCheckBox splitCheckBox;

    public JFrameViewSplit(String caption){
        super();
        setTitle(caption);
        setSize(1300, 900);
        // this.setMinimumSize(new Dimension(300,300));

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
        toolBarPanel.setSize(300,900);
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

        //PreView Panel
        addPreviewPanel();
        toolBarPanel.add(previewPanel);
//        toolBarPanel.setBackground(Color.darkGray);
    }

    private void addFeaturePanel(){

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
        int listSize = features.length+1;
        String[] items = new String[listSize];
        items[0] = "select an operation";
        for(int i=1;i<listSize;i++){
            items[i] = features[i-1].getOperation();
        }
        return new JComboBox<>(items);
    }

    private void addPreviewPanel(){
        previewPanel = new JPanel();
        previewPanel.setBorder(BorderFactory.createTitledBorder("Split View"));
        previewPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        splitTextField = new JTextField(15);
        previewButton = new JButton("Preview");
        splitLabel = new JLabel("Split % : ");

        previewPanel.add(splitLabel);
        previewPanel.add(splitTextField);
        previewPanel.add(previewButton);
//        previewPanel.setBackground(Color.darkGray);
//        cancelButton = new JButton("Cancel");
//        previewPanel.add(cancelButton);
        setPanelEnabled(previewPanel, false);
    }

    private void setPanelEnabled(JPanel panel, boolean isEnabled) {
        panel.setEnabled(isEnabled);
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                setPanelEnabled((JPanel) component,isEnabled);
            } else {
                component.setEnabled(isEnabled);
            }
        }
    }

    @Override
    public void togglePreview(boolean isEnabled){
        splitTextField.setText("");
        setPanelEnabled(previewPanel, isEnabled);
    }

    @Override
    public void toggleFeatures(){
        setPanelEnabled(featuresPanel, true);
    }

    @Override
    public void showWindow(){
        setVisible(true);
    }

    @Override
    public void showDiscardConfirmation(){
        int result = JOptionPane.showConfirmDialog(JFrameViewSplit.this,
                "Save Changes?", "Unsaved Changes",
                JOptionPane.YES_NO_OPTION);
        if (result==JOptionPane.YES_OPTION){
            features.handleSaveButton();
        }
    }

    @Override
    public void addFeatures(Features features) {
        this.features = features;
        dropdown.addActionListener(evt -> {
            switch ((String) Objects.requireNonNull(dropdown.getSelectedItem())){
                case  "blur" : features.blur();
                    break;
                case "sharpen" : features.sharpen();
                    break;
                case "sepia" : features.sepia();
                    break;
                case "grayscale" : features.greyscale();
                    break;
                case "red-component" : features.red();
                    break;
                case "blue-component" : features.blue();
                    break;
                case "green-component" : features.green();
                    break;
                case "color-correct" : features.colorCorrect();
                    break;
                case "compress" : features.handleCompress();
                    break;
                case "levels-adjust" : features.handleLevelsAdjust();
                    break;
                case "horizontal-flip" : features.horizontal();
                    break;
                case "vertical-flip" : features.vertical();
                    break;//throw new IllegalStateException("Unexpected value: " + dropdown.getSelectedItem());
            }
        });

        fileOpenButton.addActionListener(evt -> features.handleLoadButton());
        fileSaveButton.addActionListener(evt -> features.handleSaveButton());

        previewButton.addActionListener(evt -> features.preview(splitTextField.getText()));
        applyButton.addActionListener(evt -> features.apply());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                features.exitProgram();
            }
        });
    }

    private JSlider getSlider(int min, int max, int start){
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, start);
        slider.setMajorTickSpacing(max);
//        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
//        slider.setPaintLabels(true);
        return  slider;
    }
    @Override
    public void showCompressMenu() {
        JSlider slider = getSlider(0,100,0);
        JPanel sliderPanel = new JPanel(new BorderLayout());
        JLabel valueLabel = new JLabel("Selected Percentage: 0");
        slider.addChangeListener(e -> valueLabel.setText("Selected Percentage: " + slider.getValue()));
        sliderPanel.add(slider, BorderLayout.CENTER);
        sliderPanel.add(valueLabel, BorderLayout.SOUTH);
        int result = JOptionPane.showConfirmDialog(
                null,
                sliderPanel,
                "Compression Percentage",
                JOptionPane.OK_CANCEL_OPTION
        );
        if (result == JOptionPane.OK_OPTION) {
            int selectedValue = slider.getValue();
            features.compress(selectedValue);
        }
    }

    @Override
    public void showLvlAdjMenu() {
        JSlider sliderB = getSlider(0,255,0);
        JSlider sliderM = getSlider(0,255,0);
        JSlider sliderW = getSlider(0,255,0);
        JLabel labelB = new JLabel("black: 0");
        JLabel labelM = new JLabel("mid: 0");
        JLabel labelW = new JLabel("white: 0");
        sliderB.addChangeListener(e -> labelB.setText("black: " + sliderB.getValue()));
        sliderM.addChangeListener(e -> labelM.setText("mid: " + sliderM.getValue()));
        sliderW.addChangeListener(e -> labelW.setText("white: " + sliderW.getValue()));
        JPanel panelB = new JPanel();
        panelB.add(labelB);
        panelB.add(sliderB);
        JPanel panelM = new JPanel();
        panelM.add(labelM);
        panelM.add(sliderM);
        JPanel panelW = new JPanel();
        panelW.add(labelW);
        panelW.add(sliderW);
        JPanel LvlAdjPanel = new JPanel(new GridLayout(0, 1));
        LvlAdjPanel.add(new JLabel("Enter valid black, mid, white values (0 < b < m < w < 256) :"));
        LvlAdjPanel.add(panelB);
        LvlAdjPanel.add(panelM);
        LvlAdjPanel.add(panelW);
        int result = JOptionPane.showConfirmDialog(
                null, LvlAdjPanel, "Levels adjust Input", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            features.levelsAdjust(sliderB.getValue(),sliderM.getValue(),sliderW.getValue());
        }
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
        if(imageData.getData()[0].length==0){
            displayError("No Image Present");
        }
        else{
            BufferedImage image = RgbImageFileIO.convertImgDataToBuffImg(imageData);
            imageLabel.setIcon(new ImageIcon(image));
        }
    }

    @Override
    public void displayHistogram(ImageData imageData) {
        BufferedImage image = RgbImageFileIO.convertImgDataToBuffImg(imageData);
        imageLabelHist.setIcon(new ImageIcon(image));
    }

    @Override
    public void toggleSplit(boolean supportSplit){
        previewButton.setEnabled(supportSplit);
        splitTextField.setEnabled(supportSplit);
    }

    @Override
    public void displayError(String message){
        JOptionPane.showMessageDialog(JFrameViewSplit.this, message,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

}
