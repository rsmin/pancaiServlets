import mySQLConnection.DBOperation;
/**
 * Created by rsmin on 2016/5/25.
 */
public class testmain {
    public static void main(String args[]){
        DBOperation dbO=new DBOperation();
        String userId="asdf";
        String password="admin";
        System.out.println(dbO.login(userId,password));

    }
}
