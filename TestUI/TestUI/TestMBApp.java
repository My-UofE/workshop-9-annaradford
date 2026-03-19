import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestMBApp {
    public static void main(String[] args){
        MessageBoard messageboard = new MessageBoard("Coding Support");
        messageboard.addPost("Alex Adam","Help with Java","Hi, could anyone help me I need to learn how to code in java!");
        messageboard.addPost("Belinda Bennett","Help with Java","Hi Alex. Yes I can send some tutorials I found useful.");
        messageboard.addPost("Cindy Carter","Coding on a Chromebook","Hi, could anyone help me I need to learn how to code in java!");
        messageboard.addPost("Dennis Dobson", "Windows problems", "My windows laptop is stuck on a reboot loop. Does anyone know what to do!");
        messageboard.addPost("Ellie","Java IDE","Can someone recommend a Java IDE?",20148);
        messageboard.addPost("Fred Fansha", "Java IDE", "I just use VS code", 20149);

        List<Post> posts = new ArrayList<>();
        posts = messageboard.searchPostBySubject("Help with Java");
        for (Post post : posts){
            messageboard.deletePost(post.getPostID());
        }

        try{
        messageboard.saveMessageBoard("codingsupport.ser");
        messageboard.loadMessageBoard("codingsupport.ser");
        } catch (ClassNotFoundException e){
            System.out.println("class not found");
        }
        catch (IOException e){
            System.out.println("file not saved or loaded");
        }

        try {
            MessageBoard chaos = new MessageBoard("pure choas");
            chaos.addPost("banan man", "lost me bagel", "help i lost my bagel!");
            messageboard.loadMessageBoard("codingsupport.ser");
        } catch( IOException ex ) {
            System.out.println("Board not loaded.");
            ex.printStackTrace();
        } catch( ClassNotFoundException ex ) {
            System.out.println("Could not find class.");
            ex.printStackTrace();
        }

        try {
            int[] postIDS = messageboard.getPostIDs();
            messageboard.savePostAsTextFile(postIDS[0]);
        } catch(IOException ex ) {
            System.out.println("File not saved.");
            ex.printStackTrace();
}
    }

}
