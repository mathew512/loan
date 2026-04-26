package app.utility.helper;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.lang3.StringUtils;

public class EnumConverter extends AbstractConverter {

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        if (value == null) {
            return null;
        }

        String enumValue = value.toString().trim();
        if (StringUtils.isBlank(enumValue)) {
            return null;
        }

        if (!type.isEnum()) {
            throw new ConversionException(type + " is not an Enum type");
        }

        for (Object constant : type.getEnumConstants()) {
            Enum<?> enumConstant = (Enum<?>) constant;
            if (enumConstant.name().equalsIgnoreCase(enumValue)) {
                return (T) enumConstant;
            }
        }

        throw new ConversionException(
            "Failed to convert '" + enumValue + "' to " + type.getName()
        );
    }

    @Override
    protected Class<?> getDefaultType() {
        return Enum.class;
    }
}
