package org.example.DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.Connection.ConnectionFactory;

/**
 * Clasa AbstractDAO<T> este o clasa generica ce ofera functionalitati de baza pentru accesarea si manipularea datelor:
 * inserare, cautare, actualizare si stergere.
 * Foloseste tehnici de reflectie pentru a genera automat interogarile SQL si pentru a popula obiectele.
 *
 * @param <T> Tipul entitatii gestionate de instanta curenta.
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    /**
     * Constructorul clasei. Determina tipul generic T la runtime.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creeaza o interogare SQL de tip SELECT pentru un anumit camp.
     *
     * @param field Numele campului dupa care se face cautarea.
     * @return String ce contine interogarea SQL.
     */
    private String createSelectQuery(String field) {
        return "SELECT * FROM `" + type.getSimpleName() + "` WHERE " + field + " =?";
    }

    /**
     * Returneaza toate obiectele din tabelul corespunzator clasei T.
     *
     * @return Lista de obiecte de tip T.
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<T> list = new ArrayList<>();
        String query = "SELECT * FROM `" + type.getSimpleName() + "`";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            list = createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return list;
    }

    /**
     * Gaseste un obiect dupa ID.
     *
     * @param id Valoarea ID-ului.
     * @return Obiectul T daca este gasit, altfel null.
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            List<T> result = createObjects(resultSet);
            if (result.isEmpty()) {
                return null;
            }
            return result.get(0);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * Creeaza obiecte de tip T pe baza informatiilor din ResultSet.
     *
     * @param resultSet Rezultatul interogarii SQL.
     * @return Lista de obiecte T populate.
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;

        for (Constructor c : ctors) {
            if (c.getGenericParameterTypes().length == 0) {
                ctor = c;
                break;
            }
        }

        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Insereaza un obiect in baza de date.
     *
     * @param t Obiectul de inserat.
     * @return Obiectul inserat.
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        StringBuilder fields = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        List<Object> values = new ArrayList<>();

        for (Field field : type.getDeclaredFields()) {
            fields.append(field.getName()).append(",");
            placeholders.append("?,");
            try {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), type);
                Method getter = pd.getReadMethod();
                values.add(getter.invoke(t));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        fields.setLength(fields.length() - 1);
        placeholders.setLength(placeholders.length() - 1);

        String query = "INSERT INTO `" + type.getSimpleName() + "` (" + fields + ") VALUES (" + placeholders + ")";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return t;
    }

    /**
     * Actualizeaza un obiect existent in baza de date pe baza ID-ului.
     *
     * @param t Obiectul de actualizat.
     * @return Obiectul actualizat.
     */
    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        StringBuilder updates = new StringBuilder();
        List<Object> values = new ArrayList<>();
        Object idValue = null;

        for (Field field : type.getDeclaredFields()) {
            try {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), type);
                Method getter = pd.getReadMethod();
                Object value = getter.invoke(t);

                if (field.getName().equalsIgnoreCase("id")) {
                    idValue = value;
                    continue;
                }

                updates.append(field.getName()).append("=?,");
                values.add(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        updates.setLength(updates.length() - 1);

        String query = "UPDATE `" + type.getSimpleName() + "` SET " + updates + " WHERE id=?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }

            statement.setObject(values.size() + 1, idValue);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return t;
    }

    /**
     * Sterge un obiect din baza de date dupa ID.
     *
     * @param id ID-ul obiectului care trebuie sters.
     */
    public void deleteById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM `" + type.getSimpleName() + "` WHERE id=?";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteById " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}