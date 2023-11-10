package model;

interface AdvChannelModel extends ChannelModel{
//interface AdvChannelModel extends ChannelModel{
  AdvChannelModel applyCompression(double compressionRatio);
  int[] getFrequencyValues();
  int getMaxFreqPixel();
  AdvChannelModel adjustLevels(int b, int m, int w) throws IllegalArgumentException;
  AdvChannelModel trimVertical(int start, int end) throws IllegalArgumentException;
  AdvChannelModel overlapOnBase(AdvChannelModel otherChannel, int start) throws IllegalArgumentException;
}
