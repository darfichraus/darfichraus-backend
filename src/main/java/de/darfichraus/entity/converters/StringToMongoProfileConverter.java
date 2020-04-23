package de.darfichraus.entity.converters;


import org.pac4j.core.util.JavaSerializationHelper;
import org.pac4j.mongo.profile.MongoProfile;
import org.springframework.core.convert.converter.Converter;

public class StringToMongoProfileConverter implements Converter<String, MongoProfile> {

    private static final JavaSerializationHelper javaSerializationHelper = new JavaSerializationHelper();

    @Override
    public MongoProfile convert(String source) {
        return (MongoProfile) javaSerializationHelper.deserializeFromBase64(source);
    }
}


