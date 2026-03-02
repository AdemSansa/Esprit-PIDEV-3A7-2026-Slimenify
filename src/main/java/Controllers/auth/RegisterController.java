package Controllers.auth;

import Entities.Therapistis;
import Entities.User;
import Service.AuthService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import util.SceneManager;
import java.sql.Date;
import java.time.LocalDate;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import com.github.sarxos.webcam.Webcam;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;

public class RegisterController {
    @FXML
    private ComboBox<String> accountTypeBox;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private VBox patientFields;
    @FXML
    private TextField phoneField;
    @FXML
    private javafx.scene.control.DatePicker dobPicker;
    @FXML
    private ComboBox<String> genderBox;

    @FXML
    private VBox therapistFields;
    @FXML
    private TextField therapistPhoneField;
    @FXML
    private TextField specializationField;

    @FXML
    private Label messageLabel;

    @FXML
    private ImageView facePreview;
    @FXML
    private Label faceStatusLabel;

    private String capturedFaceUrl = null;
    private final AuthService authService = AuthService.getInstance();
    private final Service.ImgBBService imgBBService = Service.ImgBBService.getInstance();

    @FXML
    public void initialize() {
        accountTypeBox.setItems(FXCollections.observableArrayList("Patient", "Therapist"));
        accountTypeBox.setValue("Patient");

        genderBox.setItems(FXCollections.observableArrayList("Homme", "Femme", "Autre"));
        genderBox.setValue("Homme");
    }

    @FXML
    private void handleAccountTypeChange() {
        boolean isTherapist = "Therapist".equals(accountTypeBox.getValue());

        therapistFields.setVisible(isTherapist);
        therapistFields.setManaged(isTherapist);

        patientFields.setVisible(!isTherapist);
        patientFields.setManaged(!isTherapist);
    }

    @FXML
    private void handleRegister() {
        // Reset styles and message
        fullNameField.getStyleClass().remove("form-error");
        emailField.getStyleClass().remove("form-error");
        passwordField.getStyleClass().remove("form-error");
        phoneField.getStyleClass().remove("form-error");
        therapistPhoneField.getStyleClass().remove("form-error");
        specializationField.getStyleClass().remove("form-error");
        dobPicker.getStyleClass().remove("form-error");
        messageLabel.setText("");

        try {
            boolean hasError = false;
            StringBuilder errorMsg = new StringBuilder();

            String accountType = accountTypeBox.getValue();
            String fullName = fullNameField.getText();

            if (fullName == null || fullName.trim().isEmpty()) {
                fullNameField.getStyleClass().add("form-error");
                errorMsg.append("Full name is required. ");
                hasError = true;
            }

            if (emailField.getText() == null || !emailField.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                emailField.getStyleClass().add("form-error");
                errorMsg.append("Invalid email address. ");
                hasError = true;
            }

            if (passwordField.getText() == null || passwordField.getText().length() < 4) {
                passwordField.getStyleClass().add("form-error");
                errorMsg.append("Password too short (min 4). ");
                hasError = true;
            }

            if ("Therapist".equals(accountType)) {
                if (therapistPhoneField.getText() == null
                        || !therapistPhoneField.getText().matches("^[24579]\\d{7}$")) {
                    therapistPhoneField.getStyleClass().add("form-error");
                    errorMsg.append("Invalid Tunisian phone (8 digits). ");
                    hasError = true;
                }
                if (specializationField.getText() == null || specializationField.getText().trim().isEmpty()) {
                    specializationField.getStyleClass().add("form-error");
                    errorMsg.append("Specialization is required. ");
                    hasError = true;
                }
            } else {
                // Patient specific validation
                if (phoneField.getText() == null || !phoneField.getText().matches("^[24579]\\d{7}$")) {
                    phoneField.getStyleClass().add("form-error");
                    errorMsg.append("Invalid Tunisian phone (8 digits). ");
                    hasError = true;
                }
                if (dobPicker.getValue() == null) {
                    dobPicker.getStyleClass().add("form-error");
                    errorMsg.append("Birth date is required. ");
                    hasError = true;
                }
            }

            if (hasError) {
                throw new Exception(errorMsg.toString().trim());
            }

            String[] names = fullName.trim().split(" ", 2);
            String firstName = names[0];
            String lastName = names.length > 1 ? names[1] : "";

            if ("Therapist".equals(accountType)) {
                Therapistis therapist = new Therapistis();
                therapist.setFirstName(firstName);
                therapist.setLastName(lastName);
                therapist.setEmail(emailField.getText());
                therapist.setPassword(passwordField.getText());
                therapist.setPhoneNumber(therapistPhoneField.getText());
                therapist.setSpecialization(specializationField.getText());
                therapist.setConsultationType("ONLINE");
                therapist.setStatus("ACTIVE");

                // Use captured Face ID photo if available
                if (capturedFaceUrl != null) {
                    therapist.setPhotoUrl(capturedFaceUrl);
                }

                authService.registerTherapist(therapist);
            } else {
                User user = new User(
                        firstName,
                        lastName,
                        emailField.getText(),
                        passwordField.getText());
                user.setPhone(phoneField.getText());
                user.setDateOfBirth(Date.valueOf(dobPicker.getValue()));
                user.setGender(genderBox.getValue());

                // Use captured Face ID photo if available
                if (capturedFaceUrl != null) {
                    user.setPhotoUrl(capturedFaceUrl);
                }

                authService.register(user);
            }

            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Account created successfully ✔");
            SceneManager.switchScene("/com/example/psy/auth/login.fxml");

        } catch (Exception e) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void handleCaptureFace() {
        faceStatusLabel.setText("📷 Initialisation de la caméra...");
        faceStatusLabel.setStyle("-fx-text-fill: blue;");

        CompletableFuture.runAsync(() -> {
            try {
                Webcam webcam = Webcam.getDefault();
                if (webcam == null) {
                    updateFaceStatus("Caméra non trouvée.", true);
                    return;
                }

                webcam.open();
                BufferedImage image = webcam.getImage();
                webcam.close();

                if (image == null) {
                    updateFaceStatus("Échec de la capture.", true);
                    return;
                }

                updateFaceStatus("☁ Upload vers ImgBB...", false);

                // Convert to bytes for ImgBB
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);
                byte[] imageBytes = baos.toByteArray();

                // Upload
                String url = imgBBService.uploadImage(imageBytes);
                this.capturedFaceUrl = url;

                // Update UI (Preview and Status)
                Platform.runLater(() -> {
                    facePreview.setImage(new Image(new ByteArrayInputStream(imageBytes)));
                    faceStatusLabel.setText("✅ Face ID configuré !");
                    faceStatusLabel.setStyle("-fx-text-fill: green;");
                });

            } catch (Exception e) {
                e.printStackTrace();
                updateFaceStatus("Erreur: " + e.getMessage(), true);
            }
        });
    }

    private void updateFaceStatus(String text, boolean isError) {
        Platform.runLater(() -> {
            faceStatusLabel.setText(text);
            faceStatusLabel.setStyle("-fx-text-fill: " + (isError ? "red" : "blue") + ";");
        });
    }

    @FXML
    private void goToLogin() {
        SceneManager.switchScene("/com/example/psy/auth/login.fxml");
    }
}
