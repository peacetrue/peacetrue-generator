package com.github.peacetrue.generator;

import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.sql.JDBCType;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 添加 {@link JDBCType} 转换参数
 *
 * @author xiayx
 */
public class JdbcTypeContextHandler implements ContextHandler {

    @Override
    public void handle(Map<String, Object> context) {
        context.put("javaTypeToJDBCType", convert(JdbcTypeContextHandler::fromJDBCType));
        context.put("javaTypeToJdbcType", convert(JdbcTypeContextHandler::fromJdbcType));
    }

    private static final Map<Integer, Class<?>> SQL_TYPE_TO_JAVA_TYPE = new HashMap<>();

    //JDBCType
    static {
        SQL_TYPE_TO_JAVA_TYPE.put(Types.BIT, Boolean.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.TINYINT, Byte.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.SMALLINT, Short.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.INTEGER, Integer.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.BIGINT, Long.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.FLOAT, Float.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.REAL, Float.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.DOUBLE, Double.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.NUMERIC, BigDecimal.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.DECIMAL, BigDecimal.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.CHAR, String.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.VARCHAR, String.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.LONGVARCHAR, String.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.DATE, Date.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.TIME, Time.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.TIMESTAMP, Timestamp.class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.BINARY, Byte[].class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.VARBINARY, Byte[].class);
        SQL_TYPE_TO_JAVA_TYPE.put(Types.LONGVARBINARY, Byte[].class);
    }

    private static String fromJDBCType(int code) {
        for (JDBCType jdbcType : JDBCType.values()) {
            if (code == jdbcType.getVendorTypeNumber()) {
                return "JDBCType." + jdbcType.name();
            }
        }
        throw new IllegalArgumentException(String.format("非法的参数异常[%s]", code));
    }

    private static String fromJdbcType(int code) {
        for (JdbcType jdbcType : JdbcType.values()) {
            if (code == jdbcType.TYPE_CODE) {
                return "JdbcType." + jdbcType.name();
            }
        }
        throw new IllegalArgumentException(String.format("非法的参数异常[%s]", code));
    }

    private static Map<String, String> convert(Function<Integer, String> function) {
        Map<String, String> map = new HashMap<>(SQL_TYPE_TO_JAVA_TYPE.size());
        SQL_TYPE_TO_JAVA_TYPE.forEach((key, value) -> map.put(value.getName(), function.apply(value.equals(Date.class) ? Types.TIMESTAMP : key)));
        return map;
    }

}
