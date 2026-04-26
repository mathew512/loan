package app.utility.db;

import app.framework.DbColumn;
import app.framework.DbTable;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TableGenerator {

    public static void generateTables(DataSource ds, Set<Class<?>> entityClasses) {

        for (Class<?> clazz : entityClasses) {

            if (!clazz.isAnnotationPresent(DbTable.class)) continue;

            DbTable table = clazz.getAnnotation(DbTable.class);
            String tableName = table.name();

            List<String> columns = new ArrayList<>();
            String primaryKey = null;

            for (Field field : clazz.getDeclaredFields()) {

                if (!field.isAnnotationPresent(DbColumn.class)) continue;

                DbColumn col = field.getAnnotation(DbColumn.class);

                String sqlType = resolveSqlType(field, col);

                StringBuilder columnDef = new StringBuilder();
                columnDef.append(col.name())
                        .append(" ")
                        .append(sqlType);

                // ================= IDENTITY =================
                if (col.autoIncrement()) {
                    columnDef.append(" GENERATED ALWAYS AS IDENTITY");
                }

                // ================= NULL SAFETY =================
                if (col.primaryKey()) {
                    columnDef.append(" NOT NULL");
                    primaryKey = col.name();
                }

                columns.add(columnDef.toString());
            }

            // ================= PRIMARY KEY SAFETY =================
            if (primaryKey == null) {
                throw new RuntimeException("No primary key defined in " + clazz.getName());
            }

            columns.add("PRIMARY KEY (" + primaryKey + ")");

            String sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                    " (" + String.join(", ", columns) + ")";

            execute(ds, sql);
        }
    }

    // ================= TYPE MAPPER (IMPORTANT FIX) =================
    private static String resolveSqlType(Field field, DbColumn col) {

        Class<?> type = field.getType();

        if (type == String.class) return "VARCHAR(255)";
        if (type == int.class || type == Integer.class) return "INTEGER";
        if (type == long.class || type == Long.class) return "BIGINT";
        if (type == boolean.class || type == Boolean.class) return "BOOLEAN";

        if (type == double.class || type == Double.class) return "DOUBLE PRECISION";

        // 🔥 IMPORTANT FOR LOANS / MONEY
        if (type == java.math.BigDecimal.class) return "NUMERIC(19,2)";

        if (type == java.util.Date.class) return "DATE";

        if (type.isEnum()) return "VARCHAR(50)";

        // fallback (safe default)
        return "TEXT";
    }

    // ================= EXECUTION =================
    private static void execute(DataSource ds, String sql) {
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Executed: " + sql);

        } catch (Exception e) {
            throw new RuntimeException("Error executing SQL: " + sql, e);
        }
    }
}