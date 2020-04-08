package de.darfichraus.entity.converters;


import org.pac4j.core.util.JavaSerializationHelper;
import org.pac4j.mongo.profile.MongoProfile;
import org.springframework.core.convert.converter.Converter;

public class MongoProfileToStringConverter implements Converter<MongoProfile, String> {

    private static final JavaSerializationHelper javaSerializationHelper = new JavaSerializationHelper();

    @Override
    public String convert(MongoProfile source) {
        return javaSerializationHelper.serializeToBase64(source);
    }
}


