package com.pancai;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import mySQLConnection.DBOperation;
import org.json.JSONObject;
import org.json.JSONTokener;

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
        PrintWriter errOut = response.getWriter();
        //读取数据
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(),"UTF8"));
            StringBuffer sb = new StringBuffer("");
            String temp;
            while((temp=br.readLine())!=null){
                sb.append(temp);
            }
            br.close();
            JSONTokener jsonParser = new JSONTokener(sb.toString());
            JSONObject inputJSON = (JSONObject) jsonParser.nextValue();
            if(inputJSON.getString("userId").isEmpty() || inputJSON.getString("password").isEmpty())
                throw new Exception("receive empty value");
            else
                userIdReceived=inputJSON.getString("userId");
                passwordReceived=inputJSON.getString("password");
        } catch (Exception e){
            errOut.println(e.getMessage());
        }
        //check sqlserver
        DBOperation dbOperation = new DBOperation();
        PrintWriter out = response.getWriter();
        try {
            out.println(dbOperation.login(userIdReceived,passwordReceived).toString());
            out.flush();
        }
        catch (Exception e){
            errOut.println(dbOperation.coonectionError);
            errOut.println(e.getMessage());

        }
        finally {
            out.close();
            errOut.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }
}
