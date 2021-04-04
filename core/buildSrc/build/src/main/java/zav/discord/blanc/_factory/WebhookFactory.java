
package zav.discord.blanc._factory;

import com.google.common.cache.Cache;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Object;
import java.lang.RuntimeException;
import java.lang.String;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import zav.discord.blanc.*;
import zav.discord.blanc.activity.Activity;
import zav.jra.models.AbstractLink;
import zav.jra.models.AbstractSubreddit;



 public  class WebhookFactory   {
     public  static  Webhook create( Supplier<? extends Webhook> _factoryWebhook,  long id,  String name){

        Webhook _WebhookInstance = _factoryWebhook.get();
        _WebhookInstance.setId(id);
        _WebhookInstance.setName(name);
        return _WebhookInstance;
    }

     public  static  Webhook create( Supplier<? extends Webhook> _factoryWebhook,  Set<String> subreddits,  long id,  String name){

        Webhook _WebhookInstance = _factoryWebhook.get();
        _WebhookInstance.setSubreddits(subreddits);
        _WebhookInstance.setId(id);
        _WebhookInstance.setName(name);
        return _WebhookInstance;
    }

}