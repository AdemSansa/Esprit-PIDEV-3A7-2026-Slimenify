package Controllers.Blog;

import Entities.Blog;
import Entities.Category;
import Entities.User;
import util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import Service.BlogService;
import Service.CategoryService;

import java.io.File;
import java.sql.Date;

public class AddBlogController {

    @FXML
    private TextField tfTitle;

    @FXML
    private TextArea tfContent;

    @FXML
    private TextField tfPhoto;

    @FXML
    private ComboBox<Category> cbCategory;

    BlogService bs = new BlogService();

    CategoryService cs = new CategoryService();

    @FXML
    public void initialize() {

        cbCategory.getItems().addAll(
                cs.afficher()
        );
    }

    @FXML
    void chooseImage() {

        FileChooser fc = new FileChooser();

        File file = fc.showOpenDialog(null);

        if(file != null) {

            tfPhoto.setText(
                    file.getAbsolutePath()
            );
        }
    }

    @FXML
    void addBlog() {
        
        int authorId = 0;
        User user = Session.getInstance().getUser();
        if (user != null) {
            authorId = user.getId();
        }

        Blog b = new Blog(
                tfTitle.getText(),
                tfContent.getText(),
                tfPhoto.getText(),
                new Date(System.currentTimeMillis()),
                cbCategory.getValue(),
                authorId
        );

        bs.ajouter(b);

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setContentText(
                "Blog added successfully"
        );

        alert.show();
        
        util.SceneManager.loadPage("/com/example/psy/Blogs/blog_feed.fxml");
    }
}