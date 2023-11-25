package controller;

import java.io.IOException;
import java.sql.Struct;
import java.util.function.Function;

import exceptions.FileFormatNotSupportedException;
import model.FactoryRgbImageModel;
import model.ImageData;
import model.RgbImageModel;
import view.GuiView;

public class GuiControllerSplit implements Features, ImageController{

    private GuiView view;
    private final ImageFileIO rgbImageFileIO;
    private RgbImageModel currImage;
    private RgbImageModel actImage;
//    private double splitP;
//    private boolean supportSplit;
    private boolean imageSaved;

    private Function<RgbImageModel,RgbImageModel> currentOperation;

    public GuiControllerSplit(FactoryRgbImageModel factory, GuiView v) {
        view = v;
        view.addFeatures(this);
        this.rgbImageFileIO = new RgbImageFileIO();
        this.currImage = factory.createImageModel();
        actImage = currImage;
        imageSaved = true;
        //loadImage("res/leo.png");
    }

    @Override
    public void run() throws IOException {
        view.showWindow();
    }
//  public void setView(GuiView v) {
//    view = v;
//    //provide view with all the callbacks
//    view.addFeatures(this);
//  }

//    private void togglePanels(){
//        view.togglePreview(true);
//        view.toggleFeatures();
//    }
    @Override
    public void apply() {
        if(currentOperation!=null){
            try {
                actImage = currentOperation.apply(actImage);
                currImage = actImage;
                refreshImage();
                view.togglePreview(false);
                imageSaved = false;
            }
            catch (IllegalArgumentException e){
                view.displayError(e.getMessage());
            }
        }
        else{
            view.displayError("Invalid Operation");
        }
    }

//    @Override
//    public void cancel() {
//        currImage = actImage;
//        refreshImage();
//        togglePanels();
//    }

    @Override
    public void exitProgram() {
        if (!imageSaved) {
            view.showDiscardConfirmation();
        }
        //System.exit(0);
    }

    @Override
    public void loadImage(String filePath) {
        try {
            if(actImage.getImageData().getData()[0].length!=0 && !imageSaved){
                view.showDiscardConfirmation();
            }
            ImageData imageData = rgbImageFileIO.load(filePath);
            actImage.loadImageData(imageData);
            currImage=actImage;
            refreshImage();
            imageSaved = false;
        } catch (IOException | FileFormatNotSupportedException e){
            view.displayError(e.getMessage());
        }
    }

    @Override
    public void handleLoadButton() {
        view.showLoadMenu();
    }

    @Override
    public void handleSaveButton() {
        view.showSaveMenu();
    }

    @Override
    public void saveImage(String filePath) {
        try {
            ImageData destImageData = actImage.getImageData();
            rgbImageFileIO.save(filePath, destImageData);
            imageSaved = true;
        } catch (IOException | FileFormatNotSupportedException e){
            view.displayError(e.getMessage());
        }
    }

    private void refreshImage(){
        view.displayImage(currImage.getImageData());
        RgbImageModel imageHist = actImage.createHistogram(
                new ImageGraphicsImpl(256,256,20));
        view.displayHistogram(imageHist.getImageData());
    }

    private void setUpForOperation(){
        currImage = actImage;
        refreshImage();
    }

    @Override
    public void blur() {
        currentOperation = RgbImageModel::blur;
        setUpForOperation();
        view.togglePreview(true);
    }

    @Override
    public void sharpen() {
        currentOperation = RgbImageModel::sharpen;
        setUpForOperation();
        view.togglePreview(true);
    }

    @Override
    public void sepia() {
        currentOperation = RgbImageModel::sepia;
        setUpForOperation();
        view.togglePreview(true);
    }

    @Override
    public void greyscale() {
        currentOperation = rgb->rgb.visualizeComponent(ComponentEnum.LUMA);
        setUpForOperation();
        view.togglePreview(true);
//        executeOperation(rgb->rgb.visualizeComponent(ComponentEnum.LUMA), supportSplit);
    }

    @Override
    public void red() {
        currentOperation = rgb->rgb.visualizeComponent(ComponentEnum.RED);
        setUpForOperation();
        view.togglePreview(true);
//        executeOperation(rgb->rgb.visualizeComponent(ComponentEnum.RED), false);
    }

    @Override
    public void green() {
        currentOperation = rgb->rgb.visualizeComponent(ComponentEnum.GREEN);
        setUpForOperation();
        view.togglePreview(true);
//        executeOperation(rgb->rgb.visualizeComponent(ComponentEnum.GREEN), false);
    }

    @Override
    public void blue() {
        currentOperation = rgb->rgb.visualizeComponent(ComponentEnum.BLUE);
        setUpForOperation();
        view.togglePreview(true);
//        executeOperation(rgb->rgb.visualizeComponent(ComponentEnum.BLUE), false);
    }

    @Override
    public void horizontal() {
        currentOperation = RgbImageModel::horizontalFlip;
        setUpForOperation();
        view.togglePreview(false);
//        executeOperation(RgbImageModel::horizontalFlip, false);
    }

    @Override
    public void vertical() {
        currentOperation = RgbImageModel::verticalFlip;
        setUpForOperation();
        view.togglePreview(false);
//        actImage = currentOperation.apply(actImage);
//        currImage = actImage;
//        refreshImage();
//        executeOperation(RgbImageModel::verticalFlip, false);
    }

    @Override
    public void colorCorrect() {
        currentOperation = RgbImageModel::correctColor;
        setUpForOperation();
        view.togglePreview(true);
//        executeOperation(RgbImageModel::correctColor, supportSplit);
    }

    @Override
    public void handleCompress() {
        view.showCompressMenu();
    }

    //  @Override
//  public void compress(double compressRatio) {
//    image = image.applyCompression(compressRatio);
//    refreshImage();
//  }
    @Override
    public void compress(double compressRatio) {
//        try {
            //double cr = Double.parseDouble(compressRatio);
            currentOperation = rgb->rgb.applyCompression(compressRatio);
            setUpForOperation();
            view.togglePreview(true);
//            executeOperation(rgb->rgb.applyCompression(cr), supportSplit);
//        } catch (NumberFormatException ne){
//            view.displayError("Invalid input");
//        }
    }
    @Override
    public void handleLevelsAdjust() {
        view.showLvlAdjMenu();
    }

    //  @Override
//  public void levelsAdjust(int b, int m, int w) {
//    image = image.adjustLevels(b,m,w);
//    refreshImage();
//  }
    @Override
    public void levelsAdjust(int b, int m, int w) {
            //Should we check b<m<w
            currentOperation = rgb->rgb.adjustLevels(b,m,w);
            setUpForOperation();
            view.togglePreview(true);
    }

    @Override
    public void preview(String splitP){
        currImage = currentOperation.apply(actImage);
        RgbImageModel left = currImage.cropVertical(0, Double.parseDouble(splitP));
        currImage = actImage.overlapOnBase(left, 0);
        refreshImage();
    }

}
