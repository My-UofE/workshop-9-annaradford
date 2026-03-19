import java.util.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.io.*;
import java.time.format.DateTimeFormatter;


public class MessageBoard implements MessageBoardInterface {
    private List<Post> posts;
    private String boardName;

    public MessageBoard(String boardName) {
        this.boardName = boardName;
        this.posts = new ArrayList<>();
    }

    public String getBoardName() {
        return boardName;
    }

    public int[] getPostIDs() {
        int[] postIDs = new int[posts.size()];
        int i = 0;
        for (Post post : posts) {
            postIDs[i++] = post.getPostID();
        }
        return postIDs;
    }

    public int getPostIndex(int postID) throws IDInvalidException {
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getPostID() == postID) {
                return i;
            }
        }
        throw new IDInvalidException("Invalid post ID.");
    }

    public void addPost(String author, String subject, String message){
        Post post = new Post(author,subject,message);
        this.posts.add(post);
    }

    public void addPost(String author, String subject, String message, int date){
        LocalDate localDate = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate();
        Post post = new Post(author,subject,message,localDate);
        this.posts.add(post);
    }

    public String getFormattedPost(int postID) throws IDInvalidException{
        try {
            Post post = posts.get(getPostIndex(postID));
            return post.toFormattedString();
        } catch(IDInvalidException e){
            throw new IDInvalidException("no post has this ID");
        }
    }   

    public int[] searchPostsBySubject(String subject){
        List<Integer> subjectPosts = new ArrayList<>();
        for (Post post : posts){
            if (post.getSubject().equals(subject)){
                subjectPosts.add(post.getPostID());
            }
        }
        int[] posts = new int[subjectPosts.size()];

        for (int i = 0; i < subjectPosts.size(); i++){
            posts[i] = subjectPosts.get(i);
        }
        return posts;
    }

    public int[] searchPostsByDate(int startDate, int endDate){
        List<Integer> subjectPosts = new ArrayList<>();
        for (Post post : posts){
            if (Integer.compare(post.getDate(),startDate) >= 0 && Integer.compare(post.getDate(),endDate) <= 0){
                subjectPosts.add(post.getPostID());
            }
        }
        int[] posts = new int[subjectPosts.size()];

        for (int i = 0; i < subjectPosts.size(); i++){
            posts[i] = subjectPosts.get(i);
        }

        return posts;
    }

    public void deletePost(int PostID){
        posts.remove(getPostIndex(PostID));
    }

    public void saveMessageBoard(String filename) throws IOException{
        try{
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
        // store boardName attribute
        out.writeObject(boardName);
        // convert posts to array Post[] to simplifies the deserialisation
        Post[] postArray = posts.toArray(new Post[posts.size()]);
        //  store Post array
        out.writeObject(postArray);
        out.close();
        } catch(IOException e){
            throw new IOException("file not saved");
        }
    } 

    public void loadMessageBoard(String filename) throws IOException, ClassNotFoundException{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));

        // Read the boardName attribute
        this.boardName = (String) in.readObject();

        // Read the Post array and convert it back to the List<Post>
        Post[] postArray = (Post[]) in.readObject();
        
        // Clear existing posts
        this.posts.clear();

        // Add the loaded posts to the list
        Collections.addAll(this.posts, postArray);

        in.close(); // Close the input stream
    }

    public void savePostAsTextFile(int postID, String filename) throws IOException{
        Post post = posts.get(this.getPostIndex(postID));
        post.saveAsTextFile();
    }
}
