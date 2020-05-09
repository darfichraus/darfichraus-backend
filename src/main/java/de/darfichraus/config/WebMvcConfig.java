package de.darfichraus.config;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.*;
import org.pac4j.core.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Type;

@Configuration
@ComponentScan(basePackages = "org.pac4j.springframework.web")
public class WebMvcConfig implements WebMvcConfigurer {

    final Config config;


    public WebMvcConfig(Config config) {
        this.config = config;
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }

    @Bean
    GsonBuilder gsonBuilder(){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm");
        Converters.registerAll(gsonBuilder);
        gsonBuilder.registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter());
        return gsonBuilder;
    }

    private static class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]> {

        @Override
        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64Utils.encodeToString(src));
        }
    }

}
