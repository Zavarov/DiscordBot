
package zav.discord.blanc._json;

import com.google.common.cache.Cache;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.CharSequence;
import java.lang.Class;
import java.lang.Comparable;
import java.lang.InterruptedException;
import java.lang.Object;
import java.lang.RuntimeException;
import java.lang.String;
import java.lang.StringBuffer;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.json.JSONObject;
import zav.discord.blanc.*;
import zav.discord.blanc._visitor.ArchitectureVisitor;
import zav.discord.blanc.activity.Activity;
import zav.jra.models.AbstractLink;
import zav.jra.models.AbstractSubreddit;



 public  class JSONSelfUser   {
      protected  final  static  String ID  = "id";
      protected  final  static  String ONLINESTATUS  = "onlineStatus";
      protected  final  static  String RANKS  = "ranks";
      protected  final  static  String NAME  = "name";
     protected  void $fromId( JSONObject source,  SelfUser target){

        target.setId(source.getLong("id"));

    }

     protected  void $fromName( JSONObject source,  SelfUser target){

        target.setName(source.getString("name"));

    }

     protected  void $fromOnlineStatus( JSONObject source,  SelfUser target){

        throw new UnsupportedOperationException("Deserialization for `onlineStatus` not implemented.");
    }

     protected  void $fromRanks( JSONObject source,  SelfUser target){

        throw new UnsupportedOperationException("Deserialization for `ranks` not implemented.");
    }

     protected  void $toId( SelfUser source,  JSONObject target){

        target.put("id", source.getId());

    }

     protected  void $toName( SelfUser source,  JSONObject target){

        target.put("name", source.getName());

    }

     protected  void $toOnlineStatus( SelfUser source,  JSONObject target){

        target.put("onlineStatus", JSONOnlineStatus.toJson(source.getOnlineStatus(), new JSONObject()));

    }

     protected  void $toRanks( SelfUser source,  JSONObject target){

        throw new UnsupportedOperationException("Serialization for `ranks` not implemented.");
    }

     public  static  SelfUser fromJson( SelfUser target,  JSONObject source){

        //Create new instance for deserializer
        JSONSelfUser $json = new JSONSelfUser();
        //Deserialize attributes
        $json.$fromId(source, target);
        $json.$fromName(source, target);
        $json.$fromOnlineStatus(source, target);
        $json.$fromRanks(source, target);
        //Return deserialized  object
        return target;
    }

     public  static  SelfUser fromJson( SelfUser target,  Path path)throws IOException{

        return fromJson(target, java.nio.file.Files.readString(path));
    }

     public  static  SelfUser fromJson( SelfUser target,  String content){

        return fromJson(target, new JSONObject(content));
    }

     public  static  JSONObject toJson( SelfUser source,  JSONObject target){

        //Create new instance for serializer
        JSONSelfUser $json = new JSONSelfUser();
        //Serialize attributes
        $json.$toId(source, target);
        $json.$toName(source, target);
        $json.$toOnlineStatus(source, target);
        $json.$toRanks(source, target);
        //Return serialized  object
        return target;
    }

}