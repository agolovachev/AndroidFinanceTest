package ru.javabegin.training.android6.finance.core.dao.impls;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.javabegin.training.android6.finance.core.dao.interfaces.SourceDAO;
import ru.javabegin.training.android6.finance.core.database.SQLiteConnection;
import ru.javabegin.training.android6.finance.core.enums.OperationType;
import ru.javabegin.training.android6.finance.core.impls.DefaultSource;
import ru.javabegin.training.android6.finance.core.interfaces.Source;


//TODO можно реализовать общий абстрактный класс и вынести туда общие методы (getAll, delete и пр.)
// реализация DAO не должна заниматься лишними делами - только связь с БД, заполнение объектов
public class SourceDAOImpl implements SourceDAO {

    private static final String SOURCE_TABLE = "source";
    private List<Source> sourceList = new ArrayList<>();// хранит все элементы сплошным списком, без разделения по деревьям и пр.


    @Override
    public List<Source> getAll() {
        sourceList.clear();

        try (Statement stmt = SQLiteConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("select * from " + SOURCE_TABLE + " order by parent_id");) {

            while (rs.next()) {
                DefaultSource source = new DefaultSource();
                source.setId(rs.getLong("id"));
                source.setName(rs.getString("name"));
                source.setParentId(rs.getLong("parent_id"));
                source.setIconName(rs.getString("icon_name"));
                source.setOperationType(OperationType.getType(rs.getInt("operation_type_id")));
                source.setRefCount(rs.getInt("ref_count"));

                sourceList.add(source);
            }

            return sourceList;// должен содержать только корневые элементы

        } catch (SQLException e) {
            Logger.getLogger(SourceDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }


    public int getRefCount(Source source){
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("select ref_count from " + SOURCE_TABLE + " where id=?");) {

            stmt.setLong(1, source.getId());

            try (ResultSet rs = stmt.executeQuery();) {

                if (rs.next()) {
                    return rs.getInt("ref_count");
                }

            }

        } catch (SQLException e) {
            Logger.getLogger(SourceDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return -1;
    }

    @Override
    public Source get(long id) {

        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("select * from " + SOURCE_TABLE + " where id=?");) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery();) {
                DefaultSource source = null;

                if (rs.next()) {
                    source = new DefaultSource();
                    source.setId(rs.getLong("id"));
                    source.setName(rs.getString("name"));
                    source.setParentId(rs.getLong("parent_id"));
                    source.setIconName(rs.getString("icon_name"));
                    source.setOperationType(OperationType.getType(rs.getInt("operation_type_id")));
                    source.setRefCount(rs.getInt("ref_count"));

                }

                return source;
            }

        } catch (SQLException e) {
            Logger.getLogger(SourceDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }


    @Override
    public boolean update(Source source) {
        // для упрощения - у хранилища даем изменить только название, изменять parent_id нельзя (для этого можно удалить и заново создать)
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("update " + SOURCE_TABLE + " set name=?, icon_name=? where id=?");) {

            stmt.setString(1, source.getName());// у созданного элемента - разрешаем менять только название
            stmt.setString(2, source.getIconName());
            stmt.setLong(3, source.getId());



            // не даем обновлять operationType - тип устанавливается только один раз при создании корневеого элемента

            if (stmt.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException e) {
            Logger.getLogger(SourceDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return false;
    }

    @Override
    public boolean delete(Source source) throws SQLException {
        // TODO реализовать - если есть ли операции по данному хранилищу - запрещать удаление
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("delete from " + SOURCE_TABLE + " where id=?");) {

            stmt.setLong(1, source.getId());

            if (stmt.executeUpdate() == 1) {
                return true;
            }
        }


        return false;
    }

    @Override
    // добавляет объект в БД и присваивает ему сгенерированный id
    public boolean add(Source source) {
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("insert into " + SOURCE_TABLE + "(name, parent_id, operation_type_id, icon_name) values(?,?,?,?)");
             Statement stmtId = SQLiteConnection.getConnection().createStatement();
        ) {

            stmt.setString(1, source.getName());

            if (source.hasParent()) {
                stmt.setLong(2, source.getParent().getId());
            } else {
                stmt.setNull(2, Types.BIGINT);
            }

            stmt.setLong(3, source.getOperationType().getId());
            stmt.setString(4, source.getIconName());

            if (stmt.executeUpdate() == 1) {// если объект добавился нормально

                try (ResultSet rs = stmtId.executeQuery("SELECT last_insert_rowid()");) {// получаем id вставленной записи

                    if (rs.next()) {
                        source.setId(rs.getLong(1));// не забываем просвоить новый id в объект
                        return true;
                    }


                }

            }

        } catch (SQLException e) {
            Logger.getLogger(SourceDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return false;
    }




    @Override
    public List<Source> getList(OperationType operationType) {
        sourceList.clear();

        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("select * from " + SOURCE_TABLE + " where operation_type_id=?");) {

            stmt.setLong(1, operationType.getId());

            try (ResultSet rs = stmt.executeQuery();) {
                DefaultSource source = null;

                while (rs.next()) {
                    source = new DefaultSource();
                    source.setId(rs.getLong("id"));
                    source.setName(rs.getString("name"));
                    source.setParentId(rs.getLong("parent_id"));
                    source.setIconName(rs.getString("icon_name"));
                    source.setOperationType(OperationType.getType(rs.getInt("operation_type_id")));
                    source.setRefCount(rs.getInt("ref_count"));
                    sourceList.add(source);
                }

                return sourceList;
            }

        } catch (SQLException e) {
            Logger.getLogger(SourceDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }
}
