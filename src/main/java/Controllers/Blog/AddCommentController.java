package Controllers.Blog;

import Entities.Blog;
import Entities.Comment;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import Service.BlogService;
import Service.CommentService;

import java.sql.Date;

public class AddCommentController {

    @FXML
    private TextArea tfContent;

    @FXML
    private Spinner<Integer> spRating;

    @FXML
    private ComboBox<Blog> cbBlog;

    CommentService cs =
            new CommentService();

    BlogService bs =
            new BlogService();

    @FXML
    public void initialize() {

        cbBlog.getItems()
                .addAll(bs.afficher());

        SpinnerValueFactory<Integer> svf =
                new SpinnerValueFactory
                        .IntegerSpinnerValueFactory(1,5,1);

        spRating.setValueFactory(svf);
    }

    @FXML
    void ajouterComment() {

        Comment c =
                new Comment();

        c.setContent(
                tfContent.getText()
        );

        c.setRating(
                spRating.getValue()
        );

        c.setCreatedAt(
                new Date(
                        System.currentTimeMillis()
                )
        );

        c.setBlog(
                cbBlog.getValue()
        );

        cs.ajouter(c);

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Success");

        alert.setContentText(
                "Comment ajouté"
        );

        alert.show();
    }
}