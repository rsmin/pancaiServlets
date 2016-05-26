package mySQLConnection;

import org.json.*;
import java.sql.*;

/**
 * Created by Administrator on 2016/5/24.
 */
public class DBOperation {
    public String coonectionError="";
    //login return isSuccess,privilige, errorMessage
    public String login(String userId, String password){   //return a json string
        Boolean isSuccess=false;
        String privilige ="";
        String errorMessage="";

        String sql = "select * from user_login where id = ? and password = ?";
        Connection conn= getConnection();
        try{
            if(userId.isEmpty() || password.isEmpty()){
                errorMessage="userid or password is empty";
                throw new Exception();
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,userId);
            ps.setString(2,password);
            //execute the sql
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                isSuccess=true;
                privilige=rs.getString("privilege");
            }
            else{
                errorMessage="user doesn't exist";
            }
            rs.close();
            ps.close();
        }catch(Exception e){
            errorMessage=errorMessage+e.getMessage();
            coonectionError=e.getMessage();
            e.printStackTrace();
        }
        closeConnection(conn);

        //return result;
        JSONObject returnResult = new JSONObject();
        try {
            returnResult.put("errorMessage",errorMessage);
            returnResult.put("isSuccess", isSuccess);
            returnResult.put("privilige",privilige);
        }catch (Exception e){
            coonectionError=coonectionError+e.getMessage();
            e.printStackTrace();
        }
        return returnResult.toString();
    }


    public static Connection getConnection(){
        Connection conn=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String DBUrl="jdbc:mysql://www.timespacecolor.com.cn:3306/host_server";
            conn= DriverManager.getConnection(DBUrl,"admin","admin");
        }catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
    public static void closeConnection(Connection conn){
        if(conn!=null){
            try{
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
