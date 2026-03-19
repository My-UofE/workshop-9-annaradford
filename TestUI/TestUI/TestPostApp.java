import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestPostApp {
    public static void main(String[] args){
        Post post = new Post("Alex Adam","Help with JavaE","Hi, could anyone help me i need to learn how to code in java!");
        System.out.println(post);
        
        LocalDate date1 = LocalDate.of(2023,3,30); // 30th March 2023
        LocalDate date2 = LocalDate.now().minusDays(7); // seven days before today
        LocalDate date3 = LocalDate.ofEpochDay(50); // epoch day 50

        // create formatter to diplay dates in day/month/year format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println(date1.format(formatter));
        System.out.println(date2.format(formatter));
        System.out.println(date3.format(formatter));
    }
}
