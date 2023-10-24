package Model;

import java.util.ArrayList;
import java.util.List;

public abstract class ImageModel implements ImeModel{

//  ChannelModel r,g,b;
  List<ChannelModel> channelList;
//  ImageModel(List<ChannelModel> channelList){
//    this.channelList = channelList;
//  }
//  ImageModel visualizeComponent(String component){
//    if (component.equals("Intensity")){
//      ChannelModel res = r.add(g).add(b);
//      res = res.divide(3);
//      List<ChannelModel> resList = new ArrayList<>();
//      resList.add(res);
//      return new ImageModel(resList);
//    }
//    return null;
//  }
}
