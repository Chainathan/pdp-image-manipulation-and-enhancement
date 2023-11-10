package model;

public interface AbstractChannelCreator<T extends RgbImageModel> {
//  public abstract ChannelModel
  public abstract T create();
}
