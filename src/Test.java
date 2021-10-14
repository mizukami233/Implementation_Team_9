import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


//This class only used to test, not relate with the project.
public class Test {
    public static void main(String[] args) {
        LocalTime appBeginTime = LocalTime.now();
        System.out.println(appBeginTime.format(DateTimeFormatter.ofPattern("HH:mm")));


        ArrayList<String> strings = new ArrayList<>();
        strings.get(strings.size() - 1);
        strings.remove(strings.size() - 1);
    }}

//
//        int min = 1; gpid
//        int minnum = 0;
//        for( gp ) {
//            gp = 1;2  3
//            gpnum = 0;0 0
//            for (app){
//                app.getgp == gp{
//                    gpnum +1
//                }
//            }
//
//            if ()
//            gp == min{
//                minnum = gpnum;
//            }
//            if()
//            // gpnum = 2 1  3
//            gpnum < minnum{
//                min = gp; 2 2
//                minnum = gpnum; 1 1
//            }
//
//        }
//        return min;
//    }
//}
//
////100001,1,636,1,4,2021/10/06,09:30,09:35
//
////100002,1,636,1,4,2021/10/06,09:30,09:35
//
////100003,1,636,1,4,2021/10/06,09:30,09:35
//ramdon
//1000001