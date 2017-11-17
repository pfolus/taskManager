package pl.knowak91;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;

public class TasksHandler implements com.sun.net.httpserver.HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        TasksDao tasksDao = new TasksDao();


        String response = "";
        String method = httpExchange.getRequestMethod();

        // Send a form if it wasn't submitted yet.
        if(method.equals("GET")){
            // get a template file
            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/taskManager.twig");
            // create a model that will be passed to a template
            JtwigModel model = JtwigModel.newModel();
            model.with("tasks", tasksDao.getItemsList());
            // render a template to a string
            response = template.render(model);
        }

        //process body of request generated by AJAX
        else if(method.equals("POST")){
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String changedTaskData = br.readLine();
            System.out.println(changedTaskData);
            processRequestBody(changedTaskData);

        }

        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void processRequestBody(String body) {
        String taskNumber;
        String userNumber;

        String[] pair = body.split("&");
        taskNumber = pair[0];
        userNumber = pair[1];

        TasksDao tasksDao = new TasksDao();

        TaskModel taskPendingUpdate = tasksDao.getTaskById(taskNumber);
        taskPendingUpdate.setUserId(Integer.valueOf(userNumber));
        tasksDao.updateTask(tasksDao.getTaskById(taskNumber));


    }


}
