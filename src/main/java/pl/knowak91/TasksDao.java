package pl.knowak91;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TasksDao {

    protected List<TaskModel> itemsList = new ArrayList();

    public TasksDao() {
        readFromDatabase();
    }

    private void readFromDatabase() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks");

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer userId = resultSet.getInt("user_id");

                this.itemsList.add(new TaskModel(id, name, userId));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public List<TaskModel> getItemsList() {
        return itemsList;
    }

    public TaskModel getTaskById(String id) {
        for(TaskModel task : this.itemsList) {
            if(task.getId().equals(Integer.valueOf(id))){
                return task;
            }
        } return null;
    }

    public void updateTask(TaskModel task) {
        Integer id = task.getId();
        String name = task.getName();
        Integer userId = task.getUserId();

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();

            statement.executeUpdate(
                    String.format(
                            "UPDATE tasks SET name = '%s', user_id = '%d' WHERE id = '%d';",
                            name, userId, id));


        } catch (SQLException e) {
            System.out.println(this.getClass().getName());
        }
    }




}
