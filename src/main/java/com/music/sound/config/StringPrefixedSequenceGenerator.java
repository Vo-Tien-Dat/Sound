package com.music.sound.config;

import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.type.LongType;

public class StringPrefixedSequenceGenerator extends SequenceStyleGenerator {
    public static final String VALUE_PREFIX_PARAMETER = "valuePrefix";
    public static final String VALUE_PREFIX_DEFAULT = "";
    public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
    public static final String NUMBER_FORMAT_DEFAULT = "%d";

    private String valuePrefix;
    private String numberFormat;

    public void configure(org.hibernate.type.Type type, java.util.Properties params,
            org.hibernate.service.ServiceRegistry serviceRegistry) throws org.hibernate.MappingException {
        super.configure(LongType.INSTANCE, params, serviceRegistry);
        valuePrefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER, params, VALUE_PREFIX_DEFAULT);
        numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, params, NUMBER_FORMAT_DEFAULT);
    }

    public java.io.Serializable generate(org.hibernate.engine.spi.SharedSessionContractImplementor session,
            java.lang.Object object) throws org.hibernate.HibernateException {
        return valuePrefix + String.format(numberFormat, super.generate(session, object));
    }
}
