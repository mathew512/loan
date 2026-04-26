package app.framework;

import app.utility.db.DataSourceHelper;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class GenericDao<T, ID> {

    private final Class<T> entityClass;
    private final String tableName;
    private final List<Field> columns = new ArrayList<>();
    private Field idField;

    private final DataSource ds;

    public GenericDao(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.ds = DataSourceHelper.getDataSource();

        if (!entityClass.isAnnotationPresent(DbTable.class)) {
            throw new RuntimeException("Missing @DbTable on " + entityClass.getName());
        }

        this.tableName = entityClass.getAnnotation(DbTable.class).name();

        for (Field field : entityClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(DbColumn.class)) continue;

            field.setAccessible(true);
            columns.add(field);

            DbColumn col = field.getAnnotation(DbColumn.class);
            if (col.primaryKey()) {
                idField = field;
            }
        }

        if (idField == null) {
            throw new RuntimeException("No primary key defined in " + entityClass.getName());
        }
    }

    // ================= CREATE =================
    public void save(T entity) {
        try (Connection conn = ds.getConnection()) {
            List<String> colNames = new ArrayList<>();
            List<String> placeholders = new ArrayList<>();
            List<Object> values = new ArrayList<>();

            for (Field field : columns) {
                DbColumn col = field.getAnnotation(DbColumn.class);
                if (col.autoIncrement()) continue;

                colNames.add(col.name());
                placeholders.add("?");
                values.add(field.get(entity));
            }

            String sql = "INSERT INTO " + tableName +
                    " (" + String.join(",", colNames) + ") VALUES (" +
                    String.join(",", placeholders) + ")";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < values.size(); i++) {
                Object value = values.get(i);
                if (value instanceof java.util.Date) {
                    ps.setDate(i + 1, new java.sql.Date(((java.util.Date) value).getTime()));
                } else if (value instanceof java.sql.Timestamp) {
                    ps.setTimestamp(i + 1, (java.sql.Timestamp) value);
                } else {
                    ps.setObject(i + 1, value);
                }
            }

            ps.executeUpdate();

            // ✅ Populate auto-generated ID back into entity
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next() && idField != null) {
                idField.set(entity, keys.getObject(1));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error saving entity to " + tableName, e);
        }
    }

    // ================= FIND BY ID =================
    public T findById(ID id) {
        try (Connection conn = ds.getConnection()) {
            String idColumn = idField.getAnnotation(DbColumn.class).name();
            String sql = "SELECT * FROM " + tableName + " WHERE " + idColumn + "=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error finding entity by ID in " + tableName, e);
        }
        return null;
    }

    // ================= FIND ALL =================
    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        try (Connection conn = ds.getConnection()) {
            String sql = "SELECT * FROM " + tableName;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error finding all entities in " + tableName, e);
        }
        return list;
    }

    // ================= UPDATE =================
    public void update(T entity) {
        try (Connection conn = ds.getConnection()) {
            List<String> setParts = new ArrayList<>();
            List<Object> values = new ArrayList<>();

            String idColumn = idField.getAnnotation(DbColumn.class).name();
            Object idValue = idField.get(entity);

            for (Field field : columns) {
                DbColumn col = field.getAnnotation(DbColumn.class);
                if (col.primaryKey()) continue;

                setParts.add(col.name() + "=?");
                values.add(field.get(entity));
            }

            String sql = "UPDATE " + tableName +
                    " SET " + String.join(",", setParts) +
                    " WHERE " + idColumn + "=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            int index = 1;
            for (Object val : values) {
                ps.setObject(index++, val);
            }
            ps.setObject(index, idValue);

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error updating entity in " + tableName, e);
        }
    }

    // ================= DELETE =================
    public void delete(ID id) {
        try (Connection conn = ds.getConnection()) {
            String idColumn = idField.getAnnotation(DbColumn.class).name();
            String sql = "DELETE FROM " + tableName + " WHERE " + idColumn + "=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting entity from " + tableName, e);
        }
    }

    // ================= MAPPER =================
    private T mapResultSet(ResultSet rs) throws Exception {
        T instance = entityClass.getDeclaredConstructor().newInstance();
    
        for (Field field : columns) {
            DbColumn col = field.getAnnotation(DbColumn.class);
            Object value = rs.getObject(col.name());
    
            if (value != null) {
    
                Class<?> type = field.getType();
    
                // ================= DATE HANDLING =================
                if (type.equals(java.util.Date.class) && value instanceof java.sql.Date) {
                    value = new java.util.Date(((java.sql.Date) value).getTime());
                }
    
                // ================= TIMESTAMP HANDLING =================
                else if (type.equals(java.util.Date.class) && value instanceof java.sql.Timestamp) {
                    value = new java.util.Date(((java.sql.Timestamp) value).getTime());
                }
    
                // ================= ENUM HANDLING =================
                else if (type.isEnum() && value instanceof String) {
                    value = Enum.valueOf((Class<Enum>) type, (String) value);
                }
    
                // ================= NUMBER CONVERSIONS (IMPORTANT FIX) =================
                else if (type == double.class || type == Double.class) {
                    if (value instanceof java.math.BigDecimal) {
                        value = ((java.math.BigDecimal) value).doubleValue();
                    } else if (value instanceof Number) {
                        value = ((Number) value).doubleValue();
                    }
                }
    
                else if (type == int.class || type == Integer.class) {
                    if (value instanceof Number) {
                        value = ((Number) value).intValue();
                    }
                }
    
                else if (type == long.class || type == Long.class) {
                    if (value instanceof Number) {
                        value = ((Number) value).longValue();
                    }
                }
    
                else if (type == java.math.BigDecimal.class && value instanceof Number) {
                    value = new java.math.BigDecimal(value.toString());
                }
            }
    
            field.set(instance, value);
        }
    
        return instance;
    }

}
