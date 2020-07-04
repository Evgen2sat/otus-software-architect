package hw10.saga.order.database;

import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class QueryParam {
    private int type;
    private Object value;

    public int getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    private QueryParam(int type, Object value) {
        this.type = type;
        this.value = value;
    }

    public static QueryParam getIntNull() {
        return new QueryParam(Types.INTEGER, null);
    }

    public static QueryParam getLongNull() {
        return new QueryParam(Types.BIGINT, null);
    }

    public static QueryParam getStringNull() {
        return new QueryParam(Types.VARCHAR, null);
    }

    public static QueryParam getLocalDateNull() {
        return new QueryParam(Types.DATE, null);
    }

    public static QueryParam getLocalDateTimeNull() {
        return new QueryParam(Types.TIMESTAMP, null);
    }

    public static QueryParam getBooleanNull() {
        return new QueryParam(Types.BOOLEAN, null);
    }

    public static QueryParam getInt(int value) {
        return new QueryParam(Types.INTEGER, value);
    }

    public static QueryParam getLong(long value) {
        return new QueryParam(Types.BIGINT, value);
    }

    public static QueryParam getString(String value) {
        return new QueryParam(Types.VARCHAR, value);
    }

    public static QueryParam getLocalDate(LocalDate value) {
        return new QueryParam(Types.DATE, value);
    }

    public static QueryParam getLocalDateTime(LocalDateTime value) {
        return new QueryParam(Types.TIMESTAMP, value);
    }

    public static QueryParam getBoolean(boolean value) {
        return new QueryParam(Types.BOOLEAN, value);
    }

    public static QueryParam getFloat(float value) {
        return new QueryParam(Types.FLOAT, value);
    }
}
