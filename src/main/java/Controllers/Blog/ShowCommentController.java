package Controllers.Blog;

import Entities.Comment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import Service.CommentService;

import java.io.IOException;
import java.util.List;

public class ShowCommentController {

    @FXML
    private VBox commentContainer;

    @FXML
    private TextField tfSearch;

    CommentService cs =
            new CommentService();

    @FXML
    public void initialize() {

        afficherComments(
                cs.afficher()
        );
    }

    // ✅ AFFICHER COMMENTS

    void afficherComments(List<Comment> comments) {

        commentContainer
                .getChildren()
                .clear();

        for(Comment c : comments) {

            VBox card =
                    new VBox();

            card.setSpacing(12);

            card.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-padding: 20;" +
                            "-fx-background-radius: 20;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08),10,0,0,4);"
            );

            // BLOG TITLE

            Label blogTitle =
                    new Label(
                            "Blog : "
                                    + c.getBlog().getTitle()
                    );

            blogTitle.setStyle(
                    "-fx-font-size: 16;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #627255;"
            );

            // CONTENT

            Label content =
                    new Label(
                            c.getContent()
                    );

            content.setWrapText(true);

            content.setStyle(
                    "-fx-font-size: 15;" +
                            "-fx-text-fill: #334155;"
            );

            // RATING

            Label rating =
                    new Label(
                            "⭐ Rating : "
                                    + c.getRating()
                    );

            rating.setStyle(
                    "-fx-font-size: 14;" +
                            "-fx-text-fill: #f59e0b;" +
                            "-fx-font-weight: bold;"
            );

            // DATE

            Label date =
                    new Label(
                            c.getCreatedAt()
                                    .toString()
                    );

            date.setStyle(
                    "-fx-text-fill: gray;"
            );

            // BUTTONS

            HBox actions =
                    new HBox();

            actions.setSpacing(10);

            Button deleteBtn =
                    new Button("Delete");

            deleteBtn.setStyle(
                    "-fx-background-color: #ef4444;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 15;"
            );

            Button updateBtn =
                    new Button("Update");

            updateBtn.setStyle(
                    "-fx-background-color: #627255;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 15;"
            );

            // DELETE ACTION

            deleteBtn.setOnAction(e -> {

                cs.supprimer(c.getId());

                afficherComments(
                        cs.afficher()
                );
            });

            // UPDATE ACTION

            updateBtn.setOnAction(e -> {

                TextInputDialog dialog =
                        new TextInputDialog(
                                c.getContent()
                        );

                dialog.setTitle(
                        "Update Comment"
                );

                dialog.setHeaderText(null);

                dialog.setContentText(
                        "New Content :"
                );

                dialog.showAndWait()
                        .ifPresent(newText -> {

                            c.setContent(newText);

                            cs.modifier(c);

                            afficherComments(
                                    cs.afficher()
                            );
                        });
            });

            actions.getChildren()
                    .addAll(updateBtn, deleteBtn);

            card.getChildren()
                    .addAll(
                            blogTitle,
                            content,
                            rating,
                            date,
                            actions
                    );

            commentContainer
                    .getChildren()
                    .add(card);
        }
    }

    // ✅ SEARCH

    @FXML
    void searchComment() {

        afficherComments(

                cs.rechercher(
                        tfSearch.getText()
                )
        );
    }
}