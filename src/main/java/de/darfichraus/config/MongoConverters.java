package de.darfichraus.config;

import com.google.common.collect.Lists;
import org.pac4j.core.util.JavaSerializationHelper;
import org.pac4j.mongo.profile.MongoProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Configuration
public class MongoConverters {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Lists.newArrayList(
                new OffsetDateTimeReadConverter(),
                new OffsetDateTimeWriteConverter()
        ));
    }

    static class OffsetDateTimeWriteConverter implements Converter<OffsetDateTime, String> {
        @Override
        public String convert(OffsetDateTime source) {
            return source.toInstant().atZone(ZoneOffset.UTC).toString();
        }

    }

    static class OffsetDateTimeReadConverter implements Converter<String, OffsetDateTime> {
        @Override
        public OffsetDateTime convert(String source) {
            return OffsetDateTime.parse(source);
        }

    }

    public static class MongoProfileToStringConverter implements Converter<MongoProfile, String> {

        private static final JavaSerializationHelper javaSerializationHelper = new JavaSerializationHelper();

        @Override
        public String convert(MongoProfile source) {
            return javaSerializationHelper.serializeToBase64(source);
        }
    }

    public static class StringToMongoProfileConverter implements Converter<String, MongoProfile> {

        private static final JavaSerializationHelper javaSerializationHelper = new JavaSerializationHelper();

        @Override
        public MongoProfile convert(String source) {
            return (MongoProfile) javaSerializationHelper.deserializeFromBase64(source);
        }
    }
}
