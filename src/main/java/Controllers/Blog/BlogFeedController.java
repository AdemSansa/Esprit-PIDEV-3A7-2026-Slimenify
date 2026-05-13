package Controllers.Blog;

import Entities.Blog;
import Entities.User;
import Entities.Comment;
import util.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import Service.BlogService;
import Service.CommentService;
import util.SceneManager;

import java.io.IOException;
import java.util.List;
import java.sql.Date;

public class BlogFeedController {

    @FXML
    private VBox blogContainer;

    @FXML
    private TextField searchField;

    @FXML
    private Button btnNewBlog;

    BlogService bs = new BlogService();

    @FXML
    public void initialize() {

        User user = Session.getInstance().getUser();
        if (user != null && "patient".equalsIgnoreCase(user.getRole())) {
            if (btnNewBlog != null) {
                btnNewBlog.setVisible(false);
                btnNewBlog.setManaged(false);
            }
        }

        afficherBlogs();
    }

    public void afficherBlogs() {

        blogContainer.getChildren().clear();

        List<Blog> blogs = bs.afficher();

        for(Blog b : blogs) {

            VBox card = new VBox(12);

            card.getStyleClass().add("blog-card");

            Label title = new Label(b.getTitle());
            title.getStyleClass().add("blog-title");

            Label content = new Label(b.getContent());

            content.setWrapText(true);

            ImageView imageView = new ImageView();
            if (b.getPhoto() != null && !b.getPhoto().trim().isEmpty()) {
                try {
                    String photoPath = b.getPhoto();
                    if (!photoPath.startsWith("http") && !photoPath.startsWith("file:")) {
                        photoPath = "file:///" + photoPath.replace("\\", "/");
                    }
                    Image img = new Image(photoPath);
                    imageView.setImage(img);
                    imageView.setFitWidth(400);
                    imageView.setPreserveRatio(true);
                } catch (Exception ex) {
                    System.out.println("Could not load image: " + b.getPhoto());
                }
            }

            Label category = new Label(
                    "Category : "
                            + b.getCategory().getName()
            );

            Label date = new Label(
                    b.getCreatedAt().toString()
            );

            HBox actions = new HBox(10);
            User user = Session.getInstance().getUser();

            if (user != null && "therapist".equalsIgnoreCase(user.getRole()) && b.getAuthorId() == user.getId()) {
                Button deleteBtn = new Button("Delete");

                deleteBtn.setOnAction(e -> {
                    bs.supprimer(b.getId());
                    afficherBlogs();
                });

                Button updateBtn = new Button("Update");

                updateBtn.setOnAction(e -> {
                    System.out.println("Update");
                });

                actions.getChildren().addAll(
                        updateBtn,
                        deleteBtn
                );
            } else if (user != null && "patient".equalsIgnoreCase(user.getRole())) {
                TextField commentBox = new TextField();
                commentBox.setPromptText("Write a comment...");
                Button commentBtn = new Button("Comment");
                commentBtn.setOnAction(e -> {
                    if (commentBox.getText().trim().isEmpty()) return;
                    Comment c = new Comment();
                    c.setContent(commentBox.getText());
                    c.setRating(5);
                    c.setCreatedAt(new Date(System.currentTimeMillis()));
                    c.setBlog(b);
                    new CommentService().ajouter(c);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Comment added successfully");
                    alert.show();
                    commentBox.clear();
                });
                actions.getChildren().addAll(commentBox, commentBtn);
            }

            VBox blogContentWrapper = new VBox(10);
            blogContentWrapper.setStyle("-fx-cursor: hand;");
            
            blogContentWrapper.getChildren().addAll(
                    title,
                    imageView,
                    content,
                    category,
                    date
            );

            VBox commentsSection = new VBox(10);
            commentsSection.setVisible(false);
            commentsSection.setManaged(false);
            commentsSection.setStyle("-fx-padding: 10 0 0 0; -fx-border-color: transparent transparent transparent #cbd5e1; -fx-border-width: 0 0 0 3; -fx-padding: 0 0 0 10;");

            blogContentWrapper.setOnMouseClicked(e -> {
                if (commentsSection.isVisible()) {
                    commentsSection.setVisible(false);
                    commentsSection.setManaged(false);
                } else {
                    commentsSection.getChildren().clear();
                    Label commentsTitle = new Label("Comments:");
                    commentsTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #334155;");
                    commentsSection.getChildren().add(commentsTitle);
                    
                    List<Comment> comments = new CommentService().getCommentsByBlogId(b.getId());
                    if (comments.isEmpty()) {
                        Label noComments = new Label("No comments yet.");
                        noComments.setStyle("-fx-text-fill: #64748b; -fx-font-style: italic;");
                        commentsSection.getChildren().add(noComments);
                    } else {
                        for (Comment c : comments) {
                            VBox commentBox = new VBox(5);
                            commentBox.setStyle("-fx-background-color: #f1f5f9; -fx-padding: 10; -fx-background-radius: 8;");
                            Label commentContent = new Label(c.getContent());
                            commentContent.setWrapText(true);
                            commentContent.setStyle("-fx-text-fill: #334155;");
                            Label commentDate = new Label(c.getCreatedAt().toString());
                            commentDate.setStyle("-fx-font-size: 10px; -fx-text-fill: #94a3b8;");
                            commentBox.getChildren().addAll(commentContent, commentDate);
                            commentsSection.getChildren().add(commentBox);
                        }
                    }
                    commentsSection.setVisible(true);
                    commentsSection.setManaged(true);
                }
            });

            card.getChildren().addAll(
                    blogContentWrapper,
                    commentsSection,
                    actions
            );

            blogContainer.getChildren().add(card);
        }
    }

    @FXML
    void searchBlog() {

        String mot = searchField.getText();

        blogContainer.getChildren().clear();

        List<Blog> blogs = bs.rechercher(mot);

        for(Blog b : blogs) {

            VBox card = new VBox(10);

            Label title = new Label(b.getTitle());

            Label content = new Label(b.getContent());

            card.getChildren().addAll(
                    title,
                    content
            );

            blogContainer.getChildren().add(card);
        }
    }

    @FXML
    void goToAddBlog() throws IOException {
        SceneManager.loadPage("/com/example/psy/Blogs/add_blog.fxml");
    }
}