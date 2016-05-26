package com.pancai;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import mySQLConnection.DBOperation;
import org.json.*;

/**
 * Created by Administrator on 2016/5/24.
 */

//login received: userId, password;
//login return isSuccess,privilige, errorMessage;
@WebServlet(name = "loginMethod")
public class loginMethod extends HttpServlet {
    String userIdReceived="";
    String passwordReceived="";
    String result="";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // PrintWriter errOut = response.getWriter();
        //读取数据
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(),"UTF-8"));
            StringBuffer sb = new StringBuffer("");
            String temp;
            while((temp=br.readLine())!=null){
                sb.append(temp);
            }
            br.close();
            JSONObject inputJSON = new JSONObject(sb.toString());
                userIdReceived=inputJSON.getString("userId");
                passwordReceived=inputJSON.getString("password");
        } catch (Exception e){
           e.printStackTrace();
        }
        //check sqlserver
        DBOperation dbOperation = new DBOperation();

        DataOutputStream out = new DataOutputStream(response.getOutputStream());
        //将要上传的内容写入流中
        try{
            String outputData=dbOperation.login(userIdReceived,passwordReceived).toString();
            byte[] content = outputData.getBytes("UTF-8");
            out.write(content, 0, content.length);
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            out.close();
        }
       // PrintWriter out = response.getWriter();
       // try {
       //     out.println(dbOperation.login(userIdReceived,passwordReceived).toString());
        //    out.flush();
       // }
      //  catch (Exception e){
       //     errOut.println(dbOperation.coonectionError);
       //     errOut.println(e.getMessage());

       // }
       // finally {
       //     out.close();
       //     errOut.close();
       // }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }
}
